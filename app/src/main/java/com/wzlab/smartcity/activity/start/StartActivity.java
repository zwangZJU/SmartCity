package com.wzlab.smartcity.activity.start;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import com.wzlab.smartcity.activity.R;
import com.wzlab.smartcity.activity.account.AccountActivity;

public class StartActivity extends AppCompatActivity {

        private AlphaAnimation alp;
        private RelativeLayout cl;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            Window window = getWindow();
            //透明状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            // window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            setContentView(R.layout.activity_start);
            cl = findViewById(R.id.rl_start);
            //渐变动画
            alp = new AlphaAnimation(1, 1);
            alp.setDuration(2000);
            alp.setRepeatCount(100);
            cl.setAnimation(alp);
            alp.setAnimationListener(new Animation.AnimationListener() {

                @Override
                public void onAnimationStart(Animation arg0) {
                    // TODO Auto-generated method stub

                    if(ContextCompat.checkSelfPermission(StartActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(StartActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    }

                }

                @Override
                public void onAnimationRepeat(Animation arg0) {

                    if(ContextCompat.checkSelfPermission(StartActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED) {
                        alp.cancel();
                    }
                }

                @Override
                public void onAnimationEnd(Animation arg0) {
                    // TODO Auto-generated method stub
                    if(ContextCompat.checkSelfPermission(StartActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED) {
                        startActivity(new Intent(StartActivity.this, AccountActivity.class));
                        finish();
                    }

                }
            });
        }

}
