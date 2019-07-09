package com.lib_core.base.mvp;

/**
 * date:2019/7/9
 * name:windy
 * function:  v层的回调
 */
public interface IBaseView {

    BasePresenter initPresenter();

    void showProgress();  //显示进度

    void hideProgress();  //隐藏进度

}
