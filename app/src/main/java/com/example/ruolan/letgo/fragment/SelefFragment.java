package com.example.ruolan.letgo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ruolan.letgo.Base.BaseFragment;
import com.example.ruolan.letgo.Dao.DaoShelfBook;
import com.example.ruolan.letgo.EventBus.C;
import com.example.ruolan.letgo.EventBus.Event;
import com.example.ruolan.letgo.R;
import com.example.ruolan.letgo.Utils.GlideUtils;
import com.example.ruolan.letgo.bean.BookModel;
import com.example.ruolan.letgo.widget.SwipeMenu;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGADivider;
import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by ruolan on 2015/11/29.
 * 书架
 */
public class SelefFragment extends BaseFragment implements BGAOnItemChildClickListener {
    private RecyclerView mRecyclerView;
    private HomePageAdapter mAdapter;
    private List<BookModel> mList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        setListener();
        return view;
    }

    private void setListener() {
        mAdapter.setOnItemChildClickListener(this);
    }

    private void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.selef_recycler_view);
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
                mList.clear();
                mList.add((BookModel) event.getData());
                mAdapter.addNewData(mList);
                mRecyclerView.smoothScrollToPosition(0);
                break;
            case C.EventCode.BookDetailAuthorToSelefCancel:
                mAdapter.clear();
                mAdapter.setData(DaoShelfBook.query());
                break;
        }
    }

    @Override
    public void onItemChildClick(ViewGroup parent, View childView, int position) {
        if (childView.getId() == R.id.tv_del) {
            SwipeMenu.closeMenu();
            mAdapter.removeItem(position);
            if(DaoShelfBook.query().size()!=0){
                DaoShelfBook.deleteByModel(mList.get(position));
            }
        } else if (childView.getId() == R.id.tv_edit) {
            SwipeMenu.closeMenu();
        }
    }


    private class HomePageAdapter extends BGARecyclerViewAdapter<BookModel> {

        public HomePageAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item_selef);
        }

        @Override
        protected void setItemChildListener(BGAViewHolderHelper helper, int viewType) {
            helper.setItemChildClickListener(R.id.tv_del);
            helper.setItemChildClickListener(R.id.tv_edit);

        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, BookModel model) {
            helper.setText(R.id.book_name, model.getBooKName());
            helper.setText(R.id.book_update_content, model.getBookUpdateContent());
            helper.setText(R.id.book_update_time, model.getBookUpdateTime());
            GlideUtils.loadImage(helper.getImageView(R.id.book_img), "http:" + model.getBookPic(), R.mipmap.default_book, R.mipmap.default_book);
        }
    }
}
