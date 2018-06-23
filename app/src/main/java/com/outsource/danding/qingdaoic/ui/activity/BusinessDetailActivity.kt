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
import com.outsource.danding.qingdaoic.OnItemIndexDelete
import com.outsource.danding.qingdaoic.R
import com.outsource.danding.qingdaoic.base.BaseActivity
import com.outsource.danding.qingdaoic.bean.AuditOffice
import com.outsource.danding.qingdaoic.bean.AuditRecord
import com.outsource.danding.qingdaoic.bean.Photo
import com.outsource.danding.qingdaoic.net.HttpClient
import com.outsource.danding.qingdaoic.widget.AuditOfficeView
import com.outsource.danding.qingdaoic.widget.AuditRecordAdapter
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_business_detail.*


class BusinessDetailActivity : BaseActivity() , AuditOfficeView.OnItemDelete {


    private var passIsShow = false
    private var officeList:MutableList<AuditOffice>?=null
    private var photoList:MutableList<Photo>?=null
    private var recordList:MutableList<AuditRecord>?=null
    private var recordAdapter: AuditRecordAdapter?=null
    private var observerList:MutableList<OnItemIndexDelete>?=null




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


        photoList= mutableListOf()

        observerList= mutableListOf()

        initListener()
        getBusinessInfoDetail()
    }


    private fun initListener() {


        ll_save.setOnClickListener {

        }


        img_add.setOnClickListener {

            val office =AuditOffice("","","","","","0","0","")
            officeList?.add(office)
            val officeView= AuditOfficeView(this,officeList!!.size-1)
            ll_office.addView(officeView)
            officeView.setDataSource(officeList)//绑定数据源
            observerList?.add(officeView)//添加监听者

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
                            }
                        }

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
                                    var officeView= AuditOfficeView(this,index)
                                    ll_office.addView(officeView)
                                    officeView.setDataSource(officeList)//绑定数据源
                                    observerList?.add(officeView)//添加监听者

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


    override fun onDelete(position: Int) {
        ll_office.removeViewAt(position)
        officeList?.removeAt(position)
        observerList?.removeAt(position)
        for(observer in observerList!!)
        {
            observer.onItemIndexDelete(position)
        }
    }
}
