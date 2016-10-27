# 3DGameInfo
一个以游戏资讯为主的新闻类APP，使用了Material Design的一些设计理念。 界面仿照网易新闻客户端，

主要分为三大板块： 【文章】  【论坛】 【游戏】。
# 技术要点
整个工程采用了安卓常用的MVC的设计模式，后面有机会改成MVP的架构 。
### 主要使用的技术
1. 使用了的Material Design的一些控件：CoordinatorLayout,AppBarLayout+Toolbar,NestedScrollView,FloatingActionButton,RecyleView以及Cardview的综合使用。
2. 整体模块的框架采用了Frgment+FragmentTablayout+ViewPager。通过缓存视图解决了Frgment重复加载数据。
3. 通过判断Frgment是否显示，来实现Fragment数据的懒加载，可以解决流量消耗问题。
4. 集成了一些常用的第三方技术：bugly，极光推送，shareSDK分享，TBS Webview，
### 主要使用的第三方开源框架有： 
- butterknife 一键注解，一个非常好用的工具
- okhttpUtils 网络加载框架
- picasso 图片加载框架 
- nineoldandroid  一款功能强大的动画库
- smoothprogressbar 一款比较好看的进度条
- LrecycleView 下拉刷新框架
- convenientbanner 广告轮播框架  可实现任何View的轮播
- fastjson 解析json的框架  与gson一样好用


# 声明
3DGameInfo是一款基于3DMGAME游戏门户网站的一款APP，非官方版本，数据来源于游戏门户网站3DM，数据接口均属于非正常渠道获取，请勿用于商业用途，原作公司拥有所有权利。