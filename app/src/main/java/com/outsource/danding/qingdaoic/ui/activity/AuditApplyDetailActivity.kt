package com.outsource.danding.qingdaoic.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.outsource.danding.qingdaoic.R
import com.outsource.danding.qingdaoic.app.QdApplication
import com.outsource.danding.qingdaoic.base.BaseActivity
import com.outsource.danding.qingdaoic.bean.*
import com.outsource.danding.qingdaoic.net.HttpClient
import com.outsource.danding.qingdaoic.widget.AuditOfficeAdapter
import com.outsource.danding.qingdaoic.widget.AuditRecordAdapter
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_audit_apply_detail.*

class AuditApplyDetailActivity : BaseActivity() {

    private  lateinit var mTarget: View
    private var  officeList:MutableList<AuditOffice>?=null
    private var officeAdapter:AuditOfficeAdapter?=null
    private var recordList:MutableList<AuditRecord>?=null
    private var recordAdapter:AuditRecordAdapter?=null
    private var kemuList:MutableList<Kemu>?=null
    private var kemuNameList:MutableList<String>?=null
    private var kemuAdapter:ArrayAdapter<String>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audit_apply_detail)
        initView()
    }

    private fun initView(){

        officeList= mutableListOf()
        officeAdapter= AuditOfficeAdapter(this,officeList!!)
        lt_dataList.adapter=officeAdapter

        recordList= mutableListOf()
        recordAdapter=AuditRecordAdapter(this,recordList!!)
        lt_record.adapter=recordAdapter

        kemuList= mutableListOf()
        kemuNameList= mutableListOf()
        kemuAdapter= ArrayAdapter(this, android.R.layout.simple_spinner_item, kemuNameList)
        kemuAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_dept.adapter=kemuAdapter



        initListener()
        getApplyInfoDetail()
    }

    private fun initListener() {


    }

    private fun getApplyInfoDetail()
    {
        val expendId=intent.extras.getString("expendId")
        HttpClient.instance.getApplyInfoDetail(expendId)
                .bindToLifecycle(this)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe({
                    json: JsonObject ->


                    val data= json.getAsJsonObject("data")
                    if(data!=null)
                    {
                        tv_number.text=data.get("number").toString()
                        tv_applyDeptName.text=data.get("applyDeptName").toString()
                        tv_expendType.text=data.get("expendType").toString()
                        tv_createName.text=data.get("createName").toString()
                        tv_budgetAmount.text=data.get("budgetAmount").toString()
                        if(data.get("isLoan") != null)
                        {
                            if(data.get("isLoan").toString()=="1")
                                tv_isLoan.text="是"
                            else
                                tv_isLoan.text="否"
                        }
                        tv_createTime.text=data.get("createTime").toString()
                        tv_expendDetail.text=data.get("expendType").toString().replace("\"","")+"详细列表"

                        //kemuList
                        kemuList?.clear()
                        kemuNameList?.clear()
                        if(data.get("kemuList")!=null&&data.getAsJsonArray("kemuList")!=null)
                        {
                            val list=data.getAsJsonArray("kemuList")
                            if(list!=null&&list.size()>0)
                            {
                                val  gson= Gson()
                                for(ob in list)
                                {
                                    val kemu: Kemu = gson.fromJson(ob, Kemu::class.java)
                                    kemuList?.add(kemu)
                                    kemuNameList?.add(kemu.indexName)
                                }
                                kemuAdapter?.notifyDataSetChanged()

                            }
                        }

                        officeList?.clear()

                        //办公费
                        if(data.get("dataList")!=null&&data.getAsJsonArray("dataList")!=null)
                        {
                            val list=data.getAsJsonArray("dataList")
                            if(list!=null&&list.size()>0)
                            {
                                val  gson= Gson()
                                for(ob in list)
                                {
                                    val office: AuditOffice = gson.fromJson(ob, AuditOffice::class.java)
                                    officeList?.add(office)
                                }
                                officeAdapter?.notifyDataSetChanged()
                            }
                        }

                        //审核记录
                        recordList?.clear()
                        when (data.get("recordList")){
                            is JsonArray->
                            {
                                val list=data.getAsJsonArray("recordList")
                                if(list!=null&&list.size()>0)
                                {
                                    val  gson= Gson()
                                    for(ob in list)
                                    {
                                        val record: AuditRecord = gson.fromJson(ob, AuditRecord::class.java)
                                        recordList?.add(record)
                                    }
                                    recordAdapter?.notifyDataSetChanged()
                                }
                            }
                            else ->
                            {
                                Log.d("","")
                            }
                        }



                    }





                }, {
                    e: Throwable ->
                    cancelProgressDialog()
                })
    }



}
