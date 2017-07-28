package com.example.ruolan.letgo.Activity;

import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ruolan.letgo.Base.BaseActivity;
import com.example.ruolan.letgo.R;
import com.example.ruolan.letgo.Utils.KeyboardUtil;
import com.example.ruolan.letgo.Utils.ToastUtils;

/**
 * Created by liuwen on 2017/7/27.
 */
public class SearchBookActivity extends BaseActivity implements View.OnClickListener {
    private EditText editSearchBook;
    private TextView tvCancel, tvReflash, tvClear;
    private RelativeLayout reCommonBar, reReflash, reHistory;
    private boolean isShowCommonBar = true;


    @Override
    protected void initView() {
        showLeftView();
        setCenterText(getString(R.string.search_book));
        editSearchBook = getView(R.id.activity_search_book_name);
        tvCancel = getView(R.id.activity_search_book_cancel);
        reCommonBar = getView(R.id.common_bar);
        tvReflash = getView(R.id.activity_search_book_reflash);
        tvClear = getView(R.id.activity_search_book_clear);
        reReflash = getView(R.id.activity_search_book_re_reflash);
        reHistory = getView(R.id.activity_search_book_re_history);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {
        tvCancel.setOnClickListener(this);
        tvClear.setOnClickListener(this);
        tvReflash.setOnClickListener(this);
        editSearchBook.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int i, KeyEvent event) {

                if ((i == 0 || i == 3) && event != null) {
                    ToastUtils.showToast(SearchBookActivity.this, editSearchBook.getText().toString());
                    return true;
                }
                return false;
            }
        });

        editSearchBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isShowCommonBar) {
                    reCommonBar.setVisibility(View.GONE);
                    tvCancel.setVisibility(View.VISIBLE);
                    isShowCommonBar = false;
                } else {
                    reCommonBar.setVisibility(View.VISIBLE);
                    tvCancel.setVisibility(View.GONE);
                    isShowCommonBar = true;
                }
            }
        });
    }

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_search_book;
    }

    @Override
    public void onClick(View view) {
        if (view == tvCancel) {
            KeyboardUtil.hideInputMethodWindow(this, tvCancel);
            reCommonBar.setVisibility(View.VISIBLE);
            tvCancel.setVisibility(View.GONE);
            isShowCommonBar = true;
        } else if (view == tvReflash) {

        } else if (view == tvClear) {

        }
    }

    @Override
    public void onBackPressed() {
        if (!isShowCommonBar) {
            KeyboardUtil.hideInputMethodWindow(this, tvCancel);
            reCommonBar.setVisibility(View.VISIBLE);
            tvCancel.setVisibility(View.GONE);
            isShowCommonBar = true;
        } else {
            finish();
        }
    }
}
