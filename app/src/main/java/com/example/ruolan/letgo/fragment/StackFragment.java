package com.example.ruolan.letgo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ruolan.letgo.Adapter.BannerAdapter;
import com.example.ruolan.letgo.Base.BaseFragment;
import com.example.ruolan.letgo.Jsoup.Action.ActionCallBack;
import com.example.ruolan.letgo.Jsoup.Action.CoverAction;
import com.example.ruolan.letgo.Jsoup.Action.QiDianAction;
import com.example.ruolan.letgo.R;
import com.example.ruolan.letgo.bean.IndexModel;
import com.hejunlin.superindicatorlibray.CircleIndicator;
import com.hejunlin.superindicatorlibray.LoopViewPager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by ruolan on 2015/11/29.
 * 书库
 */
public class StackFragment extends BaseFragment {
    private List<IndexModel> mBannerList = new ArrayList<>();
    private List<String> mBannerPicList = new ArrayList<>();
    private BannerAdapter mAdapter; //头部banner
    private LoopViewPager viewpager; //头部banner
    private CircleIndicator indicator;//头部banner

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
        viewpager = (LoopViewPager) view.findViewById(R.id.viewpager);
        indicator = (CircleIndicator) view.findViewById(R.id.indicator);
    }


    private void initDate() {
        showLoadingDialog(getString(R.string.Being_loaded), false, null);
        CoverAction.searchQiDianCover(getActivity(), new ActionCallBack() {
            @Override
            public void ok(Object object) {
                mBannerList.addAll((Collection<? extends IndexModel>) object);
                mAdapter = new BannerAdapter(getActivity(), mBannerList, mBannerPicList);
                viewpager.setAdapter(mAdapter);
                viewpager.setLooperPic(true);
                indicator.setViewPager(viewpager);
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
                mBannerPicList.addAll((Collection<? extends String>) object);
            }

            @Override
            public void failed(Object object) {

            }
        });

    }

    private void setListener() {

        QiDianAction.searchQiDianType(getActivity(), new ActionCallBack() {
            @Override
            public void ok(Object object) {

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
