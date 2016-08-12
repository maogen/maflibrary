package com.maf.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.maf.R;
import com.maf.application.BaseApplication;
import com.maf.interfaces.OnItemClickListener;
import com.maf.interfaces.OnItemLongClickListener;

/**
 * 项目名称：maflibrary
 * 类描述：使用RecycleView是的基本ViewHolder，只需要实现initView方法，在里面为自己的控件设置id即可
 * 创建人：mzg
 * 创建时间：2016/8/11 17:16
 * 修改人：mzg
 * 修改时间：2016/8/11 17:16
 * 修改备注：
 */
public abstract class BaseRecycleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    protected View itemView;

    private OnItemClickListener onItemClickListener;// 单击监听器
    private OnItemLongClickListener onItemLongClickListener;// 长按监听器

    public BaseRecycleViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        initView();
    }

    protected abstract void initView();

    /**
     * 设置可点击
     *
     * @param onItemClickListener 监听器
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        itemView.setOnClickListener(this);
        itemView.setBackgroundResource(R.drawable.recycler_bg);
    }

    /**
     * 设置长按监听器
     *
     * @param onItemLongClickListener 监听器
     */
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
        itemView.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (this.onItemClickListener != null) {
            onItemClickListener.onItemClick(v, getPosition());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (this.onItemLongClickListener != null) {
            onItemLongClickListener.onItemLongClick(v, getPosition());
        }
        return true;
    }
}
