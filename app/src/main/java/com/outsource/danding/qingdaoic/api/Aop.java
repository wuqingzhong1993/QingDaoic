package com.outsource.danding.qingdaoic.api;


import android.util.Log;

import com.google.gson.JsonObject;
import com.outsource.danding.qingdaoic.net.HttpClient;
import com.outsource.danding.qingdaoic.net.api.ApiService;
import com.outsource.danding.qingdaoic.net.retrofit.NetObserver;

import java.lang.reflect.Method;

import io.reactivex.Observable;
import io.reactivex.Observer;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;


public class Aop {



    public static  JsonObject authSecurity(String url,final  Method callback) {


        Call<JsonObject> call= HttpClient.Companion.getInstance().getUserInfoSync();
        try{

            Response<JsonObject> response = call.execute();
            JsonObject json=response.body();
            return json;
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;


//        HttpClient.Companion.getInstance().getUserInfo()
//
//                .subscribeOn(Schedulers.io())
//                .subscribe(new Observer<JsonObject>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(JsonObject jsonObject) {
//                        try{
//                            callback.invoke(null,jsonObject);
//                        }catch (Exception e)
//                        {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d("", "'");
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.d("", "'");
//                    }
//                });

    }

}