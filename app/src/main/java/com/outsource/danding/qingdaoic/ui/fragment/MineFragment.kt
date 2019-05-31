package com.outsource.danding.qingdaoic.ui.fragment

import android.content.Intent
import android.os.Bundle
import com.google.gson.JsonObject
import com.outsource.danding.qingdaoic.R
import com.outsource.danding.qingdaoic.base.BaseFragment
import com.outsource.danding.qingdaoic.net.HttpClient
import com.outsource.danding.qingdaoic.ui.activity.*
import com.outsource.danding.qingdaoic.util.GlideImageLoader
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_mine.*

class MineFragment : BaseFragment() {

    private var images= mutableListOf(R.drawable.banner, R.drawable.banner, R.drawable.banner)

    override fun setLayoutId(): Int = R.layout.fragment_mine


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initListener()
    }

    private fun initView(){

        getZCList()
        //banner.setImages(images).setImageLoader(GlideImageLoader()).start()
    }

    private fun initListener(){

        ll_shenQing.setOnClickListener {
            val intent = Intent(activity, MyShenQingActivity::class.java)
            jumpToActivity(intent)
        }

        ll_config.setOnClickListener {
            val intent = Intent(activity, PasswordModifyActivity::class.java)
            jumpToActivity(intent)
        }

        ll_assets.setOnClickListener {
            val intent = Intent(activity, MyAssetActivity::class.java)
            jumpToActivity(intent)
        }

    }

    private fun getZCList() {
        HttpClient.instance.getZCList()
                .bindToLifecycle(this)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    json: JsonObject ->
                    cancelProgressDialog()

                }, {
                    e: Throwable ->
                    cancelProgressDialog()
                })
    }

    private fun handleLoginData(json: JsonObject) {

        val data=json.getAsJsonObject("data")
        val result= data.get("result").asInt
        if(result==2)
        {
            val presonId=data.get("personId").asString
            HttpClient.instance.setPersonId(presonId)
            //todo 跳转至MainActivity
            val intent = Intent(activity, MainActivity::class.java)
            jumpToActivity(intent)
        }


    }



}