package com.outsource.danding.qingdaoic.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.bumptech.glide.Glide
import com.outsource.danding.qingdaoic.R;
import com.outsource.danding.qingdaoic.base.BaseActivity
import com.outsource.danding.qingdaoic.util.GlideImageLoader
import com.outsource.danding.qingdaoic.util.UIUtils
import com.outsource.danding.qingdaoic.util.Utils
import com.youth.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private var images= mutableListOf(R.drawable.banner,R.drawable.banner,R.drawable.banner)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initListener()
    }

    private fun initView() {
        //开始轮播
        banner.setImages(images).setImageLoader(GlideImageLoader()).start()
    }


    private fun initListener() {


    }
}
