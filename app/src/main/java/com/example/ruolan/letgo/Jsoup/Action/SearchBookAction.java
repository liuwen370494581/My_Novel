package com.example.ruolan.letgo.Jsoup.Action;

import android.content.Context;
import android.text.TextUtils;

import com.example.ruolan.letgo.R;
import com.example.ruolan.letgo.bean.BookModel;
import com.example.ruolan.letgo.bean.HtmlParserUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by liuwen on 2017/7/28.
 */
public class SearchBookAction {

    //搜索书籍img
    public static void searchBookPic(final Context context, final String bookName, final int page, final ActionCallBack callBack) {
        Observable.create(new ObservableOnSubscribe<List<String>>() {
            @Override
            public void subscribe(ObservableEmitter<List<String>> e) throws Exception {
                e.onNext(HtmlParserUtil.searchBookPic(bookName, page));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<String>>() {
            @Override
            public void accept(@NonNull List<String> list) throws Exception {
                if (list != null && list.size() != 0) {
                    callBack.ok(list);
                } else {
                    callBack.failed(context.getResources().getString(R.string.endLoadingmore));
                }
            }
        });
    }

    //获取详细内容
    public static void searchBook(final Context context, final String bookName, final int page, final ActionCallBack callBack) {
        Observable.create(new ObservableOnSubscribe<List<BookModel>>() {
            @Override
            public void subscribe(ObservableEmitter<List<BookModel>> e) throws Exception {
                e.onNext(HtmlParserUtil.searchBook(bookName, page));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<BookModel>>() {
            @Override
            public void accept(@NonNull List<BookModel> list) throws Exception {
                if (list != null && list.size() != 0) {
                    callBack.ok(list);
                } else {
                    callBack.failed(context.getResources().getString(R.string.endLoadingmore));
                }
            }
        });
    }


    //搜索感兴趣的图书img
    public static void searchInterestingBookPic(final Context context, final String bookName, final ActionCallBack callBack) {
        Observable.create(new ObservableOnSubscribe<List<String>>() {
            @Override
            public void subscribe(ObservableEmitter<List<String>> e) throws Exception {
                if (!bookName.equals("")) {
                    e.onNext(HtmlParserUtil.searchInterestingBookPic(bookName));
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<String>>() {
            @Override
            public void accept(@NonNull List<String> models) throws Exception {
                if (models != null && models.size() != 0) {
                    callBack.ok(models);
                } else {
                    callBack.failed(context.getResources().getString(R.string.endLoadingmore));
                }
            }
        });
    }

    //搜索感兴趣的图书info
    public static void searchInterestingBook(final Context context, final String bookName, final ActionCallBack callBack) {
        Observable.create(new ObservableOnSubscribe<List<BookModel>>() {
            @Override
            public void subscribe(ObservableEmitter<List<BookModel>> e) throws Exception {
                if (!bookName.equals("")) {
                    e.onNext(HtmlParserUtil.searchInterestingBook(bookName));
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<BookModel>>() {
            @Override
            public void accept(@NonNull List<BookModel> models) throws Exception {
                if (models != null && models.size() != 0) {
                    callBack.ok(models);
                } else {
                    callBack.failed(context.getResources().getString(R.string.endLoadingmore));
                }
            }
        });
    }

    public static void searchDetailBookUi(final Context context, final String bookDetailUrl, final ActionCallBack callback) {
        Observable.create(new ObservableOnSubscribe<BookModel>() {
            @Override
            public void subscribe(ObservableEmitter<BookModel> e) throws Exception {
                if (bookDetailUrl != null && !TextUtils.isEmpty(bookDetailUrl)) {
                    e.onNext(HtmlParserUtil.searchDetailBookUI(bookDetailUrl));
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<BookModel>() {
            @Override
            public void accept(@NonNull BookModel models) throws Exception {
                if (models != null) {
                    callback.ok(models);
                } else {
                    callback.failed(context.getResources().getString(R.string.add_failed));
                }
            }
        });
    }

}
