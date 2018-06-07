package com.wzlab.smartcity.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wzlab on 2018/6/3.
 */

public class CountDownButton extends android.support.v7.widget.AppCompatButton implements View.OnClickListener{
    private Context mContext;
    private OnClickListener mOnClickListener;
    private Timer mTimer;//调度器  
    private TimerTask mTask;
    private long duration = 6 * 10000;//倒计时时长 设置默认10秒
    private long temp_duration;
    private String clickBeffor = "获取验证码";//点击前
    private String clickAfter = "秒后重新获取";//点击后
    private boolean isCountDown = false;



    public CountDownButton(Context context) {
        super(context);
        mContext = context;
        setOnClickListener(this);
    }

    public CountDownButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setOnClickListener(this);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            CountDownButton.this.setText(temp_duration/1000 + clickAfter);
            temp_duration -= 1000;
            if (temp_duration < 0) {//倒计时结束  
                CountDownButton.this.setEnabled(true);
                CountDownButton.this.setText(clickBeffor);
                stopTimer();
            }
        }
    };
    @Override
    public void setOnClickListener(OnClickListener onClickListener) {//提供外部访问方法  
        if (onClickListener instanceof CountDownButton) {
            super.setOnClickListener(onClickListener);
        }else{
            this.mOnClickListener = onClickListener;
        }
    }

    @Override
    public void onClick(View view) {
        if (mOnClickListener != null) {
            mOnClickListener.onClick(view);

        }
        if(isCountDown){
            startTimer();
        }






    }

    //计时开始  
    private void startTimer(){
        temp_duration = duration;
        CountDownButton.this.setEnabled(false);
        mTimer = new Timer();
        mTask = new TimerTask() {
            @Override
            public void run() {

                    mHandler.sendEmptyMessage(0x01);


            }
        };
        mTimer.schedule(mTask, 0, 1000);//调度分配，延迟0秒，时间间隔为1秒  
    }

    //计时结束  
    private void stopTimer(){
        if (mTask != null) {
            mTask.cancel();
            mTask = null;
        }
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        isCountDown = false;
    }

    public void setIsCountDown(boolean is){
        isCountDown = is;
    }

    public boolean getIsCountDown(){
        return isCountDown;
    }

    public void shutdown(){
        CountDownButton.this.setEnabled(true);
        CountDownButton.this.setText(clickBeffor);
        stopTimer();
    }

}
