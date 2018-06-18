package com.outsource.danding.qingdaoic.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.outsource.danding.qingdaoic.R
import com.outsource.danding.qingdaoic.app.QdApplication
import com.outsource.danding.qingdaoic.base.BaseActivity
import com.outsource.danding.qingdaoic.bean.Department
import com.outsource.danding.qingdaoic.bean.Receipt
import com.outsource.danding.qingdaoic.bean.ZhiChu
import com.outsource.danding.qingdaoic.ui.fragment.DatePickerFragment
import com.outsource.danding.qingdaoic.widget.ReceiptAdapter
import kotlinx.android.synthetic.main.activity_new_bao_xiao.*

class NewBaoXiaoActivity : BaseActivity(), DatePickerFragment.OnDateSetListener {

    private  lateinit var mTarget: View

    private var passIsShow = false

    lateinit var receiptAdapter: ReceiptAdapter
    var receiptList:MutableList<Receipt>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_bao_xiao)
        initView()
    }


    private fun initView(){
        title="直接报销"

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


        //初始化单位的adapter
        val zhichuList:MutableList<ZhiChu> = QdApplication.getZhiChuList()
        val zhichuNames= mutableListOf<String>()
        for(zhichu in zhichuList)
        {
            zhichuNames.add(zhichu.key)
        }
        val zhichuAdapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_item, zhichuNames)
        zhichuAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_zhichu.adapter=zhichuAdapter


        receiptList= mutableListOf(Receipt("","0","0"))
        receiptAdapter= ReceiptAdapter(this,receiptList!!)
        lt_receipt.adapter=receiptAdapter


        initListener()
    }

    private fun initListener() {


        img_add.setOnClickListener {
            receiptList?.add(Receipt("","0","0"))
            receiptAdapter.notifyDataSetChanged()
            var totalHeight=0
            for(i in receiptList!!.indices)
            {
                val itemView = receiptAdapter.getView(i, null, lt_receipt)
                itemView.measure(0,0)
                totalHeight+=itemView.measuredHeight
            }
            var params:ViewGroup.LayoutParams  = lt_receipt.getLayoutParams();
            params.height = totalHeight + (lt_receipt.getDividerHeight() * (receiptAdapter.count -1))
            lt_receipt.layoutParams=params
        }

        //todo 设置删除项的回调


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
