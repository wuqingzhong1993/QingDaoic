package com.outsource.danding.qingdaoic.ui.activity


import android.content.Intent
import android.os.Bundle
import com.outsource.danding.qingdaoic.R
import com.outsource.danding.qingdaoic.base.BaseActivity


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

    }
}
