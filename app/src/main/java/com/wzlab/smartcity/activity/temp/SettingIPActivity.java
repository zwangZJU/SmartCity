package com.wzlab.smartcity.activity.temp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wzlab.smartcity.activity.R;

public class SettingIPActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_ip);
        final EditText mEtIP = findViewById(R.id.et_setting_ip);
        Button mBtnSettingIP = findViewById(R.id.btn_setting_ip);
        mBtnSettingIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ip = mEtIP.getText().toString().trim();
                SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
                editor.putString("IP",ip);
                editor.apply();
                Toast.makeText(SettingIPActivity.this,"保存完毕",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
