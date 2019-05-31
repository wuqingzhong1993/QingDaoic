package com.outsource.danding.qingdaoic.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.outsource.danding.qingdaoic.R
import com.outsource.danding.qingdaoic.app.QdApp
import com.outsource.danding.qingdaoic.app.QdApplication
import com.outsource.danding.qingdaoic.base.BaseFragment
import com.outsource.danding.qingdaoic.bean.*
import com.outsource.danding.qingdaoic.net.HttpClient
import com.outsource.danding.qingdaoic.ui.activity.*
import com.outsource.danding.qingdaoic.util.GlideImageLoader
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_home.*
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream


class HomeFragment:BaseFragment() {

    private var images= mutableListOf(R.drawable.banner,R.drawable.banner,R.drawable.banner)

    override fun setLayoutId(): Int = R.layout.fragment_home


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initListener()
        initApplyAdd()
        getUserInfo()
        //downloadPic()
        //调用动态代理
        val thread=Thread(Runnable {
            kotlin.run {
                //QdApplication.getApiProxy().getUserInfoSync()
            }
        })
        thread.start()
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
        ll_conference.setOnClickListener {
            val intent:Intent=Intent(this.activity,ConferenceActivity::class.java)
            activity?.startActivityForResult(intent,0)
        }
        ll_cultivate.setOnClickListener {
            val intent:Intent=Intent(this.activity,CultivateActivity::class.java)
            activity?.startActivityForResult(intent,0)
        }
        ll_jd.setOnClickListener {
            val intent:Intent=Intent(this.activity,JdActivity::class.java)
            activity?.startActivityForResult(intent,0)
        }
        ll_go_abroad.setOnClickListener {
            val intent:Intent=Intent(this.activity,GoAbroadActivity::class.java)
            activity?.startActivityForResult(intent,0)
        }
        ll_baoxiao.setOnClickListener {
            val intent:Intent=Intent(this.activity,BaoXiaoActivity::class.java)
            activity?.startActivityForResult(intent,0)
        }

    }

    private fun downloadPic()
    {
        HttpClient.instance.downloadPic("http://www.lnmoto.cn/bbs/data/attachment/forum/201310/03/075632kpll31ppggpog1ip.jpg")
                .bindToLifecycle(this)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({
                    body: ResponseBody ->

                    try{
                        val  file:File = File(Environment.getExternalStorageDirectory(),"qindaoic.jpg")
                        var inputStream: InputStream? = null
                        var outputStream: OutputStream? = null
                        val fileReader = ByteArray(4096)
                        val fileSize = body.contentLength()
                        val fileSizeDownloaded: Long = 0

                        inputStream = body.byteStream();
                        outputStream =  FileOutputStream(file)
                        while (true) {
                            val read = inputStream.read(fileReader);
                            if (read == -1) {
                                break;
                            }
                            outputStream.write(fileReader, 0, read);
                        }

                        outputStream.flush();


                    }catch (e:Exception)
                    {
                        e.printStackTrace()
                    }
                }, {
                    e: Throwable ->
                    cancelProgressDialog()
                })
    }

    private fun getUserInfo()
    {
        HttpClient.instance.getUserInfo()
                .bindToLifecycle(this)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe({
                    json: JsonObject ->

                    val data= json.getAsJsonObject("data")
                    if(data!=null)
                    {
                        val  gson=Gson()
                        val userINfo:UserInfo = gson.fromJson(data,UserInfo::class.java)
                        QdApplication.setUserInfo(userINfo)

                    }

                }, {
                    e: Throwable ->
                    cancelProgressDialog()
                })
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

                        if(data.get("zhichuList")!=null&&data.getAsJsonArray("zhichuList")!=null)
                        {
                            val list=data.getAsJsonArray("zhichuList")
                            if(list!=null&&list.size()>0)
                            {
                                QdApplication.getZhiChuList().clear()
                                val  gson=Gson()
                                for(ob in list)
                                {
                                    val zhichu:ZhiChu = gson.fromJson(ob,ZhiChu::class.java)
                                    QdApplication.getZhiChuList().add(zhichu)
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