package com.maf.views;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.maf.R;


/**
 * 项目名称：BallInfoApplication
 * 类描述：
 * 创建人：mzg
 * 创建时间：2016/11/24 14:28
 * 修改人：mzg
 * 修改时间：2016/11/24 14:28
 * 修改备注：
 */

public class TitleBarView extends RelativeLayout implements View.OnClickListener {
    private ImageView imageBack;// 返回按钮
    private TextView textTitle;// 标题
    private ImageView imageMenu;// 菜单
    private ImageView imageMenuLeft;// 菜单左边的按钮，如果显示该按钮，则菜单按钮也会默认显示出来
    private TextView textTitleRight;// 右边文字

    public TitleBarView(Context context) {
        super(context);
    }

    public TitleBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 初始化布局
     */
    @Override
    protected void onFinishInflate() {
        imageBack = (ImageView) findViewById(R.id.image_title_back);
        textTitle = (TextView) findViewById(R.id.text_product_title);
        imageMenu = (ImageView) findViewById(R.id.image_title_menu);
        imageMenuLeft = (ImageView) findViewById(R.id.image_title_menu_left);
        textTitleRight = (TextView) findViewById(R.id.text_title_right);
        // 设置点击事件
        imageBack.setOnClickListener(this);
        super.onFinishInflate();
    }

    /**
     * 设置是否有返回键
     *
     * @param isShow 是否显示
     */
    public void setImageBackShow(boolean isShow) {
        if (!isShow) {
            imageBack.setVisibility(View.GONE);
        }
    }

    /**
     * 设置右边文字按钮信息（R.id.text_title_right）
     *
     * @param text            需要显示的文字
     * @param onClickListener 点击监听器
     */
    public void setTitleRight(String text, OnClickListener onClickListener) {
        textTitleRight.setVisibility(View.VISIBLE);
        textTitleRight.setText(text);
        textTitleRight.setOnClickListener(onClickListener);
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        textTitle.setText(title);
    }

    /**
     * 设置菜单点击监听器
     * R.id.image_title_menu
     *
     * @param onClickListener 监听器
     */
    public void setOnMenuClick(OnClickListener onClickListener) {
        imageMenu.setVisibility(VISIBLE);
        imageMenu.setOnClickListener(onClickListener);
    }

    /**
     * 设置菜单点击监听器，同时将换按钮图标
     * R.id.image_title_menu
     *
     * @param onMenuLeftClickListener 菜单按钮监听器
     * @param onMenuClickListener     菜单左边按钮监听器
     */
    public void setOnMenuLeftClick(OnClickListener onMenuLeftClickListener
            , OnClickListener onMenuClickListener) {
        imageMenu.setVisibility(VISIBLE);
        imageMenu.setOnClickListener(onMenuClickListener);

        imageMenuLeft.setVisibility(VISIBLE);
        imageMenuLeft.setOnClickListener(onMenuLeftClickListener);
    }

    /**
     * 设置菜单点击监听器，同时将换按钮图标
     * R.id.image_title_menu
     *
     * @param imageId         按钮图标
     * @param onClickListener
     */
    public void setOnMenuClick(int imageId, OnClickListener onClickListener) {
        imageMenu.setVisibility(VISIBLE);
        imageMenu.setImageResource(imageId);
        imageMenu.setOnClickListener(onClickListener);
    }

    /**
     * 设置菜单点击监听器，同时将换按钮图标
     * R.id.image_title_menu
     * R.id.image_title_menu_left
     *
     * @param imageLeftId             菜单左边的按钮图标
     * @param imageId                 菜单按钮图标
     * @param onMenuLeftClickListener 菜单按钮监听器
     * @param onMenuClickListener     菜单左边按钮监听器
     */
    public void setOnMenuLeftClick(int imageLeftId, int imageId, OnClickListener onMenuLeftClickListener
            , OnClickListener onMenuClickListener) {
        imageMenu.setVisibility(VISIBLE);
        if (imageId != 0) {
            imageMenu.setImageResource(imageId);
        }
        imageMenu.setOnClickListener(onMenuClickListener);

        imageMenuLeft.setVisibility(VISIBLE);
        if (imageLeftId != 0) {
            imageMenuLeft.setImageResource(imageLeftId);
        }
        imageMenuLeft.setOnClickListener(onMenuLeftClickListener);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.image_title_back) {
            // 返回键
            Context context = getContext();
            if (context instanceof Activity) {
                ((Activity) context).setResult(Activity.RESULT_OK);
                ((Activity) context).finish();
            }
        }
    }
}
