package com.outsource.danding.qingdaoic.ui.activity

import android.app.DatePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.outsource.danding.qingdaoic.R
import com.outsource.danding.qingdaoic.R.id.et_w_number
import com.outsource.danding.qingdaoic.app.QdApplication
import com.outsource.danding.qingdaoic.base.BaseActivity
import com.outsource.danding.qingdaoic.bean.*
import com.outsource.danding.qingdaoic.net.HttpClient
import com.outsource.danding.qingdaoic.ui.fragment.DatePickerFragment
import com.outsource.danding.qingdaoic.widget.OfficeAdapter
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_new_travel.*

class NewTravelActivity : BaseActivity(),DatePickerFragment.OnDateSetListener {

    private  lateinit var mTarget:View

    var internalId:String?=null
    var internalName:String?=null
    var applyDeptId:String?=null
    var applyDeptName:String?=null
    var isLoan:String?=null
    var loanReason:String?=null
    lateinit var officeAdapter: OfficeAdapter
    var officeList:String?=null
   // var officeList:MutableList<BusinessOffice>?=null


    var fare:String ?=null
    var hotel:String?=null
    var w_number:String?=null
    var city:String?=null
    var cityNames:MutableList<String>?=null
    var province:String?=null
    var provinceNames:MutableList<String>?=null
    var personIds:MutableList<String>?=null
    var selectedPersonIds:MutableList<String>?=null
    var jt_tools:String?="汽车"
    var fh_tools:String?="汽车"
    var type:String?="01"
    var zs_jd:String?=null
    var outStartDate:String?=null
    var outEndDate:String?=null

    private var passIsShow = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_travel)
        initView()
    }

    private fun initView() {
        title="出差申请"

        //初始化单位的adapter
        val departments:MutableList<Department> = QdApplication.getDepartments()
        val departmentNames= mutableListOf<String>()
        for(department in departments)
        {
            departmentNames.add(department.deptName)
        }
        val adapter:ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_item, departmentNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_dept.adapter=adapter



        //初始化省的adapter
        val provinces:MutableList<Province> = QdApplication.getProvinces()
        provinceNames= mutableListOf<String>()
        for(province in provinces)
        {
            provinceNames?.add(province.townName)
        }
        val provinceAdapter:ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_item, provinceNames)
        provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_province.adapter=provinceAdapter

        //初始化城市的adapter
        val cities:MutableList<City> = QdApplication.getCities()
        cityNames= mutableListOf<String>()
        for(city in cities)
        {
            cityNames?.add(city.city)
        }
        val cityAdapter:ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_item, cityNames)
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_city.adapter=cityAdapter


        //初始化出差人的adapter
        val persons:MutableList<Person> = QdApplication.getPersons()
        val personNames= mutableListOf<String>()
        personIds= mutableListOf()
        for(person in persons)
        {
            personNames.add(person.username)
            personIds?.add(person.personId)
        }
        val personAdapter:ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_item, personNames)
        personAdapter.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice)
        sp_person.adapter=personAdapter


        initListener()
    }

    private fun initListener() {
        btn_commit.setOnClickListener {
            saveTravelApply("0")
        }
        btn_temp_save.setOnClickListener {
            saveTravelApply("1")
        }

        //车船费
        et_fare.addTextChangedListener( object:TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_fare.text.toString()!="")
                {
                    fare=et_fare.text.toString()
                }
            }
        })
        //旅馆费
        et_hotel.addTextChangedListener( object:TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_hotel.text.toString()!="")
                {
                    hotel=et_hotel.text.toString()
                    getMoney()
                }
            }
        })
        //外出单位人数
        et_w_number.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_w_number.text.toString()!="")
                {
                    w_number=et_w_number.text.toString()
                }
            }
        })

        sp_person?.onItemSelectedListener=object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedPersonIds= mutableListOf(personIds!![position])
            }
        }

        sp_city?.onItemSelectedListener= object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                city=QdApplication.getCities()?.get(position)?.ccId
            }
        }
        sp_province.onItemSelectedListener=object:AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                province=QdApplication.getProvinces()?.get(position)?.townId
            }
        }
        ll_begin_date.setOnClickListener {
            pickDate(tv_begin_date)
        }
        ll_end_date.setOnClickListener {
            pickDate(tv_end_date)
        }

        rg_zs_jd.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId)
            {
                R.id.yes_2->
                    zs_jd="1"
                R.id.no_2->
                    zs_jd="0"
            }
        }

        rg_jt_tools.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId)
            {
                R.id.car_1 ->
                        jt_tools="汽车"
                R.id.train_1->
                        jt_tools="火车"
                R.id.plane_1->
                        jt_tools="飞机"
                else->{
                }
            }
        }

        rg_fh_tools.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId)
            {
                R.id.car_2->
                    fh_tools="汽车"
                R.id.train_2->
                    fh_tools="火车"
                R.id.plane_2->
                    fh_tools="飞机"
                else->{
                }

            }
        }

        rg_out_type.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId)
            {
                R.id.spontaneous->
                        type="01"
                R.id.cultivate->
                        type="02"
                R.id.conference->
                        type="03"
                R.id.countrySide->
                        type="04"
            }
        }

    }
    private fun saveTravelApply(flag:String) {
        val officeJson= Gson().toJson(officeList!!)

        HttpClient.instance.saveTravelApply(flag!!,internalId!!,internalName!!, applyDeptId!!,
                applyDeptName!!,isLoan!!,loanReason,
                officeJson!!)
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

    private fun getMoney()
    {
        HttpClient.instance.getMoney(city!!,province!!,selectedPersonIds!!,
                outStartDate!!,outEndDate!!,jt_tools!!,fh_tools!!,type!!,w_number !!,fare!!,"1000",zs_jd!!,"派车")
                .bindToLifecycle(this)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    json: JsonObject ->
                    val data=json.getAsJsonObject("data")
                    levels.text=data?.get("levels").toString()
                    jt_money.text=data?.get("jt_money").toString()
                    zs_money.text=data?.get("zs_money").toString()
                    hs_money.text=data?.get("hs_money").toString()


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
                R.id.tv_begin_date->
                        outStartDate=dateStr
                R.id.tv_end_date->
                        outEndDate=dateStr
            }

        }
    }

}
