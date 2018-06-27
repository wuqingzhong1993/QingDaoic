package com.outsource.danding.qingdaoic.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.google.gson.JsonObject
import com.outsource.danding.qingdaoic.OnItemIndexDelete
import com.outsource.danding.qingdaoic.R
import com.outsource.danding.qingdaoic.app.QdApplication
import com.outsource.danding.qingdaoic.base.BaseActivity
import com.outsource.danding.qingdaoic.bean.BusinessOffice
import com.outsource.danding.qingdaoic.bean.Department
import com.outsource.danding.qingdaoic.bean.Receipt
import com.outsource.danding.qingdaoic.bean.ZhiChu
import com.outsource.danding.qingdaoic.net.HttpClient
import com.outsource.danding.qingdaoic.ui.fragment.DatePickerFragment
import com.outsource.danding.qingdaoic.widget.ReceiptAdapter
import com.outsource.danding.qingdaoic.widget.ReceiptView
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_new_bao_xiao.*

class NewBaoXiaoActivity : BaseActivity(), DatePickerFragment.OnDateSetListener ,
        ReceiptView.OnItemDelete {

    private  lateinit var mTarget: View

    private var passIsShow = false


    var receiptList:MutableList<Receipt>?=null
    private var observerList:MutableList<OnItemIndexDelete>?=null

    var receiptAmount:Int=0
    var receiptNumber:Int=0
    private var amountMap:MutableMap<Int,Int> = mutableMapOf()
    private var numberMap:MutableMap<Int,Int> = mutableMapOf()


    var budgetAmount:String ?=null
    var applyDeptId:String?= null
    var applyDeptName:String?= null
    var internalId:String?= null
    var internalName:String?= null
    var reason:String?= null
    var sumNum:String?= null
    var sumAmount:String?= null
    var reimbList:MutableList<String>?= null
    var zzList:MutableList<String>?=null
    var expendType:String?= null
    var expendId:String?= null
    var offcard:String?= null
    var cash:String?= null

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


        //初始化支出事项的adapter
        val zhichuList:MutableList<ZhiChu> = QdApplication.getZhiChuList()
        val zhichuNames= mutableListOf<String>()
        for(zhichu in zhichuList)
        {
            zhichuNames.add(zhichu.key)
        }
        val zhichuAdapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_item, zhichuNames)
        zhichuAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_zhichu.adapter=zhichuAdapter


        //初始化收据的列表
        receiptList= mutableListOf()
        observerList= mutableListOf()
        addReceipt()


        amountMap= mutableMapOf()
        numberMap= mutableMapOf()


        initListener()
    }

    private fun initListener() {
        btn_commit.setOnClickListener {
            saveBaoXiaoApply("0")
        }
        btn_temp_save.setOnClickListener{
            saveBaoXiaoApply("1")
        }

        img_add.setOnClickListener {
            addReceipt()
        }

    }


    private fun addReceipt(){
        val receipt = Receipt("","0","0")
        receiptList?.add(receipt)
        val receiptView= ReceiptView(this,receiptList!!.size-1)
        ll_receipt.addView(receiptView)
        receiptView.setDataSource(receiptList)//绑定数据源
        observerList?.add(receiptView)//添加监听者

        receiptView.setReimbName(receipt.reimbName)
        receiptView.setAmount(receipt.amount)
        receiptView.setNumber(receipt.number)
    }


    private fun saveBaoXiaoApply(flag: String) {
        HttpClient.instance.saveBaoXiaoApply(flag!!,applyDeptId!!,applyDeptName!!, internalId!!,
                internalName!!,reason!!,sumNum!!, sumAmount!!,
                reimbList.toString()!!,zzList.toString()!!,expendType!!,expendId!!,offcard!!,cash!!)
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


    override fun onDelete(position: Int) {
        ll_receipt.removeViewAt(position)
        receiptList?.removeAt(position)
        observerList?.removeAt(position)
        for(observer in observerList!!)
        {
            observer.onItemIndexDelete(position)
        }
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
