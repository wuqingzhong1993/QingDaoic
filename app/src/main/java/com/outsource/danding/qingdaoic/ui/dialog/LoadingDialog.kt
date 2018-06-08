package com.outsource.danding.qingdaoic.ui.dialog

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import com.bumptech.glide.Glide

import com.outsource.danding.qingdaoic.R;
import kotlinx.android.synthetic.main.dialog_loading.*

class LoadingDialog : AlertDialog {


    constructor(context: Context?) : super(context)
    constructor(context: Context?, cancelable: Boolean, cancelListener: DialogInterface.OnCancelListener?) : super(context, cancelable, cancelListener)
    constructor(context: Context?, themeResId: Int) : super(context, themeResId)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_loading)
        setCanceledOnTouchOutside(false)

    }

    override fun show() {
        super.show()
        Glide.with(context).load(R.drawable.img_loading).into(iv_loading)
    }


}