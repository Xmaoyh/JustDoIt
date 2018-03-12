package com.maoyihan.www.kobe.base;

/** View基类
 * Created by owner on 2017/7/5.
 */

public interface IBaseView {
    void showMessage(String msg);
    void showLoading();
    void hideLoading();
    void showErrorView();
}
