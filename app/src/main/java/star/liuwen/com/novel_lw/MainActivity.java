package star.liuwen.com.novel_lw;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import star.liuwen.com.novel_lw.Base.BaseActivity;
import star.liuwen.com.novel_lw.Base.Config;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        setListener();
    }

    private void initView() {
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

    }

    private void setListener() {
        if (mToolbar != null) {
            mToolbar.setNavigationIcon(R.mipmap.list);
        }
        if (mNavigationView != null) {
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            });

        }
        //抽屉的监听事件
        mNavigationView.setNavigationItemSelectedListener(this);
    }


    private void initData() {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
        switch (item.getItemId()) {
            case R.id.nav_home:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_change_theme:
                mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {

                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {

                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        SharedPreferences sp = getSharedPreferences(Config.USER_SETTINGS, MODE_PRIVATE);
                        if ((getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) ==
                                Configuration.UI_MODE_NIGHT_YES) {
                            sp.edit().putInt(Config.USER_THEME, 0).apply();
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        } else {
                            sp.edit().putInt(Config.USER_THEME, 1).apply();
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        }
                        getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
                        recreate();
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {

                    }
                });
                break;

            case R.id.nav_settings:

                break;

            case R.id.nav_add:
                break;

            case R.id.nav_del:
                break;

            case R.id.nav_about:
                break;
        }
        return true;
    }
}
