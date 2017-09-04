package com.example.ruolan.letgo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.ruolan.letgo.Activity.BookDetailActivity;
import com.example.ruolan.letgo.Adapter.BannerAdapter;
import com.example.ruolan.letgo.Base.BaseFragment;
import com.example.ruolan.letgo.Base.Config;
import com.example.ruolan.letgo.Jsoup.Action.ActionCallBack;
import com.example.ruolan.letgo.Jsoup.Action.HomePageAction;
import com.example.ruolan.letgo.R;
import com.example.ruolan.letgo.Utils.GlideUtils;
import com.example.ruolan.letgo.bean.BookModel;
import com.hejunlin.superindicatorlibray.CircleIndicator;
import com.hejunlin.superindicatorlibray.LoopViewPager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by ruolan on 2015/11/29.
 * 书库
 */
public class StackFragment extends BaseFragment {
    private List<BookModel> mBannerList = new ArrayList<>();
    private List<String> mBannerPicList = new ArrayList<>();
    private BannerAdapter mBannerAdapter; //头部banner
    private LoopViewPager viewpager; //头部banner
    private CircleIndicator indicator;//头部banner

    private RecyclerView mRecyclerView, editRecyclerView, newUpdateRecyclerView, newBookRecommendRecyclerView;
    private View headView;
    private FreeTimeAdapter mFreeTimeAdapter;
    private EditAdapter mEditAdapter;
    private NewUpdateAdapter mNewUpdateAdapter;
    private NewBookRecommendAdapter mNewBookRecommendAdapter;
    private List<BookModel> mList = new ArrayList<>();
    private List<BookModel> editList = new ArrayList<>();
    private List<BookModel> newUpdateList = new ArrayList<>();
    private List<BookModel> newBookRecommendList = new ArrayList<>();
    private LinearLayout lyType, lyEdit, lyNewUpdate, lyNewBookRecomm;


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
        //分类模块
        typeModule();
        //编辑模块
        EditModule();
        //最新更新模块
        NewUpdateTimeModule();
        //新书推荐模块
        NewBookRecommend();
        //限时免费模块
        FreeTimeModule();

    }

    private void FreeTimeModule() {
        mFreeTimeAdapter = new FreeTimeAdapter(mRecyclerView);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mFreeTimeAdapter.addHeaderView(headView);
        mFreeTimeAdapter.setData(mList);
        mRecyclerView.setAdapter(mFreeTimeAdapter.getHeaderAndFooterAdapter());
    }

    private void NewBookRecommend() {
        newBookRecommendRecyclerView = (RecyclerView) headView.findViewById(R.id.head_body_new_book_recomm_recyclerView);
        lyNewBookRecomm = (LinearLayout) headView.findViewById(R.id.head_body_new_book);
        mNewBookRecommendAdapter = new NewBookRecommendAdapter(newBookRecommendRecyclerView);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3, LinearLayoutManager.VERTICAL, false);
        newBookRecommendRecyclerView.setLayoutManager(manager);
        mNewBookRecommendAdapter.setData(newBookRecommendList);
        newBookRecommendRecyclerView.setAdapter(mNewBookRecommendAdapter);
    }

    private void NewUpdateTimeModule() {
        lyNewUpdate = (LinearLayout) headView.findViewById(R.id.head_body_new_update);
        newUpdateRecyclerView = (RecyclerView) headView.findViewById(R.id.head_body_new_update_recyclerView);
        mNewUpdateAdapter = new NewUpdateAdapter(newUpdateRecyclerView);
        newUpdateRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mNewUpdateAdapter.setData(newUpdateList);
        newUpdateRecyclerView.setAdapter(mNewUpdateAdapter);
    }

    private void typeModule() {
        lyType = (LinearLayout) headView.findViewById(R.id.head_body_type);
    }

    private void EditModule() {
        editRecyclerView = (RecyclerView) headView.findViewById(R.id.head_body_edit_recyclerView);
        lyEdit = (LinearLayout) headView.findViewById(R.id.head_body_edit);
        mEditAdapter = new EditAdapter(editRecyclerView);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3, LinearLayoutManager.VERTICAL, false);
        editRecyclerView.setLayoutManager(manager);
        mEditAdapter.setData(editList);
        editRecyclerView.setAdapter(mEditAdapter);
    }

    private void initDate() {
        showLoadingDialog(getString(R.string.Being_loaded), true, null);
        HomePageAction.searchQiDianCover(getActivity(), new ActionCallBack() {
            @Override
            public void ok(Object object) {
                mBannerList.addAll((Collection<? extends BookModel>) object);
                mBannerAdapter = new BannerAdapter(getActivity(), mBannerList, mBannerPicList);
                viewpager.setAdapter(mBannerAdapter);
                viewpager.setLooperPic(true);
                indicator.setViewPager(viewpager);

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

        HomePageAction.searchQiDianAppsFree(getActivity(), new ActionCallBack() {
            @Override
            public void ok(Object object) {
                mList.addAll((Collection<? extends BookModel>) object);
                mFreeTimeAdapter.setData(mList);

            }

            @Override
            public void failed(Object object) {

            }
        });

        HomePageAction.searchQiDianEditRecommendation(getActivity(), new ActionCallBack() {
            @Override
            public void ok(Object object) {
                editList.addAll((Collection<? extends BookModel>) object);
                mEditAdapter.setData(editList);
                lyType.setVisibility(View.VISIBLE);
                lyEdit.setVisibility(View.VISIBLE);
                lyNewUpdate.setVisibility(View.VISIBLE);
                lyNewBookRecomm.setVisibility(View.VISIBLE);
                hideLoadingDialog();
            }

            @Override
            public void failed(Object object) {

            }
        });

        HomePageAction.searchQiDianNewUpdate(getActivity(), new ActionCallBack() {
            @Override
            public void ok(Object object) {
                newUpdateList.addAll((Collection<? extends BookModel>) object);
                mNewUpdateAdapter.setData(newUpdateList);
            }

            @Override
            public void failed(Object object) {

            }
        });

        HomePageAction.searchQiDianNewBookReComm(getActivity(), new ActionCallBack() {
            @Override
            public void ok(Object object) {
                newBookRecommendList.addAll((Collection<? extends BookModel>) object);
                mNewBookRecommendAdapter.setData(newBookRecommendList);
            }

            @Override
            public void failed(Object object) {

            }
        });


    }

    private void setListener() {
        mFreeTimeAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                Intent intent = new Intent(getActivity(), BookDetailActivity.class);
                intent.putExtra(Config.INTENT_BOOK_DETAIL_LIST, mFreeTimeAdapter.getItem(position));
                intent.putExtra(Config.INTENT_BOOK_TYPE, "HomeUi");
                mContext.startActivity(intent);
            }
        });

        mEditAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                Intent intent = new Intent(getActivity(), BookDetailActivity.class);
                intent.putExtra(Config.INTENT_BOOK_DETAIL_LIST, mEditAdapter.getItem(position));
                intent.putExtra(Config.INTENT_BOOK_TYPE, "HomeUi");
                mContext.startActivity(intent);
            }
        });

        mNewUpdateAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                Intent intent = new Intent(getActivity(), BookDetailActivity.class);
                intent.putExtra(Config.INTENT_BOOK_DETAIL_LIST, mNewUpdateAdapter.getItem(position));
                intent.putExtra(Config.INTENT_BOOK_TYPE, "HomeUi");
                mContext.startActivity(intent);
            }
        });

        mNewBookRecommendAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                Intent intent = new Intent(getActivity(), BookDetailActivity.class);
                intent.putExtra(Config.INTENT_BOOK_DETAIL_LIST, mNewBookRecommendAdapter.getItem(position));
                intent.putExtra(Config.INTENT_BOOK_TYPE, "HomeUi");
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {
        Log.e(Config.TAG, "书架页面");
    }

    private class FreeTimeAdapter extends BGARecyclerViewAdapter<BookModel> {


        public FreeTimeAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item_edit_recycler_view);
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, BookModel model) {
            helper.setText(R.id.item_edit_tv, model.getBooKName());
            helper.getView(R.id.label).setVisibility(View.VISIBLE);
            GlideUtils.loadImage(helper.getImageView(R.id.item_edit_img), "http:" + model.getBookPic(), R.mipmap.default_book, R.mipmap.default_book);
        }
    }

    private class NewBookRecommendAdapter extends BGARecyclerViewAdapter<BookModel> {

        public NewBookRecommendAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item_edit_recycler_view);
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, BookModel model) {
            helper.setText(R.id.item_edit_tv, model.getBooKName());
            GlideUtils.loadImage(helper.getImageView(R.id.item_edit_img), "http:" + model.getBookPic(), R.mipmap.default_book, R.mipmap.default_book);
        }
    }


    private class EditAdapter extends BGARecyclerViewAdapter<BookModel> {

        public EditAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item_edit_recycler_view);
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, BookModel model) {
            helper.setText(R.id.item_edit_tv, model.getBooKName());
            GlideUtils.loadImage(helper.getImageView(R.id.item_edit_img), "http:" + model.getBookPic(), R.mipmap.default_book, R.mipmap.default_book);

        }
    }


    private class NewUpdateAdapter extends BGARecyclerViewAdapter<BookModel> {

        public NewUpdateAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item_head_new_update);
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, BookModel model) {
            helper.setText(R.id.book_name, model.getBooKName());
            helper.setText(R.id.author, model.getBookAuthor());
            helper.setText(R.id.info, model.getBookDesc());
            helper.setText(R.id.update_title, model.getBookUpdateTime());
            helper.setText(R.id.update_time, model.getBookAuthorUrl());
            GlideUtils.loadImage(helper.getImageView(R.id.book_img), "http:" + model.getBookPic(), R.mipmap.default_book, R.mipmap.default_book);
        }
    }

}
