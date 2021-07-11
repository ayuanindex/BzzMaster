package com.shenkong.bzzmaster.model.bean;

public class ChatBean {
    /**
     * 联系我们ID
     */
    private Long chatid;

    /**
     * 标题
     */
    private String title;

    /**
     * 二维码
     */
    private String qrcode;

    /**
     * 发布人
     */
    private Long aid;

    /**
     * 备注
     */
    private String remarks;

    public Long getChatid() {
        return chatid;
    }

    public void setChatid(Long chatid) {
        this.chatid = chatid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "ChatBean{" +
                "chatid=" + chatid +
                ", title='" + title + '\'' +
                ", qrcode='" + qrcode + '\'' +
                ", aid=" + aid +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}