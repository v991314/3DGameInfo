package com.gavin.your3dmgame.fragments;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.gavin.your3dmgame.R;
import com.gavin.your3dmgame.activity.GameDetailActivity;
import com.gavin.your3dmgame.adapter.Game_adapter;
import com.gavin.your3dmgame.entity.Detail_game;
import com.gavin.your3dmgame.entity.GameListItem;
import com.gavin.your3dmgame.utils.API;
import com.gavin.your3dmgame.utils.Constant;
import com.gavin.your3dmgame.utils.JsonUtils;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.HeaderSpanSizeLookup;
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
public class gameFragment extends BaseFragment {
    private Spinner mSpinner;
    private View mView;
    private LRecyclerView mRecyclerView;
    private LRecyclerViewAdapter mRecyclerViewAdapter;
    private boolean isLazy=true;
    private List<GameListItem> mList = new ArrayList<>();
    private Game_adapter mGameAdapter;
    private int mCurrentPosition=0;
    private int currenPage = 1;
    private ProgressDialog mDialog;
    private boolean isFirst=true;
//    private View mEmptyView;
    private int mRow=14;

    public gameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView == null) {
            // Inflate the layout for this fragment
            mView = inflater.inflate(R.layout.fragment_game,container,false);
            findView();
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) mView.getParent();
        if (parent != null) {
            parent.removeView(mView);
        }
        Log.d("gameFragment", "懒加载:" + isLazy);
        Log.d("gameFragment", "是否可见:" + getUserVisibleHint());
        return mView;
    }



    private void initView() {


        //setLayoutManager
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        manager.setSpanSizeLookup(new HeaderSpanSizeLookup((LRecyclerViewAdapter) mRecyclerView.getAdapter(), manager.getSpanCount()));
        mRecyclerView.setLayoutManager(manager);
//        mRecyclerView.setRefreshing(true);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);
    }

    private void findView() {
        mSpinner = (Spinner) mView.findViewById(R.id.game_Spinner);
        mRecyclerView = (LRecyclerView) mView.findViewById(R.id.game_RecyclerView);
//        mEmptyView = mView.findViewById(R.id.empty_view);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        setAdapter();
        setListener();
    }

    private void setListener() {
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCurrentPosition = position;
                if (isFirst) {
                    Log.d("gameFragment", "第一次进入");
                    isFirst = false;
                } else {
                    downloadData(1, mCurrentPosition);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                downloadData(1, mCurrentPosition);
            }
        });
        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(mRecyclerView);
                if (state == LoadingFooter.State.Loading) {
                    Log.d(TAG, "the state is Loading, just wait..");
                    return;
                }
                RecyclerViewStateUtils.setFooterViewState(getActivity(), mRecyclerView, 8, LoadingFooter.State.Loading, null);
                downloadData(currenPage,mCurrentPosition);
            }
        });
        mRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {


            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), GameDetailActivity.class);
                String id = mList.get(position).getId();
                String typeid = mList.get(position).getTypeid();
                String title = mList.get(position).getTitle();
                String typename = mList.get(position).getTypename();
                String made_company = mList.get(position).getMade_company();
                String release_date = mList.get(position).getRelease_date();
                String release_company = mList.get(position).getRelease_company();
                String websit = mList.get(position).getWebsit();
                String terrace = mList.get(position).getTerrace();
                String language = mList.get(position).getLanguage();
                String description = mList.get(position).getDescription();
                String litpic = mList.get(position).getLitpic();

                Bundle bundle = new Bundle();
                Detail_game detail_game = new Detail_game(id, typeid, title, litpic, description, typename, websit,
                        release_company, made_company, terrace, language, release_date);
                bundle.putParcelable("detail_game", detail_game);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int i) {

            }
        });
    }
    private View.OnClickListener mFooterClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerViewStateUtils.setFooterViewState(getActivity(), mRecyclerView, 12, LoadingFooter.State.Loading, null);
            downloadData(currenPage,mCurrentPosition);
        }
    };
    private void setAdapter() {
        ArrayAdapter adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_activated_1, Constant.GAME_TYPES);
        mSpinner.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mGameAdapter = new Game_adapter(getContext(), mList);
        mRecyclerViewAdapter = new LRecyclerViewAdapter(mGameAdapter);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);


    }

    private void downloadData(final int page, int position) {

        Log.d("gameFragment", "游戏栏开始加载");
        OkHttpUtils.get(API.GAME_URL)
                .params("row", mRow)
                .params("typeid", Constant.GAME_TYPE_ID[position])
                .params("paging", 1)
                .params("page", page)
                .connTimeOut(10000)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.d("gameFragment", "游戏栏加载完成");
                        closeDialog();
                        String json = JsonUtils.removeBOM(s);
                        List<GameListItem> gameListItems = JsonUtils.parseGameJson(json);
                        if (gameListItems != null && gameListItems.size() != 0) {
                            isLazy=false;
                            if (page == 1) {
                                mList.clear();
                                mRecyclerView.refreshComplete();
                            }
                            currenPage = page;
                            currenPage++;
                            //设置刷新操作
                            mList.addAll(gameListItems);
                            mGameAdapter.notifyDataSetChanged();
                            RecyclerViewStateUtils.setFooterViewState(mRecyclerView, LoadingFooter.State.Normal);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.d("gameFragment", "游戏栏加载失败");
                        closeDialog();
                        isLazy=true;
                        if (page == 1) {
                            mRecyclerView.refreshComplete();
                        }
                        RecyclerViewStateUtils.setFooterViewState(getActivity(), mRecyclerView, 12, LoadingFooter.State.NetWorkError, mFooterClick);


                    }
                });

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
        mDialog = new ProgressDialog(getActivity());
        mDialog.setMessage("游戏加载中。。。");
        mDialog.setCancelable(false);
        mDialog.show();
        mDialog.setOnKeyListener(onKeyListener);
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

    @Override
    protected void lazyLoad() {
        if (isLazy) {
            Log.d("gameFragment", "开始懒加载");
            openDialog();
            mSpinner.setSelection(1,true);
            mSpinner.setSelection(mCurrentPosition,true);
            isLazy=false;
        }
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("gameFragment"); //友盟统计页面，"gameFragment"为页面名称，可自定义
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("gameFragment");
    }
}
