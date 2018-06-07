package com.wzlab.smartcity.net.account;

import com.wzlab.smartcity.net.HttpMethod;
import com.wzlab.smartcity.net.NetConnection;
import com.wzlab.smartcity.activity.account.Config;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wzlab on 2018/6/2.
 */

public class GetSmsCode {
    public GetSmsCode(String phone, String action, final SuccessCallback successCallback, final FailCallback failCallback){
        new NetConnection(Config.SERVER_URL + "getCode", HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    switch (jsonObject.getInt(Config.KEY_STATUS)){
                        case Config.RESULT_STATUS_SUCCESS:
                            if(successCallback!=null){
                                successCallback.onSuccess();
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
        },Config.KEY_ACTION, action,Config.KEY_PHONE,phone);
    }

    public static interface SuccessCallback{
        void onSuccess();
    }
    public static interface FailCallback{
        void onFail();
    }
}
