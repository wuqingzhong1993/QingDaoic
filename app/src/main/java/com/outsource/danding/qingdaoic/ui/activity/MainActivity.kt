package com.outsource.danding.qingdaoic.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.bumptech.glide.Glide
import com.outsource.danding.qingdaoic.R;
import com.outsource.danding.qingdaoic.base.BaseActivity
import com.outsource.danding.qingdaoic.base.BaseTabActivity
import com.outsource.danding.qingdaoic.ui.fragment.AuditFragment
import com.outsource.danding.qingdaoic.ui.fragment.HomeFragment
import com.outsource.danding.qingdaoic.ui.fragment.MineFragment
import com.outsource.danding.qingdaoic.util.GlideImageLoader
import com.outsource.danding.qingdaoic.util.UIUtils
import com.outsource.danding.qingdaoic.util.Utils
import com.youth.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_title_bar.*

class MainActivity : BaseTabActivity() {


    override fun getFragmentClasses()= arrayOf(HomeFragment::class.java,AuditFragment::class.java,MineFragment::class.java)

    override fun getStrings(): Array<String> = arrayOf("申请","审核","我的")

    override fun getIcons(): IntArray = intArrayOf(R.drawable.bg_tab_apply,R.drawable.bg_tab_audit,R.drawable.bg_tab_mine)

    private var images= mutableListOf(R.drawable.banner,R.drawable.banner,R.drawable.banner)

    override fun getTextColor(): Int = R.color.color_tab_text

    override fun firstPosition(): Int = 0

    override fun setContentView(layoutResID: Int) {
        super.setContentView(R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        initListener()
    }

    override fun isTabCanClick(position: Int): Boolean =
            when (position) {

                0 ->{
                    true
                }
                1->{
                    true
                }
                2->{
                    true
                }
                else -> {
                    true
                }
            }

    override fun onTabSelected(position: Int) {
        super.onTabSelected(position)
        iv_start.visibility = View.GONE
        iv_end.visibility = View.GONE
        tv_end.visibility = View.GONE
        toolbar.visibility = View.VISIBLE
        when (position) {
            0 -> {
                title = getString(R.string.tab_home)
                toolbar.visibility = View.GONE
            }
            1->{
                title=getString(R.string.tab_audit)
                toolbar.visibility = View.GONE

            }
            2 ->{
                title= getString(R.string.tab_mine)
                toolbar.visibility=View.GONE
            }
            else -> {

            }
        }
    }





    private fun initListener() {


    }
}
