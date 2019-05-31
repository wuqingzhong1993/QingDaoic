package com.outsource.danding.qingdaoic.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.outsource.danding.qingdaoic.R
import com.outsource.danding.qingdaoic.app.QdApplication
import com.outsource.danding.qingdaoic.base.BaseActivity
import com.outsource.danding.qingdaoic.bean.UserInfo
import com.outsource.danding.qingdaoic.net.HttpClient
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_password_modify.*

class PasswordModifyActivity : BaseActivity() {

    private  lateinit var mTarget: View
    private var passIsShow = false
    private var oldPass :String=""
    private var newPass:String=""
    private var qurenPass:String=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_modify)
        initView()
    }

    private fun initView() {
        title = "修改密码"
        tv_personName.text = QdApplication.getUserInfo().personName
        initListener()
    }

    private fun initListener() {



        et_oldPass.addTextChangedListener( object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_oldPass.text.toString()!="")
                {
                    oldPass=et_oldPass.text.toString()
                }
            }
        })

        et_newPass.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_newPass.text.toString()!="")
                {
                    newPass=et_newPass.text.toString()
                }
            }
        })

        et_qurenPass.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_qurenPass.text.toString()!="")
                {
                    qurenPass=et_qurenPass.text.toString()
                }
            }
        })



        ll_save.setOnClickListener {
            getPassCheck()
        }
    }

    private fun getPassCheck()
    {

        HttpClient.instance.getPassCheck(oldPass,newPass,qurenPass)
                .bindToLifecycle(this)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe({
                    json: JsonObject ->

                    val data= json.getAsJsonObject("data")
                    if(data!=null)
                    {

                        if(data.get("result").asInt==1)
                        {
                            Toast.makeText(this,"修改成功",Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(this,"修改失败",Toast.LENGTH_SHORT).show()

                        }

                    }

                }, {
                    e: Throwable ->
                    cancelProgressDialog()
                })
    }


}
