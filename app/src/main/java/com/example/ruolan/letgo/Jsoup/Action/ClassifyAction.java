package com.example.ruolan.letgo.Jsoup.Action;

import android.content.Context;

import com.example.ruolan.letgo.R;
import com.example.ruolan.letgo.Utils.ToastUtils;
import com.example.ruolan.letgo.bean.BookModel;
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

    //查找起点分类总数据
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


    public static void searchQiDianClassify(final Context context, final String webUrl, final int indexPage, final ActionCallBack callBack) {
        Observable.create(new ObservableOnSubscribe<List<BookModel>>() {
            @Override
            public void subscribe(ObservableEmitter<List<BookModel>> e) throws Exception {
                if (webUrl != null || indexPage != 0) {
                    e.onNext(HtmlParserUtil.searchQiDianClassify(webUrl, indexPage));
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


    public static void searchQiDianPicClassify(final Context context, final String webUrl, final int indexPage, final ActionCallBack callBack) {
        Observable.create(new ObservableOnSubscribe<List<String>>() {
            @Override
            public void subscribe(ObservableEmitter<List<String>> e) throws Exception {
                if (webUrl != null || indexPage != 0) {
                    e.onNext(HtmlParserUtil.searchQiDianClassifyPic(webUrl, indexPage));
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

}
