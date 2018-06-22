package com.outsource.danding.qingdaoic.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.outsource.danding.qingdaoic.R;


public class AuditOfficeView extends LinearLayout {

    private TextView tv_name;
    private TextView tv_standard;
    private TextView tv_univalent;
    private TextView tv_number;
    private TextView tv_money;

    public AuditOfficeView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.adapter_audit_office,this);
        tv_name=findViewById(R.id.tv_name);
        tv_standard=findViewById(R.id.tv_standard);
        tv_univalent=findViewById(R.id.tv_univalent);
        tv_number=findViewById(R.id.tv_number);
        tv_money=findViewById(R.id.tv_money);
        initListener();
    }

    public AuditOfficeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.adapter_audit_office,this);
        tv_name=findViewById(R.id.tv_name);
        tv_standard=findViewById(R.id.tv_standard);
        tv_univalent=findViewById(R.id.tv_univalent);
        tv_number=findViewById(R.id.tv_number);
        tv_money=findViewById(R.id.tv_money);
    }

    public void initListener(){

    }

    /**
     * 设置数据
     */

    public void setName(String name)
    {
        tv_name.setText(name);
    }
    public void setMoney(String money)
    {
        tv_money.setText(money);
    }
    public void setStandard(String standard)
    {
        tv_standard.setText(standard);
    }
    public void setNumber(String number)
    {
        tv_number.setText(number);
    }
    public void setUnivalent(String univalent)
    {
        tv_univalent.setText(univalent);
    }







}
