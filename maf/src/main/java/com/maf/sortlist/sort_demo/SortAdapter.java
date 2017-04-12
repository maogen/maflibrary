package com.maf.sortlist.sort_demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.maf.R;
import com.maf.adapter.BaseListAdapter;

import java.util.List;

/**
 * 项目名称：maflibrary
 * 类描述：列表索引
 * 创建人：mzg
 * 创建时间：2016/7/22 14:56
 * 修改人：mzg
 * 修改时间：2016/7/22 14:56
 * 修改备注：
 */
public class SortAdapter extends BaseListAdapter<SortModel> implements SectionIndexer {
    public SortAdapter(Context context, List<SortModel> list) {
        super(context, list);
    }


    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder;
        final SortModel mContent = list.get(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.layout_sort_item, null);
            viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
            viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        //根据position获取分类的首字母的char ascii值
        int section = getSectionForPosition(position);

        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            viewHolder.tvLetter.setVisibility(View.VISIBLE);
            viewHolder.tvLetter.setText(mContent.getSortLetters());
        } else {
            viewHolder.tvLetter.setVisibility(View.GONE);
        }

        viewHolder.tvTitle.setText(this.list.get(position).getName());

        return view;

    }


    final static class ViewHolder {
        TextView tvLetter;
        TextView tvTitle;
    }


    /**
     * 根据ListView的当前位置获取分类的首字母的char ascii值
     */
    public int getSectionForPosition(int position) {
        return list.get(position).getSortLetters().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }

        return -1;
    }


    @Override
    public Object[] getSections() {
        return null;
    }
}
