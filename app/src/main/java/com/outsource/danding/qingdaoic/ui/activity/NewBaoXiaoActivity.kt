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
import com.outsource.danding.qingdaoic.R
import com.outsource.danding.qingdaoic.app.QdApplication
import com.outsource.danding.qingdaoic.base.BaseActivity
import com.outsource.danding.qingdaoic.bean.Department
import com.outsource.danding.qingdaoic.bean.Receipt
import com.outsource.danding.qingdaoic.bean.ZhiChu
import com.outsource.danding.qingdaoic.net.HttpClient
import com.outsource.danding.qingdaoic.ui.fragment.DatePickerFragment
import com.outsource.danding.qingdaoic.widget.ReceiptAdapter
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_new_bao_xiao.*
import kotlinx.android.synthetic.main.item_list_audit_apply.*

class NewBaoXiaoActivity : BaseActivity(), DatePickerFragment.OnDateSetListener ,
        ReceiptAdapter.OnDeleteListener {

    private  lateinit var mTarget: View

    private var passIsShow = false

    lateinit var receiptAdapter: ReceiptAdapter
    var receiptList:MutableList<Receipt>?=null
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


        receiptList= mutableListOf(Receipt("","0","0"))
        receiptAdapter= ReceiptAdapter(this,receiptList!!)
        lt_receipt.adapter=receiptAdapter

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

        lt_receipt.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom -> onNotify(null,null) }

        //todo 设置删除项的回调


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

    override fun onDelete() {
        var totalHeight=0
        for(i in receiptList!!.indices)
        {
            val itemView = receiptAdapter.getView(i, null, lt_receipt)
            itemView.measure(0,0)
            totalHeight+=itemView.measuredHeight
        }
        var params: ViewGroup.LayoutParams  = lt_receipt.getLayoutParams();
        params.height = totalHeight + (lt_receipt.getDividerHeight() * (receiptAdapter.count -1))
        lt_receipt.layoutParams=params

    }

    /**
     * todo 将通知整合成按下标进行组织的形式
     */
    fun onNotify(number:Int?,amount:Int?) {

        if(lt_receipt.childCount>0)
        {
            receiptAmount=0
            receiptNumber=0
            for(i in 0  until  lt_receipt.childCount)
            {
                val j=i
                if(lt_receipt.getChildAt(i)==null)
                {
                    continue;
                }
                val container=lt_receipt.getChildAt(i)!! as LinearLayout
                val et_number= container.findViewById<EditText>(R.id.et_number)
                if(et_number.text.toString()!="")
                {
                    receiptNumber+=et_number.text.toString().toInt()
                }
            }

            tv_receipt_number.text=receiptNumber.toString()
            tv_receipt_amount.text=receiptAmount.toString()
        }

    }


}
