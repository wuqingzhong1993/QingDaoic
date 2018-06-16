package com.outsource.danding.qingdaoic.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import com.outsource.danding.qingdaoic.R
import com.outsource.danding.qingdaoic.app.QdApplication
import com.outsource.danding.qingdaoic.base.BaseActivity
import com.outsource.danding.qingdaoic.bean.Department
import com.outsource.danding.qingdaoic.ui.fragment.DatePickerFragment
import kotlinx.android.synthetic.main.activity_new_jd.*

class NewJdActivity : BaseActivity() ,DatePickerFragment.OnDateSetListener{


    private  lateinit var mTarget: View

    private var passIsShow = false

    var trainTime:String?=null
    var visitorNum:String?=null
    var accompanyNum:String?=null
    var letterNo:String?=null
    var guestUnit:String?=null
    var nameAndPost:String?=null
    var activityContent:String?=null
    var jc_address:String?=null
    var zs_address:String?=null
    var zs_days:String?=null
    var jdzs_money:String?=null
    var jdhs_money:String?=null
    var jdqt_money:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_jd)
        initView()
    }

    private fun initView(){
        title="公务接待费"

        //初始化单位的adapter
        val departments:MutableList<Department> = QdApplication.getDepartments()
        val departmentNames= mutableListOf<String>()
        for(department in departments)
        {
            departmentNames.add(department.deptName)
        }
        val adapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_item, departmentNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_dept.adapter=adapter


        initListener()
    }

    private fun initListener() {

        ll_trainTime.setOnClickListener { v->
            pickDate(tv_trainTime)
        }
        et_visitorNum.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_visitorNum.text.toString()!="")
                {
                    visitorNum=et_visitorNum.text.toString()
                }
            }
        })

        et_accompanyNum.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_accompanyNum.text.toString()!="")
                {
                    accompanyNum=et_accompanyNum.text.toString()
                }
            }
        })
        et_letterNo.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_letterNo.text.toString()!="")
                {
                    letterNo=et_letterNo.text.toString()
                }
            }
        })
        et_nameAndPost.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_nameAndPost.text.toString()!="")
                {
                    nameAndPost=et_nameAndPost.text.toString()
                }
            }
        })
        et_guestUnit.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_guestUnit.text.toString()!="")
                {
                    guestUnit=et_guestUnit.text.toString()
                }
            }
        })
        et_activityContent.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_activityContent.text.toString()!="")
                {
                    activityContent=et_activityContent.text.toString()
                }
            }
        })
        et_jc_address.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_jc_address.text.toString()!="")
                {
                    jc_address=et_jc_address.text.toString()
                }
            }
        })
        et_zs_address.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_zs_address.text.toString()!="")
                {
                    zs_address=et_zs_address.text.toString()
                }
            }
        })
        et_zs_days.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_zs_days.text.toString()!="")
                {
                    zs_days=et_zs_days.text.toString()
                }
            }
        })
        et_jdzs_money.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_jdzs_money.text.toString()!="")
                {
                    jdzs_money=et_jdzs_money.text.toString()
                }
            }
        })
        et_jdhs_money.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_jdhs_money.text.toString()!="")
                {
                    jdhs_money=et_jdhs_money.text.toString()
                }
            }
        })
        et_jdqt_money.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_jdqt_money.text.toString()!="")
                {
                    jdqt_money=et_jdqt_money.text.toString()
                }
            }
        })

    }

    fun pickDate(v: View) {
        mTarget=v//设置需要绑定日期回调的控件
        val datePicker = DatePickerFragment()
        datePicker.show(supportFragmentManager, "datePicker")
    }

    override fun onDateSet(year: Int, month: Int, day: Int) {
        var month = month
        var dateStr:String?=null
        if(mTarget!=null)
        {
            when(mTarget)
            {
                is TextView ->
                {
                    var monthStr:String?=null
                    if(month+1>=10)
                        monthStr=""+(month+1)
                    else
                        monthStr="0"+(month+1)
                    var dayStr:String?=null
                    if(day>=10)
                        dayStr=""+day
                    else
                        dayStr="0"+day
                    dateStr=year.toString() + "-" + monthStr + "-" + dayStr
                    (mTarget as TextView).text=dateStr

                }
                else -> {
                }
            }

            when(mTarget.id)
            {
                R.id.tv_trainTime->
                    trainTime=dateStr

            }

        }
    }

}
