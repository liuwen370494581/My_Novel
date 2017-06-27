package com.example.ruolan.letgo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.ruolan.letgo.Base.BaseActivity;
import com.example.ruolan.letgo.Base.Config;
import com.example.ruolan.letgo.Enage.DataEnage;
import com.example.ruolan.letgo.MyToolbar;
import com.example.ruolan.letgo.R;
import com.example.ruolan.letgo.bean.RankingModel;

import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by liuwen on 2017/6/26.
 */
public class RankingActivity extends BaseActivity implements BGAOnRVItemClickListener {
    private RecyclerView mRecyclerView;
    private RankingAdapter mAdapter;


    @Override
    protected void initView() {
        showLeftView();
        setCenterText(getString(R.string.ranking));
        mRecyclerView = getView(R.id.ranking_recycler_view);
        mAdapter = new RankingAdapter(mRecyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mAdapter.setData(DataEnage.getRankListData());
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void initData() {
    }

    @Override
    protected void setListener() {
        mAdapter.setOnRVItemClickListener(this);
    }

    @Override
    protected int setLayoutRes() {
        return R.layout.ranking_activity;
    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        RankingModel model = mAdapter.getData().get(position);
        Intent intent = new Intent(RankingActivity.this, RankingShowActivity.class);
        intent.putExtra(Config.INTENT_RANK, model);
        startActivity(intent);
    }


    private class RankingAdapter extends BGARecyclerViewAdapter<RankingModel> {


        public RankingAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item_ranking);
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, RankingModel model) {
            helper.setImageResource(R.id.item_image, model.getUrl()).setText(R.id.item_name, model.getName());
        }
    }
}
