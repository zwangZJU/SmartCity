package com.wzlab.smartcity.activity.account;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by wzlab on 2018/6/2.
 */

public class Config {
    public static final String IP = "192.168.1.163";
    public static final String SERVER_URL = "http://"+IP+":9090/cdz/api/do.jhtml?router=appApiController.";
    public static final String ACTION_GET_CODE_LOGIN = "code_login";
    public static final String ACTION_GET_CODE_REGISTER = "code_register";
    public static final String ACTION_REGISTER = "register";
    public static final String ACTION_LOGIN="userLogin";
    public static final String KEY_ACTION = "action";
    public static final String KEY_MODE = "mode";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_PHONE_MD5 = "phone_md5";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_SMS_CODE = "code";
    public static final String KEY_STATUS = "status";
    public static final String RESULT_STATUS_SUCCESS = "1";
    public static final String RESULT_STATUS_FAIL = "0";
    public static final String RESULT_STATUS_INVALID_TOKEN = "2";
    public static final String RESULT_MESSAGE = "msg";
    public static final String LOGIN_BY_PASSWORD = "0";
    public static final String LOGIN_BY_CODE = "1";

    public static final String CHARSET = "utf-8";
    public static final String APP_ID = "com.wzlab.smartcity";


    public static String getCachedToken(Context context){
        return context.getSharedPreferences(APP_ID,Context.MODE_PRIVATE).getString(KEY_TOKEN,null);
    }

    public static void cacheToken(Context context, String token){
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_TOKEN,token);
        editor.apply();
    }

    public static String getCachedPhone(Context context){
        return context.getSharedPreferences(APP_ID,Context.MODE_PRIVATE).getString(KEY_PHONE,null);
    }

    public static void cachePhone(Context context, String phone){
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_PHONE,phone);
        editor.apply();
    }

    public static String getCachedPassword(Context context){
        return context.getSharedPreferences(APP_ID,Context.MODE_PRIVATE).getString(KEY_PASSWORD,null);
    }

    public static void cachePassword(Context context, String password){
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_PASSWORD,password);
        editor.apply();
    }
}
