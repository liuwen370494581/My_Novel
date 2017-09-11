package com.example.ruolan.letgo.Jsoup.Action;

import android.content.Context;
import android.os.Environment;

import com.example.ruolan.letgo.R;
import com.example.ruolan.letgo.bean.BookModel;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.schedulers.IoScheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by liuwen on 2017/8/24.
 */
public class LoadLocalFileAction {


    public static void searchLocalFile(final Context context, final ActionCallBack callBack) {
        Observable.create(new ObservableOnSubscribe<File>() {
            @Override
            public void subscribe(ObservableEmitter<File> e) throws Exception {
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    searchBook(e, new File(Environment.getExternalStorageDirectory().getAbsolutePath()));
                }
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<File>() {
            @Override
            public void accept(@NonNull File list) throws Exception {
                if (list.exists()) {
                    callBack.ok(list);
                } else {
                    callBack.failed(context.getString(R.string.add_failed));
                }
            }
        });
    }

    //搜索本地文件
    private static void searchBook(ObservableEmitter<File> e, File parentFile) {
        if (null != parentFile && parentFile.listFiles().length > 0) {
            File[] childFiles = parentFile.listFiles();
            for (int i = 0; i < childFiles.length; i++) {
                if (childFiles[i].isFile() && childFiles[i].getName().substring(childFiles[i].getName().lastIndexOf(".") + 1).equalsIgnoreCase("txt") && childFiles[i].length() > 100 * 1024) {   //100kb
                    e.onNext(childFiles[i]);
                    continue;
                }
                if (childFiles[i].isDirectory() && childFiles[i].listFiles().length > 0) {
                    searchBook(e, childFiles[i]);
                }
            }
        }

    }
}
