package com.example.ruolan.letgo.Jsoup.Action;

import android.content.Context;

import com.example.ruolan.letgo.R;
import com.example.ruolan.letgo.Utils.ToastUtils;
import com.example.ruolan.letgo.bean.BookModel;
import com.example.ruolan.letgo.bean.HtmlParserUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.schedulers.IoScheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by liuwen on 2017/6/27.
 */
public class QiDianAction {


    public static void searchQiDianRanking(final Context context, final ActionCallBack callBack) {
        Observable.create(new ObservableOnSubscribe<List<BookModel>>() {
            @Override
            public void subscribe(ObservableEmitter<List<BookModel>> e) throws Exception {
                e.onNext(HtmlParserUtil.searchQiDianRanking());
                //e.onNext(HtmlParserUtil.searchQiDianRankingPic());
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<BookModel>>() {
            @Override
            public void accept(@NonNull List<BookModel> models) throws Exception {
                if (models != null) {
                    callBack.ok(models);
                } else {
                    ToastUtils.showToast(context, context.getResources().getString(R.string.add_failed));
                }
            }
        });
    }


}
