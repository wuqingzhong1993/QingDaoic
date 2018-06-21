package com.outsource.danding.qingdaoic.ui.activity

import android.graphics.Paint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.outsource.danding.qingdaoic.R
import com.outsource.danding.qingdaoic.base.BaseActivity
import com.outsource.danding.qingdaoic.bean.Asset
import kotlinx.android.synthetic.main.activity_my_asset_detail.*


class MyAssetDetailActivity : BaseActivity() {

    private  lateinit var mTarget: View
    private lateinit var asset:Asset
    private var passIsShow = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_asset_detail)
        initView()
    }

    private fun initView() {
        title = "资产详情"
        tv_title_asset_detail.paint.flags= Paint.UNDERLINE_TEXT_FLAG
        asset=intent.extras.get("asset") as Asset

        tv_assetsNum.text = asset.assetsNum
        tv_assetsName.text=asset.assetsName
        tv_amount.text=asset.amount.toString()
        tv_typeId_str.text=asset.typeId_str
        tv_useDeptId.text=asset.useDeptId
        tv_typeOfValue.text=asset.typeOfValue
        tv_usePerson.text=asset.usePerson
        tv_assetsState.text=asset.assetsState
        tv_markFileDate.text=asset.markFileDate
        tv_postingDate.text=asset.postingDate
        tv_gainDate.text=asset.gainDate
        tv_version.text=asset.version
        tv_storePlace.text=asset.storePlace
        tv_unit.text=asset.unit
        tv_price.text=asset.price
        tv_gainWay.text=asset.gainWay
        tv_remark.text=asset.remark


        initListener()
    }

    private fun initListener() {

    }
}
