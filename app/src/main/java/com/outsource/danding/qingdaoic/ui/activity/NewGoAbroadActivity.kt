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
import com.outsource.danding.qingdaoic.bean.Department
import com.outsource.danding.qingdaoic.net.HttpClient
import com.outsource.danding.qingdaoic.ui.fragment.DatePickerFragment
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_new_go_abroad.*
import org.w3c.dom.Text

class NewGoAbroadActivity : BaseActivity(), DatePickerFragment.OnDateSetListener {

    private  lateinit var mTarget: View

    private var passIsShow = false
    var budgetAmount:String?=null
    var groupUnit:String?=null
    var groupName:String?=null
    var colonel:String?=null
    var groupNum:String?=null
    var visitingCountry:String?=null
    var visitingDay:String?=null
    var ht_money:String?=null
    var zs_money:String?=null
    var hs_money:String?=null
    var jt_money:String?=null
    var qt_money:String?=null

    var expendType:String="22"
    var applyDeptName:String?=null
    var isLoan:String="1"
    var loanReason:String?=null
    var departments:MutableList<Department>?=null
    var departmentNames:MutableList<String>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_go_abroad)
        initView()
    }

    private fun initView(){

        title="因公出国(境)"


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
            saveGoAbroadApply("0")
        }
        btn_temp_save.setOnClickListener {
            saveGoAbroadApply("1")
        }
        et_budgetAmount.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_budgetAmount.text.toString()!="")
                {
                    budgetAmount=et_budgetAmount.text.toString()
                }
            }
        })

        et_groupName.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_groupName.text.toString()!="")
                {
                    groupName=et_groupName.text.toString()
                }
            }
        })

        et_groupUnit.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_groupUnit.text.toString()!="")
                {
                    groupUnit=et_groupUnit.text.toString()
                }
            }
        })

        et_colonel.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_colonel.text.toString()!="")
                {
                    colonel=et_colonel.text.toString()
                }
            }
        })

        et_groupNum.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_groupNum.text.toString()!="")
                {
                    groupNum=et_groupNum.text.toString()
                }
            }
        })

        et_visitingCountry.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_visitingCountry.text.toString()!="")
                {
                    visitingCountry=et_visitingCountry.text.toString()
                }
            }
        })

        et_visitingDay.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_visitingDay.text.toString()!="")
                {
                    visitingDay=et_visitingDay.text.toString()
                }
            }
        })

        et_ht_money.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_ht_money.text.toString()!="")
                {
                    ht_money=et_ht_money.text.toString()
                }
            }
        })
        et_hs_money.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_hs_money.text.toString()!="")
                {
                    hs_money=et_hs_money.text.toString()
                }
            }
        })

        et_zs_money.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_zs_money.text.toString()!="")
                {
                    zs_money=et_zs_money.text.toString()
                }
            }
        })

        et_jt_money.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_jt_money.text.toString()!="")
                {
                    jt_money=et_jt_money.text.toString()
                }
            }
        })

        et_qt_money.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_qt_money.text.toString()!="")
                {
                    qt_money=et_qt_money.text.toString()
                }
            }
        })
        sp_dept?.onItemSelectedListener=object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                applyDeptName=departments?.get(position)?.deptName
            }
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
        
    }

    private fun saveGoAbroadApply(flag: String) {
        HttpClient.instance.saveGoAbroadApply(flag!!,expendType!!,applyDeptName!!, isLoan!!,
                loanReason,budgetAmount!!,
                groupName!!,groupUnit!!,colonel!!,groupNum!!,visitingCountry!!,
                visitingDay!!,ht_money!!,zs_money!!,
                hs_money!!,jt_money!!,qt_money!!)
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
