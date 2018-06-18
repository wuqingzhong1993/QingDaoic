package com.outsource.danding.qingdaoic.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import com.outsource.danding.qingdaoic.R
import com.outsource.danding.qingdaoic.app.QdApplication
import com.outsource.danding.qingdaoic.base.BaseActivity
import com.outsource.danding.qingdaoic.bean.Department
import com.outsource.danding.qingdaoic.ui.fragment.DatePickerFragment
import kotlinx.android.synthetic.main.activity_new_conference.*

class NewConferenceActivity : BaseActivity(), DatePickerFragment.OnDateSetListener {


    private  lateinit var mTarget: View

    private var passIsShow = false

    var meetingTime:String?=null


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
