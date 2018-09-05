package com.wzlab.smartcity.net.main;



import com.wzlab.smartcity.activity.account.Config;
import com.wzlab.smartcity.net.HttpMethod;
import com.wzlab.smartcity.net.NetConnection;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wzlab on 2018/8/14.
 */

public class ImageUpload {
    private static final String TAG = "ImageUpload";

    public static void singleImageUpload(String phone, final String imageBase64Str, final SuccessCallback successCallback, final FailCallback failCallback){



        new NetConnection(Config.SERVER_URL + Config.ACTION_UPLOAD_USER_AVATAR, HttpMethod.POST, new NetConnection.SuccessCallback() {
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
        },Config.KEY_PHONE,phone,"avatar",imageBase64Str);
    }

    public static interface SuccessCallback{
        void onSuccess(String msg);
    }

    public static interface FailCallback{
        void onFail(String msg);
    }
}
