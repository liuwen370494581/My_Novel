package com.example.ruolan.letgo.Base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.ruolan.letgo.Utils.ActivityKiller;
import com.example.ruolan.letgo.Utils.ToastUtils;


/**
 * Created by liuwen on 2017/6/21.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutRes());
        initView();
        initData();
        setListener();
        ActivityKiller.getInstance().addActivity(this);
    }

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void setListener();

    protected abstract int setLayoutRes();


    public <T extends View> T getView(int viewId) {
        return (T) this.findViewById(viewId);
    }

    protected void openActivity(Class toActivity) {
        Intent intent = new Intent(this, toActivity);
        startActivity(intent);
    }

    protected void openActivity(Class toActivity, Bundle bundle) {
        Intent intent = new Intent(this, toActivity);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    protected void closeActivity() {
        finish();
    }

    protected void openActivity(Class toActivity, int reqCode) {
        Intent intent = new Intent(this, toActivity);
        startActivityForResult(intent, reqCode);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityKiller.getInstance().removeActivity(this);
        ToastUtils.removeToast();
    }
}
