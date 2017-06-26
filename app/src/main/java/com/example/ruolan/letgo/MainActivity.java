package com.example.ruolan.letgo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NavUtils;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ruolan.letgo.Activity.SettingActivity;
import com.example.ruolan.letgo.bean.Tab;
import com.example.ruolan.letgo.fragment.DiscoveryFragment;
import com.example.ruolan.letgo.fragment.StackFragment;
import com.example.ruolan.letgo.fragment.SelefFragment;
import com.example.ruolan.letgo.fragment.SelectFragment;
import com.example.ruolan.letgo.widget.FragmentTabHost;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FragmentTabHost mTabHost;
    private LayoutInflater mInflater;
    private ImageView img;
    private TextView text;
    private MyToolbar mToolbar;
    private RelativeLayout reAccount, reMessage, reHistory, reCollect, reNightMode, reSetting;


    private List<Tab> mTabs = new ArrayList<>(5);

    NavigationView mNavView;
    DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initTab();
        setListener();
    }

    private void setListener() {
        reAccount.setOnClickListener(this);
        reMessage.setOnClickListener(this);
        reHistory.setOnClickListener(this);
        reCollect.setOnClickListener(this);
        reNightMode.setOnClickListener(this);
        reSetting.setOnClickListener(this);
    }

    /**
     * 初始化布局控件
     */
    private void initView() {
        mNavView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToolbar = (MyToolbar) findViewById(R.id.toolbar);
        View headView = View.inflate(MainActivity.this, R.layout.nav_layout, mNavView);
        reAccount = (RelativeLayout) headView.findViewById(R.id.profile_account);
        reMessage = (RelativeLayout) headView.findViewById(R.id.profile_message);
        reHistory = (RelativeLayout) headView.findViewById(R.id.profile_history);
        reCollect = (RelativeLayout) headView.findViewById(R.id.profile_collection);
        reNightMode = (RelativeLayout) headView.findViewById(R.id.profile_night_mode);
        reSetting = (RelativeLayout) headView.findViewById(R.id.profile_setting);

        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, null, R.string.open, R.string.close
        );
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        mToolbar.setTitle("首页");
        mToolbar.setRightIcon(getResources().getDrawable(R.mipmap.ic_menu_search));
        //搜索
        mToolbar.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "你好", Toast.LENGTH_SHORT).show();
            }
        });

        mToolbar.setLeftIcon(getResources().getDrawable(R.mipmap.drawer_menu_icon));
        mToolbar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void initTab() {
        Tab home = new Tab(R.string.selef_book, R.drawable.selector_icon_selef, SelefFragment.class);
        Tab hot = new Tab(R.string.book_care, R.drawable.selector_icon_care, SelectFragment.class);
        Tab category = new Tab(R.string.book_stack, R.drawable.selector_icon_stack, StackFragment.class);
        Tab cart = new Tab(R.string.book_disc, R.drawable.selector_icon_discover, DiscoveryFragment.class);

        mTabs.add(home);
        mTabs.add(hot);
        mTabs.add(category);
        mTabs.add(cart);

        mInflater = LayoutInflater.from(this);
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);

        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        for (Tab tab : mTabs) {
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(getString(tab.getTitle()));
            tabSpec.setIndicator(builderIndiator(tab));
            mTabHost.addTab(tabSpec, tab.getFragment(), null);
        }

        if (Build.VERSION.SDK_INT >= 11) {
            //去掉分割线
            mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        }
        mTabHost.setCurrentTab(0);

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                if (s.equals(getString(R.string.selef_book))) {
                    mToolbar.setTitle(R.string.selef_book);
                } else if (s.equals(getString(R.string.book_stack))) {
                    mToolbar.setTitle(R.string.book_stack);
                } else if (s.equals(getString(R.string.book_care))) {
                    mToolbar.setTitle(R.string.book_care);
                } else if (s.equals(getString(R.string.book_disc))) {
                    mToolbar.setTitle(R.string.book_disc);
                }
            }
        });

    }

    /**
     * 创建indiator
     *
     * @param tab
     * @return
     */
    private View builderIndiator(Tab tab) {
        View view = mInflater.inflate(R.layout.tab_indicator, null);

        img = (ImageView) view.findViewById(R.id.icon_tab);
        text = (TextView) view.findViewById(R.id.text_indicator);
        img.setBackgroundResource(tab.getImage());
        text.setText(tab.getTitle());

        return view;
    }


    @Override
    public void onClick(View view) {
        if (view == reAccount) {

        } else if (view == reMessage) {

        } else if (view == reCollect) {

        } else if (view == reHistory) {

        } else if (view == reNightMode) {

        } else if (view == reSetting) {
            startActivity(new Intent(MainActivity.this, SettingActivity.class));
        }

    }

}
