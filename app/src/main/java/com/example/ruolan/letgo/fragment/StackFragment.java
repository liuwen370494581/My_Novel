package com.example.ruolan.letgo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ruolan.letgo.Base.BaseFragment;
import com.example.ruolan.letgo.Jsoup.Action.ActionCallBack;
import com.example.ruolan.letgo.Jsoup.Action.CoverAction;
import com.example.ruolan.letgo.R;

/**
 * Created by ruolan on 2015/11/29.
 * 书库
 */
public class StackFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        initView(view);
        initDate();
        setListener();
        return view;
    }




    private void initView(View view) {
    }


    private void initDate() {
      CoverAction.searchQiDianCover(getActivity(), new ActionCallBack() {
          @Override
          public void ok(Object object) {

          }

          @Override
          public void failed(Object object) {

          }
      });
    }

    private void setListener() {

    }

    @Override
    public void initData() {

    }

}
