package com.maf.popupwindow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.maf.R;


/**
 * 项目名称：maflibrary
 * 类描述：款式操作菜单列表适配器
 * 创建人：mzg
 * 创建时间：2016/7/26 13:53
 * 修改人：mzg
 * 修改时间：2016/7/26 13:53
 * 修改备注：
 */
public class PopupSelectStyleAdapter extends BaseAdapter {
    private Context context;
    private int[] iconId;
    private String[] menus;

    public PopupSelectStyleAdapter(Context context, int[] iconId, String[] menus) {
        this.context = context;
        this.iconId = iconId;
        this.menus = menus;
    }


    @Override
    public int getCount() {
        return menus.length;
    }

    @Override
    public Object getItem(int position) {
        return menus[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_style_select_popup_item, null);

            viewHolder = new ViewHolder();
            viewHolder.imageIcon = (ImageView) convertView.findViewById(R.id.image_menu_icon);
            viewHolder.textMenu = (TextView) convertView.findViewById(R.id.text_menu_title);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (iconId == null) {
            viewHolder.imageIcon.setVisibility(View.GONE);
        } else {
            viewHolder.imageIcon.setImageResource(iconId[position]);
        }
        viewHolder.textMenu.setText(menus[position]);
        return convertView;
    }

    static class ViewHolder {
        ImageView imageIcon;
        TextView textMenu;
    }

//    /**
//     * 设置某一项菜单灰色
//     *
//     * @param position 位置
//     */
//    public void setUnableByPosition(int position) {
//        isAble[position] = false;
//    }
//
//    /**
//     * 是否可点击
//     *
//     * @param position 是否可点击
//     * @return
//     */
//    public boolean isAble(int position) {
//        return isAble[position];
//    }

    /**
     * 设置菜单
     *
     * @param position 位置
     * @param title    标题
     */
    public void setMenu(int position, String title) {
        menus[position] = title;
        notifyDataSetChanged();
    }
}
