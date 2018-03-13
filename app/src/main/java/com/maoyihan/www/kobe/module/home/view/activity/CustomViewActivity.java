package com.maoyihan.www.kobe.module.home.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.maoyihan.www.kobe.R;
import com.maoyihan.www.kobe.base.BaseActivity;
import com.maoyihan.www.kobe.widget.ArcProgress;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2017/8/23 0023.
 */

public class CustomViewActivity extends BaseActivity{
    ArcProgress mArcProgress;
    private MyHandler mMyHandler = new MyHandler(this);
    private static class MyHandler extends Handler{
        private WeakReference<CustomViewActivity> mCustomViewActivityWeakReference;
        MyHandler(CustomViewActivity customViewActivity){
            mCustomViewActivityWeakReference = new WeakReference<CustomViewActivity>(customViewActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            CustomViewActivity activity = mCustomViewActivityWeakReference.get();
            if(activity != null){
                if(msg.what == 1){
                    activity.mArcProgress.changeShape();
                    activity.mMyHandler.sendEmptyMessageDelayed(Message.obtain().what =1,1000);
                }
            }
        }
    }
    @Override
    protected int getLayout() {
        return R.layout.activity_custom;
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initView() {
        mArcProgress = (ArcProgress) findViewById(R.id.arcProgress);
        mMyHandler.sendEmptyMessage(1);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void handleBundle(Bundle savedInstanceState) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMyHandler.removeCallbacksAndMessages(this);
    }
}
