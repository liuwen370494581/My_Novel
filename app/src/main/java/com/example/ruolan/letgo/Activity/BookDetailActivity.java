package com.example.ruolan.letgo.Activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ruolan.letgo.Adapter.InterestingAdapter;
import com.example.ruolan.letgo.Base.BaseActivity;
import com.example.ruolan.letgo.Base.Config;
import com.example.ruolan.letgo.Enage.DataEnage;
import com.example.ruolan.letgo.Jsoup.Action.ActionCallBack;
import com.example.ruolan.letgo.Jsoup.Action.SearchBookAction;
import com.example.ruolan.letgo.R;
import com.example.ruolan.letgo.Utils.GlideUtils;
import com.example.ruolan.letgo.bean.BookModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by liuwen on 2017/7/11.
 */
public class BookDetailActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private BookModel model;
    private InterestingAdapter mAdapter;
    private TextView tvBookName, tvBookAuthor, tvBookUpdateTime, tvBookMore, tvBookDesc, tvBookUpdateContent, tvBookUpdateTimeTitle;
    private Button btnAddUpdate, btnReadBook, btnTypeOne, btnTypeTwo;
    private ImageView imgBooKUrl;
    private LinearLayout lyBookUpdateContent;
    private List<String> mPicList = new ArrayList<>();
    private List<BookModel> mList = new ArrayList<>();
    private String bookName = "";

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
        tvBookUpdateTimeTitle = getView(R.id.tv_update_time);
        tvBookUpdateContent = getView(R.id.update_content);
        lyBookUpdateContent = getView(R.id.ly_update_content);

        mAdapter = new InterestingAdapter(this, mPicList, mList);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        model = (BookModel) getIntent().getExtras().getSerializable(Config.INTENT_BOOK_DETAIL_LIST);
        String url = getIntent().getStringExtra(Config.INTENT_BOOK_DETAIL_PIC);
        BookModel bookModel = (BookModel) getIntent().getExtras().getSerializable("BookModel");
        if (model != null) {
            bookName = model.getBooKName();
            String typeStr = getIntent().getStringExtra(Config.INTENT_BOOK_TYPE);
            if (typeStr.equals("RankUi")) {
                GlideUtils.loadImage(imgBooKUrl, "http:" + url, R.mipmap.default_book, R.mipmap.default_book);
                tvBookName.setText(model.getBooKName());
                tvBookAuthor.setText(model.getBookAuthor());
                tvBookDesc.setText(model.getBookDesc());
                tvBookUpdateTime.setText(model.getBookUpdateTime());
                tvBookUpdateContent.setText(model.getBookUpdateContent());
                String[] type = model.getBookAuthor().split("\\|");
                if (type[1].contains("·")) {
                    String[] typeOne = type[1].split("·");
                    btnTypeOne.setText(typeOne[0]);
                    btnTypeTwo.setText(typeOne[1]);
                } else {
                    btnTypeOne.setText(type[1]);
                    btnTypeTwo.setVisibility(View.GONE);
                }
            } else if (typeStr.equals("ClassifyUi")) {
                GlideUtils.loadImage(imgBooKUrl, "http:" + url, R.mipmap.default_book, R.mipmap.default_book);
                lyBookUpdateContent.setVisibility(View.GONE);
                tvBookUpdateTimeTitle.setText("字数:");
                tvBookName.setText(model.getBooKName());
                tvBookAuthor.setText(model.getBookAuthor());
                tvBookDesc.setText(model.getBookDesc());
                tvBookUpdateTime.setText(model.getBookUpdateContent());
                String[] type = model.getBookAuthor().split("\\|");
                if (type[1].contains("·")) {
                    String[] typeOne = type[1].split("·");
                    btnTypeOne.setText(typeOne[0]);
                    btnTypeTwo.setText(typeOne[1]);
                } else {
                    btnTypeOne.setText(type[1]);
                    btnTypeTwo.setVisibility(View.GONE);
                }
            } else if (typeStr.equals("AuthorUi")) {
                GlideUtils.loadImage(imgBooKUrl, "http:" + model.getBookPic(), R.mipmap.default_book, R.mipmap.default_book);
                tvBookName.setText(model.getBooKName());
                tvBookAuthor.setText(bookModel.getBookAuthor());
                tvBookDesc.setText(model.getBookDesc());
                tvBookUpdateTime.setText(model.getBookUpdateTime());
                tvBookUpdateContent.setText(model.getBookUpdateContent());
                String[] type = bookModel.getBookAuthor().split("\\|");
                if (type[1].contains("·")) {
                    String[] typeOne = type[1].split("·");
                    btnTypeOne.setText(typeOne[0]);
                    btnTypeTwo.setText(typeOne[1]);
                } else {
                    btnTypeOne.setText(type[1]);
                    btnTypeTwo.setVisibility(View.GONE);
                }
            }else if(typeStr.equals("InterestingUi")){
                GlideUtils.loadImage(imgBooKUrl, "http:" + url, R.mipmap.default_book, R.mipmap.default_book);
                tvBookName.setText(model.getBooKName());
                tvBookAuthor.setText(model.getBookAuthor());
            }

        }
        LoadData();
    }

    private void LoadData() {
        showLoadingDialog(getString(R.string.Being_loaded), true, null);
        SearchBookAction.searchInterestingBookPic(this, bookName, new ActionCallBack() {
            @Override
            public void ok(Object object) {
                mPicList.addAll((Collection<? extends String>) object);
                mAdapter.updateDataPic(mPicList);
            }

            @Override
            public void failed(Object object) {
                hideLoadingDialog();
            }
        });

        SearchBookAction.searchInterestingBook(this, bookName, new ActionCallBack() {
            @Override
            public void ok(Object object) {
                mList.addAll((Collection<? extends BookModel>) object);
                mAdapter.updateData(mList);
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
        //作者点击页面
        tvBookAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookDetailActivity.this, RankingShowActivity.class);
                intent.putExtra(Config.INTENT_AUTHOR_URL, model);
                startActivity(intent);
            }
        });
    }

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_book_detail;
    }

}
