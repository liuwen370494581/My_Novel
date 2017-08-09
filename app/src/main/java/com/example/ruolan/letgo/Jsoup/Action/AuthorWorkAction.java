package com.example.ruolan.letgo.Jsoup.Action;

import android.content.Context;

import com.example.ruolan.letgo.R;
import com.example.ruolan.letgo.Utils.CacheManager;
import com.example.ruolan.letgo.Utils.NetworkUtils;
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
 * Created by liuwen on 2017/8/1.
 */
public class AuthorWorkAction {
    //搜索作者作品页面 没有加缓存的Api
    public static void searchAuthorWork(final Context context, final String webUrl, final ActionCallBack callBack) {
        Observable.create(new ObservableOnSubscribe<List<BookModel>>() {
            @Override
            public void subscribe(ObservableEmitter<List<BookModel>> e) throws Exception {
                if (webUrl != null) {
                    e.onNext(HtmlParserUtil.searchAuthorWork(webUrl));
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

    public static void searchAuthorWorks(final Context context, final String webUrl, final ActionCallBack callBack) {

        Observable<List<BookModel>> cache = Observable.create(new ObservableOnSubscribe<List<BookModel>>() {
            @Override
            public void subscribe(ObservableEmitter<List<BookModel>> e) throws Exception {
                List<BookModel> authorList = CacheManager.getInstance().getBookModelList();
                if (!NetworkUtils.isConnected(context) && authorList != null && authorList.size() != 0) {
                    e.onNext(authorList);
                } else {
                    //没有网络并且缓存中没有数据才去网络中加载
                    e.onComplete();
                }
            }
        });

        Observable<List<BookModel>> netWork = Observable.create(new ObservableOnSubscribe<List<BookModel>>() {
            @Override
            public void subscribe(ObservableEmitter<List<BookModel>> e) throws Exception {
                e.onNext(HtmlParserUtil.searchAuthorWork(webUrl));
            }
        });

        Observable.concat(cache, netWork).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<BookModel>>() {
                    @Override
                    public void accept(@NonNull List<BookModel> list) throws Exception {
                        if (list != null && list.size() != 0) {
                            callBack.ok(list);
                            //加入缓存
                            CacheManager.getInstance().setBookModelList(list);
                        } else {
                            callBack.failed(context.getResources().getString(R.string.endLoadingmore));
                        }
                    }
                });
    }


}
