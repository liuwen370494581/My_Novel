package com.example.ruolan.letgo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.ruolan.letgo.Base.BaseFragment;
import com.example.ruolan.letgo.Jsoup.Action.ActionCallBack;
import com.example.ruolan.letgo.Jsoup.Action.CoverAction;
import com.example.ruolan.letgo.R;
import com.example.ruolan.letgo.bean.BookModel;
import com.example.ruolan.letgo.bean.IndexModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by ruolan on 2015/11/29.
 * 书库
 */
public class StackFragment extends BaseFragment {
    private List<IndexModel> mList = new ArrayList<>();
    private List<String> mPicList = new ArrayList<>();

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
        showLoadingDialog(getString(R.string.Being_loaded), false, null);
        CoverAction.searchQiDianCover(getActivity(), new ActionCallBack() {
            @Override
            public void ok(Object object) {
                mList.addAll((Collection<? extends IndexModel>) object);
                hideLoadingDialog();
            }

            @Override
            public void failed(Object object) {
                hideLoadingDialog();

            }
        });

        CoverAction.searchQiDianCoverPic(getActivity(), new ActionCallBack() {
            @Override
            public void ok(Object object) {
                mPicList.addAll((Collection<? extends String>) object);
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
