package com.outsource.danding.qingdaoic.ui.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.outsource.danding.qingdaoic.R
import com.outsource.danding.qingdaoic.base.BaseListActivity
import com.outsource.danding.qingdaoic.base.FNAdapter
import com.outsource.danding.qingdaoic.bean.ApplyInfo
import com.outsource.danding.qingdaoic.net.HttpClient
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.item_list_business.view.*

class ConferenceActivity : BaseListActivity<ApplyInfo>() {

    override fun loadData() {
        enableRefresh(true)
        getApplyInfoList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun setContentView(layoutResID: Int) {
        super.setContentView(R.layout.activity_conference)
        title="会议"
        initListener()
    }

    private fun initListener(){

    }

    override fun getView(parent: ViewGroup?, viewType: Int): View = layoutInflater.inflate(R.layout.item_list_conference, parent, false)

    override fun bindDataToView(holder: FNAdapter.MyViewHolder?, position: Int) {

        holder!!.itemView.internal_name.text = mList[position].internalName
        holder!!.itemView.budget_amount.text ="$"+ mList[position].budgetAmount.toString()
        holder!!.itemView.expend_type.text = mList[position].expendType
        holder!!.itemView.state.text = mList[position].state
        holder!!.itemView.create_time.text=mList[position].createTime
    }

    override fun onItemClick(holder: FNAdapter.MyViewHolder?, position: Int) {
        intent= Intent(this,NewCultivateActivity::class.java)
        startActivity(intent)
    }

    private fun getApplyInfoList()
    {
        HttpClient.instance.getApplyInfoList()
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
                                val applyInfo: ApplyInfo = gson.fromJson(ob, ApplyInfo::class.java)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menu_add) {
            val intent = Intent(this,NewConferenceActivity::class.java)
            jumpToActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }


}