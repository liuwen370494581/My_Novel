package com.example.ruolan.letgo.Activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.ruolan.letgo.Adapter.RankShowAdapter;
import com.example.ruolan.letgo.Base.BaseActivity;
import com.example.ruolan.letgo.Base.Config;
import com.example.ruolan.letgo.Jsoup.Action.ActionCallBack;
import com.example.ruolan.letgo.Jsoup.Action.QiDianAction;
import com.example.ruolan.letgo.R;
import com.example.ruolan.letgo.Utils.ToastUtils;
import com.example.ruolan.letgo.bean.BookModel;
import com.example.ruolan.letgo.bean.RankingModel;
import com.example.ruolan.letgo.widget.DefineBAGRefreshWithLoadView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by liuwen on 2017/6/26.
 */
public class RankingShowActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {

    private RankingModel model;
    private List<BookModel> mList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RankShowAdapter mAdapter;
    private List<String> mPicList = new ArrayList<>();

    //下拉刷新控件
    private DefineBAGRefreshWithLoadView mDefineBAGRefreshWithLoadView = null;
    private BGARefreshLayout mBGARefreshLayout;

    private int page = 1;//先从第一页开始查询
    private int totalpage = 25; //数据只有3页 所以请求三次 一次加载20条数据
    private String webUrl;

    @Override
    protected void initView() {
        showLeftView();
        mRecyclerView = getView(R.id.rank_show_recycler_view);
        mBGARefreshLayout = getView(R.id.define_bga_refresh_with_load);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new RankShowAdapter(mList, mPicList, RankingShowActivity.this);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void initData() {
        model = (RankingModel) getIntent().getExtras().getSerializable(Config.INTENT_RANK);
        if (model != null) {
            setCenterText(model.getName());
            webUrl = model.getWebUrl();
            LoadData(webUrl, page);
        }
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
    protected int setLayoutRes() {
        return R.layout.ranking_show_activity;
    }

    private void LoadData(String webUrl, int indexPage) {
        showLoadingDialog(getString(R.string.Being_loaded), false, null);
        QiDianAction.searchQiDianPicRanking(RankingShowActivity.this, webUrl, indexPage, new ActionCallBack() {
            @Override
            public void ok(Object object) {
                mPicList.addAll((Collection<? extends String>) object);
                mAdapter.updateDataPic(mPicList);
                mBGARefreshLayout.endLoadingMore();
                hideLoadingDialog();
            }

            @Override
            public void failed(Object object) {
                ToastUtils.showToast(RankingShowActivity.this, "加载失败,请刷新");
                hideLoadingDialog();
            }
        });

        QiDianAction.searchQiDianRanking(RankingShowActivity.this, webUrl, indexPage, new ActionCallBack() {
            @Override
            public void ok(Object object) {
                mList.addAll((Collection<? extends BookModel>) object);
                mAdapter.updateData(mList);
                mBGARefreshLayout.endLoadingMore();
                hideLoadingDialog();
            }

            @Override
            public void failed(Object object) {
                ToastUtils.showToast(RankingShowActivity.this, "加载失败,请刷新");
                hideLoadingDialog();
            }
        });

    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mDefineBAGRefreshWithLoadView.showLoadingMoreImg();
        page = 1;
        mPicList.clear();
        mList.clear();
        LoadData(webUrl, page);
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
        LoadData(webUrl, page);
        mAdapter.notifyDataSetChanged();
        mBGARefreshLayout.endLoadingMore();
        return true;
    }
}
