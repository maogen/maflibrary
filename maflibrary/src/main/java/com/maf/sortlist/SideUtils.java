package com.maf.sortlist;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.maf.application.BaseApplication;
import com.maf.utils.BaseToast;
import com.maf.utils.CharacterParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 项目名称：maflibrary
 * 类描述：初始化索引控件
 * 创建人：mzg
 * 创建时间：2016/7/22 15:16
 * 修改人：mzg
 * 修改时间：2016/7/22 15:16
 * 修改备注：
 */
public class SideUtils {
    private ListView listContent;
    private SideBar sideBar;
    private TextView dialog;
    private SortAdapter adapter;
    private String[] date;

    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<SortModel> SourceDateList;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private SortPinyinComparator pinyinComparator;

    public SideUtils(ListView listContent, SideBar sideBar, TextView dialog, String[] date) {
        this.listContent = listContent;
        this.sideBar = sideBar;
        this.dialog = dialog;
        this.date = date;
    }

    public void initSort() {
//实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new SortPinyinComparator();
        sideBar.setTextView(dialog);

        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    listContent.setSelection(position);
                }

            }
        });
        listContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //这里要利用adapter.getItem(position)来获取当前position所对应的对象
                BaseToast.makeTextShort(((SortModel) adapter.getItem(position)).getName());
            }
        });

        SourceDateList = filledData(date);

        // 根据a-z进行排序源数据
        Collections.sort(SourceDateList, pinyinComparator);
        adapter = new SortAdapter(BaseApplication._application, SourceDateList);
        listContent.setAdapter(adapter);
    }

    /**
     * 为ListView填充数据
     *
     * @param date
     * @return
     */
    private List<SortModel> filledData(String[] date) {
        List<SortModel> mSortList = new ArrayList<>();

        for (int i = 0; i < date.length; i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(date[i]);
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }
}
