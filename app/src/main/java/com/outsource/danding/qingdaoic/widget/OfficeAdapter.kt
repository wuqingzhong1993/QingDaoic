package com.outsource.danding.qingdaoic.widget

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.outsource.danding.qingdaoic.R
import com.outsource.danding.qingdaoic.bean.BusinessOffice
import com.outsource.danding.qingdaoic.bean.Receipt

class OfficeAdapter(context: Context, list:MutableList<BusinessOffice>): BaseAdapter() {

    var mContext: Context?=null

    var mList=list
    var number:Int?=null
    var univalent:Int?=null

    init{
        mContext=context
    }

    var listener:OnDeleteListener ?=null

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var viewHolder: ViewHolder
        var view: View

        if (convertView == null) {
            view = View.inflate(mContext, R.layout.adapter_office, null)
            viewHolder = ViewHolder(view)
            view.setTag(viewHolder)
        } else {
            view = convertView
            viewHolder = view.getTag() as ViewHolder
        }

        viewHolder.et_name.setText( mList[position].name )
        viewHolder.et_standard.setText(mList[position].standard)
        viewHolder.et_univalent.setText(mList[position].univalent)
        viewHolder.et_number.setText(mList[position].number)
        viewHolder.et_money.setText(mList[position].money)
        viewHolder.et_remarks.setText(mList[position].remarks)

        viewHolder.et_name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if( viewHolder.et_name.text.toString()!="")
                {
                    mList[position].name=viewHolder.et_name.text.toString()
                }
            }

        })

        viewHolder.et_standard.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if( viewHolder.et_standard.text.toString()!="")
                {
                    mList[position].standard=viewHolder.et_standard.text.toString()
                }
            }

        })


        viewHolder.et_univalent.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if( viewHolder.et_univalent.text.toString()!="")
                {
                    mList[position].univalent=viewHolder.et_univalent.text.toString()
                    try{
                        univalent= viewHolder.et_univalent.text.toString().toInt()
                        if(number!=null)
                        {
                            mList[position].money=(univalent!!*number!!).toString()
                            viewHolder.et_money.setText((univalent!!*number!!).toString())
                        }
                    }catch (e:Exception)
                    {
                        e.printStackTrace()
                    }
                }else{
                    if( viewHolder.et_money.text.toString()!="")
                        viewHolder.et_money.setText("")
                }
            }

        })

        viewHolder.et_number.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if( viewHolder.et_number.text.toString()!="")
                {
                    mList[position].number=viewHolder.et_number.text.toString()
                    try{
                        number= viewHolder.et_number.text.toString().toInt()
                        if(univalent!=null)
                        {
                            mList[position].money=(univalent!!*number!!).toString()
                            viewHolder.et_money.setText((univalent!!*number!!).toString())
                        }
                    }catch (e:Exception)
                    {
                        e.printStackTrace()
                    }

                }else{
                    if( viewHolder.et_money.text.toString()!="")
                        viewHolder.et_money.setText("")
                }
            }

        })

        viewHolder.et_money.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if( viewHolder.et_money.text.toString()!="")
                {
                    mList[position].money=viewHolder.et_money.text.toString()
                }
            }

        })

        viewHolder.et_remarks.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if( viewHolder.et_remarks.text.toString()!="")
                {
                    mList[position].remarks=viewHolder.et_remarks.text.toString()
                }
            }

        })



        viewHolder.tv_delete.setOnClickListener {
            mList.removeAt(position)
            notifyDataSetChanged()
            if(mContext is OnDeleteListener)
            {
                val callback=mContext as OnDeleteListener
                callback.onDelete()
            }else{
                throw ClassCastException(mContext.toString() + " must implement OnDeleteSetListener")
            }

        }

        return view
    }

    override fun getItem(position: Int): BusinessOffice {
       return mList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {

        return mList.size
    }

    class ViewHolder(var v: View) {
        var et_name: EditText = v.findViewById(R.id.et_name)
        var et_standard: EditText = v.findViewById(R.id.et_standard)
        var et_univalent: EditText =v.findViewById(R.id.et_univalent)
        var et_number: EditText =v.findViewById(R.id.et_number)
        var et_money:EditText=v.findViewById(R.id.et_money)
        var et_remarks:EditText=v.findViewById(R.id.et_remarks)
        val tv_delete:TextView=v.findViewById(R.id.tv_delete)

    }

    /**
     * 删除回调
     */
    interface OnDeleteListener
    {
        fun onDelete()
    }


}