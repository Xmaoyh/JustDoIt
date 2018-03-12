package com.maoyihan.www.kobe.http;

import com.maoyihan.www.kobe.R;
import com.maoyihan.www.kobe.base.BaseActivity;
import com.maoyihan.www.kobe.base.BaseResponse;
import com.maoyihan.www.kobe.base.MyApplication;
import com.maoyihan.www.kobe.utils.ToastUtils;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;


/**
 * 不带progress
 * Created by Administrator on 2017/8/10 0010.
 */

public abstract class BaseObserver<T extends BaseResponse> implements Observer<T> {
    private BaseActivity mActivity;

    public BaseObserver(BaseActivity activity) {
        this.mActivity = activity;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
    }

    @Override
    public void onNext(@NonNull T t) {
        if (t.getCode() == 0) {
            doOnNext(t);
        } else {
            ToastUtils.showShort(MyApplication.getInstance(), t.getMessage());
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        ToastUtils.showShort(MyApplication.getInstance(), MyApplication.getInstance().getString(R.string.network_on_error) + e.toString());
    }

    @Override
    public void onComplete() {
        mActivity.hideLoadingView();
    }

    abstract void doOnNext(@NonNull T t);


}
