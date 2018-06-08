package com.outsource.danding.qingdaoic.net

import android.os.Build
import android.text.TextUtils
import com.google.gson.JsonObject
import com.outsource.danding.qingdaoic.app.QdApp
import com.outsource.danding.qingdaoic.net.api.ApiConstants
import com.outsource.danding.qingdaoic.net.api.ApiConstants.BASE_URL
import com.outsource.danding.qingdaoic.net.api.ApiService
import com.outsource.danding.qingdaoic.net.api.HttpResult
import com.outsource.danding.qingdaoic.net.retrofit.ResponseConverterFactory
import com.outsource.danding.qingdaoic.util.HttpsUtils
import com.outsource.danding.qingdaoic.util.NetworkUtils
import com.outsource.danding.qingdaoic.util.SystemUtils
import io.reactivex.Observable
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.File
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit





class HttpClient private constructor() {

    private var mOkHttpClient: OkHttpClient? = null

    private var personId: String? = null

    private val apiService: ApiService

    private fun getBaseMap(isAddToken: Boolean = true): TreeMap<String, String> {
        val map = TreeMap<String, String>()
        map.put("time", System.currentTimeMillis().toString())
        if (isAddToken && !TextUtils.isEmpty(personId)) {
            map.put("personId", personId!!)
        }
        return map
    }


    init {
        initOkHttpClient()
        apiService = createApi()
    }

    private fun createApi(): ApiService {
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(mOkHttpClient!!)
                .addConverterFactory(ResponseConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return retrofit.create(ApiService::class.java)
    }


    fun setPersonId(personId: String) {
        this.personId = personId
    }

    /**
     * 用户登陆
     */
    fun login(username: String, password: String): Observable<JsonObject> {
        val map = getBaseMap(false)
        map.put("username", username)
        map.put("password", password)
        return apiService.login(DataHandler.encryptParams(map))
    }





    /**
     * 初始化OKHttpClient,设置缓存,设置超时时间,设置打印日志,设置UA拦截器
     */
    private fun initOkHttpClient() {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        if (mOkHttpClient == null) {
            //设置Http缓存
            val cache = Cache(File(QdApp.appContext.cacheDir, "HttpCache"), (1024 * 1024 * 10).toLong())
            mOkHttpClient = OkHttpClient.Builder()
                    .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                    .cache(cache)
                    .addInterceptor(HeaderInterceptor())
                    .addInterceptor(interceptor)
                    .addNetworkInterceptor(CacheInterceptor())
                    .retryOnConnectionFailure(true)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .build()
        }
    }


    /**
     * 为okhttp添加缓存，这里是考虑到服务器不支持缓存时，从而让okhttp支持缓存
     */
    private inner class CacheInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            // 有网络时 设置缓存超时时间1个小时
//            val maxAge = 60 * 60
            val maxAge = 60 * 5
            // 无网络时，设置超时为1天
//            val maxStale = 60 * 60 * 24
            val maxStale = 60 * 60
            var request = chain.request()
            request = if (NetworkUtils.isNetworkAvailable(QdApp.appContext)) {
                //有网络时只从网络获取
                request.newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build()
            } else {
                //无网络时只从缓存中读取
                request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build()
            }


            var response = chain.proceed(request)
            response = if (NetworkUtils.isNetworkAvailable(QdApp.appContext)) {
                response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build()
            } else {
                response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build()
            }
            return response
        }
    }


    //添加header
    private inner class HeaderInterceptor : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            val language = if (SystemUtils.isZh()) {
                "zh_CN"
            } else {
                "en_US"
            }



            val originalRequest = chain.request()
                    .newBuilder()
                    .header("Content-Type", "application/json;charset=utf-8")
                    .header("Build-CU", "1")
                    .header("SysVersion-CU", SystemUtils.getSystemVersion())
                    .header("SysSDK-CU", Build.VERSION.SDK_INT.toString())
                    .header("Channel-CU", "")
                    .header("Mobile-Model-CU", SystemUtils.getSystemModel())
                    .header("UUID-CU:APP", "xxxxxxxxxxxxxxxx")
                    .header("Platform-CU", "android")
                    .header("Network-CU", NetworkUtils.getNetType(QdApp.appContext)!!)
                    .header("Language", language)
                    .build()
            return chain.proceed(originalRequest)
        }
    }

    companion object {

        private var INSTANCE: HttpClient? = null

        val instance: HttpClient
            get() {
                if (INSTANCE == null) {
                    synchronized(HttpClient::class.java) {
                        if (INSTANCE == null) {
                            INSTANCE = HttpClient()
                        }
                    }
                }
                return INSTANCE!!
            }
    }
}