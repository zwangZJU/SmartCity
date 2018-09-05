package com.wzlab.smartcity.activity.main;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.skateboard.zxinglib.CaptureActivity;
import com.wzlab.smartcity.R;
import com.wzlab.smartcity.activity.account.Config;
import com.wzlab.smartcity.net.HttpMethod;
import com.wzlab.smartcity.net.NetConnection;

import org.json.JSONException;
import org.json.JSONObject;

public class HandleActivity extends AppCompatActivity {


    private Button mBtnsubmit;
   private EditText mEtdetail;

    private String phone;
    private String id;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        setContentView(R.layout.activity_handle);
        phone = Config.getCachedPhone(getApplicationContext());
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        type = intent.getStringExtra("type");

        Toolbar toolbar = findViewById(R.id.toolbar_handle);
        if(type.equals("1")){
            toolbar.setTitle("上传维修结果");
        }else{
            toolbar.setTitle("上传处理结果");
        }

        mEtdetail=findViewById(R.id.et_detail);
        mBtnsubmit = findViewById(R.id.btn_submit);
        mBtnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String detail = mEtdetail.getText().toString().trim();

                if(TextUtils.isEmpty(detail)){
                    Toast.makeText(HandleActivity.this, "请填写内容", Toast.LENGTH_SHORT).show();
                }else{
                    uploadRepairResult("id", id,"type",type,"result",detail);
                }

            }
        });



    }

    private void uploadRepairResult(String ... kv) {
        new NetConnection(Config.SERVER_URL + Config.ACTION_RESULT_RETURN, HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);

                    if(jsonObject.getString("status").equals("1")){
                        String msg = jsonObject.getString("msg");
                        Toast.makeText(getApplicationContext(), msg ,Toast.LENGTH_SHORT).show();
                    }else{

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new NetConnection.FailCallback() {
            @Override
            public void onFail() {
                Toast.makeText(getApplicationContext(),"未能连接服务器",Toast.LENGTH_SHORT).show();
            }
        },kv);
    }



}
