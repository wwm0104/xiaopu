package com.chinaxiaopu.xiaopuMobi.util;

import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by liuwei
 * date: 2016/11/3
 */
public class DateTimeUtil {

    public static final String SIMPLE_DATE_FORMAT_STRING = "yyyy/MM/dd";
    public final static String DATE_TIME_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
    public final static String DATE_TIME_Sec = "yyyy-MM-dd HH:mm:ss";
    public final static String DATE_TIME_SORT = "yyyyMMddHHmmss";
    public final static String DATE_TIME_YMD = "yyyy-MM-dd";
    public final static String DATE_TIME_YMD_PIONT = "yyyy.MM.dd";
    public final static String DATE_TIME_YMD_NOSYMBOL = "yyyyMMdd";
    public final static String DATE_TIME_YM = "yyyy-MM";
    public final static String DATE_TIME_Y = "yyyy";

    /**
     * 根据某个日期，返回本周最后一天
     *
     * @param date
     * @return Date 当周最后一天
     * @author liuwei
     */
    public static Date getCurrentWeekSaturday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);
        int index = cal.get(Calendar.DAY_OF_WEEK);
        if (index == 1) {
            index = 8;
        }
        cal.add(Calendar.DATE, -(index - 2));
        cal.add(Calendar.DATE, 6);
        return cal.getTime();
    }

    /**
     * 根据某个日期，返回本月最后一天
     *
     * @param date 任何一天
     * @return Date 当月最后一天
     */
    public static Date getMonthsLastDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    /**
     * 获取服务器的当天时间
     *
     * @return
     */
    public static String getOneDayStart(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);//设置日期格式
        return df.format(new Date());
    }

    /**
     * 修改日期为指定格式
     *
     * @return
     */
    public static String formatDateTime(String format,Date date) {
        SimpleDateFormat df = new SimpleDateFormat(format);//设置日期格式
        return df.format(date);
    }

    /**
     * 事件转换
     *
     * @param createTime
     * @return
     */
    public static String getShowTime(Date createTime) {
        String showTime = "";
        if (!StringUtils.isEmpty(createTime)) {
            long time = (new Date().getTime() - createTime.getTime()) / 1000;
            if (time > 0) {
                showTime = time + "秒前";
                if (time / 60 > 0) {
                    showTime = time / 60 + "分钟前";
                    if (time / 60 / 60 > 0) {
                        showTime = time / 60 / 60 + "小时前";
                        if (time / 60 / 60 / 24 > 0) {
                            showTime = time / 60 / 60 / 24 + "天前";
                        }
                    }
                }
            }else{
                showTime = "刚刚";
            }
        }
        return showTime;
    }

    public static String getShowTime2(Date createTime) {
        String showTime = "";
        if (!StringUtils.isEmpty(createTime)) {
            long time = (new Date().getTime() - createTime.getTime()) / 1000;
            if (time > 0) {
                showTime = time + "秒前";
                if (time / 60 > 0) {
                    showTime = time / 60 + "分钟前";
                    if (time / 60 / 60 > 0) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        String timeStr = format.format(new Date());
                        Date today = new Date();
                        try {
                            today = format.parse(timeStr);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if(createTime.getTime() - today.getTime() > 0){
                            showTime = new SimpleDateFormat("今天 HH:mm").format(createTime);

                        } else {
                            showTime = new SimpleDateFormat("MM-dd HH:mm").format(createTime);
                        }
                    }
                }
            }else{
                showTime = "刚刚";
            }
        }
        return showTime;
    }


    /**
     * 转换为时间（天,时:分:秒.毫秒）
     * @param timeMillis
     * @return
     */
    public static String formatDateTime(long timeMillis){
        long day = timeMillis/(24*60*60*1000);
        long hour = (timeMillis/(60*60*1000)-day*24);
        long min = ((timeMillis/(60*1000))-day*24*60-hour*60);
        long s = (timeMillis/1000-day*24*60*60-hour*60*60-min*60);
        long sss = (timeMillis-day*24*60*60*1000-hour*60*60*1000-min*60*1000-s*1000);
        return (day>0?day+",":"")+hour+":"+min+":"+s+"."+sss;
    }


    /**
     * 返回已添加指定时间间隔的日期
     *
     * @param interval
     *            表示要添加的时间间隔("y":年;"d":天;"m":月;如有必要可以自行增加)
     * @param number
     *            表示要添加的时间间隔的个数
     * @param date
     *            java.util.Date()
     * @param dateFormat
     *            返回的日期格式
     *
     * @return String 默认为yyyy-MM-dd HH:mm:ss.SSS格式的日期字串
     * @author pepsi.liao
     */
    public static String dateAdd(String interval, int number,
                                 java.util.Date date, String dateFormat) {
        String strTmp = "";
        if (dateFormat == null || "".equals(dateFormat)) {
            dateFormat = "yyyy-MM-dd HH:mm:ss sss";
        }
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        // 加若干年
        if (interval.equals("y")) {
            int currYear = gc.get(Calendar.YEAR);
            gc.set(Calendar.YEAR, currYear + number);
        }
        // 加若干月
        else if (interval.equals("m")) {
            int currMonth = gc.get(Calendar.MONTH);
            gc.set(Calendar.MONTH, currMonth + number);
        }
        // 加若干天
        else if (interval.equals("d")) {
            int currDay = gc.get(Calendar.DATE);
            gc.set(Calendar.DATE, currDay + number);
        }
        // 加若小时
        else if (interval.equals("h")) {
            int currDay = gc.get(Calendar.HOUR);
            gc.set(Calendar.HOUR, currDay + number);
        }
        // 加若分钟
        else if (interval.equals("mm")) {
            int currMinute = gc.get(Calendar.MINUTE);
            gc.set(Calendar.MINUTE, currMinute + number);
        }
        SimpleDateFormat bartDateFormat = new SimpleDateFormat(dateFormat);
        strTmp = bartDateFormat.format(gc.getTime());
        return strTmp;
    }

    // a integer to xx:xx:xx
    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }
}
