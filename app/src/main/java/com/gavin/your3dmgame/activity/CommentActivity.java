package com.gavin.your3dmgame.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.gavin.your3dmgame.R;
import com.gavin.your3dmgame.adapter.Comment_adapter;
import com.gavin.your3dmgame.entity.Comment_item;
import com.gavin.your3dmgame.utils.API;
import com.gavin.your3dmgame.utils.JsonUtils;
import com.gavin.your3dmgame.utils.SoftKeyBoardUtils;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static android.content.ContentValues.TAG;


public class CommentActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private LRecyclerView mRecyclerView;
    private LRecyclerViewAdapter mRecyclerViewAdapter;

    private Context mContext = CommentActivity.this;
    private Activity mActivity = CommentActivity.this;
    private String mId;
    private String mTypeid;
    private Button mButton;
    private EditText mEditText;
    private List<Comment_item.DescriptionBean.DataBean> mData;
    private List<Comment_item.DescriptionBean.DataBean> CommentData = new ArrayList<>();
    ;
    private Comment_adapter mCommentAdapter;
    private int currenPage = 1;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        Intent intent = getIntent();
        mId = intent.getStringExtra("id");
        mTypeid = intent.getStringExtra("typeid");
        MobclickAgent.setScenarioType(CommentActivity.this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        initWindow();
        init();
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
    private void init() {
        findView();
        initView();
        setAdapter();
        initListener();
        openDialog("获取评论中。。。");
        downloadData(1);
    }

    private void initView() {
        //设置标题
        mToolbar.setTitle("评论");

        mToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        //设置标题颜色
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setRefreshing(true);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

    }

    private void setAdapter() {
        mCommentAdapter = new Comment_adapter(mContext, CommentData);
        mRecyclerViewAdapter = new LRecyclerViewAdapter(mCommentAdapter);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }

    /*获取评论列表*/
    private void downloadData(final int page) {
        Log.e("CommentActivity", "开始加载");
        OkHttpUtils.get(API.COMMENT_URL)
                .params("aid", mId)
                .params("pageno", page)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        closeDialog();
                        Log.e("CommentActivity", "加载完成");
                        String json = JsonUtils.removeBOM(s);
                        Log.e("CommentActivity", json);
                        Comment_item commentItem = JSON.parseObject(json, Comment_item.class);
                        mData = commentItem.getDescription().getData();
                        if (mData != null) {
                            if (page == 1) {
                                mRecyclerView.refreshComplete();
                                CommentData.clear();
                            }
                            currenPage = page;
                            currenPage++;
                            CommentData.addAll(mData);
                            mCommentAdapter.notifyDataSetChanged();
                            RecyclerViewStateUtils.setFooterViewState(mRecyclerView, LoadingFooter.State.Normal);

                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        closeDialog();
                        if (page == 1) {
                            mRecyclerView.refreshComplete();
                            Toast.makeText(mContext, "刷新失败", Toast.LENGTH_SHORT).show();
                        }
//                        mChapter_recyclerView.setEmptyView(mEmptyView);只有服务器没数据是才使用，空view之后不能刷新了。
                        RecyclerViewStateUtils.setFooterViewState(mActivity, mRecyclerView, 10, LoadingFooter.State.NetWorkError, mFooterClick);

                    }
                });
    }

    //设置事件监听
    private void initListener() {
        //toolbard的返回按钮事件监听
        mToolbar.setNavigationOnClickListener(this);
        mButton.setOnClickListener(this);
        mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                downloadData(1);
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
                RecyclerViewStateUtils.setFooterViewState(mActivity, mRecyclerView, 10, LoadingFooter.State.Loading, null);
                downloadData(currenPage);
            }
        });
    }

    private View.OnClickListener mFooterClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerViewStateUtils.setFooterViewState(mActivity, mRecyclerView, 10, LoadingFooter.State.Loading, null);
            downloadData(currenPage);
        }
    };

    private void findView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mRecyclerView = (LRecyclerView) findViewById(R.id.comment_RecyclerView);
        mButton = (Button) findViewById(R.id.comment_btn);
        mEditText = (EditText) findViewById(R.id.ed_comment);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.comment_btn) {
            commmitComment();
            return;
        }
        finish();
    }

    private void commmitComment() {
        openDialog("评论中。。。");
        commit();
    }

    /*提交评论*/
    private void commit() {
        OkHttpUtils.post(API.COMMENT_COMMIT_URL)
                .params("aid", mId)
                .params("msg", mEditText.getText().toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        String json = JsonUtils.removeBOM(s);
                        Log.e("CommentActivity", json);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(json);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        closeDialog();
                        String code = jsonObject.optString("code");
                        Log.e("CommentActivity", code);
                        if (code.equalsIgnoreCase("1")) {
                            CommentData.clear();
                            downloadData(1);
                            mCommentAdapter.notifyDataSetChanged();
                            mEditText.setText("");
                            mEditText.setCursorVisible(false);//让光标消失
                            SoftKeyBoardUtils.closeSoftInputMethod(CommentActivity.this, mEditText);
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        closeDialog();
                    }
                });
    }
    //获取当前点击位置是否为edittext

    /*获取当前点击位置是否为et
     @param view 焦点所在View
     @param event 触摸事件
     @ return*/

    public boolean isClickEt(View view, MotionEvent event) {
        if (view != null && (view instanceof EditText)) {
            int[] leftTop = {0, 0};
//            获取输入框当前的 location 位置
            view.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
//            此处根据输入框左上位置和宽高获得右下位置
            int bottom = top + view.getHeight();
            int right = left + view.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
//                点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }


    /**
     * 点击EditText以外的区域后键盘消失
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            // 获取当前获得当前焦点所在View
            View view = getCurrentFocus();
            if (isClickEt(view, event)) {

                // 如果不是edittext，则隐藏键盘

                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null) {
                    // 隐藏键盘
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(event);
        }
        /* *
        * 看源码可知superDispatchTouchEvent  是个抽象方法，用于自定义的Window
                * 此处目的是为了继续将事件由dispatchTouchEvent ( MotionEvent event ) 传递到onTouchEvent ( MotionEvent event )
                * 必不可少，否则所有组件都不能触发 onTouchEvent ( MotionEvent event )
        */
        if (getWindow().superDispatchTouchEvent(event)) {
            return true;
        }
        return onTouchEvent(event);
    }


    //关闭进度对话框
    private void closeDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    //显示进度对话框
    private void openDialog(String msg) {
        mDialog = new ProgressDialog(CommentActivity.this);
        mDialog.setMessage(msg);
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
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("CommentActivity"); //友盟统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
        MobclickAgent.onResume(this);//统计时长
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("CommentActivity"); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
        MobclickAgent.onPause(this);
    }
}
