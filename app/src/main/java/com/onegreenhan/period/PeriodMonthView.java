package com.onegreenhan.period;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.MonthView;

/**
 * 下标标记的日历控件
 * Created by huanghaibin on 2017/11/15.
 */

public class PeriodMonthView extends MonthView {
    private int mPadding;
    private int mH, mW;
    private Paint mPointPaint = new Paint();//标记画笔，目前用于标记排卵期
    private float mPointRadius;//额外标记-小圆点风格半径

    public PeriodMonthView(Context context) {
        super(context);
        mPadding = dipToPx(getContext(), 4);
        mH = dipToPx(getContext(), 2);
        mW = dipToPx(getContext(), 8);
        mPointPaint.setAntiAlias(true);
        mPointPaint.setStyle(Paint.Style.FILL);
        mPointPaint.setTextAlign(Paint.Align.CENTER);
        mPointPaint.setColor(Color.RED);
        mPointRadius = dipToPx(context, 2);
    }


    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme) {
        if (!"period".equals(calendar.getScheme())) {//如果不是经期就绘制选中效果
            mSelectedPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            canvas.drawRect(x + mPadding, y + mPadding, x + mItemWidth - mPadding, y + mItemHeight - mPadding, mSelectedPaint);
        }
        return true;
    }

    /**
     * onDrawSelected
     *
     * @param canvas   canvas
     * @param calendar 日历calendar
     * @param x        日历Card x起点坐标
     * @param y        日历Card y起点坐标
     */
    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y) {
        if ("period".equals(calendar.getScheme())) {//只有月经期才绘制
            canvas.drawRect(x + mPadding, y + mPadding, x + mItemWidth - mPadding, y + mItemHeight - mPadding, mSchemePaint);
            return;
        }
        if ("ovulation".equals(calendar.getScheme())) {//排卵期有特别标示
            mPointPaint.setColor(0xFFE4CFFB);
            canvas.drawRect(x + mItemWidth / 2 - mW / 2,
                    y + mItemHeight - mH * 2 - mPadding,
                    x + mItemWidth / 2 + mW / 2,
                    y + mItemHeight - mH - mPadding, mPointPaint);//底部横线表示
            //canvas.drawCircle(x + mItemWidth / 2, y + mItemHeight - 3 * mPadding, mPointRadius, mPointPaint);//底部小圆点表示
        }
    }

    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme,
                              boolean isSelected) {
        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;
        int top = y - mItemHeight / 6;
        //设置文本画笔颜色
        switch (calendar.getScheme()) {
            case "period": //月经期，所有文本颜色都是白色
                mSchemeTextPaint.setColor(0xFFFFFFFF);//标记的文本画笔
                mSchemeLunarTextPaint.setColor(0xFFFFFFFF);//标记农历文本画笔
                mCurMonthTextPaint.setColor(0xFFFFFFFF);//当前月份日期文本画笔
                mCurMonthLunarTextPaint.setColor(0xFFFFFFFF);//当前月份农历文本画笔
                mOtherMonthTextPaint.setColor(0xFFFFFFFF);//其它月份日期文本画笔
                mOtherMonthLunarTextPaint.setColor(0xFFFFFFFF);//其它月份农历文本画笔
                break;
            case "security"://安全期，所有的文本颜色都是绿色
                mCurMonthTextPaint.setColor(0xff8CD97C);
                mCurMonthLunarTextPaint.setColor(0xff8CD97C);
                mSchemeTextPaint.setColor(0xff8CD97C);
                mSchemeLunarTextPaint.setColor(0xff8CD97C);
                mOtherMonthTextPaint.setColor(0xff8CD97C);
                mOtherMonthLunarTextPaint.setColor(0xff8CD97C);
                break;
            case "yiyun"://易孕期
            case "ovulation"://排卵日(目前只设置了一天)，所有的文本颜色都是粉色
                mCurMonthTextPaint.setColor(0xffE4BBF8);
                mCurMonthLunarTextPaint.setColor(0xffE4BBF8);
                mSchemeTextPaint.setColor(0xffE4BBF8);
                mSchemeLunarTextPaint.setColor(0xffE4BBF8);
                mOtherMonthTextPaint.setColor(0xffE4BBF8);
                mOtherMonthLunarTextPaint.setColor(0xffE4BBF8);
                break;
            default://如果没有设置标识，那就是文本是黑色，农历是灰色
                mCurMonthTextPaint.setColor(0xff000000);
                mCurMonthLunarTextPaint.setColor(0xffACACAC);
                mSchemeTextPaint.setColor(0xff000000);
                mSchemeLunarTextPaint.setColor(0xffACACAC);
                mOtherMonthTextPaint.setColor(0xff000000);
                mOtherMonthLunarTextPaint.setColor(0xffACACAC);
                break;
        }
        //绘制文本
        if (isSelected) {//如果选中
            canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + top, mSelectTextPaint);//绘制当前日期,选中文本画笔
            canvas.drawText(calendar.isCurrentDay() ? "今天" : calendar.getLunar(), cx, mTextBaseLine + y + mItemHeight / 10, mSelectedLunarTextPaint);//绘制当前农历,选中农历画笔
        } else if (hasScheme) {//如果有标记
            canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + top, calendar.isCurrentMonth() ? mSchemeTextPaint : mOtherMonthTextPaint);//其他月份画笔
            canvas.drawText(calendar.isCurrentDay() ? "今天" : calendar.getLunar(), cx, mTextBaseLine + y + mItemHeight / 10,//标记农历画笔
                    mSchemeLunarTextPaint);
        } else {//默认
            canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + top,
                    calendar.isCurrentDay() ? mCurDayTextPaint : calendar.isCurrentMonth() ? mCurMonthTextPaint : mOtherMonthTextPaint);

            canvas.drawText(calendar.isCurrentDay() ? "今天" : calendar.getLunar(), cx, mTextBaseLine + y + mItemHeight / 10,
                    calendar.isCurrentDay() ? mCurDayLunarTextPaint :
                            calendar.isCurrentMonth() ? mCurMonthLunarTextPaint : mOtherMonthLunarTextPaint);
        }
    }


    /**
     * dp转px
     */
    private static int dipToPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
