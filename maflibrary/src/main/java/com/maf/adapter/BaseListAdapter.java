package com.maf.adapter;

import android.content.Context;

import java.util.List;

/**
 * 项目名称：Ytb_Android
 * 类描述：公用的ListView设配器
 * 创建人：mzg
 * 创建时间：2016/7/26 15:27
 * 修改人：mzg
 * 修改时间：2016/7/26 15:27
 * 修改备注：
 */
public abstract class BaseListAdapter<T> extends android.widget.BaseAdapter {
    protected List<T> list;
    protected Context context;

    public BaseListAdapter(Context context, List<T> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        }
        return list.size();

    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 更新列表
     *
     * @param list 新的列表
     */
    public void updateList(List<T> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
