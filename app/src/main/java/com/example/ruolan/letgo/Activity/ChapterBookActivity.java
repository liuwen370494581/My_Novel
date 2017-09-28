package com.example.ruolan.letgo.Activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.ruolan.letgo.Base.BaseActivity;
import com.example.ruolan.letgo.Base.Config;
import com.example.ruolan.letgo.Jsoup.Action.ActionCallBack;
import com.example.ruolan.letgo.Jsoup.Action.ChapterBookAction;
import com.example.ruolan.letgo.R;
import com.example.ruolan.letgo.Utils.SharedPreferencesUtil;
import com.example.ruolan.letgo.Utils.ToastUtils;
import com.example.ruolan.letgo.bean.BookModel;
import com.example.ruolan.letgo.bean.ChapterListModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by liuwen on 2017/9/18.
 */
public class ChapterBookActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private ChapterAdapter mAdapter;
    private List<ChapterListModel> mList = new ArrayList<>();
    private String chapterUrl;
    private HashMap<String, Integer> mHashMap = new HashMap<>();

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_chapter_book;
    }

    @Override
    protected void initView() {
        showLeftView();
        mRecyclerView = getView(R.id.chapter_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ChapterAdapter(mRecyclerView);
        mAdapter.setData(mList);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        BookModel model = (BookModel) getIntent().getExtras().getSerializable(Config.INTENT_BOOK_FREE_READ);
        if (null != model) {
            setCenterText(model.getBooKName());
            chapterUrl = model.getBookDetailUrl();
        }
        showLoadingDialog(getString(R.string.Being_loaded), true, null);
        ChapterBookAction.searchChapterBook(this, chapterUrl, new ActionCallBack() {
            @Override
            public void ok(Object object) {
                mAdapter.setData((List<ChapterListModel>) object);
                hideLoadingDialog();
            }

            @Override
            public void failed(Object object) {
                hideLoadingDialog();
                ToastUtils.showToast(ChapterBookActivity.this, object.toString());
            }
        });
    }

    @Override
    protected void setListener() {
        mAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {

            }
        });
    }

    private class ChapterAdapter extends BGARecyclerViewAdapter<ChapterListModel> {

        public ChapterAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item_chapter);
        }



        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, ChapterListModel model) {
            helper.setText(R.id.item_chapter_name, model.getDurChapterName());
            if (currentMap.get(model.getUrl()) == position) {
                helper.setVisibility(R.id.item_chapter_daohang, View.VISIBLE);
                helper.setVisibility(R.id.item_chapter_dian, View.GONE);
                helper.setTextColor(R.id.item_chapter_name, getResources().getColor(R.color.red));
            } else {
                helper.setVisibility(R.id.item_chapter_daohang, View.GONE);
                helper.setVisibility(R.id.item_chapter_dian, View.VISIBLE);
                helper.setTextColor(R.id.item_chapter_name, getResources().getColor(R.color.text_color_66));
            }
        }

    }
}
