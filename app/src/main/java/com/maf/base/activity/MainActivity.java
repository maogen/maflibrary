package com.maf.base.activity;

import android.view.View;
import android.widget.Button;

import com.google.gson.reflect.TypeToken;
import com.maf.activity.BaseActivity;
import com.maf.application.BaseApplication;
import com.maf.application.CrashHandler;
import com.maf.base.bean.JsonTestBean;
import com.maf.git.GsonUtils;
import com.maf.utils.DateUtils;
import com.maf.utils.LogUtils;
import com.maf.utils.RawUtils;

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
    private Button btnCollapsing;
    private Button btnSort;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        btnToast = (Button) findViewById(R.id.btn_goto_toast);
        btnImage = (Button) findViewById(R.id.btn_goto_image);
        btnNet = (Button) findViewById(R.id.btn_goto_net);
        btnPrint = (Button) findViewById(R.id.btn_goto_print);
        btnHtml = (Button) findViewById(R.id.btn_goto_html);
        btnChart = (Button) findViewById(R.id.btn_goto_chart);
        btnCollapsing = (Button) findViewById(R.id.btn_goto_collapsing);
        btnSort = (Button) findViewById(R.id.btn_goto_sort);

    }

    @Override
    protected void initEvent() {
        btnToast.setOnClickListener(this);
        btnImage.setOnClickListener(this);
        btnNet.setOnClickListener(this);
        btnPrint.setOnClickListener(this);
        btnHtml.setOnClickListener(this);
        btnChart.setOnClickListener(this);
        btnCollapsing.setOnClickListener(this);
        btnSort.setOnClickListener(this);
    }

    @Override
    protected void initValue() {
        CrashHandler.getInstance().setStartContext(this);
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
//        String permissionJson = RawUtils.getRawStr(BaseApplication._application, R.raw.permission);
//
//        List<PermissionGroup> groupList = GsonUtils.stringToGson(permissionJson, new TypeToken<List<PermissionGroup>>() {
//        });
//        for (int i = 0; i < groupList.size(); i++) {
//            PermissionGroup group = groupList.get(i);
//            LogUtils.d(GsonUtils.gsonToString(group));
//        }
        String jsonString = RawUtils.getRawStr(BaseApplication._application, R.raw.jsontest);
        JsonTestBean bean = GsonUtils.stringToGson(jsonString, new TypeToken<JsonTestBean>() {
        });
        if (bean.getAge() == null) {
            LogUtils.d("Integer is null");
        } else {
            LogUtils.d(bean.getAge().toString());
        }
        LogUtils.d(GsonUtils.gsonToString(bean));
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
            case R.id.btn_goto_collapsing:
                // 进入收缩测试界面
                startActivity(MyCollapsingActivity.class);
                break;
            case R.id.btn_goto_sort:
                // 进入排序界面
                startActivity(SortTestActivity.class);
                break;
            default:
                break;
        }
    }
}
