package com.wzlab.smartcity.activity.account;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;



/**
 * Created by wzlab on 2018/6/2.
 */

public class Config {
    public static final String IP = "10.180.34.181";
    public static final String SERVER_URL = "http://"+IP+":9090/cdz/api/do.jhtml?router=appApiService.";
    public static final String TYPE_ROLE = "3";

    public static final String ACTION_REGISTER = "userRegister";
    public static final String ACTION_LOGIN="userLogin";
    public static final String ACTION_FORGET_PASSWORD="forgetPwd";
    public static final String ACTION_GET_SMS_CODE="getSmsCode";
    public static final String TYPE_SMS_CODE_LOGIN = "2";
    public static final String TYPE_SMS_CODE_FORGET_PASSWORD = "1";
    public static final String TYPE_SMS_CODE_REGISTER = "0";

    public static final String KEY_ACTION = "action";
    public static final String KEY_TYPE = "type";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_PHONE_MD5 = "phone_md5";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_NEWPASSWORD = "newPwd";
    public static final String KEY_SMS_CODE = "smsCode";
    public static final String KEY_SMS_SESSION_ID = "smsSessionId";
    public static final String KEY_STATUS = "status";
    public static final String KEY_ROLE = "role";


    public static final String RESULT_STATUS_SUCCESS = "1";
    public static final String RESULT_STATUS_FAIL = "0";
    public static final String RESULT_STATUS_INVALID_TOKEN = "2";
    public static final String RESULT_MESSAGE = "msg";
    public static final String LOGIN_BY_PASSWORD = "0";
    public static final String LOGIN_BY_SMS_CODE = "1";

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

    //main
    public static final String KEY_DATA = "data";
    public static final String KEY_ROW = "rows";
}
