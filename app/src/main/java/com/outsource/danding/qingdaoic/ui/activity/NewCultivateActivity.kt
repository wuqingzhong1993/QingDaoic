package com.outsource.danding.qingdaoic.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
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
import kotlinx.android.synthetic.main.activity_new_cultivate.*

class NewCultivateActivity : BaseActivity() , DatePickerFragment.OnDateSetListener {

    private  lateinit var mTarget: View

    private var passIsShow = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_cultivate)
        initView()
    }

    private fun initView(){
        title="培训费申请"

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

        ll_commit.setOnClickListener { v->
            commit()
        }
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
