package com.outsource.danding.qingdaoic.ui.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.google.gson.JsonObject
import com.outsource.danding.qingdaoic.R;
import com.outsource.danding.qingdaoic.base.BaseActivity
import com.outsource.danding.qingdaoic.net.HttpClient
import com.outsource.danding.qingdaoic.util.UIUtils
import com.outsource.danding.qingdaoic.util.Utils
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    private var passIsShow = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initView()
    }

    private fun initView() {
        initListener()
    }


    private fun initListener() {



//        ll_area.setOnClickListener {
//            val intent = Intent(this, ChooseAreaActivity::class.java)
//            startActivityForResult(intent, ChooseAreaActivity.CHOOSE_COUNTRY_CODE)
//        }

        btn_login.setOnClickListener {
            val username = et_username.text.toString()
            val pass = et_pass.text.toString()


            if (TextUtils.isEmpty(username)) {
                UIUtils.showToast(getString(R.string.toast_no_username))
                return@setOnClickListener
            }


            if (TextUtils.isEmpty(pass)) {
                UIUtils.showToast(getString(R.string.toast_no_pass))
                return@setOnClickListener
            }

            showProgressDialog(getString(R.string.logging))

            //登录
            login(username, pass)

        }
    }


    /**
     * 账户1: 财务处2 1 -> 审核
     * 账户2: 经办人1 1 -> 申请
     * 账户3: 会计 1 -> 审核
     * 账户4: 处长1 1
     *
     *
     * 新加账户1 : 陈琳 1 -> 申请
     * 新加账户2:  马云蔚 1 -> 审核
     * 新加账户3:  admin tpsoft
     * 新加账户4:  刘雪青  1 -> 审核
     */
    private fun login(username: String, password: String) {
        HttpClient.instance.login(username, password)
                .bindToLifecycle(this)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    json:JsonObject ->
                    handleLoginData(json)
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
            val intent = Intent(this,MainActivity::class.java)
            jumpToActivity(intent)
        }

    }



}
