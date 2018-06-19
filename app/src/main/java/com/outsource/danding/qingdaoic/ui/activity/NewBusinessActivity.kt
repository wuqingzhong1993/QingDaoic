package com.outsource.danding.qingdaoic.ui.activity


import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import com.outsource.danding.qingdaoic.R
import com.outsource.danding.qingdaoic.app.QdApplication
import com.outsource.danding.qingdaoic.base.BaseActivity
import com.outsource.danding.qingdaoic.bean.ZhiChu
import kotlinx.android.synthetic.main.activity_new_bao_xiao.*


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



    }
}
