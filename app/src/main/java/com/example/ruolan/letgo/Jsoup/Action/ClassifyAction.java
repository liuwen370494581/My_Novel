package com.example.ruolan.letgo.Jsoup.Action;

import android.content.Context;
import android.util.Log;

import com.example.ruolan.letgo.R;
import com.example.ruolan.letgo.bean.ClassifyModel;
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
 * Created by liuwen on 2017/6/29.
 */
public class ClassifyAction {

    public static void searchClassify(final Context context, final ActionCallBack callback) {
        Observable.create(new ObservableOnSubscribe<List<ClassifyModel>>() {
            @Override
            public void subscribe(ObservableEmitter<List<ClassifyModel>> e) throws Exception {
                e.onNext(HtmlParserUtil.searchQIDianClassify(1));
                e.onNext(HtmlParserUtil.searchQIDianClassify(2));
                e.onNext(HtmlParserUtil.searchQIDianClassify(3));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<ClassifyModel>>() {
            @Override
            public void accept(@NonNull List<ClassifyModel> models) throws Exception {
                if (models.size() != 0) {
                    callback.ok(models);
                } else {
                    callback.failed(context.getResources().getString(R.string.add_failed));
                }
            }
        });
    }

    public static String searchQIDianCount(final String url) {
        final String[] count = {""};
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext(HtmlParserUtil.searchQIDIANClassifyCount(url));

            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String models) throws Exception {
                count[0] = models;
            }
        });
        return count[0];
    }
}
