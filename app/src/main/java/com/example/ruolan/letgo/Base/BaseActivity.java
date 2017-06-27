package com.example.ruolan.letgo.Base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ruolan.letgo.R;
import com.example.ruolan.letgo.Utils.ActivityKiller;
import com.example.ruolan.letgo.Utils.StatusBarUtils;
import com.example.ruolan.letgo.Utils.ToastUtils;
import com.example.ruolan.letgo.widget.LoadingProgressDialog;


/**
 * Created by liuwen on 2017/6/21.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private LinearLayout lyCommonBar;
    private TextView mTvCenter;//toobar中间文字
    private LoadingProgressDialog mLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutRes());
        initView();
        initData();
        setListener();
        StatusBarUtils.setWindowStatusBarColor(this,R.color.statusColor);
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

    protected void showLeftView() {
        lyCommonBar = getView(R.id.back_view);
        if (lyCommonBar == null) {
            return;
        }
        if (lyCommonBar != null) {
            lyCommonBar.setVisibility(View.VISIBLE);
        }
        lyCommonBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    protected void setCenterText(String str) {
        mTvCenter = getView(R.id.title);
        if (mTvCenter == null) {
            return;
        }
        if (mTvCenter != null) {
            mTvCenter.setText(str);
            mTvCenter.setVisibility(View.VISIBLE);
        }
    }

    public void showLoadingDialog(String loadingText, boolean isCanCancel, LoadingProgressDialog.ILoadingDialogListener listener) {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingProgressDialog(this, loadingText);
            mLoadingDialog.show();
            if (isCanCancel) {
                mLoadingDialog.setCannotCancel();
                mLoadingDialog.setListener(listener);
            }
        }
    }

    public void hideLoadingDialog() {
        if (null != mLoadingDialog && mLoadingDialog.isShowing()) {
            mLoadingDialog.closeDialog();
        }
    }
}
