package com.outsource.danding.qingdaoic.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.EditText

import com.outsource.danding.qingdaoic.R;

//带清除功能的输入框
class ClearEditText : EditText {

    private lateinit var clearImg: Drawable


    constructor(context: Context?) : super(context) {
        init()
    }


    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) {
        init()
    }


    private fun init() {
        clearImg = context.getDrawable(R.drawable.ic_clear)
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (TextUtils.isEmpty(s)) {
                    //给textView上下左右四个方向添加drawable
                    setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
                } else {
                    setCompoundDrawablesWithIntrinsicBounds(null, null, clearImg, null)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
    }


    //给图片设置监听事件
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_UP) {
//            val eventX = event.rawX
//            val eventY = event.rawY
//            val rect = Rect()
//            //获取可见范围
//            getGlobalVisibleRect(rect)
//            rect.left = rect.right - 100 - paddingEnd
//            rect.right = rect.right - paddingEnd
//            if (rect.contains(eventX.toInt(), eventY.toInt())) {
//                setText("")
//            }
            val isClean = event.x > width - totalPaddingRight && event.x < width - paddingRight
            if (isClean) {
                setText("")
            }
        }
        return super.onTouchEvent(event)
    }


}
