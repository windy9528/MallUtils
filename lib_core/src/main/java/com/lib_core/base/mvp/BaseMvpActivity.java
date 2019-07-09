package com.lib_core.base.mvp;

import com.lib_core.base.BaseActivity;

/**
 * date:2019/7/9
 * name:windy
 * function:
 */
public abstract class BaseMvpActivity<M extends IBaseModel, P extends BasePresenter> extends BaseActivity implements IBaseView {

    public M model;
    public P presenter;

    @Override
    protected void initData() {

        presenter = (P) initPresenter();

        if (presenter != null) {
            model = (M) presenter.getModel();
            if (model != null) {
                presenter.attach(model, this);
            }
        }

        initDatas();
    }

    protected abstract void initDatas();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detach();
        }
    }
}
