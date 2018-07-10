package com.wzlab.smartcity.activity.account;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.wzlab.smartcity.activity.R;
import com.wzlab.smartcity.activity.main.MainActivity;
import com.wzlab.smartcity.activity.main.ScannerActivity;
import com.wzlab.smartcity.net.account.GetSmsCode;

import com.wzlab.smartcity.net.account.Login;
import com.wzlab.smartcity.widget.ClearableEditText;
import com.wzlab.smartcity.widget.CountDownButton;
import com.wzlab.smartcity.widget.FloatLabeledEditText;

public class LoginFragment extends Fragment {

    private Button mBtnLogin;
    private View progress;
    private View mInputLayout;
    private ClearableEditText mEtLoginPhone;
    private ClearableEditText mEtLoginPwd;
    private TextView mTvLoginBySmsCode;
    private TextView mTvLoginByPwd;
    private ClearableEditText mEtLoginSmsCode;
    private CountDownButton mBtnSendSmsCode;

    private String loginMethod = Config.LOGIN_BY_PASSWORD;

    String phone;
    String password;
    String smsCode;
    String mSmsSessionId;
    FloatLabeledEditText f;
    private TextView mTvRegister;
    private TextView mTvForgetPassword;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login,container,false);
        
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData(view);
    }


    //If have logged in before, the phone and password used last time will be filled in the EditText
    private void initData(View view) {
        phone = Config.getCachedPhone(view.getContext());
        password = Config.getCachedPassword(view.getContext());
        mEtLoginPhone.setText(phone);
        mEtLoginPhone.setSelection(mEtLoginPhone.getText().toString().length());
        mEtLoginPwd.setText(password);
    }

    private void initView(final View view) {

        progress = view.findViewById(R.id.pb_login);
        mEtLoginPhone = view.findViewById(R.id.et_login_phone);
        mEtLoginPwd = view.findViewById(R.id.et_login_pwd);
        mEtLoginSmsCode = view.findViewById(R.id.et_login_sms_code);

        mBtnSendSmsCode = view.findViewById(R.id.btn_send_sms_code);
        mBtnSendSmsCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = mEtLoginPhone.getText().toString().trim();
                if(!TextUtils.isEmpty(phone)){
                    mBtnSendSmsCode.setIsCountDown(true);

                    new GetSmsCode(phone, Config.TYPE_SMS_CODE_LOGIN, new GetSmsCode.SuccessCallback() {
                        @Override
                        public void onSuccess(String smsSessionId, String msg) {
                            mSmsSessionId = smsSessionId;
                            Toast.makeText(view.getContext(),msg,Toast.LENGTH_SHORT).show();
                        }
                    }, new GetSmsCode.FailCallback() {
                        @Override
                        public void onFail(String msg) {
                            mBtnSendSmsCode.shutdown();
                            Toast.makeText(view.getContext(),msg,Toast.LENGTH_SHORT).show();

                        }
                    });
                }else{
                    mBtnSendSmsCode.shutdown();
                    Toast.makeText(view.getContext(),R.string.phone_number_can_not_be_empty,Toast.LENGTH_SHORT).show();

                }
            }
        });
        // the Click Listener of TextView LoginBysmsCode
        mTvLoginBySmsCode = view.findViewById(R.id.tv_login_by_sms_code);
        mTvLoginBySmsCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f =view.findViewById(R.id.flet_pwd);
                f.setVisibility(View.GONE);

                mEtLoginPwd.setVisibility(View.GONE);
                view.findViewById(R.id.rl_sms_code).setVisibility(View.VISIBLE);
                view.findViewById(R.id.flet_sms_code).setVisibility(View.VISIBLE);
                mBtnSendSmsCode.setVisibility(View.VISIBLE);
                mEtLoginSmsCode.setVisibility(View.VISIBLE);
                mEtLoginSmsCode.requestFocus();
                mTvLoginBySmsCode.setVisibility(View.GONE);
                mTvLoginByPwd.setVisibility(View.VISIBLE);
                loginMethod = Config.LOGIN_BY_SMS_CODE;

            }
        });

        mTvLoginByPwd = view.findViewById(R.id.tv_login_by_pwd);
        mTvLoginByPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.findViewById(R.id.rl_sms_code).setVisibility(View.GONE);
                view.findViewById(R.id.flet_sms_code).setVisibility(View.GONE);
                mBtnSendSmsCode.setVisibility(View.GONE);
                mEtLoginSmsCode.setVisibility(View.GONE);
                view.findViewById(R.id.flet_pwd).setVisibility(View.VISIBLE);

                mEtLoginPwd.setVisibility(View.VISIBLE);
                mEtLoginPwd.requestFocus();
                mTvLoginByPwd.setVisibility(View.GONE);
                mTvLoginBySmsCode.setVisibility(View.VISIBLE);
                loginMethod = Config.LOGIN_BY_PASSWORD;
            }
        });

        mTvRegister = view.findViewById(R.id.tv_register);
        mTvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fl_account_container, new RegisterFragment()).commitAllowingStateLoss();
            }
        });

        mBtnLogin =  view.findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin(view);
            }
        });


        mTvForgetPassword = view.findViewById(R.id.tv_forget_password);
        mTvForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fl_account_container,new ForgetPasswordFragment()).commitAllowingStateLoss();
            }
        });

        TextView mTvShowServiceTerms = view.findViewById(R.id.tv_terms_of_service);
        mTvShowServiceTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),ScannerActivity.class));
                getActivity().finish();
            }
        });

    }






    private void attemptLogin(final View view) {

        // Store values at the time of the login attempt.
        phone = mEtLoginPhone.getText().toString().trim();
        // 1.Login by password
        if(loginMethod == Config.LOGIN_BY_PASSWORD){
            password = mEtLoginPwd.getText().toString().trim();
            if (TextUtils.isEmpty(phone)) {
                Toast.makeText(view.getContext(), R.string.phone_number_can_not_be_empty, Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(password)) {
                Toast.makeText(view.getContext(), R.string.password_can_not_be_empty, Toast.LENGTH_SHORT).show();
            } else {
              //  Toast.makeText(view.getContext(),phone+password,Toast.LENGTH_SHORT).show();
                Config.cachePhone(view.getContext(),phone);
                Config.cachePassword(view.getContext(),password);
                progress.setVisibility(View.VISIBLE);
                progressAnimator(progress);

                new Login(phone, password, "",Config.LOGIN_BY_PASSWORD, new Login.SuccessCallback() {
                    @Override
                    public void onSuccess(String token, String msg) {
                        Toast.makeText(view.getContext(),msg,Toast.LENGTH_SHORT).show();

                        Config.cacheToken(view.getContext(), token);
                        Config.cachePhone(view.getContext(), phone);

                        startActivity(new Intent(getActivity(), MainActivity.class));
                        getActivity().finish();


                    }
                }, new Login.FailCallback() {
                    @Override
                    public void onFail(String msg) {
                        Toast.makeText(view.getContext(),msg,Toast.LENGTH_SHORT).show();

                    }
                });
                //TODO 以后要删除
               // startActivity(new Intent(LoginFragment.this, ScannerActivity.class));
            }
            // Login by smssmsCode
        }else if(loginMethod == Config.LOGIN_BY_SMS_CODE){
            smsCode = mEtLoginSmsCode.getText().toString().trim();
            if (TextUtils.isEmpty(phone)) {
                Toast.makeText(view.getContext(), R.string.phone_number_can_not_be_empty, Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(smsCode)) {
                Toast.makeText(view.getContext(), R.string.code_can_not_be_empty, Toast.LENGTH_SHORT).show();
                return;
            }


            new Login(phone, smsCode, mSmsSessionId,Config.LOGIN_BY_SMS_CODE, new Login.SuccessCallback() {
                @Override
                public void onSuccess(String token, String msg) {

                    Toast.makeText(view.getContext(),msg,Toast.LENGTH_SHORT).show();

                    Config.cacheToken(view.getContext(), token);
                    Config.cachePhone(view.getContext(), phone);
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    getActivity().finish();

                }
            }, new Login.FailCallback() {
                @Override
                public void onFail(String msg) {
                    Toast.makeText(view.getContext(),R.string.fail_to_login,Toast.LENGTH_SHORT).show();

                }
            });




                SharedPreferences mSpUserInfo = view.getContext().getSharedPreferences("user",0);
                SharedPreferences.Editor editor = mSpUserInfo.edit();
                editor.putString("phone", phone);
                editor.apply();

                progress.setVisibility(View.VISIBLE);
                progressAnimator(progress);

        }


    }


    private void progressAnimator(final View view) {
        PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("scaleX",
                0.5f, 1f);
        PropertyValuesHolder animator2 = PropertyValuesHolder.ofFloat("scaleY",
                0.5f, 1f);
        ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(view,
                animator, animator2);
        animator3.setDuration(500);
        animator3.start();
        animator3.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //     Toast.makeText(view.getContext(),'s',Toast.LENGTH_SHORT).show();
                progress.setVisibility(View.INVISIBLE);

                // startActivity(new Intent(RegisteredActivity.ACTION));
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });



    }
}




