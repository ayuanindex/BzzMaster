package com.shenkong.bzzmaster.model.bean;

import java.util.Date;

public class AppUpdateBean {

    /**
     * 客户端ID
     */
    private long appid;

    /**
     * 版本号
     */
    private long edition;

    /**
     * 下载地址
     */
    private String downloadurl;

    /**
     * 日志
     */
    private String message;

    /**
     * 标题
     */
    private String title;

    /**
     * 发布时间
     */
    private String pushtime;

    /**
     * 发布人
     */
    private long aid;

    /**
     * 类型
     */
    private int type;

    /**
     * 类型
     */
    private String firm;

    public long getAppid() {
        return appid;
    }

    public void setAppid(long appid) {
        this.appid = appid;
    }

    public long getEdition() {
        return edition;
    }

    public void setEdition(long edition) {
        this.edition = edition;
    }

    public String getDownloadurl() {
        return downloadurl;
    }

    public void setDownloadurl(String downloadurl) {
        this.downloadurl = downloadurl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPushtime() {
        return pushtime;
    }

    public void setPushtime(String pushtime) {
        this.pushtime = pushtime;
    }

    public long getAid() {
        return aid;
    }

    public void setAid(long aid) {
        this.aid = aid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFirm() {
        return firm;
    }

    public void setFirm(String firm) {
        this.firm = firm;
    }

    @Override
    public String toString() {
        return "AppUpdateBean{" +
                "appid=" + appid +
                ", edition=" + edition +
                ", downloadurl='" + downloadurl + '\'' +
                ", message='" + message + '\'' +
                ", title='" + title + '\'' +
                ", pushtime='" + pushtime + '\'' +
                ", aid=" + aid +
                ", type=" + type +
                ", firm='" + firm + '\'' +
                '}';
    }
}
