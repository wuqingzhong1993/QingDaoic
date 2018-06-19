package com.outsource.danding.qingdaoic.ui.activity


import android.content.Intent
import android.os.Bundle
import com.google.gson.JsonObject
import com.outsource.danding.qingdaoic.R
import com.outsource.danding.qingdaoic.base.BaseActivity
import com.outsource.danding.qingdaoic.net.HttpClient
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_new_business.*
import kotlinx.android.synthetic.main.activity_new_travel.*


class NewBusinessActivity : BaseActivity() {

    private var passIsShow = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_business)
        initView()
    }

    private fun initView() {
        title="多种申请"
        initListener()
    }

    private fun initListener() {
        btn_commit.setOnClickListener {
           commitBusinessApply()
        }
    }

    private fun commitBusinessApply() {
        HttpClient.instance.commitBusinessApply(city!!,province!!,selectedPersonIds!!,
                outStartDate!!,outEndDate!!,jt_tools!!,fh_tools!!,type!!,w_number !!,fare!!,"1000",zs_jd!!,"派车")
                .bindToLifecycle(this)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    json: JsonObject ->
                    val data=json.getAsJsonObject("data")

                    cancelProgressDialog()

                }, {
                    e: Throwable ->
                    cancelProgressDialog()
                })
    }
}
