package com.gavin.your3dmgame.entity;

import java.util.List;

/**
 * Created by GaVin on 2016/10/11 0011.
 */

public class Comment_item {

    /**
     * code : 1
     * description : {"data":[{"id":"5828915","aid":"3598806","typeid":"0","username":"匿名发表","ip":"192.168.0.123","ip1":"","ip2":"","ischeck":"1","dtime":"1476189664","mid":"0","bad":"0","good":"0","ftype":"","face":"0","msg":"底蕴","cid":"","reid":"0","topid":"0","floor":"2","reply":"0"},{"id":"5828914","aid":"3598806","typeid":"0","username":"匿名发表","ip":"192.168.0.123","ip1":"","ip2":"","ischeck":"1","dtime":"1476189655","mid":"0","bad":"0","good":"0","ftype":"","face":"0","msg":"居然送啥","cid":"","reid":"0","topid":"0","floor":"1","reply":"0"}],"paging":{"n":"1","size":8,"start":0,"total":"2","totalpage":1}}
     */

    private String code;
    /**
     * data : [{"id":"5828915","aid":"3598806","typeid":"0","username":"匿名发表","ip":"192.168.0.123","ip1":"","ip2":"","ischeck":"1","dtime":"1476189664","mid":"0","bad":"0","good":"0","ftype":"","face":"0","msg":"底蕴","cid":"","reid":"0","topid":"0","floor":"2","reply":"0"},{"id":"5828914","aid":"3598806","typeid":"0","username":"匿名发表","ip":"192.168.0.123","ip1":"","ip2":"","ischeck":"1","dtime":"1476189655","mid":"0","bad":"0","good":"0","ftype":"","face":"0","msg":"居然送啥","cid":"","reid":"0","topid":"0","floor":"1","reply":"0"}]
     * paging : {"n":"1","size":8,"start":0,"total":"2","totalpage":1}
     */

    private DescriptionBean description;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DescriptionBean getDescription() {
        return description;
    }

    public void setDescription(DescriptionBean description) {
        this.description = description;
    }

    public static class DescriptionBean {
        /**
         * n : 1
         * size : 8
         * start : 0
         * total : 2
         * totalpage : 1
         */

        private PagingBean paging;
        /**
         * id : 5828915
         * aid : 3598806
         * typeid : 0
         * username : 匿名发表
         * ip : 192.168.0.123
         * ip1 :
         * ip2 :
         * ischeck : 1
         * dtime : 1476189664
         * mid : 0
         * bad : 0
         * good : 0
         * ftype :
         * face : 0
         * msg : 底蕴
         * cid :
         * reid : 0
         * topid : 0
         * floor : 2
         * reply : 0
         */

        private List<DataBean> data;

        public PagingBean getPaging() {
            return paging;
        }

        public void setPaging(PagingBean paging) {
            this.paging = paging;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class PagingBean {
            private String n;
            private int size;
            private int start;
            private String total;
            private int totalpage;

            public String getN() {
                return n;
            }

            public void setN(String n) {
                this.n = n;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public int getStart() {
                return start;
            }

            public void setStart(int start) {
                this.start = start;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public int getTotalpage() {
                return totalpage;
            }

            public void setTotalpage(int totalpage) {
                this.totalpage = totalpage;
            }
        }

        public static class DataBean {
            private String id;
            private String aid;
            private String typeid;
            private String username;
            private String ip;
            private String ip1;
            private String ip2;
            private String ischeck;
            private String dtime;
            private String mid;
            private String bad;
            private String good;
            private String ftype;
            private String face;
            private String msg;
            private String cid;
            private String reid;
            private String topid;
            private String floor;
            private String reply;

            @Override
            public String toString() {
                return "DataBean{" +
                        "id='" + id + '\'' +
                        ", aid='" + aid + '\'' +
                        ", typeid='" + typeid + '\'' +
                        ", username='" + username + '\'' +
                        ", ip='" + ip + '\'' +
                        ", ip1='" + ip1 + '\'' +
                        ", ip2='" + ip2 + '\'' +
                        ", ischeck='" + ischeck + '\'' +
                        ", dtime='" + dtime + '\'' +
                        ", mid='" + mid + '\'' +
                        ", bad='" + bad + '\'' +
                        ", good='" + good + '\'' +
                        ", ftype='" + ftype + '\'' +
                        ", face='" + face + '\'' +
                        ", msg='" + msg + '\'' +
                        ", cid='" + cid + '\'' +
                        ", reid='" + reid + '\'' +
                        ", topid='" + topid + '\'' +
                        ", floor='" + floor + '\'' +
                        ", reply='" + reply + '\'' +
                        '}';
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getAid() {
                return aid;
            }

            public void setAid(String aid) {
                this.aid = aid;
            }

            public String getTypeid() {
                return typeid;
            }

            public void setTypeid(String typeid) {
                this.typeid = typeid;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getIp() {
                return ip;
            }

            public void setIp(String ip) {
                this.ip = ip;
            }

            public String getIp1() {
                return ip1;
            }

            public void setIp1(String ip1) {
                this.ip1 = ip1;
            }

            public String getIp2() {
                return ip2;
            }

            public void setIp2(String ip2) {
                this.ip2 = ip2;
            }

            public String getIscheck() {
                return ischeck;
            }

            public void setIscheck(String ischeck) {
                this.ischeck = ischeck;
            }

            public String getDtime() {
                return dtime;
            }

            public void setDtime(String dtime) {
                this.dtime = dtime;
            }

            public String getMid() {
                return mid;
            }

            public void setMid(String mid) {
                this.mid = mid;
            }

            public String getBad() {
                return bad;
            }

            public void setBad(String bad) {
                this.bad = bad;
            }

            public String getGood() {
                return good;
            }

            public void setGood(String good) {
                this.good = good;
            }

            public String getFtype() {
                return ftype;
            }

            public void setFtype(String ftype) {
                this.ftype = ftype;
            }

            public String getFace() {
                return face;
            }

            public void setFace(String face) {
                this.face = face;
            }

            public String getMsg() {
                return msg;
            }

            public void setMsg(String msg) {
                this.msg = msg;
            }

            public String getCid() {
                return cid;
            }

            public void setCid(String cid) {
                this.cid = cid;
            }

            public String getReid() {
                return reid;
            }

            public void setReid(String reid) {
                this.reid = reid;
            }

            public String getTopid() {
                return topid;
            }

            public void setTopid(String topid) {
                this.topid = topid;
            }

            public String getFloor() {
                return floor;
            }

            public void setFloor(String floor) {
                this.floor = floor;
            }

            public String getReply() {
                return reply;
            }

            public void setReply(String reply) {
                this.reply = reply;
            }
        }
    }
}
