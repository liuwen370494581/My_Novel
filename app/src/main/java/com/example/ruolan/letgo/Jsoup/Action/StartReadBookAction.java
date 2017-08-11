package com.example.ruolan.letgo.Jsoup.Action;

import android.content.Context;
import android.text.TextUtils;

import com.example.ruolan.letgo.R;
import com.example.ruolan.letgo.bean.BookModel;
import com.example.ruolan.letgo.bean.HtmlParserUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by liuwen on 2017/8/10.
 */
public class StartReadBookAction {

    public static void searchDetailBookUi(final Context context, final String bookDetailUrl, final ActionCallBack callback) {
        Observable.create(new ObservableOnSubscribe<BookModel>() {
            @Override
            public void subscribe(ObservableEmitter<BookModel> e) throws Exception {
                e.onNext(HtmlParserUtil.startReadBook(bookDetailUrl));
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
