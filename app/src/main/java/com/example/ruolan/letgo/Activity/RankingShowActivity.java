package com.example.ruolan.letgo.Activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.ruolan.letgo.Base.BaseActivity;
import com.example.ruolan.letgo.Base.Config;
import com.example.ruolan.letgo.Jsoup.Action.ActionCallBack;
import com.example.ruolan.letgo.Jsoup.Action.AuthorWorkAction;
import com.example.ruolan.letgo.R;
import com.example.ruolan.letgo.Utils.GlideUtils;
import com.example.ruolan.letgo.Utils.NetworkUtils;
import com.example.ruolan.letgo.Utils.ToastUtils;
import com.example.ruolan.letgo.bean.BookModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by liuwen on 2017/6/26.
 * 是作者作品页面
 */
public class RankingShowActivity extends BaseActivity {

    private BookModel model;
    private List<BookModel> mList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private AuthorWorksAdapter mAdapter;
    private String webUrl;

    @Override
    protected void initView() {
        showLeftView();
        mRecyclerView = getView(R.id.rank_show_recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new AuthorWorksAdapter(mRecyclerView);
        mAdapter.setData(mList);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void initData() {
        model = (BookModel) getIntent().getExtras().getSerializable(Config.INTENT_AUTHOR_URL);
        if (model != null) {
            setCenterText(model.getBookAuthor());
            webUrl = model.getBookAuthorUrl();
            LoadData(webUrl);
        }
    }

    @Override
    protected void setListener() {
        mAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                Intent intent = new Intent(RankingShowActivity.this, BookDetailActivity.class);
                intent.putExtra(Config.INTENT_BOOK_DETAIL_LIST,mAdapter.getItem(position));
                intent.putExtra("BookModel", model);
                intent.putExtra(Config.INTENT_BOOK_TYPE, "AuthorUi");
                startActivity(intent);
            }
        });
    }

    @Override
    protected int setLayoutRes() {
        return R.layout.ranking_show_activity;
    }

    private void LoadData(String webUrl) {

        showLoadingDialog(getString(R.string.Being_loaded), true, null);
        if (!NetworkUtils.isConnected(this)) {
            hideLoadingDialog();
            ToastUtils.showToast(this, "网络有问题");
            //会做一个显示网络错误的图 然后点击在加载
            return;
        }

        AuthorWorkAction.searchAuthorWork(this, webUrl, new ActionCallBack() {
            @Override
            public void ok(Object object) {
                mList.addAll((Collection<? extends BookModel>) object);
                mAdapter.setData(mList);
                hideLoadingDialog();
            }

            @Override
            public void failed(Object object) {
                hideLoadingDialog();
            }
        });

    }


    private class AuthorWorksAdapter extends BGARecyclerViewAdapter<BookModel> {

        public AuthorWorksAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item_bookl_author_list);
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, BookModel model) {
            helper.setText(R.id.type, model.getBookType());
            helper.setText(R.id.book_name, model.getBooKName());
            helper.setText(R.id.info, model.getBookDesc());
            helper.setText(R.id.update_time, model.getBookUpdateTime());
            helper.setText(R.id.update_title, model.getBookUpdateContent());
            if (needTitle(position)) {
                helper.setText(R.id.item_book_author_time, model.getBookAuthorWriteTime());
                helper.setVisibility(R.id.item_book_author_time, View.VISIBLE);
                helper.setVisibility(R.id.tvDot, View.VISIBLE);
            } else {
                helper.setVisibility(R.id.item_book_author_time, View.GONE);
                helper.setVisibility(R.id.tvDot, View.GONE);
            }
            GlideUtils.loadImage(helper.getImageView(R.id.book_img), "http:" + model.getBookPic(), R.mipmap.default_book, R.mipmap.default_book);

        }


        private boolean needTitle(int position) {
            if (position == 0) {
                return true;
            }
            if (position < 0) {
                return false;

            }
            BookModel currentModel = getItem(position);
            BookModel previousModel = getItem(position - 1);
            if (currentModel == null || previousModel == null) {
                return false;
            }
            String currentData = currentModel.getBookAuthorWriteTime();
            String previousData = previousModel.getBookAuthorWriteTime();
            if (currentData.equals(previousData)) {
                return false;
            }
            return true;
        }
    }
}
