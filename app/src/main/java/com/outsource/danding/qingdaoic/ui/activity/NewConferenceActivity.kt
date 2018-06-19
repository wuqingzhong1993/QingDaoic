package com.outsource.danding.qingdaoic.ui.activity

import android.app.DatePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import com.outsource.danding.qingdaoic.R
import com.outsource.danding.qingdaoic.app.QdApplication
import com.outsource.danding.qingdaoic.base.BaseActivity
import com.outsource.danding.qingdaoic.bean.City
import com.outsource.danding.qingdaoic.bean.Department
import com.outsource.danding.qingdaoic.bean.Person
import com.outsource.danding.qingdaoic.bean.Province
import com.outsource.danding.qingdaoic.ui.fragment.DatePickerFragment
import kotlinx.android.synthetic.main.activity_new_travel.*

class NewConferenceActivity : BaseActivity(),DatePickerFragment.OnDateSetListener {

    private  lateinit var mTarget:View

    private var passIsShow = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_travel)
        initView()
    }

    private fun initView() {
        title="会议申请"

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
        val provinceNames= mutableListOf<String>()
        for(province in provinces)
        {
            provinceNames.add(province.townName)
        }
        val provinceAdapter:ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_item, provinceNames)
        provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_province.adapter=provinceAdapter

        //初始化城市的adapter
        val cities:MutableList<City> = QdApplication.getCities()
        val cityNames= mutableListOf<String>()
        for(city in cities)
        {
            cityNames.add(city.city)
        }
        val cityAdapter:ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_item, cityNames)
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_city.adapter=cityAdapter

        //初始化出差人的adapter
        val persons:MutableList<Person> = QdApplication.getPersons()
        val personNames= mutableListOf<String>()
        for(person in persons)
        {
            personNames.add(person.username)
        }
        val personAdapter:ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_item, personNames)
        personAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_person.adapter=personAdapter


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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
