package com.outsource.danding.qingdaoic.app;


import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.imnjh.imagepicker.PickerConfig;
import com.imnjh.imagepicker.SImagePicker;
import com.outsource.danding.qingdaoic.R;
import com.outsource.danding.qingdaoic.annotation.Security;
import com.outsource.danding.qingdaoic.api.Aop;
import com.outsource.danding.qingdaoic.api.IAPI;
import com.outsource.danding.qingdaoic.bean.City;
import com.outsource.danding.qingdaoic.bean.Department;
import com.outsource.danding.qingdaoic.bean.Person;
import com.outsource.danding.qingdaoic.bean.Province;
import com.outsource.danding.qingdaoic.bean.UserInfo;
import com.outsource.danding.qingdaoic.bean.ZhiChu;
import com.outsource.danding.qingdaoic.net.HttpClient;
import com.outsource.danding.qingdaoic.widget.GlideImageLoader;


import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;

public class QdApplication extends Application {

    public static Context appContext;
    private static QdApplication mInstance;
    private List<Department> departmentList;
    private List<Province> provinces;
    private List<City> cities;
    private List<Person> persons;
    private List<ZhiChu> zhiChuList;
    private UserInfo userInfo;
    private HttpClient apiProxy;
    private Set<String> cookies;


    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        mInstance=new QdApplication();
        mInstance.departmentList=new ArrayList<>();
        mInstance.provinces=new ArrayList<>();
        mInstance.persons=new ArrayList<>();
        mInstance.cities=new ArrayList<>();
        mInstance.zhiChuList=new ArrayList<>();
        mInstance.cookies=new HashSet<>();
//        try{
//
//
//
//
//            mInstance.apiProxy = (HttpClient) Proxy.newProxyInstance(IAPI.class.getClassLoader(), new Class<?>[]{HttpClient.class}, new InvocationHandler() {
//
//                @Override
//                public Object invoke(Object proxy, Method method, Object[] args)
//                        throws Throwable {
//
//                    Integer pageCount = (Integer) args[0];
//                    Integer pageIndex = (Integer) args[1];
//                    //System.out.println("参数: " + pageCount + "," + pageIndex);
//                    //System.out.println("方法名: " + method.getName());
//
//                    Security annotation = method.getAnnotation(Security.class);
//                    String url= annotation.value().toString();
//                    JsonObject res= Aop.authSecurity(url, QdApplication.class.getMethod("onResult",JsonObject.class));
//                    Log.d("application",res.toString());
//                    method.invoke(this,args);
//
//                    return null;
//                }
//            });
//        }catch (Exception e)
//        {
//            e.printStackTrace();
//        }



        SImagePicker.init(new PickerConfig.Builder().setAppContext(this)
                .setImageLoader(new GlideImageLoader())
                .setToolbaseColor(getResources().getColor(R.color.colorPrimary))
                .build());

    }

    public static void onResult(JsonObject json)
    {
        Log.d("","");
    }

    public static void  setDepartments(List<Department> deps)
    {
        if(mInstance!=null)
            mInstance.departmentList=deps;
    }

    public static HttpClient getApiProxy()
    {
        if(mInstance!=null)
        {
            return mInstance.apiProxy;
        }
        mInstance=new QdApplication();
        return mInstance.apiProxy;
    }

    public static List<Province> getProvinces(){
        if(mInstance!=null)
        {
            return mInstance.provinces;
        }
        mInstance=new QdApplication();
        mInstance.provinces=new ArrayList<>();
        return null;
    }

    public static List<City> getCities(){
        if(mInstance!=null)
        {
            return mInstance.cities;
        }
        mInstance=new QdApplication();
        mInstance.cities=new ArrayList<>();
        return null;
    }

    public static List<Person> getPersons(){
        if(mInstance!=null)
        {
            return mInstance.persons;
        }
        mInstance=new QdApplication();
        mInstance.persons=new ArrayList<>();
        return null;
    }



    public static List<Department> getDepartments(){
        if(mInstance!=null)
        {
            return mInstance.departmentList;
        }
        mInstance=new QdApplication();
        mInstance.departmentList=new ArrayList<>();
        return null;
    }

    public static List<ZhiChu> getZhiChuList(){
        if(mInstance!=null)
        {
            return mInstance.zhiChuList;
        }
        mInstance=new QdApplication();
        mInstance.zhiChuList=new ArrayList<>();
        return null;
    }

    public static Set<String> getCookie(){
        if(mInstance!=null)
        {
            return mInstance.cookies;
        }
        mInstance=new QdApplication();
        return null;
    }

    public static void setCookie(Set<String> cookie){
        if(mInstance==null)
        {
            mInstance=new QdApplication();
        }
        mInstance.cookies=cookie;

    }

    public static void setUserInfo(UserInfo userInfo)
    {
        if(mInstance==null)
        {
            mInstance=new QdApplication();
        }
        mInstance.userInfo=userInfo;
    }

    public static UserInfo getUserInfo(){
        if(mInstance==null)
        {
            mInstance=new QdApplication();
            return null;
        }
        return mInstance.userInfo;
    }


    public static QdApplication getInstance(){
        if(mInstance==null)
            mInstance=new QdApplication();
        return mInstance;
    }


}
