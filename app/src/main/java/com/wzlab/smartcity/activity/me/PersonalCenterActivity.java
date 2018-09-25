package com.wzlab.smartcity.activity.me;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;


import com.wzlab.smartcity.R;
import com.wzlab.smartcity.activity.account.Config;
import com.wzlab.smartcity.net.HttpMethod;
import com.wzlab.smartcity.net.NetConnection;
import com.wzlab.smartcity.utils.DataParser;
import com.wzlab.smartcity.utils.GraphProcess;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;


public class PersonalCenterActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView mIvAvatar;
    private String phone;
    private String avatarURL;
    Bitmap scaledBitmap;
    private static final String TAG = "PersonalCenterActivity";
    private String avatarPath;
    private ImageView mIvBackground;

    private int KEY_IMAGE_UPLOAD_SUCCESS = 7;
    private int KEY_IMAGE_DOWNLOAD_SUCCESS = 8;
    private int LOAD_USER_INFO_ALL_SUCCESS = 9;


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == KEY_IMAGE_UPLOAD_SUCCESS) {
                showImage();

            } else if (msg.what == KEY_IMAGE_DOWNLOAD_SUCCESS) {
                showImage();
            } else if(msg.what == LOAD_USER_INFO_ALL_SUCCESS){
                showTextInfo();
            }
        }
    };
    private String dialogTitle;
    private String tip = "";
    private TextView mTvAccout;
    private TextView mTvName;
    private TextView mTvGender;
    private TextView mTvCity;
    private TextView mTvAge;
    private int viewResource;
    private AlertDialog alertDialog;
    private int editType;
    private String key;
    private String value;

    private String userInfoName;
    private String userInfoIsCert;
    private String userInfoAge;
    private String userInfoGender;
    private String city;
    private String provience;
    private boolean isMale;
     
    private boolean isFemale;
    private TextView mTvTitleUserName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));

        setContentView(R.layout.fragment_personal_center);
        phone = Config.getCachedPhone(getApplicationContext());
        Intent intent = getIntent();
        // 接收传参

        avatarURL = intent.getStringExtra("avatarURL");


        mIvBackground = findViewById(R.id.iv_back);
        mIvAvatar = findViewById(R.id.iv_avatar);
        mIvAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new ShowAvatarFragment();

                getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.rv_me_container, fragment).commitAllowingStateLoss();
            }
        });



        //获取需要被模糊的原图bitmap
        // 先判断有没有缓存

        initView();
        initData();



    }

    private void initData() {
        mTvAccout.setText(phone);
        getUserBasicInfo();
        showImage();

    }

    private void initView() {
        ImageView mIvBack = findViewById(R.id.iv_personal_center_back);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mTvTitleUserName = findViewById(R.id.tv_title_user_name);
        findViewById(R.id.rv_edit_name).setOnClickListener(this);
        findViewById(R.id.rv_edit_gender).setOnClickListener(this);

        findViewById(R.id.rv_edit_age).setOnClickListener(this);


        mTvAccout = findViewById(R.id.tv_edit_account);
        mTvName = findViewById(R.id.tv_edit_name);
        mTvGender = findViewById(R.id.tv_edit_gender);
        mTvCity = findViewById(R.id.tv_edit_city);
        mTvAge = findViewById(R.id.tv_edit_age);

    }

    private void showTextInfo() {

        mTvAge.setText(userInfoAge);
        mTvCity.setText(city);
        mTvGender.setText(userInfoGender);
        mTvName.setText(userInfoName);
        mTvTitleUserName.setText(userInfoName);

    }


    // 显示头像及背景
    private void showImage() {

        avatarPath = getExternalFilesDir(null).getPath() + "/avatar/" + phone + ".png";
        File file = new File(avatarPath);
        // 预设图片
        scaledBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_avatar);
        if (file.exists()) {//如果有缓存，直接显示
            scaledBitmap = BitmapFactory.decodeFile(avatarPath);
        } else {//没有缓存
            if (avatarURL != null && !avatarURL.equals("")) {
                //但有链接，下载图片并存储
                new Thread() {
                    @Override
                    public void run() {
                        scaledBitmap = GraphProcess.downLoadImage(avatarURL);
                        GraphProcess.savaImage(scaledBitmap, getExternalFilesDir(null).getPath() + "/avatar", phone);
                        Message message = new Message();
                        message.what = KEY_IMAGE_DOWNLOAD_SUCCESS;
                        handler.sendMessage(message);
                    }
                }.start();

            }


        }

        // 背景虚化
        Bitmap blurBitmap = GraphProcess.toBlur(scaledBitmap, 3);
        mIvBackground.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mIvBackground.setImageBitmap(blurBitmap);
        mIvAvatar.setImageBitmap(scaledBitmap);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        showImage();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rv_edit_name:
                dialogTitle = "请填写姓名";
                tip = "请输入真实姓名，不超过10个字";
                editType = 0;
                break;
            case R.id.rv_edit_gender:
                dialogTitle = "请选择性别";
                tip = "";
                editType = 1;
                break;
            case R.id.rv_edit_age:
                dialogTitle = "请填写年龄";
                tip = "";
                editType = 2;
                break;


        }

        showEditDialog();


    }

    private void showEditDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setView(R.layout.content_edit_user_info)
                .setCancelable(false);
        alertDialog = builder.create();
        TextView title = new TextView(this);
        title.setGravity(Gravity.CENTER);
        title.setText(dialogTitle);
        title.setTextSize(18);
        title.setPadding(10,30,10,30);
        title.setTextColor(getResources().getColor(R.color.black));
        alertDialog.setCustomTitle(title);



        alertDialog.show();
        TextView textView =  alertDialog.findViewById(R.id.tv_edit_tip);
        LinearLayout genderSelector = alertDialog.findViewById(R.id.ll_gender);
        final ImageView mIvMale = alertDialog.findViewById(R.id.iv_male);
        final ImageView mIvFemale = alertDialog.findViewById(R.id.iv_female);

        if(tip.length()>1){
            textView.setText(tip);
        }


        EditText mEtEditName = alertDialog.findViewById(R.id.et_edit_name);
        NumberPicker mNpAge = alertDialog.findViewById(R.id.np_edit_age);
        if(editType == 0){
            mEtEditName.setVisibility(View.VISIBLE);
            mEtEditName.setText(userInfoName);
            mNpAge.setVisibility(View.GONE);
            genderSelector.setVisibility(View.GONE);
            key = "name";
            mEtEditName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    value = String.valueOf(charSequence);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }else if(editType == 1){
            isMale = false;
            isFemale = false;
            mEtEditName.setVisibility(View.GONE);
            mNpAge.setVisibility(View.GONE);
            genderSelector.setVisibility(View.VISIBLE);
            key = "gender";
            mIvMale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isMale){
                        mIvMale.setImageDrawable(getDrawable(R.drawable.ic_male));
                        isMale = false;
                        value = "";
                    }else if(isFemale){
                        mIvMale.setImageDrawable(getDrawable(R.drawable.ic_male_focused));
                        mIvFemale.setImageDrawable(getDrawable(R.drawable.ic_female));
                        isMale = true;
                        isFemale = false;
                        value = "男";
                    }else{
                        mIvMale.setImageDrawable(getDrawable(R.drawable.ic_male_focused));
                        isMale = true;
                        value = "男";
                    }

                }
            });
            
            mIvFemale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isFemale){
                        mIvFemale.setImageDrawable(getDrawable(R.drawable.ic_female));
                        isFemale = false;
                        value = "";
                    }else if(isMale){
                        mIvMale.setImageDrawable(getDrawable(R.drawable.ic_male));
                        mIvFemale.setImageDrawable(getDrawable(R.drawable.ic_female_focused));
                        isMale = false;
                        isFemale = true;
                        value = "女";
                    }else{
                        mIvFemale.setImageDrawable(getDrawable(R.drawable.ic_female_focused));
                        isFemale = true;
                        value = "女";
                    }
                }
            });
            Log.e(TAG, "showEditDialog: "+value );
        }else if(editType == 2){
            mEtEditName.setVisibility(View.GONE);
            mNpAge.setVisibility(View.VISIBLE);
            genderSelector.setVisibility(View.GONE);
            key = "age";
            mNpAge.setMinValue(10);
            mNpAge.setMaxValue(99);
            mNpAge.setValue(18);
            mNpAge.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
            value = String.valueOf("18");
            mNpAge.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    value = String.valueOf(newVal);
                 //   Toast.makeText(getApplicationContext(),age,Toast.LENGTH_SHORT).show();
                }
            });
        }

        Button mBtnCancel = alertDialog.findViewById(R.id.btn_cancel_edit);
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        Button mBtnConfirm = alertDialog.findViewById(R.id.btn_confirm_edit);
        mBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(value.equals("")){
                     Toast.makeText(getApplicationContext(),"请选择性别",Toast.LENGTH_LONG).show();
                     return;

                }

                new NetConnection(Config.SERVER_URL + Config.ACTION_UPDATE_USER_BASIC_INFO, HttpMethod.POST, new NetConnection.SuccessCallback() {
                    @Override
                    public void onSuccess(String result) {
                         initData();

                         alertDialog.dismiss();

                    }
                }, new NetConnection.FailCallback() {
                    @Override
                    public void onFail() {

                        alertDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"未能连接服务器",Toast.LENGTH_SHORT).show();

                    }
                },Config.KEY_PHONE, phone,"key",key,"value",value);
            }
        });

    }
    public void getUserBasicInfo(){
        new NetConnection(Config.SERVER_URL + Config.ACTION_GET_USER_BASIC_INFO, HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    switch (jsonObject.getString(Config.KEY_STATUS)){
                        case Config.RESULT_STATUS_SUCCESS:

                            userInfoName = DataParser.getData(jsonObject.getString("name"),"未实名");
                            userInfoIsCert = DataParser.getData(jsonObject.getString("is_cert"),"未认证").equals("1")?"已认证":"未认证";
                            userInfoAge = DataParser.getData(jsonObject.getString("age"),"未知");
                            userInfoGender = DataParser.getData(jsonObject.getString("gender"),"未知");
                            String userInfoAddress = DataParser.getData(jsonObject.getString("address"),"未知");

                            city = userInfoAddress.split("市")[0];

                            Message message = new Message();
                            message.what = LOAD_USER_INFO_ALL_SUCCESS;
                            handler.sendMessage(message);
                            break;
                        default:
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"数据解析异常",Toast.LENGTH_SHORT).show();
                }
            }
        }, new NetConnection.FailCallback() {
            @Override
            public void onFail() {
                Toast.makeText(getApplicationContext(),"未能链接服务器",Toast.LENGTH_SHORT).show();
            }
        },Config.KEY_PHONE, phone);
    }

}
