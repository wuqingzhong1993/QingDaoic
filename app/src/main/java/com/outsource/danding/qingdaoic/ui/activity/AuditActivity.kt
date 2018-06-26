package com.outsource.danding.qingdaoic.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.View
import com.outsource.danding.qingdaoic.R
import com.outsource.danding.qingdaoic.base.BaseActivity
import com.outsource.danding.qingdaoic.ui.fragment.AuditApplyFragment
import com.outsource.danding.qingdaoic.ui.fragment.AuditHistoryFragment
import com.outsource.danding.qingdaoic.widget.TabFragmentPagerAdapter
import kotlinx.android.synthetic.main.activity_audit.*

class AuditActivity : BaseActivity() {


    private  lateinit var mTarget: View

    private var passIsShow = false

    private var fragments:MutableList<Fragment>?=null
    private var adapter:TabFragmentPagerAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audit)
        initView()
    }

    private fun initView(){

        fragments= mutableListOf(AuditApplyFragment(),AuditHistoryFragment())
        adapter= TabFragmentPagerAdapter(supportFragmentManager,fragments)
        view_pager.adapter=adapter
        view_pager.addOnPageChangeListener(object:ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                when(position)
                {
                    0->{

                        tv_audit_list.setTextColor(resources.getColor(R.color.colorTabTextSelect))
                        tv_audit_history.setTextColor(resources.getColor(R.color.colorTabTextNormal))
                        view_audit_list.visibility=View.VISIBLE
                        view_audit_history.visibility=View.INVISIBLE
                    }
                    1->{
                        tv_audit_history.setTextColor(resources.getColor(R.color.colorTabTextSelect))
                        tv_audit_list.setTextColor(resources.getColor(R.color.colorTabTextNormal))
                        view_audit_history.visibility=View.VISIBLE
                        view_audit_list.visibility=View.INVISIBLE
                    }
                    else->{
                    }
                }
            }
        })
        view_pager.setCurrentItem(0,true)

        initListener()
    }

    private fun initListener() {

        ll_audit_list.setOnClickListener {
            view_pager.setCurrentItem(0,true)
        }

        ll_audit_history.setOnClickListener {
            view_pager.setCurrentItem(1,true)
        }
    }


}
