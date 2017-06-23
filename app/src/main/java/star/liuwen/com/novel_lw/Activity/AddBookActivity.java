package star.liuwen.com.novel_lw.Activity;

import android.util.Log;
import android.view.View;

import star.liuwen.com.novel_lw.Base.BaseActivity;
import star.liuwen.com.novel_lw.Jsoup.Action.ActionCallBack;
import star.liuwen.com.novel_lw.Jsoup.Action.AddBookActivityAction;
import star.liuwen.com.novel_lw.R;
import star.liuwen.com.novel_lw.Utils.ToastUtils;

/**
 * Created by liuwen on 2017/6/22.
 */
public class AddBookActivity extends BaseActivity {

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_add_book;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {


    }

    @Override
    protected void setListener() {

    }

    public void addBook(View view) {
        AddBookActivityAction.AddBook(AddBookActivity.this, new ActionCallBack() {
            @Override
            public void ok(Object object) {
                Log.e("MainActivity", object.toString());
                ToastUtils.showToast(AddBookActivity.this, object.toString());
            }

            @Override
            public void failed(Object object) {
            }
        });
    }


}
