package com.maf.adapter;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.maf.R;
import com.maf.interfaces.OnItemClickListener;
import com.maf.interfaces.OnItemDoubleClickListener;
import com.maf.interfaces.OnItemLongClickListener;

import java.util.Date;

/**
 * 项目名称：maflibrary
 * 类描述：使用RecycleView是的基本ViewHolder，只需要实现initView方法，在里面为自己的控件设置id即可
 * 创建人：mzg
 * 创建时间：2016/8/11 17:16
 * 修改人：mzg
 * 修改时间：2016/8/11 17:16
 * 修改备注：
 */
public abstract class BaseRecycleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener
{
    protected View itemView;

    private OnItemClickListener onItemClickListener;// 单击监听器
    private OnItemLongClickListener onItemLongClickListener;// 长按监听器
    private OnItemDoubleClickListener onItemDoubleClickListener; // 双击监听器
    // 消息发送
    private Handler handler;

    public BaseRecycleViewHolder(View itemView)
    {
        super(itemView);
        this.itemView = itemView;
        initView();
        handler = new Handler(new Handler.Callback()
        {
            @Override
            public boolean handleMessage(Message msg)
            {
                View v = (View) msg.obj;
                int position = getPosition();
                if (position < 0) {
                    return false;
                }
                switch (msg.what) {
                    case 0:
                        // 单击
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(v, position);
                        }
                        break;
                    case 1:
                        // 双击
                        if (onItemDoubleClickListener != null) {
                            onItemDoubleClickListener.onItemDoubleClick(v, position);
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    protected abstract void initView();

    /**
     * 设置可点击
     *
     * @param onItemClickListener 监听器
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener = onItemClickListener;
        itemView.setOnClickListener(this);
        itemView.setBackgroundResource(R.drawable.recycler_bg);
    }

    /**
     * 设置可点击
     *
     * @param onItemClickListener 监听器
     * @param hasBackground       是否显示默认的点击背景，如果传true，自己定义的背景将无效
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener, boolean hasBackground)
    {
        this.onItemClickListener = onItemClickListener;
        itemView.setOnClickListener(this);
        if (hasBackground) {
            itemView.setBackgroundResource(R.drawable.recycler_bg);
        }
    }

    /**
     * 设置长按监听器
     *
     * @param onItemLongClickListener 监听器
     */
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener)
    {
        this.onItemLongClickListener = onItemLongClickListener;
        itemView.setOnLongClickListener(this);
    }

    /**
     * 设置双击监听器
     *
     * @param onItemDoubleClickListener
     */
    public void setOnItemDoubleClickListener(OnItemDoubleClickListener onItemDoubleClickListener)
    {
        this.onItemDoubleClickListener = onItemDoubleClickListener;
        itemView.setOnClickListener(this);
    }

    private int count = 0;//点击次数
    private long firstClick = 0;//第一次点击时间
    private long secondClick = 0;//第二次点击时间
    private final int totalTime = 618;// 两次点击时间间隔，单位毫秒


    @Override
    public void onClick(View v)
    {
        if (this.onItemDoubleClickListener != null) {
            // 有设置双击事件
//            Logger.d("OnItemClick", "count = " + count);
            if (count == 0) {
                // 第一次点击
                count = 1;

                firstClick = new Date().getTime();
                // 一段时间后，响应单击事件
                Message message = Message.obtain();
                message.what = 0;
                message.obj = v;
                handler.sendMessageDelayed(message, totalTime);
            } else {
                // 第二次点击
                count = 0;

                secondClick = new Date().getTime();
                // 判断如果两次点击事件间隔小，响应双击事件
                if ((secondClick - firstClick) <= totalTime) {
                    // 移除响应单击事件的消息
                    handler.removeMessages(0);
                    Message message = Message.obtain();
                    message.what = 1;
                    message.obj = v;
                    handler.sendMessage(message);
                } else {
                    Message message = Message.obtain();
                    message.what = 0;
                    message.obj = v;
                    handler.sendMessage(message);
                }
            }
        } else {
            // 没有设置双击事件，直接响应单击事件
            if (this.onItemClickListener != null) {
                onItemClickListener.onItemClick(v, getPosition());
            }
        }
    }

    @Override
    public boolean onLongClick(View v)
    {
        if (this.onItemLongClickListener != null) {
            onItemLongClickListener.onItemLongClick(v, getPosition());
        }
        return true;
    }
}
