package com.example.ruolan.letgo.Activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.ruolan.letgo.Base.BaseActivity;
import com.example.ruolan.letgo.Jsoup.Action.ActionCallBack;
import com.example.ruolan.letgo.Jsoup.Action.ChapterBookAction;
import com.example.ruolan.letgo.R;
import com.example.ruolan.letgo.bean.ChapterListModel;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by liuwen on 2017/9/18.
 */
public class ChapterBookActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private ChapterAdapter mAdapter;
    private List<ChapterListModel> mList = new ArrayList<>();


    @Override
    protected int setLayoutRes() {
        return R.layout.activity_chapter_book;
    }

    @Override
    protected void initView() {
        showLeftView();
        setCenterText(getString(R.string.book_chapter));
        mRecyclerView = getView(R.id.chapter_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ChapterAdapter(mRecyclerView);
        mAdapter.setData(mList);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void initData() {
        showLoadingDialog(getString(R.string.Being_loaded), true, null);
        ChapterBookAction.searchChapterBook(this, "", new ActionCallBack() {
            @Override
            public void ok(Object object) {
                mAdapter.setData((List<ChapterListModel>) object);
                hideLoadingDialog();
            }

            @Override
            public void failed(Object object) {

            }
        });
    }

    @Override
    protected void setListener() {

    }

    private class ChapterAdapter extends BGARecyclerViewAdapter<ChapterListModel> {

        public ChapterAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item_chapter);
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, ChapterListModel model) {
            helper.setText(R.id.item_chapter_name, model.getDurChapterName());
        }
    }
}
