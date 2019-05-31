package com.outsource.danding.qingdaoic.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.outsource.danding.qingdaoic.R
import com.outsource.danding.qingdaoic.base.BaseActivity
import com.outsource.danding.qingdaoic.bean.*
import com.outsource.danding.qingdaoic.net.HttpClient
import com.outsource.danding.qingdaoic.widget.AuditOfficeAdapter
import com.outsource.danding.qingdaoic.widget.AuditRecordAdapter
import com.outsource.danding.qingdaoic.widget.AuditTravelAdapter
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_audit_apply_detail.*
import android.widget.ScrollView
import com.outsource.danding.qingdaoic.base.GlideApp
import com.outsource.danding.qingdaoic.net.api.ApiConstants.PHOTO_URL


class AuditApplyDetailActivity : BaseActivity() {

    private  lateinit var mTarget: View
    private var  officeList:MutableList<AuditOffice>?=null
    private var officeAdapter:AuditOfficeAdapter?=null
    private var recordList:MutableList<AuditRecord>?=null
    private var recordAdapter:AuditRecordAdapter?=null
    private var kemuList:MutableList<Kemu>?=null
    private var kemuNameList:MutableList<String>?=null
    private var kemuAdapter:ArrayAdapter<String>?=null
    private var travelList:MutableList<AuditTravel>?=null
    private var travelAdapter:AuditTravelAdapter?=null
    private var photoList:MutableList<Photo>?=null

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

        travelList= mutableListOf()
        travelAdapter= AuditTravelAdapter(this,travelList!!)
        lt_travelList.adapter=travelAdapter


        photoList= mutableListOf()


        initListener()
        getApplyInfoDetail()
    }

    private fun initListener() {

        img_travel_collapse.setOnClickListener {
            if(lt_travelList.visibility!=View.GONE)
            {
                lt_travelList.visibility=View.GONE
                img_travel_collapse.setImageResource(R.drawable.ic_keyboard_arrow_up_black_32)
            }
            else
            {
                lt_travelList.visibility=View.VISIBLE
                img_travel_collapse.setImageResource(R.drawable.ic_keyboard_arrow_down_black_32)
            }

        }
        btn_passed.setOnClickListener{
            checkApplyPassed()
        }
        btn_notPassed.setOnClickListener{
            checkApplyNotPassed()

        }
    }

    private fun checkApplyPassed() {
//        HttpClient.instance.checkApplyPassed(taskId!!,expendId!!,reason!!, kemuName!!,
//                kemuId!!,basis!!)
//                .bindToLifecycle(this)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    json: JsonObject ->
//                    val data=json.getAsJsonObject("data")
//
//                    cancelProgressDialog()
//
//
//                }, {
//                    e: Throwable ->
//                    cancelProgressDialog()
//                })

    }
    private fun checkApplyNotPassed() {
//        HttpClient.instance.checkApplyNotPassed(taskId!!,expendId!!,reason!!,basis!!)
//                .bindToLifecycle(this)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    json: JsonObject ->
//                    val data=json.getAsJsonObject("data")
//
//                    cancelProgressDialog()
//
//
//                }, {
//                    e: Throwable ->
//                    cancelProgressDialog()
//                })

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
                        tv_createName.text=data.get("createName").toString().replace("\"","")
                        tv_budgetAmount.text=data.get("budgetAmount").toString()
                        if(data.get("isLoan") != null)
                        {
                            if(data.get("isLoan").toString()=="1")
                                tv_isLoan.text="是"
                            else
                                tv_isLoan.text="否"
                        }
                        tv_createTime.text=data.get("createTime").toString().replace("\"","")
                        tv_expendDetail.text=data.get("expendType").toString().replace("\"","")+"详细列表"




                        //图片
                        if(data.get("photoList")!=null&&data.getAsJsonArray("photoList")!=null)
                        {
                            val list=data.getAsJsonArray("photoList")
                            if(list!=null&&list.size()>0)
                            {
                                val  gson= Gson()
                                for((index,ob) in list.withIndex())
                                {
                                    val photo: Photo = gson.fromJson(ob, Photo::class.java)
                                    photoList?.add(photo )
                                }
                                val photo = photoList!![0]
                                GlideApp.with(this).load("http://www.lnmoto.cn/bbs/data/attachment/forum/201310/03/075632kpll31ppggpog1ip.jpg").fitCenter().into(img_country)
                            }
                        }

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

                        //差旅费
                        travelList?.clear()
                        if(data.get("travelList")!=null&&data.getAsJsonArray("travelList")!=null)
                        {
                            val list=data.getAsJsonArray("travelList")
                            if(list!=null&&list.size()>0)
                            {
                                ll_travelList.visibility=View.VISIBLE
                                val  gson= Gson()
                                for(ob in list)
                                {
                                    val office: AuditTravel = gson.fromJson(ob, AuditTravel::class.java)
                                    travelList?.add(office)
                                }
                                travelAdapter?.notifyDataSetChanged()
                                var totalHeight=0
                                for(i in travelList!!.indices)
                                {
                                    val itemView = travelAdapter?.getView(i, null, lt_travelList)
                                    itemView?.measure(0,0)
                                    totalHeight+=itemView!!.measuredHeight
                                }
                                var params: ViewGroup.LayoutParams  = lt_travelList.getLayoutParams();
                                params.height = totalHeight + (lt_travelList.getDividerHeight() * (travelAdapter!!.count -1))
                                lt_travelList.layoutParams=params
                            }
                        }

                        //办公费
                        officeList?.clear()
                        if(data.get("dataList")!=null&&data.getAsJsonArray("dataList")!=null)
                        {
                            val list=data.getAsJsonArray("dataList")
                            if(list!=null&&list.size()>0)
                            {
                                ll_office.visibility=View.VISIBLE
                                val  gson= Gson()
                                for(ob in list)
                                {
                                    val office: AuditOffice = gson.fromJson(ob, AuditOffice::class.java)
                                    officeList?.add(office)
                                }
                                officeAdapter?.notifyDataSetChanged()
                            }
                        }

                        if(data.get("expendType")!=null)
                        {
                            when(data.get("expendType").toString())
                            {
                                "培训费","\"培训费\""->{

                                    ll_trainTime.visibility=View.VISIBLE
                                    tv_trainTime.text=data.get("trainTime").toString().replace("\"","")

                                    ll_trainEnd.visibility= View.VISIBLE
                                    tv_trainEnd.text=data.get("trainEnd").toString().replace("\"","")

                                    ll_trainReport.visibility=View.VISIBLE
                                    tv_trainReport.text=data.get("trainReport").toString().replace("\"","")

                                    ll_trainLeave.visibility=View.VISIBLE
                                    tv_trainLeave.text=data.get("trainLeave").toString().replace("\"","")

                                    ll_trainPlace.visibility=View.VISIBLE
                                    tv_trainPlace.text=data.get("trainPlace").toString().replace("\"","")

                                    ll_trainBudget.visibility=View.VISIBLE
                                    tv_trainBudget.text=data.get("trainBudget").toString().replace("\"","")

                                    ll_trainNum.visibility=View.VISIBLE
                                    tv_trainNum.text=data.get("trainNum").toString().replace("\"","")

                                    ll_trainStaffNum.visibility=View.VISIBLE
                                    tv_trainStaffNum.text=data.get("trainStaffNum").toString().replace("\"","")
                                }
                                "会议费","\"会议费\""->{

                                }
                                else->{

                                }
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

                        scrollView.postDelayed(
                                Runnable {
                                    //scrollView.fullScroll(ScrollView.FOCUS_UP)
                                },2000)



                    }





                }, {
                    e: Throwable ->
                    cancelProgressDialog()
                })
    }



}
