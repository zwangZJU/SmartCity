package com.wzlab.smartcity.activity.account;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.wzlab.smartcity.activity.R;

public class AccountActivity extends AppCompatActivity {

    private boolean isExit = false; //按两次退出
    private static int ACTION_EXIT = -1;

    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        public void handleMessage(Message msg) {
            if (msg.what == ACTION_EXIT){
                isExit = false;
            }
        }
    };
    private LoginFragment loginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Make the status bar transparent
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //Make the Navigation bar transparent
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.activity_account);
        loginFragment = new LoginFragment();
        getFragmentManager().beginTransaction().add(R.id.fl_account_container, loginFragment).commitAllowingStateLoss();
    }


    /*
     点击两次返回才退出程序
   */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {

        if (!isExit) {
            if( getFragmentManager().findFragmentById(R.id.fl_account_container) == loginFragment){
                isExit = true;
                Toast.makeText(getApplicationContext(), "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
            } else{
                getFragmentManager().popBackStack();
            }

            // 利用handler延迟发送更改状态信息
            handler.sendEmptyMessageDelayed(ACTION_EXIT, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }
}
