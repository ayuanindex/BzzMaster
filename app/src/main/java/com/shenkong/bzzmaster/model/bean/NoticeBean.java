package com.shenkong.bzzmaster.model.bean;

public class NoticeBean {
    /**
     * 公告ID
     */
    private long noticeid;

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
    private long type;

    /**
     * 详情地址
     */
    private String link;

    public long getNoticeid() {
        return noticeid;
    }

    public void setNoticeid(long noticeid) {
        this.noticeid = noticeid;
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

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "NoticeBean{" +
                "noticeid=" + noticeid +
                ", message='" + message + '\'' +
                ", title='" + title + '\'' +
                ", pushtime='" + pushtime + '\'' +
                ", aid=" + aid +
                ", type=" + type +
                ", link='" + link + '\'' +
                '}';
    }
}
