package com.outsource.danding.qingdaoic.ui.activity


import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.outsource.danding.qingdaoic.R
import com.outsource.danding.qingdaoic.app.QdApplication
import com.outsource.danding.qingdaoic.base.BaseActivity
import com.outsource.danding.qingdaoic.bean.BusinessOffice
import com.outsource.danding.qingdaoic.bean.Department
import com.outsource.danding.qingdaoic.bean.Receipt
import com.outsource.danding.qingdaoic.bean.ZhiChu
import com.outsource.danding.qingdaoic.net.HttpClient
import com.outsource.danding.qingdaoic.widget.OfficeAdapter
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_new_business.*
import org.w3c.dom.Text


class NewBusinessActivity : BaseActivity() , OfficeAdapter.OnDeleteListener{

    private var passIsShow = false

    var budgetAmount:String ?=null
    var cashContent:String?=null
    var remark:String?=null
    var departments:MutableList<Department>?=null
    var departName:String?=null
    var expendType:String?=null
    var zhichuList:MutableList<ZhiChu>?=null
    var isLoan:String="1"
    var loanReason:String?=null

    lateinit var officeAdapter: OfficeAdapter
    var officeList:MutableList<BusinessOffice>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_business)
        initView()
    }

    private fun initView() {
        title="多种申请"

        //初始化单位的adapter
        departments = QdApplication.getDepartments()
        val departmentNames= mutableListOf<String>()
        for(department in departments!!)
        {
            departmentNames.add(department.deptName)
        }
        val adapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_item, departmentNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_dept.adapter=adapter


        //初始化支出事项的adapter
        zhichuList = QdApplication.getZhiChuList()
        val zhichuNames= mutableListOf<String>()
        for(zhichu in zhichuList!!)
        {
            zhichuNames.add(zhichu.key)
        }
        val zhichuAdapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_item, zhichuNames)
        zhichuAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_zhichu.adapter=zhichuAdapter


        officeList= mutableListOf(BusinessOffice("","","","","",""))
        officeAdapter= OfficeAdapter(this,officeList!!)
        lt_office.adapter=officeAdapter

        initListener()
    }

    private fun initListener() {

        sp_dept?.onItemSelectedListener=object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                departName=departments?.get(position)?.deptName
            }
        }

        sp_zhichu?.onItemSelectedListener=object :AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                expendType=zhichuList?.get(position)?.value
            }
        }

        et_budgetAmount.addTextChangedListener( object: TextWatcher {
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

        et_cashContent.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(et_cashContent.text.toString()!="")
                {
                    cashContent=et_cashContent.text.toString()
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

        et_loanReason.addTextChangedListener(object:TextWatcher{
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


        img_add.setOnClickListener {
            officeList?.add(BusinessOffice("","0","0","","",""))
            officeAdapter.notifyDataSetChanged()
            var totalHeight=0
            for(i in officeList!!.indices)
            {
                val itemView = officeAdapter.getView(i, null, lt_office)
                itemView.measure(0,0)
                totalHeight+=itemView.measuredHeight
            }
            var params: ViewGroup.LayoutParams  = lt_office.getLayoutParams();
            params.height = totalHeight + (lt_office.getDividerHeight() * (officeAdapter.count -1))
            lt_office.layoutParams=params
        }

        btn_commit.setOnClickListener {
            saveBusinessApply("1")
        }



    }



    override fun onDelete()
    {
        var totalHeight=0
        for(i in officeList!!.indices)
        {
            val itemView = officeAdapter.getView(i, null, lt_office)
            itemView.measure(0,0)
            totalHeight+=itemView.measuredHeight
        }
        var params: ViewGroup.LayoutParams  = lt_office.getLayoutParams();
        params.height = totalHeight + (lt_office.getDividerHeight() * (officeAdapter.count -1))
        lt_office.layoutParams=params
    }

    private fun saveBusinessApply(flag:String) {

        val officeJson= Gson().toJson(officeList!!)


        HttpClient.instance.saveBusinessApply(flag,expendType!!,departName!!, isLoan!!,
                loanReason,budgetAmount,remark, cashContent,
                officeJson)
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


}
