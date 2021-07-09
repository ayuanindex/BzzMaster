package com.shenkong.bzzmaster.model.bean;

public class CarouselBean {

    private int carouselid;
    private String message;
    private String createtime;
    private String pushtime;
    private String invalidtime;
    private String skiplink;
    private int staue;
    private int aid;

    public int getCarouselid() {
        return carouselid;
    }

    public void setCarouselid(int carouselid) {
        this.carouselid = carouselid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getPushtime() {
        return pushtime;
    }

    public void setPushtime(String pushtime) {
        this.pushtime = pushtime;
    }

    public String getInvalidtime() {
        return invalidtime;
    }

    public void setInvalidtime(String invalidtime) {
        this.invalidtime = invalidtime;
    }

    public String getSkiplink() {
        return skiplink;
    }

    public void setSkiplink(String skiplink) {
        this.skiplink = skiplink;
    }

    public int getStaue() {
        return staue;
    }

    public void setStaue(int staue) {
        this.staue = staue;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    @Override
    public String toString() {
        return "CarouselBean{" +
                "carouselid=" + carouselid +
                ", message='" + message + '\'' +
                ", createtime='" + createtime + '\'' +
                ", pushtime='" + pushtime + '\'' +
                ", invalidtime='" + invalidtime + '\'' +
                ", skiplink='" + skiplink + '\'' +
                ", staue=" + staue +
                ", aid=" + aid +
                '}';
    }
}