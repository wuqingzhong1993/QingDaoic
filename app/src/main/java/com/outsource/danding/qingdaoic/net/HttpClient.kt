package com.outsource.danding.qingdaoic.net

import android.os.Build
import android.text.TextUtils
import com.google.gson.*
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
import kotlin.collections.AbstractList
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
     * 修改个人信息
     */
    fun getPassCheck(oldPass:String,newPass:String,qurenPass:String):Observable<JsonObject>{
        val map = HashMap<String,String>()
        map.put("personId", this.personId!!)
        map.put("oldPass", oldPass)
        map.put("newPass", newPass)
        map.put("qurenPass",qurenPass)
        return apiService.getPassCheck(map )
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
     * 获取审核项详情
     */
    fun getApplyInfoDetail(expendId:String):Observable<JsonObject>{

        return apiService.getApplyInfoDetail(this.personId!!,expendId)
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
     * 获取审核申请列表
     */
    fun getAuditApplyList():Observable<JsonObject>{
        return apiService.getApplyInfoList(this.personId!!,"2")
    }

    /**
     * 获取审核历史列表
     */
    fun getAuditHistoryList():Observable<JsonObject>{
        return apiService.getAuditHistoryList(this.personId!!,"1")
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
     * 办公申请添加页面——提交0,暂存1
     * zy20180619
     */
    fun saveBusinessApply( flag:String,expendType:String,applyDeptName:String, isLoan:String,
                  loanReason:String,budgetAmount:String,remark:String,cashContent:String,
                  officeList: String):Observable<JsonObject>{

        val map = HashMap<String,String>()
        map.put("personId", this.personId!!)
        map.put("flag",flag)
        map.put("expendType",expendType)
        map.put("applyDeptName",applyDeptName)
        map.put("isLoan",isLoan)
        map.put("loanReason",loanReason)
        map.put("budgetAmount",budgetAmount)
        map.put("remark",remark)
        map.put("cashContent",cashContent)
        val gson=Gson()
        var ob:JsonObject= JsonObject()
        val dataList:JsonArray= JsonParser().parse(officeList).asJsonArray
        ob.add("dataList",dataList)
        gson.toJson(ob)
        map.put("officeList",ob.toString())

        return apiService.saveBusinessApply(map)

    }
    /**
     * 会议申请添加页面——提交0,暂存1
     * zy20180619
     */
    fun saveConferenceApply(flag:String,expendType:String,applyDeptName:String, isLoan:String,
                            loanReason:String?,budgetAmount:String?,remark:String?,
                            meetingName:String,meetingTime:String,trainEnd:String,trainReport:String,trainLeave:String,
                            meetingCategory:String,meetingPlace:String,estimatedNum:String,staffNum:String,
                            meetingBudget:String,meetingReason:String?):Observable<JsonObject>{

        val map = HashMap<String,String>()
        map.put("personId", this.personId!!)
        map.put("flag",flag)
        map.put("expendType",expendType)
        map.put("applyDeptName",applyDeptName)
        map.put("isLoan",isLoan)
        if(loanReason!=null){
            map.put("loanReason",loanReason)
        }
        if(budgetAmount!=null){
            map.put("budgetAmount",budgetAmount)
        }
        if(remark!=null){
            map.put("remark",remark)
        }

        map.put("meetingName",meetingName)
        map.put("meetingTime",meetingTime)
        map.put("trainEnd",trainEnd)
        map.put("trainReport",trainReport)
        map.put("trainLeave",trainLeave)
        map.put("meetingCategory",meetingCategory)
        map.put("meetingPlace",meetingPlace)
        map.put("estimatedNum",estimatedNum)
        map.put("staffNum",staffNum)
        map.put("meetingBudget",meetingBudget)
        if(meetingReason!=null){
            map.put("meetingReason",meetingReason)
        }
        return apiService.saveBusinessApply(map)
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
     * 获取个人资产
     */
    fun getDoZCList( pageNum:String):Observable<JsonObject>{

        return apiService.getDoZCList(this.personId!!,pageNum)
    }



    fun getshenQingInfoList():Observable<JsonObject>{
        return apiService.getshenQingInfoList(this.personId!!,"3")
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
                    .addInterceptor(CookieInterceptor())
                    .addInterceptor(CookieAddInterceptor())
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
     * 直接报销申请添加页面——提交0,暂存1
     * zy20180619
     */
    fun saveBaoXiaoApply(flag: String, applyDeptId: String, applyDeptName: String, internalId: String, internalName: String,
                         reason: String, sumNum: String, sumAmount: String, reimbList: String,
                         zzList:String, expendType: String, expendId: String, offcard: String,
                         cash: String): Observable<JsonObject> {
        val map = HashMap<String,String>()
        map.put("personId", this.personId!!)
        map.put("applyDeptId",applyDeptId)
        map.put("flag",flag)
        map.put("applyDeptName",applyDeptName)
        map.put("internalId",internalId)
        map.put("internalName",internalName)
        map.put("reason",reason)
        map.put("sumNum",sumNum)
        map.put("sumAmount",sumAmount)
        map.put("reimbList",reimbList)
        map.put("zzList",zzList)
        map.put("expendType",expendType)
        map.put("expendId",expendId)
        map.put("offcard",offcard)
        map.put("cash",cash)
        return apiService.saveBaoXiaoApply(map)

    }
    /**
     * 普通申请审核--通过
     * zy20180619
     */
    fun checkApplyPassed(taskId: String, expendId: String, reason: String, kemuName: String , kemuId: String,
                         basis:String ): Observable<JsonObject>  {
        val map = HashMap<String,String>()
        map.put("personId", this.personId!!)
        map.put("taskId",taskId)
        map.put("expendId",expendId)
        map.put("reason",reason)
        map.put("kemuName",kemuName)
        map.put("kemuId",kemuId)
        map.put("basis",basis)
        return apiService.checkApplyPassed(map)
    }

    /**
     * 普通申请审核--不通过
     * zy20180619
     */
    fun checkApplyNotPassed(taskId: String, expendId: String, reason: String,
                         basis:String ): Observable<JsonObject>  {
        val map = HashMap<String,String>()
        map.put("personId", this.personId!!)
        map.put("taskId",taskId)
        map.put("expendId",expendId)
        map.put("reason",reason)
        map.put("basis",basis)
        return apiService.checkApplyNotPassed(map)
    }


    /**
     * 出国申请添加页面——提交0,暂存1
     * zy20180619
     */
//    fun saveGoAbroadApply(flag: String, expendType: String, applyDeptName: String, isLoan: String,
//                          loanReason: String, budgetAmount: String, remark: String, cashContent: String,
//                          groupName: String, groupUnit: String, colonel: String, groupNum: String,
//                          visitingCountry: String, visitingDay: String, budgetAmount1: String,
//                          ht_money: String, zs_money: String, hs_money: String, jt_money: String,
//                          qt_money: String): Observable<JsonObject> {
//        val map = HashMap<String,String>()
//
//        map.put("personId", this.personId!!)
//        map.put("flag",flag)
//        map.put("expendType",expendType)
//        map.put("applyDeptName",applyDeptName)
//        map.put("isLoan",isLoan)
//        map.put("loanReason",loanReason)
//        map.put("budgetAmount",budgetAmount)
//        map.put("remark",remark)
//        map.put("cashContent",cashContent)
//
//        map.put("groupName",groupName)
//        map.put("groupUnit",groupUnit)
//        map.put("colonel",colonel)
//        map.put("groupNum",groupNum)
//        map.put("visitingCountry",visitingCountry)
//        map.put("visitingDay",visitingDay)
//        map.put("budgetAmount1",budgetAmount1)
//        map.put("ht_money",ht_money)
//        map.put("zs_money",zs_money)
//        map.put("hs_money",hs_money)
//        map.put("jt_money",jt_money)
//        map.put("qt_money",qt_money)
//
//        return apiService.saveGoAbroadApply(map)
//
//    }


    /**
     * 差旅费申请添加页面——提交0,暂存1
     * zy20180620
     */
    fun saveTravelApply(flag: String, internalId: String, internalName: String, applyDeptId: String,
                        applyDeptName: String, isLoan: String, loanReason: String?,
                        officeList: String): Observable<JsonObject>{
        val map = HashMap<String,String>()

        map.put("personId", this.personId!!)
        map.put("flag",flag)
        map.put("internalId",internalId)
        map.put("internalName",internalName)
        map.put("applyDeptId",applyDeptId)

        map.put("applyDeptName",applyDeptName)
        map.put("isLoan",isLoan)
        if(loanReason!=null){
            map.put("loanReason",loanReason)
        }
        map.put("officeList",officeList)

        return apiService.saveTravelApply(map)

    }

    fun saveCultivateApply(flag: String, expendType: String, applyDeptName: String, isLoan: String,
                           loanReason: String?, budgetAmount: String, remark: String?,
                            trainName: String, trainTime: String,
                           trainEnd: String, trainReport: String, trainLeave: String, trainPlace: String,
                           trainNum: String, trainStaffNum: String, trainBudget: String,
                           trainObject: String):  Observable<JsonObject> {
        val map = HashMap<String,String>()
        map.put("personId", this.personId!!)
        map.put("flag",flag)
        map.put("expendType",expendType)
        map.put("applyDeptName",applyDeptName)
        map.put("isLoan",isLoan)
        if(loanReason!=null){
            map.put("loanReason",loanReason)
        }
        map.put("budgetAmount",budgetAmount)
        if(remark!=null){
            map.put("remark",remark)
        }

        map.put("trainName", trainName)
        map.put("trainTime",trainTime)
        map.put("trainEnd",trainEnd)
        map.put("trainReport",trainReport)
        map.put("trainLeave",trainLeave)
        map.put("trainPlace",trainPlace)
        map.put("trainNum",trainNum)
        map.put("trainStaffNum",trainStaffNum)
        map.put("trainBudget",trainBudget)
        map.put("trainObject",trainObject)

        return apiService.saveCultivateActivity(map)
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

    //获取cookie头
    private inner class CookieInterceptor:Interceptor{
        override fun intercept(chain: Interceptor.Chain?): Response {
            val originalResponse = chain?.proceed(chain?.request())
            if(originalResponse?.headers("Set-Cookie")!=null)
            {
                val cookieBuffer = StringBuffer()
                for(header in originalResponse.headers("Set-Cookie"))
                {
                    val cookieArray = header.split(";")
                    val cookie=cookieArray[0]
                    cookieBuffer.append(cookie).append(";")
                }
                if(!cookieBuffer.isEmpty())//cookie不为空
                {
                    QdApplication.setCookie(cookieBuffer.toString())
                }

            }
            return originalResponse!!
        }
    }

    //添加cookie
    private inner class CookieAddInterceptor:Interceptor{
        override fun intercept(chain: Interceptor.Chain?): Response {

            val builder = chain?.request()?.newBuilder()
            if(QdApplication.getCookie()!=null)
            {
                builder?.addHeader("Cookie", QdApplication.getCookie())
            }

            return chain?.proceed(builder?.build())!!
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