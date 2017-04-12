package com.maf.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.maf.R;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * 项目名称：maflibrary
 * 类描述：自定义时间选择
 * 创建人：mzg
 * 创建时间：2017/1/6 15:14
 * 修改人：mzg
 * 修改时间：2017/1/6 15:14
 * 修改备注：
 */

public class CustomDatePicker extends LinearLayout {
    /**
     * 滑动控件
     */
    private ScrollerNumberPicker yearPicker;
    private ScrollerNumberPicker monthPicker;
    private ScrollerNumberPicker dayPicker;
    /**
     * 选择监听
     */
    private OnSelectingListener onSelectingListener;
    private OnSelectChangeListener onSelectChangeListener;
    /**
     * 刷新界面
     */
    private static final int REFRESH_VIEW = 0x001;
    public static final int YEAROUTOFINDEX = 0x004;
    public static final int MONTHOUTOFINDEX = 0x002;
    public static final int DAYOUTOFINDEX = 0x003;

    public ScrollerNumberPicker getYearPicker() {
        return yearPicker;
    }

    public void setYearPicker(ScrollerNumberPicker yearPicker) {
        this.yearPicker = yearPicker;
    }

    public int getSelectYear() {
        return selectYear;
    }

    public void setSelectYear(int selectYear) {
        this.selectYear = selectYear;
    }

    public int getSelectMonth() {
        return selectMonth;
    }

    public void setSelectMonth(int selectMonth) {
        this.selectMonth = selectMonth;
    }

    public int getSelectDay() {
        return selectDay;
    }

    public void setSelectDay(int selectDay) {
        this.selectDay = selectDay;
    }

    private Context context;

    //选中的年月日
    private int selectYear = 1;
    private int selectMonth = 1;
    private int selectDay = 1;


    private Toast toast;

    private Calendar calendar;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what) {
                case REFRESH_VIEW:
                    if (onSelectChangeListener != null) {
                        onSelectChangeListener.changed(selectYear, selectMonth, selectDay);
                    }
                    if (onSelectingListener != null)
                        onSelectingListener.selected(true);
                    break;
//                case MONTHOUTOFINDEX:
//                    if (toast != null) {
//                        toast.cancel();
//                        toast.setText("最低月份:" + minMonth + "当前id:" + msg.arg1);
//                        toast.show();
//                    }
//                    selectMonth = minMonth;
//                    monthPicker.setDefault(selectMonth - 1);
//
//                    if (selectDay < minDay) {
//                        toast.setText("选择日期小于最小日期");
//                        toast.show();
//                        selectDay = minDay;
//                        dayPicker.setDefault(selectDay - 1);
//                    } else {
//                        toast.setText("选择日期不小于最小日期");
//                        toast.show();
//                    }
//
//                    if (onSelectChangeListener != null) {
//                        onSelectChangeListener.changed(selectYear, selectMonth, selectDay);
//                    }
//                    if (onSelectingListener != null)
//                        onSelectingListener.selected(true);
//                    break;
//                case DAYOUTOFINDEX:
//                    if (toast != null) {
//                        toast.cancel();
//                        toast.setText("最低日期:" + minDay + "当前id:" + msg.arg1);
//                        toast.show();
//                    }
//                    selectDay = minDay;
//                    dayPicker.setDefault(minDay - 1);
//
//                    if (onSelectChangeListener != null) {
//                        onSelectChangeListener.changed(selectYear, selectMonth, selectDay);
//                    }
//                    if (onSelectingListener != null)
//                        onSelectingListener.selected(true);
//                    break;
                default:
                    break;
            }
        }

    };

    public CustomDatePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        // TODO Auto-generated constructor stub
        initSomeValue();
    }

    public CustomDatePicker(Context context) {
        super(context);
        this.context = context;
        initSomeValue();
    }

    /**
     * 初始化一些东西
     */
    private void initSomeValue() {
        calendar = Calendar.getInstance();
        selectYear = calendar.get(Calendar.YEAR);
        selectMonth = calendar.get(Calendar.MONTH) + 1;
        selectDay = calendar.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
        initEvent();
    }

    /**
     * 初始化view
     */
    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_custom_datepicker, this);
        // 获取控件引用
        yearPicker = (ScrollerNumberPicker) findViewById(R.id.snp_year);
        monthPicker = (ScrollerNumberPicker) findViewById(R.id.snp_month);
        dayPicker = (ScrollerNumberPicker) findViewById(R.id.snp_day);
    }

    private ArrayList<String> yearList;//年
    private ArrayList<String> monthList;//月
    private ArrayList<String> dayList;//日

    /**
     * 初始化事件
     */
    private void initEvent() {
        yearList = getYearList();
        yearPicker.setData(yearList);
        yearPicker.setDefault(selectYear - START_YEAR);
        monthList = getMonthList();
        monthPicker.setData(monthList);
        monthPicker.setDefault(selectMonth - 1);
        dayList = getDayList(getOneMonthDays(selectYear, selectMonth));
        dayPicker.setData(dayList);
        dayPicker.setDefault(selectDay - 1);

        yearPicker.setOnSelectListener(new ScrollerNumberPicker.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
                if (text.equals("") || text == null)
                    return;
                selectYear = START_YEAR + id;
                if (isDateRange) {
                    if (!isDateEffective()) {
                        yearPicker.setData(getYearList());
                        if (isDatelittleThanMinDate()) {
                            resetDateToMin();
                        } else {
                            resetDateToMax();
                        }
                    }
                }

                int theMonthDays = getOneMonthDays(selectYear, selectMonth);
                dayPicker.setData(getDayList(theMonthDays));
                if (selectDay > theMonthDays) {
                    selectDay = theMonthDays;
                }
                dayPicker.setDefault(selectDay - 1);
                Message message = Message.obtain();
                message.what = REFRESH_VIEW;
                handler.sendMessage(message);
            }

            @Override
            public void selecting(int id, String text) {

            }
        });

        monthPicker.setOnSelectListener(new ScrollerNumberPicker.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
                if (text.equals("") || text == null)
                    return;
                selectMonth = id + 1;
                if (isDateRange) {
                    if (!isDateEffective()) {
                        monthPicker.setData(getMonthList());
                        if (isDatelittleThanMinDate()) {
                            resetDateToMin();
                        } else {
                            resetDateToMax();
                        }
                    }
                }

                int theMonthDays = getOneMonthDays(selectYear, selectMonth);
                dayPicker.setData(getDayList(theMonthDays));
                if (selectDay > theMonthDays) {
                    selectDay = theMonthDays;
                }
                dayPicker.setDefault(selectDay - 1);
                Message message = Message.obtain();
                message.what = REFRESH_VIEW;
                handler.sendMessage(message);

            }

            @Override
            public void selecting(int id, String text) {
                // TODO Auto-generated method stub
            }
        });

        dayPicker.setOnSelectListener(new ScrollerNumberPicker.OnSelectListener() {

            @Override
            public void endSelect(int id, String text) {
                if (text.equals("") || text == null)
                    return;
                selectDay = id + 1;
                if (isDateRange) {
                    if (!isDateEffective()) {
                        dayPicker.setData(getDayList(getOneMonthDays(selectYear, selectMonth)));
                        if (isDatelittleThanMinDate()) {
                            resetDateToMin();
                        } else {
                            resetDateToMax();
                        }
                    }
                }

                Message message = Message.obtain();
                message.what = REFRESH_VIEW;
                handler.sendMessage(message);
            }

            @Override
            public void selecting(int id, String text) {
                // TODO Auto-generated method stub
            }
        });
    }

    /**
     * @param year  年份
     * @param month 月份
     * @param day   日期
     */
    public void setDefaultDate(int year, int month, int day) {
        this.selectYear = year;
        this.selectMonth = month;
        this.selectDay = day;
        int tempSelectYearPosition = 0;
        int tempSelectMonthPosition = 0;
        int tempSelectDayPosition = 0;
        tempSelectYearPosition = year - START_YEAR;
        tempSelectMonthPosition = month - 1;
        tempSelectDayPosition = day - 1;
        dayList = getDayList(getOneMonthDays(year, month));

        //对一些非法数据做出判断
        if (tempSelectYearPosition < 0 || tempSelectYearPosition > yearList.size() - 1 || tempSelectMonthPosition < 0 || tempSelectMonthPosition > monthList.size() - 1 || tempSelectDayPosition < 0 || tempSelectDayPosition > dayList.size() - 1) {
            Calendar todayCalendar = Calendar.getInstance();
            this.selectYear = todayCalendar.get(Calendar.YEAR);
            this.selectMonth = todayCalendar.get(Calendar.MONTH) + 1;
            this.selectDay = todayCalendar.get(Calendar.DAY_OF_MONTH);
            tempSelectYearPosition = this.selectYear - START_YEAR;
            tempSelectMonthPosition = this.selectMonth - 1;
            tempSelectDayPosition = this.selectDay - 1;
        }
        yearPicker.setDefault(tempSelectYearPosition);
        monthPicker.setDefault(tempSelectMonthPosition);
        dayPicker.setData(dayList);
        dayPicker.setDefault(tempSelectDayPosition);

    }

    public void setOnSelectingListener(OnSelectingListener onSelectingListener) {
        this.onSelectingListener = onSelectingListener;
    }

    public void setOnSelectChangeListener(OnSelectChangeListener onSelectChangeListener) {
        this.onSelectChangeListener = onSelectChangeListener;
    }

    public interface OnSelectingListener {

        public void selected(boolean selected);
    }

    public interface OnSelectChangeListener {

        public void changed(int year, int month, int day);
    }

    //开始年份 和 结束年份
    public static final int START_YEAR = 1800;
    public static final int END_YEAR = 2200;

    //拿到年份
    private ArrayList<String> getYearList() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = START_YEAR; i <= END_YEAR; i++) {
            list.add(i + "");
        }
        return list;
    }

    //拿到月份
    private ArrayList<String> getMonthList() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i < 13; i++) {
            list.add(i + "月");
        }
        return list;
    }

    //拿到天数
    private ArrayList<String> getDayList(int maxday) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i <= maxday; i++) {
            list.add(i + "日");
        }
        return list;
    }

    /**
     * @param year  年份
     * @param month 月份
     * @return 返回该月的天数?
     */
    public int getOneMonthDays(int year, int month) {

        int[] ping = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int[] run = {0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (isRunYear(year)) {
            return run[month];
        } else {
            return ping[month];
        }
    }

    /**
     * @param year 年份
     * @return 返回该年份是不是闰年
     */
    public boolean isRunYear(int year) {
        if (year % 400 == 0 || year % 4 == 0 && year % 100 != 0) {
            return true;
        }
        return false;
    }

    //限制用户选择的时间
    private boolean isDateRange = false;
    private int minDay;
    private int minMonth;
    private int minYear;
    private int maxDay;
    private int maxMonth;
    private int maxYear;

    /**
     * Set the range of selectable dates.
     *
     * @param minDay   The day value of minimum date.
     * @param minMonth The month value of minimum date.
     * @param minYear  The year value of minimum date.
     * @param maxDay   The day value of maximum date.
     * @param maxMonth The month value of maximum date.
     * @param maxYear  The year value of maximum date.
     */
    public void setDateRange(int minDay, int minMonth, int minYear, int maxDay, int maxMonth, int maxYear) {
        isDateRange = true;
        if (minDay == 0 || minMonth == 0 || minYear == 0) {
            this.minDay = START_YEAR;
            this.minMonth = 1;
            this.minYear = 1;
        } else {
            this.minDay = minDay;
            this.minMonth = minMonth;
            this.minYear = minYear;
        }
        this.maxDay = maxDay;
        this.maxMonth = maxMonth;
        this.maxYear = maxYear;

        //如果比最小的时间小
        if (isDatelittleThanMinDate()) {
            setDefaultDate(minYear, minMonth, minDay);
        }

        //如果比最大的时间还大
        if (isDateBigThanMaxDate()) {
            setDefaultDate(maxYear, maxMonth, maxDay);
        }

    }

    /**
     * 判断选中的日期是否在有效的范围之内
     *
     * @return
     */
    public boolean isDateEffective() {
        if (isDateBigThanMaxDate() || isDatelittleThanMinDate()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断选中的日期是否大于最大日期
     *
     * @return
     */
    public boolean isDateBigThanMaxDate() {
        Calendar maxCalendar = Calendar.getInstance();
        maxCalendar.set(maxYear, maxMonth, maxDay);
        Calendar selectCalendar = Calendar.getInstance();
        selectCalendar.set(selectYear, selectMonth, selectDay);
        long selectTimes = selectCalendar.getTimeInMillis();
        long maxTimes = maxCalendar.getTimeInMillis();
        if (selectTimes > maxTimes) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断选中的日期是否比最小的日期还要小
     *
     * @return
     */
    public boolean isDatelittleThanMinDate() {
        Calendar minCalendar = Calendar.getInstance();
        minCalendar.set(minYear, minMonth, minDay);
        Calendar selectCalendar = Calendar.getInstance();
        selectCalendar.set(selectYear, selectMonth, selectDay);
        long selectTimes = selectCalendar.getTimeInMillis();
        long minTimes = minCalendar.getTimeInMillis();
        if (selectTimes < minTimes) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 重置为最小时间
     */
    private void resetDateToMin() {
        selectYear = minYear;
        selectMonth = minMonth;
        selectDay = minDay;
        int defaultPosotion = minYear - START_YEAR > 0 ? minYear - START_YEAR : 0;
        yearPicker.setDefault(defaultPosotion);
        monthPicker.setDefault(selectMonth - 1);
        dayPicker.setDefault(selectDay - 1);
    }

    /**
     * 重置为最大时间
     */
    private void resetDateToMax() {
        selectYear = maxYear;
        selectMonth = maxMonth;
        selectDay = maxDay;
        int defaultPosotion = maxYear - START_YEAR > 0 ? maxYear - START_YEAR : 0;
        yearPicker.setDefault(defaultPosotion);
        monthPicker.setDefault(selectMonth - 1);
        dayPicker.setDefault(selectDay - 1);
    }
}
