package com.maf.base.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.maf.activity.BaseActivity;
import com.maf.application.BaseApplication;
import com.maf.sortlist.SideBar;
import com.maf.sortlist.sort_demo.SortAdapter;
import com.maf.sortlist.sort_demo.SortModel;
import com.maf.sortlist.sort_demo.SortPinyinComparator;
import com.maf.utils.BaseToast;
import com.maf.utils.CharacterParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import maf.com.mafproject.R;

/**
 * 项目名称：maflibrary
 * 类描述：
 * 创建人：mzg
 * 创建时间：2016/7/22 15:00
 * 修改人：mzg
 * 修改时间：2016/7/22 15:00
 * 修改备注：
 */
public class SortTestActivity extends BaseActivity {
    private ListView listContent;
    private SideBar sideBar;// 侧边栏
    private TextView dialog;// 显示侧边栏选中的对话框
    private SortAdapter adapter;// 排序ListView设配器
    private String[] datas;// 数据

    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<SortModel> SourceDateList;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private SortPinyinComparator pinyinComparator;

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_sort_list;
    }

    @Override
    protected void initView() {
        listContent = (ListView) findViewById(R.id.list_content);
        sideBar = (SideBar) findViewById(R.id.list_sidebar);
        dialog = (TextView) findViewById(R.id.dialog);
        datas = getResources().getStringArray(R.array.user_data);
        initSortView();
        listContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //这里要利用adapter.getItem(position)来获取当前position所对应的对象
                BaseToast.makeTextShort("点击第" + position + "个元素");
            }
        });

    }

    private void initSortView() {
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

        SourceDateList = filledData(datas);

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

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initValue() {

    }

    @Override
    public void onClick(View v) {

    }
}
