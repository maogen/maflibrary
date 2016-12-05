package com.maf.popupwindow;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.maf.R;

/**
 * 项目名称：maflibrary
 * 类描述：基础列表行的popup
 * 创建人：mzg
 * 创建时间：2016/12/5 14:58
 * 修改人：mzg
 * 修改时间：2016/12/5 14:58
 * 修改备注：
 */

public class BaseListPopup extends BasePopup {
    private ListView listView;
    private PopupSelectStyleAdapter styleAdapter;


    /**
     * @param context
     */
    public BaseListPopup(Context context) {
        super(context);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected int getViewId() {
        return R.layout.layout_style_select_popup;
    }

    @Override
    protected void initView() {
        listView = (ListView) view.findViewById(R.id.list_select_style);
    }

    @Override
    protected void initValue() {
    }

    /**
     * * @param menus   菜单列表
     *
     * @param ids         菜单图片
     * @param onItemClick
     */
    public void setMenu(String[] menus, int[] ids, AdapterView.OnItemClickListener onItemClick) {
        styleAdapter = new PopupSelectStyleAdapter(context, ids, menus);
        listView.setAdapter(styleAdapter);
        listView.setOnItemClickListener(onItemClick);
    }

    @Override
    protected void initEvent() {

    }


    /**
     * 显示在某个按钮的下方
     *
     * @param v
     */
    public void showBottomByView(View v) {
        showAsDropDown(v, 0, 0);
    }
}
