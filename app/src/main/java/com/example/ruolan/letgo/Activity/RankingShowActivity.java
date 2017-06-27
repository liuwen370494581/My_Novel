package com.example.ruolan.letgo.Activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.ruolan.letgo.Base.BaseActivity;
import com.example.ruolan.letgo.Base.Config;
import com.example.ruolan.letgo.Jsoup.Action.ActionCallBack;
import com.example.ruolan.letgo.Jsoup.Action.AddBookActivityAction;
import com.example.ruolan.letgo.Jsoup.Action.QiDianAction;
import com.example.ruolan.letgo.R;
import com.example.ruolan.letgo.Utils.ToastUtils;
import com.example.ruolan.letgo.bean.BookModel;
import com.example.ruolan.letgo.bean.RankingModel;

import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by liuwen on 2017/6/26.
 */
public class RankingShowActivity extends BaseActivity {

    private RankingModel model;
    private List<BookModel> mList;
    private RecyclerView mRecyclerView;
    private RankShowAdapter mAdapter;

    @Override
    protected void initView() {
        showLeftView();
        mRecyclerView = getView(R.id.rank_show_recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new RankShowAdapter(mRecyclerView);
    }

    @Override
    protected void initData() {
        showLoadingDialog(getString(R.string.Being_loaded), false, null);
        model = (RankingModel) getIntent().getExtras().getSerializable(Config.INTENT_RANK);
        QiDianAction.searchQiDianRanking(RankingShowActivity.this, new ActionCallBack() {
            @Override
            public void ok(Object object) {
                hideLoadingDialog();
                mList = (List<BookModel>) object;
                mAdapter.setData(mList);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void failed(Object object) {
                hideLoadingDialog();
            }
        });

        if (model != null) {
            setCenterText(model.getName());
        }
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected int setLayoutRes() {
        return R.layout.ranking_show_activity;
    }


    private class RankShowAdapter extends BGARecyclerViewAdapter<BookModel> {

        public RankShowAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item_book_ranking);
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, BookModel model) {
            helper.setText(R.id.book_name, model.getBooKName()).setText(R.id.author, model.getBookAuthor())
                    .setText(R.id.info, model.getBookDesc()).setText(R.id.update_time, model.getBookUpdateTime())
                    .setText(R.id.update_title, model.getBookUpdateContent());
        }
    }
}
