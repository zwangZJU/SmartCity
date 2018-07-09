package com.wzlab.smartcity.net.account;

import com.wzlab.smartcity.activity.account.Config;
import com.wzlab.smartcity.net.HttpMethod;
import com.wzlab.smartcity.net.NetConnection;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wzlab on 2018/6/18.
 */

public class RecoverPassword {
    public RecoverPassword(String phone, String pwd, String smsCode, String smsSessionId, final SuccessCallback successCallback, final FailCallback failCallback){
        new NetConnection(Config.SERVER_URL + Config.ACTION_FORGET_PASSWORD, HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String msg) {
                try {
                    JSONObject jsonObject = new JSONObject(msg);
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
                        failCallback.onFail("数据解析异常");
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
        },Config.KEY_PHONE, phone,Config.KEY_PASSWORD, pwd, Config.KEY_SMS_CODE, smsCode,Config.KEY_SMS_SESSION_ID,smsSessionId);
    }

    public static interface SuccessCallback {
        void onSuccess(String msg);
    }
    public static interface FailCallback {
        void onFail(String msg);
    }
}
