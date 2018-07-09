package com.wzlab.smartcity.activity.account;

import android.app.Fragment;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wzlab.smartcity.activity.R;
import com.wzlab.smartcity.net.account.GetSmsCode;
import com.wzlab.smartcity.net.account.Register;
import com.wzlab.smartcity.widget.CountDownButton;

public class RegisterFragment extends Fragment {

    private EditText mEtPhone;
    private EditText mEtPassword;
    private EditText mEtSmsCode;
    private Button mBtnRegister;
    private CountDownButton mBtnSendSmsCode;

    private String mSmsSessionId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(final View view) {
        mEtPhone = view.findViewById(R.id.et_register_phone);
        mEtPassword = view.findViewById(R.id.et_register_password);
        mEtSmsCode = view.findViewById(R.id.et_register_code);

        mBtnSendSmsCode = view.findViewById(R.id.btn_register_send_code);
        mBtnSendSmsCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = mEtPhone.getText().toString().trim();
                if(!TextUtils.isEmpty(phone)){
                    mBtnSendSmsCode.setIsCountDown(true);

                    new GetSmsCode(phone, Config.TYPE_SMS_CODE_REGISTER, new GetSmsCode.SuccessCallback() {
                        @Override
                        public void onSuccess(String smsSessionId,String msg) {
                            mSmsSessionId = smsSessionId;
                            Toast.makeText(view.getContext(),msg,Toast.LENGTH_SHORT).show();
                        }
                    }, new GetSmsCode.FailCallback() {
                        @Override
                        public void onFail(String msg) {

                            Toast.makeText(view.getContext(),msg,Toast.LENGTH_SHORT).show();
                            mBtnSendSmsCode.shutdown();
                        }
                    });
                }else{
                    Toast.makeText(view.getContext(),R.string.phone_number_can_not_be_empty,Toast.LENGTH_SHORT).show();
                    mBtnSendSmsCode.shutdown();
                }
            }
        });

        mBtnRegister = view.findViewById(R.id.btn_register);
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = mEtPhone.getText().toString().trim();
                String password = mEtPassword.getText().toString().trim();
                String smsCode = mEtSmsCode.getText().toString().trim();
                if(TextUtils.isEmpty(phone)){
                    Toast.makeText(view.getContext(),R.string.phone_number_can_not_be_empty,Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(view.getContext(),R.string.password_can_not_be_empty,Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(smsCode)){
                    Toast.makeText(view.getContext(),R.string.code_can_not_be_empty,Toast.LENGTH_SHORT).show();
                    return;
                }
               // Toast.makeText(view.getContext(),"注册",Toast.LENGTH_SHORT).show();
                new Register(phone, password, smsCode, mSmsSessionId ,new Register.SuccessCallback() {
                    @Override
                    public void onSuccess(String msg) {

                        Toast.makeText(view.getContext(),msg,Toast.LENGTH_SHORT).show();
                            // 返回登录界面

                            getFragmentManager().popBackStack();


                    }
                }, new Register.FailCallback() {
                    @Override
                    public void onFail(String msg) {
                        Toast.makeText(view.getContext(),msg,Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }


}
