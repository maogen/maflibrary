package com.maf.bean;

import android.content.Context;

import com.maf.application.BaseApplication;
import com.maf.db.DbHelper;

import java.util.List;

/**
 * Created by ZGMAO on 2016/3/10.
 * 用于操作数据表的dao层
 */
public class TableDemoDao {
    // 数据库操作类
    private DbHelper db;
    private BaseApplication ap;
    private String tableName = "TABLE_DEMO";

    /**
     * 带context的构造函数
     *
     * @param context
     */
    public TableDemoDao(Context context) {
        ap = (BaseApplication) context.getApplicationContext();
        db = ap.db;
        // 检查表是否存在，不存在则创建
        db.checkOrCreateTable(TableDemo.class);
    }

    /**
     * 插入或者更新函数
     *
     * @param vo
     * @return 主键
     */
    public String insertOrUpdate(TableDemo vo) {
        Long id = vo.getId();
        if (null == id) {
            // 如果主键为空，新的数据，插入
            db.save(vo);
            // 根据demo里面定义的表名，得到最新的一个主键
            Long newId = db.getLastInsertId(tableName);
            vo.setId(newId);
        } else {
            // 主键不为空，根据主键更新
            // 此时要求根据主键要在表中能找到数据，并更新
            db.update(vo);
        }
        return String.valueOf(vo.getId());
    }

    /**
     * 根据名字查找数据
     *
     * @param name
     * @return
     */
    public TableDemo getItemByName(String name) {
        if (null == name || "".equals(name)) {
            // 如果查找条件为空，返回空对象
            return null;
        }
        // 拼接name字段，查找
        StringBuffer sql = new StringBuffer();
        sql.append(":name = ?");
        TableDemo demo = db.queryFirst(TableDemo.class, sql.toString(),
                name);
        return demo;
    }

    /**
     * 根据名字查找列表数据
     *
     * @param name
     * @return
     */
    public List<TableDemo> getListByName(String name) {
        if (null == name || "".equals(name)) {
            // 如果查找条件为空，返回空对象
            return null;
        }
        StringBuffer sql = new StringBuffer();
        sql.append(":name = ?");
        List<TableDemo> demos = db.queryList(TableDemo.class, sql.toString(),
                name);
        return demos;
    }

    /**
     * 根据索引排序，得到最后一个（或者第一个）.
     * 前提：要在表格里面定义index字段（使用的字段名要统一）
     *
     * @return
     */
    public TableDemo getItemByIndex() {
        StringBuffer sql = new StringBuffer();
        sql.append(":name <> ? order by demoindex desc");
        return db.queryFirst(TableDemo.class, sql.toString(), "");
    }

    /**
     * 查找所有表中数据
     *
     * @return
     */
    public List<TableDemo> getAllData() {
        return db.queryList(TableDemo.class, "", "");
    }

    /**
     * 根据name字段查找数据数目
     *
     * @param name
     * @return
     */
    public int getCountByName(String name) {
        StringBuffer sql = new StringBuffer();
        sql.append(":name = ? ");
        return db.getCount(TableDemo.class, sql.toString(), name);
    }

    /**
     * 根据名称删除数据
     *
     * @param name
     * @return
     */
    public boolean deleteByName(String name) {
        Boolean result = false;
        try {
            db.getDb().execSQL("delete from " + tableName + " where name = " + name);
            result = true;
        } catch (Exception e) {
            return result;
        }
        return result;
    }

    /**
     * 删除表格所有数据
     *
     * @return
     */
    public boolean deleteTable() {
        Boolean result = false;
        try {
            db.getDb().execSQL("delete from " + tableName);
            result = true;
        } catch (Exception e) {
            return result;
        }
        return result;
    }
}
