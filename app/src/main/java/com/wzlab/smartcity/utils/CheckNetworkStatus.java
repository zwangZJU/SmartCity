package com.wzlab.smartcity.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by wzlab on 2018/7/12.
 */

public class CheckNetworkStatus {

    //  判断手机的网络状态（是否联网）
    public static int getNetWorkInfo(Context context) {
        //网络状态初始值
        int type = -1;  //-1(当前网络异常，没有联网)
        //通过上下文得到系统服务，参数为网络连接服务，返回网络连接的管理类
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //通过网络管理类的实例得到联网日志的状态，返回联网日志的实例
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        //判断联网日志是否为空
        if (activeNetworkInfo == null) {
            //状态为空当前网络异常，没有联网
            return type;
        }
        //不为空得到使用的网络类型
        int type1 = activeNetworkInfo.getType();
        //网络类型为运营商（移动/联通/电信）
        if (type1 == ConnectivityManager.TYPE_MOBILE) {
            // 注：如果想要判断其他网络类型进入ConnectivityManager类中根据常量值判断
            type = 0;
            //网络类型为WIFI（无线网）
        } else if (type1 == ConnectivityManager.TYPE_WIFI) {

            type = 1;
        }
        //返回网络类型
        return type;
    }

}
