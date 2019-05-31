package com.outsource.danding.qingdaoic.widget;

import android.content.Context;
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
import com.outsource.danding.qingdaoic.bean.Receipt;

import java.util.List;


public class ReceiptView extends LinearLayout implements OnItemIndexDelete{




    private EditText et_reimbName;
    private EditText et_number;
    private EditText et_amount;
    private LinearLayout ll_delete;
    private int mPosition;
    private Context mContext;
    private View mView;
    private List<Receipt> mReceipts;

    public ReceiptView(Context context, int position)  {
        super(context);
        mView=  LayoutInflater.from(context).inflate(R.layout.view_receipt,this);
        et_reimbName=findViewById(R.id.et_reimbName);
        et_number=findViewById(R.id.et_number);
        et_amount=findViewById(R.id.et_amount);


        ll_delete=findViewById(R.id.ll_delete);
        mPosition=position;
        mContext=context;
        initListener();
        init();
    }

    public ReceiptView(Context context, int position, AttributeSet attrs) {
        super(context, attrs);
        mView= LayoutInflater.from(context).inflate(R.layout.view_receipt,this);
        et_reimbName=findViewById(R.id.et_reimbName);
        et_number=findViewById(R.id.et_number);
        et_amount=findViewById(R.id.et_amount);
        ll_delete=findViewById(R.id.ll_delete);

        mPosition=position;
        mContext=context;
        initListener();
        init();
    }



    public void initListener(){


        et_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(mReceipts.get(mPosition)!=null)
                    mReceipts.get(mPosition).setAmount(et_amount.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        et_reimbName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(mReceipts.get(mPosition)!=null)
                    mReceipts.get(mPosition).setReimbName(et_reimbName.getText().toString());
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
                if(mReceipts.get(mPosition)!=null)
                {
                    mReceipts.get(mPosition).setNumber(et_number.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });



        //删除监听
        ll_delete.setOnClickListener(new OnClickListener() {
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

    public void setReimbName(String reimbName)
    {
        et_reimbName.setText(reimbName);
    }
    public void setNumber(String number)
    {
        et_number.setText(number);
    }
    public void setAmount(String amount)
    {
        et_amount.setText(amount);
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

    public void setDataSource(List<Receipt> receipts)
    {
        mReceipts=receipts;
    }



}
