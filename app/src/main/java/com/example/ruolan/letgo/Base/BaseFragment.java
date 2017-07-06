package com.example.ruolan.letgo.Base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ruolan.letgo.Utils.ToastUtils;
import com.example.ruolan.letgo.widget.LoadingProgressDialog;

/**
 * Created by liuwen on 2017/6/21.
 */
public abstract class BaseFragment extends Fragment {

    private LoadingProgressDialog mLoadingDialog;
    public Context mContext;
    protected boolean isVisible;
    private boolean isPrepared;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            lazyLoad();
        } else {
            isVisible = false;
            onInvisible();
        }
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        setHasOptionsMenu(true);
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        isPrepared = true;
        lazyLoad();
    }

    /**
     * 懒加载
     */
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }

        initData();
    }

    // 不可见
    protected void onInvisible() {

    }


    public abstract void initData();



    public void showLoadingDialog(String loadingText, boolean isCanCancel, LoadingProgressDialog.ILoadingDialogListener listener) {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingProgressDialog(getActivity(), loadingText);
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

    protected void openActivity(Class toActivity) {
        Intent intent = new Intent(getActivity(), toActivity);
        startActivity(intent);
    }

    protected void openActivity(Class toActivity, Bundle bundle) {
        Intent intent = new Intent(getActivity(), toActivity);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    protected void openActivity(Class toActivity, int reqCode) {
        Intent intent = new Intent(getActivity(), toActivity);
        startActivityForResult(intent, reqCode);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        ToastUtils.removeToast();
    }
}
