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
import com.outsource.danding.qingdaoic.bean.ShenQing
import com.outsource.danding.qingdaoic.net.HttpClient
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.item_list_my_shen_qing.view.*

class MyShenQingActivity : BaseListActivity<ShenQing>() {

    override fun loadData() {
        enableRefresh(true)
        getApplyInfoList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(R.layout.activity_my_shen_qing)
        title="我的申请"
        initListener()
    }

    private fun initListener(){
    }


    override fun getView(parent: ViewGroup?, viewType: Int): View = layoutInflater.inflate(R.layout.item_list_my_shen_qing, parent, false)

    override fun bindDataToView(holder: FNAdapter.MyViewHolder?, position: Int) {

        holder!!.itemView.tv_internalName.text = mList[position].internalName
        holder!!.itemView.tv_sumAmount.text ="$"+ mList[position].sumAmount.toString()
        holder!!.itemView.tv_expendType.text = mList[position].expendType
        holder!!.itemView.tv_applyDeptName.text = mList[position].applyDeptName
        holder!!.itemView.tv_createTime.text=mList[position].createTime
        holder!!.itemView.tv_createName.text=mList[position].createName
        holder!!.itemView.tv_state.text=mList[position].state
    }

    override fun onItemClick(holder: FNAdapter.MyViewHolder?, position: Int) {
        intent= Intent(this,NewCultivateActivity::class.java)
        startActivity(intent)
    }

    private fun getApplyInfoList()
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
                                val shenQing: ShenQing = gson.fromJson(ob, ShenQing::class.java)
                                mList.add(shenQing)
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
