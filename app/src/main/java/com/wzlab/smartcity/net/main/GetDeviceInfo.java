package com.wzlab.smartcity.net.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.wzlab.smartcity.activity.account.Config;
import com.wzlab.smartcity.net.HttpMethod;
import com.wzlab.smartcity.net.NetConnection;
import com.wzlab.smartcity.net.account.GetSmsCode;
import com.wzlab.smartcity.po.Device;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by wzlab on 2018/7/11.
 */

public class GetDeviceInfo {
    private static final String TAG = "GetDeviceInfo";
    Context context;
    int layoutResource;
    int containerId;

    public GetDeviceInfo(Context context, int layoutResource, int containerId){
        this.context = context;
        this.layoutResource = layoutResource;
        this.containerId = containerId;
    }

    public static interface SuccessCallback{
        void onSuccess(ArrayList list, String msg);
    }

    public static interface FailCallback{
        void onFail(String msg);
    }

    public static void getDeviceList(String phone, final SuccessCallback successCallback, final FailCallback failCallback){
        String url = Config.SERVER_URL + "getDeviceList";
        new NetConnection(url, HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    switch (jsonObject.getString(Config.KEY_STATUS)){
                        case Config.RESULT_STATUS_SUCCESS:
                            if(successCallback!=null){
                                //TODO
                                ArrayList<Device> deviceList = new ArrayList<Device>();
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                Gson gson = new Gson();
                                for(int i=0;i<jsonArray.length();i++){
                                    deviceList.add(gson.fromJson(jsonArray.get(i).toString(),Device.class));
                                }



                                successCallback.onSuccess(deviceList,jsonObject.getString(Config.RESULT_MESSAGE));

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
        },Config.KEY_PHONE, phone);
    }


    // 获得设备详细数据
    @SuppressLint("StaticFieldLeak")
    public static void getDeviceDetail(String deviceId, final SuccessCallback successCallback, final FailCallback failCallback){
        ArrayList list = new ArrayList();
        String url = Config.SERVER_URL + "getDeviceDetail";
        new NetConnection(url, HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    switch (jsonObject.getString(Config.KEY_STATUS)){
                        case Config.RESULT_STATUS_SUCCESS:
                            if(successCallback!=null){
                                ArrayList<Device> deviceList = new ArrayList<>();
                                successCallback.onSuccess(deviceList,jsonObject.getString(Config.RESULT_MESSAGE));
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
                        failCallback.onFail("登录异常");
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
        });

    }
}
