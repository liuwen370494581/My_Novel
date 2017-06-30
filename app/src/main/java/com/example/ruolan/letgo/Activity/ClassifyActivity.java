package com.example.ruolan.letgo.Activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.ruolan.letgo.Base.BaseActivity;
import com.example.ruolan.letgo.Base.Config;
import com.example.ruolan.letgo.Enage.DataEnage;
import com.example.ruolan.letgo.R;
import com.example.ruolan.letgo.bean.ClassifyModel;
import com.example.ruolan.letgo.widget.DividerGridItemDecoration;

import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by liuwen on 2017/6/26.
 */
public class ClassifyActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private ClassifyAdapter mAdapter;
    private RecyclerView mRecyclerViewHead;
    private ClassifyHeadAdapter mAdapterHead;

    @Override
    protected void initView() {
        showLeftView();
        setCenterText(getString(R.string.classify));
        mRecyclerView = getView(R.id.classify_recycler_view);
        View headView = View.inflate(this, R.layout.head_classify, null);

        mRecyclerViewHead = (RecyclerView) headView.findViewById(R.id.classify_recycler_view_head);

        mAdapterHead = new ClassifyHeadAdapter(mRecyclerViewHead);
        mAdapter = new ClassifyAdapter(mRecyclerView);


        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerViewHead.setLayoutManager(new GridLayoutManager(this, 3));
        mAdapter.addHeaderView(headView);

        mAdapterHead.setData(DataEnage.getClassifyDataMan());
        mAdapter.setData(DataEnage.getClassifyFDataWoman());
        mRecyclerViewHead.setAdapter(mAdapterHead);
        mRecyclerView.setAdapter(mAdapter.getHeaderAndFooterAdapter());

        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        mRecyclerViewHead.addItemDecoration(new DividerGridItemDecoration(this));
    }

    @Override
    protected void initData() {


    }

    @Override
    protected void setListener() {

        mAdapterHead.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                Intent intent = new Intent(ClassifyActivity.this, ClassifyDetailActivity.class);
                intent.putExtra(Config.INTENT_CLASSIFY, mAdapterHead.getItem(position));
                startActivity(intent);
            }
        });

        mAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                Intent intent = new Intent(ClassifyActivity.this, ClassifyDetailActivity.class);
                intent.putExtra(Config.INTENT_CLASSIFY, mAdapter.getItem(position));
                startActivity(intent);
            }
        });
    }

    @Override
    protected int setLayoutRes() {
        return R.layout.classify_activity;
    }


    private class ClassifyAdapter extends BGARecyclerViewAdapter<ClassifyModel> {

        public ClassifyAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item_classify);
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, ClassifyModel model) {
            helper.setText(R.id.item_classify_txtName, model.getBookName()).setText(R.id.item_classify_count, model.getBookCount() + "本");

        }
    }


    private class ClassifyHeadAdapter extends BGARecyclerViewAdapter<ClassifyModel> {

        public ClassifyHeadAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item_classify);
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, ClassifyModel model) {
            helper.setText(R.id.item_classify_txtName, model.getBookName()).setText(R.id.item_classify_count, model.getBookCount() + "本");

        }
    }


}
