package com.example.ruolan.letgo.Jsoup.Action;

import android.content.Context;

import com.example.ruolan.letgo.R;
import com.example.ruolan.letgo.bean.BookModel;
import com.example.ruolan.letgo.bean.Dish;
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
 * Created by liuwen on 2017/7/24.
 */
public class HomePageAction {


    //获取首页封面图片
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
                    callback.failed(context.getResources().getString(R.string.add_failed));
                }
            }
        });
    }

    //获取首页封面文字
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


    // 获取首页编辑推荐
    public static void searchQiDianEditRecommendation(final Context context, final ActionCallBack callBack) {
        Observable.create(new ObservableOnSubscribe<List<Dish>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Dish>> e) throws Exception {
                e.onNext(HtmlParserUtil.searchQIDianType());
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<Dish>>() {
            @Override
            public void accept(@NonNull List<Dish> list) throws Exception {
                if (list != null && list.size() != 0) {
                    callBack.ok(list);
                } else {
                    callBack.failed(context.getResources().getString(R.string.add_failed));
                }
            }
        });
    }


    // 获取首页最新更新
    public static void searchQiDianNewUpdate(final Context context, final ActionCallBack callBack) {
        Observable.create(new ObservableOnSubscribe<List<BookModel>>() {
            @Override
            public void subscribe(ObservableEmitter<List<BookModel>> e) throws Exception {
                e.onNext(HtmlParserUtil.searchQIDianNewUpdate());
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


    // 获取起点首页新书推荐和已完结作品
    public static void searchQiDianNewBookReComm(final Context context, final ActionCallBack callBack) {
        Observable.create(new ObservableOnSubscribe<List<BookModel>>() {
            @Override
            public void subscribe(ObservableEmitter<List<BookModel>> e) throws Exception {
                e.onNext(HtmlParserUtil.searchQIDianNewBookReComm());
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


    // 获取首页限时免费
    public static void searchQiDianAppsFree(final Context context, final ActionCallBack callBack) {
        Observable.create(new ObservableOnSubscribe<List<BookModel>>() {
            @Override
            public void subscribe(ObservableEmitter<List<BookModel>> e) throws Exception {
                e.onNext(HtmlParserUtil.searchQIDianAppsFree());
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
