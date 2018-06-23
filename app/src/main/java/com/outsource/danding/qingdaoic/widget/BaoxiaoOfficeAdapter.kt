package com.outsource.danding.qingdaoic.widget

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.outsource.danding.qingdaoic.R
import com.outsource.danding.qingdaoic.bean.BaoxiaoOffice

class BaoxiaoOfficeAdapter(context: Context, list:MutableList<BaoxiaoOffice>): BaseAdapter() {

    var mContext: Context?=null

    var mList=list

    init{
        mContext=context
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var viewHolder: ViewHolder
        var view: View

        if (convertView == null) {
            view = View.inflate(mContext, R.layout.adapter_baoxiao_office, null)
            viewHolder = ViewHolder(view)
            view.setTag(viewHolder)
        } else {
            view = convertView
            viewHolder = view.getTag() as ViewHolder
        }

        viewHolder.tv_reimbName.setText( mList[position].reimbName )
        viewHolder.tv_amount.setText(mList[position].amount)
        viewHolder.tv_number.setText(mList[position].number)


        return view
    }

    override fun getItem(position: Int): BaoxiaoOffice {
       return mList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {

        return mList.size
    }

    class ViewHolder(var v: View) {
        val tv_reimbName: TextView = v.findViewById(R.id.tv_reimbName)
        val tv_number: TextView = v.findViewById(R.id.tv_number)
        val tv_amount: TextView =v.findViewById(R.id.tv_amount)
    }
}