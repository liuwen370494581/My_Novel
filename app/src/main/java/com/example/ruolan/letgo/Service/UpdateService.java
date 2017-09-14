package com.example.ruolan.letgo.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;

import com.example.ruolan.letgo.Dao.DaoShelfBook;
import com.example.ruolan.letgo.EventBus.C;
import com.example.ruolan.letgo.EventBus.Event;
import com.example.ruolan.letgo.EventBus.EventBusUtil;
import com.example.ruolan.letgo.MainActivity;
import com.example.ruolan.letgo.R;
import com.example.ruolan.letgo.Utils.NetworkUtils;
import com.example.ruolan.letgo.bean.BookModel;
import com.example.ruolan.letgo.bean.HtmlParserUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuwen on 2017/9/7.
 * 更新书籍的服务
 */
public class UpdateService extends Service {
    private static NotificationManager mNotifyManager;
    private List<BookModel> localBookList;//本地数据库
    private BookModel mBookModel, NetWorkModel;//本地数据  网络数据

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 112) {
                //做一些通知效果 比较两个列表是否相同
                List<BookModel> netWorkList = DaoShelfBook.query();
                if (localBookList.size() > 0 && netWorkList.size() > 0) {
                    for (int i = 0; i < localBookList.size(); i++) {
                        BookModel localBook = localBookList.get(i);
                        for (int j = 0; j < netWorkList.size(); j++) {
                            BookModel netWorkModel = netWorkList.get(i);
                            //比较书名是否相同 如果不相同 则发出通知栏 告诉这本书更新了
                            if (localBook.getBooKName().equals(netWorkModel.getBooKName())) {
                                if (!localBook.getBookUpdateTime().equals(netWorkModel.getBookUpdateTime())) {
                                    Intent intent = new Intent(UpdateService.this, MainActivity.class);
                                    PendingIntent pendingIntent = PendingIntent.getActivity(UpdateService.this, 0, intent, 0);
                                    NotificationCompat.Builder builder = new NotificationCompat.Builder(UpdateService.this);
                                    builder.setContentTitle(netWorkModel.getBooKName() + ">>有更新");
                                    builder.setContentText(netWorkModel.getBookUpdateTime() + " " + netWorkModel.getBookUpdateContent());
                                    builder.setWhen(System.currentTimeMillis());
                                    builder.setSmallIcon(R.mipmap.ic_fiber_new_white_36dp);
                                    builder.setColor(Color.parseColor("#EAA935"));
                                    builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.icon));
                                    builder.setAutoCancel(true);
                                    builder.setContentIntent(pendingIntent);
                                    builder.setDefaults(Notification.DEFAULT_VIBRATE);//设置震动
                                    builder.setDefaults(Notification.DEFAULT_LIGHTS);//设置呼吸灯
                                    builder.setDefaults(Notification.DEFAULT_SOUND);//设置声音
                                    Notification notification = builder.getNotification();
                                    mNotifyManager.notify(j, notification);//每本书籍来了都要发通知更新

                                }
                            }
                        }
                    }
                }

            }
        }
    };


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        localBookList = new ArrayList<>();
        //初始化消息通知栏
        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                refreshAllBookState();
                mHandler.sendEmptyMessage(112);
                EventBusUtil.sendEvent(new Event(C.EventCode.BooKService));
            }
        }).start();

        return super.onStartCommand(intent, flags, startId);

    }

    public void clearNotify() {
        if (mNotifyManager == null) {
            mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        mNotifyManager.cancelAll();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        clearNotify();
    }

    //获取每本书的更新状态
    private void refreshAllBookState() {
        localBookList = DaoShelfBook.query();
        if (localBookList.size() > 0) {
            if (NetworkUtils.isConnected(this) && (NetworkUtils.isWifiConnected(this) || NetworkUtils.mobileDataConnected(this))) {
                for (int i = 0; i < localBookList.size(); i++) {
                    mBookModel = localBookList.get(i);
                    BookModel bookModel = HtmlParserUtil.searchDetailBookUI(localBookList.get(i).getBookDetailUrl());
                    NetWorkModel = bookModel;
                    mBookModel.setBookUpdateTime(NetWorkModel.getBookUpdateTime());
                    mBookModel.setBookUpdateContent(NetWorkModel.getBookUpdateContent());
                    DaoShelfBook.update(mBookModel);
                }
            }
        }
    }
}
