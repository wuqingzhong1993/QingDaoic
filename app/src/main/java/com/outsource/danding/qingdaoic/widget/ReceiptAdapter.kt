package com.outsource.danding.qingdaoic.widget

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.outsource.danding.qingdaoic.R
import com.outsource.danding.qingdaoic.bean.Receipt



class ReceiptAdapter(context:Context,list:MutableList<Receipt>): BaseAdapter() {

    var mContext:Context?=null

    var mList=list
    private var number:Int=0
    private var amount:Int=0
    private var dirty:Boolean=false

    init{
        mContext=context
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var viewHolder: ViewHolder
        var view: View

        if (convertView == null) {
            view = View.inflate(mContext, R.layout.adapter_receipt, null)
            viewHolder = ViewHolder(view)
            view.setTag( viewHolder)

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



        viewHolder.et_number.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                Log.d("",s.toString())
                try{
                    if(s.toString().toInt()!=number)
                    {
                        number=s.toString().toInt()
                        notifyDataSetChanged()
                        dirty=true
                        viewHolder.et_number.postDelayed(Runnable {

                            viewHolder.et_number.setSelection(s.toString().length)
                            //viewHolder.et_number.requestFocus()
                        },200)
                    }
                }catch (e:Exception)
                {
                    e.printStackTrace()
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if(viewHolder.et_number.text.toString()!="")
                {
                    number=viewHolder.et_number.text.toString().toInt()
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if( viewHolder.et_number.text.toString()!="")
                {
                    mList[position].number=viewHolder.et_number.text.toString()
                    try{
                        val newNumber= viewHolder.et_number.text.toString().toInt()
                        if(newNumber==viewHolder.number)
                        {
                        }else{

                            if(viewHolder.et_number.getTag()!=null)
                            {

                            }else{

                            }
                        }
                    }catch (e:Exception)
                    {
                        e.printStackTrace()
                    }
                }
            }

        })

        viewHolder.et_amount.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if( viewHolder.et_amount.text.toString()!="")
                {
                    mList[position].amount=viewHolder.et_amount.text.toString()
                    try{
                        val newAmount= viewHolder.et_amount.text.toString().toInt()
                        if(viewHolder.amount==newAmount)
                        {
                        }else{
                            if(mContext is ReceiptAdapter.OnNotifyListener)
                            {

                                val callback=mContext as ReceiptAdapter.OnNotifyListener
                                //view.setTag("amount","")
                                callback.onNotify(null,null)
                            }else{
                                throw ClassCastException(mContext.toString() + " must implement OnDeleteSetListener")
                            }
                        }
                    }catch (e:Exception)
                    {
                        e.printStackTrace()
                    }
                }
            }

        })


        viewHolder.ll_delete.setOnClickListener {
            mList.removeAt(position)
            notifyDataSetChanged()
            if(mContext is ReceiptAdapter.OnDeleteListener)
            {
                val callback=mContext as ReceiptAdapter.OnDeleteListener
                callback.onDelete()
            }else{
                throw ClassCastException(mContext.toString() + " must implement OnDeleteSetListener")
            }
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
        var number:Int=0
        var amount:Int=0
    }

    /**
     * 删除回调
     */
    interface OnDeleteListener
    {
        fun onDelete()
    }



    /**
     * 增加回调
     */
    interface OnNotifyListener
    {
        fun onNotify(number:Int?,amount:Int?)
    }


}