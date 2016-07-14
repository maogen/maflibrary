package com.maf.base.bean;

/**
 * 项目名称：Ytb_Android
 * 类描述：
 * 创建人：mzg
 * 创建时间：2016/7/13 11:01
 * 修改人：mzg
 * 修改时间：2016/7/13 11:01
 * 修改备注：
 */
public class BaseRequestBean<T> {
    private int state;
    private boolean isSucceeded;
    private T content;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isSucceeded() {
        return isSucceeded;
    }

    public void setIsSucceeded(boolean isSucceeded) {
        this.isSucceeded = isSucceeded;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
