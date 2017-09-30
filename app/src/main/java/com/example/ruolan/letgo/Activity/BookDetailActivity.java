package com.example.ruolan.letgo.Activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
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
import com.example.ruolan.letgo.Dao.DaoShelfBook;
import com.example.ruolan.letgo.EventBus.C;
import com.example.ruolan.letgo.EventBus.Event;
import com.example.ruolan.letgo.EventBus.EventBusUtil;
import com.example.ruolan.letgo.Jsoup.Action.ActionCallBack;
import com.example.ruolan.letgo.Jsoup.Action.SearchBookAction;
import com.example.ruolan.letgo.R;
import com.example.ruolan.letgo.Utils.DateTimeUtils;
import com.example.ruolan.letgo.Utils.GlideUtils;
import com.example.ruolan.letgo.bean.BookModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by liuwen on 2017/7/11.
 */
public class BookDetailActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView mRecyclerView;
    private BookModel model;
    private InterestingAdapter mAdapter;
    private TextView tvBookName, tvBookAuthor, tvBookUpdateTime, tvBookMore, tvBookDesc, tvBookUpdateContent, tvBookUpdateTimeTitle;
    private Button btnAddUpdate, btnReadBook, btnTypeOne, btnTypeTwo;
    private ImageView imgBooKUrl;
    private LinearLayout lyBookUpdateContent, lyBookUpdateTime;
    private List<String> mPicList = new ArrayList<>();
    private List<BookModel> mList = new ArrayList<>();
    private String bookName = "", typeStr = "", url = "";
    private boolean isBtnNoAdd = true;
    private BookModel netWorkModel;
    private BookModel insertModel;
    private boolean flag = true;

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
        imgBooKUrl = getView(R.id.book_img);
        tvBookUpdateTimeTitle = getView(R.id.tv_update_time);
        tvBookUpdateContent = getView(R.id.update_content);
        lyBookUpdateContent = getView(R.id.ly_update_content);
        lyBookUpdateTime = getView(R.id.ly_update_time);

        mAdapter = new InterestingAdapter(this, mPicList, mList);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        insertModel = new BookModel();
        insertModel.setId(DaoShelfBook.getCount());
    }

    @Override
    protected void initData() {
        model = (BookModel) getIntent().getExtras().getSerializable(Config.INTENT_BOOK_DETAIL_LIST);
        url = getIntent().getStringExtra(Config.INTENT_BOOK_DETAIL_PIC);
        BookModel bookModel = (BookModel) getIntent().getExtras().getSerializable("BookModel");
        if (model != null) {
            bookName = model.getBooKName();
            typeStr = getIntent().getStringExtra(Config.INTENT_BOOK_TYPE);
            if (typeStr.equals("RankUi")) {
                GlideUtils.loadImage(imgBooKUrl, "http:" + url, R.mipmap.default_book, R.mipmap.default_book);
                tvBookName.setText(model.getBooKName());
                tvBookAuthor.setText(model.getBookAuthor());
                tvBookDesc.setText(model.getBookDesc());
                tvBookUpdateTime.setText(model.getBookUpdateTime());
                tvBookUpdateContent.setText(model.getBookUpdateContent());
            } else if (typeStr.equals("ClassifyUi")) {
                GlideUtils.loadImage(imgBooKUrl, "http:" + url, R.mipmap.default_book, R.mipmap.default_book);
                tvBookName.setText(model.getBooKName());
                tvBookAuthor.setText(model.getBookAuthor());
                tvBookDesc.setText(model.getBookDesc());
            } else if (typeStr.equals("AuthorUi")) {
                GlideUtils.loadImage(imgBooKUrl, "http:" + model.getBookPic(), R.mipmap.default_book, R.mipmap.default_book);
                tvBookName.setText(model.getBooKName());
                tvBookAuthor.setText(bookModel.getBookAuthor());
                tvBookDesc.setText(model.getBookDesc());
                tvBookUpdateTime.setText(model.getBookUpdateTime());
                tvBookUpdateContent.setText(model.getBookUpdateContent());
            } else if (typeStr.equals("InterestingUi")) {
                GlideUtils.loadImage(imgBooKUrl, "http:" + url, R.mipmap.default_book, R.mipmap.default_book);
                tvBookName.setText(model.getBooKName());
                tvBookAuthor.setText(model.getBookAuthor());
            } else if (typeStr.equals("shufflingUI")) {
                GlideUtils.loadImage(imgBooKUrl, "http:" + url, R.mipmap.default_book, R.mipmap.default_book);
                tvBookName.setText(model.getBooKName());
            } else if (typeStr.equals("HomeUi")) {
                GlideUtils.loadImage(imgBooKUrl, "http:" + model.getBookPic(), R.mipmap.default_book, R.mipmap.default_book);
                tvBookName.setText(model.getBooKName());
            }
        }
        for (int i = 0; i < DaoShelfBook.query().size(); i++) {
            if (DaoShelfBook.query().get(i).getBooKName().equals(bookName)) {
                flag = false;
                break;
            }
        }
        if (!flag) {
            Drawable btnDrawable = ContextCompat.getDrawable(this, R.drawable.book_btn_full_gray);
            btnAddUpdate.setBackground(btnDrawable);
            btnAddUpdate.setText(getResources().getString(R.string.no_Chase_update));
            btnAddUpdate.setTextColor(ContextCompat.getColor(this, R.color.white));
            isBtnNoAdd = false;
        } else {
            Drawable btnDrawable = ContextCompat.getDrawable(this, R.drawable.book_btn_empty);
            btnAddUpdate.setBackground(btnDrawable);
            btnAddUpdate.setText(getResources().getString(R.string.Chase_update));
            btnAddUpdate.setTextColor(ContextCompat.getColor(this, R.color.statusColor));
            isBtnNoAdd = true;
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
            }

            @Override
            public void failed(Object object) {
                hideLoadingDialog();
            }
        });

        SearchBookAction.searchDetailBookUi(this, model.getBookDetailUrl(), new ActionCallBack() {
            @Override
            public void ok(Object object) {
                netWorkModel = (BookModel) object;
                tvBookUpdateTime.setText(netWorkModel.getBookUpdateTime());
                tvBookUpdateContent.setText(netWorkModel.getBookUpdateContent());
                tvBookDesc.setText(netWorkModel.getBookDesc());
                tvBookAuthor.setText(netWorkModel.getBookAuthor());
                if (netWorkModel.getBookAuthor() != null) {
                    if (netWorkModel.getBookAuthor().split("\\|") != null) {
                        String[] type = netWorkModel.getBookAuthor().split("\\|");
                        btnTypeOne.setText(type[1]);
                    }
                }
                tvBookAuthor.setText(netWorkModel.getBookAuthor());
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
                if (typeStr.equals("HomeUi")) {
                    model.setBookAuthorUrl(netWorkModel.getBookAuthorUrl());
                }
                Intent intent = new Intent(BookDetailActivity.this, RankingShowActivity.class);
                intent.putExtra(Config.INTENT_AUTHOR_URL, model);
                startActivity(intent);
            }
        });
        btnAddUpdate.setOnClickListener(this);
        btnTypeOne.setOnClickListener(this);
        btnReadBook.setOnClickListener(this);

    }

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_book_detail;
    }

    @Override
    public void onClick(View view) {
        if (view == btnAddUpdate) {
            //追更新 添加到书架中
            addBookToShelf();
        } else if (view == btnReadBook) {
            Intent intent = new Intent(this, FullBookShowActivity.class);
            intent.putExtra(Config.INTENT_BOOK_FREE_READ, model);
            startActivity(intent);
        } else if (view == btnTypeOne) {
            Intent intent = new Intent(BookDetailActivity.this, ClassifyDetailActivity.class);
            intent.putExtra(Config.INTENT_BOOK_TYPE_URL, netWorkModel.getBookType());
            startActivity(intent);
        }
    }

    private void addBookToShelf() {
        insertModel.setBooKName(model.getBooKName());
        insertModel.setBookReadTime(DateTimeUtils.getCurrentTime_Today());
        insertModel.setBookDetailUrl(model.getBookDetailUrl());
        if (typeStr.equals("RankUi")) {
            insertModel.setBookUpdateContent(netWorkModel.getBookUpdateContent());
            insertModel.setBookUpdateTime(netWorkModel.getBookUpdateTime());
            insertModel.setBookPic(url);
        } else if (typeStr.equals("ClassifyUi")) {
            insertModel.setBookPic(url);
            insertModel.setBookUpdateContent(netWorkModel.getBookUpdateContent());
            insertModel.setBookUpdateTime(netWorkModel.getBookUpdateTime());
        } else if (typeStr.equals("InterestingUi")) {
            insertModel.setBookPic(url);
            insertModel.setBookUpdateContent(netWorkModel.getBookUpdateContent());
            insertModel.setBookUpdateTime(netWorkModel.getBookUpdateTime());
        } else if (typeStr.equals("HomeUi")) {
            insertModel.setBookUpdateContent(netWorkModel.getBookUpdateContent());
            insertModel.setBookUpdateTime(netWorkModel.getBookUpdateTime());
            insertModel.setBookPic(model.getBookPic());
        } else if (typeStr.equals("AuthorUi")) {
            insertModel.setBookUpdateContent(netWorkModel.getBookUpdateContent());
            insertModel.setBookUpdateTime(netWorkModel.getBookUpdateTime());
            insertModel.setBookPic(model.getBookPic());
        }
        if (isBtnNoAdd) {
            Drawable btnDrawable = ContextCompat.getDrawable(this, R.drawable.book_btn_full_gray);
            btnAddUpdate.setBackground(btnDrawable);
            btnAddUpdate.setText(getResources().getString(R.string.no_Chase_update));
            btnAddUpdate.setTextColor(ContextCompat.getColor(this, (R.color.white)));
            isBtnNoAdd = false;
            DaoShelfBook.insert(insertModel);
            EventBusUtil.sendEvent(new Event(C.EventCode.BookDetailAuthorToSelefAdd, insertModel));
        } else {
            Drawable btnDrawable = ContextCompat.getDrawable(this, R.drawable.book_btn_empty);
            btnAddUpdate.setBackground(btnDrawable);
            btnAddUpdate.setText(getResources().getString(R.string.Chase_update));
            btnAddUpdate.setTextColor(ContextCompat.getColor(this, R.color.statusColor));
            isBtnNoAdd = true;
            DaoShelfBook.deleteByModel(insertModel);
            EventBusUtil.sendEvent(new Event(C.EventCode.BookDetailAuthorToSelefCancel, insertModel));
        }
    }

}
