package com.maf.base.logic;

import android.content.Context;
import android.view.View;

import com.maf.activity.BaseActivity;
import com.maf.base.activity.ChartActivity;
import com.maf.base.activity.CodeActivity;
import com.maf.base.activity.GPSActivity;
import com.maf.base.activity.HotFixActivity;
import com.maf.base.activity.HtmlActivity;
import com.maf.base.activity.ImageActivity;
import com.maf.base.activity.LoadMoreActivity;
import com.maf.base.activity.MyCollapsingActivity;
import com.maf.base.activity.MyViewTestActivity;
import com.maf.base.activity.NetActivity;
import com.maf.base.activity.ReadPdfActivity;
import com.maf.base.activity.ShellTestActivity;
import com.maf.base.activity.SortTestActivity;
import com.maf.base.activity.SystemTestActivity;
import com.maf.base.activity.ToastActivity;
import com.maf.base.activity.XListViewActivity;
import com.maf.base.activity.XPosedActivity;
import com.maf.base.activity.XUtilsTestActivity;
import com.maf.popupwindow.BaseListPopup;
import com.maf.utils.BaseToast;
import com.maf.utils.FileUtils;

import maf.com.mafproject.R;

/**
 * 项目名称：maflibrary
 * 类描述：首页的业务逻辑
 * 创建人：zgmao
 * 创建时间：2017/5/4
 * 修改人：Administrator
 * 修改时间：2017/5/4
 * 修改备注：
 * Created by Administrator on 2017/5/4.
 */

public class MainLogic {
    private Context context;
    private BaseListPopup listPopup;// 菜单

    public MainLogic(Context _context) {
        this.context = _context;
    }

    public void setMenuPopup(BaseListPopup _listPopup) {
        this.listPopup = _listPopup;
    }

    /**
     * 进入不同的界面的业务逻辑
     *
     * @param view 按钮控件
     */
    public void onBtnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_goto_toast:
                // 进入Toast测试界面
                ((BaseActivity) context).startActivity(ToastActivity.class);
                break;
            case R.id.btn_goto_image:
                // 进入Image测试界面
                ((BaseActivity) context).startActivity(ImageActivity.class);
                break;
            case R.id.btn_goto_net:
                // 进入网络测试界面
                ((BaseActivity) context).startActivity(NetActivity.class);
                break;
            case R.id.btn_goto_print:
                // 进入打印功能
//                startActivity(PrintActivity.class);
                break;
            case R.id.btn_goto_html:
                // 进入html测试界面
                ((BaseActivity) context).startActivity(HtmlActivity.class);
                break;
            case R.id.btn_goto_chart:
                // 进入图形库测试界面
                ((BaseActivity) context).startActivity(ChartActivity.class);
                break;
            case R.id.btn_goto_collapsing:
                // 进入收缩测试界面
                ((BaseActivity) context).startActivity(MyCollapsingActivity.class);
                break;
            case R.id.btn_goto_sort:
                // 进入排序界面
                ((BaseActivity) context).startActivity(SortTestActivity.class);
                break;
            case R.id.btn_goto_code:
                // 进入二维码测试界面
                ((BaseActivity) context).startActivity(CodeActivity.class);
                break;
            case R.id.btn_goto_hot_fix:
                // 进入热修复界面
                ((BaseActivity) context).startActivity(HotFixActivity.class);
                break;
            case R.id.btn_goto_main_text:
                // 进入测试界面
                ((BaseActivity) context).startActivity(com.maf.base.test.MainActivity.class);
                break;
            case R.id.btn_goto_load:
                // 进入测试上拉刷新，下拉加载更多组件测试界面
                ((BaseActivity) context).startActivity(LoadMoreActivity.class);
                break;
            case R.id.image_title_back:
                // 显示菜单
                BaseToast.makeTextShort("点击菜单");
                if (listPopup != null) {
                    listPopup.showBottomByView(view);
                }
                break;
            case R.id.btn_goto_slide:
                // 自定义控件
                ((BaseActivity) context).startActivity(MyViewTestActivity.class);
                break;
            case R.id.btn_goto_system:
                // 进入系统测试界面
                ((BaseActivity) context).startActivity(SystemTestActivity.class);
                break;
            case R.id.btn_goto_signal:
                // 进入签名界面
                ((BaseActivity) context).startActivity(ReadPdfActivity.class);
//                startActivity(PDFActivity.class);
                break;
            case R.id.btn_goto_x_utils:
                // 进入xUtils测试界面
                ((BaseActivity) context).startActivity(XUtilsTestActivity.class);
                break;
            case R.id.btn_goto_xposed:
                // 进入Xposed测试界面
                ((BaseActivity) context).startActivity(XPosedActivity.class);
                break;
            case R.id.btn_goto_scan_code:
                // 测试扫描二维码
                ((BaseActivity) context).startActivity(CodeActivity.class);
                break;
            case R.id.btn_goto_gps:
                // GPS测试
                ((BaseActivity) context).startActivity(GPSActivity.class);
                break;
            case R.id.btn_goto_x_listview:
                // XListView测试
                ((BaseActivity) context).startActivity(XListViewActivity.class);
                break;
            case R.id.btn_goto_shell:
                // 测试Shell
                ((BaseActivity) context).startActivity(ShellTestActivity.class);
                break;
            case R.id.btn_install_apk:
                // 安装apk
                FileUtils.installApk(context, FileUtils.getFilePath("test.apk"));
                break;
            default:
                break;
        }
    }

}
