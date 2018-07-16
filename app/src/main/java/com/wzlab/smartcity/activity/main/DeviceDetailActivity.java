package com.wzlab.smartcity.activity.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Switch;
import android.widget.TextView;


import com.wzlab.smartcity.activity.R;
import com.wzlab.smartcity.widget.ColorfulProgressBar;

public class DeviceDetailActivity extends AppCompatActivity {

    private TextView mTvDeviceId;
    private TextView mTvAddr;
    private TextView mTvHeadName;
    private TextView mTvHeadPhone;
    private TextView mTvPoliceStation;
    private Switch mSwitchDeviceStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        setContentView(R.layout.activity_device_detail);
        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String user_id = bundle.getString("user_id","暂无数据");
        String user_address = bundle.getString("user_address","暂无数据");
        String head = bundle.getString("head","暂无数据");
        String head_phone = bundle.getString("head_phone","暂无数据");
        String police_station = bundle.getString("police_station","暂无数据");
        mTvDeviceId.setText(user_id);
        mTvAddr.setText("定位信息:" + user_address);
        mTvHeadName.setText("姓名：" + head);
        mTvHeadPhone.setText("联系电话：" + head_phone);
        mTvPoliceStation.setText("工作单位：" + police_station);
        String deviceStatus = bundle.getString("device_status","1");
        if(deviceStatus.equals("1")){
             mSwitchDeviceStatus.setChecked(true);
        }else {
            mSwitchDeviceStatus.setChecked(false);
        }
        mSwitchDeviceStatus.setEnabled(false);
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.tb_device_detail);
        toolbar.setTitle("设备详情");
        mTvDeviceId = findViewById(R.id.tv_device_num);
        mTvAddr = findViewById(R.id.tv_device_location);
        mTvHeadName = findViewById(R.id.tv_principal_name);
        mTvHeadPhone = findViewById(R.id.tv_principal_phone);
        mTvPoliceStation = findViewById(R.id.tv_principal_police);
        mSwitchDeviceStatus = findViewById(R.id.switch_device_status);



    }
}
