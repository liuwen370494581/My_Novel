package com.example.ruolan.letgo.Jsoup.Action;

import android.content.Context;

import com.example.ruolan.letgo.R;
import com.example.ruolan.letgo.bean.HtmlParserUtil;
import com.example.ruolan.letgo.bean.IndexModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by liuwen on 2017/6/30.
 */
public class CoverAction {

    public static void searchQiDianCoverPic(final Context context, final ActionCallBack callback) {
        Observable.create(new ObservableOnSubscribe<List<String>>() {
            @Override
            public void subscribe(ObservableEmitter<List<String>> e) throws Exception {
                e.onNext(HtmlParserUtil.searchQiDianCoverPic());
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<String>>() {
            @Override
            public void accept(@NonNull List<String> models) throws Exception {
                if (models != null && models.size() != 0) {
                    callback.ok(models);
                } else {
                    //callback.failed(context.getResources().getString(R.string.add_failed));
                }
            }
        });
    }

    public static void searchQiDianCover(final Context context, final ActionCallBack callback) {

        Observable.create(new ObservableOnSubscribe<List<IndexModel>>() {
            @Override
            public void subscribe(ObservableEmitter<List<IndexModel>> e) throws Exception {
                e.onNext(HtmlParserUtil.searchQiDianCover());

            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<IndexModel>>() {
            @Override
            public void accept(@NonNull List<IndexModel> list) throws Exception {
                if (list != null && list.size() != 0) {
                    callback.ok(list);
                } else {
                    callback.failed(context.getResources().getString(R.string.add_failed));
                }
            }
        });

    }
}
