package com.outsource.danding.qingdaoic.widget

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.LinearLayout
import com.outsource.danding.qingdaoic.R
import com.outsource.danding.qingdaoic.bean.Receipt



class ReceiptAdapter(context:Context,list:MutableList<Receipt>): BaseAdapter() {

    var mContext:Context?=null

    var mList=list

    init{
        mContext=context
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var viewHolder: ViewHolder
        var view: View

        if (convertView == null) {
            view = View.inflate(mContext, R.layout.adapter_receipt, null)
            viewHolder = ViewHolder(view)
            view.setTag(viewHolder)
        } else {
            view = convertView
            viewHolder = view.getTag() as ViewHolder
        }

        viewHolder.et_reimbName.setText( mList[position].reimbName )
        viewHolder.et_number.setText(mList[position].number)
        viewHolder.et_amount.setText(mList[position].amount)
        viewHolder.et_reimbName.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if( viewHolder.et_reimbName.text.toString()!="")
                {
                    mList[position].reimbName=viewHolder.et_reimbName.text.toString()
                }
            }

        })
        viewHolder.ll_delete.setOnClickListener {
            mList.removeAt(position)
            notifyDataSetChanged()
        }



        return view
    }

    override fun getItem(position: Int): Receipt {
       return mList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {

        return mList.size
    }

    class ViewHolder(var v: View) {
        var et_reimbName: EditText = v.findViewById(R.id.et_reimbName)
        var et_number:EditText= v.findViewById(R.id.et_number)
        var et_amount:EditText=v.findViewById(R.id.et_amount)
        var ll_delete:LinearLayout=v.findViewById(R.id.ll_delete)
    }
}