package com.example.ruolan.letgo.Activity;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ruolan.letgo.Base.BaseActivity;
import com.example.ruolan.letgo.Dao.DaoBookHistory;
import com.example.ruolan.letgo.Enage.DataEnage;
import com.example.ruolan.letgo.Jsoup.Action.ActionCallBack;
import com.example.ruolan.letgo.Jsoup.Action.SearchBookAction;
import com.example.ruolan.letgo.R;
import com.example.ruolan.letgo.Utils.DateTimeUtils;
import com.example.ruolan.letgo.Utils.KeyboardUtil;
import com.example.ruolan.letgo.Utils.ToastUtils;
import com.example.ruolan.letgo.bean.Dish;

import java.lang.reflect.Method;
import java.util.ArrayList;

import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by liuwen on 2017/7/27.
 */
public class SearchBookActivity extends BaseActivity implements View.OnClickListener, BGAOnRVItemClickListener {
    private EditText editSearchBook;
    private TextView tvCancel, tvReflash, tvClear;
    private RelativeLayout reCommonBar, reReflash, reHistory;
    private boolean isShowCommonBar = true;
    private RecyclerView reflashRecyclerView, searchHistoryRecyclerView;
    private ReflashAdapter mReflashAdapter;
    private int reflashIndex = 2;
    private SearchHistoryAdapter mSearchHistoryAdapter;


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
        showLoadingDialog(getString(R.string.Being_loaded), true, null);
        SearchBookAction.searchBookPic(this, "", new ActionCallBack() {
            @Override
            public void ok(Object object) {

            }

            @Override
            public void failed(Object object) {
            }
        });

        SearchBookAction.searchBook(this, "", new ActionCallBack() {
            @Override
            public void ok(Object object) {
                hideLoadingDialog();
            }

            @Override
            public void failed(Object object) {
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
                        ToastUtils.showToast(SearchBookActivity.this, editSearchBook.getText().toString());
                        Dish dish = new Dish(DaoBookHistory.getCount(), editSearchBook.getText().toString(), DateTimeUtils.getCurrentTime_Today());
                        DaoBookHistory.insert(dish);
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
