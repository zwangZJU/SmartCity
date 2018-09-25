package com.wzlab.smartcity.activity.start;

import android.Manifest;
import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideContext;
import com.wzlab.smartcity.R;
import com.wzlab.smartcity.activity.account.AccountActivity;
import com.wzlab.smartcity.activity.account.Config;
import com.wzlab.smartcity.activity.main.MainActivity;
import com.wzlab.smartcity.net.account.Login;
import com.wzlab.smartcity.utils.GlideCacheUtil;


public class StartActivity extends AppCompatActivity {

    private AlphaAnimation alp;
    private RelativeLayout cl;
    private String message = "2";
    private static final String TAG = "StartActivity";
    private boolean isAlarming;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = getIntent();
        isAlarming = intent.getBooleanExtra("isAlarming",false);


        //去除标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去除状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);
        ImageView imageView = findViewById(R.id.iv_profile);
        String url = Config.START_IMG_URL;
        Glide.with(getApplication()).load(url).into(imageView);
        GlideCacheUtil.clearImageAllCache(getApplication());


        cl = findViewById(R.id.rl_start);
        //渐变动画
        alp = new AlphaAnimation(1, 1);
        alp.setDuration(2000);
        alp.setRepeatCount(100);
        cl.setAnimation(alp);
        alp.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation arg0) {
                // 动画开始时就登录
                String token = Config.getCachedToken(getApplicationContext());
                if(!TextUtils.isEmpty(token)){
                    String phone = Config.getCachedPhone(getApplicationContext());
                    String password = Config.getCachedPassword(getApplicationContext());
                    new Login(phone, password, "",Config.LOGIN_BY_PASSWORD, new Login.SuccessCallback() {
                        @Override
                        public void onSuccess(String token, String msg) {
                            message = "1";
                            //  Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                            //   startActivity(new Intent(StartActivity.this, MainActivity.class));
                            //  finish();
                        }
                    }, new Login.FailCallback() {
                        @Override
                        public void onFail(String msg) {
                            message = "0";
                            //   Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();

                        }
                    });
                }else{
                    message = "0";
                }


                if(ContextCompat.checkSelfPermission(StartActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(StartActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(StartActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(StartActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(StartActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.READ_PHONE_STATE}, 1);
                }


            }

            @Override
            public void onAnimationRepeat(Animation arg0) {

                if(ContextCompat.checkSelfPermission(StartActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(StartActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(StartActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(StartActivity.this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {

                    alp.cancel();
                }
            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                // TODO Auto-generated method stub
                if(ContextCompat.checkSelfPermission(StartActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED) {


                    if(message.equals("1")){
                        Intent mainIntent = new Intent(StartActivity.this, MainActivity.class);
                        mainIntent.putExtra("isAlarming",isAlarming);
                        startActivity(mainIntent);



                    }else{
                        startActivity(new Intent(StartActivity.this, AccountActivity.class));
                    }


                }
                finish();

            }
        });
    }

}
