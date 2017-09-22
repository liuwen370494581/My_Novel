package com.example.ruolan.letgo.Activity;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ruolan.letgo.Base.BaseActivity;
import com.example.ruolan.letgo.R;
import com.example.ruolan.letgo.Utils.ToastUtils;
import com.example.ruolan.letgo.widget.ReadBook.BookContentView;
import com.example.ruolan.letgo.widget.ReadBook.ContentSwitchView;
import com.monke.mprogressbar.MHorProgressBar;

import me.grantland.widget.AutofitTextView;

/**
 * Created by liuwen on 2017/8/10.
 */
public class FullBookShowActivity extends BaseActivity {

    private FrameLayout flContent;
    private ContentSwitchView csvBook;


    //主菜单
    private FrameLayout flMenu;//点击显示
    private View vMenuBg;//背景
    private LinearLayout llMenuTop;//上面顶部栏
    private LinearLayout llMenuBottom;//下面导航栏
    private ImageButton ivReturn;//返回键
    private ImageView ivMenuMore;//选择更多键
    private AutofitTextView atvTitle;//章节目录
    private TextView tvPre;//上一章
    private TextView tvNext;//下一章
    private MHorProgressBar hpbReadProgress;
    private LinearLayout llCatalog;//分类
    private LinearLayout llLight;//调光
    private LinearLayout llFont;//字体
    private LinearLayout llCache;//缓存
    private LinearLayout llSetting;//设置

    //主菜单动画
    private Animation menuTopIn;
    private Animation menuTopOut;
    private Animation menuBottomIn;
    private Animation menuBottomOut;


    @Override
    protected int setLayoutRes() {
        return R.layout.full_book_show_activity;
    }

    @Override
    protected void initView() {
        flContent = getView(R.id.fl_content);
        csvBook = getView(R.id.csv_book);
        flMenu = getView(R.id.fl_menu);
        vMenuBg = findViewById(R.id.v_menu_bg);
        llMenuTop = getView(R.id.ll_menu_top);
        llMenuBottom = getView(R.id.ll_menu_bottom);
        ivReturn = getView(R.id.iv_return);
        ivMenuMore = getView(R.id.iv_more);
        atvTitle = getView(R.id.atv_title);

        tvPre = getView(R.id.tv_pre);
        tvNext = getView(R.id.tv_next);
        hpbReadProgress = getView(R.id.hpb_read_progress);
        llCatalog = getView(R.id.ll_catalog);
        llLight = getView(R.id.ll_light);
        llFont = getView(R.id.ll_font);
        llCache = getView(R.id.ll_cache);
        llSetting = getView(R.id.ll_setting);
        initAnimation();
        initCsvBook();
    }

    /**
     * 初始化动画
     */
    private void initAnimation() {

        menuTopIn = AnimationUtils.loadAnimation(this, R.anim.anim_readbook_top_in);
        menuTopIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                vMenuBg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        llMenuTop.startAnimation(menuTopOut);
                        llMenuBottom.startAnimation(menuBottomOut);
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        menuBottomIn = AnimationUtils.loadAnimation(this, R.anim.anim_readbook_bottom_in);

        menuTopOut = AnimationUtils.loadAnimation(this, R.anim.anim_readbook_top_out);
        menuTopOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                vMenuBg.setOnClickListener(null);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                flMenu.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        menuBottomOut = AnimationUtils.loadAnimation(this, R.anim.anim_readbook_bottom_out);
    }

    //初始化书籍资料
    private void initCsvBook() {
        csvBook.bookReadInit(new ContentSwitchView.OnBookReadInitListener() {
            @Override
            public void success() {

            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {
        //返回键
        ivReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ivMenuMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showToast(getActivityContext(), "选择了更多");
            }
        });

        csvBook.setLoadDataListener(new ContentSwitchView.LoadDataListener() {
            @Override
            public void loaddata(BookContentView bookContentView, long tag, int chapterIndex, int pageIndex) {

            }

            @Override
            public void updateProgress(int chapterIndex, int pageIndex) {

            }

            @Override
            public String getChapterTitle(int chapterIndex) {
                return null;
            }

            @Override
            public void initData(int lineCount) {

            }

            @Override
            public void showMenu() {
                flMenu.setVisibility(View.VISIBLE);
                llMenuTop.startAnimation(menuTopIn);
                llMenuBottom.startAnimation(menuBottomIn);
            }
        });

        llCatalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity(ChapterBookActivity.class);
            }
        });
    }
}
