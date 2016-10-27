package com.gavin.your3dmgame.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by GaVin on 2016/10/12 0012.
 */

public class Detail_game implements Parcelable {

    /**
     * id : 3598635
     * typeid : 181
     * typeid2 : 0
     * sortrank : 1476152890
     * flag :
     * ismake : 1
     * channel : 17
     * arcrank : 0
     * click : 525
     * money : 0
     * title : Thumper
     * shorttitle : Thumper
     * color :
     * writer : 夯大力
     * source : 未知
     * litpic : /uploads/allimg/161011/316-1610111029360-L.jpg
     * pubdate : 1476154052
     * senddate : 1476152890
     * mid : 316
     * keywords : Thumper,Thumper中文版下载,截图,攻略,配置
     * lastpost : 0
     * scores : 0
     * goodpost : 0
     * badpost : 0
     * voteid : 0
     * notpost : 0
     * description : 《Thumper》专题站提供本游戏攻略,完整硬盘版下载,官网,官方网站,存档,发售日期,新闻,截图,视频,评测,评分,破解,补丁,修改器,配置,中文,汉化,秘籍等游戏资料...
     * filename : thumper
     * dutyadmin : 244
     * tackid : 0
     * mtype : 0
     * weight : 288477
     * fby_id : 0
     * game_id : 0
     * feedback : 0
     * typedir : {cmspath}/a/games
     * typename : 动作(ACT)
     * corank : 0
     * isdefault : -1
     * defaultname : index.html
     * namerule : {typedir}/{Y}/{M}{D}/{aid}.html
     * namerule2 : {typedir}/list_{tid}_{page}.html
     * ispart : 0
     * moresite : 0
     * siteurl :
     * sitepath : {cmspath}/a/games
     * aid : 3598635
     * redirecturl :
     * templet :
     * userip : 192.168.0.123
     * vid : 209257
     * game_bbs :
     * total : 0
     * multiplayer : 0
     * concept : 0
     * sound : 0
     * graphics : 0
     * gameplay : 0
     * websit : http://thumpergame.com/
     * release_company : Drool
     * made_company : Drool
     * terrace : PC,PS4
     * language : 简体中文,英文
     * release_date : 2016年10月11日
     * game_trans_name : Thumper
     * fst : T
     * tid : 动作冒险（ACT）
     * game_othername1 :
     * game_othername2 :
     * zhuanti_toutiao2 :
     * zhuanti_toutiao3 :
     * zhuanti_toutiao4 :
     * imgid : 0
     * downid : 0
     * arcurl : http://www.3dmgame.com/games/thumper/
     * typeurl : http://www.3dmgame.com/games/act/
     * gl : {}
     * topgl : null
     * body_imgs : {}
     * body :
     */

    private String id;
    private String typeid;
    private String typeid2;
    private String sortrank;
    private String flag;
    private String ismake;
    private String channel;
    private String arcrank;
    private String click;
    private String money;
    private String title;
    private String shorttitle;
    private String color;
    private String writer;
    private String source;
    private String litpic;
    private String pubdate;
    private String senddate;
    private String mid;
    private String keywords;
    private String lastpost;
    private String scores;
    private String goodpost;
    private String badpost;
    private String voteid;
    private String notpost;
    private String description;
    private String filename;
    private String dutyadmin;
    private String tackid;
    private String mtype;
    private String weight;
    private String fby_id;
    private String game_id;
    private String feedback;
    private String typedir;
    private String typename;
    private String corank;
    private String isdefault;
    private String defaultname;
    private String namerule;
    private String namerule2;
    private String ispart;
    private String moresite;
    private String siteurl;
    private String sitepath;
    private String aid;
    private String redirecturl;
    private String templet;
    private String userip;
    private String vid;
    private String game_bbs;
    private String total;
    private String multiplayer;
    private String concept;
    private String sound;
    private String graphics;
    private String gameplay;
    private String websit;
    private String release_company;
    private String made_company;
    private String terrace;
    private String language;
    private String release_date;
    private String game_trans_name;
    private String fst;
    private String tid;
    private String game_othername1;
    private String game_othername2;
    private String zhuanti_toutiao2;
    private String zhuanti_toutiao3;
    private String zhuanti_toutiao4;
    private String imgid;
    private String downid;
    private String arcurl;
    private String typeurl;

    public Detail_game(String id, String typeid, String title, String litpic, String description, String typename, String websit, String release_company, String made_company, String terrace, String language, String release_date) {
        this.id = id;
        this.typeid = typeid;
        this.title = title;
        this.litpic = litpic;
        this.description = description;
        this.typename = typename;
        this.websit = websit;
        this.release_company = release_company;
        this.made_company = made_company;
        this.terrace = terrace;
        this.language = language;
        this.release_date = release_date;
    }

    @Override
    public String toString() {
        return "Detail_game{" +
                "id='" + id + '\'' +
                ", typeid='" + typeid + '\'' +
                ", title='" + title + '\'' +
                ", litpic='" + litpic + '\'' +
                ", description='" + description + '\'' +
                ", typename='" + typename + '\'' +
                ", release_date='" + release_date + '\'' +
                ", language='" + language + '\'' +
                ", terrace='" + terrace + '\'' +
                ", made_company='" + made_company + '\'' +
                ", release_company='" + release_company + '\'' +
                ", websit='" + websit + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public String getTypeid2() {
        return typeid2;
    }

    public void setTypeid2(String typeid2) {
        this.typeid2 = typeid2;
    }

    public String getSortrank() {
        return sortrank;
    }

    public void setSortrank(String sortrank) {
        this.sortrank = sortrank;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getIsmake() {
        return ismake;
    }

    public void setIsmake(String ismake) {
        this.ismake = ismake;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getArcrank() {
        return arcrank;
    }

    public void setArcrank(String arcrank) {
        this.arcrank = arcrank;
    }

    public String getClick() {
        return click;
    }

    public void setClick(String click) {
        this.click = click;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShorttitle() {
        return shorttitle;
    }

    public void setShorttitle(String shorttitle) {
        this.shorttitle = shorttitle;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLitpic() {
        return litpic;
    }

    public void setLitpic(String litpic) {
        this.litpic = litpic;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getSenddate() {
        return senddate;
    }

    public void setSenddate(String senddate) {
        this.senddate = senddate;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getLastpost() {
        return lastpost;
    }

    public void setLastpost(String lastpost) {
        this.lastpost = lastpost;
    }

    public String getScores() {
        return scores;
    }

    public void setScores(String scores) {
        this.scores = scores;
    }

    public String getGoodpost() {
        return goodpost;
    }

    public void setGoodpost(String goodpost) {
        this.goodpost = goodpost;
    }

    public String getBadpost() {
        return badpost;
    }

    public void setBadpost(String badpost) {
        this.badpost = badpost;
    }

    public String getVoteid() {
        return voteid;
    }

    public void setVoteid(String voteid) {
        this.voteid = voteid;
    }

    public String getNotpost() {
        return notpost;
    }

    public void setNotpost(String notpost) {
        this.notpost = notpost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getDutyadmin() {
        return dutyadmin;
    }

    public void setDutyadmin(String dutyadmin) {
        this.dutyadmin = dutyadmin;
    }

    public String getTackid() {
        return tackid;
    }

    public void setTackid(String tackid) {
        this.tackid = tackid;
    }

    public String getMtype() {
        return mtype;
    }

    public void setMtype(String mtype) {
        this.mtype = mtype;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getFby_id() {
        return fby_id;
    }

    public void setFby_id(String fby_id) {
        this.fby_id = fby_id;
    }

    public String getGame_id() {
        return game_id;
    }

    public void setGame_id(String game_id) {
        this.game_id = game_id;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getTypedir() {
        return typedir;
    }

    public void setTypedir(String typedir) {
        this.typedir = typedir;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getCorank() {
        return corank;
    }

    public void setCorank(String corank) {
        this.corank = corank;
    }

    public String getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(String isdefault) {
        this.isdefault = isdefault;
    }

    public String getDefaultname() {
        return defaultname;
    }

    public void setDefaultname(String defaultname) {
        this.defaultname = defaultname;
    }

    public String getNamerule() {
        return namerule;
    }

    public void setNamerule(String namerule) {
        this.namerule = namerule;
    }

    public String getNamerule2() {
        return namerule2;
    }

    public void setNamerule2(String namerule2) {
        this.namerule2 = namerule2;
    }

    public String getIspart() {
        return ispart;
    }

    public void setIspart(String ispart) {
        this.ispart = ispart;
    }

    public String getMoresite() {
        return moresite;
    }

    public void setMoresite(String moresite) {
        this.moresite = moresite;
    }

    public String getSiteurl() {
        return siteurl;
    }

    public void setSiteurl(String siteurl) {
        this.siteurl = siteurl;
    }

    public String getSitepath() {
        return sitepath;
    }

    public void setSitepath(String sitepath) {
        this.sitepath = sitepath;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getRedirecturl() {
        return redirecturl;
    }

    public void setRedirecturl(String redirecturl) {
        this.redirecturl = redirecturl;
    }

    public String getTemplet() {
        return templet;
    }

    public void setTemplet(String templet) {
        this.templet = templet;
    }

    public String getUserip() {
        return userip;
    }

    public void setUserip(String userip) {
        this.userip = userip;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getGame_bbs() {
        return game_bbs;
    }

    public void setGame_bbs(String game_bbs) {
        this.game_bbs = game_bbs;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getMultiplayer() {
        return multiplayer;
    }

    public void setMultiplayer(String multiplayer) {
        this.multiplayer = multiplayer;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getGraphics() {
        return graphics;
    }

    public void setGraphics(String graphics) {
        this.graphics = graphics;
    }

    public String getGameplay() {
        return gameplay;
    }

    public void setGameplay(String gameplay) {
        this.gameplay = gameplay;
    }

    public String getWebsit() {
        return websit;
    }

    public void setWebsit(String websit) {
        this.websit = websit;
    }

    public String getRelease_company() {
        return release_company;
    }

    public void setRelease_company(String release_company) {
        this.release_company = release_company;
    }

    public String getMade_company() {
        return made_company;
    }

    public void setMade_company(String made_company) {
        this.made_company = made_company;
    }

    public String getTerrace() {
        return terrace;
    }

    public void setTerrace(String terrace) {
        this.terrace = terrace;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getGame_trans_name() {
        return game_trans_name;
    }

    public void setGame_trans_name(String game_trans_name) {
        this.game_trans_name = game_trans_name;
    }

    public String getFst() {
        return fst;
    }

    public void setFst(String fst) {
        this.fst = fst;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getGame_othername1() {
        return game_othername1;
    }

    public void setGame_othername1(String game_othername1) {
        this.game_othername1 = game_othername1;
    }

    public String getGame_othername2() {
        return game_othername2;
    }

    public void setGame_othername2(String game_othername2) {
        this.game_othername2 = game_othername2;
    }

    public String getZhuanti_toutiao2() {
        return zhuanti_toutiao2;
    }

    public void setZhuanti_toutiao2(String zhuanti_toutiao2) {
        this.zhuanti_toutiao2 = zhuanti_toutiao2;
    }

    public String getZhuanti_toutiao3() {
        return zhuanti_toutiao3;
    }

    public void setZhuanti_toutiao3(String zhuanti_toutiao3) {
        this.zhuanti_toutiao3 = zhuanti_toutiao3;
    }

    public String getZhuanti_toutiao4() {
        return zhuanti_toutiao4;
    }

    public void setZhuanti_toutiao4(String zhuanti_toutiao4) {
        this.zhuanti_toutiao4 = zhuanti_toutiao4;
    }

    public String getImgid() {
        return imgid;
    }

    public void setImgid(String imgid) {
        this.imgid = imgid;
    }

    public String getDownid() {
        return downid;
    }

    public void setDownid(String downid) {
        this.downid = downid;
    }

    public String getArcurl() {
        return arcurl;
    }

    public void setArcurl(String arcurl) {
        this.arcurl = arcurl;
    }

    public String getTypeurl() {
        return typeurl;
    }

    public void setTypeurl(String typeurl) {
        this.typeurl = typeurl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.typeid);
        dest.writeString(this.typeid2);
        dest.writeString(this.sortrank);
        dest.writeString(this.flag);
        dest.writeString(this.ismake);
        dest.writeString(this.channel);
        dest.writeString(this.arcrank);
        dest.writeString(this.click);
        dest.writeString(this.money);
        dest.writeString(this.title);
        dest.writeString(this.shorttitle);
        dest.writeString(this.color);
        dest.writeString(this.writer);
        dest.writeString(this.source);
        dest.writeString(this.litpic);
        dest.writeString(this.pubdate);
        dest.writeString(this.senddate);
        dest.writeString(this.mid);
        dest.writeString(this.keywords);
        dest.writeString(this.lastpost);
        dest.writeString(this.scores);
        dest.writeString(this.goodpost);
        dest.writeString(this.badpost);
        dest.writeString(this.voteid);
        dest.writeString(this.notpost);
        dest.writeString(this.description);
        dest.writeString(this.filename);
        dest.writeString(this.dutyadmin);
        dest.writeString(this.tackid);
        dest.writeString(this.mtype);
        dest.writeString(this.weight);
        dest.writeString(this.fby_id);
        dest.writeString(this.game_id);
        dest.writeString(this.feedback);
        dest.writeString(this.typedir);
        dest.writeString(this.typename);
        dest.writeString(this.corank);
        dest.writeString(this.isdefault);
        dest.writeString(this.defaultname);
        dest.writeString(this.namerule);
        dest.writeString(this.namerule2);
        dest.writeString(this.ispart);
        dest.writeString(this.moresite);
        dest.writeString(this.siteurl);
        dest.writeString(this.sitepath);
        dest.writeString(this.aid);
        dest.writeString(this.redirecturl);
        dest.writeString(this.templet);
        dest.writeString(this.userip);
        dest.writeString(this.vid);
        dest.writeString(this.game_bbs);
        dest.writeString(this.total);
        dest.writeString(this.multiplayer);
        dest.writeString(this.concept);
        dest.writeString(this.sound);
        dest.writeString(this.graphics);
        dest.writeString(this.gameplay);
        dest.writeString(this.websit);
        dest.writeString(this.release_company);
        dest.writeString(this.made_company);
        dest.writeString(this.terrace);
        dest.writeString(this.language);
        dest.writeString(this.release_date);
        dest.writeString(this.game_trans_name);
        dest.writeString(this.fst);
        dest.writeString(this.tid);
        dest.writeString(this.game_othername1);
        dest.writeString(this.game_othername2);
        dest.writeString(this.zhuanti_toutiao2);
        dest.writeString(this.zhuanti_toutiao3);
        dest.writeString(this.zhuanti_toutiao4);
        dest.writeString(this.imgid);
        dest.writeString(this.downid);
        dest.writeString(this.arcurl);
        dest.writeString(this.typeurl);
    }

    public Detail_game() {
    }

    protected Detail_game(Parcel in) {
        this.id = in.readString();
        this.typeid = in.readString();
        this.typeid2 = in.readString();
        this.sortrank = in.readString();
        this.flag = in.readString();
        this.ismake = in.readString();
        this.channel = in.readString();
        this.arcrank = in.readString();
        this.click = in.readString();
        this.money = in.readString();
        this.title = in.readString();
        this.shorttitle = in.readString();
        this.color = in.readString();
        this.writer = in.readString();
        this.source = in.readString();
        this.litpic = in.readString();
        this.pubdate = in.readString();
        this.senddate = in.readString();
        this.mid = in.readString();
        this.keywords = in.readString();
        this.lastpost = in.readString();
        this.scores = in.readString();
        this.goodpost = in.readString();
        this.badpost = in.readString();
        this.voteid = in.readString();
        this.notpost = in.readString();
        this.description = in.readString();
        this.filename = in.readString();
        this.dutyadmin = in.readString();
        this.tackid = in.readString();
        this.mtype = in.readString();
        this.weight = in.readString();
        this.fby_id = in.readString();
        this.game_id = in.readString();
        this.feedback = in.readString();
        this.typedir = in.readString();
        this.typename = in.readString();
        this.corank = in.readString();
        this.isdefault = in.readString();
        this.defaultname = in.readString();
        this.namerule = in.readString();
        this.namerule2 = in.readString();
        this.ispart = in.readString();
        this.moresite = in.readString();
        this.siteurl = in.readString();
        this.sitepath = in.readString();
        this.aid = in.readString();
        this.redirecturl = in.readString();
        this.templet = in.readString();
        this.userip = in.readString();
        this.vid = in.readString();
        this.game_bbs = in.readString();
        this.total = in.readString();
        this.multiplayer = in.readString();
        this.concept = in.readString();
        this.sound = in.readString();
        this.graphics = in.readString();
        this.gameplay = in.readString();
        this.websit = in.readString();
        this.release_company = in.readString();
        this.made_company = in.readString();
        this.terrace = in.readString();
        this.language = in.readString();
        this.release_date = in.readString();
        this.game_trans_name = in.readString();
        this.fst = in.readString();
        this.tid = in.readString();
        this.game_othername1 = in.readString();
        this.game_othername2 = in.readString();
        this.zhuanti_toutiao2 = in.readString();
        this.zhuanti_toutiao3 = in.readString();
        this.zhuanti_toutiao4 = in.readString();
        this.imgid = in.readString();
        this.downid = in.readString();
        this.arcurl = in.readString();
        this.typeurl = in.readString();
    }

    public static final Parcelable.Creator<Detail_game> CREATOR = new Parcelable.Creator<Detail_game>() {
        @Override
        public Detail_game createFromParcel(Parcel source) {
            return new Detail_game(source);
        }

        @Override
        public Detail_game[] newArray(int size) {
            return new Detail_game[size];
        }
    };
}
