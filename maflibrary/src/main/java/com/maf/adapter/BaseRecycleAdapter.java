package com.maf.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 项目名称：maflibrary
 * 类描述：使用RecycleView时用到的基本Adapter
 * 1：需要返回布局，
 * 2：初始化BaseRecycleViewHolder：viewHolder = new BaseRecycleViewHolder(view);
 * 3：重新onBindViewHolder，为ViewHolder的每个控件设置内容
 * 创建人：mzg
 * 创建时间：2016/8/11 17:08
 * 修改人：mzg
 * 修改时间：2016/8/11 17:08
 * 修改备注：
 */
public abstract class BaseRecycleAdapter<T, VH extends BaseRecycleViewHolder> extends RecyclerView.Adapter<VH> {
    protected Context context;
    protected List<T> list;

    public BaseRecycleAdapter(Context context, List<T> list) {
        this.context = context;
        this.list = list;
    }

    protected abstract int getResourceId();

    protected abstract VH getViewHolder(View view);

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(getResourceId(), parent, false);
        return getViewHolder(view);
    }


    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }
}
