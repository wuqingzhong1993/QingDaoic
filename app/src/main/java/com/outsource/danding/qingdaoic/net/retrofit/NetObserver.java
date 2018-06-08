package com.outsource.danding.qingdaoic.net.retrofit;


import android.content.Intent;

import com.google.gson.JsonParseException;
import com.outsource.danding.qingdaoic.app.QdApp;
import com.outsource.danding.qingdaoic.net.api.HttpResult;
import com.outsource.danding.qingdaoic.ui.activity.LoginActivity;

import com.outsource.danding.qingdaoic.util.NetworkUtils;
import com.outsource.danding.qingdaoic.util.UIUtils;


import java.net.SocketException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class NetObserver<T> implements Observer<HttpResult<T>> {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(HttpResult<T> value) {
        if (value.isSuccess()) {
            T t = value.getData();
            onHandleSuccess(t);
            onHandleSuccess(t, value.getMsg());
        } else {
            onHandleError(value.getMsg());
            onHandleError(value.getCode(), value.getMsg());
        }
    }

    @Override
    public void onError(Throwable e) {
        if (!NetworkUtils.isNetworkAvailable(QdApp.appContext)) {
            onHandleError("网络连接断开");
            return;
        }
        if (e instanceof SocketException) {
            onHandleError("网络异常");
        } else if (e instanceof TimeoutException) {
            onHandleError("请求超时");
        } else if (e instanceof JsonParseException) {
            onHandleError("数据解析失败");
        } else {
            onHandleError("网络异常");
        }
    }

    @Override
    public void onComplete() {

    }

    protected abstract void onHandleSuccess(T t);

    protected void onHandleError(String msg) {
        UIUtils.showToast(msg);
    }

    protected void onHandleSuccess(T t, String msg) {

    }

    protected void onHandleError(int code, String msg) {
        if (code == 100006) {

            Intent intent = new Intent(QdApp.appContext, LoginActivity.class);
            QdApp.appContext.startActivity(intent);
        }
    }
}
