package com.shenkong.bzzmaster.model.bean;

public class FrontPage<T> {
    //每页显示条数
    private int rows;

    //当前页数
    private int page;

    //排序的字段
    private String sidx;

    //排序方式 asc升序  desc降序
    private String sord;

    private Object keyvalue;

    //总条目
    private Long total;

    private T t;

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Object getKeyvalue() {
        return keyvalue;
    }

    public void setKeyvalue(Object keyvalue) {
        this.keyvalue = keyvalue;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    @Override
    public String toString() {
        return "FrontPage{" +
                "rows=" + rows +
                ", page=" + page +
                ", sidx='" + sidx + '\'' +
                ", sord='" + sord + '\'' +
                ", keyvalue=" + keyvalue +
                ", total=" + total +
                ", t=" + t +
                '}';
    }
}
