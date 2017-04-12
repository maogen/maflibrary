package com.maf.sortlist.sort_demo;

/**
 * 项目名称：maflibrary
 * 类描述：带索引的模型
 * 创建人：mzg
 * 创建时间：2016/7/22 14:50
 * 修改人：mzg
 * 修改时间：2016/7/22 14:50
 * 修改备注：
 */
public class SortModel {
    private String name;   //显示的数据
    private String sortLetters;  //显示数据拼音的首字母

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }
}
