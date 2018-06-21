package com.outsource.danding.qingdaoic.net.api

import com.google.gson.JsonObject
import io.reactivex.Observable
import retrofit2.http.*
import com.outsource.danding.qingdaoic.bean.*
import okhttp3.RequestBody


interface  ApiService{
    @FormUrlEncoded
    @POST("api/login_email")
    fun loginByEmail(@FieldMap map: Map<String, String>): Observable<HttpResult<JsonObject>>

//    @GET("mb_login.do")
//    fun login(@QueryMap map: Map<String, String> ): Observable<JsonObject>

    @FormUrlEncoded
    @POST("mb_login.do")
    fun login(@FieldMap map: Map<String, String> ): Observable<JsonObject>


    @FormUrlEncoded
    @Headers("Accept-Encoding:application/json")
    @POST("m_getTodoList.do")
    fun getAuditWaiting(@Field("personId") personId:String): Observable<JsonObject>

    @FormUrlEncoded
    @Headers("Accept-Encoding:application/json")
    @POST("m_getApplyInfoDetail.do")
    fun getApplyInfoDetail(@Field("personId") personId:String,@Field("expendId") expendId:String): Observable<JsonObject>



    @FormUrlEncoded
    @Headers("Accept-Encoding:application/json")
    @POST("m_getRemibConfirmInfoList.do")
    fun getAuditByOperator(@Field("personId") personId:String,@Field("pageNum") pageNum:String): Observable<JsonObject>

    @FormUrlEncoded
    @Headers("Accept-Encoding:application/json")
    @POST("m_getUserInfoDetail.do")
    fun getUserInfo(@Field("personId") personId:String): Observable<JsonObject>

    @FormUrlEncoded
    @Headers("Accept-Encoding:application/json")
    @POST("m_getApplyInfoList.do")
    fun getApplyInfoList(@Field("personId") personId:String,@Field("pageNum") pageNum: String): Observable<JsonObject>

    @FormUrlEncoded
    @Headers("Accept-Encoding:application/json")
    @POST("m_getHistoryList.do")
    fun getAuditHistoryList(@Field("personId") personId:String,@Field("pageNum") pageNum: String): Observable<JsonObject>




    @FormUrlEncoded
    @Headers("Accept-Encoding:application/json")
    @POST("m_getApplyInfoList.do")
    fun getAuditApplyList(@Field("personId") personId:String,@Field("pageNum") pageNum: String): Observable<JsonObject>



    @FormUrlEncoded
    @Headers("Accept-Encoding:application/json")
    @POST("m_out_apply_add_init.do")
    fun initApplyAdd(@Field("personId") personId:String): Observable<JsonObject>

    @FormUrlEncoded
    @Headers("Accept-Encoding:application/json")
    @POST("m_out_apply_add_getmoney.do")
    fun getMoney(@FieldMap map: Map<String, String> ):Observable<JsonObject>

    @FormUrlEncoded
    @Headers("Accept-Encoding:application/json")
    @POST("do_saveByOffice.do")
    fun commit(@FieldMap map: Map<String, String> ):Observable<JsonObject>

    @FormUrlEncoded
    @Headers("Accept-Encoding:application/json")
    @POST("do_saveByOffice.do")
    fun saveBusinessApply(@FieldMap map: HashMap<String, String>): Observable<JsonObject>



    @FormUrlEncoded
    @Headers("Accept-Encoding:application/json")
    @POST("m_getshenQingInfoList.do")
    fun getshenQingInfoList(@Field("personId") personId:String,@Field("pageNum") pageNum: String): Observable<JsonObject>


    @FormUrlEncoded
    @Headers("Accept-Encoding:application/json")
    @POST("do_saveByOffice.do")
    fun saveConferenceApply(@FieldMap map: HashMap<String, String>): Observable<JsonObject>

    @FormUrlEncoded
    @Headers("Accept-Encoding:application/json")
    @POST("do_saveZjBaoxiao.do")
    fun saveBaoXiaoApply(@FieldMap map: HashMap<String, String>): Observable<JsonObject>

    @FormUrlEncoded
    @Headers("Accept-Encoding:application/json")
    @POST("m_getApplyInfoCheck.do")
    fun checkApplyPassed(@FieldMap map: HashMap<String, String>): Observable<JsonObject>

    @FormUrlEncoded
    @Headers("Accept-Encoding:application/json")
    @POST("m_getApplyInfoCheck_no.do")
    fun checkApplyNotPassed(@FieldMap map: HashMap<String, String>): Observable<JsonObject>

    @FormUrlEncoded
    @Headers("Accept-Encoding:application/json")
    @POST("m_out_apply_add.do")
    fun saveTravelApply(@FieldMap map: HashMap<String, String>): Observable<JsonObject>


}


