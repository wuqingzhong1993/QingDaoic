package com.outsource.danding.qingdaoic.ui.activity


import android.content.Intent
import android.os.Bundle
import com.outsource.danding.qingdaoic.R
import com.outsource.danding.qingdaoic.base.BaseActivity
import kotlinx.android.synthetic.main.activity_basic_info.*
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat



class BasicInfoActivity : BaseActivity() {

    private var passIsShow = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic_info)
        initView()
    }

    private fun initView() {

        initListener()
    }

    private fun initListener() {
        ll_country.setOnClickListener{
            val options = ActivityOptionsCompat.makeScaleUpAnimation(country, //The View that the new activity is animating from
                    country.width as Int / 2, country.height as Int / 2, //拉伸开始的坐标
                    0, 0)//拉伸开始的区域大小，这里用（0，0）表示从无到全屏
            intent = Intent(this,DetailActivity::class.java)
            ActivityCompat.startActivity (this,intent,options.toBundle())
        }
    }
}
