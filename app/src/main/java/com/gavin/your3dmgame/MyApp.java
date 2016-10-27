package com.gavin.your3dmgame;

import android.app.Application;
import android.util.Log;

import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsDownloader;
import com.tencent.smtt.sdk.TbsListener;

import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by GaVin on 2016/10/14 0014.
 */

public class MyApp extends Application {
    private static TbsListener z;

    @Override
    public void onCreate() {
        super.onCreate();
        //极光推送初始化  初始化sdk
        JPushInterface.setDebugMode(true);//正式版的时候设置false，关闭调试
        JPushInterface.init(this);
        //建议添加tag标签，发送消息的之后就可以指定tag标签来发送了
        Set<String> set = new HashSet<>();
        set.add("GaVin");//名字任意，可多添加几个
        JPushInterface.setTags(this, set, null);//设置标签

        //腾讯Bugly的初始化
        CrashReport.initCrashReport(this, "900056157", true);
       /* 第三个参数为SDK调试模式开关，调试模式的行为特性如下：
        ● 输出详细的Bugly SDK的Log；
        ● 每一条Crash都会被立即上报；
        ● 自定义日志将会在Logcat中输出。
        建议在测试阶段建议设置成true，发布时设置为false。*/


        //    腾讯X5浏览器的初始化
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        TbsDownloader.needDownload(this, false);
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                Log.d("apptbs", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub

            }
        };
        QbSdk.setTbsListener(new TbsListener() {
            @Override
            public void onDownloadFinish(int i) {
                Log.d("apptbs", "onDownloadFinish");
            }

            @Override
            public void onInstallFinish(int i) {
                Log.d("apptbs", "onInstallFinish");
            }

            @Override
            public void onDownloadProgress(int i) {
                Log.d("apptbs", "onDownloadProgress:" + i);
            }
        });
        QbSdk.initX5Environment(this, cb);
    }


}
