package star.liuwen.com.rxeasyhttp.Utils.Actions;

import android.content.Context;
import android.widget.Toast;

import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

import star.liuwen.com.rxeasyhttp.Bean.Test;
import star.liuwen.com.rxeasyhttp.Utils.ToastUtils;

/**
 * Created by liuwen on 2017/6/26.
 */
public class TestActions {


    public static List<Test> showBookList(final Context context, final ActionCallBack callBack) {
        List<Test> list = new ArrayList<>();
        EasyHttp.get("/categories")
                .connectTimeout(30 * 1000)
                .execute(new SimpleCallBack<Object>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showToast(context, "请求失败");
                    }

                    @Override
                    public void onSuccess(Object response) {
                        callBack.ok(response);
                    }
                });


        return list;
    }
}
