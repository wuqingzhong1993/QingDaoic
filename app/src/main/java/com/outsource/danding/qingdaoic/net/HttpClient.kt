package com.outsource.danding.qingdaoic.net

import android.os.Build
import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.outsource.danding.qingdaoic.app.QdApp
import com.outsource.danding.qingdaoic.app.QdApplication
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
import org.json.JSONObject
import okhttp3.RequestBody
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


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
        map.put("userName", username)
        map.put("passWord", password)
        map.put("mobileClient","1")
        return apiService.login(DataHandler.encryptParams(map))
    }

    /**
     * 获取个人信息
     */
    fun getUserInfo():Observable<JsonObject>{

        return apiService.getUserInfo(this.personId!!)
    }

    /**
     * 获取待办事项
     */
    fun getAuditWaiting(): Observable<JsonObject> {
        val gson=Gson();

        val map = HashMap<String,String>()
        map.put("personId", this.personId!!)
        val postInfoStr = gson.toJson(map)
        val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), postInfoStr)
        return apiService.getAuditWaiting(this.personId!!)
    }

    /**
     * 获取经办事项
     */
    fun getAuditByOperator(): Observable<JsonObject> {
        val gson=Gson();

        val map = HashMap<String,String>()
        map.put("personId", this.personId!!)
        val postInfoStr = gson.toJson(map)
        val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), postInfoStr)
        return apiService.getAuditByOperator(this.personId!!,"1")
    }

    /**
     * 获取个人资产
     */
    fun getZCList(): Observable<JsonObject> {
        return apiService.getAuditByOperator(this.personId!!,"1")
    }

    /**
     * 获取个人信息
     */
    fun getMessage(): Observable<JsonObject> {
        return apiService.getAuditByOperator(this.personId!!,"1")
    }

    /**
     * 获取办公列表
     */
    fun getApplyInfoList():Observable<JsonObject>{
        return apiService.getApplyInfoList(this.personId!!,"1")
    }

    /**
     * 初始化办公
     */
    fun initApplyAdd():Observable<JsonObject>{
        return apiService.initApplyAdd(this.personId!!)
    }

    /**
     * 获取差旅列表
     */
    fun getTravelList():Observable<JsonObject>{
        return apiService.getApplyInfoList(this.personId!!,"1")
    }

    /**
     * 计算金额
     */
    fun getMoney(city:String, province:String, personIds:MutableList<String>, outStartDate:String,
                 outEndDate:String,jt_tools:String,fh_tools:String,type:String,w_numbe:String,fare:String,hotel:String,
                 zs_jd:String,paiche:String):Observable<JsonObject>{

        val map = HashMap<String,String>()
        map.put("personId", this.personId!!)
        map.put("city",city)
        map.put("province",province)
        map.put("personIds",personIds.toString())
        map.put("outStartDate",outStartDate)
        map.put("outEndDate",outEndDate)
        map.put("jt_tools",jt_tools)
        map.put("fh_tools",fh_tools)
        map.put("type",type)
        map.put("w_numbe",w_numbe)
        map.put("fare",fare)
        map.put("hotel",hotel)
        map.put("zs_jd",zs_jd)
        map.put("paiche",paiche)

        return apiService.getMoney(map)
    }

    /**
     * 提交
     */
    fun commit(map:HashMap<String,String>):Observable<JsonObject>{
        val map = HashMap<String,String>()
        map.put("personId", this.personId!!)
        return apiService.commit(map)
    }

    /**
     * 初始化OKHttpClient,设置缓存,设置超时时间,设置打印日志,设置UA拦截器
     */
    private fun initOkHttpClient() {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        if (mOkHttpClient == null) {
            //设置Http缓存
            val cache = Cache(File(QdApplication.appContext.cacheDir, "HttpCache"), (1024 * 1024 * 10).toLong())
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
            request = if (NetworkUtils.isNetworkAvailable(QdApplication.appContext)) {
                //有网络时只从网络获取
                request.newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build()
            } else {
                //无网络时只从缓存中读取
                request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build()
            }


            var response = chain.proceed(request)
            response = if (NetworkUtils.isNetworkAvailable(QdApplication.appContext)) {
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
                    .header("Network-CU", NetworkUtils.getNetType(QdApplication.appContext)!!)
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