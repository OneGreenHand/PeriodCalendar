package com.onegreenhan.period;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;

import java.util.HashMap;
import java.util.Map;

/**
 * @author luo
 * @package com.onegreenhan.period
 * @fileName PeriodActivity
 * @data on 2019/7/6 17:00
 */
public class PeriodActivity extends AppCompatActivity implements com.haibin.calendarview.CalendarView.OnCalendarSelectListener, com.haibin.calendarview.CalendarView.OnMonthChangeListener {
    private Toolbar toolbar;
    private TextView tvMonth;
    private CalendarView mCalendarView;

    public static void openActivity(Context context) {
        context.startActivity(new Intent(context, PeriodActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_period);
        findId();
        init();
        initData();
    }

    /**
     * 找id
     */
    private void findId() {
        toolbar = findViewById(R.id.toolbar);
        tvMonth = findViewById(R.id.tv_month);
        mCalendarView = findViewById(R.id.calendarView);
    }

    private void init() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mCalendarView.setOnCalendarSelectListener(this);
        mCalendarView.setOnMonthChangeListener(this);
    }

    private void initData() {
        int y = mCalendarView.getCurYear();//获取年
        int m = mCalendarView.getCurMonth();//获取月
        tvMonth.setText(y + "年" + m + "月");
        mCalendarView.setRange(m == 1 ? y - 1 : y, m == 1 ? 12 : m - 1, 1, m == 12 ? y + 1 : y, m == 12 ? 1 : m + 1, 31);//限制选择范围
        mCalendarView.scrollToCurrent();//滚动到今天
        setDatas(y, m);//填充数据,可自行计算或者后台返回，这里是固定数据
    }

    /**
     * @param year  年
     * @param month 月
     * @param day   日
     * @param type  标记的文本(本示例不显示，只用于区分经期类型)
     *              //@param color 标记的颜色(暂时不使用)
     * @return
     */
    private Calendar getSchemeCalendar(int year, int month, int day, String type) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        //     calendar.setSchemeColor(color);//优先级高于xml设置的颜色
        calendar.setScheme(type);//优先级高于xml设置的文字
        return calendar;
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {//超出日期选择范围
        Toast.makeText(this, "超出日期选择范围", Toast.LENGTH_SHORT).show();
    }

    /**
     * 后面可根据经期计算规则自由组装数据然后填充，或者叫后台直接返回，嘿嘿
     * 目前经期数据为固定显示：10号为排卵日，月经期为24-28，易孕为6-9和11-15，其他均为安全期
     * 这里暂时将标记类型作为不同经期判断处理： period为月经期 security为安全期 yiyun易孕期 ovulation排软日(只设置了一天)
     * 如果需要用到标记，可下载他的library修改，根据Calendar类的getScheme()方法进行修改
     *
     * @param y 年
     * @param m 月
     */
    private void setDatas(int y, int m) {
        Map<String, Calendar> map = new HashMap<>();
        map.put(getSchemeCalendar(y, m, 1, "security").toString(),
                getSchemeCalendar(y, m, 1, "security"));
        map.put(getSchemeCalendar(y, m, 2, "security").toString(),
                getSchemeCalendar(y, m, 2, "security"));
        map.put(getSchemeCalendar(y, m, 3, "security").toString(),
                getSchemeCalendar(y, m, 3, "security"));
        map.put(getSchemeCalendar(y, m, 4, "security").toString(),
                getSchemeCalendar(y, m, 4, "security"));
        map.put(getSchemeCalendar(y, m, 5, "security").toString(),
                getSchemeCalendar(y, m, 5, "security"));
        map.put(getSchemeCalendar(y, m, 6, "yiyun").toString(),
                getSchemeCalendar(y, m, 6, "yiyun"));
        map.put(getSchemeCalendar(y, m, 7, "yiyun").toString(),
                getSchemeCalendar(y, m, 7, "yiyun"));
        map.put(getSchemeCalendar(y, m, 8, "yiyun").toString(),
                getSchemeCalendar(y, m, 8, "yiyun"));
        map.put(getSchemeCalendar(y, m, 9, "yiyun").toString(),
                getSchemeCalendar(y, m, 9, "yiyun"));
        map.put(getSchemeCalendar(y, m, 10, "ovulation").toString(),
                getSchemeCalendar(y, m, 10, "ovulation"));
        map.put(getSchemeCalendar(y, m, 11, "yiyun").toString(),
                getSchemeCalendar(y, m, 11, "yiyun"));
        map.put(getSchemeCalendar(y, m, 12, "yiyun").toString(),
                getSchemeCalendar(y, m, 12, "yiyun"));
        map.put(getSchemeCalendar(y, m, 13, "yiyun").toString(),
                getSchemeCalendar(y, m, 13, "yiyun"));
        map.put(getSchemeCalendar(y, m, 14, "yiyun").toString(),
                getSchemeCalendar(y, m, 14, "yiyun"));
        map.put(getSchemeCalendar(y, m, 15, "yiyun").toString(),
                getSchemeCalendar(y, m, 15, "yiyun"));
        map.put(getSchemeCalendar(y, m, 16, "security").toString(),
                getSchemeCalendar(y, m, 16, "security"));
        map.put(getSchemeCalendar(y, m, 17, "security").toString(),
                getSchemeCalendar(y, m, 17, "security"));
        map.put(getSchemeCalendar(y, m, 18, "security").toString(),
                getSchemeCalendar(y, m, 18, "security"));
        map.put(getSchemeCalendar(y, m, 19, "security").toString(),
                getSchemeCalendar(y, m, 19, "security"));
        map.put(getSchemeCalendar(y, m, 20, "security").toString(),
                getSchemeCalendar(y, m, 20, "security"));
        map.put(getSchemeCalendar(y, m, 21, "security").toString(),
                getSchemeCalendar(y, m, 21, "security"));
        map.put(getSchemeCalendar(y, m, 22, "security").toString(),
                getSchemeCalendar(y, m, 22, "security"));
        map.put(getSchemeCalendar(y, m, 23, "security").toString(),
                getSchemeCalendar(y, m, 23, "security"));
        map.put(getSchemeCalendar(y, m, 24, "period").toString(),
                getSchemeCalendar(y, m, 24, "period"));
        map.put(getSchemeCalendar(y, m, 25, "period").toString(),
                getSchemeCalendar(y, m, 25, "period"));
        map.put(getSchemeCalendar(y, m, 26, "period").toString(),
                getSchemeCalendar(y, m, 26, "period"));
        map.put(getSchemeCalendar(y, m, 27, "period").toString(),
                getSchemeCalendar(y, m, 27, "period"));
        map.put(getSchemeCalendar(y, m, 28, "period").toString(),
                getSchemeCalendar(y, m, 28, "period"));
        switch (m) {
            case 2://二月
                if (y % 4 == 0 && y % 100 != 0 || y % 400 == 0) {//闰年
                    map.put(getSchemeCalendar(y, m, 29, "security").toString(),
                            getSchemeCalendar(y, m, 29, "security"));
                }
                break;
            case 1://有31天
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                map.put(getSchemeCalendar(y, m, 29, "security").toString(),
                        getSchemeCalendar(y, m, 29, "security"));
                map.put(getSchemeCalendar(y, m, 30, "security").toString(),
                        getSchemeCalendar(y, m, 30, "security"));
                map.put(getSchemeCalendar(y, m, 31, "security").toString(),
                        getSchemeCalendar(y, m, 31, "security"));
                break;
            default://只有30天
                map.put(getSchemeCalendar(y, m, 29, "security").toString(),
                        getSchemeCalendar(y, m, 29, "security"));
                map.put(getSchemeCalendar(y, m, 30, "security").toString(),
                        getSchemeCalendar(y, m, 30, "security"));
                break;
        }
        //此方法在巨大的数据量上不影响遍历性能，推荐使用
        mCalendarView.setSchemeDate(map);
    }

    int count = 0;

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {//选中事件
        if ("period".equals(calendar.getScheme())) {
            Toast.makeText(this, "当前为月经期", Toast.LENGTH_SHORT).show();
        } else if ("ovulation".equals(calendar.getScheme())) {
            Toast.makeText(this, "排卵期", Toast.LENGTH_SHORT).show();
        } else {//初次加载会走两次方法是因为，你设置了日期范围和滚动到当天
            if (count < 2)
                count++;
            else
                Toast.makeText(this, "选中：" + calendar.getDay(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMonthChange(int year, int month) {//月份改变事件
        tvMonth.setText(year + "年" + month + "月");
    }
}
