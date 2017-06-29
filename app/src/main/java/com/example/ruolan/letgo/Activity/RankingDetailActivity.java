package com.example.ruolan.letgo.Activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.ruolan.letgo.Base.BaseActivity;
import com.example.ruolan.letgo.Base.BaseFragment;
import com.example.ruolan.letgo.R;
import com.example.ruolan.letgo.fragment.MonthFragment;
import com.example.ruolan.letgo.fragment.WeekFragment;
import com.example.ruolan.letgo.fragment.YearFragment;

/**
 * Created by liuwen on 2017/6/28.
 */
public class RankingDetailActivity extends BaseActivity {

    private String[] mTabTitles = new String[]{};
    private BaseFragment[] fragments = {new WeekFragment(), new MonthFragment(),
            new YearFragment()};

    @Override
    protected void initView() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);

        mTabTitles = getResources().getStringArray(R.array.tab_titles);

        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(0);
        tabLayout.setupWithViewPager(viewPager, true);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // 方案二：页面选中时才去加载数据
                // BaseFragment fragment = fragments[position];
                // fragment.initData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class FragmentAdapter extends FragmentPagerAdapter {
        // FragmentPagerAdapter与FragmentStatePagerAdapter区别
        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return mTabTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (mTabTitles != null) {
                return mTabTitles[position];
            }
            return super.getPageTitle(position);
        }

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected int setLayoutRes() {
        return R.layout.ranking_detail_activity;
    }
}
