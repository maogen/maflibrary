package com.maf.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;

import com.maf.R;
import com.maf.application.BaseApplication;
import com.maf.swipe.SwipeMenu;
import com.maf.swipe.SwipeMenuCreator;
import com.maf.swipe.SwipeMenuItem;
import com.maf.swipe.SwipeMenuListView;

/**
 * 项目名称：maflibrary
 * 类描述：自定义侧滑删除控件，设置侧滑按钮
 * 创建人：mzg
 * 创建时间：2016/7/22 14:14
 * 修改人：mzg
 * 修改时间：2016/7/22 14:14
 * 修改备注：
 */
public class CustomSwipeMenuListView extends SwipeMenuListView {
    public CustomSwipeMenuListView(Context context) {
        super(context);
    }

    public CustomSwipeMenuListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomSwipeMenuListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 侧滑菜单项
     */
    private SwipeMenuItem[] items = new SwipeMenuItem[2];
    /**
     * 侧滑菜单每项的背景色
     */
    private int[] itemBGColor = {R.color.green_swipe, R.color.red_swipe};
    /**
     * 侧滑菜单每项标题
     */
    private String[] itemTitle = {"修改", "撤销"};

    private final int MENU_WIDTH = 180;//侧滑菜单每项的宽度
    private final int MENU_TITLE_SIZE = 14;// 菜单字体大小

    /**
     * 设置侧滑菜单，默认只有删除和撤销两种删除按钮
     */
    public void setSwipeMenu() {
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                for (int i = 0; i < items.length; i++) {
                    SwipeMenuItem item = new SwipeMenuItem(
                            BaseApplication._application);
                    item.setBackground(new ColorDrawable(getResources().getColor(itemBGColor[i])));
                    item.setWidth(MENU_WIDTH);
                    item.setTitle(itemTitle[i]);
                    item.setTitleSize(MENU_TITLE_SIZE);
                    item.setTitleColor(Color.WHITE);
                    // add to menu
                    menu.addMenuItem(item);
                }
            }
        };
        setMenuCreator(creator);
    }

    /**
     * 自定义按钮
     *
     * @param title
     */
    public void setSwipeMenu(final String[] title) {
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                for (int i = 0; i < title.length; i++) {
                    SwipeMenuItem item = new SwipeMenuItem(
                            BaseApplication._application);
                    int color = itemBGColor[i];
                    if (title.length == 1) {
                        // 如果按钮只有一个，只显示红色背景
                        color = itemBGColor[1];
                    }
                    item.setBackground(new ColorDrawable(getResources().getColor(color)));
                    item.setWidth(MENU_WIDTH);
                    item.setTitle(title[i]);
                    item.setTitleSize(MENU_TITLE_SIZE);
                    item.setTitleColor(Color.WHITE);
                    // add to menu
                    menu.addMenuItem(item);
                }
            }
        };
        setMenuCreator(creator);
    }
}