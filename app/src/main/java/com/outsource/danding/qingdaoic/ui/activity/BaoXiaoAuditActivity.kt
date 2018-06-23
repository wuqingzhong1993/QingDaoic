package com.outsource.danding.qingdaoic.ui.activity;


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.outsource.danding.qingdaoic.R;
import com.outsource.danding.qingdaoic.base.BaseActivity
import com.outsource.danding.qingdaoic.ui.fragment.AuditBaoXiaoApplyFragment
import com.outsource.danding.qingdaoic.ui.fragment.AuditBaoXiaoHistoryFragment
import com.outsource.danding.qingdaoic.widget.TabFragmentPagerAdapter
import kotlinx.android.synthetic.main.activity_audit.*

class BaoXiaoAuditActivity : BaseActivity() {

     private  lateinit var mTarget: View

     private var passIsShow = false

    private var fragments:MutableList<Fragment>?=null
    private var adapter: TabFragmentPagerAdapter?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bao_xiao_audit)
        initView()
    }

    private fun initView(){

        fragments= mutableListOf(AuditBaoXiaoApplyFragment(), AuditBaoXiaoHistoryFragment())
        adapter= TabFragmentPagerAdapter(supportFragmentManager,fragments)
        view_pager.adapter=adapter
        view_pager.setCurrentItem(0,true)


        initListener()
    }

    private fun initListener() {


    }



}
