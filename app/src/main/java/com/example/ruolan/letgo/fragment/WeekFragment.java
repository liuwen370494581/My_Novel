package com.example.ruolan.letgo.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ruolan.letgo.Activity.RankingDetailActivity;
import com.example.ruolan.letgo.Activity.RankingShowActivity;
import com.example.ruolan.letgo.Adapter.RankShowAdapter;
import com.example.ruolan.letgo.Base.BaseFragment;
import com.example.ruolan.letgo.EventBus.C;
import com.example.ruolan.letgo.EventBus.Event;
import com.example.ruolan.letgo.Jsoup.Action.ActionCallBack;
import com.example.ruolan.letgo.Jsoup.Action.QiDianAction;
import com.example.ruolan.letgo.R;
import com.example.ruolan.letgo.Utils.NetworkUtils;
import com.example.ruolan.letgo.Utils.ToastUtils;
import com.example.ruolan.letgo.bean.BookModel;
import com.example.ruolan.letgo.bean.RankingModel;
import com.example.ruolan.letgo.widget.DefineBAGRefreshWithLoadView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by liuwen on 2017/6/28.
 * 周榜
 */
public class WeekFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate {

    private RankingModel model;
    private List<BookModel> mList = new ArrayList<>();
    private List<String> mPicList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RankShowAdapter mAdapter;

    //下拉刷新控件
    private DefineBAGRefreshWithLoadView mDefineBAGRefreshWithLoadView = null;
    private BGARefreshLayout mBGARefreshLayout;

    private int page = 1;//先从第一页开始查询
    private int totalpage = 28; //数据28页 所以请求三次 一次加载20条数据 起点有bug 加载到25之后还是有数据
    private String webUrl;
    private int typePage = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RankingDetailActivity activity = (RankingDetailActivity) getActivity();
        model = activity.getRankingModel();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_week, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rank_show_recycler_view);
        mBGARefreshLayout = (BGARefreshLayout) view.findViewById(R.id.define_bga_refresh_with_load);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new RankShowAdapter(mList, mPicList, getActivity());
        mRecyclerView.setAdapter(mAdapter);

        setListener();
    }

    private void setListener() {
        mBGARefreshLayout.setDelegate(this);
        mDefineBAGRefreshWithLoadView = new DefineBAGRefreshWithLoadView(getActivity(), true, true);
        //设置刷新样式
        mBGARefreshLayout.setRefreshViewHolder(mDefineBAGRefreshWithLoadView);
        mDefineBAGRefreshWithLoadView.setRefreshingText("正在加载中...");
        mDefineBAGRefreshWithLoadView.setPullDownRefreshText("正在加载中...");
        mDefineBAGRefreshWithLoadView.setReleaseRefreshText("下拉刷新中...");
    }


    @Override
    public void initData() {
        if (model != null) {
            webUrl = model.getWebUrl();
            LoadData(webUrl, page);
        }

    }

    private void LoadData(String webUrl, int indexPage) {

        showLoadingDialog(getString(R.string.Being_loaded), true, null);
        if (!NetworkUtils.isConnected(getActivity())) {
            hideLoadingDialog();
            ToastUtils.showToast(getActivity(), "网络有问题");
            //会做一个显示网络错误的图 然后点击在加载
            return;
        }
        QiDianAction.searchQiDianPicRanking(getActivity(), webUrl, indexPage, typePage, new ActionCallBack() {
            @Override
            public void ok(Object object) {
                mPicList.addAll((Collection<? extends String>) object);
                mAdapter.updateDataPic(mPicList);
                mDefineBAGRefreshWithLoadView.updateLoadingMoreText("加载数据中,请稍等...");
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

        QiDianAction.searchQiDianRanking(getActivity(), webUrl, indexPage, typePage, new ActionCallBack() {
            @Override
            public void ok(Object object) {
                mList.addAll((Collection<? extends BookModel>) object);
                mAdapter.updateData(mList);
                mDefineBAGRefreshWithLoadView.updateLoadingMoreText("加载数据中,请稍等...");
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
