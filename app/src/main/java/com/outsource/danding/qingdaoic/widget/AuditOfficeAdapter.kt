package com.outsource.danding.qingdaoic.widget

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
import com.outsource.danding.qingdaoic.bean.AuditOffice
import com.outsource.danding.qingdaoic.bean.Receipt

class AuditOfficeAdapter(context: Context, list:MutableList<AuditOffice>): BaseAdapter() {

    var mContext: Context?=null

    var mList=list

    init{
        mContext=context
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var viewHolder: ViewHolder
        var view: View

        if (convertView == null) {
            view = View.inflate(mContext, R.layout.adapter_audit_office, null)
            viewHolder = ViewHolder(view)
            view.setTag(viewHolder)
        } else {
            view = convertView
            viewHolder = view.getTag() as ViewHolder
        }

        viewHolder.tv_name.setText( mList[position].name )
        viewHolder.tv_money.setText(mList[position].money)
        viewHolder.tv_number.setText(mList[position].number)
        viewHolder.tv_standard.setText(mList[position].standard)
        viewHolder.tv_univalent.setText(mList[position].univalent)

        return view
    }

    override fun getItem(position: Int): AuditOffice {
       return mList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {

        return mList.size
    }

    class ViewHolder(var v: View) {
        val tv_name: TextView = v.findViewById(R.id.tv_name)
        val tv_standard: TextView = v.findViewById(R.id.tv_standard)
        val tv_univalent: TextView =v.findViewById(R.id.tv_univalent)
        val tv_number:TextView=v.findViewById(R.id.tv_number)
        val tv_money: TextView =v.findViewById(R.id.tv_money)
    }
}