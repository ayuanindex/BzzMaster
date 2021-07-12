package com.shenkong.bzzmaster.model.bean;

import java.util.ArrayList;

public class RevenueListBean extends FrontPage {
    /**
     * 总共所有的收益汇总
     */
    private ArrayList<RevenueBean> allGains = new ArrayList<>();

    private ArrayList<RevenueBean> revenueDayLists = new ArrayList<>();

    public ArrayList<RevenueBean> getAllGains() {
        return allGains;
    }

    public void setAllGains(ArrayList<RevenueBean> allGains) {
        this.allGains = allGains;
    }

    public ArrayList<RevenueBean> getRevenueDayLists() {
        return revenueDayLists;
    }

    public void setRevenueDayLists(ArrayList<RevenueBean> revenueDayLists) {
        this.revenueDayLists = revenueDayLists;
    }

    @Override
    public String toString() {
        return "RevenueListBean{" +
                "allGains=" + allGains +
                ", revenueDayLists=" + revenueDayLists +
                '}';
    }
}
