package com.wzlab.smartcity.activity.account;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.wzlab.smartcity.activity.R;
import com.wzlab.smartcity.activity.main.MainActivity;
import com.wzlab.smartcity.net.account.GetSmsCode;
import com.wzlab.smartcity.net.account.Login;
import com.wzlab.smartcity.widget.ClearableEditText;
import com.wzlab.smartcity.widget.CountDownButton;
import com.wzlab.smartcity.widget.FloatLabeledEditText;

public class LoginActivity extends AppCompatActivity {

    private Button mBtnLogin;
    private View progress;
    private View mInputLayout;
    private ClearableEditText mEtLoginPhone;
    private ClearableEditText mEtLoginPwd;
    private TextView mTvLoginBycode;
    private TextView mTvLoginByPwd;
    private ClearableEditText mEtLoginCode;
    private CountDownButton mBtnSendCode;

    private String loginMethod = Config.LOGIN_BY_PASSWORD;

    String phone;
    String password;
    String code;
    FloatLabeledEditText f;
    private TextView mTvRegister;
    private TextView mTvForgetPassword;
    //private float mWidth, mHeight;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.activity_login);

        initView();
        initData();

    }

    //如果登录过，将用户名和密码填在EditText上
    private void initData() {
        phone = Config.getCachedPhone(getApplicationContext());
        password = Config.getCachedPassword(getApplicationContext());
        mEtLoginPhone.setText(phone);
        mEtLoginPwd.setText(password);
    }

    private void initView() {

        progress = findViewById(R.id.pb_login);
        mEtLoginPhone = findViewById(R.id.et_login_phone);
        mEtLoginPwd = findViewById(R.id.et_login_pwd);
        mEtLoginCode = findViewById(R.id.et_login_code);

        mBtnSendCode = findViewById(R.id.btn_send_code);
        mBtnSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone = mEtLoginPhone.getText().toString().trim();
                if(!TextUtils.isEmpty(phone)){
                    mBtnSendCode.setIsCountDown(true);

                    new GetSmsCode(phone, Config.ACTION_GET_CODE_LOGIN, new GetSmsCode.SuccessCallback() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(getApplicationContext(),R.string.success_to_send_code,Toast.LENGTH_SHORT).show();
                        }
                    }, new GetSmsCode.FailCallback() {
                        @Override
                        public void onFail() {
                            mBtnSendCode.shutdown();
                            Toast.makeText(getApplicationContext(),R.string.fail_to_get_code,Toast.LENGTH_SHORT).show();

                        }
                    });
                }else{
                    mBtnSendCode.shutdown();
                    Toast.makeText(getApplicationContext(),R.string.phone_number_can_not_be_empty,Toast.LENGTH_SHORT).show();

                }
            }
        });

        mTvLoginBycode = findViewById(R.id.tv_login_by_code);
        mTvLoginBycode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                f =findViewById(R.id.flet_pwd);
                f.setVisibility(View.GONE);

                mEtLoginPwd.setVisibility(View.GONE);
                findViewById(R.id.rl_code).setVisibility(View.VISIBLE);
                findViewById(R.id.flet_code).setVisibility(View.VISIBLE);
                mBtnSendCode.setVisibility(View.VISIBLE);
                mEtLoginCode.setVisibility(View.VISIBLE);
                mEtLoginCode.requestFocus();
                mTvLoginBycode.setVisibility(View.GONE);
                mTvLoginByPwd.setVisibility(View.VISIBLE);
                loginMethod = Config.LOGIN_BY_CODE;

            }
        });

        mTvLoginByPwd = findViewById(R.id.tv_login_by_pwd);
        mTvLoginByPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.rl_code).setVisibility(View.GONE);
                findViewById(R.id.flet_code).setVisibility(View.GONE);
                mBtnSendCode.setVisibility(View.GONE);
                mEtLoginCode.setVisibility(View.GONE);
                findViewById(R.id.flet_pwd).setVisibility(View.VISIBLE);

                mEtLoginPwd.setVisibility(View.VISIBLE);
                mEtLoginPwd.requestFocus();
                mTvLoginByPwd.setVisibility(View.GONE);
                mTvLoginBycode.setVisibility(View.VISIBLE);
                loginMethod = Config.LOGIN_BY_PASSWORD;
            }
        });

        mTvRegister = findViewById(R.id.tv_register);
        mTvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(getApplicationContext(),"ddd",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        mBtnLogin =  findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });


        mTvForgetPassword = findViewById(R.id.tv_forget_password);
        mTvForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });

    }






    private void attemptLogin() {

        // Store values at the time of the login attempt.
        phone = mEtLoginPhone.getText().toString().trim();
        if(loginMethod == Config.LOGIN_BY_PASSWORD){
            password = mEtLoginPwd.getText().toString().trim();
            if (TextUtils.isEmpty(phone)) {
                Toast.makeText(this, R.string.phone_number_can_not_be_empty, Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, R.string.password_can_not_be_empty, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(),phone+password,Toast.LENGTH_SHORT).show();
                Config.cachePhone(getApplicationContext(),phone);
                Config.cachePassword(getApplicationContext(),password);
                progress.setVisibility(View.VISIBLE);
                progressAnimator(progress);

                new Login(phone, password, Config.LOGIN_BY_PASSWORD, new Login.SuccessCallback() {
                    @Override
                    public void onSuccess(String token) {
                        Config.cacheToken(getApplicationContext(),token);
                        Config.cachePhone(getApplicationContext(),phone);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }
                }, new Login.FailCallback() {
                    @Override
                    public void onFail() {
                        Toast.makeText(getApplicationContext(),R.string.fail_to_login,Toast.LENGTH_SHORT).show();

                    }
                });
                //TODO 以后要删除
               // startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        }else if(loginMethod == Config.LOGIN_BY_CODE){
            code = mEtLoginCode.getText().toString().trim();
            if (TextUtils.isEmpty(phone)) {
                Toast.makeText(this, R.string.phone_number_can_not_be_empty, Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(code)) {
                Toast.makeText(this, R.string.code_can_not_be_empty, Toast.LENGTH_SHORT).show();
                return;
            }


            new Login(phone, code, Config.LOGIN_BY_CODE, new Login.SuccessCallback() {
                @Override
                public void onSuccess(String token) {
                    Config.cacheToken(getApplicationContext(),token);
                    Config.cachePhone(getApplicationContext(),phone);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            }, new Login.FailCallback() {
                @Override
                public void onFail() {
                    Toast.makeText(getApplicationContext(),R.string.fail_to_login,Toast.LENGTH_SHORT).show();

                }
            });




                SharedPreferences mSpUserInfo = getSharedPreferences("user",0);
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
                //     Toast.makeText(getApplicationContext(),'s',Toast.LENGTH_SHORT).show();
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




