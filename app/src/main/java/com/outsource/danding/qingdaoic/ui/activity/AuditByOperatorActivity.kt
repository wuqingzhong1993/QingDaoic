package com.outsource.danding.qingdaoic.ui.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.outsource.danding.qingdaoic.R
import com.outsource.danding.qingdaoic.base.BaseListActivity
import com.outsource.danding.qingdaoic.base.FNAdapter
import com.outsource.danding.qingdaoic.bean.AuditByOperator

import com.outsource.danding.qingdaoic.net.HttpClient
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_audit_by_operator.*
import kotlinx.android.synthetic.main.item_list_audit_by_operator.view.*
import java.util.*

class AuditByOperatorActivity : BaseListActivity<AuditByOperator>() {


    override fun loadData() {
        enableRefresh(true)
        getAuditWaiting()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(R.layout.activity_audit_by_operator)
        title="经办事项"
        initListener()
    }


    private fun initListener(){

        //时间排序
        tv_tab_one.setOnClickListener {

            Collections.sort(mList,object:Comparator<AuditByOperator>{
                override fun compare(o1: AuditByOperator?, o2: AuditByOperator?): Int {
                    return o1!!.taskId.toInt()-o2!!.taskId.toInt()
                }
            })
            setListAdapter()
        }
        tv_tab_two.setOnClickListener{

        }
    }

    override fun getView(parent: ViewGroup?, viewType: Int): View = layoutInflater.inflate(R.layout.item_list_audit_by_operator, parent, false)

    override fun bindDataToView(holder: FNAdapter.MyViewHolder?, position: Int) {
        holder!!.itemView.audit_internalName.text = mList[position].internalName
        holder!!.itemView.audit_date.text=mList[position].taskId
    }

    override fun onItemClick(holder: FNAdapter.MyViewHolder?, position: Int) {
        intent= Intent(this,BasicInfoActivity::class.java)
        startActivity(intent)
    }



    private fun getAuditWaiting() {

        HttpClient.instance.getAuditByOperator()
                .bindToLifecycle(this)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe({
                    json: JsonObject ->

                    cancelProgressDialog()
                    val data= json.getAsJsonObject("data")
                    if(data!=null&&data.getAsJsonArray("dataList")!=null)
                    {
                        val list=data.getAsJsonArray("dataList")
                        if(list!=null&&list.size()>0)
                        {
                            val  gson= Gson()
                            for(ob in list)
                            {
                                val audit: AuditByOperator = gson.fromJson(ob, AuditByOperator::class.java)
                                mList.add(audit)
                            }
                        }
                    }
                    enableLoadMore(false)
                    setListAdapter()
                }, {
                    e: Throwable ->
                    cancelProgressDialog()
                })
    }

}
