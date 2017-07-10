package com.example.ruolan.letgo.Activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.ruolan.letgo.Adapter.ClassifyDetailListAdapter;
import com.example.ruolan.letgo.Adapter.ClassifyShowAdapter;
import com.example.ruolan.letgo.Base.BaseActivity;
import com.example.ruolan.letgo.Base.Config;
import com.example.ruolan.letgo.Jsoup.Action.ActionCallBack;
import com.example.ruolan.letgo.Jsoup.Action.ClassifyAction;
import com.example.ruolan.letgo.R;
import com.example.ruolan.letgo.Utils.NetworkUtils;
import com.example.ruolan.letgo.Utils.ToastUtils;
import com.example.ruolan.letgo.bean.BookModel;
import com.example.ruolan.letgo.bean.ClassifyModel;
import com.example.ruolan.letgo.bean.IndexModel;
import com.example.ruolan.letgo.widget.DefineBAGRefreshWithLoadView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by liuwen on 2017/6/29.
 */
public class ClassifyDetailActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
    private ClassifyModel model;
    private RecyclerView mHeadRecyclerView;
    private RecyclerView mBodyRecyclerView;
    private HeadAdapter mHeadAdapter;
    private ClassifyShowAdapter mBodyAdapter;
    private ClassifyDetailListAdapter mListAdapter;

    private String webUrl;
    private List<BookModel> mList = new ArrayList<>();
    private List<String> mPicList = new ArrayList<>();
    private int page = 1;
    private int totalpage = 28;

    //下拉刷新控件
    private DefineBAGRefreshWithLoadView mDefineBAGRefreshWithLoadView = null;
    private BGARefreshLayout mBGARefreshLayout;


    @Override
    protected int setLayoutRes() {
        return R.layout.classify_detail_activity;
    }


    @Override
    protected void initView() {
        showLeftView();
        mHeadRecyclerView = getView(R.id.classify_detail_recycler_view_head);
        mBodyRecyclerView = getView(R.id.classify_detail_recycler_view_body);

        mBGARefreshLayout = getView(R.id.define_bga_refresh_with_load);

        mBodyAdapter = new ClassifyShowAdapter(mList, mPicList, this);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        mBodyRecyclerView.setLayoutManager(manager);
        mBodyRecyclerView.setAdapter(mBodyAdapter);

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
        ClassifyAction.searchQiDianPicClassify(this, webUrl, indexPage, new ActionCallBack() {
            @Override
            public void ok(Object object) {
                mPicList.addAll((Collection<? extends String>) object);
                mBodyAdapter.updateDataPic(mPicList);
                mDefineBAGRefreshWithLoadView.updateLoadingMoreText("加载中");
                mBGARefreshLayout.endLoadingMore();
                hideLoadingDialog();
            }


            @Override
            public void failed(Object object) {
                /** 设置文字 **/
                mDefineBAGRefreshWithLoadView.updateLoadingMoreText(object.toString());
                mBGARefreshLayout.endLoadingMore();
                hideLoadingDialog();
            }
        });


        ClassifyAction.searchQiDianClassify(this, webUrl, indexPage, new ActionCallBack() {
            @Override
            public void ok(Object object) {
                mList.addAll((Collection<? extends BookModel>) object);
                mBodyAdapter.updateData(mList);
                mDefineBAGRefreshWithLoadView.updateLoadingMoreText("加载中");
                mBGARefreshLayout.endLoadingMore();
            }

            @Override
            public void failed(Object object) {
                /** 设置文字 **/
                mDefineBAGRefreshWithLoadView.updateLoadingMoreText(object.toString());
                mBGARefreshLayout.endLoadingMore();
                hideLoadingDialog();
            }
        });
    }

    @Override
    protected void setListener() {
        mBGARefreshLayout.setDelegate(this);
        mDefineBAGRefreshWithLoadView = new DefineBAGRefreshWithLoadView(this, true, true);
        //设置刷新样式
        mBGARefreshLayout.setRefreshViewHolder(mDefineBAGRefreshWithLoadView);
        mDefineBAGRefreshWithLoadView.setRefreshingText("正在加载中...");
        mDefineBAGRefreshWithLoadView.setPullDownRefreshText("正在加载中...");
        mDefineBAGRefreshWithLoadView.setReleaseRefreshText("下拉刷新中...");
    }


    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mDefineBAGRefreshWithLoadView.showLoadingMoreImg();
        page = 1;
        mPicList.clear();
        mList.clear();
        LoadData(page);
        mBGARefreshLayout.endRefreshing();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        mDefineBAGRefreshWithLoadView.showLoadingMoreImg();
        if (page == totalpage) {
            /** 设置文字 **/
            mDefineBAGRefreshWithLoadView.updateLoadingMoreText("已经刷到底了");
            /** 隐藏图片 **/
            mDefineBAGRefreshWithLoadView.hideLoadingMoreImg();
            //当刷新当25页的数据的时候 停止刷新;
            mBGARefreshLayout.endLoadingMore();
            return true;
        }
        page++;
        LoadData(page);
        mBodyAdapter.notifyDataSetChanged();
        mBGARefreshLayout.endLoadingMore();
        return true;
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
