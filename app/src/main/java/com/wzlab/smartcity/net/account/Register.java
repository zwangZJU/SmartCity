package com.wzlab.smartcity.net.account;

import android.content.Context;
import android.widget.Toast;

import com.wzlab.smartcity.activity.account.Config;
import com.wzlab.smartcity.net.HttpMethod;
import com.wzlab.smartcity.net.NetConnection;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wzlab on 2018/6/4.
 */

public class Register {
    public Register(String phone, String password, String smsCode, String smsSessionId,final SuccessCallback successCallback, final FailCallback failCallback){
        new NetConnection(Config.SERVER_URL + Config.ACTION_REGISTER, HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    switch (jsonObject.getString(Config.KEY_STATUS)){
                        case Config.RESULT_STATUS_SUCCESS:
                            if(successCallback!=null){
                                successCallback.onSuccess(jsonObject.getString(Config.RESULT_MESSAGE));
                            }
                            break;
                        default:
                            if(failCallback!=null){
                                failCallback.onFail(jsonObject.getString(Config.RESULT_MESSAGE));
                            }
                            break;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    if(failCallback!=null){
                        failCallback.onFail("解析异常");
                    }
                }
            }
        }, new NetConnection.FailCallback() {
            @Override
            public void onFail() {
                if(failCallback!=null){
                    failCallback.onFail("未能连接到服务器");

                }
            }
        },Config.KEY_PHONE, phone, Config.KEY_PASSWORD,password, Config.KEY_SMS_CODE, smsCode,Config.KEY_SMS_SESSION_ID,smsSessionId,Config.KEY_ROLE,Config.TYPE_ROLE);


    }

    public static interface SuccessCallback{
        void onSuccess(String msg);

    }
    public static interface FailCallback{
        void onFail(String msg);
    }
}
