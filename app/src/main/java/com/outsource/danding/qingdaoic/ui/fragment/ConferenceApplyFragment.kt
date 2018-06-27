package com.outsource.danding.qingdaoic.ui.fragment

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.outsource.danding.qingdaoic.R
import com.outsource.danding.qingdaoic.base.BaseListFragment
import com.outsource.danding.qingdaoic.base.FNAdapter
import com.outsource.danding.qingdaoic.bean.BusinessInfo
import com.outsource.danding.qingdaoic.net.HttpClient
import com.outsource.danding.qingdaoic.ui.activity.AuditApplyDetailActivity
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.item_list_conference.view.*


class ConferenceApplyFragment : BaseListFragment<BusinessInfo>() {


    override fun enableLazyLoad(): Boolean = false

    override fun setLayoutId(): Int = R.layout.fragment_business_apply

    override fun loadData() {
        enableRefresh(true)
        getBusinessApplyList()
    }

    override fun getView(parent: ViewGroup?, viewType: Int): View = layoutInflater.inflate(R.layout.item_list_conference, parent, false)

    override fun bindDataToView(holder: FNAdapter.MyViewHolder?, position: Int) {
        holder!!.itemView.budget_amount.text ="$"+ mList[position].budgetAmount
        holder!!.itemView.expend_type.text = mList[position].expendType
        holder!!.itemView.state.text = mList[position].state
        holder!!.itemView.create_time.text=mList[position].createTime
    }

    private fun getBusinessApplyList()
    {
        HttpClient.instance.getshenQingInfoList()
                .bindToLifecycle(this)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe({
                    json: JsonObject ->


                    val data= json.getAsJsonObject("data")
                    if(data!=null&&data.getAsJsonArray("dataList")!=null)
                    {
                        val list=data.getAsJsonArray("dataList")
                        if(list!=null&&list.size()>0)
                        {
                            val  gson= Gson()
                            for(ob in list)
                            {
                                val applyInfo: BusinessInfo = gson.fromJson(ob, BusinessInfo::class.java)
                                mList.add(applyInfo)
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