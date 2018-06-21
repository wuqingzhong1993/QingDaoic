package com.outsource.danding.qingdaoic.app;


import android.app.Application;
import android.content.Context;

import com.imnjh.imagepicker.PickerConfig;
import com.imnjh.imagepicker.SImagePicker;
import com.outsource.danding.qingdaoic.bean.City;
import com.outsource.danding.qingdaoic.bean.Department;
import com.outsource.danding.qingdaoic.bean.Person;
import com.outsource.danding.qingdaoic.bean.Province;
import com.outsource.danding.qingdaoic.bean.ZhiChu;


import java.util.ArrayList;
import java.util.List;

public class QdApplication extends Application {

    public static Context appContext;
    private static QdApplication mInstance;
    private List<Department> departmentList;
    private List<Province> provinces;
    private List<City> cities;
    private List<Person> persons;
    private List<ZhiChu> zhiChuList;


    private String cookie;


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



    }

    public static void  setDepartments(List<Department> deps)
    {
        if(mInstance!=null)
            mInstance.departmentList=deps;
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

    public static String getCookie(){
        if(mInstance!=null)
        {
            return mInstance.cookie;
        }
        mInstance=new QdApplication();
        return null;
    }

    public static void setCookie(String cookie){
        if(mInstance==null)
        {
            mInstance=new QdApplication();
        }
        mInstance.cookie=cookie;

    }



    public static QdApplication getInstance(){
        if(mInstance==null)
            mInstance=new QdApplication();
        return mInstance;
    }


}
