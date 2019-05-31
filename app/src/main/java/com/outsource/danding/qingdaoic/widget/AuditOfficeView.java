package com.outsource.danding.qingdaoic.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.outsource.danding.qingdaoic.OnItemIndexDelete;
import com.outsource.danding.qingdaoic.R;
import com.outsource.danding.qingdaoic.bean.AuditOffice;


import java.util.List;


public class AuditOfficeView extends LinearLayout implements OnItemIndexDelete{




    private EditText et_name;
    private EditText et_standard;
    private EditText et_univalent;
    private EditText et_number;
    private EditText et_money;
    private EditText et_remarks;
    private TextView tv_delete;
    private int mPosition;
    private Context mContext;
    private View mView;
    private List<AuditOffice> mOffices;

    public AuditOfficeView(Context context,int position)  {
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
        mContext=context;
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
        mContext=context;
        initListener();
        init();
    }



    public void initListener(){


        et_remarks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(mOffices.get(mPosition)!=null)
                    mOffices.get(mPosition).setRemarks(et_remarks.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(mOffices.get(mPosition)!=null)
                    mOffices.get(mPosition).setName(et_name.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        et_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(mOffices.get(mPosition)!=null)
                {
                    mOffices.get(mPosition).setNumber(et_number.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        et_standard.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(mOffices.get(mPosition)!=null)
                    mOffices.get(mPosition).setStandard(et_standard.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        et_univalent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(mOffices.get(mPosition)!=null)
                    mOffices.get(mPosition).setUnivalent(et_univalent.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //删除监听
        tv_delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    OnItemDelete callback = (OnItemDelete) mContext;
                    callback.onDelete(mPosition);
                } catch (ClassCastException e) {
                    throw new ClassCastException(mContext.toString() + " must implement OnDateSetListener");
                }
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



    @Override
    public void onItemIndexDelete(int position) {
        if(mPosition>position)
            mPosition--;
    }


    /**
     * 删除接口回调
     */
    public interface OnItemDelete{
        // method to be implemented in activity
        void onDelete(int position);
    }

    /**
     * 设置position
     */
    public void setPosition(int position)
    {
        this.mPosition=position;
    }

    public void setDataSource(List<AuditOffice> offices)
    {
        mOffices=offices;
    }



}
