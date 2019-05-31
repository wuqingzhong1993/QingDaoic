package com.outsource.danding.qingdaoic.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.outsource.danding.qingdaoic.OnItemIndexDelete;
import com.outsource.danding.qingdaoic.R;
import com.outsource.danding.qingdaoic.app.QdApplication;
import com.outsource.danding.qingdaoic.bean.AuditOffice;
import com.outsource.danding.qingdaoic.bean.City;
import com.outsource.danding.qingdaoic.bean.Province;
import com.outsource.danding.qingdaoic.bean.TravelInfo;

import java.util.List;


public class TravelInfoView extends LinearLayout implements OnItemIndexDelete{



    private RadioGroup rg_out_type;
    private Spinner sp_province;
    private Spinner sp_city;
    private Spinner sp_person;
    private TextView b_number;
    private EditText et_w_number;
    private RadioGroup rg_jt_tools;
    private EditText et_type;
    private LinearLayout ll_begin_date;
    private TextView tv_begin_date;
    private LinearLayout ll_end_date;
    private TextView tv_end_date;
    private RadioGroup rg_fh_tools;
    private RadioGroup rg_zs_jd;
    private RadioGroup rg_sendCar;
    private EditText et_fare;
    private EditText et_remarks;
    private EditText et_hotel;
    private EditText et_jt_money;
    private EditText et_zs_money;
    private EditText et_hs_money;
    private EditText et_other_fee;
    private TextView tv_levels;


    private TextView tv_delete;
    private int mPosition;
    private Context mContext;
    private View mView;
    private List<TravelInfo> mTravels;

    public TravelInfoView(Context context, int position)  {
        super(context);
        mView=  LayoutInflater.from(context).inflate(R.layout.view_travel_info,this);
        rg_out_type=findViewById(R.id.rg_out_type);
        sp_province=findViewById(R.id.sp_province);
        sp_city=findViewById(R.id.sp_city);
        sp_person=findViewById(R.id.sp_person);
        b_number=findViewById(R.id.b_number);
        et_w_number=findViewById(R.id.et_w_number);
        rg_jt_tools=findViewById(R.id.rg_jt_tools);
        et_type=findViewById(R.id.et_type);
        ll_begin_date=findViewById(R.id.ll_begin_date);
        tv_begin_date=findViewById(R.id.tv_begin_date);
        ll_end_date=findViewById(R.id.ll_end_date);
        tv_end_date=findViewById(R.id.tv_end_date);
        rg_fh_tools=findViewById(R.id.rg_fh_tools);
        rg_zs_jd=findViewById(R.id.rg_zs_jd);
        et_remarks=findViewById(R.id.et_remarks);
        rg_sendCar=findViewById(R.id.rg_sendCar);
        et_fare=findViewById(R.id.et_fare);
        et_hotel=findViewById(R.id.et_hotel);
        et_jt_money=findViewById(R.id.et_jt_money);
        et_zs_money=findViewById(R.id.et_zs_money);
        et_hs_money=findViewById(R.id.et_hs_money);
        et_other_fee=findViewById(R.id.et_other_fee);
        tv_levels=findViewById(R.id.tv_levels);
        tv_delete=findViewById(R.id.tv_delete);
        mPosition=position;
        mContext=context;
        initListener();
        init();
    }

    public TravelInfoView(Context context, int position, AttributeSet attrs) {
        super(context, attrs);
        mView= LayoutInflater.from(context).inflate(R.layout.view_travel_info,this);
        rg_out_type=findViewById(R.id.rg_out_type);
        sp_province=findViewById(R.id.sp_province);
        sp_city=findViewById(R.id.sp_city);
        sp_person=findViewById(R.id.sp_person);
        b_number=findViewById(R.id.b_number);
        et_w_number=findViewById(R.id.et_w_number);
        rg_jt_tools=findViewById(R.id.rg_jt_tools);
        et_type=findViewById(R.id.et_type);
        ll_begin_date=findViewById(R.id.ll_begin_date);
        tv_begin_date=findViewById(R.id.tv_begin_date);
        ll_end_date=findViewById(R.id.ll_end_date);
        tv_end_date=findViewById(R.id.tv_end_date);
        rg_fh_tools=findViewById(R.id.rg_fh_tools);
        rg_zs_jd=findViewById(R.id.rg_zs_jd);
        et_remarks=findViewById(R.id.et_remarks);
        rg_sendCar=findViewById(R.id.rg_sendCar);
        et_fare=findViewById(R.id.et_fare);
        et_hotel=findViewById(R.id.et_hotel);
        et_jt_money=findViewById(R.id.et_jt_money);
        et_zs_money=findViewById(R.id.et_zs_money);
        et_hs_money=findViewById(R.id.et_hs_money);
        et_other_fee=findViewById(R.id.et_other_fee);
        tv_levels=findViewById(R.id.tv_levels);
        mPosition=position;
        mContext=context;
        initListener();
        init();
    }



    public void initListener(){

        rg_out_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.spontaneous:
                        mTravels.get(mPosition).setType("01");
                        break;
                    case R.id.cultivate:
                        mTravels.get(mPosition).setType("02");
                        break;
                    case R.id.conference:
                        mTravels.get(mPosition).setType("03");
                        break;
                    case R.id.countrySide:
                        mTravels.get(mPosition).setType("04");
                        break;

                }
            }
        });

        rg_jt_tools.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.car_1:
                        mTravels.get(mPosition).setJt_tools_str("汽车");
                        break;
                    case R.id.train_1:
                        mTravels.get(mPosition).setJt_tools_str("火车");
                        break;
                    case R.id.plane_1:
                        mTravels.get(mPosition).setJt_tools_str("飞机");
                        break;

                }
            }
        });

        rg_fh_tools.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.car_2:
                        mTravels.get(mPosition).setFh_tools_str("汽车");
                        break;
                    case R.id.train_2:
                        mTravels.get(mPosition).setFh_tools_str("火车");
                        break;
                    case R.id.plane_2:
                        mTravels.get(mPosition).setFh_tools_str("飞机");
                        break;

                }
            }
        });

        rg_zs_jd.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.yes_2:
                        mTravels.get(mPosition).setZs_jd("0");
                        break;
                    case R.id.no_2:
                        mTravels.get(mPosition).setZs_jd("1");
                        break;

                }
            }
        });

        rg_sendCar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.send_car:
                        mTravels.get(mPosition).setSendCar("0");
                        break;
                    case R.id.go_alone:
                        mTravels.get(mPosition).setSendCar("1");
                        break;
                    case R.id.pickup_else:
                        mTravels.get(mPosition).setSendCar("2");
                        break;
                    case R.id.rent_car:
                        mTravels.get(mPosition).setSendCar("3");
                        break;

                }
            }
        });


        sp_province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mTravels.get(mPosition).setCity_town(QdApplication.getProvinces().get(position).getTownName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        sp_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mTravels.get(mPosition).setCity(QdApplication.getCities().get(position).getCity());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        et_hs_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(mTravels.get(mPosition)!=null)
                    mTravels.get(mPosition).setHs_money(et_hs_money.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_other_fee.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(mTravels.get(mPosition)!=null)
                    mTravels.get(mPosition).setQt_money(et_other_fee.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_remarks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(mTravels.get(mPosition)!=null)
                    mTravels.get(mPosition).setRemarks(et_remarks.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        et_zs_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(mTravels.get(mPosition)!=null)
                    mTravels.get(mPosition).setZs_money(et_zs_money.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });



        et_type.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(mTravels.get(mPosition)!=null)
                    mTravels.get(mPosition).setType(et_type.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        et_fare.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(mTravels.get(mPosition)!=null)
                    mTravels.get(mPosition).setFare(et_fare.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        et_hotel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(mTravels.get(mPosition)!=null)
                    mTravels.get(mPosition).setHt_money(et_hotel.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        et_jt_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(mTravels.get(mPosition)!=null)
                    mTravels.get(mPosition).setJt_money(et_jt_money.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });



        et_w_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(mTravels.get(mPosition)!=null)
                    mTravels.get(mPosition).setW_number(et_w_number.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        sp_person.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("","");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        et_w_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(mTravels.get(mPosition)!=null)
                    mTravels.get(mPosition).setW_number(et_w_number.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        et_type.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(mTravels.get(mPosition)!=null)
                    mTravels.get(mPosition).setType(et_type.getText().toString());
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

    public void setType(String type)
    {
        switch (type)
        {
            case "01":
                rg_out_type.check(R.id.spontaneous);
                break;
            case "02":
                rg_out_type.check(R.id.cultivate);
                break;
            case "03":
                rg_out_type.check(R.id.conference);
                break;
            case "04":
                rg_out_type.check(R.id.countrySide);
                break;
        }
    }

    public void setCityTown(String cityTown)
    {
        for(int i=0;i<QdApplication.getProvinces().size();i++)
        {
            Province province=QdApplication.getProvinces().get(i);
            if(province.getTownName().equals(cityTown))
            {
                sp_province.setSelection(i);
                break;
            }
        }
    }

    public void setCity(String city)
    {
        for(int i=0;i<QdApplication.getCities().size();i++)
        {
            City cityBean=QdApplication.getCities().get(i);
            if(cityBean.getCity().equals(city))
            {
                sp_city.setSelection(i);
                break;
            }
        }
    }

    public void setWNumber(String wNumber)
    {
        et_w_number.setText(wNumber);
    }

    public void setJtToolsStr(String jt_tools_str)
    {
        switch (jt_tools_str)
        {
            case "汽车":
                rg_jt_tools.check(R.id.car_1);
                break;
            case "火车":
                rg_jt_tools.check(R.id.train_1);
                break;
            case "飞机":
                rg_jt_tools.check(R.id.plane_1);
                break;
        }
    }

    public void setfhToolsStr(String fh_tools_str)
    {
        switch (fh_tools_str)
        {
            case "汽车":
                rg_fh_tools.check(R.id.car_2);
                break;
            case "火车":
                rg_fh_tools.check(R.id.train_2);
                break;
            case "飞机":
                rg_fh_tools.check(R.id.plane_2);
                break;
        }
    }

    public void setzsJd(String zs_jd)
    {
        switch (zs_jd)
        {
            case "0":
                rg_zs_jd.check(R.id.yes_2);
                break;
            case "1":
                rg_zs_jd.check(R.id.no_2);
                break;
        }
    }

    public void setRemarks(String remarks)
    {
        et_remarks.setText(remarks);
    }

    public void setSendCar(String sendCar)
    {
        switch (sendCar)
        {
            case "派车":
                rg_sendCar.check(R.id.send_car);
                break;
            case "自行前往":
                rg_sendCar.check(R.id.go_alone);
                break;
            case "乘坐其他单位车辆":
                rg_sendCar.check(R.id.pickup_else);
                break;
            case "租车":
                rg_sendCar.check(R.id.rent_car);
                break;
        }

    }

    public void setFare(String fare)
    {
        et_fare.setText(fare);
    }

    //旅馆费
    public void setHotel(String hotel)
    {
        et_hotel.setText(hotel);
    }

    public void setJtMoney(String jt_money)
    {
        et_jt_money.setText(jt_money);
    }

    public void setZsMoney(String zs_money)
    {
        et_zs_money.setText(zs_money);
    }

    //伙食费
    public void setHsMoney(String hs_money)
    {
        et_hs_money.setText(hs_money);
    }

    public void setOtherFee(String other_fee)
    {
        et_other_fee.setText(other_fee);
    }

    public void setLevels(String levels)
    {
        tv_levels.setText(levels);
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

    public void setDataSource(List<TravelInfo> travels)
    {
        mTravels=travels;
    }



}
