package com.gavin.your3dmgame.fragments;

import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * Created by GaVin on 2016/10/14 0014.
 */

public abstract class BaseFragment extends Fragment {

    /** Fragment当前状态是否可见 */
    protected boolean isVisible;


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }


    /**
     * 可见
     */
    protected void onVisible() {
        Log.d("BaseFragment", "可见");
        lazyLoad();
    }


    /**
     * 不可见
     */
    protected void onInvisible() {
        Log.d("BaseFragment", "不可见");

    }


    /**
     * 延迟加载
     * 子类必须重写此方法
     */
    protected abstract void lazyLoad();
}
