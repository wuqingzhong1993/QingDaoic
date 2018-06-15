package com.outsource.danding.qingdaoic.ui.fragment

import android.content.Intent
import android.os.Bundle
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.outsource.danding.qingdaoic.R
import com.outsource.danding.qingdaoic.app.QdApp
import com.outsource.danding.qingdaoic.app.QdApplication
import com.outsource.danding.qingdaoic.base.BaseFragment
import com.outsource.danding.qingdaoic.bean.*
import com.outsource.danding.qingdaoic.net.HttpClient
import com.outsource.danding.qingdaoic.ui.activity.AuditByOperatorActivity
import com.outsource.danding.qingdaoic.ui.activity.AuditWaitingActivity
import com.outsource.danding.qingdaoic.ui.activity.BusinessActivity
import com.outsource.danding.qingdaoic.ui.activity.TravelActivity
import com.outsource.danding.qingdaoic.util.GlideImageLoader
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment:BaseFragment() {

    private var images= mutableListOf(R.drawable.banner,R.drawable.banner,R.drawable.banner)

    override fun setLayoutId(): Int = R.layout.fragment_home


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initListener()
        initApplyAdd()
    }

    private fun initView(){
        banner.setImages(images).setImageLoader(GlideImageLoader()).start()
    }

    private fun initListener(){
        audit_wait.setOnClickListener {
            val intent:Intent=Intent(this.activity,AuditWaitingActivity::class.java)
            activity?.startActivityForResult(intent,0)
        }

        audit_by_operator.setOnClickListener{
            val intent:Intent= Intent(this.activity,AuditByOperatorActivity::class.java)
            activity?.startActivityForResult(intent,0)
        }

        ll_business.setOnClickListener {
            val intent:Intent=Intent(this.activity,BusinessActivity::class.java)
            activity?.startActivityForResult(intent,0)
        }

        ll_travel.setOnClickListener {
            val intent:Intent=Intent(this.activity,TravelActivity::class.java)
            activity?.startActivityForResult(intent,0)
        }

    }

    private fun initApplyAdd()
    {
        HttpClient.instance.initApplyAdd()
                .bindToLifecycle(this)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe({
                    json: JsonObject ->

                    cancelProgressDialog()
                    val data= json.getAsJsonObject("data")
                    if(data!=null)
                    {
                        if(data.get("depList")!=null&&data.getAsJsonArray("depList")!=null)
                        {
                            val list=data.getAsJsonArray("depList")
                            if(list!=null&&list.size()>0)
                            {
                                QdApplication.getDepartments().clear()
                                val  gson=Gson()
                                for(ob in list)
                                {
                                    val dept:Department = gson.fromJson(ob,Department::class.java)
                                    QdApplication.getDepartments().add(dept)
                                }
                            }
                        }

                        if(data.get("provinceList")!=null&&data.getAsJsonArray("provinceList")!=null)
                        {
                            val list=data.getAsJsonArray("provinceList")
                            if(list!=null&&list.size()>0)
                            {
                                QdApplication.getProvinces().clear()
                                val  gson=Gson()
                                for(ob in list)
                                {
                                    val province:Province = gson.fromJson(ob,Province::class.java)
                                    QdApplication.getProvinces().add(province)
                                }
                            }
                        }

                        if(data.get("cityList")!=null&&data.getAsJsonArray("cityList")!=null)
                        {
                            val list=data.getAsJsonArray("cityList")
                            if(list!=null&&list.size()>0)
                            {
                                QdApplication.getCities().clear()
                                val  gson=Gson()
                                for(ob in list)
                                {
                                    val city:City = gson.fromJson(ob,City::class.java)
                                    QdApplication.getCities().add(city)
                                }
                            }
                        }



                        if(data.get("personList")!=null&&data.getAsJsonArray("personList")!=null)
                        {
                            val list=data.getAsJsonArray("personList")
                            if(list!=null&&list.size()>0)
                            {
                                QdApplication.getPersons().clear()
                                val  gson=Gson()
                                for(ob in list)
                                {
                                    val person:Person = gson.fromJson(ob,Person::class.java)
                                    QdApplication.getPersons().add(person)
                                }
                            }
                        }



                    }


                }, {
                    e: Throwable ->
                    cancelProgressDialog()
                })
    }





}