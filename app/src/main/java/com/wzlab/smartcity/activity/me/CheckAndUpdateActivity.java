package com.wzlab.smartcity.activity.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.allenliu.versionchecklib.core.http.HttpParams;
import com.allenliu.versionchecklib.core.http.HttpRequestMethod;
import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.RequestVersionListener;
import com.wzlab.smartcity.R;
import com.wzlab.smartcity.SmartSecurityApplication;
import com.wzlab.smartcity.activity.account.Config;




public class CheckAndUpdateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        setContentView(R.layout.activity_check_and_update);
        Button mBtnCheck = findViewById(R.id.btn_check);
        TextView mTvCurrentVersion = findViewById(R.id.tv_current_version);
        String versionName = SmartSecurityApplication.getVersionName(getApplicationContext());
        mTvCurrentVersion.setText("版本号：" + versionName);
        mBtnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAndUpdate();
            }
        });
    }

    private void checkAndUpdate() {
        HttpParams httpParams = new HttpParams();
//        httpParams.put("role","1");
//        httpParams.put("current_version","v 1.0");
        httpParams.put("phone","18392888103");
        AllenVersionChecker
                .getInstance()
                .requestVersion()
                .setRequestMethod(HttpRequestMethod.POSTJSON)

                .setRequestParams(httpParams)
                .setRequestUrl(Config.SERVER_URL+Config.ACTION_GET_USER_BASIC_INFO)
                .request(new RequestVersionListener() {
                    @Nullable
                    @Override
                    public UIData onRequestVersionSuccess(String result) {
                        //拿到服务器返回的数据，解析，拿到downloadUrl和一些其他的UI数据

                        UIData uiData = UIData
                                .create()
                                .setDownloadUrl("www.baidu.con")
                                .setTitle("发现新版本")
                                .setContent("对之前bug进行修复");
                        //放一些其他的UI参数，拿到后面自定义界面使用
                        uiData.getVersionBundle().putString("key", "your value");
                        return uiData;

                    }

                    @Override
                    public void onRequestVersionFailure(String message) {

                    }
                })
                .excuteMission(getApplicationContext());
    }
}
