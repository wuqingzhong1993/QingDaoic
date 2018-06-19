package com.outsource.danding.qingdaoic.ui.activity


import android.content.Intent
import android.os.Bundle
import com.google.gson.JsonObject
import android.widget.ArrayAdapter
import com.outsource.danding.qingdaoic.R
import com.outsource.danding.qingdaoic.app.QdApplication
import com.outsource.danding.qingdaoic.base.BaseActivity
import com.outsource.danding.qingdaoic.net.HttpClient
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_new_business.*
import com.outsource.danding.qingdaoic.bean.ZhiChu



class NewBusinessActivity : BaseActivity() {

    private var passIsShow = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_business)
        initView()
    }

    private fun initView() {
        title="多种申请"


        //初始化支出事项的adapter
        val zhichuList:MutableList<ZhiChu> = QdApplication.getZhiChuList()
        val zhichuNames= mutableListOf<String>()
        for(zhichu in zhichuList)
        {
            zhichuNames.add(zhichu.key)
        }
        val zhichuAdapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_item, zhichuNames)
        zhichuAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_zhichu.adapter=zhichuAdapter

        initListener()
    }

    private fun initListener() {
        btn_commit.setOnClickListener {
            saveBusinessApply("0")
        }
        btn_temp_save.setOnClickListener {
            saveBusinessApply("1")
        }
    }

    private fun saveBusinessApply(flag:String) {
        HttpClient.instance.saveBusinessApply(city!!,province!!,selectedPersonIds!!,
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
