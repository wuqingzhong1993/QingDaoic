package com.outsource.danding.qingdaoic.ui.fragment

import android.content.Intent
import android.os.Bundle
import com.outsource.danding.qingdaoic.R
import com.outsource.danding.qingdaoic.base.BaseFragment
import com.outsource.danding.qingdaoic.ui.activity.AuditWaitingActivity
import com.outsource.danding.qingdaoic.util.GlideImageLoader
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment:BaseFragment() {

    private var images= mutableListOf(R.drawable.banner,R.drawable.banner,R.drawable.banner)

    override fun setLayoutId(): Int = R.layout.fragment_home


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initListener()
    }

    private fun initView(){
        banner.setImages(images).setImageLoader(GlideImageLoader()).start()
    }

    private fun initListener(){
        audit_wait.setOnClickListener {
            val intent:Intent=Intent(this.activity,AuditWaitingActivity::class.java)
            activity?.startActivityForResult(intent,0)
        }
    }


}