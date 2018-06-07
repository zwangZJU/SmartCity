package com.wzlab.smartcity.net;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import com.wzlab.smartcity.activity.account.Config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by wzlab on 2018/6/2.
 */

public class NetConnection {

    private static String TAG = "NetConnection";
    @SuppressLint("StaticFieldLeak")
    public NetConnection(final String url, final HttpMethod method, final SuccessCallback successCallback, final FailCallback failCallback, final String ... kvs){



        new AsyncTask<Void, Void, String>(){

            @Override
            protected String doInBackground(Void... voids) {
                StringBuffer paramsStr = new StringBuffer();
                for (int i = 0; i < kvs.length; i+=2) {
                    paramsStr.append(kvs[i]).append("=").append(kvs[i+1]).append("&");
                }

                try {
                    URLConnection urlConnection;
                    switch (method){
                        case POST:
                            urlConnection = new URL(url).openConnection();
                            urlConnection.setDoOutput(true);
                            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), Config.CHARSET));
                            bw.write(paramsStr.toString());
                            bw.flush();
                            break;
                        default:
                            urlConnection = new URL(url+"?"+paramsStr.toString()).openConnection();
                            break;
                    }

                    Log.i(TAG, "doInBackground: URL"+urlConnection.getURL());
                    Log.i(TAG, "doInBackground: data"+paramsStr);
                    BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),Config.CHARSET));
                    String line = null;
                    StringBuffer result = new StringBuffer();
                    while ((line=br.readLine())!=null){
                        result.append(line);
                    }
                    Log.i(TAG, "doInBackground: result "+result);
                    return result.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                if(result!=null){
                    if(successCallback!=null){
                        successCallback.onSuccess(result);
                    }
                }else{
                    if(failCallback!=null){
                        failCallback.onFail();
                    }

                }
                super.onPostExecute(result);
            }
        }.execute();
    }

    public static interface SuccessCallback{
        void onSuccess(String result);
    }
    public static interface FailCallback{
        void onFail();
    }
}
