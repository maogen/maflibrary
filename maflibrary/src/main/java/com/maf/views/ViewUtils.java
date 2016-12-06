package com.maf.views;

import android.app.Activity;
import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.maf.R;
import com.maf.application.BaseApplication;
import com.maf.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 项目名称：Ytb_Android
 * 类描述：处理VIew的工具类，隐藏输入法等
 * 创建人：mzg
 * 创建时间：2016/7/26 11:45
 * 修改人：mzg
 * 修改时间：2016/7/26 11:45
 * 修改备注：
 */
public class ViewUtils {

    /**
     * 设置编辑框是否可编辑
     *
     * @param editText 编辑框
     * @param able     是否可编辑
     */
    public static void setEditAble(EditText editText, boolean able) {
        editText.setEnabled(able);
        editText.setFocusableInTouchMode(able);
        editText.setFocusable(able);
        if (able) {
            editText.requestFocus();
            String content = editText.getText().toString();
            if (StringUtils.isNotEmpty(content)) {
                editText.setSelection(content.length());
            }
        }
    }


    /**
     * 显示输入法
     *
     * @param editText
     */
    public static void showInputMethodManager(final EditText editText) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
                           public void run() {
                               InputMethodManager inputManager =
                                       (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                               inputManager.showSoftInput(editText, 0);
                           }
                       },
                200);
    }

    /**
     * 隐藏输入法
     *
     * @param activity
     */
    public static void hideInputMethodManager(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View v = activity.getCurrentFocus();
        if (imm != null && v != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    /**
     * 判断点击是否是指定的VIew
     *
     * @param view
     * @param ev
     * @return
     */
    public static boolean inRangeOfView(View view, MotionEvent ev) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        if (ev.getX() < x || ev.getX() > (x + view.getWidth()) || ev.getY() < y || ev.getY() > (y + view.getHeight())) {
            return false;
        }
        return true;
    }

    /**
     * 设置搜索内容匹配显示
     *
     * @param textView 显示文本的控件
     * @param title    原来的内容
     * @param content  搜索的内容
     */
    public static void setTextBySearch(TextView textView, String title, String content, int colorId) {
        if (StringUtils.isNotEmpty(content)) {
            ForegroundColorSpan redSpan = new ForegroundColorSpan(BaseApplication._application
                    .getResources().getColor(colorId));
            SpannableStringBuilder builder = new SpannableStringBuilder(title);
            int changeIndex = title.indexOf(content);
            if (changeIndex != -1) {
                builder.setSpan(redSpan, changeIndex,
                        changeIndex + content.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView.setText(builder);
            }
        }
    }

    /**
     * 设置重点颜色
     *
     * @param textView 显示文本的控件
     * @param title    原来的内容
     * @param content  需要设置的内容
     */
    public static void setTextByContent(TextView textView, String title, String content) {
        if (StringUtils.isNotEmpty(content)) {
            ForegroundColorSpan redSpan = new ForegroundColorSpan(BaseApplication._application
                    .getResources().getColor(R.color.black));
            SpannableStringBuilder builder = new SpannableStringBuilder(title);
            int changeIndex = title.indexOf(content);
            if (changeIndex != -1) {
                builder.setSpan(redSpan, changeIndex,
                        changeIndex + content.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView.setText(builder);
            }
        }
    }
}
