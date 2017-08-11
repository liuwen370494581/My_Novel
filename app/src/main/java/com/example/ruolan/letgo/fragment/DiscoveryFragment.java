package com.example.ruolan.letgo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ruolan.letgo.Base.BaseFragment;
import com.example.ruolan.letgo.Jsoup.Action.ActionCallBack;
import com.example.ruolan.letgo.Jsoup.Action.StartReadBookAction;
import com.example.ruolan.letgo.R;

/**
 * Created by ruolan on 2015/11/29.
 * 发现
 */
public class DiscoveryFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        initView();
        return view;
    }

    private void initView() {
        showLoadingDialog(getString(R.string.Being_loaded),true,null);
        StartReadBookAction.searchDetailBookUi(getActivity(), "", new ActionCallBack() {
            @Override
            public void ok(Object object) {
        hideLoadingDialog();
            }

            @Override
            public void failed(Object object) {

            }
        });
    }

    @Override
    public void initData() {

    }

}
