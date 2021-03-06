package com.maf.base.adapter;

import android.content.Context;
import android.view.View;

import com.maf.adapter.BaseRecycleAdapter;
import com.maf.interfaces.OnItemClickListener;

import java.util.List;

import maf.com.mafproject.R;

/**
 * 项目名称：maflibrary
 * 类描述：
 * 创建人：mzg
 * 创建时间：2016/7/14 21:00
 * 修改人：mzg
 * 修改时间：2016/7/14 21:00
 * 修改备注：
 */
public class MyCollapsingAdapter extends BaseRecycleAdapter<String, MyViewHolder> {

    public MyCollapsingAdapter(Context context, List<String> list) {
        super(context, list);
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
        holder.mTextView.setText(list.get(position));
    }
}
