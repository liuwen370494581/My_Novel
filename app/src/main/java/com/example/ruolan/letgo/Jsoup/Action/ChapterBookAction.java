package com.example.ruolan.letgo.Jsoup.Action;

import android.content.Context;

import com.example.ruolan.letgo.R;
import com.example.ruolan.letgo.bean.ChapterListModel;
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
 * Created by liuwen on 2017/9/18.
 */
public class ChapterBookAction {

    public static void searchChapterBook(final Context context, final String webUrl, final ActionCallBack callBack) {
        Observable.create(new ObservableOnSubscribe<List<ChapterListModel>>() {
            @Override
            public void subscribe(ObservableEmitter<List<ChapterListModel>> e) throws Exception {
                e.onNext(HtmlParserUtil.searchBookChapter(webUrl));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<ChapterListModel>>() {
            @Override
            public void accept(@NonNull List<ChapterListModel> models) throws Exception {
                if (models != null && models.size() != 0) {
                    callBack.ok(models);
                } else {
                    callBack.failed(context.getResources().getString(R.string.endLoadingmore));
                }
            }
        });
    }
}
