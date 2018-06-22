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
import com.outsource.danding.qingdaoic.R.id.ll_trainTime
import com.outsource.danding.qingdaoic.app.QdApplication
import com.outsource.danding.qingdaoic.base.BaseActivity
import com.outsource.danding.qingdaoic.bean.Department
import com.outsource.danding.qingdaoic.bean.ZhiChu
import com.outsource.danding.qingdaoic.net.HttpClient
import com.outsource.danding.qingdaoic.ui.fragment.DatePickerFragment
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_new_cultivate.*

class NewCultivateActivity : BaseActivity() , DatePickerFragment.OnDateSetListener {

    private  lateinit var mTarget: View

    private var passIsShow = false

    var expendType:String="14"
    var applyDeptName:String?=null
    var isLoan:String="1"
    var loanReason:String?=null
    var budgetAmount:String?=null
    var remark:String?=null
    var trainName:String?=null
    var trainPlace:String?=null
    var trainNum:String?=null
    var trainStaffNum:String?=null
    var trainBudget:String?=null

    var trainObject:String?=null

    var departments:MutableList<Department>?=null
    var departmentNames:MutableList<String>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_cultivate)
        initView()
    }

    private fun initView(){
        title="培训费申请"

        //初始化单位的adapter
        departments = QdApplication.getDepartments()

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
           // commit()
            saveCultivateApply("0")
        }
        btn_temp_save.setOnClickListener{
            saveCultivateApply("1")
        }
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
        et_remark.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_remark.text.toString()!="")
                {
                    remark=et_remark.text.toString()
                }
            }

        })
        et_train_name.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_train_name.text.toString()!=""){
                    trainName=et_train_name.text.toString()
                }
            }
        })
        ll_trainTime.setOnClickListener { v->
            pickDate(tv_trainTime)
        }
        ll_trainEnd.setOnClickListener { v->
            pickDate(tv_trainEnd)
        }
        ll_trainReport.setOnClickListener { v->
            pickDate(tv_trainReport)
        }
        ll_trainLeave.setOnClickListener { v->
            pickDate(tv_trainLeave)
        }
        et_trainPlace.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_trainPlace.text.toString()!="")
                {
                    trainPlace=et_trainPlace.text.toString()
                }
            }
        })
        et_trainBudget.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_trainBudget.text.toString()!="")
                {
                    trainBudget=et_trainBudget.text.toString()
                    budgetAmount=trainBudget
                }
            }
        })
        et_trainNum.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_trainNum.text.toString()!="")
                {
                    trainNum=et_trainNum.text.toString()
                }
            }
        })
        et_trainStaffNum.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_trainStaffNum.text.toString()!=""){
                    trainStaffNum=et_trainStaffNum.text.toString()
                }
            }
        })
        et_train_object.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_train_object.text.toString()!=""){
                    trainObject=et_train_object.text.toString()
                }
            }
        })

    }

    private fun saveCultivateApply(flag: String) {
        HttpClient.instance.saveCultivateApply(flag!!,expendType!!,applyDeptName!!, isLoan!!,
                loanReason,budgetAmount!!,remark,
                trainName!!,tv_trainTime.text.toString()!!,tv_trainEnd.text.toString()!!, tv_trainReport.text.toString()!!,
                tv_trainLeave.text.toString()!!,trainPlace!!,trainNum!!, trainStaffNum!!,
                trainBudget!!,trainObject!!)
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

    //提交
    private fun commit(){

        val map =HashMap<String,String>()
        map.put("trainName",et_train_name.text.toString())
        map.put("trainTime",tv_trainTime.text.toString())
        map.put("trainEnd",tv_trainEnd.text.toString())
        map.put("trainReport",tv_trainReport.text.toString())
        map.put("trainLeave",tv_trainLeave.text.toString())
        map.put("trainPlace",et_trainPlace.text.toString())
        map.put("trainNum",et_trainNum.text.toString())
        map.put("trainStaffNum",et_trainStaffNum.text.toString())
        map.put("trainBudget",et_trainBudget.text.toString())

        HttpClient.instance.commit(map)
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


        }
    }


}
