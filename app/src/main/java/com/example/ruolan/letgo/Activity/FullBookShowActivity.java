package com.example.ruolan.letgo.Activity;

import android.widget.FrameLayout;

import com.example.ruolan.letgo.Base.BaseActivity;
import com.example.ruolan.letgo.R;
import com.example.ruolan.letgo.widget.ReadBook.ContentSwitchView;

/**
 * Created by liuwen on 2017/8/10.
 */
public class FullBookShowActivity extends BaseActivity {

    private FrameLayout flContent;
    private ContentSwitchView csvBook;

    @Override
    protected int setLayoutRes() {
        return R.layout.full_book_show_activity;
    }

    @Override
    protected void initView() {
        flContent = (FrameLayout) findViewById(R.id.fl_content);
        csvBook = (ContentSwitchView) findViewById(R.id.csv_book);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }
}
