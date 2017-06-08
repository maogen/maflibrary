package com.scan.idcard.activity;

import java.io.Serializable;

/**
 * 项目名称：maflibrary
 * 类描述：身份证扫描结果
 * 创建人：zgmao
 * 创建时间：2017/6/8
 * 修改人：zgmao
 * 修改时间：2017/6/8
 * 修改备注：
 * Created by zgmao on 2017/6/8.
 */

public class IDCardResult implements Serializable {
    private String cardNum;
    private String name;
    private String sex;
    private String address;
    private String nation;
    private String birth;

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }
}
