package com.outsource.danding.qingdaoic.ui.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.outsource.danding.qingdaoic.R
import com.outsource.danding.qingdaoic.base.BaseActivity
import com.outsource.danding.qingdaoic.bean.AuditOffice
import com.outsource.danding.qingdaoic.bean.AuditRecord
import com.outsource.danding.qingdaoic.net.HttpClient
import com.outsource.danding.qingdaoic.widget.AuditOfficeView
import com.outsource.danding.qingdaoic.widget.AuditRecordAdapter
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_business_detail.*


class BusinessDetailActivity : BaseActivity() {

    private var passIsShow = false
    private var officeList:MutableList<AuditOffice>?=null
    private var recordList:MutableList<AuditRecord>?=null
    private var recordAdapter: AuditRecordAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business_detail)
        initView()
    }

    private fun initView() {
        title="申请详情"

        officeList= mutableListOf()


        recordList= mutableListOf()
        recordAdapter=AuditRecordAdapter(this,recordList!!)
        lt_record.adapter=recordAdapter




        initListener()
        getBusinessInfoDetail()
    }


    private fun initListener() {


    }

    private fun getBusinessInfoDetail(){

        HttpClient.instance.getApplyInfoDetail(intent.extras.get("expendId") as String)
                .bindToLifecycle(this)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe({
                    json: JsonObject ->


                    val data= json.getAsJsonObject("data")
                    if(data!=null)
                    {
                        tv_cashContent.text=data.get("cashContent").toString()
                        tv_remark.text=data.get("remark")?.toString()

                        //办公费
                        officeList?.clear()

                        if(data.get("dataList")!=null&& data.getAsJsonArray("dataList")!=null)
                        {
                            val list=data.getAsJsonArray("dataList")
                            if(list!=null&&list.size()>0)
                            {

                                ll_office.visibility= View.VISIBLE
                                val  gson= Gson()
                                for((index,ob) in list.withIndex())
                                {
                                    val office: AuditOffice = gson.fromJson(ob, AuditOffice::class.java)
                                    officeList?.add(office)
                                    var officeView:AuditOfficeView= AuditOfficeView(this,index)
                                    ll_office.addView(officeView)
                                    var layoutParams = officeView.layoutParams
                                    layoutParams.width=LinearLayout.LayoutParams.MATCH_PARENT
                                    layoutParams.height=LinearLayout.LayoutParams.WRAP_CONTENT
                                    officeView.layoutParams=layoutParams
                                    officeView.setName(office.name)
                                    officeView.setMoney(office.money)
                                    officeView.setNumber(office.number)
                                    officeView.setStandard(office.standard)
                                    officeView.setUnivalent(office.univalent)
                                    officeView.setRemarks(office.remarks)


                                }



                            }
                        }

                        //审核记录
                        recordList?.clear()
                        when (data.get("recordList")){
                            is JsonArray ->
                            {
                                val list=data.getAsJsonArray("recordList")
                                if(list!=null&&list.size()>0)
                                {
                                    ll_record.visibility=View.VISIBLE
                                    val  gson= Gson()
                                    for(ob in list)
                                    {
                                        val record: AuditRecord = gson.fromJson(ob, AuditRecord::class.java)
                                        recordList?.add(record)
                                    }
                                    recordAdapter?.notifyDataSetChanged()
                                    var totalHeight=0
                                    for(i in recordList!!.indices)
                                    {
                                        val itemView = recordAdapter?.getView(i, null, lt_record)
                                        itemView?.measure(0,0)
                                        totalHeight+=itemView!!.measuredHeight
                                    }
                                    var params: ViewGroup.LayoutParams  = lt_record.getLayoutParams();
                                    params.height = totalHeight + (lt_record.getDividerHeight() * (recordAdapter!!.count -1))
                                    lt_record.layoutParams=params
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
