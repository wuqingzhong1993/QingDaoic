package com.outsource.danding.qingdaoic.api;

public class HttpResponse<T> {
    private int code;
    private String error;
    private T result;
    // some set and some get
    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
