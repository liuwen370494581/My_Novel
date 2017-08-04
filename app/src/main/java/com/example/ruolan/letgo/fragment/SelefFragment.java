package com.example.ruolan.letgo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ruolan.letgo.Base.BaseFragment;
import com.example.ruolan.letgo.Jsoup.Action.ActionCallBack;
import com.example.ruolan.letgo.Jsoup.Action.AuthorWorkAction;
import com.example.ruolan.letgo.Jsoup.Action.SearchBookAction;
import com.example.ruolan.letgo.R;

/**
 * Created by ruolan on 2015/11/29.
 * 书架
 */
public class SelefFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }


    @Override
    public void initData() {

    }

}
