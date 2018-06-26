package com.outsource.danding.qingdaoic.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.google.gson.JsonObject
import com.outsource.danding.qingdaoic.R
import com.outsource.danding.qingdaoic.app.QdApplication
import com.outsource.danding.qingdaoic.base.BaseActivity
import com.outsource.danding.qingdaoic.bean.BusinessOffice
import com.outsource.danding.qingdaoic.bean.Department
import com.outsource.danding.qingdaoic.bean.ZhiChu
import com.outsource.danding.qingdaoic.net.HttpClient
import com.outsource.danding.qingdaoic.ui.fragment.DatePickerFragment
import com.outsource.danding.qingdaoic.widget.OfficeAdapter
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_new_conference.*

class NewConferenceActivity : BaseActivity(), DatePickerFragment.OnDateSetListener {


   private  lateinit var mTarget: View

   // private var passIsShow = false

//    var meetingTime:String?=null
    private var passIsShow = false

    var budgetAmount:String?=null
    var cashContent:String?=null
    var remark:String?=null
    var applyDeptName:String?=null
    var expendType:String="13"
    var zhichuList:MutableList<ZhiChu>?=null
    var isLoan:String="1"
    var loanReason:String?=null

    var meetingName:String?=null
    var meetingCategory:String?=null
    var meetingPlace:String?=null
    var estimatedNum:String?=null
    var staffNum:String?=null
    var meetingBudget:String?=null
    var meetingReason:String?=null

    var departments:MutableList<Department>?=null
    var departmentNames:MutableList<String>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_conference)
        initView()
    }


    private fun initView(){
        title="会议申请"

        //初始化单位的adapter
        departments= QdApplication.getDepartments()
        departmentNames= mutableListOf<String>()
        for(department in departments!!)
        {
            departmentNames?.add(department.deptName)
        }
        val adapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_item, departmentNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_dept.adapter=adapter

        initListener()
    }

    private fun initListener() {
        btn_commit.setOnClickListener {
            saveConferenceApply("0")
        }
        btn_temp_save.setOnClickListener {
            saveConferenceApply("1")
        }
        sp_dept?.onItemSelectedListener=object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                applyDeptName=departments?.get(position)?.deptName
            }
        }
        tv_meetingTime.setOnClickListener { v->
            pickDate(tv_meetingTime)
        }
        tv_trainEnd.setOnClickListener{ v->
            pickDate(tv_trainEnd)
        }
        tv_trainReport.setOnClickListener { v->
            pickDate(tv_trainReport)
        }
        tv_trainLeave.setOnClickListener { v->
            pickDate(tv_trainLeave)
        }
        rg_isLoan.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId)
            {
                R.id.yes->
                {
                    isLoan="0"
                    ll_loanReason.visibility=View.VISIBLE
                }
                R.id.no->
                {
                    isLoan="1"
                    ll_loanReason.visibility=View.GONE
                }
            }
        }
        et_loanReason.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_loanReason.text.toString()!="")
                {
                    loanReason=et_loanReason.text.toString()
                }
            }
        })
//        et_remark.addTextChangedListener(object :TextWatcher{
//            override fun afterTextChanged(s: Editable?) {
//            }
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                if(et_remark.text.toString()!="")
//                {
//                    remark=et_remark.text.toString()
//                }
//            }
//
//        })
        et_meetingName.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_meetingName.text.toString()!=""){
                    meetingName=et_meetingName.text.toString()
                }
            }
        })
        et_meetingCategory.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_meetingCategory.text.toString()!=""){
                    meetingCategory=et_meetingCategory.text.toString()
                }
            }
        })
        et_meetingPlace.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_meetingPlace.text.toString()!=""){
                    meetingPlace=et_meetingPlace.text.toString()
                }
            }
        })
        et_estimatedNum.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_estimatedNum.text.toString()!=""){
                    estimatedNum=et_estimatedNum.text.toString()
                }
            }
        })

        et_staffNum.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_staffNum.text.toString()!=""){
                    staffNum=et_staffNum.text.toString()
                }
            }
        })
        et_meetingReason.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_meetingReason.text.toString()!=""){
                    meetingReason=et_meetingReason.text.toString()
                }
            }
        })
        et_meetingBudget.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_meetingBudget.text.toString()!=""){
                    meetingBudget=et_meetingBudget.text.toString()
                    budgetAmount=meetingBudget

                }
            }
        })

    }

    private fun saveConferenceApply(flag: String) {1
        HttpClient.instance.saveConferenceApply(flag!!,expendType!!,applyDeptName!!, isLoan!!,
                loanReason,budgetAmount,remark,
                meetingName!!,tv_meetingTime.text.toString()!!,tv_trainEnd.text.toString()!!,
                tv_trainReport.text.toString()!!,tv_trainLeave.text.toString()!!,
                meetingCategory!!,meetingPlace!!,estimatedNum!!,staffNum!!,
                meetingBudget!!,meetingReason)
                .bindToLifecycle(this)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    json: JsonObject ->
                    val data=json.getAsJsonObject("data")
                    if(data!=null&&data.get("result")!=null)
                    {
                        if(flag=="0"){
                            if(data.get("result").toString()=="1")
                            {
                                Toast.makeText(this,"提交成功", Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(this,"提交失败", Toast.LENGTH_SHORT).show()
                            }
                        }else{
                            if(data.get("result").toString()=="1")
                            {
                                Toast.makeText(this, "暂存成功", Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(this, "暂存失败", Toast.LENGTH_SHORT).show()
                            }
                        }

                    }
                    cancelProgressDialog()

                }, {
                    e: Throwable ->
                    cancelProgressDialog()
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
        }
    }


}