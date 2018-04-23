package com.maf.base.adapter;

import android.content.Context;
import android.view.View;

import com.maf.adapter.BaseRecycleAdapter;

import java.util.List;

import maf.com.mafproject.R;

/**
 * 项目名称：maflibrary
 * 类描述：
 * 创建人：zgmao
 * 创建时间：2018/4/23
 */
public class GroupSendAdapter extends BaseRecycleAdapter<String, GroupSendViewHolder>
{
    public GroupSendAdapter(Context context, List<String> list, boolean hasBackground)
    {
        super(context, list, hasBackground);
    }

    @Override
    protected int getResourceId()
    {
        return R.layout.item_wx_group_send;
    }

    @Override
    protected GroupSendViewHolder getViewHolder(View view)
    {
        return new GroupSendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GroupSendViewHolder holder, int position)
    {

    }
}
