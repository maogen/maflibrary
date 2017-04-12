package com.maf.views;

import android.content.Context;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maf.R;

/**
 * 项目名称：maflibrary
 * 类描述：搜索控件，不点击时，提示语居中。点击时，显示输入框
 * 创建人：mzg
 * 创建时间：2016/12/14 17:28
 * 修改人：mzg
 * 修改时间：2016/12/14 17:28
 * 修改备注：
 * 1：在布局申明控件
 * <com.maf.views.MySearchBar
      android:id="@+id/search_bar"
      android:layout_width="match_parent"
      android:layout_height="wrap_content" />
   2：监听搜索框输入内容
    searchBar.setTextChangedListener(new TextWatcher() {})
 */

public class MySearchBar extends LinearLayout {
    private Context mContext;
    private View parentView;
    private ImageView search_icon;
    private TextView search_hintText;
    private EditText search_editText;
    private InputMethodManager imm;
    private boolean default_flag;

    public MySearchBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    public MySearchBar(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public MySearchBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    private void initView() {
        imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        parentView = LayoutInflater.from(mContext).inflate(R.layout.layout_search_view, this);
        search_icon = (ImageView) parentView.findViewById(R.id.search_bar_icon);
        search_hintText = (TextView) parentView.findViewById(R.id.search_bar_text);
        search_editText = (EditText) parentView.findViewById(R.id.search_bar_editText);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSearch();
            }
        });
    }

    /**
     * 点击时间
     */
    public void onClickSearch() {
        if (default_flag) {
            search_editText.setText("");
            search_editText.setVisibility(GONE);
            search_hintText.setVisibility(VISIBLE);
            if (imm.isActive()) {
                //关闭软键盘
                imm.hideSoftInputFromWindow(search_editText.getWindowToken(), 0);
            }
            default_flag = false;
        } else {
            search_hintText.setVisibility(GONE);
            search_editText.setVisibility(VISIBLE);
            search_editText.setFocusable(true);
            search_editText.requestFocus();
            //打开软键盘
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            default_flag = true;
        }
    }

    /**
     * 显示搜索按钮
     */
    public void showIcon() {
        search_icon.setVisibility(VISIBLE);
    }

    /**
     * 隐藏搜索按钮
     */
    public void hideIcon() {
        search_icon.setVisibility(GONE);
    }

    /**
     * 设置文字监听事件
     *
     * @param textWatcher TextWatcher
     */
    public void setTextChangedListener(TextWatcher textWatcher) {
        search_editText.addTextChangedListener(textWatcher);
    }

    /**
     * 设置提示文字
     *
     * @param str 提示文字
     */
    public void setHintText(String str) {
        search_hintText.setText(str);
        search_editText.setHint(str);
    }
}
