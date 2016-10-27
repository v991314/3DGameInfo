package com.gavin.your3dmgame.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import com.gavin.your3dmgame.R;
import com.gavin.your3dmgame.entity.Detail_chapter;
import com.gavin.your3dmgame.utils.API;
import com.gavin.your3dmgame.utils.DateUtils;
import com.gavin.your3dmgame.utils.JsonUtils;
import com.google.gson.Gson;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.umeng.analytics.MobclickAgent;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import okhttp3.Call;
import okhttp3.Response;

import static com.gavin.your3dmgame.utils.API.DMGEAME_URL;


public class ChapterDetailActivity extends AppCompatActivity implements View.OnClickListener, PlatformActionListener {

    private WebView mWebView;
    private String mTypeid;
    private String mId;
    private String mBody;
    private String mTitle;
    private String mWriter;
    private String mSenddate;
    private String mSource;
    private String mDecode;

    private Toolbar mToolbar;
    private FloatingActionButton mFloatingActionButton;
    private ProgressDialog mDialog;
    private String mHtml;
    private ImageButton mArticle_share;
    private String mShorttitle;
    private String mArcurl;
    private String mLitpic;
    private String mDate;
    private SmoothProgressBar mSmoothProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_detail);
        mId = getIntent().getStringExtra("id");
        mTypeid = getIntent().getStringExtra("typeid");
        //        a)网页中的视频，上屏幕的时候，可能出现闪烁的情况，需要如下设置：Activity在onCreate时需要设置:
//        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        //        避免输入法界面弹出后遮挡输入光标的问题
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initWindow();
        MobclickAgent.setScenarioType(ChapterDetailActivity.this, MobclickAgent.EScenarioType.E_UM_NORMAL);
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
        finView();
        initView();
        initListener();
        downloadData();

        ShareSDK.initSDK(this);
    }


    //设置事件监听
    private void initListener() {
        mArticle_share.setOnClickListener(this);
        //toolbard的返回按钮事件监听
        mToolbar.setNavigationOnClickListener(this);
        //点击按钮跳转到评论界面
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChapterDetailActivity.this, CommentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", mId);
                bundle.putString("typeid", mTypeid);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView webView, int newProgress) {
                mSmoothProgressBar.setProgress(newProgress);
                if (newProgress == 100) {
//                    网页加载完成，关闭对话框
                    mSmoothProgressBar.setVisibility(View.GONE);
                    Log.d("ChapterDetailActivity", "加载成功");
                } else {
                    //网页加载中，打开进度对话框
                    mSmoothProgressBar.setVisibility(View.VISIBLE);
                }
            }
        });
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                mSmoothProgressBar.setVisibility(View.GONE);
                Log.d("ChapterDetailActivity", "论坛加载错误");
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                //需要监听webview中点击的url，拦截并在另一个浏览器中打开。但是有的url是重定向，就需要在当前的webview中打开。
                //腾讯的TBS直接返回flase不起作用，自带的webview可以。
                if (webView.getHitTestResult() != null) {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    intent.setData(Uri.parse(s));
                    startActivity(intent);
                    return true;
                } else {
                    webView.loadUrl(s);
                    return false;
                }
            }

        });

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.article_share) {
            showShare(v);
            return;
        }
        finish();
    }

    public void showShare(View v) {
        /**
         * 分享的内容。
         */

        OnekeyShare oks = new OnekeyShare();

        oks.disableSSOWhenAuthorize();

        oks.setTitle(mShorttitle);
        oks.setTitleUrl(mArcurl);
        oks.setText(mTitle + " 作者:" + mWriter + " 发布时间:" + mDate + " 来源：" + mSource);
        oks.setImageUrl(DMGEAME_URL + mLitpic);
        oks.setUrl(mArcurl);
        oks.setComment(mShorttitle);
        oks.setSite("3DM");
        oks.setSiteUrl(mArcurl);
        oks.show(this);
    }

    private void initView() {

        mToolbar.setTitle("文章详情");

        mToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        //设置标题
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
    }

    private void finView() {
        mWebView = (WebView) findViewById(R.id.chapter_Detail);
        mToolbar = (Toolbar) findViewById(R.id.mToolbar);
        mSmoothProgressBar = (SmoothProgressBar) findViewById(R.id.web_progress);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        mArticle_share = (ImageButton) findViewById(R.id.article_share);
    }

    //初始化数据
    private void initData() {
        //启用支持javascript
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setLoadWithOverviewMode(true);
//        settings.setTextSize(WebSettings.TextSize.LARGEST);//设置字体大小
        settings.setTextZoom(100);//设置字体大小
        settings.setDefaultTextEncodingName("utf-8");//设置默认编码格式
//        //自适应屏幕
        settings.setUseWideViewPort(true);

        if (mBody != null) {
            try {
                //由于body的数据进行了URLEncode编码，所以需要我们再进行URLDecoder解码
                //否则只能显示图片
                mDecode = URLDecoder.decode(mBody, "utf-8");
                //发布时间
                mDate = DateUtils.dateFromat(mSenddate);
                //自定义样式，设置图片显示大小
                mHtml = "<html><body>"
                        + "<h3>"
                        + mTitle
                        + "</h3>"
                        + "<p>"
                        + "作者:" + mWriter
                        + "&nbsp&nbsp"
                        + "发布时间:" + mDate
                        + "&nbsp&nbsp"
                        + "来源：" + mSource
                        + "</p>"
                        + "<style>"
                        + "img{width:100%;height:auto;}"//自定义样式，设置图片显示大小
                        + "</style>"
                        + mDecode
                        + "</body></html>";

                mWebView.loadDataWithBaseURL(DMGEAME_URL, mHtml, "text/html", "charset=UTF-8", null);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Log.e("接收数据", mBody);

        }
    }

    private void downloadData() {
        OkHttpUtils.get(API.ChapterContent_URL)
                .params("id", mId)
                .params("typeid", mTypeid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        String json = JsonUtils.removeBOM(s);
                        Log.e("接收数据", json);
                        Gson gson = new Gson();
                        Detail_chapter detail_chapter = gson.fromJson(json, Detail_chapter.class);
                        mBody = detail_chapter.getBody();
                        mTitle = detail_chapter.getTitle();
                        mShorttitle = detail_chapter.getShorttitle();
                        mWriter = detail_chapter.getWriter();
                        mSenddate = detail_chapter.getSenddate();
                        mSource = detail_chapter.getSource();
                        mArcurl = detail_chapter.getArcurl();
                        mLitpic = detail_chapter.getLitpic();
                        initData();
                    }
                });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView != null && mWebView.canGoBack()) {
                mWebView.goBack();
                return true;
            } else {
                return super.onKeyDown(keyCode, event);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /*//关闭进度对话框
    private void closeDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    //显示进度对话框
    private void openDialog() {
        mDialog = new ProgressDialog(ChapterDetailActivity.this);
        mDialog.setMessage("加载中。。。");
        mDialog.setCancelable(false);
        mDialog.show();
        mDialog.setOnKeyListener(onKeyListener);
    }

    *//**
     * add a keylistener for progress dialog
     *//*
    private DialogInterface.OnKeyListener onKeyListener = new DialogInterface.OnKeyListener() {
        @Override
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                closeDialog();
            }
            return false;
        }
    };*/

    /*shareSDK的回调*/
    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        Toast.makeText(this, "分享成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        Toast.makeText(this, "分享失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancel(Platform platform, int i) {
        Toast.makeText(this, "取消分享", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("ChapterDetailActivity"); //友盟统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
        MobclickAgent.onResume(this);//统计时长
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("ChapterDetailActivity"); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
        MobclickAgent.onPause(this);
    }


}
