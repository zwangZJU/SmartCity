package com.wzlab.smartcity.net.account;

import android.widget.Toast;

import com.wzlab.smartcity.net.HttpMethod;
import com.wzlab.smartcity.net.NetConnection;
import com.wzlab.smartcity.activity.account.Config;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wzlab on 2018/6/2.
 */

public class Login {
    public Login(String phone_md5, String codeOrPassword, final String mode, final SuccessCallback successCallback, final FailCallback failCallback){
        new NetConnection(Config.SERVER_URL + Config.ACTION_LOGIN, HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {

                try {
                    JSONObject jsonObject = new JSONObject(result);

                    switch (jsonObject.getString(Config.KEY_STATUS)){
                        case Config.RESULT_STATUS_SUCCESS:
                            if(successCallback!=null){
                                successCallback.onSuccess(jsonObject.getString(Config.KEY_TOKEN));

                            }
                            break;
                        default:
                            if(failCallback!=null){
                                failCallback.onFail();
                            }
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if(failCallback!=null){
                        failCallback.onFail();
                    }
                }
            }
        }, new NetConnection.FailCallback() {
            @Override
            public void onFail() {
                if(failCallback!=null){
                    failCallback.onFail();
                }
            }
        },Config.KEY_PHONE,phone_md5,Config.KEY_MODE,mode,mode.equals(Config.LOGIN_BY_PASSWORD)?Config.KEY_PASSWORD:Config.KEY_SMS_CODE, codeOrPassword);
    }

    public static interface SuccessCallback{
        void onSuccess(String token);
    }

    public static interface FailCallback{
        void onFail();
    }
}
