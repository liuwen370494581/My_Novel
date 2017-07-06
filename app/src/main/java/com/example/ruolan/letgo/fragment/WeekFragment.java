package com.example.ruolan.letgo.fragment;

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

import com.example.ruolan.letgo.Adapter.RankShowAdapter;
import com.example.ruolan.letgo.Base.BaseFragment;
import com.example.ruolan.letgo.R;
import com.example.ruolan.letgo.bean.BookModel;
import com.example.ruolan.letgo.bean.RankingModel;
import com.example.ruolan.letgo.widget.DefineBAGRefreshWithLoadView;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by liuwen on 2017/6/28.
 * 周榜
 */
public class WeekFragment extends BaseFragment {

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
    }


    @Override
    public void initData() {
        Toast.makeText(getActivity(), "加载了周", Toast.LENGTH_SHORT).show();
    }
}
