package com.shenkong.bzzmaster.common.base;



public class ResultBean<Date> {

    private int code;
    private String msg;
    private Date date;


    public ResultBean(int code, String msg, Date date) {
        this.code = code;
        this.msg = msg;
        this.date = date;
    }

    public ResultBean() {

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ResultBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", date=" + date +
                '}';
    }
}