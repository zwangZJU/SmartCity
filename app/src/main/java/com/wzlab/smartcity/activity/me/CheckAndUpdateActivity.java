package com.wzlab.smartcity.activity.me;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.allenliu.versionchecklib.core.http.HttpParams;
import com.allenliu.versionchecklib.core.http.HttpRequestMethod;
import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.RequestVersionListener;
import com.wzlab.smartcity.R;
import com.wzlab.smartcity.SmartSecurityApplication;
import com.wzlab.smartcity.activity.account.Config;

import org.json.JSONException;
import org.json.JSONObject;

public class CheckAndUpdateActivity extends AppCompatActivity {

    private String versionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        setContentView(R.layout.activity_check_and_update);
        Button mBtnCheck = findViewById(R.id.btn_check);
        TextView mTvCurrentVersion = findViewById(R.id.tv_current_version);
        versionName = SmartSecurityApplication.getVersionName(getApplicationContext());
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
        httpParams.put("role",Config.TYPE_ROLE);
        httpParams.put("current_version",versionName);
        //      httpParams.put("phone","18392888103");
        AllenVersionChecker
                .getInstance()
                .requestVersion()
                .setRequestMethod(HttpRequestMethod.POSTJSON)

                .setRequestParams(httpParams)
                .setRequestUrl(Config.SERVER_URL+Config.ACTION_CHECK_AND_UPDATE)
                .request(new RequestVersionListener() {
                    @Nullable
                    @Override
                    public UIData onRequestVersionSuccess(String result) {
                        //拿到服务器返回的数据，解析，拿到downloadUrl和一些其他的UI数据
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            String status = jsonObject.getString("status");
                            if(status.equals("1")) {
                                String newContent = jsonObject.getString("update_content");
                                String versionNo = jsonObject.getString("version_no");
                                String newVersionUrl = jsonObject.getString("new_version_url");

                                String packageSize = jsonObject.getString("package_size");

                                UIData uiData = UIData
                                        .create()
                                        .setDownloadUrl(newVersionUrl)
                                        .setTitle("发现新版本")
                                        .setContent(newContent);
                                //放一些其他的UI参数，拿到后面自定义界面使用
                                // uiData.getVersionBundle().putString("key", "your value");
                                return uiData;
                            }else{
                                Toast.makeText(getApplicationContext(),"当前已是最新版本",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }


                        return null;
                    }



                    @Override
                    public void onRequestVersionFailure(String message) {

                    }
                })
                .excuteMission(getApplicationContext());
    }
}
