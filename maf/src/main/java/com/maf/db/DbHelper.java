package com.maf.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 数据库帮助类
 *
 * @author nanhuang
 */
public class DbHelper
{

    /**
     * 数据库类
     */
    private SQLiteDatabase sqldb;
    /**
     * context
     */
    Context ctx;

    /**
     * 构造方法初始化
     *
     * @param context
     */
    public DbHelper(Context context)
    {
        this.ctx = context;
    }

    /**
     * 在应用内创建数据库
     *
     * @param dbname
     * @param dbversion
     */
    public void init(String dbname, int dbversion)
    {
        this.sqldb = new SqliteDbHelper(ctx, dbname, dbversion)
                .getWritableDatabase();
    }

    ;

    /**
     * 保存
     *
     * @param obj
     */
    public void save(Object obj)
    {
        if (obj == null)
            return;
        checkOrCreateTable(obj.getClass());
        SqlProxy proxy = SqlProxy.insert(obj);
        sqldb.execSQL(proxy.getSql(), proxy.paramsArgs());
    }

    /**
     * 更新
     *
     * @param obj
     */
    public void update(Object obj)
    {
        if (obj == null)
            return;
        checkOrCreateTable(obj.getClass());
        SqlProxy proxy = SqlProxy.update(obj);
        sqldb.execSQL(proxy.getSql(), proxy.paramsArgs());
    }

    /**
     * 单个结果集查询
     *
     * @param clazz
     * @param where
     * @param whereargs
     * @return
     */
    public <T> T queryFirst(Class<T> clazz, String where, Object... whereargs)
    {
        if (where.indexOf("limit") < -1) {
            where += " limit 0,1";
        }
        List<T> list = queryList(clazz, where, whereargs);
        if (list == null || list.size() == 0)
            return null;
        return list.get(0);
    }

    /**
     * 通过sql查询多个结果集
     *
     * @param clazz
     * @param where
     * @param whereargs
     * @return
     */
    public <T> List<T> queryList(Class<T> clazz, String where,
                                 Object... whereargs)
    {
        checkOrCreateTable(clazz);
        SqlProxy proxy = SqlProxy.select(clazz, where, whereargs);
        return queryList(proxy);
    }

    /**
     * 查询单个结果集
     *
     * @param proxy
     * @return
     */
    public <T> T queryFirst(SqlProxy proxy)
    {
        String sql = proxy.getSql();
        if (sql.indexOf("limit") < -1) {
            sql += " limit 0,1";
            proxy = SqlProxy.select(proxy.getRelClass(), sql,
                    (Object) proxy.paramsArgs());
        }
        List<T> list = queryList(proxy);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 查询多个结果集
     *
     * @param proxy
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> queryList(SqlProxy proxy)
    {
        Cursor cursor = sqldb.rawQuery(proxy.getSql(), proxy.paramsArgs());
        try {
            List<T> list = new ArrayList<T>();
            while (cursor.moveToNext()) {
                T t = (T) cursorToBean(cursor, proxy.getRelClass());
                list.add(t);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            cursor = null;
        }
        return null;
    }

    /**
     * 获取最近一次插入的主键
     *
     * @return
     */
    public Long getLastInsertId(String table)
    {
        Cursor c = sqldb.rawQuery("select Max(id) from " + table, null);
        Long count = 0L;
        if (c.moveToNext()) {
            count = c.getLong(0);
        }
        c.close();
        return count;
    }

    /**
     * 对象封装
     *
     * @param cursor
     * @param clazz
     * @return
     */
    private <T> T cursorToBean(Cursor cursor, Class<T> clazz)
    {
        EntityInfo entity = EntityInfo.build(clazz);
        Set<String> keys = entity.getColumns().keySet();
        T obj = null;
        try {
            obj = clazz.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        // 反射获取值
        for (Iterator<String> iterator = keys.iterator(); iterator.hasNext(); ) {
            String key = iterator.next();
            if (key.startsWith("$")) {
                // 有的环境，编译后会多个$开头的成员变量，需过滤
                continue;
            }
            String column = entity.getColumns().get(key);
            Field field = BeanUtil.getDeclaredField(clazz, key);
            if (field.getType().equals(Integer.class)
                    || field.getType().equals(int.class)) {
                BeanUtil.setProperty(obj, key,
                        cursor.getInt(cursor.getColumnIndex(column)));
            } else if (field.getType().equals(Long.class)
                    || field.getType().equals(long.class)) {
                BeanUtil.setProperty(obj, key,
                        cursor.getLong(cursor.getColumnIndex(column)));
            } else if (field.getType().equals(Double.class)
                    || field.getType().equals(double.class)) {
                BeanUtil.setProperty(obj, key,
                        cursor.getDouble(cursor.getColumnIndex(column)));
            } else if (field.getType().equals(Float.class)
                    || field.getType().equals(float.class)) {
                BeanUtil.setProperty(obj, key,
                        cursor.getFloat(cursor.getColumnIndex(column)));
            } else if (field.getType().equals(String.class)) {
                BeanUtil.setProperty(obj, key,
                        cursor.getString(cursor.getColumnIndex(column)));
            } else if (field.getType().equals(Date.class)) {
                try {
                    BeanUtil.setProperty(
                            obj,
                            key,
                            new Date(cursor.getLong(cursor
                                    .getColumnIndex(column))));
                } catch (Exception e) {
                }
            } else if (field.getType().equals(Boolean.class)
                    || field.getType().equals(boolean.class)) {
                String value = cursor.getString(cursor.getColumnIndex(column));
                if (TextUtils.isEmpty(value)) {
                    BeanUtil.setProperty(obj, key, cursor.getInt(cursor
                            .getColumnIndex(column)) == 0 ? false : true);
                } else {
                    BeanUtil.setProperty(obj, key, "true".equals(value) ? true : false);
                }

            }
        }

        return obj;
    }

    /**
     * 判断表是否存在
     *
     * @param clazz
     */
    @SuppressWarnings("rawtypes")
    public void checkOrCreateTable(Class clazz)
    {
        EntityInfo info = EntityInfo.build(clazz);
        if (info.isChecked()) {
            return;
        } else {
            boolean isexit = checkTable(info.table);
            if (!isexit) {
                String sql = getCreatTableSQL(clazz);
                sqldb.execSQL(sql);
            }
        }
    }

    /**
     * 获取表创建语句
     *
     * @param clazz
     * @return
     */
    private static String getCreatTableSQL(Class<?> clazz)
    {
        EntityInfo info = EntityInfo.build(clazz);
        StringBuffer sql = new StringBuffer();
        sql.append("CREATE TABLE IF NOT EXISTS ");
        sql.append(info.getTable());
        sql.append(" ( ");
        Map<String, String> propertys = info.getColumns();
        Set<String> keys = propertys.keySet();
        for (Iterator<String> iterator = keys.iterator(); iterator.hasNext(); ) {
            String key = iterator.next();
            if (key.startsWith("$")) {
                // 有的环境，编译后会多个$开头的成员变量，需过滤
                continue;
            }
            sql.append(propertys.get(key));
            Class<?> dataType = BeanUtil.getDeclaredField(clazz, key).getType();
            if (dataType == int.class || dataType == Integer.class
                    || dataType == long.class || dataType == Long.class) {
                sql.append(" INTEGER");
            } else if (dataType == float.class || dataType == Float.class
                    || dataType == double.class || dataType == Double.class) {
                sql.append(" REAL");
            } else if (dataType == boolean.class || dataType == Boolean.class) {
                sql.append(" NUMERIC");
            }
            if (key.equals(info.pk)) {
                sql.append(" PRIMARY KEY");
                if (info.pkAuto) {
                    sql.append(" AUTOINCREMENT");
                }
            }
            sql.append(",");
        }
        sql.deleteCharAt(sql.length() - 1);
        sql.append(" )");
        return sql.toString();
    }

    /**
     * 检查表是否存在
     *
     * @param table
     * @return
     */
    private boolean checkTable(String table)
    {
        Cursor cursor = null;
        try {
            String sql = "SELECT COUNT(*) AS c FROM sqlite_master WHERE type ='table' AND name ='"
                    + table + "' ";
            cursor = sqldb.rawQuery(sql, null);
            if (cursor != null && cursor.moveToNext()) {
                int count = cursor.getInt(0);
                if (count > 0) {
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            cursor = null;
        }
        return false;
    }

    /**
     * 根据条件查找数据数目查找
     *
     * @param clazz
     * @param where
     * @param whereargs
     * @return
     */
    public int getCount(Class<?> clazz, String where,
                        Object... whereargs)
    {
        SqlProxy sqlProxy = SqlProxy.count(clazz, where, whereargs);
        Cursor cursor = sqldb.rawQuery(sqlProxy.getSql(), sqlProxy.paramsArgs());
        try {
            if (cursor != null && cursor.moveToNext()) {
                int count = cursor.getInt(0);
                if (count > 0) {
                    return count;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            cursor = null;
        }
        return 0;
    }

    /**
     * 继承安卓splite数据库操作类
     *
     * @author nanHuang
     */
    class SqliteDbHelper extends SQLiteOpenHelper
    {
        //private MyApp ap;
        private Context context;
        private String dbName;

        public SqliteDbHelper(Context context, String name, int version)
        {
            super(context, name, null, version);
            this.context = context;
            this.dbName = name;
            //ap = (MyApp) this.context.getApplicationContext();
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            // 如果版本号不一致，删除原来的数据库
            if (oldVersion != newVersion && null != context) {
                context.deleteDatabase(dbName);
                try {
                    sqldb = new SqliteDbHelper(context, dbName, newVersion)
                            .getWritableDatabase();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * @return the db
     */
    public SQLiteDatabase getDb()
    {
        return sqldb;
    }

    /**
     * @param db the db to set
     */
    public void setDb(SQLiteDatabase db)
    {
        this.sqldb = db;
    }

}
