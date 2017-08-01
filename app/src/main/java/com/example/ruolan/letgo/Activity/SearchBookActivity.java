package com.example.ruolan.letgo.Activity;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ruolan.letgo.Adapter.RankShowAdapter;
import com.example.ruolan.letgo.Base.BaseActivity;
import com.example.ruolan.letgo.Base.Config;
import com.example.ruolan.letgo.Dao.DaoBookHistory;
import com.example.ruolan.letgo.Enage.DataEnage;
import com.example.ruolan.letgo.Jsoup.Action.ActionCallBack;
import com.example.ruolan.letgo.Jsoup.Action.SearchBookAction;
import com.example.ruolan.letgo.R;
import com.example.ruolan.letgo.Utils.DateTimeUtils;
import com.example.ruolan.letgo.Utils.KeyboardUtil;
import com.example.ruolan.letgo.Utils.ToastUtils;
import com.example.ruolan.letgo.bean.BookModel;
import com.example.ruolan.letgo.bean.Dish;
import com.example.ruolan.letgo.widget.DefineBAGRefreshWithLoadView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by liuwen on 2017/7/27.
 */
public class SearchBookActivity extends BaseActivity implements View.OnClickListener, BGAOnRVItemClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {
    private EditText editSearchBook;
    private TextView tvCancel, tvReflash, tvClear;
    private RelativeLayout reCommonBar, reReflash, reHistory;
    private boolean isShowCommonBar = true;
    private RecyclerView reflashRecyclerView, searchHistoryRecyclerView, searchResultRecyclerView;
    private ReflashAdapter mReflashAdapter;
    private int reflashIndex = 2;
    private SearchHistoryAdapter mSearchHistoryAdapter;
    private Dish dish;

    private RankShowAdapter mAdapter;

    //下拉刷新控件
    private DefineBAGRefreshWithLoadView mDefineBAGRefreshWithLoadView = null;
    private BGARefreshLayout mBGARefreshLayout;

    //数据
    private List<String> mPicList = new ArrayList<>();
    private List<BookModel> mList = new ArrayList<>();


    private int page = 1;//先从第一页开始查询
    private int totalpage = 100; //数据28页 所以请求三次 一次加载20条数据 起点有bug 加载到25之后还是有数据
    private String bookName = "";

    @Override

    protected void initView() {
        showLeftView();
        setCenterText(getString(R.string.search_book));
        editSearchBook = getView(R.id.activity_search_book_name);
        tvCancel = getView(R.id.activity_search_book_cancel);
        reCommonBar = getView(R.id.common_bar);
        tvReflash = getView(R.id.activity_search_book_reflash);
        tvClear = getView(R.id.activity_search_book_clear);
        reReflash = getView(R.id.activity_search_book_re_reflash);
        reHistory = getView(R.id.activity_search_book_re_history);
        //刷新模块
        ReflashModule();
        //搜索历史模块
        SearchHistory();
        //搜索结果模块
        SearchResult();
    }

    private void SearchResult() {
        searchResultRecyclerView = getView(R.id.activity_search_book_recycler_view);
        mBGARefreshLayout = getView(R.id.define_bga_refresh_with_load);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        searchResultRecyclerView.setLayoutManager(manager);
        mAdapter = new RankShowAdapter(mList, mPicList, this);
        searchResultRecyclerView.setAdapter(mAdapter);

        mBGARefreshLayout.setDelegate(this);
        mDefineBAGRefreshWithLoadView = new DefineBAGRefreshWithLoadView(this, true, true);
        //设置刷新样式
        mBGARefreshLayout.setRefreshViewHolder(mDefineBAGRefreshWithLoadView);
        mDefineBAGRefreshWithLoadView.setRefreshingText("正在加载中...");
        mDefineBAGRefreshWithLoadView.setPullDownRefreshText("正在加载中...");
        mDefineBAGRefreshWithLoadView.setReleaseRefreshText("下拉刷新中...");

    }


    private void ReflashModule() {
        reflashRecyclerView = getView(R.id.activity_search_book_reflash_recycler_view);
        GridLayoutManager manager = new GridLayoutManager(this, 3, LinearLayoutManager.HORIZONTAL, false);
        reflashRecyclerView.setLayoutManager(manager);
        mReflashAdapter = new ReflashAdapter(reflashRecyclerView);
        mReflashAdapter.setData(DataEnage.getReflashData());
        reflashRecyclerView.setAdapter(mReflashAdapter);
    }

    private void SearchHistory() {
        searchHistoryRecyclerView = getView(R.id.activity_search_book_history_recycler_view);
        searchHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSearchHistoryAdapter = new SearchHistoryAdapter(searchHistoryRecyclerView);
        if (DaoBookHistory.query().size() == 0 && DaoBookHistory.query() != null) {
            //无数据
            mSearchHistoryAdapter.setData(new ArrayList<Dish>());
        } else {
            //有数据
            mSearchHistoryAdapter.setData(DaoBookHistory.query());
        }
        searchHistoryRecyclerView.setAdapter(mSearchHistoryAdapter);

    }


    @Override
    protected void initData() {

    }

    private void LoadData(String bookName, int page) {
        showLoadingDialog(getString(R.string.Being_loaded), true, null);
        SearchBookAction.searchBookPic(this, bookName, page, new ActionCallBack() {
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

        SearchBookAction.searchBook(this, bookName, page, new ActionCallBack() {
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
    protected void setListener() {
        tvCancel.setOnClickListener(this);
        tvClear.setOnClickListener(this);
        tvReflash.setOnClickListener(this);
        mReflashAdapter.setOnRVItemClickListener(this);
        editSearchBook.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int te, KeyEvent event) {
                if (event != null) {
                    if (TextUtils.isEmpty(editSearchBook.getText().toString())) {
                        ToastUtils.showToast(SearchBookActivity.this, "请输入书名或者作者名称");
                    } else {
                        dish = new Dish(DaoBookHistory.getCount(), editSearchBook.getText().toString(), DateTimeUtils.getCurrentTime_Today());
                        DaoBookHistory.insert(dish);
                        bookName = editSearchBook.getText().toString();
                        LoadData(bookName, page);
                        reHistory.setVisibility(View.GONE);
                        reReflash.setVisibility(View.GONE);
                        searchHistoryRecyclerView.setVisibility(View.GONE);
                        reflashRecyclerView.setVisibility(View.GONE);

                    }
                    return false;
                }
                return false;
            }
        });

        editSearchBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(editSearchBook.getText().toString())) {
                    reCommonBar.setVisibility(View.GONE);
                    tvCancel.setVisibility(View.VISIBLE);
                    return;
                }

                if (isShowCommonBar) {
                    reCommonBar.setVisibility(View.GONE);
                    tvCancel.setVisibility(View.VISIBLE);
                    KeyboardUtil.isShowKeyBoard(true, editSearchBook, SearchBookActivity.this);
                    isShowCommonBar = false;
                } else {
                    reCommonBar.setVisibility(View.VISIBLE);
                    tvCancel.setVisibility(View.GONE);
                    isShowCommonBar = true;
                }
            }
        });
    }

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_search_book;
    }

    @Override
    public void onClick(View view) {
        if (view == tvCancel) {
            KeyboardUtil.hideInputMethodWindow(this, tvCancel);
            reCommonBar.setVisibility(View.VISIBLE);
            tvCancel.setVisibility(View.GONE);
            isShowCommonBar = true;
        } else if (view == tvReflash) {
            if (reflashIndex == 1) {
                mReflashAdapter.setData(DataEnage.getReflashData());
                reflashIndex = 2;
            } else if (reflashIndex == 2) {
                mReflashAdapter.setData(DataEnage.getReflashData_2());
                reflashIndex = 3;
            } else if (reflashIndex == 3) {
                mReflashAdapter.setData(DataEnage.getReflashData_3());
                reflashIndex = 1;
            }

        } else if (view == tvClear) {
            if (DaoBookHistory.query().size() != 0) {
                DaoBookHistory.deleteAllData();
                mSearchHistoryAdapter.clear();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (!isShowCommonBar) {
            KeyboardUtil.hideInputMethodWindow(this, tvCancel);
            reCommonBar.setVisibility(View.VISIBLE);
            tvCancel.setVisibility(View.GONE);
            isShowCommonBar = true;
        } else {
            finish();
        }
    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        Dish dish = new Dish(DaoBookHistory.getCount(), mReflashAdapter.getItem(position).getTitle(), DateTimeUtils.getCurrentTime_Today());
        DaoBookHistory.insert(dish);
        editSearchBook.setText(mReflashAdapter.getItem(position).getTitle());
        bookName = mReflashAdapter.getItem(position).getTitle();
        LoadData(mReflashAdapter.getItem(position).getTitle(), page);
        reHistory.setVisibility(View.GONE);
        reReflash.setVisibility(View.GONE);
        searchHistoryRecyclerView.setVisibility(View.GONE);
        reflashRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mDefineBAGRefreshWithLoadView.showLoadingMoreImg();
        page = 1;
        mPicList.clear();
        mList.clear();
        LoadData(bookName, page);
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
            mBGARefreshLayout.endLoadingMore();
            return true;
        }
        page++;
        LoadData(bookName, page);
        mAdapter.notifyDataSetChanged();
        mBGARefreshLayout.endLoadingMore();
        return true;
    }


    private class ReflashAdapter extends BGARecyclerViewAdapter<Dish> {

        public ReflashAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item_search_book_reflash);
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, Dish model) {
            helper.setText(R.id.item_name, model.getTitle());
            GradientDrawable p = (GradientDrawable) helper.getView(R.id.item_name).getBackground();
            p.setColor(Color.parseColor(DataEnage.getRandomColor()));

        }
    }

    private class SearchHistoryAdapter extends BGARecyclerViewAdapter<Dish> {

        public SearchHistoryAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item_search_book_history);
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, Dish model) {
            helper.setText(R.id.item_history_name, model.getTitle());
        }
    }
}
