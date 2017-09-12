package com.example.ruolan.letgo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ruolan.letgo.Adapter.HomeUIAdapter;
import com.example.ruolan.letgo.Base.BaseFragment;
import com.example.ruolan.letgo.Enage.DataEnage;
import com.example.ruolan.letgo.Jsoup.Action.ActionCallBack;
import com.example.ruolan.letgo.Jsoup.Action.HomePageAction;
import com.example.ruolan.letgo.R;
import com.example.ruolan.letgo.Utils.DensityUtil;
import com.example.ruolan.letgo.bean.BookModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by liuwen on 2017/9/5.
 */
public class StackRoomFragment extends BaseFragment {
    private RecyclerView recyclerView;
    //数据
    private List<HashMap<String, Object>> channelList = new ArrayList<>();//频道数据
    private List<BookModel> editList = new ArrayList<>();
    private List<BookModel> newUpdateList = new ArrayList<>();
    private List<BookModel> newBookList = new ArrayList<>();
    private List<BookModel> timeLimitList = new ArrayList<>();
    private List<BookModel> mBannerList = new ArrayList<>();
    private List<String> mBannerPicList = new ArrayList<>();
    private HomeUIAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        initView(view);
        okData();
        setListener();
        return view;
    }


    private void initView(View view) {
        int itemWidth = DensityUtil.getScreenWidth(getActivity());
        recyclerView = (RecyclerView) view.findViewById(R.id.stack_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new HomeUIAdapter(getActivity(), DataEnage.getChannelList(), editList, newUpdateList, newBookList, timeLimitList, mBannerList, mBannerPicList,itemWidth);
        recyclerView.setAdapter(mAdapter);

    }


    private void okData() {
        showLoadingDialog(getString(R.string.Being_loaded), true, null);
        HomePageAction.searchQiDianCover(getActivity(), new ActionCallBack() {
            @Override
            public void ok(Object object) {
                mBannerList.addAll((Collection<? extends BookModel>) object);
                mAdapter.updateBannerModelList(mBannerList);

            }

            @Override
            public void failed(Object object) {
                hideLoadingDialog();
            }
        });

        HomePageAction.searchQiDianCoverPic(getActivity(), new ActionCallBack() {
            @Override
            public void ok(Object object) {
                mBannerPicList.addAll((Collection<? extends String>) object);
                mAdapter.updateBannerList(mBannerPicList);
            }

            @Override
            public void failed(Object object) {
                hideLoadingDialog();
            }
        });

        HomePageAction.searchQiDianAppsFree(getActivity(), new ActionCallBack() {
            @Override
            public void ok(Object object) {
                timeLimitList.addAll((Collection<? extends BookModel>) object);
                mAdapter.updateTimeLimitList(timeLimitList);
            }

            @Override
            public void failed(Object object) {
                hideLoadingDialog();
            }
        });

        HomePageAction.searchQiDianEditRecommendation(getActivity(), new ActionCallBack() {
            @Override
            public void ok(Object object) {
                editList.addAll((Collection<? extends BookModel>) object);
                mAdapter.updateEditList(editList);
                hideLoadingDialog();
            }

            @Override
            public void failed(Object object) {
                hideLoadingDialog();
            }
        });

        HomePageAction.searchQiDianNewUpdate(getActivity(), new ActionCallBack() {
            @Override
            public void ok(Object object) {
                newUpdateList.addAll((Collection<? extends BookModel>) object);
                mAdapter.updateNewUpdateList(newUpdateList);
            }

            @Override
            public void failed(Object object) {
                hideLoadingDialog();
            }
        });

        HomePageAction.searchQiDianNewBookReComm(getActivity(), new ActionCallBack() {
            @Override
            public void ok(Object object) {
                newBookList.addAll((Collection<? extends BookModel>) object);
                mAdapter.updateNewBookList(newBookList);
            }

            @Override
            public void failed(Object object) {
                hideLoadingDialog();
            }
        });

    }

    private void setListener() {

    }

    @Override
    public void initData() {

    }
}
