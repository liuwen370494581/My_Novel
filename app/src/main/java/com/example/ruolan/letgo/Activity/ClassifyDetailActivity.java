package com.example.ruolan.letgo.Activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.example.ruolan.letgo.Adapter.ClassifyDetailListAdapter;
import com.example.ruolan.letgo.Adapter.RankShowAdapter;
import com.example.ruolan.letgo.Base.BaseActivity;
import com.example.ruolan.letgo.Base.Config;
import com.example.ruolan.letgo.Enage.DataEnage;
import com.example.ruolan.letgo.Jsoup.Action.ActionCallBack;
import com.example.ruolan.letgo.Jsoup.Action.ClassifyAction;
import com.example.ruolan.letgo.Jsoup.Action.QiDianAction;
import com.example.ruolan.letgo.R;
import com.example.ruolan.letgo.Utils.NetworkUtils;
import com.example.ruolan.letgo.Utils.ToastUtils;
import com.example.ruolan.letgo.bean.BookModel;
import com.example.ruolan.letgo.bean.ClassifyModel;
import com.example.ruolan.letgo.bean.IndexModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by liuwen on 2017/6/29.
 */
public class ClassifyDetailActivity extends BaseActivity {
    private ClassifyModel model;
    private RecyclerView mRecyclerView;
    private RecyclerView headRecyclerView;
    private RankShowAdapter mAdapter;
    private HeadAdapter mHeadAdapter;
    private ListView mListView;
    private ClassifyDetailListAdapter mListAdapter;
    private String webUrl;
    private List<BookModel> mList = new ArrayList<>();
    private List<String> mPicList = new ArrayList<>();
    private View mfootView;
    private int page = 1;
    private int totalPage = 20; //一次加载20条数据


    @Override
    protected void initView() {
        showLeftView();
        mRecyclerView = getView(R.id.classify_detail_recycler_view);
        View headView = View.inflate(this, R.layout.head_classify_detail, null);
        headRecyclerView = (RecyclerView) headView.findViewById(R.id.classify_recycler_view_head);
        mListView = getView(R.id.classify_detail_list_view);
        mListAdapter = new ClassifyDetailListAdapter(this, mList, mPicList);
        mfootView = View.inflate(this, R.layout.footer_bga_dodo, null);
        mfootView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));


    }

    @Override
    protected void initData() {
        model = (ClassifyModel) getIntent().getExtras().getSerializable(Config.INTENT_CLASSIFY);
        if (null != model) {
            setCenterText(model.getBookName());
            webUrl = model.getBookWebUrl();
            LoadData(page);
        }
    }


    private void LoadData(int indexPage) {
        showLoadingDialog(getString(R.string.Being_loaded), false, null);
        if (!NetworkUtils.isConnected(this)) {
            hideLoadingDialog();
            ToastUtils.showToast(this, "网络有问题");
            //会做一个显示网络错误的图 然后点击在加载
            return;
        }
        ClassifyAction.searchQiDianClassify(this, webUrl, indexPage, new ActionCallBack() {
            @Override
            public void ok(Object object) {
                mList.addAll((Collection<? extends BookModel>) object);
                mListView.addFooterView(mfootView);
                mListView.setAdapter(mListAdapter);
                mListAdapter.updateData(mList);
                hideLoadingDialog();
            }

            @Override
            public void failed(Object object) {
                hideLoadingDialog();
            }
        });


        ClassifyAction.searchQiDianPicClassify(this, Config.ORIGIN_VIP, indexPage, new ActionCallBack() {
            @Override
            public void ok(Object object) {
                mPicList.addAll((Collection<? extends String>) object);
                mListAdapter.updateDataPic(mPicList);
            }

            @Override
            public void failed(Object object) {

            }
        });
    }

    @Override
    protected void setListener() {
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int i) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount) {
//                    if (page > 1) {
//                        LoadData(page);
//                    }
//                    page++;
                }
            }
        });
    }

    @Override
    protected int setLayoutRes() {
        return R.layout.classify_detail_activity;
    }

    private class HeadAdapter extends BGARecyclerViewAdapter<IndexModel> {

        public HeadAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item_head_classify_detail);
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, IndexModel model) {
            helper.setText(R.id.item_head_name, model.getName());
        }


    }
}
