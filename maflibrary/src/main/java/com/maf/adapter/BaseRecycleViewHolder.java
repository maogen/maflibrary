package com.maf.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 项目名称：maflibrary
 * 类描述：使用RecycleView是的基本ViewHolder，只需要实现initView方法，在里面为自己的控件设置id即可
 * 创建人：mzg
 * 创建时间：2016/8/11 17:16
 * 修改人：mzg
 * 修改时间：2016/8/11 17:16
 * 修改备注：
 */
public abstract class BaseRecycleViewHolder extends RecyclerView.ViewHolder {

    public BaseRecycleViewHolder(View itemView) {
        super(itemView);
        initView(itemView);
    }

    protected abstract void initView(View itemView);
}
