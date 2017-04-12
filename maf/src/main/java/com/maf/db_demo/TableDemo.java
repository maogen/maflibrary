package com.maf.db_demo;


import com.maf.db.Column;
import com.maf.db.Entity;
import com.maf.db.NoColumn;

/**
 * Created by ZGMAO on 2016/3/10.
 * 数据库表格模版类，用于展示数据库db框架的使用方法
 * Entity标签用于定义表格名称。
 * Column标签用于定义表格列的。
 * NoColumn标签说明该字段不是数据表的一列
 */
@Entity(table = "TABLE_DEMO")
public class TableDemo {
    // auto设置是否自增，一般针对Long型变量，pk设置是否为主键
    @Column(auto = true, pk = true)
    private Long id;
    @Column
    private String name;
    // 设置该变量不是表的一列
    @NoColumn
    private String noName;
    @Column
    private String password;
    @Column
    private String demoindex;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNoName() {
        return noName;
    }

    public void setNoName(String noName) {
        this.noName = noName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDemoindex() {
        return demoindex;
    }

    public void setDemoindex(String demoindex) {
        this.demoindex = demoindex;
    }
}
