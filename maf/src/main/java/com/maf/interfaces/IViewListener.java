package com.maf.interfaces;

/**
 * 项目名称：maflibrary
 * 类描述：界面刷新接口
 * 创建人：zgmao
 * 创建时间：2017/5/4
 * 修改人：Administrator
 * 修改时间：2017/5/4
 * 修改备注：
 * Created by Administrator on 2017/5/4.
 */

public interface IViewListener<T> {
    /**
     * 界面刷新的回调
     *
     * @param t 界面参数
     */
    void updateView(T t);

    /**
     * 请求成功回调
     *
     * @param result
     */
    void onSuccess(String result);

    /**
     * 请求失败或者请求取消回调
     *
     * @param result
     */
    void onError(String result);

    /**
     * 请求结束回调
     */
    void onFinished();
}
