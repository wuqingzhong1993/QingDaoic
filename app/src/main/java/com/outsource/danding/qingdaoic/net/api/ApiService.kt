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

    @GET("mb_login.do")
    fun login(@QueryMap map: Map<String, String> ): Observable<JsonObject>


    @FormUrlEncoded
    @Headers("Accept-Encoding:application/json")
    @POST("m_getTodoList.do")
    fun getAuditWaiting(@Field("personId") personId:String): Observable<JsonObject>


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
    @POST("m_getdozcList.do")
    fun getZCList(@Field("personId") personId:String,@Field("pageNum") pageNum: String): Observable<JsonObject>



}


