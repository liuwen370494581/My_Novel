package star.liuwen.com.rxeasyhttp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import star.liuwen.com.rxeasyhttp.Bean.Test;
import star.liuwen.com.rxeasyhttp.Utils.Actions.ActionCallBack;
import star.liuwen.com.rxeasyhttp.Utils.Actions.TestActions;
import star.liuwen.com.rxeasyhttp.Utils.ToastUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onTest(View view) {
        EasyHttp.get("/categories").baseUrl("http://novel.juhe.im")
                .connectTimeout(30 * 1000)
                .execute(new SimpleCallBack<Test>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showToast(MainActivity.this, e.getCode()+"");
                    }

                    @Override
                    public void onSuccess(Test response) {
                        ToastUtils.showToast(MainActivity.this, response.getData().toString());
                    }
                });
    }
}
