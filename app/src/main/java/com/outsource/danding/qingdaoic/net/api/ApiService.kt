package com.outsource.danding.qingdaoic.net.api

import com.google.gson.JsonObject
import io.reactivex.Observable
import retrofit2.http.*
import com.outsource.danding.qingdaoic.bean.*


interface  ApiService{
    @FormUrlEncoded
    @POST("api/login_email")
    fun loginByEmail(@FieldMap map: Map<String, String>): Observable<HttpResult<JsonObject>>

    @GET("mb_login.do")
    fun login(@QueryMap map: Map<String, String> ): Observable<JsonObject>

    @FormUrlEncoded
    @POST("/m_getTodoList.do")
    fun getAuditWaiting(@Field("personId") personId:String): Observable<JsonObject>
}


