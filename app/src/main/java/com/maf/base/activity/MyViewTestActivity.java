package com.maf.base.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.maf.activity.BaseTitleActivity;
import com.maf.dialog.CitySelectDialog;
import com.maf.utils.BaseToast;
import com.maf.utils.StringUtils;
import com.maf.views.MySearchBar;
import com.maf.views.SlideSwitch;

import maf.com.mafproject.R;

/**
 * 项目名称：maflibrary
 * 类描述：自定义控件测试
 * 创建人：mzg
 * 创建时间：2016/12/14 15:33
 * 修改人：mzg
 * 修改时间：2016/12/14 15:33
 * 修改备注：
 */

public class MyViewTestActivity extends BaseTitleActivity {
    // 搜索
    private MySearchBar searchBar;
    // 切换控件
    private SlideSwitch slideOne;
    private SlideSwitch slideTwo;
    private SlideSwitch slideThree;
    private SlideSwitch slideFour;
    // 选择城市
    private Button btnSelectDialog;
    private CitySelectDialog dialog;

    @Override
    protected void initTitleView() {
        titleBarView.setTitle("测试自定义控件");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_my_view_test;
    }

    @Override
    protected void initView() {
        searchBar = (MySearchBar) findViewById(R.id.search_bar);
        slideOne = (SlideSwitch) findViewById(R.id.switch_one);

        slideTwo = (SlideSwitch) findViewById(R.id.switch_two);
        slideTwo.setStatus(false);
        slideTwo.setNotSwitch();

        slideThree = (SlideSwitch) findViewById(R.id.switch_three);
        slideThree.setText("关", "开");
        slideFour = (SlideSwitch) findViewById(R.id.switch_four);
        slideFour.setText("关", "开");

        btnSelectDialog = (Button) findViewById(R.id.text_select_city);
    }

    @Override
    protected void initEvent() {
        slideOne.setOnSwitchChangedListener(new SlideSwitch.OnSwitchChangedListener() {
            @Override
            public void onSwitchChanged(SlideSwitch obj, int status) {
                if (status == SlideSwitch.SWITCH_OFF) {
                    BaseToast.makeTextShort("打开");
                } else {
                    BaseToast.makeTextShort("关闭");
                }
            }
        });
        btnSelectDialog.setOnClickListener(this);
        searchBar.setTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String search = charSequence.toString();
                if (StringUtils.isNotEmpty(search)) {
                    BaseToast.makeTextShort("搜索：" + search);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    protected void initValue() {
        dialog = new CitySelectDialog(this);
        dialog.setSelectCity("浙江省", "杭州市", "");
        dialog.setOnSelectListener(new CitySelectDialog.OnSelectListener() {
            @Override
            public void onCitySelect(String provinceName, String cityName, String districtName) {
                BaseToast.makeTextShort(provinceName + ";" + cityName + ";" + districtName);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_select_city:
                // 选择城市
                dialog.show();
                break;
            default:
                break;
        }
    }
}
