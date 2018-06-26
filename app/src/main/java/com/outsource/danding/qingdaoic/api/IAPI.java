package com.outsource.danding.qingdaoic.api;

import com.google.gson.JsonObject;
import com.outsource.danding.qingdaoic.annotation.Security;

import retrofit2.Call;


public interface IAPI {
    @Security("mb_login.do")
    Call<JsonObject> getUserInfoSync();
}
