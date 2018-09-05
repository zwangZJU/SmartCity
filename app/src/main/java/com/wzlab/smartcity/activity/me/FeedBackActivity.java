package com.wzlab.smartcity.activity.me;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mixiaoxiao.smoothcompoundbutton.SmoothCheckBox;
import com.mixiaoxiao.smoothcompoundbutton.SmoothCompoundButton;
import com.wzlab.smartcity.R;
import com.wzlab.smartcity.activity.account.Config;
import com.wzlab.smartcity.net.HttpMethod;
import com.wzlab.smartcity.net.NetConnection;


import org.json.JSONException;
import org.json.JSONObject;

public class FeedBackActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher{

    private TextView mTvBack;
    private Button mBtnSubmit;
    private EditText mEtContent;
    private TextView mTvCharStatistic;
    private SmoothCheckBox mScbFunction;
    private SmoothCheckBox mScbSuggest;
    private SmoothCheckBox mScbOther;

    private String function = null;
    private String suggest = null;
    private String other = null;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        setContentView(R.layout.activity_feed_back);

        phone = Config.getCachedPhone(getApplicationContext());
        initView();
    }

    private void initView() {

        mTvBack = findViewById(R.id.tv_cancel_send);
        mBtnSubmit = findViewById(R.id.btn_submit);
        mEtContent = findViewById(R.id.et_edit_content);
        mTvCharStatistic = findViewById(R.id.tv_char_statistic);
        mEtContent.addTextChangedListener(this);
        mScbFunction = findViewById(R.id.cb_function);
        mScbSuggest = findViewById(R.id.cb_suggest);
        mScbOther = findViewById(R.id.cb_other);
        mTvBack.setOnClickListener(this);
        mBtnSubmit.setOnClickListener(this);

        mScbFunction.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(SmoothCompoundButton smoothCompoundButton, boolean b) {
                if(b){
                    function = "功能异常：";
                }else {
                    function = null;
                }
            }
        });
        mScbSuggest.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(SmoothCompoundButton smoothCompoundButton, boolean b) {
                if(b){
                    suggest = "产品建议：";
                }else {
                    suggest = null;
                }
            }
        });
        mScbOther.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(SmoothCompoundButton smoothCompoundButton, boolean b) {
                if(b){
                    other = "其他问题：";
                }else {
                    other = null;
                }
            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_cancel_send:
                finish();
                break;
            case R.id.btn_submit:
                submitSuggestion();
                break;

        }
    }

    private void submitSuggestion() {
        String content = mEtContent.getText().toString().trim();
        if(TextUtils.isEmpty(function+suggest+other)||TextUtils.isEmpty(content)){
            Toast.makeText(this,"请勾选问题类型，并填写详细描述",Toast.LENGTH_LONG).show();
        }else if(content.length()<10){
            Toast.makeText(this,"字数太少，请详细描述",Toast.LENGTH_LONG).show();
        }else{
            new NetConnection(Config.SERVER_URL + Config.ACTION_SUBMIT_SUGGESTION, HttpMethod.POST, new NetConnection.SuccessCallback() {
                @Override
                public void onSuccess(String result) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        Toast.makeText(getApplicationContext(),jsonObject.getString("msg"),Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new NetConnection.FailCallback() {
                @Override
                public void onFail() {
                    Toast.makeText(getApplicationContext(),"未能链接服务器",Toast.LENGTH_LONG).show();
                }
            },Config.KEY_PHONE, phone, "suggestion", function+suggest+other+content);
        }

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

        int count = editable.length();
        mTvCharStatistic.setText(String.valueOf(count) + "/240");
        if(count>=180){
            mTvCharStatistic.setTextColor(getResources().getColor(R.color.remotefile_timebar_alarm_color));
        }
        if(count>200){
            mTvCharStatistic.setTextColor(getResources().getColor(R.color.colorAccent));

        }
    }
}
