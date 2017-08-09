package com.example.ruolan.letgo.Jsoup.Action;

import android.content.Context;

import com.example.ruolan.letgo.R;
import com.example.ruolan.letgo.Utils.CacheManager;
import com.example.ruolan.letgo.Utils.NetworkUtils;
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
        Observable<List<String>> cache = Observable.create(new ObservableOnSubscribe<List<String>>() {
            @Override
            public void subscribe(ObservableEmitter<List<String>> e) throws Exception {
                List<String> mPicList = CacheManager.getInstance().getCoverList();
                if (!NetworkUtils.isConnected(context) && mPicList != null && mPicList.size() != 0) {
                    e.onNext(mPicList);
                } else {
                    e.onComplete();
                }
            }
        });
        Observable<List<String>> netWork = Observable.create(new ObservableOnSubscribe<List<String>>() {
            @Override
            public void subscribe(ObservableEmitter<List<String>> e) throws Exception {
                e.onNext(HtmlParserUtil.searchQiDianCoverPic());
            }
        });

        Observable.concat(cache, netWork).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(@NonNull List<String> list) throws Exception {
                        if (list != null && list.size() != 0) {
                            callback.ok(list);
                            //加入缓存
                            CacheManager.getInstance().setCoverList(list);
                        } else {
                            callback.failed(context.getResources().getString(R.string.endLoadingmore));
                        }
                    }
                });

    }


    //获取首页封面轮播图文字
    public static void searchQiDianCover(final Context context, final ActionCallBack callback) {
        Observable<List<BookModel>> cache = Observable.create(new ObservableOnSubscribe<List<BookModel>>() {
            @Override
            public void subscribe(ObservableEmitter<List<BookModel>> e) throws Exception {
                List<BookModel> coverModelList = CacheManager.getInstance().getCoverModelList();
                if (!NetworkUtils.isConnected(context) && coverModelList != null && coverModelList.size() != 0) {
                    e.onNext(coverModelList);
                } else {
                    //没有网络并且缓存中没有数据才去网络中加载
                    e.onComplete();
                }
            }
        });

        Observable<List<BookModel>> netWork = Observable.create(new ObservableOnSubscribe<List<BookModel>>() {
            @Override
            public void subscribe(ObservableEmitter<List<BookModel>> e) throws Exception {
                e.onNext(HtmlParserUtil.searchQiDianCover());
            }
        });

        Observable.concat(cache, netWork).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<BookModel>>() {
                    @Override
                    public void accept(@NonNull List<BookModel> list) throws Exception {
                        if (list != null && list.size() != 0) {
                            callback.ok(list);
                            //加入缓存
                            CacheManager.getInstance().setCoverModelList(list);
                        } else {
                            callback.failed(context.getResources().getString(R.string.endLoadingmore));
                        }
                    }
                });
    }

    // 获取首页编辑推荐
    public static void searchQiDianEditRecommendation(final Context context, final ActionCallBack callBack) {
        Observable<List<BookModel>> cache = Observable.create(new ObservableOnSubscribe<List<BookModel>>() {
            @Override
            public void subscribe(ObservableEmitter<List<BookModel>> e) throws Exception {
                List<BookModel> editRecommendationList = CacheManager.getInstance().getEditRecommendation();
                if (!NetworkUtils.isConnected(context) && editRecommendationList != null && editRecommendationList.size() != 0) {
                    e.onNext(editRecommendationList);
                } else {
                    //没有网络并且缓存中没有数据才去网络中加载
                    e.onComplete();
                }
            }
        });

        Observable<List<BookModel>> netWork = Observable.create(new ObservableOnSubscribe<List<BookModel>>() {
            @Override
            public void subscribe(ObservableEmitter<List<BookModel>> e) throws Exception {
                e.onNext(HtmlParserUtil.searchQIDianType());
            }
        });

        Observable.concat(cache, netWork).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<BookModel>>() {
                    @Override
                    public void accept(@NonNull List<BookModel> list) throws Exception {
                        if (list != null && list.size() != 0) {
                            callBack.ok(list);
                            //加入缓存
                            CacheManager.getInstance().setEditRecommendation(list);
                        } else {
                            callBack.failed(context.getResources().getString(R.string.endLoadingmore));
                        }
                    }
                });
    }


    // 获取首页最新更新
    public static void searchQiDianNewUpdate(final Context context, final ActionCallBack callBack) {
        Observable<List<BookModel>> cache = Observable.create(new ObservableOnSubscribe<List<BookModel>>() {
            @Override
            public void subscribe(ObservableEmitter<List<BookModel>> e) throws Exception {
                List<BookModel> newUpdateList = CacheManager.getInstance().getNewUpdateList();
                if (!NetworkUtils.isConnected(context) && newUpdateList != null && newUpdateList.size() != 0) {
                    e.onNext(newUpdateList);
                } else {
                    //没有网络并且缓存中没有数据才去网络中加载
                    e.onComplete();
                }
            }
        });

        Observable<List<BookModel>> netWork = Observable.create(new ObservableOnSubscribe<List<BookModel>>() {
            @Override
            public void subscribe(ObservableEmitter<List<BookModel>> e) throws Exception {
                e.onNext(HtmlParserUtil.searchQIDianNewUpdate());
            }
        });

        Observable.concat(cache, netWork).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<BookModel>>() {
                    @Override
                    public void accept(@NonNull List<BookModel> list) throws Exception {
                        if (list != null && list.size() != 0) {
                            callBack.ok(list);
                            //加入缓存
                            CacheManager.getInstance().setNewUpdateList(list);
                        } else {
                            callBack.failed(context.getResources().getString(R.string.endLoadingmore));
                        }
                    }
                });
    }


    // 获取起点首页新书推荐和已完结作品
    public static void searchQiDianNewBookReComm(final Context context, final ActionCallBack callBack) {
        Observable<List<BookModel>> cache = Observable.create(new ObservableOnSubscribe<List<BookModel>>() {
            @Override
            public void subscribe(ObservableEmitter<List<BookModel>> e) throws Exception {
                List<BookModel> newBookFinishList = CacheManager.getInstance().getNewBookFinish();
                if (!NetworkUtils.isConnected(context) && newBookFinishList != null && newBookFinishList.size() != 0) {
                    e.onNext(newBookFinishList);
                } else {
                    //没有网络并且缓存中没有数据才去网络中加载
                    e.onComplete();
                }
            }
        });

        Observable<List<BookModel>> netWork = Observable.create(new ObservableOnSubscribe<List<BookModel>>() {
            @Override
            public void subscribe(ObservableEmitter<List<BookModel>> e) throws Exception {
                e.onNext(HtmlParserUtil.searchQIDianNewBookReComm());
            }
        });

        Observable.concat(cache, netWork).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<BookModel>>() {
                    @Override
                    public void accept(@NonNull List<BookModel> list) throws Exception {
                        if (list != null && list.size() != 0) {
                            callBack.ok(list);
                            //加入缓存
                            CacheManager.getInstance().setNewBookFinish(list);
                        } else {
                            callBack.failed(context.getResources().getString(R.string.endLoadingmore));
                        }
                    }
                });
    }

    // 获取首页限时免费
    public static void searchQiDianAppsFree(final Context context, final ActionCallBack callBack) {
        Observable<List<BookModel>> cache = Observable.create(new ObservableOnSubscribe<List<BookModel>>() {
            @Override
            public void subscribe(ObservableEmitter<List<BookModel>> e) throws Exception {
                List<BookModel> appsFreeList = CacheManager.getInstance().getAppsFree();
                if (!NetworkUtils.isConnected(context) && appsFreeList != null && appsFreeList.size() != 0) {
                    e.onNext(appsFreeList);
                } else {
                    //没有网络并且缓存中没有数据才去网络中加载
                    e.onComplete();
                }
            }
        });

        Observable<List<BookModel>> netWork = Observable.create(new ObservableOnSubscribe<List<BookModel>>() {
            @Override
            public void subscribe(ObservableEmitter<List<BookModel>> e) throws Exception {
                e.onNext(HtmlParserUtil.searchQIDianAppsFree());
            }
        });

        Observable.concat(cache, netWork).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<BookModel>>() {
                    @Override
                    public void accept(@NonNull List<BookModel> list) throws Exception {
                        if (list != null && list.size() != 0) {
                            callBack.ok(list);
                            //加入缓存
                            CacheManager.getInstance().setAppsFree(list);
                        } else {
                            callBack.failed(context.getResources().getString(R.string.endLoadingmore));
                        }
                    }
                });
    }
}
