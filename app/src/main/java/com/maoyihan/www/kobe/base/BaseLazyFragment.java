package com.maoyihan.www.kobe.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maoyihan.www.kobe.utils.ToastUtils;


/**
 * 懒加载fragment
 * Created by Administrator on 2017/8/4 0004.
 */

public abstract class BaseLazyFragment extends Fragment {
    protected boolean isVisible;
    // 标志位，标志已经初始化完成。
    private boolean isPrepared;
    //已经初始化过了
    private boolean isInit;
    protected Activity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
    }

    @Nullable//@Nullable 表示定义的字段可以为空.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return getLayout(inflater, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        isPrepared = true;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 在这里实现Fragment数据的缓加载.
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected void lazyLoad() {
        if (!isPrepared || !isVisible || isInit) {
            return;
        }
        initData();
        initListener();
        isInit = true;
    }

    protected void onInvisible() {
    }

    /**
     * 加载布局
     */
    protected abstract View getLayout(LayoutInflater inflater, ViewGroup container);

    /**
     * 初始化组件
     */
    protected abstract void initView(View view);

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 添加事件监听
     */
    protected abstract void initListener();

    public void showMsg(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            ToastUtils.showShort(MyApplication.getInstance(), msg);
        }
    }
}
