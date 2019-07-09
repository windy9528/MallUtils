package com.lib_core.base;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.lib_core.utils.Log;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * date:2019/7/9
 * name:windy
 * function: 对activity抽基类
 */
public abstract class BaseActivity extends AppCompatActivity {

    private boolean isStatus;//沉浸式
    private boolean isFullScreen;//全屏
    public Dialog mLoadDialog;// 加载框

    /**
     * 记录处于前台的Activity
     */
    private static BaseActivity mForegroundActivity = null;
    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //打印堆栈ID
        Log.i("getTaskId = " + getTaskId()
                + "    ThreadName:" + Thread.currentThread().getName());
        initLoad();
        setContentView(getLayoutId());
        //绑定布局
        bind = ButterKnife.bind(this);

        initView(); //初始化组件

        initData(); //初始化数据
    }

    /**
     * 设置layoutId
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化视图
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 清除数据
     */
    protected abstract void destoryData();

    /**
     *全屏
     * @param
     */
    public void isFullscreen(boolean fullscreenn){
        if (fullscreenn){
            //全屏代码

        }
    }

    /**
     * @param mActivity 无参跳转
     */
    public void intent(Class mActivity) {
        Intent intent = new Intent(this, mActivity);
        startActivity(intent);
    }

    /**
     * @param mActivity 有参跳转
     * @param bundle
     */
    public void intent(Class mActivity, Bundle bundle) {
        Intent intent = new Intent(this, mActivity);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 显示toast、
     *
     * @param msg
     */
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示带时间的toast
     *
     * @param msg
     */
    public void showToast(String msg, int time) {
        Toast.makeText(this, msg, time).show();
    }

    /**
     * 初始化加载框
     */
    private void initLoad() {
        mLoadDialog = new ProgressDialog(this);// 加载框
        mLoadDialog.setCanceledOnTouchOutside(false);
        mLoadDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {
                if (mLoadDialog.isShowing() && keyCode == KeyEvent.KEYCODE_BACK) {
                    cancelLoadDialog();//加载消失的同时
                    mLoadDialog.cancel();
                }
                return false;
            }
        });
    }

    /**
     * 释放内存，避免内存泄露
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bind != null) {
            bind.unbind();  //解绑布局
            bind = null;
        }
        destoryData();
        System.gc();
    }

    //取消操作：请求或者其他
    public void cancelLoadDialog() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("onStart");
        mForegroundActivity = this;
    }

    /**
     * 获取当前处于前台的activity
     */
    public static BaseActivity getForegroundActivity() {
        return mForegroundActivity;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("onReStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("onStop");
    }
}