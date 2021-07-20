package com.shenkong.bzzmaster.model.bean;

import java.util.ArrayList;

import io.reactivex.annotations.NonNull;

public class DetailBean {

    public ArrayList<Statistics> statistics = new ArrayList<>();

    /**
     * 提币
     */
    private ArrayList<DetailItem> applys = new ArrayList<>();

    /**
     * 转入
     */
    private ArrayList<DetailItem> transactions = new ArrayList<>();

    /**
     * 收益
     */
    private ArrayList<DetailItem> revenues = new ArrayList<>();


    public static class Statistics {
        /**
         * 类型
         */
        private int type;
        /**
         * 提币总条目数
         */
        private int number;
        /**
         * 币种
         */
        private String currency;
        /**
         * 总金额
         */
        private double amout;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public double getAmout() {
            return amout;
        }

        public void setAmout(double amout) {
            this.amout = amout;
        }

        @NonNull
        @Override
        public String toString() {
            return "Statistics{" +
                    "type=" + type +
                    ", number=" + number +
                    ", currency='" + currency + '\'' +
                    ", amout=" + amout +
                    '}';
        }
    }

    public static class DetailItem {
        /**
         * 计划
         */
        public String name;

        /**
         * 实际金额
         */
        private Double amout;
        /**
         * 币种
         */
        private String currency;
        /**
         * 时间
         */
        private String createtime;

        /**
         * 地址
         */
        public String adress = "";

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Double getAmout() {
            return amout;
        }

        public void setAmout(Double amout) {
            this.amout = amout;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getAdress() {
            return adress;
        }

        public void setAdress(String adress) {
            this.adress = adress;
        }

        @NonNull
        @Override
        public String toString() {
            return "DetailItem{" +
                    "name='" + name + '\'' +
                    ", amout=" + amout +
                    ", currency='" + currency + '\'' +
                    ", createtime=" + createtime +
                    ", adress='" + adress + '\'' +
                    '}';
        }
    }

    public ArrayList<Statistics> getStatistics() {
        return statistics;
    }

    public void setStatistics(ArrayList<Statistics> statistics) {
        this.statistics = statistics;
    }

    public ArrayList<DetailItem> getApplys() {
        return applys;
    }

    public void setApplys(ArrayList<DetailItem> applys) {
        this.applys = applys;
    }

    public ArrayList<DetailItem> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<DetailItem> transactions) {
        this.transactions = transactions;
    }

    public ArrayList<DetailItem> getRevenues() {
        return revenues;
    }

    public void setRevenues(ArrayList<DetailItem> revenues) {
        this.revenues = revenues;
    }

    @NonNull
    @Override
    public String toString() {
        return "DetailDto{" +
                "statistics=" + statistics +
                ", applys=" + applys +
                ", transactions=" + transactions +
                ", revenues=" + revenues +
                '}';
    }
}
