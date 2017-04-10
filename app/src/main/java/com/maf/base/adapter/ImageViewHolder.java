package com.maf.base.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.maf.adapter.BaseRecycleViewHolder;

import maf.com.mafproject.R;

/**
 * 项目名称：maflibrary
 * 类描述：加载单个图片的ViewHolder
 * 创建人：zgmao
 * 创建时间：2017/4/10
 * 修改人：zgmao
 * 修改时间：2017/4/10
 * 修改备注：
 * Created by zgmao on 2017/4/10.
 */
public class ImageViewHolder extends BaseRecycleViewHolder {

    ImageView imageView;
    TextView textView;

    public ImageViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void initView() {
        imageView = (ImageView) itemView.findViewById(R.id.imageView);
        textView = (TextView) itemView.findViewById(R.id.textView);
    }
}
