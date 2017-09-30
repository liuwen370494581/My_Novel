package com.example.ruolan.letgo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ruolan.letgo.Activity.FullBookShowActivity;
import com.example.ruolan.letgo.Base.BaseFragment;
import com.example.ruolan.letgo.Base.Config;
import com.example.ruolan.letgo.Dao.DaoShelfBook;
import com.example.ruolan.letgo.EventBus.C;
import com.example.ruolan.letgo.EventBus.Event;
import com.example.ruolan.letgo.R;
import com.example.ruolan.letgo.Service.UpdateService;
import com.example.ruolan.letgo.Utils.GlideUtils;
import com.example.ruolan.letgo.Utils.NetworkUtils;
import com.example.ruolan.letgo.Utils.ToastUtils;
import com.example.ruolan.letgo.bean.BookModel;
import com.example.ruolan.letgo.bean.HtmlParserUtil;
import com.example.ruolan.letgo.widget.DefineBAGRefreshWithLoadView;
import com.example.ruolan.letgo.widget.SwipeMenuLayout;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by ruolan on 2015/11/29.
 * 书架
 */
public class SelefFragment extends BaseFragment implements BGAOnItemChildClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {
    private RecyclerView mRecyclerView;
    private HomePageAdapter mAdapter;
    private List<BookModel> mList = new ArrayList<>();

    //下拉刷新控件
    private DefineBAGRefreshWithLoadView mDefineBAGRefreshWithLoadView = null;
    private BGARefreshLayout mBGARefreshLayout;

    private BookModel mBookModel, NetWorkModel;//本地数据  网络数据
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x110) {
                mAdapter.setData(DaoShelfBook.query());
                mAdapter.notifyDataSetChanged();
                ToastUtils.showToast(getContext(), getString(R.string.update_success));
                mBGARefreshLayout.endRefreshing();
                hideLoadingDialog();
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        setListener();
        return view;
    }


    private void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.selef_recycler_view);
        mBGARefreshLayout = (BGARefreshLayout) view.findViewById(R.id.define_bga_refresh_with_load);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new HomePageAdapter(mRecyclerView);
        if (DaoShelfBook.query().size() == 0) {
            //无数据
            mAdapter.setData(mList);
        } else {
            //有数据
            mList = DaoShelfBook.query();
            mAdapter.setData(mList);
        }
        mRecyclerView.setAdapter(mAdapter);
        startService();
    }

    private void okData() {
        if (DaoShelfBook.getCount() == 0) {
            ToastUtils.showToast(getContext(), "请先添加书籍");
            mBGARefreshLayout.endRefreshing();
            return;
        }
        showLoadingDialog(getString(R.string.update_book), true, null);
        new Thread(new Runnable() {
            @Override
            public void run() {
                refreshAllBookState();
                mHandler.sendEmptyMessage(0x110);
            }
        }).start();
    }


    //获取每本书的更新状态
    private void refreshAllBookState() {
        if (DaoShelfBook.query().size() > 0) {
            if (NetworkUtils.isConnected(getContext()) && (NetworkUtils.isWifiConnected(getContext()) || NetworkUtils.mobileDataConnected(getContext()))) {
                for (int i = 0; i < DaoShelfBook.query().size(); i++) {
                    mBookModel = DaoShelfBook.query().get(i);
                    BookModel bookModel = HtmlParserUtil.searchDetailBookUI(DaoShelfBook.query().get(i).getBookDetailUrl());
                    NetWorkModel = bookModel;
                    mBookModel.setBookUpdateTime(NetWorkModel.getBookUpdateTime());
                    mBookModel.setBookUpdateContent(NetWorkModel.getBookUpdateContent());
                    DaoShelfBook.update(mBookModel);
                }
            }
        }
    }

    private void startService() {
        Intent intent = new Intent(getActivity(), UpdateService.class);
        getActivity().startService(intent);
    }

    private void setListener() {
        mAdapter.setOnItemChildClickListener(this);
        mBGARefreshLayout.setDelegate(this);
        mDefineBAGRefreshWithLoadView = new DefineBAGRefreshWithLoadView(getActivity(), true, true);
        //设置刷新样式
        mBGARefreshLayout.setRefreshViewHolder(mDefineBAGRefreshWithLoadView);
        mDefineBAGRefreshWithLoadView.setRefreshingText("更新书籍...");
        mDefineBAGRefreshWithLoadView.setPullDownRefreshText("更新书籍中...");
        mDefineBAGRefreshWithLoadView.setReleaseRefreshText("下拉刷新中...");

    }


    @Override
    public void initData() {
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    public void onEventBusCome(Event event) {
        super.onEventBusCome(event);
        switch (event.getCode()) {
            case C.EventCode.BookDetailAuthorToSelefAdd:
                mAdapter.clear();
                mAdapter.setData(DaoShelfBook.query());
                mRecyclerView.smoothScrollToPosition(0);
                break;
            case C.EventCode.BookDetailAuthorToSelefCancel:
                mAdapter.clear();
                mAdapter.setData(DaoShelfBook.query());
                mAdapter.notifyDataSetChanged();
                break;
            case C.EventCode.BooKService:
                mAdapter.clear();
                mAdapter.setData(DaoShelfBook.query());
                mAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onItemChildClick(ViewGroup parent, View childView, int position) {
        if (childView.getId() == R.id.item_tv_top) {
            BookModel bookModel = mAdapter.getItem(position);
            if(DaoShelfBook.query().size()!=0){
                mAdapter.removeItem(position);
                mAdapter.addItem(0,bookModel);
            }
        } else if (childView.getId() == R.id.ly_body) {
            Intent intent = new Intent(getActivity(), FullBookShowActivity.class);
            intent.putExtra(Config.INTENT_BOOK_FREE_READ, DaoShelfBook.query().get(position));
            startActivity(intent);
        } else if (childView.getId() == R.id.item_tv_del) {
            BookModel bookModel = mAdapter.getItem(position);
            if (DaoShelfBook.query().size() != 0) {
                mAdapter.removeItem(position);
                DaoShelfBook.deleteByModel(bookModel);
            }
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mDefineBAGRefreshWithLoadView.showLoadingMoreImg();
        okData();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }


    private class HomePageAdapter extends BGARecyclerViewAdapter<BookModel> {
        private SwipeMenuLayout mSwipeMenuLayout;

        public HomePageAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item_selef);
        }

        @Override
        protected void setItemChildListener(BGAViewHolderHelper helper, int viewType) {
            helper.setItemChildClickListener(R.id.item_tv_top);
            helper.setItemChildClickListener(R.id.item_tv_edit);
            helper.setItemChildClickListener(R.id.item_tv_del);
            helper.setItemChildClickListener(R.id.ly_body);
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, BookModel model) {
            mSwipeMenuLayout = helper.getView(R.id.item_layout_swip);
            helper.setText(R.id.book_name, model.getBooKName());
            helper.setText(R.id.book_update_content, model.getBookUpdateContent());
            helper.setText(R.id.book_update_time, model.getBookUpdateTime());
            if (null != model.getBookUpdateTime()) {
                helper.setVisibility(R.id.update, model.getBookUpdateTime().substring(0, 2).equals("今天") ? View.VISIBLE : View.GONE);
            }
            GlideUtils.loadImage(helper.getImageView(R.id.book_img), "http:" + model.getBookPic(), R.mipmap.default_book, R.mipmap.default_book);
//            helper.getView(R.id.item_tv_top).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mSwipeMenuLayout.collapseSmooth();
//                    ToastUtils.showToast(getActivity(), "置顶了");
//                }
//            });
        }
    }
}
