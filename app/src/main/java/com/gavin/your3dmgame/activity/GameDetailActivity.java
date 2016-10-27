package com.gavin.your3dmgame.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.gavin.your3dmgame.R;
import com.gavin.your3dmgame.entity.Detail_game;
import com.gavin.your3dmgame.utils.API;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.squareup.picasso.Picasso;
import com.umeng.analytics.MobclickAgent;


public class GameDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private ImageButton mArticleShare;
    private TextView mTvGameName;
    private TextView mTvGameType;
    private TextView mTvGameProduct;
    private TextView mTvGameTime;
    private TextView mTvReleaseCompany;
    private TextView mTvGameUrl;
    private TextView mTvGameTerrace;
    private TextView mTvGameLanguage;
    private TextView mTvGameDetail;
    private Detail_game mDetail_game;
    private ImageView iv_game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);
        mDetail_game = getIntent().getParcelableExtra("detail_game");
        MobclickAgent.setScenarioType(GameDetailActivity.this, MobclickAgent.EScenarioType.E_UM_NORMAL);
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
//        downloadData();
        updateData();
        initListener();
    }

    private void initListener() {
        //toolbard的返回按钮事件监听
        mToolbar.setNavigationOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    private void initView() {
        mToolbar.setTitle("游戏详情");

        mToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        //设置标题
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
    }


    private void findView() {
        mToolbar = (Toolbar) findViewById(R.id.mToolbar);
        mArticleShare = (ImageButton) findViewById(R.id.article_share);
        iv_game = (ImageView) findViewById(R.id.iv_game);
        mTvGameName = (TextView) findViewById(R.id.tv_game_name);
        mTvGameType = (TextView) findViewById(R.id.tv_game_type);
        mTvGameProduct = (TextView) findViewById(R.id.tv_game_product);
        mTvGameTime = (TextView) findViewById(R.id.tv_game_time);
        mTvReleaseCompany = (TextView) findViewById(R.id.tv_release_company);
        mTvGameUrl = (TextView) findViewById(R.id.tv_game_url);
        mTvGameTerrace = (TextView) findViewById(R.id.tv_game_terrace);
        mTvGameLanguage = (TextView) findViewById(R.id.tv_game_language);
        mTvGameDetail = (TextView) findViewById(R.id.tv_game_detail);
    }

    private void updateData() {
        mTvGameUrl.setMovementMethod(LinkMovementMethod.getInstance());
        mTvGameName.setText(mDetail_game.getTitle());
        mTvGameType.setText(mDetail_game.getTypename());
        mTvGameProduct.setText(mDetail_game.getMade_company());
        mTvGameTime.setText(mDetail_game.getRelease_date());
        mTvReleaseCompany.setText(mDetail_game.getRelease_company());
        String websit = mDetail_game.getWebsit();
        if (TextUtils.isEmpty(websit)) {
            mTvGameUrl.setText("无");
        } else {
            mTvGameUrl.setText(websit);
        }
        mTvGameTerrace.setText(mDetail_game.getTerrace());
        mTvGameLanguage.setText(mDetail_game.getLanguage());
        mTvGameDetail.setText(mDetail_game.getDescription());
        //获取到图片地址
        String litpic = mDetail_game.getLitpic();
        if (litpic == null) {
            iv_game.setImageResource(R.drawable.product_default);
            return;
        }
        //地址拼接
        String imageUrl = API.DMGEAME_URL + litpic;
        Picasso.with(GameDetailActivity.this).load(imageUrl).error(R.drawable.product_default).into(iv_game);
    }

    /*这些数据在游戏页面已经获取了，因此这里无需再获取了*/
    /*private void downloadData() {
        openDialog();
        OkHttpUtils.get(API.API_URL)
                .params("id", mId)
                .params("typeid", mTypeid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        String json = JsonUtils.removeBOM(s);
                        Log.e("接收数据", json);
                        mDetail_game = JSON.parseObject(json, Detail_game.class);
                        updateData();
                        content_game_detail.setVisibility(View.VISIBLE);
                        tv_game_description.setVisibility(View.VISIBLE);

                        closeDialog();
                    }
                });

    }*/
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("GameDetailActivity"); //友盟统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
        MobclickAgent.onResume(this);//统计时长
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("GameDetailActivity"); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
        MobclickAgent.onPause(this);
    }
}
