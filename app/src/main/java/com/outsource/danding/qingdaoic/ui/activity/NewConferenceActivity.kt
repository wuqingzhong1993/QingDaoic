package com.outsource.danding.qingdaoic.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
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

    var budgetAmount:String ?=null
    var cashContent:String?=null
    var remark:String?=null
    var departments:MutableList<Department>?=null
    var departName:String?=null
    var expendType:String="13"
    var zhichuList:MutableList<ZhiChu>?=null
    var isLoan:String?=null
    var loanReason:String?=null

    var meetingName:String?=null
    var meetingTime:String?=null
    var trainEnd:String?=null
    var trainReport:String?=null
    var trainLeave:String?=null
    var meetingCategory:String?=null
    var meetingPlace:String?=null
    var estimatedNum:String?=null
    var staffNum:String?=null
    var meetingBudget:String?=null
    var meetingReason:String?=null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_conference)
        initView()
    }


    private fun initView(){
        title="会议申请"

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
                departName=departments?.get(position)?.deptName
            }
        }
    }

    private fun saveConferenceApply(flag: String) {
        HttpClient.instance.saveConferenceApply(flag!!,expendType!!,departName!!, isLoan!!,
                loanReason!!,budgetAmount!!,remark!!, cashContent!!,
                meetingName!!,meetingTime!!,trainEnd!!,trainReport!!,trainLeave!!,
                meetingCategory!!,meetingPlace!!,estimatedNum!!,staffNum!!,
                meetingBudget!!,meetingReason!!)
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
                R.id.tv_meetingTime->
                    meetingTime=dateStr

            }

        }
    }


}