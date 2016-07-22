package com.maf.sortlist;

import java.util.Comparator;

/**
 * 项目名称：maflibrary
 * 类描述：索引排序
 * 创建人：mzg
 * 创建时间：2016/7/22 15:07
 * 修改人：mzg
 * 修改时间：2016/7/22 15:07
 * 修改备注：
 */
public class SortPinyinComparator implements Comparator<SortModel> {
    public int compare(SortModel o1, SortModel o2) {
        //这里主要是用来对ListView里面的数据根据ABCDEFG...来排序
        if (o2.getSortLetters().equals("#")) {
            return -1;
        } else if (o1.getSortLetters().equals("#")) {
            return 1;
        } else {
            return o1.getSortLetters().compareTo(o2.getSortLetters());
        }
    }
}
