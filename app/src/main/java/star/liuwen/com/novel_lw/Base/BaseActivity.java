package star.liuwen.com.novel_lw.Base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import star.liuwen.com.novel_lw.Utils.ActivityKiller;

/**
 * Created by liuwen on 2017/6/21.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutRes());
        initView();
        initData();
        setListener();
        ActivityKiller.getInstance().addActivity(this);
    }

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void setListener();

    protected abstract int setLayoutRes();


    public <T extends View> T getView(int viewId) {
        return (T) this.findViewById(viewId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityKiller.getInstance().removeActivity(this);
    }
}
