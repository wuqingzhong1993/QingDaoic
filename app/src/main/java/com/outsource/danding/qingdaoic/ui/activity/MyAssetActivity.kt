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
import com.outsource.danding.qingdaoic.bean.Asset
import com.outsource.danding.qingdaoic.net.HttpClient
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.item_list_my_assets.view.*

class MyAssetActivity : BaseListActivity<Asset>() {

    override fun loadData() {
        enableRefresh(true)
        getApplyInfoList()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(R.layout.activity_my_asset)
        title="我的资产"
        initListener()
    }

    private fun initListener(){

    }

    override fun getView(parent: ViewGroup?, viewType: Int): View = layoutInflater.inflate(R.layout.item_list_my_assets, parent, false)

    override fun bindDataToView(holder: FNAdapter.MyViewHolder?, position: Int) {

        holder!!.itemView.tv_assetsNum.text = mList[position].assetsNum
        holder!!.itemView.tv_assetsName.text = mList[position].assetsName
        holder!!.itemView.tv_amount.text = mList[position].amount.toString()
        holder!!.itemView.tv_price.text = mList[position].price
        holder!!.itemView.tv_postingDate.text=mList[position].postingDate
    }

    override fun onItemClick(holder: FNAdapter.MyViewHolder?, position: Int) {

        val intent= Intent(activity, MyAssetDetailActivity::class.java)
        intent.putExtra("asset",mList[position])
        startActivity(intent)
    }

    private fun getApplyInfoList()
    {
        HttpClient.instance.getDoZCList("1")
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
                                val asset: Asset = gson.fromJson(ob, Asset::class.java)
                                mList.add(asset)
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
