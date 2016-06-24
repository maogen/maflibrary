package com.maf.base.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.maf.activity.BaseBackActivity;

import java.util.ArrayList;

import maf.com.mafproject.R;

/**
 * Created by mzg on 2016/6/23.
 * 测试图标库组件
 */
public class ChartActivity extends BaseBackActivity {
    private Button btnPie;
    private Button btnBar;
    private Button btnLine;

    private PieChart pieChart;
    private BarChart barChart;
    private LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_chart);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        btnPie = (Button) findViewById(R.id.btn_pie);
        btnBar = (Button) findViewById(R.id.btn_bar);
        btnLine = (Button) findViewById(R.id.btn_line);

        pieChart = (PieChart) findViewById(R.id.spread_pie_chart);
        barChart = (BarChart) findViewById(R.id.barChart);
        lineChart = (LineChart) findViewById(R.id.lineChart);
    }

    @Override
    protected void initEvent() {
        btnPie.setOnClickListener(this);
        btnBar.setOnClickListener(this);
        btnLine.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pie:
                pieChart.setVisibility(View.VISIBLE);
                barChart.setVisibility(View.GONE);
                lineChart.setVisibility(View.GONE);
                initPie();
                break;
            case R.id.btn_bar:
                pieChart.setVisibility(View.GONE);
                barChart.setVisibility(View.VISIBLE);
                lineChart.setVisibility(View.GONE);
                initBar();
                break;
            case R.id.btn_line:
                pieChart.setVisibility(View.GONE);
                barChart.setVisibility(View.GONE);
                lineChart.setVisibility(View.VISIBLE);
                initLine();
                break;
        }
    }

    /**
     * 初始化饼状图
     */
    private void initPie() {
        pieChart.setDescription("测试案例");
//        pieChart.setHoleColor(R.color.trans);
        pieChart.setHoleRadius(60);//半径
        pieChart.setTransparentCircleRadius(40);// 半透明圈
        pieChart.setHoleRadius(45);// 实心圆

        pieChart.setDrawCenterText(true);  //饼状图中间可以添加文字
        pieChart.setDrawHoleEnabled(true);
//        pieChart.setRotationAngle(90); // 初始旋转角度

        pieChart.setRotationEnabled(true); // 可以手动旋转
        pieChart.setUsePercentValues(true);  //显示成百分比
        pieChart.setBackgroundColor(getResources().getColor(R.color.trans));
//        pieChart.setCenterText("中间的文字");  //饼状图中间的文字

        //设置数据
        PieData pieData = getPieData(6);
        pieChart.setData(pieData);

//        Legend mLegend = pieChart.getLegend();  //设置比例图
//        mLegend.setPosition(Legend.LegendPosition.LEFT_OF_CHART_CENTER);  //最左边显示
//        mLegend.setForm(Legend.LegendForm.SQUARE);  //设置比例图的形状，默认是方形 SQUARE
//        mLegend.setXEntrySpace(7f);
//        mLegend.setYEntrySpace(5f);


        pieChart.animateXY(1000, 1000);  //设置动画
        pieChart.invalidate();
    }

    private PieData getPieData(int count) {
        int[] yy = {12, 12, 18, 20, 28, 10};

        ArrayList<String> xValues = new ArrayList<>();  //xVals用来表示每个饼块上的内容
        for (int i = 0; i < count; i++) {
            xValues.add("PieChart" + (i + 1));  //饼块上显示成PieChart1, PieChart2, PieChart3, PieChart4，PieChart5，PieChart6
        }

        /*
        * 将一个饼形图分成六部分， 各个部分的数值比例为12:12:18:20:28:10
        * 所以 12代表的百分比就是12%
        * 在具体的实现过程中，这里是获取网络请求的list的数据
         */
        ArrayList<Entry> yValues = new ArrayList<>();  //yVals用来表示封装每个饼块的实际数据
        for (int i = 0; i < count; i++) {
            yValues.add(new Entry(yy[i], i));
        }

        //y轴的集合
        PieDataSet pieDataSet = new PieDataSet(yValues, "PieChart Revenue 2014");
        pieDataSet.setSliceSpace(0f); //设置个饼状图之间的距离

        // 饼图颜色
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(205, 205, 205));
        colors.add(Color.rgb(114, 188, 223));
        colors.add(Color.rgb(255, 123, 124));
        colors.add(Color.rgb(57, 135, 200));
        colors.add(Color.rgb(30, 20, 200));
        colors.add(Color.rgb(80, 60, 150));
        pieDataSet.setColors(colors);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = 5 * (metrics.densityDpi / 160f);
        pieDataSet.setSelectionShift(px); // 选中态多出的长度
        PieData pieData = new PieData(xValues, pieDataSet);

        return pieData;
    }

    /**
     * 初始化柱状图
     */
    private void initBar() {
        barChart.setDrawGridBackground(false);
        barChart.setDrawBorders(false);  //是否在折线图上添加边框
        barChart.setDescription("柱状图测试");// 数据描述
        barChart.setNoDataTextDescription("no data to display"); // 如果没有数据，显示
        barChart.setDrawGridBackground(false); // 是否显示表格颜色
        barChart.setGridBackgroundColor(Color.WHITE); // 表格的的颜色，在这里是是给颜色设置一个透明度
        barChart.setBackgroundColor(getResources().getColor(R.color.trans));

        barChart.setTouchEnabled(true); // 设置是否可以触摸
        barChart.setDragEnabled(false);// 是否可以拖拽
        barChart.setScaleEnabled(false);// 是否可以缩放
        barChart.setPinchZoom(false);//
        barChart.setDrawBarShadow(true);

        // 设置数据
        BarData mBarData = getBarData(6);
        barChart.setData(mBarData);

        Legend mLegend = barChart.getLegend(); // 设置比例图标示
        mLegend.setForm(Legend.LegendForm.CIRCLE);// 样式
        mLegend.setFormSize(6f);// 字体
        mLegend.setTextColor(Color.BLUE);// 颜色

        // X轴设定
        barChart.animateY(1000);
        barChart.invalidate();
    }

    private BarData getBarData(int count) {
        int[] yy = {80, 50, 60, 50, 80, 100};// 所占比例

        ArrayList<String> xValues = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            xValues.add(String.format("CharData%s", (i + 1)));
        }

        ArrayList<BarEntry> yValues = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            yValues.add(new BarEntry(yy[i], i));
        }

        // y轴的数据集合
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(205, 205, 205));
        colors.add(Color.rgb(114, 188, 223));
        colors.add(Color.rgb(255, 123, 124));
        colors.add(Color.rgb(57, 135, 200));
        colors.add(Color.rgb(30, 20, 200));
        colors.add(Color.rgb(80, 60, 150));
//        Color[] colors = {};

        BarDataSet barDataSet = new BarDataSet(yValues, "BarChart Test");
        barDataSet.setColor(Color.rgb(114, 188, 223));
        barDataSet.setColors(colors);
        ArrayList<IBarDataSet> barDataSets = new ArrayList<>();
        barDataSets.add(barDataSet); // add the datasets

        BarData barData = new BarData(xValues, barDataSets);

        return barData;
    }

    private void initLine() {
        /*
        * ====================1.初始化-自由配置===========================
        */
        // 是否在折线图上添加边框
        lineChart.setDrawGridBackground(false);
        lineChart.setDrawBorders(false);
        // 设置描述
        lineChart.setDescription("");
        //设置透明度
        lineChart.setAlpha(0.8f);
        //设置网格底下的那条线的颜色
        lineChart.setBorderColor(Color.rgb(213, 216, 214));
        //设置高亮显示
//        lineChart.setHighlightEnabled(true);
        //设置是否可以触摸，如为false，则不能拖动，缩放等
        lineChart.setTouchEnabled(true);
        //设置是否可以拖拽
        lineChart.setDragEnabled(false);
        //设置是否可以缩放
        lineChart.setScaleEnabled(false);
        //设置是否能扩大扩小
        lineChart.setPinchZoom(false);
        /*
        * ====================2. 布局点添加数据 - 自由布局 ===========================
        */
        // 折线图的点，点击战士的布局和数据
//        MyMarkView mv = new MyMarkView(this);
//        lineChart.setMarkerView(mv);
        // 加载数据
        LineData data = getLineData();
        lineChart.setData(data);
        /*
        * ====================3. x，y动画效果和刷新图表等 ===========================
        */
        //从X轴进入的动画
        lineChart.animateX(1000);
        lineChart.animateY(1000);   //从Y轴进入的动画
        lineChart.animateXY(1000, 1000);    //从XY轴一起进入的动画
        //设置最小的缩放
        lineChart.setScaleMinima(0.5f, 1f);
        Legend l = lineChart.getLegend();
        l.setForm(Legend.LegendForm.LINE);  //设置图最下面显示的类型
        l.setTextSize(15);
        l.setTextColor(Color.rgb(104, 241, 175));
        l.setFormSize(30f);
        // 刷新图表
        lineChart.invalidate();
    }

    private LineData getLineData() {
        String[] xx = {"2", "4", "6", "8", "10", "12", "14", "16", "18"};
        String[] yy = {"20", "80", "10", "60", "30", "70", "55", "22", "40"};

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < xx.length; i++) {
            xVals.add(xx[i]);
        }

        ArrayList<Entry> yVals = new ArrayList<Entry>();
        for (int i = 0; i < yy.length; i++) {
            yVals.add(new Entry(Float.parseFloat(yy[i]), i));
        }

        LineDataSet set1 = new LineDataSet(yVals, "LineChart Test");
        set1.setDrawCubic(true);  //设置曲线为圆滑的线
        set1.setCubicIntensity(0.2f);
        set1.setDrawFilled(false);  //设置包括的范围区域填充颜色
        set1.setDrawCircles(true);  //设置有圆点
        set1.setLineWidth(2f);    //设置线的宽度
        set1.setCircleSize(5f);   //设置小圆的大小
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setColor(Color.rgb(104, 241, 175));    //设置曲线的颜色

        return new LineData(xVals, set1);
    }
}
