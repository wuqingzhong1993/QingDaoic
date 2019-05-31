package com.outsource.danding.qingdaoic.ui.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.outsource.danding.qingdaoic.R
import com.outsource.danding.qingdaoic.base.BaseActivity
import com.outsource.danding.qingdaoic.base.BaseListActivity
import com.outsource.danding.qingdaoic.base.FNAdapter
import com.outsource.danding.qingdaoic.bean.ApplyInfo
import com.outsource.danding.qingdaoic.net.HttpClient
import com.outsource.danding.qingdaoic.ui.fragment.BusinessApplyFragment
import com.outsource.danding.qingdaoic.ui.fragment.ConferenceApplyFragment
import com.outsource.danding.qingdaoic.ui.fragment.TravelApplyFragment
import com.outsource.danding.qingdaoic.widget.TabFragmentPagerAdapter
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_conference.*


class ConferenceActivity : BaseActivity() {


    private  lateinit var mTarget: View

    private var passIsShow = false

    private var fragments:MutableList<Fragment>?=null
    private var adapter: TabFragmentPagerAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conference)
        initView()
    }

    private fun initView() {
        title = "会议"
        fragments= mutableListOf(ConferenceApplyFragment(), ConferenceApplyFragment())
        adapter= TabFragmentPagerAdapter(supportFragmentManager,fragments)
        view_pager.adapter=adapter
        view_pager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                when(position)
                {
                    0->{

                        tv_unfinish.setTextColor(resources.getColor(R.color.colorTabTextSelect))
                        tv_done.setTextColor(resources.getColor(R.color.colorTabTextNormal))
                        view_unfinish.visibility=View.VISIBLE
                        view_done.visibility=View.INVISIBLE
                    }
                    1->{
                        tv_done.setTextColor(resources.getColor(R.color.colorTabTextSelect))
                        tv_unfinish.setTextColor(resources.getColor(R.color.colorTabTextNormal))
                        view_done.visibility=View.VISIBLE
                        view_unfinish.visibility=View.INVISIBLE
                    }
                    else->{
                    }
                }
            }
        })
        view_pager.setCurrentItem(0,true)
        initListener()
    }



    private fun initListener(){

        ll_unfinish.setOnClickListener {
            view_pager.setCurrentItem(0,true)
        }

        ll_done.setOnClickListener {
            view_pager.setCurrentItem(1,true)
        }
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