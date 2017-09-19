package com.maf.base.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.AdapterView;
import android.widget.Button;

import com.google.gson.reflect.TypeToken;
import com.maf.activity.BaseTitleActivity;
import com.maf.application.BaseApplication;
import com.maf.base.bean.JsonTestBean;
import com.maf.base.logic.MainLogic;
import com.maf.git.GsonUtils;
import com.maf.popupwindow.BaseListPopup;
import com.maf.utils.BaseToast;
import com.maf.utils.DateUtils;
import com.maf.utils.Lg;
import com.maf.utils.RawUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import maf.com.mafproject.R;

/**
 * Created by mzg on 2016/5/23.
 * 开始界面，进入不同的测试界面
 */
public class MainActivity extends BaseTitleActivity
{
    private int[] btnIds = {R.id.btn_goto_toast, R.id.btn_goto_image,
            R.id.btn_goto_net, R.id.btn_goto_print,
            R.id.btn_goto_html, R.id.btn_goto_chart,
            R.id.btn_goto_collapsing, R.id.btn_goto_sort,
            R.id.btn_goto_code, R.id.btn_goto_hot_fix,
            R.id.btn_goto_main_text, R.id.btn_goto_load,
            R.id.btn_goto_slide, R.id.btn_goto_system,
            R.id.btn_goto_signal, R.id.btn_goto_x_utils,
            R.id.btn_goto_xposed, R.id.btn_goto_scan_code,
            R.id.btn_goto_gps, R.id.btn_goto_x_listview,
            R.id.btn_goto_shell};
    // 声明Button控件
    private Button[] btn = new Button[btnIds.length];

    private BaseListPopup listPopup;// 菜单

    private MainLogic mainLogic;

    @Override
    protected void initTitleView()
    {
        titleBarView.setTitle("首页");
        titleBarView.setOnMenuClick(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // 显示菜单
                BaseToast.makeTextShort("点击菜单");
                listPopup.showBottomByView(view);
            }
        });
    }

    @Override
    protected int getLayoutResId()
    {
        return R.layout.activity_main;
    }

    @Override
    protected void initView()
    {
        for (int i = 0; i < btnIds.length; i++) {
            btn[i] = (Button) findViewById(btnIds[i]);
            btn[i].setOnClickListener(this);
        }
        String[] menu = {"菜单1", "菜单2"};
        listPopup = new BaseListPopup(this);
        listPopup.setMenu(menu, null, new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long
                    l)
            {
                BaseToast.makeTextShort("点击第" + i + "个菜单");
            }
        });

        mainLogic = new MainLogic(this);
        mainLogic.setMenuPopup(listPopup);
    }

    @Override
    protected void initEvent()
    {
        getTelecomManager();
    }

    public void getTelecomManager()
    {
        int slotId = 1;
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                TelecomManager telecomManager = (TelecomManager) getSystemService(Context.TELECOM_SERVICE);
                List<PhoneAccountHandle> listHandle = telecomManager.getCallCapablePhoneAccounts();
                if (null != listHandle) {
                    PhoneAccountHandle phoneAccountHandle = listHandle.get(slotId);
                    Intent intent = new Intent();
                    intent.putExtra(TelecomManager.EXTRA_PHONE_ACCOUNT_HANDLE, phoneAccountHandle);
                }
            } catch (SecurityException se) {
                se.printStackTrace();
            }
        }

        try {
            Class<?> tManager = Class.forName("android.telecom.TelecomManager");
//            Class manager =  tManager.newInstance();
            Method callMethod = tManager.getClass().getDeclaredMethod("getCallCapablePhoneAccounts");
            callMethod.setAccessible(true);
            // getCallCapablePhoneAccounts返回的是List<PhoneAccountHandle>
            Object object = callMethod.invoke(tManager);
            Method getMethod = object.getClass().getDeclaredMethod("get");
            getMethod.setAccessible(true);
            // PhoneAccountHandle handler = list.get(i);
            Object listItem = getMethod.invoke(object, slotId);
            // 处理PhoneAccountHandle
            Intent intent = new Intent();
            intent.putExtra(TelecomManager.EXTRA_PHONE_ACCOUNT_HANDLE, (Parcelable) listItem);
//            listItem.getClass().getDeclaredMethod("");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (NoSuchMethodException ex) {
            ex.printStackTrace();
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void initValue()
    {
//        CrashHandler.getInstance().setStartContext(this);
        testDateUtils();
        testGsonUtils();
    }

    /**
     * 测试时间工具类
     */
    private void testDateUtils()
    {
        Lg.d("**********测试DateUtils***********");
        String dateString = DateUtils.getDateByDefault();
        Lg.d(dateString);
        String purposeDate = DateUtils.changeDateFormat(dateString, DateUtils
                .defaultDateFormat, "HH:mm:ss(yyyy/MM/dd)");
        Lg.d(purposeDate);
        Lg.d("**********END***********");
    }

    /**
     * 测试Gson工具类
     */
    private void testGsonUtils()
    {
        Lg.d("**********测试GsonUtils***********");
        String jsonString = RawUtils.getRawStr(BaseApplication._application, R.raw
                .jsontest);
        JsonTestBean bean = GsonUtils.stringToGson(jsonString, new
                TypeToken<JsonTestBean>()
                {
                });
        if (bean.getAge() == null) {
            Lg.d("Integer is null");
        } else {
            Lg.d(bean.getAge().toString());
        }
        Lg.d(GsonUtils.gsonToString(bean));
        Lg.d("**********END***********");
    }

    @Override
    public void onClick(View v)
    {
        mainLogic.onBtnClick(v);
    }
}
