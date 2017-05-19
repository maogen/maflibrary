package com.maf.base.bean;

import com.maf.db.Column;
import com.maf.db.Entity;

/**
 * 项目名称：maflibrary
 * 类描述：账单内容
 * 创建人：zgmao
 * 创建时间：2017/5/16
 * 修改人：Administrator
 * 修改时间：2017/5/16
 * 修改备注：
 * Created by Administrator on 2017/5/16.
 */
@Entity(table = "TABLE_BILLING")
public class BillingBean {
    // auto设置是否自增，一般针对Long型变量，pk设置是否为主键
    @Column(auto = true, pk = true)
    private Long id;

    /**
     * 记录日期
     */
    @Column
    private String date;
    /**
     * 事项
     */
    @Column
    private String matter;
    /**
     * 金额
     */
    @Column
    private String money;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMatter() {
        return matter;
    }

    public void setMatter(String matter) {
        this.matter = matter;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
