package com.maf.base.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

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
public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView mTextView;

    public MyViewHolder(View itemView) {
        super(itemView);
        mTextView = (TextView) itemView.findViewById(R.id.item_tv);
    }
}