package com.example.ruolan.letgo.Jsoup.Action;

import android.content.Context;

import com.example.ruolan.letgo.R;
import com.example.ruolan.letgo.Utils.CacheManager;
import com.example.ruolan.letgo.Utils.NetworkUtils;
import com.example.ruolan.letgo.bean.BookModel;
import com.example.ruolan.letgo.bean.HtmlParserUtil;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
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
                //第一种情况 当有缓存的时候 直接去缓存
                if (coverModelList != null && coverModelList.size() != 0) {
                    e.onNext(coverModelList);
                } else if (!NetworkUtils.isConnected(context) && coverModelList != null && coverModelList.size() != 0) {
                    // 第二种情况 当没有网络 没有缓存的时候 去网上加载数据
                    e.onNext(coverModelList);
                } else {
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
                if (editRecommendationList != null && editRecommendationList.size() != 0) {
                    e.onNext(editRecommendationList);
                } else if (!NetworkUtils.isConnected(context) && editRecommendationList != null && editRecommendationList.size() != 0) {
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
                if (newUpdateList != null && newUpdateList.size() != 0) {
                    e.onNext(newUpdateList);
                } else if (!NetworkUtils.isConnected(context) && newUpdateList != null && newUpdateList.size() != 0) {
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
                if (newBookFinishList != null && newBookFinishList.size() != 0) {
                    e.onNext(newBookFinishList);
                } else if (!NetworkUtils.isConnected(context) && newBookFinishList != null && newBookFinishList.size() != 0) {
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
                if (appsFreeList != null && appsFreeList.size() != 0) {
                    e.onNext(appsFreeList);
                } else if (!NetworkUtils.isConnected(context) && appsFreeList != null && appsFreeList.size() != 0) {
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


    public static void searchAllData(final Context context, final ActionCallBack callBack) {

        Flowable.create(new FlowableOnSubscribe<List<BookModel>>() {
            @Override
            public void subscribe(FlowableEmitter<List<BookModel>> e) throws Exception {
                e.onNext(HtmlParserUtil.searchQIDianType());
                e.onNext(HtmlParserUtil.searchQIDianNewUpdate());
                e.onNext(HtmlParserUtil.searchQIDianNewBookReComm());
                e.onNext(HtmlParserUtil.searchQIDianAppsFree());
                e.onComplete();
            }
        }, BackpressureStrategy.BUFFER).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<BookModel>>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(4);
            }

            @Override
            public void onNext(List<BookModel> list) {
                if (list != null || list.size() != 0) {
                    callBack.ok(list);
                }
            }

            @Override
            public void onError(Throwable t) {
                callBack.failed(t);
            }

            @Override
            public void onComplete() {

            }
        });
    }

}
