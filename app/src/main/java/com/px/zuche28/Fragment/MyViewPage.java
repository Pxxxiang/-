package com.px.zuche28.Fragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MyViewPage extends ViewPager {

    private int startX;
    private int startY;

    public MyViewPage(@NonNull Context context) {
        super(context);
    }

    public MyViewPage(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    //重写这个方法，并且在方法里面请求所有的父控件都不要拦截他的事件

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(false);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) ev.getX();
                startY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:

                int endX = (int) ev.getX();
                int endY = (int) ev.getY();

                int dx = endX - startX;
                int dy = endY - startY;

                if (Math.abs(dy) < Math.abs(dx)) {
                    int currentItem = getCurrentItem();
                    // 左右滑动
                    if (dx > 0) {
                        // 向右划
                        if (currentItem == 0) {
                            // 第一个页面,需要拦截
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    } else {
                        // 向左划
                        int count = getAdapter().getCount();// item总数
                        if (currentItem == count - 1) {
                            // 最后一个页面,需要拦截
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    }

                }default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

}

