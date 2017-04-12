package com.maf.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * 项目名称：Ytb
 * 类描述：fragment基类
 * 创建人：mzg
 * 创建时间：2016/3/30 16:36
 * 修改人：mzg
 * 修改时间：2016/3/30 16:36
 * 修改备注：
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener  {

    public View parentView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        parentView = inflater.inflate(getLayoutResId(), container, false);
        initView();
        initEvent();
        initValue();
        return parentView;
    }

    /**
     * 返回当前Activity布局文件的id
     *
     * @return
     */
    abstract protected int getLayoutResId();

    /**
     * 初始化界面控件方法
     */
    protected abstract void initView();

    /**
     * 初始化控件的事件
     */
    protected abstract void initEvent();

    /**
     * 初始化数据
     */
    protected abstract void initValue();

    @Override
    public abstract void onClick(View v);

    @Override
    public void onDestroyView() {
        if (parentView == null) {
            parentView = null;
        }
        super.onDestroyView();
    }
}
