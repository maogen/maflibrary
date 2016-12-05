package com.maf.base.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.google.gson.reflect.TypeToken;
import com.maf.activity.BaseTitleActivity;
import com.maf.application.BaseApplication;
import com.maf.application.CrashHandler;
import com.maf.base.bean.JsonTestBean;
import com.maf.git.GsonUtils;
import com.maf.popupwindow.BaseListPopup;
import com.maf.utils.BaseToast;
import com.maf.utils.DateUtils;
import com.maf.utils.LogUtils;
import com.maf.utils.RawUtils;

import maf.com.mafproject.R;

/**
 * Created by mzg on 2016/5/23.
 * 开始界面，进入不同的测试界面
 */
public class MainActivity extends BaseTitleActivity {
    private int[] btnIds = {R.id.btn_goto_toast, R.id.btn_goto_image,
            R.id.btn_goto_net, R.id.btn_goto_print,
            R.id.btn_goto_html, R.id.btn_goto_chart,
            R.id.btn_goto_collapsing, R.id.btn_goto_sort,
            R.id.btn_goto_code, R.id.btn_goto_hot_fix,
            R.id.btn_goto_main_text, R.id.btn_goto_load};
    // 声明Button控件
    private Button[] btn = new Button[btnIds.length];

    private BaseListPopup listPopup;// 菜单

    @Override
    protected void initTitleView() {
        titleBarView.setTitle("首页");
        titleBarView.setOnMenuClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 显示菜单
                BaseToast.makeTextShort("点击菜单");
                listPopup.showBottomByView(view);
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        for (int i = 0; i < btnIds.length; i++) {
            btn[i] = (Button) findViewById(btnIds[i]);
            btn[i].setOnClickListener(this);
        }
        String[] menu = {"菜单1", "菜单2"};
        listPopup = new BaseListPopup(this);
        listPopup.setMenu(menu, null, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BaseToast.makeTextShort("点击第" + i + "个菜单");
            }
        });
    }

    @Override
    protected void initEvent() {
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
            case R.id.btn_goto_code:
                // 进入二维码测试界面
                startActivity(CodeActivity.class);
                break;
            case R.id.btn_goto_hot_fix:
                // 进入热修复界面
                startActivity(HotFixActivity.class);
                break;
            case R.id.btn_goto_main_text:
                // 进入测试界面
                startActivity(com.maf.base.test.MainActivity.class);
                break;
            case R.id.btn_goto_load:
                // 进入测试上拉刷新，下拉加载更多组件测试界面
                startActivity(LoadMoreActivity.class);
                break;
            case R.id.image_title_back:
                // 显示菜单
                BaseToast.makeTextShort("点击菜单");
                listPopup.showBottomByView(v);
                break;
            default:
                break;
        }
    }
}
