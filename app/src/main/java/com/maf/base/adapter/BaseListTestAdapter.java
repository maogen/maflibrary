package com.maf.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maf.adapter.BaseListAdapter;

import java.util.List;

import maf.com.mafproject.R;

/**
 * 项目名称：maflibrary
 * 类描述：
 * 创建人：zgmao
 * 创建时间：2017/7/3
 * 修改人：zgmao
 * 修改时间：2017/7/3
 * 修改备注：
 * Created by zgmao on 2017/7/3.
 */

public class BaseListTestAdapter extends BaseListAdapter<String> {
    public BaseListTestAdapter(Context context, List<String> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list, null);

            viewHolder = new ViewHolder();
            viewHolder.textContent = (TextView) convertView.findViewById(R.id.item_tv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textContent.setText(list.get(position));
        return convertView;
    }

    class ViewHolder {
        TextView textContent;
    }
}
