package com.wzlab.smartcity.activity.account;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wzlab.smartcity.activity.R;
import com.wzlab.smartcity.net.account.GetSmsCode;
import com.wzlab.smartcity.net.account.Register;
import com.wzlab.smartcity.widget.CountDownButton;

public class RegisterActivity extends AppCompatActivity {

    private EditText mEtPhone;
    private EditText mEtPassword;
    private EditText mEtSmsCode;
    private Button mBtnRegister;
    private CountDownButton mBtnSendSmsCode;

    private String mSmsSessionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        mEtPhone = findViewById(R.id.et_register_phone);
        mEtPassword = findViewById(R.id.et_register_password);
        mEtSmsCode = findViewById(R.id.et_register_code);

        mBtnSendSmsCode = findViewById(R.id.btn_register_send_code);
        mBtnSendSmsCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = mEtPhone.getText().toString().trim();
                if(!TextUtils.isEmpty(phone)){
                    mBtnSendSmsCode.setIsCountDown(true);

                    new GetSmsCode(phone, Config.TYPE_SMS_CODE_REGISTER, new GetSmsCode.SuccessCallback() {
                        @Override
                        public void onSuccess(String smsSessionId) {
                            mSmsSessionId = smsSessionId;
                            Toast.makeText(getApplicationContext(),R.string.success_to_send_code,Toast.LENGTH_SHORT).show();
                        }
                    }, new GetSmsCode.FailCallback() {
                        @Override
                        public void onFail(String msg) {

                            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                            mBtnSendSmsCode.shutdown();
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(),R.string.phone_number_can_not_be_empty,Toast.LENGTH_SHORT).show();
                    mBtnSendSmsCode.shutdown();
                }
            }
        });

        mBtnRegister = findViewById(R.id.btn_register);
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = mEtPhone.getText().toString().trim();
                String password = mEtPassword.getText().toString().trim();
                String smsCode = mEtSmsCode.getText().toString().trim();
                if(TextUtils.isEmpty(phone)){
                    Toast.makeText(getApplicationContext(),R.string.phone_number_can_not_be_empty,Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(),R.string.password_can_not_be_empty,Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(smsCode)){
                    Toast.makeText(getApplicationContext(),R.string.code_can_not_be_empty,Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getApplicationContext(),"注册",Toast.LENGTH_SHORT).show();
                new Register(phone, password, smsCode, mSmsSessionId ,new Register.SuccessCallback() {
                    @Override
                    public void Success(String result) {
                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                    }
                }, new Register.FailCallback() {
                    @Override
                    public void Fail() {
                        Toast.makeText(getApplicationContext(),"注册失败",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }


}
