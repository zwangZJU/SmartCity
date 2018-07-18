package com.wzlab.smartcity.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by wzlab on 2018/7/10.
 */

public class NoScrollViewPager extends ViewPager {



    public boolean isScroll = false;

    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    //禁用滑动
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(isScroll){
            return super.onTouchEvent(ev);
        }else{//禁用滑动
            return true;
        }

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(isScroll){
            return super.onInterceptTouchEvent(ev);
        }else{//禁用滑动
            return false;
        }
    }

    //去除切换动画
    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item,false);
    }

//    @Override
//    public void setCurrentItem(int item) {
//        super.setCurrentItem(item);
//    }

    public boolean isScroll() {
        return isScroll;
    }

    public void setScroll(boolean scroll) {
        isScroll = scroll;
    }
}
