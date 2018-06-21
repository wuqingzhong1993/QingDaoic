package com.outsource.danding.qingdaoic.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.outsource.danding.qingdaoic.R
import com.outsource.danding.qingdaoic.base.BaseActivity

class PasswordModifyActivity : BaseActivity() {

    private  lateinit var mTarget: View

    private var passIsShow = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_modify)
        initView()
    }

    private fun initView() {
        title = "修改密码"

        initListener()
    }

    private fun initListener() {

    }


}
