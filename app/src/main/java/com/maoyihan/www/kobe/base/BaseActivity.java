package com.maoyihan.www.kobe.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.maoyihan.www.kobe.R;
import com.maoyihan.www.kobe.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * activity基类
 * Created by Administrator on 2016/9/15.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected String tag = getClass().getSimpleName();

    private FrameLayout flBaseContent;
    private RelativeLayout rlTitle;
    private ImageView ivBack;
    private TextView tvTitle;
    private RelativeLayout rlLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉手机信息栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(getLayout());
        ActivityControl.addAty(tag, this);
        handleBundle(savedInstanceState);
        initVariables();
        initView();
        loadData();
        initListener();
        EventBus.getDefault().register(this);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        View view = getLayoutInflater().inflate(R.layout.activity_base, null);
        super.setContentView(view);
        //加载子类Activity的布局
        initDefaultView(layoutResID);
    }

    private void initDefaultView(int layoutResId) {
        flBaseContent = (FrameLayout) findViewById(R.id.fl_activity_child_container);
        rlTitle = (RelativeLayout) findViewById(R.id.commonTitle_rl);
        ivBack = (ImageView) findViewById(R.id.title_iv_back);
        tvTitle = (TextView) findViewById(R.id.title_tv_title);
        rlLoading = (RelativeLayout) findViewById(R.id.commonProgress_rl);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        View childView = getLayoutInflater().inflate(layoutResId, null);
        flBaseContent.addView(childView, 0);
    }

    /**
     * 返回值为所要加载的布局文件
     */
    protected abstract int getLayout();

    /**
     * 初始化变量，包括Intent带的数据
     */
    protected abstract void initVariables();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 调用API获取数据
     */
    protected abstract void loadData();

    /**
     * 监听事件
     */
    protected abstract void initListener();

    /**
     * 处理Bundle
     */
    protected abstract void handleBundle(Bundle savedInstanceState);


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        ActivityControl.removeAty(tag);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }
    }

    public void showLoadingView() {
        rlLoading.setVisibility(View.VISIBLE);
    }

    public void hideLoadingView() {
        rlLoading.setVisibility(View.GONE);
    }

    public void showMsg(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            ToastUtils.showShort(this, msg);
        }
    }

    public void hideTitle() {
        rlTitle.setVisibility(View.GONE);
    }
}
