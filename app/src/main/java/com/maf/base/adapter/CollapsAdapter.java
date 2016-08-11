package com.maf.base.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.maf.adapter.BaseRecycleAdapter;

import java.util.ArrayList;
import java.util.List;

import maf.com.mafproject.R;

/**
 * 项目名称：maflibrary
 * 类描述：
 * 创建人：mzg
 * 创建时间：2016/7/14 19:06
 * 修改人：mzg
 * 修改时间：2016/7/14 19:06
 * 修改备注：
 */
public class CollapsAdapter extends BaseRecycleAdapter<Integer, MyViewHolder> {
    private List<Integer> lists;

    public CollapsAdapter(Context context, List<Integer> list) {
        super(context, list);
        getRandomHeights(list);
    }

    private void getRandomHeights(List<Integer> datas) {
        lists = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            lists.add((int) (200 + Math.random() * 400));
        }
    }

    @Override
    protected int getResourceId() {
        return R.layout.item_list;
    }

    @Override
    protected MyViewHolder getViewHolder(View view) {
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        params.height = lists.get(position);//把随机的高度赋予item布局
        holder.itemView.setLayoutParams(params);
        holder.mTextView.setText(position + "");
    }

}

