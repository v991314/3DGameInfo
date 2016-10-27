package com.gavin.your3dmgame.fragments.inner_fragments;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gavin.your3dmgame.R;
import com.gavin.your3dmgame.activity.ChapterDetailActivity;
import com.gavin.your3dmgame.adapter.Chapter_adapter;
import com.gavin.your3dmgame.entity.chapter_item;
import com.gavin.your3dmgame.fragments.BaseFragment;
import com.gavin.your3dmgame.utils.API;
import com.gavin.your3dmgame.utils.JsonUtils;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class chapter_normal extends BaseFragment {


    private LRecyclerView mChapter_recyclerView;
    private View mView;
    private int currenPage = 1;
    private List<chapter_item> mChapter_items;
    private List<chapter_item> data = new ArrayList<>();
    private Chapter_adapter mAdapter;
    private int mTypeid;
    private LRecyclerViewAdapter mRecyclerViewAdapter;
    private boolean isLazy=true;
    private ProgressDialog mDialog;
    private int mRow=15;

    //    private View mEmptyView;

    public chapter_normal() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView == null) {
            // Inflate the layout for this fragment
            mView = inflater.inflate(R.layout.fragment_chapter_normal,container,false);
            //获取到文章分类id
            mTypeid = getArguments().getInt("typeid");
            findView();
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) mView.getParent();
        if (parent != null) {
            parent.removeView(mView);
        }
        Log.d("chapter_normal", "懒加载:" + isLazy);
        return mView;
    }



    private void findView() {
        mChapter_recyclerView = (LRecyclerView) mView.findViewById(R.id.chapter_RecyclerView);
//        mEmptyView = mView.findViewById(R.id.empty_view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setView();
        setAdapter();
        setListener();
    }

    private void setView() {
        mChapter_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        mChapter_recyclerView.setRefreshing(true);
        mChapter_recyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mChapter_recyclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);

    }

    private void setAdapter() {
        mAdapter = new Chapter_adapter(getActivity(), data);
        mRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter);
        mChapter_recyclerView.setAdapter(mRecyclerViewAdapter);
    }

    private void setListener() {
        mRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), ChapterDetailActivity.class);
                String id = data.get(position).getId();
                String typeid = data.get(position).getTypeid();

                Bundle bundle = new Bundle();
                bundle.putString("id", id);//文章id
                bundle.putString("typeid", typeid);//文章分类id
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        mChapter_recyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                downloadData(1);
            }
        });
        mChapter_recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(mChapter_recyclerView);
                if (state == LoadingFooter.State.Loading) {
                    Log.d(TAG, "the state is Loading, just wait..");
                    return;
                }
                RecyclerViewStateUtils.setFooterViewState(getActivity(), mChapter_recyclerView, 10, LoadingFooter.State.Loading, null);
                downloadData(currenPage);
            }
        });
    }

    private View.OnClickListener mFooterClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerViewStateUtils.setFooterViewState(getActivity(), mChapter_recyclerView, 10, LoadingFooter.State.Loading, null);
            downloadData(currenPage);
        }
    };

    private void downloadData(final int page) {


        Log.d("chapter_normal",mTypeid+"开始加载");
        OkHttpUtils.get(API.ARTICLE_URL)
                .params("row", mRow)
                .params("typeid", mTypeid)
                .params("paging", 1)
                .params("page", page)
                .connTimeOut(5000)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        mChapter_items = JsonUtils.parseChapterJson(s);
                        closeDialog();
                        Log.d("chapter_normal", mTypeid + "加载成功");
                        if (mChapter_items != null) {
                            isLazy = false;
                            if (page == 1) {
                                mChapter_recyclerView.refreshComplete();
                                data.clear();
                            }
                            currenPage = page;
                            currenPage++;
                            data.addAll(mChapter_items);
                            mRecyclerViewAdapter.notifyDataSetChanged();
                            RecyclerViewStateUtils.setFooterViewState(mChapter_recyclerView, LoadingFooter.State.Normal);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        closeDialog();
                        isLazy = true;
                        if (page == 1) {
                            mChapter_recyclerView.refreshComplete();
                            Log.d("chapter_normal", mTypeid + "刷新失败");
                            return;
                        }
                        Log.d("chapter_normal", mTypeid + "加载失败");
//                        mChapter_recyclerView.setEmptyView(mEmptyView);只有服务器没数据是才使用，空view之后不能刷新了。
                        RecyclerViewStateUtils.setFooterViewState(getActivity(), mChapter_recyclerView, 10, LoadingFooter.State.NetWorkError, mFooterClick);
                    }
                });
    }

    @Override
    protected void lazyLoad() {

        if (isLazy) {
                if (null != getContext()) {
                    openDialog();
                }
            Log.d("chapter_normal", "开始懒加载");
            downloadData(1);
            isLazy=false;
        }
    }

    //关闭进度对话框
    private void closeDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    //显示进度对话框
    private void openDialog() {
        if (mDialog == null) {
            mDialog = new ProgressDialog(getContext());
            mDialog.setMessage(mTypeid+"文章加载中。。。");
            mDialog.setCancelable(false);
            mDialog.show();
            mDialog.setOnKeyListener(onKeyListener);
        }
    }
    /**
     * add a keylistener for progress dialog
     */
    private DialogInterface.OnKeyListener onKeyListener = new DialogInterface.OnKeyListener() {
        @Override
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                closeDialog();
            }
            return false;
        }
    };
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("chapter_normal:"+mTypeid); //友盟统计页面，"MainScreen"为页面名称，可自定义
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("chapter_normal:"+mTypeid);
    }
}
