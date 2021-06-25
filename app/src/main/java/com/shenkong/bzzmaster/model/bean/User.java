package com.shenkong.bzzmaster.model.bean;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

    private static final long serialVersionUID =  7756471828000666340L;

    /**
     * 用户ID
     */
    private Long userid;

    /**
     * 手机号
     */
    private String phonenumber;

    /**
     * 密码
     */
    private String pwd;

    /**
     * 头像
     */
    private String icon;

    /**
     * 邀请码
     */
    private String code;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 资金密码
     */
    private String fundpassword;

    /**
     * 权限
     * default: 0
     */
    private Long jurisdiction;

    /**
     * 状态
     * default: 0
     */
    private Long status;

    /**
     * 实名
     * default: 0
     */
    private Long authentication;

    /**
     * 性别
     */
    private String sex;

    /**
     * 出生日期
     */
    private Date birthday;

    /**
     * 令牌
     */
    private String token;

    public User() {
    }

    public User(Long userid, String phonenumber, String pwd, String icon, String code, String email, String fundpassword, Long jurisdiction, Long status, Long authentication, String sex, Date birthday, String token) {
        this.userid = userid;
        this.phonenumber = phonenumber;
        this.pwd = pwd;
        this.icon = icon;
        this.code = code;
        this.email = email;
        this.fundpassword = fundpassword;
        this.jurisdiction = jurisdiction;
        this.status = status;
        this.authentication = authentication;
        this.sex = sex;
        this.birthday = birthday;
        this.token = token;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFundpassword() {
        return fundpassword;
    }

    public void setFundpassword(String fundpassword) {
        this.fundpassword = fundpassword;
    }

    public Long getJurisdiction() {
        return jurisdiction;
    }

    public void setJurisdiction(Long jurisdiction) {
        this.jurisdiction = jurisdiction;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Long authentication) {
        this.authentication = authentication;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "User{" +
                "userid=" + userid +
                ", phonenumber='" + phonenumber + '\'' +
                ", pwd='" + pwd + '\'' +
                ", icon='" + icon + '\'' +
                ", code='" + code + '\'' +
                ", email='" + email + '\'' +
                ", fundpassword='" + fundpassword + '\'' +
                ", jurisdiction=" + jurisdiction +
                ", status=" + status +
                ", authentication=" + authentication +
                ", sex='" + sex + '\'' +
                ", birthday=" + birthday +
                ", token='" + token + '\'' +
                '}';
    }
}

