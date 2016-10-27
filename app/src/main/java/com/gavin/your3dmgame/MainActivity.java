package com.gavin.your3dmgame;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.gavin.your3dmgame.fragments.chapterFragment;
import com.gavin.your3dmgame.fragments.forumFragment;
import com.gavin.your3dmgame.fragments.gameFragment;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.tencent.smtt.sdk.WebView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private RadioButton mRadioButton1;
    private RadioGroup mRadioGroup;
    private chapterFragment mChapterFragment;
    private forumFragment mForumFragment;
    private gameFragment mGameFragment;
    private List<Fragment> mFragmentList;
    private long mExitTime;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWindow();
//        a)网页中的视频，上屏幕的时候，可能出现闪烁的情况，需要如下设置：Activity在onCreate时需要设置:
//        getWindow().setFormat(PixelFormat.TRANSLUCENT);
//        避免输入法界面弹出后遮挡输入光标的问题
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        /** 设置是否对日志信息进行加密, 默认false(不加密). */
        MobclickAgent.enableEncrypt(true);//友盟的6.0.0版本及以后
        MobclickAgent.openActivityDurationTrack(false);//禁止默认的页面统计方式，这样将不会再自动统计Activity。
        MobclickAgent.setScenarioType(MainActivity.this, MobclickAgent.EScenarioType.E_UM_NORMAL);

        init();
    }

    private void init() {
        findView();
        initDate();
        setAdapter();
        setListener();
    }

    //初始化窗体布局
    private void initWindow() {
        SystemBarTintManager tintManager;
        //由于沉浸式状态栏需要在Android4.4.4以上才能使用
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintColor(getResources().getColor(R.color.text_checked));
            tintManager.setStatusBarTintEnabled(true);
        }
    }

    private void findView() {
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);
        mRadioButton1 = (RadioButton) findViewById(R.id.radioButton1);
        mRadioGroup = (RadioGroup) findViewById(R.id.mRadioGroup);
    }

    private void initDate() {
        mRadioButton1.setChecked(true);
        mChapterFragment = new chapterFragment();
        mForumFragment = new forumFragment();
        mGameFragment = new gameFragment();
        mFragmentList = new ArrayList<>();
        mFragmentList.add(mChapterFragment);
        mFragmentList.add(mForumFragment);
        mFragmentList.add(mGameFragment);
        mForumFragment.setOnWebPreBackListener(new forumFragment.OnWebPreBackListener() {
            @Override
            public void onWebPreBack(WebView web_view) {
                mWebView = web_view;
            }
        });
    }

    private void setAdapter() {
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(new myFragmentPageAdapter(getSupportFragmentManager()));
    }

    private void setListener() {

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < group.getChildCount(); i++) {
                    RadioButton rb = (RadioButton) group.getChildAt(i);
                    if (rb.isChecked()) {
                        mViewPager.setCurrentItem(i);
                        break;
                    }
                }
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d("MainActivity", "当前选择了" + position + "");
                ((RadioButton) mRadioGroup.getChildAt(position)).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class myFragmentPageAdapter extends FragmentPagerAdapter {

        public myFragmentPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }

    //我们需要重写回退按钮的时间,当用户点击回退按钮：
    //如果不可以连续点击两次退出App,否则弹出提示Toast
    @Override
    public void onBackPressed() {
//        负责论坛页面的TBS webview的回退
        if (mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(MainActivity.this, "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);//统计时长
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        JPushInterface.onPause(this);
    }
}
