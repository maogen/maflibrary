package com.maf.base.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.maf.activity.BaseActivity;
import com.maf.application.BaseApplication;
import com.maf.git.GsonUtils;
import com.maf.base.permission.PermissionGroup;
import com.maf.utils.DateUtils;
import com.maf.utils.LogUtils;
import com.maf.utils.RawUtils;

import java.util.List;

import maf.com.mafproject.R;

/**
 * Created by mzg on 2016/5/23.
 * 开始界面，进入不同的测试界面
 */
public class MainActivity extends BaseActivity {
    // 声明Button控件
    private Button btnToast;
    private Button btnImage;
    private Button btnNet;
    private Button btnPrint;
    private Button btnHtml;
    private Button btnChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        btnToast = (Button) findViewById(R.id.btn_goto_toast);
        btnImage = (Button) findViewById(R.id.btn_goto_image);
        btnNet = (Button) findViewById(R.id.btn_goto_net);
        btnPrint = (Button) findViewById(R.id.btn_goto_print);
        btnHtml = (Button) findViewById(R.id.btn_goto_html);
        btnChart = (Button) findViewById(R.id.btn_goto_chart);
    }

    @Override
    protected void initEvent() {
        btnToast.setOnClickListener(this);
        btnImage.setOnClickListener(this);
        btnNet.setOnClickListener(this);
        btnPrint.setOnClickListener(this);
        btnHtml.setOnClickListener(this);
        btnChart.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        testDateUtils();
        testGsonUtils();
    }

    /**
     * 测试时间工具类
     */
    private void testDateUtils() {
        LogUtils.d("**********测试DateUtils***********");
        String dateString = DateUtils.getDateByDefault();
        LogUtils.d(dateString);
        String purposeDate = DateUtils.changeDateFormat(dateString, DateUtils.defaultDateFormat, "HH:mm:ss(yyyy/MM/dd)");
        LogUtils.d(purposeDate);
        LogUtils.d("**********END***********");
    }

    /**
     * 测试Gson工具类
     */
    private void testGsonUtils() {
        LogUtils.d("**********测试GsonUtils***********");
        String permissionJson = RawUtils.getRawStr(BaseApplication._application, R.raw.permission);
//        List<PermissionGroup> groupList =
//                GsonUtils.getByJsonString(new ArrayList<PermissionGroup>(), permissionJson);
        List<PermissionGroup> groupList = new Gson().fromJson(permissionJson, new TypeToken<List<PermissionGroup>>() {
        }.getType());
        for (int i = 0; i < groupList.size(); i++) {
            PermissionGroup group = groupList.get(i);
            LogUtils.d(GsonUtils.getJson(group));
        }
        LogUtils.d("**********END***********");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_goto_toast:
                // 进入Toast测试界面
                startActivity(ToastActivity.class);
                break;
            case R.id.btn_goto_image:
                // 进入Image测试界面
                startActivity(ImageActivity.class);
                break;
            case R.id.btn_goto_net:
                // 进入网络测试界面
                startActivity(NetActivity.class);
                break;
            case R.id.btn_goto_print:
                // 进入打印功能
                startActivity(PrintActivity.class);
                break;
            case R.id.btn_goto_html:
                // 进入html测试界面
                startActivity(HtmlActivity.class);
                break;
            case R.id.btn_goto_chart:
                // 进入图形库测试界面
                startActivity(ChartActivity.class);
                break;
            default:
                break;
        }
    }
}
