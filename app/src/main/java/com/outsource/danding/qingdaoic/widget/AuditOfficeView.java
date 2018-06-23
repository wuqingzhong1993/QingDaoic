package com.outsource.danding.qingdaoic.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.outsource.danding.qingdaoic.R;


public class AuditOfficeView extends LinearLayout {




    private EditText et_name;
    private EditText et_standard;
    private EditText et_univalent;
    private EditText et_number;
    private EditText et_money;
    private EditText et_remarks;
    private TextView tv_delete;
    private int mPosition;
    private View mView;

    public AuditOfficeView(Context context,int position) {
        super(context);
        mView=  LayoutInflater.from(context).inflate(R.layout.view_apply_office,this);
        et_name=findViewById(R.id.et_name);
        et_standard=findViewById(R.id.et_standard);
        et_univalent=findViewById(R.id.et_univalent);
        et_number=findViewById(R.id.et_number);
        et_money=findViewById(R.id.et_money);
        et_remarks=findViewById(R.id.et_remarks);
        tv_delete=findViewById(R.id.tv_delete);
        mPosition=position;
        initListener();
        init();
    }

    public AuditOfficeView(Context context, int position, AttributeSet attrs) {
        super(context, attrs);
        mView= LayoutInflater.from(context).inflate(R.layout.view_apply_office,this);
        et_name=findViewById(R.id.et_name);
        et_standard=findViewById(R.id.et_standard);
        et_univalent=findViewById(R.id.et_univalent);
        et_number=findViewById(R.id.et_number);
        et_money=findViewById(R.id.et_money);
        et_remarks=findViewById(R.id.et_remarks);
        mPosition=position;
        initListener();
        init();
    }



    public void initListener(){


        //删除监听
        tv_delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void init(){
        setWillNotDraw(false);
    }


    /**
     * 设置数据
     */

    public void setName(String name)
    {
        et_name.setText(name);
    }
    public void setMoney(String money)
    {
        et_money.setText(money);
    }
    public void setStandard(String standard)
    {
        et_standard.setText(standard);
    }
    public void setNumber(String number)
    {
        et_number.setText(number);
    }
    public void setUnivalent(String univalent)
    {
        et_univalent.setText(univalent);
    }
    public void setRemarks(String remarks)
    {
        et_remarks.setText(remarks);
    }







}
