package com.outsource.danding.qingdaoic.ui.fragment

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.outsource.danding.qingdaoic.R
import com.outsource.danding.qingdaoic.base.BaseListFragment
import com.outsource.danding.qingdaoic.base.FNAdapter
import com.outsource.danding.qingdaoic.bean.ApplyInfo
import com.outsource.danding.qingdaoic.bean.AuditApplyInfo
import com.outsource.danding.qingdaoic.net.HttpClient
import com.outsource.danding.qingdaoic.ui.activity.AuditApplyDetailActivity
import com.outsource.danding.qingdaoic.ui.activity.BasicInfoActivity
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.item_list_audit_apply.view.*


class AuditApplyFragment : BaseListFragment<AuditApplyInfo>() {


    override fun enableLazyLoad(): Boolean = false

    override fun setLayoutId(): Int = R.layout.fragment_audit_apply

    override fun loadData() {
        enableRefresh(true)
        getAuditApplyList()
    }

    override fun getView(parent: ViewGroup?, viewType: Int): View = layoutInflater.inflate(R.layout.item_list_audit_apply, parent, false)

    override fun bindDataToView(holder: FNAdapter.MyViewHolder?, position: Int) {
        holder!!.itemView.number.text = mList[position].number
        holder!!.itemView.expend_type.text = mList[position].expendType
        holder!!.itemView.budget_amount.text = "¥"+ mList[position].budgetAmount
        holder!!.itemView.applyDeptName.text=mList[position].applyDeptName
        holder!!.itemView.createTime.text=mList[position].createTime
        holder!!.itemView.createName.text=mList[position].createName
    }

    private fun getAuditApplyList()
    {
        HttpClient.instance.getAuditApplyList()
                .bindToLifecycle(this)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe({
                    json: JsonObject ->


                    val data= json.getAsJsonObject("data")
                    if(data!=null)
                    {
                        if(data.get("dataList")!=null&&data.getAsJsonArray("dataList")!=null)
                        {
                            val list=data.getAsJsonArray("dataList")
                            if(list!=null&&list.size()>0)
                            {
                                val  gson= Gson()
                                for(ob in list)
                                {
                                    val applyInfo: AuditApplyInfo = gson.fromJson(ob, AuditApplyInfo::class.java)
                                    mList.add(applyInfo)
                                }
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

    override fun onItemClick(holder: FNAdapter.MyViewHolder?, position: Int) {
        val intent= Intent(activity, AuditApplyDetailActivity::class.java)
        intent.putExtra("expendId",mList[position].expendId)
        startActivity(intent)
    }


}