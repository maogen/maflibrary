package com.maf.application;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.maf.db.DbHelper;
import com.maf.utils.FileUtils;
import com.maf.utils.SPUtils;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.squareup.leakcanary.LeakCanary;

import org.xutils.x;

/**
 * Created by mzg on 2016/5/23.
 * 用于初始化maf中的相关变量
 */
public class BaseApplication extends MultiDexApplication {
    public static Context _application;
    /**
     * 是否开启内存溢出检测
     */
    private boolean isStartLeakCanary = true;
    /**
     * 数据库操作类
     */
    public DbHelper db;

    @Override
    public void onCreate() {
        super.onCreate();
        _application = getApplicationContext();
        initDB();
        initImageLoader();
        initLeakCanary();
        initXUtils();

        FileUtils.initDir();

    }

    /**
     * 分割 Dex 支持
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * 初始化数据库
     */
    private void initDB() {
        db = new DbHelper(getApplicationContext());
        String dbName = (String) SPUtils.get(this, "dataBase", "MAF_DB");
        db.init(dbName, 1);
    }

    /**
     * 初始化ImageLoader
     */
    private void initImageLoader() {
        // ImageLoader
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }

    /**
     * 初始化内存溢出检测
     */
    private void initLeakCanary() {
        if (isStartLeakCanary) {
            LeakCanary.install(this);
        }
    }

    /**
     * 初始化xUtils
     */
    private void initXUtils() {
        x.Ext.init(this);
        x.Ext.setDebug(true); // 是否输出debug日志
    }

    /**
     * 是否开始内存检测
     *
     * @param isStart 设置是否开启
     */
    public void startLeakCanary(boolean isStart) {
        isStartLeakCanary = isStart;
    }
}
