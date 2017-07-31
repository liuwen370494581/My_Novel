package com.example.ruolan.letgo.Jsoup.Action;

import android.content.Context;

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

    public static void searchBookPic(final Context context, final String bookName, final int page, final ActionCallBack callBack) {
        Observable.create(new ObservableOnSubscribe<List<String>>() {
            @Override
            public void subscribe(ObservableEmitter<List<String>> e) throws Exception {
                e.onNext(HtmlParserUtil.searchBookPic(bookName,page));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<String>>() {
            @Override
            public void accept(@NonNull List<String> list) throws Exception {
                if (list != null && list.size() != 0) {
                    callBack.ok(list);
                } else {
                    callBack.failed(context.getResources().getString(R.string.add_failed));
                }
            }
        });
    }

  //获取详细内容
    public static void searchBook(final Context context, final String bookName,final int page, final ActionCallBack callBack) {
        Observable.create(new ObservableOnSubscribe<List<BookModel>>() {
            @Override
            public void subscribe(ObservableEmitter<List<BookModel>> e) throws Exception {
                e.onNext(HtmlParserUtil.searchBook(bookName,page));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<BookModel>>() {
            @Override
            public void accept(@NonNull List<BookModel> list) throws Exception {
                if (list != null && list.size() != 0) {
                    callBack.ok(list);
                } else {
                    callBack.failed(context.getResources().getString(R.string.add_failed));
                }
            }
        });
    }
}
