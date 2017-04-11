package com.maf.base.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.maf.adapter.BaseRecycleViewHolder;

import maf.com.mafproject.R;

/**
 * 项目名称：maflibrary
 * 类描述：
 * 创建人：zgmao
 * 创建时间：2017/4/11
 * 修改人：zgmao
 * 修改时间：2017/4/11
 * 修改备注：
 * Created by zgmao on 2017/4/11.
 */
public class MovieViewHolder extends BaseRecycleViewHolder {

    ImageView imageCover;
    TextView textMovieName;
    TextView textMovieSummary;
    TextView textMovieType;

    public MovieViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void initView() {
        imageCover = (ImageView) itemView.findViewById(R.id.image_movie_cover);
        textMovieName = (TextView) itemView.findViewById(R.id.text_movie_name);
        textMovieSummary = (TextView) itemView.findViewById(R.id.text_movie_summary);
        textMovieType = (TextView) itemView.findViewById(R.id.text_movie_type);
    }
}
