package star.liuwen.com.novel_lw.Jsoup.Action;

import android.content.Context;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import star.liuwen.com.novel_lw.Bean.Dish;
import star.liuwen.com.novel_lw.Jsoup.HtmlParserUtil;
import star.liuwen.com.novel_lw.R;
import star.liuwen.com.novel_lw.Utils.ToastUtils;

/**
 * Created by liuwen on 2017/6/22.
 */
public class AddBookActivityAction {

    public static void AddBook(final Context context, final ActionCallBack callBack) {
        Observable.create(new ObservableOnSubscribe<List<Dish>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Dish>> e) throws Exception {
                e.onNext(HtmlParserUtil.searchBook());
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<Dish>>() {
            @Override
            public void accept(@NonNull List<Dish> dishs) throws Exception {
                if (dishs != null) {
                    callBack.ok(dishs);
                } else {
                    ToastUtils.showToast(context, context.getResources().getString(R.string.add_failed));
                }
            }
        });
    }
}
