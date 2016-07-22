package com.maf.base.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.maf.activity.BaseActivity;
import com.maf.sortlist.SideBar;
import com.maf.sortlist.SideUtils;
import com.maf.utils.BaseToast;

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
    private SideBar sideBar;
    private TextView dialog;

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_sort_list;
    }

    @Override
    protected void initView() {
        listContent = (ListView) findViewById(R.id.list_content);
        sideBar = (SideBar) findViewById(R.id.list_sidebar);
        dialog = (TextView) findViewById(R.id.dialog);
        String[] datas = getResources().getStringArray(R.array.user_data);
        SideUtils sideUtils = new SideUtils(listContent, sideBar, dialog, datas);
        sideUtils.initSort();
        listContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //这里要利用adapter.getItem(position)来获取当前position所对应的对象
                BaseToast.makeTextShort("点击第" + position + "个元素");
            }
        });

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
