package com.gavin.your3dmgame.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gavin.your3dmgame.R;
import com.gavin.your3dmgame.fragments.inner_fragments.chapter_banner;
import com.gavin.your3dmgame.fragments.inner_fragments.chapter_normal;
import com.gavin.your3dmgame.utils.Constant;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class chapterFragment extends Fragment {
    private List<Fragment> fragmentList = new ArrayList<>();;
    private TabLayout mTabLayout_chapter;
    private ViewPager mViewPager_chapter;
    private TextView mChapter_title_tv;
    private View mView;
    private Toolbar mToolbar;
    private MyframentPagerAdapter mMyframentPagerAdapter;
//    private boolean isLazy=true;

    public chapterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView == null) {
            // Inflate the layout for this fragment
            mView = inflater.inflate(R.layout.fragment_chapter, container, false);
            init();
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) mView.getParent();
        if (parent != null) {
            parent.removeView(mView);
        }
//        Log.d("chapterFragment", "懒加载:" + isLazy);
        return mView;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void init() {
        findView();
        initView();
        setListener();
    }

    private void initView() {
        initDate();
        setAdapter();
        viewPagerWithTablelayout();
    }


    private void setListener() {
        mViewPager_chapter.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mChapter_title_tv.setText(Constant.ARTICLE_TITLE[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void viewPagerWithTablelayout() {
        mTabLayout_chapter.setupWithViewPager(mViewPager_chapter);
    }

    private void setAdapter() {
        mMyframentPagerAdapter = new MyframentPagerAdapter(fragmentList, Constant.ARTICLE_TITLE, getChildFragmentManager());
        mViewPager_chapter.setAdapter(mMyframentPagerAdapter);

    }

    private void initDate() {
        Log.d("chapterFragment", "首页初始化");
        fragmentList.add(new chapter_banner());
        for (int i = 1; i <Constant.ARTICLE_TITLE.length ; i++) {
            chapter_normal chapternormal = new chapter_normal();
            Bundle bundle = new Bundle();
            bundle.putInt("typeid",Constant.ARTICLE_ID[i]);
            chapternormal.setArguments(bundle);
            fragmentList.add(chapternormal);
        }
    }

    private void findView() {
        mTabLayout_chapter = (TabLayout)mView.findViewById(R.id.mTabLayout_chapter);
        mViewPager_chapter = (ViewPager)mView.findViewById(R.id.mViewPager_chapter);
        mChapter_title_tv = (TextView)mView.findViewById(R.id.chapter_title_tv);
        mToolbar = (Toolbar)mView.findViewById(R.id.mToolbar);

    }



    public class MyframentPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> framents;
        private String[] titlelList;
//        private int mChildCount = 0;


        public MyframentPagerAdapter(List<Fragment> framents, String[] titlelList, FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
            this.titlelList = titlelList;
            this.framents = framents;
        }


       /* @Override
        public void notifyDataSetChanged() {
            mChildCount = getCount();
            super.notifyDataSetChanged();
        }
        @Override
        public int getItemPosition(Object object) {
            if ( mChildCount > 0) {
                mChildCount --;
                return POSITION_NONE;
            }
            return super.getItemPosition(object);
        }*/

        @Override
        public Fragment getItem(int position) {
            return framents.get(position);
        }

        @Override
        public int getCount() {
            return framents.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titlelList[position];
        }
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("chapterFragment"); //友盟统计页面，"chapterFragment"为页面名称，可自定义
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("chapterFragment");
    }
}
