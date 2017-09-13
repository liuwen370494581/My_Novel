package com.example.ruolan.letgo.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.ruolan.letgo.Base.Config;
import com.example.ruolan.letgo.Dao.DaoShelfBook;
import com.example.ruolan.letgo.EventBus.C;
import com.example.ruolan.letgo.EventBus.Event;
import com.example.ruolan.letgo.EventBus.EventBusUtil;
import com.example.ruolan.letgo.Jsoup.Action.ActionCallBack;
import com.example.ruolan.letgo.Jsoup.Action.SearchBookAction;
import com.example.ruolan.letgo.Utils.NetworkUtils;
import com.example.ruolan.letgo.bean.BookModel;
import com.example.ruolan.letgo.bean.HtmlParserUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by liuwen on 2017/9/7.
 * 更新书籍的服务
 */
public class UpdateService extends Service {

    private List<BookModel> localBookList;//本地数据库
    private BookModel mBookModel, NetWorkModel;//本地数据  网络数据


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        localBookList = new ArrayList<>();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        refreshAllBookState();
        return super.onStartCommand(intent, flags, startId);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    //获取每本书的更新状态
    private void refreshAllBookState() {
        localBookList = DaoShelfBook.query();
        if (localBookList.size() > 0) {
            if (NetworkUtils.isConnected(this) && (NetworkUtils.isWifiConnected(this) || NetworkUtils.mobileDataConnected(this))) {
                for (int i = 0; i < localBookList.size(); i++) {
                    mBookModel = localBookList.get(i);
                    SearchBookAction.searchDetailBookUi(this, mBookModel.getBookDetailUrl(), new ActionCallBack() {
                        @Override
                        public void ok(Object object) {
                            NetWorkModel = (BookModel) object;
                            mBookModel.setBookUpdateTime(NetWorkModel.getBookUpdateTime());
                            mBookModel.setBookUpdateContent(NetWorkModel.getBookUpdateContent());
                            DaoShelfBook.update(mBookModel);
                            EventBusUtil.sendEvent(new Event(C.EventCode.BooKService));
                        }

                        @Override
                        public void failed(Object object) {

                        }
                    });
                }
            }
        }
    }
}
