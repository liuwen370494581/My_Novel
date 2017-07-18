package com.example.ruolan.letgo.Activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ruolan.letgo.Base.BaseActivity;
import com.example.ruolan.letgo.Base.Config;
import com.example.ruolan.letgo.Enage.DataEnage;
import com.example.ruolan.letgo.R;
import com.example.ruolan.letgo.Utils.GlideUtils;
import com.example.ruolan.letgo.bean.BookModel;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by liuwen on 2017/7/11.
 */
public class BookDetailActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private BookModel model;
    private InterestingAdapter mAdapter;
    private TextView tvBookName, tvBookAuthor, tvBookUpdateTime, tvBookMore, tvBookDesc;
    private Button btnAddUpdate, btnReadBook, btnTypeOne, btnTypeTwo;
    private ImageView imgBooKUrl;


    @Override
    protected void initView() {
        showLeftView();
        setCenterText(getString(R.string.book_detail));
        mRecyclerView = getView(R.id.book_detail_recycler_view);
        tvBookName = getView(R.id.book_name);
        tvBookAuthor = getView(R.id.author);
        tvBookUpdateTime = getView(R.id.update_time);
        tvBookMore = getView(R.id.book_detail_more);
        tvBookDesc = getView(R.id.info);
        btnAddUpdate = getView(R.id.btn_add_update);
        btnReadBook = getView(R.id.btn_start_read);
        btnTypeOne = getView(R.id.type_01);
        btnTypeTwo = getView(R.id.type_02);
        imgBooKUrl = getView(R.id.book_img);

        mAdapter = new InterestingAdapter(mRecyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(manager);
        mAdapter.setData(DataEnage.getInterestingData());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        model = (BookModel) getIntent().getExtras().getSerializable(Config.INTENT_BOOK_DETAIL_LIST);
        String url = getIntent().getStringExtra(Config.INTENT_BOOK_DETAIL_PIC);
        if (model != null && url != null) {
            GlideUtils.loadImage(imgBooKUrl, "http:" + url, R.mipmap.bookimg, R.mipmap.bookimg);
            tvBookName.setText(model.getBooKName());
            tvBookAuthor.setText(model.getBookAuthor());
            tvBookDesc.setText(model.getBookDesc());
            tvBookUpdateTime.setText(model.getBookUpdateTime());
            String[] type = model.getBookAuthor().split("\\|");
            if (type[1].contains("·")) {
                String[] typeOne = type[1].split("·");
                btnTypeOne.setText(typeOne[0]);
                btnTypeTwo.setText(typeOne[1]);
            } else {
                btnTypeOne.setText(type[1]);
                btnTypeTwo.setVisibility(View.GONE);
            }

        }
    }

    @Override
    protected void setListener() {
        //作者点击页面
        tvBookAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookDetailActivity.this, RankingShowActivity.class);
                intent.putExtra(Config.INTENT_AUTHOR_URL,model);
                startActivity(intent);
            }
        });
    }

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_book_detail;
    }


    private class InterestingAdapter extends BGARecyclerViewAdapter<BookModel> {

        public InterestingAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item_interesting);
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, BookModel model) {
            GlideUtils.loadImage(helper.getImageView(R.id.book_img), model.getBookUrl(), R.mipmap.bookimg, R.mipmap.bookimg);
            helper.setText(R.id.book_name, model.getBooKName());
        }
    }
}
