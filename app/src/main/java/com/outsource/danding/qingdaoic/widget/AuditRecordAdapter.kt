package com.outsource.danding.qingdaoic.widget

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.outsource.danding.qingdaoic.R
import com.outsource.danding.qingdaoic.bean.AuditRecord

class AuditRecordAdapter(context: Context, list:MutableList<AuditRecord>): BaseAdapter() {

    var mContext: Context?=null

    var mList=list

    init{
        mContext=context
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var viewHolder: ViewHolder
        var view: View

        if (convertView == null) {
            view = View.inflate(mContext, R.layout.adapter_audit_record, null)
            viewHolder = ViewHolder(view)
            view.setTag(viewHolder)
        } else {
            view = convertView
            viewHolder = view.getTag() as ViewHolder
        }

        viewHolder.tv_createName.text= mList[position].createName
        viewHolder.tv_result.text=mList[position].result
        viewHolder.tv_create_time.text=mList[position].createTime
        viewHolder.img_status.bringToFront()
        if(mList[position].audit_opinion!="")
            viewHolder.tv_auditOpinion.text=mList[position].audit_opinion
        else
            viewHolder.tv_auditOpinion.visibility=View.GONE


        if(position==0)
            viewHolder.up_timeLine.visibility=View.INVISIBLE
        if(position==count-1)
            viewHolder.down_timeLine.visibility=View.INVISIBLE

        if(mList[position].result!="通过")
            viewHolder.img_status.setImageResource(R.drawable.ic_adjust_black_32)

        return view
    }

    override fun getItem(position: Int): AuditRecord {
       return mList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {

        return mList.size
    }

    class ViewHolder(var v: View) {
        val tv_createName: TextView = v.findViewById(R.id.tv_createName)
        val tv_result: TextView = v.findViewById(R.id.tv_result)
        val tv_create_time: TextView =v.findViewById(R.id.tv_create_time)
        val up_timeLine:View=v.findViewById(R.id.up_timeLine)
        var down_timeLine:View=v.findViewById(R.id.down_timeLine)
        var img_status:ImageView=v.findViewById(R.id.img_status)
        var tv_auditOpinion:TextView=v.findViewById(R.id.tv_auditOpinion)


    }
}