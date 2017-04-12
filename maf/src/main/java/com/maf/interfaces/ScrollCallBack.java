package com.maf.interfaces;

/**
 * 项目名称：maflibrary
 * 类描述：ScrollView滑动监c听
 * 创建人：mzg
 * 创建时间：2016/10/25 11:59
 * 修改人：mzg
 * 修改时间：2016/10/25 11:59
 * 修改备注：
 */
public interface ScrollCallBack {
    /**
     * 是否滑动到顶部
     *
     * @param isTop
     */
    void OnScrollTop(boolean isTop);

    /**
     *
     */
    void scrollBottomState();
}
