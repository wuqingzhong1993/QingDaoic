package com.outsource.danding.qingdaoic.widget

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.outsource.danding.qingdaoic.R
import com.outsource.danding.qingdaoic.bean.AuditOffice
import com.outsource.danding.qingdaoic.bean.AuditTravel

class AuditTravelAdapter(context: Context, list:MutableList<AuditTravel>): BaseAdapter() {

    var mContext: Context?=null

    var mList=list

    init{
        mContext=context
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var viewHolder: ViewHolder
        var view: View

        if (convertView == null) {
            view = View.inflate(mContext, R.layout.adapter_audit_travel, null)
            val v:TextView=view.findViewById(R.id.type)
            viewHolder = ViewHolder(view)
            view.setTag(viewHolder)
        } else {
            view = convertView
            viewHolder = view.getTag() as ViewHolder
        }

        viewHolder.tv_type.text= mList[position].type
        viewHolder.tv_city.text=mList[position].city
        viewHolder.tv_isUserCar.text=mList[position].isUserCar
        viewHolder.tv_persons.text=mList[position].persons
        viewHolder.tv_b_number.text=mList[position].b_number
        viewHolder.tv_w_number.text=mList[position].w_number
        viewHolder.tv_content.text=mList[position].content
        viewHolder.tv_go_time.text=mList[position].go_time
        viewHolder.tv_back_time.text=mList[position].back_time
        viewHolder.tv_jt_tools_str.text=mList[position].jt_tools_str
        viewHolder.tv_fh_tools_str.text=mList[position].fh_tools_str
        if(mList[position].zs_jd=="0")
            viewHolder.tv_zs_jd.text="是"
        else
            viewHolder.tv_zs_jd.text="否"
        viewHolder.tv_days.text=mList[position].days
        viewHolder.tv_remarks.text=mList[position].remarks
        viewHolder.tv_fare.text=mList[position].fare
        viewHolder.tv_ht_money.text=mList[position].ht_money
        viewHolder.tv_jt_money.text=mList[position].jt_money
        viewHolder.tv_zs_money.text=mList[position].zs_money
        viewHolder.tv_hs_money.text=mList[position].hs_money
        viewHolder.tv_qt_money.text=mList[position].qt_money
        viewHolder.tv_all_money.text=mList[position].all_money



        return view
    }

    override fun getItem(position: Int): AuditTravel {
       return mList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {

        return mList.size
    }

    class ViewHolder(var v: View) {
        val tv_type: TextView = v.findViewById(R.id.type)
        val tv_city:TextView=v.findViewById(R.id.tv_city)
        val tv_isUserCar:TextView=v.findViewById(R.id.tv_isUserCar)
        val tv_persons:TextView=v.findViewById(R.id.tv_persons)
        val tv_b_number:TextView=v.findViewById(R.id.tv_b_number)
        val tv_w_number:TextView=v.findViewById(R.id.tv_w_number)
        val tv_content:TextView=v.findViewById(R.id.tv_content)
        val tv_go_time:TextView=v.findViewById(R.id.tv_go_time)
        val tv_back_time:TextView=v.findViewById(R.id.tv_back_time)
        val tv_jt_tools_str:TextView=v.findViewById(R.id.tv_jt_tools_str)
        val tv_fh_tools_str:TextView=v.findViewById(R.id.tv_fh_tools_str)
        val tv_zs_jd:TextView=v.findViewById(R.id.tv_zs_jd)
        val tv_days:TextView=v.findViewById(R.id.tv_days)
        val tv_remarks:TextView=v.findViewById(R.id.tv_remarks)
        val tv_fare:TextView=v.findViewById(R.id.tv_fare)
        val tv_ht_money:TextView=v.findViewById(R.id.tv_ht_money)
        val tv_jt_money:TextView=v.findViewById(R.id.tv_jt_money)
        val tv_zs_money:TextView=v.findViewById(R.id.tv_zs_money)
        val tv_hs_money:TextView=v.findViewById(R.id.tv_hs_money)
        val tv_qt_money:TextView=v.findViewById(R.id.tv_qt_money)
        val tv_all_money:TextView=v.findViewById(R.id.tv_all_money)


    }
}