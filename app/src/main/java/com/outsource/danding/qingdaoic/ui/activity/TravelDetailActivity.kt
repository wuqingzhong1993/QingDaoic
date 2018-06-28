package com.outsource.danding.qingdaoic.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.outsource.danding.qingdaoic.OnItemIndexDelete
import com.outsource.danding.qingdaoic.R
import com.outsource.danding.qingdaoic.app.QdApplication
import com.outsource.danding.qingdaoic.base.BaseActivity
import com.outsource.danding.qingdaoic.bean.*
import com.outsource.danding.qingdaoic.net.HttpClient
import com.outsource.danding.qingdaoic.widget.AuditOfficeView
import com.outsource.danding.qingdaoic.widget.AuditRecordAdapter
import com.outsource.danding.qingdaoic.widget.TravelInfoView
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_travel_detail.*

class TravelDetailActivity : BaseActivity() , AuditOfficeView.OnItemDelete {


    private var passIsShow = false
    private var travelList:MutableList<TravelInfo>?=null
    private var photoList:MutableList<Photo>?=null
    private var recordList:MutableList<AuditRecord>?=null
    private var recordAdapter: AuditRecordAdapter?=null
    private var observerList:MutableList<OnItemIndexDelete>?=null
    var zhichuList:MutableList<ZhiChu>?=null
    var zhichuNames: MutableList<String>?=null
    var departments:MutableList<Department>?=null
    var departName:String?=null
    var departmentNames:MutableList<String>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_travel_detail)
        initView()
    }

    private fun initView() {
        title="申请详情"

        travelList= mutableListOf()

        recordList= mutableListOf()
        recordAdapter= AuditRecordAdapter(this, recordList!!)
        lt_record.adapter=recordAdapter
        photoList= mutableListOf()

        observerList= mutableListOf()




        //初始化单位的adapter
        departments = QdApplication.getDepartments()
        departmentNames= mutableListOf()
        for(department in departments!!)
        {
            departmentNames?.add(department.deptName)
        }
        val adapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_item, departmentNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_dept.adapter=adapter


        initListener()
        getBusinessInfoDetail()
    }


    private fun initListener() {


        ll_save.setOnClickListener {

        }


        img_add.setOnClickListener {
            addTravelInfoView()
        }
    }

    private fun addTravelInfoView()
    {
        val travel = TravelInfo(mutableListOf(), "", "", "", "", "", "", "",
                "","","","","","","","","","","0",
                "0","0","0","0","0","0","")
        travelList?.add(travel)
        val travelView= TravelInfoView(this, travelList!!.size - 1)
        //ll_office.addView(officeView)
        travelView.setDataSource(travelList)//绑定数据源
        observerList?.add(travelView)//添加监听者

        var layoutParams = travelView.layoutParams
        layoutParams.width= LinearLayout.LayoutParams.MATCH_PARENT
        layoutParams.height= LinearLayout.LayoutParams.WRAP_CONTENT
        travelView.layoutParams=layoutParams

        travelView.setCity(travel.city)
        travelView.setCityTown(travel.city_town)
        travelView.setZsMoney(travel.zs_money)
        travelView.setFare(travel.fare)
        travelView.setHotel(travel.ht_money)
        travelView.setHsMoney(travel.hs_money)
        travelView.setJtMoney(travel.jt_money)
        travelView.setLevels(travel.all_money)
        travelView.setOtherFee(travel.qt_money)
        travelView.setSendCar(travel.sendCar)
        travelView.setzsJd(travel.zs_jd)
        travelView.setJtToolsStr(travel.jt_tools_str)
        travelView.setfhToolsStr(travel.fh_tools_str)
        travelView.setWNumber(travel.w_number)
        travelView.setRemarks(travel.remarks)
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

                        //tv_remark.text=data.get("remark").toString().replace("\"","")



                        if(data.get("applyDeptName")!=null&&departmentNames?.size!=0)
                        {
                            val applyDeptName=data.get("applyDeptName").toString().replace("\"","")
                            for ((index,name) in departmentNames!!.withIndex())
                            {
                                if(applyDeptName==name)
                                {
                                    sp_dept.setSelection(index,true)
                                    break
                                }
                            }
                        }



                        if(data.get("isLoan")!=null)
                        {
                            when(data.get("isLoan").toString())
                            {
                                "0"->{
                                    rg_isLoan.check(R.id.yes)
                                }
                                "1"->{
                                    rg_isLoan.check(R.id.no)
                                }
                                else->{}
                            }
                        }


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

                        //差旅信息
                        travelList?.clear()

                        if(data.get("travelList")!=null&& data.getAsJsonArray("travelList")!=null)
                        {
                            val list=data.getAsJsonArray("travelList")
                            if(list!=null&&list.size()>0)
                            {

                                ll_travel.visibility= View.VISIBLE
                                val  gson= Gson()
                                for((index,ob) in list.withIndex())
                                {
                                    val travel: TravelInfo = gson.fromJson(ob, TravelInfo::class.java)
                                    travelList?.add(travel)
                                    var travelView= TravelInfoView(this, index)
                                    ll_travel.addView(travelView)
                                    travelView.setDataSource(travelList)//绑定数据源
                                    observerList?.add(travelView)//添加监听者

                                    var layoutParams = travelView.layoutParams
                                    layoutParams.width= LinearLayout.LayoutParams.MATCH_PARENT
                                    layoutParams.height= LinearLayout.LayoutParams.WRAP_CONTENT
                                    travelView.layoutParams=layoutParams


                                    travelView.setCity(travel.city)
                                    travelView.setCityTown(travel.city_town)
                                    travelView.setZsMoney(travel.zs_money)
                                    travelView.setFare(travel.fare)
                                    travelView.setHotel(travel.ht_money)
                                    travelView.setHsMoney(travel.hs_money)
                                    travelView.setJtMoney(travel.jt_money)
                                    travelView.setLevels(travel.all_money)
                                    travelView.setOtherFee(travel.qt_money)
                                    travelView.setSendCar(travel.sendCar)
                                    travelView.setzsJd(travel.zs_jd)
                                    travelView.setJtToolsStr(travel.jt_tools_str)
                                    travelView.setfhToolsStr(travel.fh_tools_str)
                                    travelView.setWNumber(travel.w_number)
                                    travelView.setRemarks(travel.remarks)


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
                                    ll_record.visibility= View.VISIBLE
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
                                    var params: ViewGroup.LayoutParams = lt_record.getLayoutParams();
                                    params.height = totalHeight + (lt_record.getDividerHeight() * (recordAdapter!!.count -1))
                                    lt_record.layoutParams=params
                                }
                            }
                            else ->
                            {
                                Log.d("", "")
                            }
                        }




                    }


                }, {
                    e: Throwable ->
                    cancelProgressDialog()
                })
    }


    override fun onDelete(position: Int) {
        //ll_office.removeViewAt(position)
        travelList?.removeAt(position)
        observerList?.removeAt(position)
        for(observer in observerList!!)
        {
            observer.onItemIndexDelete(position)
        }
    }
}