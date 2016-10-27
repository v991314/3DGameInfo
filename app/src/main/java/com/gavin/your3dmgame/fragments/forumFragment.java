package com.gavin.your3dmgame.fragments;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.gavin.your3dmgame.R;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.umeng.analytics.MobclickAgent;


/**
 * A simple {@link Fragment} subclass.
 */
public class forumFragment extends BaseFragment {

    public static final String HTTP_FORM_URL = "http://bbs.3dmgame.com/forum.php";
    private WebView web_view;
    private ProgressDialog dialog;
    private View mView;
    private boolean isLazy=true;
    private ProgressBar mProgressBar;

    public forumFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView == null) {
            // Inflate the layout for this fragment
            mView = inflater.inflate(R.layout.fragment_forum, container, false);
            init();
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) mView.getParent();
        if (parent != null) {
            parent.removeView(mView);
        }
        Log.d("forumFragment", "懒加载:" + isLazy);
        return mView;
    }

    private void init() {
        web_view = (WebView) mView.findViewById(R.id.forum_WebView);
        mProgressBar = (ProgressBar) mView.findViewById(R.id.progressBar);
////        c)以下接口禁止(直接或反射)调用，避免视频画面无法显示：
//        web_view.setLayerType(View.LAYER_TYPE_SOFTWARE,new Paint());
//        web_view.setDrawingCacheEnabled(true);
//        设置可以在webview中加载网页中的连接

        web_view.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                mProgressBar.setProgress(newProgress);
                if (newProgress == 100) {
//                    网页加载完成，关闭对话框
                    mProgressBar.setVisibility(View.GONE);
                    isLazy=false;
                    Log.d("forumFragment", "加载成功");
                } else {
                    //网页加载中，打开进度对话框
                    mProgressBar.setVisibility(View.VISIBLE);
                }
            }
        }
        );
        //启用支持javascript
        WebSettings settings = web_view.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        /*web_view.setOnKeyListener(new WebView.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.d("forumFragment", "我进来了");
                if (keyCode == KeyEvent.KEYCODE_BACK ) {
                    if (web_view != null && web_view.canGoBack()) {
                        web_view.goBack();
                        return true;
                    }
                }
                return false;
            }//换成腾讯的TBS之后，键盘监听失效了
        });*/
    }


    private void loadWeb() {
        //加载网络资源
        web_view.loadUrl(HTTP_FORM_URL);
        web_view.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.d("forumFragment", "论坛开始加载");
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d("forumFragment", "论坛加载完成");
                isLazy=false;
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                mProgressBar.setVisibility(View.GONE);
                Log.d("forumFragment", "论坛加载错误");
                isLazy=true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);//设置为上面的url
                return true;
            }

        });
    }

    @Override
    protected void lazyLoad() {
        if (isLazy) {
            if (OnWebPreBackListener != null) {
                Log.d("forumFragment", "回调webview到mainactivity");
                OnWebPreBackListener.onWebPreBack(web_view);
            }
//            CrashReport.testJavaCrash();//测试Bugly时使用
            Log.d("forumFragment", "开始懒加载");
            loadWeb();
            Log.d("forumFragment", "WebView内核版本信息:" + WebView.getTbsCoreVersion(getContext()));
            //函数返回的内核版本信息、
            Log.d("forumFragment", "WebView浏览器SDK版本信息:" + WebView.getTbsSDKVersion(getContext()));
            //函数返回的浏览器SDK版本信息、
            Log.d("forumFragment","WebView的crash线索信息"+WebView.getCrashExtraMessage(getContext()));
           // 函数返回的crash线索信息
            isLazy=false;
//            throw new IllegalArgumentException("测试不合法参数");//测试Bugly时使用
        }
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("forumFragment"); //友盟统计页面，"forumFragment"为页面名称，可自定义
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("forumFragment");
    }
    public interface OnWebPreBackListener{
        void onWebPreBack(WebView web_view);
    }
    private OnWebPreBackListener OnWebPreBackListener;

    public void setOnWebPreBackListener(OnWebPreBackListener OnWebPreBackListener){
        this.OnWebPreBackListener=OnWebPreBackListener;
    }
}
