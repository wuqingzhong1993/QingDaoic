package com.outsource.danding.qingdaoic.ui.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.google.gson.JsonObject
import com.outsource.danding.qingdaoic.R
import com.outsource.danding.qingdaoic.base.BaseListActivity
import com.outsource.danding.qingdaoic.base.FNAdapter
import com.outsource.danding.qingdaoic.bean.AuditWaiting
import com.outsource.danding.qingdaoic.net.HttpClient
import com.outsource.danding.qingdaoic.net.retrofit.NetObserver
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_audit_waiting.*
import kotlinx.android.synthetic.main.item_list_audit_waiting.view.*
import java.util.*
import kotlin.Comparator

class AuditWaitingActivity : BaseListActivity<AuditWaiting>() {




    override fun loadData() {
        enableRefresh(true)
        getAuditWaiting()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(R.layout.activity_audit_waiting)
        title="待办事项"
        initListener()
    }

    private fun initListener(){

        //时间排序
        tv_tab_one.setOnClickListener {

            Collections.sort(mList,object:Comparator<AuditWaiting>{
                override fun compare(o1: AuditWaiting?, o2: AuditWaiting?): Int {
                    return o1!!.taskId.toInt()-o2!!.taskId.toInt()
                }
            })
            setListAdapter()
        }
        tv_tab_two.setOnClickListener{

        }
    }

    override fun getView(parent: ViewGroup?, viewType: Int): View = layoutInflater.inflate(R.layout.item_list_audit_waiting, parent, false)

    override fun bindDataToView(holder: FNAdapter.MyViewHolder?, position: Int) {
        holder!!.itemView.audit_content.text = mList[position].content
        holder!!.itemView.audit_type.text = mList[position].type
        holder!!.itemView.audit_price.text = mList[position].price
        holder!!.itemView.audit_date.text=mList[position].taskId
    }

    override fun onItemClick(holder: FNAdapter.MyViewHolder?, position: Int) {
       intent=Intent(this,BasicInfoActivity::class.java)
        startActivity(intent)
    }

    private fun getAuditWaiting() {



//        HttpClient.instance.getAuditWaiting()
//                .bindToLifecycle(this)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//
//                .subscribe({
//                    json: JsonObject ->
//                    val data=json.getAsJsonObject("data")
//                    cancelProgressDialog()
//
//                }, {
//                    e: Throwable ->
//                    cancelProgressDialog()
//                })

        mList.add(AuditWaiting("报销审核","办公费","0","0","1","1142","first","1000"))
        mList.add(AuditWaiting("执行审核","差旅费","1","0","1","1143","first","1000"))
        mList.add(AuditWaiting("执行审核","差旅费","0","0","1","1141","first","1000"))

        mList.add(AuditWaiting("执行审核","差旅费","1","0","1","1148","first","1000"))
        mList.add(AuditWaiting("执行审核","差旅费","0","0","1","1145","first","1000"))
        enableLoadMore(false)
        setListAdapter()



//                .subscribe(object : NetObserver<Message>() {
//                    override fun onHandleSuccess(t: Message?) {
//                        if (t == null) {
//                            setListAdapter()
//                            return
//                        }
//                        if (typeList.isEmpty()) {
//                            typeList.addAll(t.type_list)
//                        }
//                        if (t.message.messages.size < mCount) {
//                            enableLoadMore(false)
//                        } else {
//                            enableLoadMore(true)
//                        }
//                        mList.addAll(t.message.messages)
//                        if (!mList.isEmpty())
//                            minId = mList.last().id
//                        setListAdapter()
//                    }
//                })
    }

}
