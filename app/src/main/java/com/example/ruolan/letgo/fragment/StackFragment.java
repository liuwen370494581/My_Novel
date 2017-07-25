package com.example.ruolan.letgo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ruolan.letgo.Adapter.BannerAdapter;
import com.example.ruolan.letgo.Base.BaseFragment;
import com.example.ruolan.letgo.Jsoup.Action.ActionCallBack;
import com.example.ruolan.letgo.Jsoup.Action.HomePageAction;
import com.example.ruolan.letgo.R;
import com.example.ruolan.letgo.Utils.GlideUtils;
import com.example.ruolan.letgo.bean.BookModel;
import com.example.ruolan.letgo.bean.IndexModel;
import com.hejunlin.superindicatorlibray.CircleIndicator;
import com.hejunlin.superindicatorlibray.LoopViewPager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by ruolan on 2015/11/29.
 * 书库
 */
public class StackFragment extends BaseFragment {
    private List<IndexModel> mBannerList = new ArrayList<>();
    private List<String> mBannerPicList = new ArrayList<>();
    private BannerAdapter mBannerAdapter; //头部banner
    private LoopViewPager viewpager; //头部banner
    private CircleIndicator indicator;//头部banner

    private RecyclerView mRecyclerView;
    private View headView;
    private StackAdapter mAdapter;
    private List<BookModel> mList = new ArrayList<>();

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
        mRecyclerView = (RecyclerView) view.findViewById(R.id.stack_recycler_view);
        headView = View.inflate(getActivity(), R.layout.head_banner, null);
        viewpager = (LoopViewPager) headView.findViewById(R.id.viewpager);
        indicator = (CircleIndicator) headView.findViewById(R.id.indicator);
        mAdapter = new StackAdapter(mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter.addHeaderView(headView);
        mAdapter.setData(mList);
        mRecyclerView.setAdapter(mAdapter.getHeaderAndFooterAdapter());

    }


    private void initDate() {
        showLoadingDialog(getString(R.string.Being_loaded), false, null);
        HomePageAction.searchQiDianCover(getActivity(), new ActionCallBack() {
            @Override
            public void ok(Object object) {
                mBannerList.addAll((Collection<? extends IndexModel>) object);
                mBannerAdapter = new BannerAdapter(getActivity(), mBannerList, mBannerPicList);
                viewpager.setAdapter(mBannerAdapter);
                viewpager.setLooperPic(true);
                indicator.setViewPager(viewpager);
                hideLoadingDialog();
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
            }

            @Override
            public void failed(Object object) {

            }
        });

        HomePageAction.searchQiDianNewUpdate(getActivity(), new ActionCallBack() {
            @Override
            public void ok(Object object) {
                mList.addAll((Collection<? extends BookModel>) object);
                mAdapter.setData(mList);
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

    private class StackAdapter extends BGARecyclerViewAdapter<BookModel> {

        public StackAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item_book_ranking);
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, BookModel model) {
            helper.setText(R.id.book_name, model.getBooKName());
            helper.setText(R.id.author, model.getBookAuthor());
            helper.setText(R.id.info, model.getBookDesc());
            helper.setText(R.id.update_title, model.getBookUpdateTime());
            helper.setText(R.id.update_time, model.getBookAuthorUrl());
            GlideUtils.loadImage(helper.getImageView(R.id.book_img), "http:" + model.getBookUpdateContent(), R.mipmap.default_book, R.mipmap.default_book);
        }
    }

}
