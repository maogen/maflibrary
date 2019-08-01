package com.maf.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maf.interfaces.OnItemClickListener;
import com.maf.interfaces.OnItemDoubleClickListener;
import com.maf.interfaces.OnItemLongClickListener;

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
public abstract class BaseRecycleAdapter<T, VH extends BaseRecycleViewHolder> extends RecyclerView.Adapter<VH>
{
    protected Context context;
    protected List<T> list;
    protected VH viewHolder;

    private OnItemClickListener onItemClickListener;// 单击监听器
    private OnItemLongClickListener onItemLongClickListener;// 长按监听器
    private OnItemDoubleClickListener onItemDoubleClickListener;// 双击监听器

    private boolean hasBackground = true;

    public BaseRecycleAdapter(Context context, List<T> list)
    {
        this.context = context;
        this.list = list;
    }

    public BaseRecycleAdapter(Context context, List<T> list, boolean hasBackground)
    {
        this.context = context;
        this.list = list;
        this.hasBackground = hasBackground;
    }

    protected abstract int getResourceId();

    protected abstract VH getViewHolder(View view);

    /**
     * 设置可点击
     *
     * @param onItemClickListener 监听器
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 设置长按监听器
     *
     * @param onItemLongClickListener 监听器
     */
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener)
    {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    /**
     * 设置双击事件监听器
     *
     * @param onItemDoubleClickListener
     */
    public void setOnItemDoubleClickListener(OnItemDoubleClickListener onItemDoubleClickListener)
    {
        this.onItemDoubleClickListener = onItemDoubleClickListener;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(getResourceId(), parent, false);
        viewHolder = getViewHolder(view);
        // 设置单击监听
        if (onItemClickListener != null) {
            viewHolder.setOnItemClickListener(onItemClickListener);
        }
        // 设置长按监听
        if (onItemLongClickListener != null) {
            viewHolder.setOnItemLongClickListener(onItemLongClickListener);
        }
        // 设置双击监听
        if (onItemDoubleClickListener != null) {
            viewHolder.setOnItemDoubleClickListener(onItemDoubleClickListener);
        }
        return viewHolder;
    }


    @Override
    public int getItemCount()
    {
        if (list == null) {
            return 0;
        }
        return list.size();
    }
}
