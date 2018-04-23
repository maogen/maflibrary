package com.maf.base.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;

import com.maf.base.adapter.GroupSendAdapter;

import java.util.List;

import maf.com.mafproject.R;

/**
 * 项目名称：maflibrary
 * 类描述：
 * 创建人：zgmao
 * 创建时间：2018/4/23
 */
public class GroupSendActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private List<String> list;
    private GroupSendAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_send_two);

//        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
//
//        list = new ArrayList<>();
//        list.add("XXX");
//        adapter = new GroupSendAdapter(getBaseContext(), list, false);
//        recyclerView.setAdapter(adapter);

        Window window = getWindow();
        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.parseColor("#373c3d"));
        }
    }
}
