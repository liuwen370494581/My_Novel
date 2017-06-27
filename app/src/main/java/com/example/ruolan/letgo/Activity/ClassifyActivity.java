package com.example.ruolan.letgo.Activity;

import com.example.ruolan.letgo.Base.BaseActivity;
import com.example.ruolan.letgo.R;

/**
 * Created by liuwen on 2017/6/26.
 */
public class ClassifyActivity extends BaseActivity {
    @Override
    protected void initView() {
        showLeftView();
        setCenterText(getString(R.string.classify));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected int setLayoutRes() {
        return R.layout.classify_activity;
    }
}
