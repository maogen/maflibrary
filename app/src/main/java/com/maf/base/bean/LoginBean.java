package com.maf.base.bean;

/**
 * Created by mzg on 2016/6/1.
 * 登录结果
 */
public class LoginBean {
    private String access_token;
    private String token_type;
    private String expires_in;
    private String username;
    private String biz_userid;
    private String biz_username;
    private String biz_orgid;
    private String issued;
    private String expires;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBiz_userid() {
        return biz_userid;
    }

    public void setBiz_userid(String biz_userid) {
        this.biz_userid = biz_userid;
    }

    public String getBiz_username() {
        return biz_username;
    }

    public void setBiz_username(String biz_username) {
        this.biz_username = biz_username;
    }

    public String getBiz_orgid() {
        return biz_orgid;
    }

    public void setBiz_orgid(String biz_orgid) {
        this.biz_orgid = biz_orgid;
    }

    public String getIssued() {
        return issued;
    }

    public void setIssued(String issued) {
        this.issued = issued;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }
}
