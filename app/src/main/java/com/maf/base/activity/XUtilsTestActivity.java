package com.maf.base.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.maf.utils.BaseToast;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import maf.com.mafproject.R;

/**
 * 项目名称：maflibrary
 * 类描述：
 * 创建人：zgmao
 * 创建时间：2017/4/11
 * 修改人：zgmao
 * 修改时间：2017/4/11
 * 修改备注：
 * Created by zgmao on 2017/4/11.
 */
@ContentView(R.layout.activity_x_utils)
public class XUtilsTestActivity extends Activity {
    @ViewInject(R.id.text_product_title)
    private TextView textTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 注！！！：当启动xUtils注解方法时，如果遇到程序崩溃，会自动重启应用
        x.view().inject(this);
        // 设置标题
        textTitle.setText("xUtils测试");
    }

    /**
     * 注！！！： 按钮的点击事件注解，方法必须是private，方法参数View v
     *
     * @param view
     */
    @Event(value = R.id.btn_xUtils_annotation)
    private void clickBtnAnno(View view) {
        BaseToast.makeTextShort("注解方式处理按钮单击事件");
    }

    /**
     * 请求地址
     */
    private String url = "http://api.m.mtime.cn/PageSubArea/TrailerList.api";

    /**
     * 下载大文件
     *
     * @param view
     */
    @Event(value = R.id.btn_file_download)
    private void clickBtnFileDownload(View view) {
        BaseToast.makeTextShort("下载大文件");
    }
}
