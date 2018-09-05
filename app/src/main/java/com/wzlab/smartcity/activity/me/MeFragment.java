package com.wzlab.smartcity.activity.me;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.wzlab.smartcity.R;
import com.wzlab.smartcity.activity.account.Config;
import com.wzlab.smartcity.net.HttpMethod;
import com.wzlab.smartcity.net.NetConnection;
import com.wzlab.smartcity.utils.DataParser;

import org.json.JSONException;
import org.json.JSONObject;

public class MeFragment extends Fragment implements View.OnClickListener{

    private String userInfoName = "";
    private String userInfoAvatarURL = null;
    private String userInfoIsCert = "";
    private Bitmap bmAvatar;
    private String phone;
    private static int LOAD_USER_INFO_TEXT_SUCCESS = 5;
    private static int LOAD_USER_INFO_ALL_SUCCESS = 6;


    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(msg.what == LOAD_USER_INFO_ALL_SUCCESS){
                if(userInfoAvatarURL != null){
                    Glide.with(getContext()).load(userInfoAvatarURL).into(mIvAvatar);
                }
                mTvName.setText(userInfoName);
                mTvDuration.setText(userInfoIsCert);
            }
        }
    };
    private ImageView mIvAvatar;
    private TextView mTvName;
    private TextView mTvDuration;
    private Button mBtnEditProfile;
    private Button mBtnWhereof;
    private Button mBtnFeedback;
    private Button mBtnUpdateApp;

    public MeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        phone = Config.getCachedPhone(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mIvAvatar = view.findViewById(R.id.iv_me_avatar);
        mTvName = view.findViewById(R.id.tv_me_name);
        mTvDuration = view.findViewById(R.id.tv_me_duration);
        mBtnEditProfile = view.findViewById(R.id.btn_edit);
        mBtnWhereof = view.findViewById(R.id.btn_whereof);
        mBtnFeedback = view.findViewById(R.id.btn_feedback);
        mBtnUpdateApp = view.findViewById(R.id.btn_update);
        mBtnEditProfile.setOnClickListener(this);
        mBtnWhereof.setOnClickListener(this);
        mBtnFeedback.setOnClickListener(this);
        mBtnUpdateApp.setOnClickListener(this);

//        mIvAvatar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Fragment fragment = new PersonalCenterFragment();
//                Bundle bundle = new Bundle();
//                bundle.putString("avatarURL",userInfoAvatarURL);
//                fragment.setArguments(bundle);
//                getChildFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fl_main_container, fragment).commitAllowingStateLoss();
//
//            }
//        });


        getUserBasicInfo();


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    public void getUserBasicInfo(){
        new NetConnection(Config.SERVER_URL + Config.ACTION_GET_USER_BASIC_INFO, HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    switch (jsonObject.getString(Config.KEY_STATUS)){
                        case Config.RESULT_STATUS_SUCCESS:
                            userInfoAvatarURL = jsonObject.getString("avatar");
                            userInfoName = DataParser.getData(jsonObject.getString("name"),"未实名");
                            userInfoIsCert = DataParser.getData(jsonObject.getString("is_cert"),"未认证").equals("1")?"已认证":"未认证";
                            Message message = new Message();
                            message.what = LOAD_USER_INFO_ALL_SUCCESS;
                            handler.sendMessage(message);
                            break;
                        default:
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(),"数据解析异常",Toast.LENGTH_SHORT).show();
                }
            }
        }, new NetConnection.FailCallback() {
            @Override
            public void onFail() {
                Toast.makeText(getContext(),"未能链接服务器",Toast.LENGTH_SHORT).show();
            }
        },Config.KEY_PHONE, phone);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){

            case R.id.btn_update:
                intent = new Intent(getContext(),CheckAndUpdateActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_feedback:
                intent = new Intent(getContext(),FeedBackActivity.class);
                startActivity(intent);
                break;
        }
    }
}
