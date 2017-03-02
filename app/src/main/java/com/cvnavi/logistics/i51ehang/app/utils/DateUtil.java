package com.cvnavi.logistics.i51ehang.app.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 版权所有 上海势航
 *
 * @author chenJun and johnnyYuan
 * @version 1.0.0
 * @description
 * @date 2016-5-17 下午1:23:36
 * @email yuanlunjie@163.com
 */
@SuppressLint("SimpleDateFormat")
public class DateUtil {

    /**
     * 英文简写如：2010
     */
    public static String FORMAT_Y = "yyyy";
    /**
     * 英文简写如：2016-08
     */
    public static String FORMAT_YM = "yyyy-MM";

    /**
     * 英文简写如：06
     */
    public static String FORMAT_M = "MM";
    /**
     * 英文简写如：25
     */
    public static String FORMAT_D = "dd";

    /**
     * 英文简写如：12:01
     */
    public static String FORMAT_HH = "HH";

    /**
     * 英文简写如：12:01
     */
    public static String FORMAT_mm = "mm";

    /**
     * 英文简写如：12:01
     */
    public static String FORMAT_HM = "HH:mm";

    /**
     * 英文简写如：1-12 12:01
     */
    public static String FORMAT_MDHM = "MM-dd HH:mm";

    public static String FORMAT_MD = "MM-dd";

    /**
     * 英文简写（默认）如：2010-12-01
     */
    public static String FORMAT_YMD = "yyyy-MM-dd";

    public static String FORMAT_YMD2 = "yyyy/MM/dd";

    public static String FORMAT_YMD_SN = "yyyyMMdd";

    /**
     * 英文全称 如：2010-12-01 23:15
     */
    public static String FORMAT_YMDHM = "yyyy-MM-dd HH:mm";

    /**
     * 英文全称 如：2010-12-01 23:15:06
     */
    public static String FORMAT_YMDHMS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 精确到毫秒的完整时间 如：yyyy-MM-dd HH:mm:ss.S
     */
    public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";

    /**
     * 精确到毫秒的完整时间 如：yyyy-MM-dd HH:mm:ss.S
     */
    public static String FORMAT_FULL_SN = "yyyyMMddHHmmssS";

    /**
     * 中文简写 如：2010年12月01日
     */
    public static String FORMAT_YMD_CN = "yyyy年MM月dd日";
    /**
     * 中文简写 如：2016年08月
     */
    public static String FORMAT_YM_CN = "yyyy年MM月";

    /**
     * 中文简写 如：2010年12月01日 12时
     */
    public static String FORMAT_YMDH_CN = "yyyy年MM月dd日 HH时";

    /**
     * 中文简写 如：2010年12月01日 12时12分
     */
    public static String FORMAT_YMDHM_CN = "yyyy年MM月dd日 HH时mm分";

    /**
     * 中文全称 如：2010年12月01日 23时15分06秒
     */
    public static String FORMAT_YMDHMS_CN = "yyyy年MM月dd日  HH时mm分ss秒";

    /**
     * 精确到毫秒的完整中文时间
     */
    public static String FORMAT_FULL_CN = "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒";

    public static Calendar calendar = null;
    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";


    public static Date str2Date(String str, String format) {
        if (str == null || str.length() == 0) {
            return null;
        }
        if (format == null || format.length() == 0) {
            format = FORMAT;
        }
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Calendar str2Calendar(String str, String format) {
        Date date = str2Date(str, format);
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }

    public static String date2Str(Calendar c) {// yyyy-MM-dd HH:mm:ss
        return date2Str(c, null);
    }

    public static String date2Str(Calendar c, String format) {
        if (c == null) {
            return null;
        }
        return date2Str(c.getTime(), format);
    }

    public static String date2Str(Date d) {// yyyy-MM-dd HH:mm:ss
        return date2Str(d, null);
    }

    public static String date2Str(Date d, String format) {// yyyy-MM-dd HH:mm:ss
        if (d == null) {
            return null;
        }
        if (format == null || format.length() == 0) {
            format = FORMAT;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String s = sdf.format(d);
        return s;
    }

    /**
     * 通用日期转换方法
     * 字符串日期(2016-5-25 15:06:30)转换为 5-25 15:06 特定：只适用于本系统
     *
     * @param str
     * @param oldFormat
     * @param newFormat
     * @return
     */
    public synchronized static String strOldFormat2NewFormat(String str, String oldFormat, String newFormat) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }

        Date date = str2Date(str, oldFormat);
        if (date == null) {
            return null;
        }
        return date2Str(date, newFormat);
    }

    public synchronized static String str2YMDStr(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }

        Date date = str2Date(str, FORMAT_YMD);
        if (date == null) {
            return null;
        }
        return date2Str(date, FORMAT_YMD);
    }

    public synchronized static String str2YMD_SNStr(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }

        Date date = str2Date(str, FORMAT_YMD);
        if (date == null) {
            return null;
        }
        return date2Str(date, FORMAT_YMD_SN);
    }

    public synchronized static String str2HMStr(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }

        Date date = str2Date(str, FORMAT_YMDHMS);
        if (date == null) {
            return null;
        }
        return date2Str(date, FORMAT_HM);
    }

    public static String getCurDateStr() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        return c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH) + " " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND);
    }

    public static String getCurDateStr2() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        return c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获得当前日期的字符串格式
     *
     * @param format 格式化的类型
     * @return 返回格式化之后的事件
     */
    public static String getCurDateStr(String format) {
        Calendar c = Calendar.getInstance();
        return date2Str(c, format);
    }

    /**
     * @param time 当前的时间
     * @return 格式到秒
     */
    public static String getTimeFormat(String format, long time) {
        return new SimpleDateFormat(format).format(time);
    }

    /**
     * 在日期上增加数个整月
     *
     * @param date 日期
     * @param n    要增加的月数
     * @return 增加数个整月或者天数
     * <p/>
     * fromat = Calendar.MONTH 或者 Calendar.DATE
     */
    public static Date addMonthOrDay(Date date, int n, int format) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, n);
        return cal.getTime();
    }


    /**
     * 获取距现在某一小时的时刻
     *
     * @param format 格式化时间的格式
     * @param h      距现在的小时 例如：h=-1为上一个小时，h=1为下一个小时
     * @return 获取距现在某一小时的时刻
     */
    public static String getNextHour(String format, int h) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = new Date();
        date.setTime(date.getTime() + h * 60 * 60 * 1000);
        return sdf.format(date);
    }

    /**
     * 获取时间戳
     *
     * @return 获取时间戳
     */
    public static String getTimeString() {
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_FULL);
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());
    }

    /**
     * 功能描述：返回月
     *
     * @param date Date 日期
     * @return 返回月份
     */
    public static int getMonth(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 功能描述：返回日
     *
     * @param date Date 日期
     * @return 返回日份
     */
    public static int getDay(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 功能描述：返回小
     *
     * @param date 日期
     * @return 返回小时
     */
    public static int getHour(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 功能描述：返回分
     *
     * @param date 日期
     * @return 返回分钟
     */
    public static int getMinute(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }


    /**
     * 获得默认的 date pattern
     *
     * @return 默认的格式
     */
    public static String getDatePattern() {
        return FORMAT_YMDHMS;
    }

    /**
     * 返回秒钟
     *
     * @param date Date 日期
     * @return 返回秒钟
     */
    public static int getSecond(Date date) {
        calendar = Calendar.getInstance();

        calendar.setTime(date);
        return calendar.get(Calendar.SECOND);
    }

    /**
     * 使用预设格式提取字符串日期
     *
     * @param strDate 日期字符串
     * @return 提取字符串的日期
     */
    public static Date parse(String strDate) {
        return parse(strDate, getDatePattern());
    }

    /**
     * 功能描述：返回毫
     *
     * @param date 日期
     * @return 返回毫
     */
    public static long getMillis(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

    /**
     * 按默认格式的字符串距离今天的天数
     *
     * @param date 日期字符串
     * @return 按默认格式的字符串距离今天的天数
     */
    public static int countDays(String date) {
        long t = Calendar.getInstance().getTime().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(parse(date));
        long t1 = c.getTime().getTime();
        return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
    }

    /**
     * 使用用户格式提取字符串日期
     *
     * @param strDate 日期字符串
     * @param pattern 日期格式
     * @return 提取字符串日期
     */
    public static Date parse(String strDate, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 按用户格式字符串距离今天的天数
     *
     * @param date   日期字符串
     * @param format 日期格式
     * @return 按用户格式字符串距离今天的天数
     */
    public static int countDays(String date, String format) {
        long t = Calendar.getInstance().getTime().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(parse(date, format));
        long t1 = c.getTime().getTime();
        return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
    }

    /**
     * 获取星期几。
     *
     * @param strDate
     * @return
     */
    public static String getWeek(String strDate) {
        // 再转换为时间
        Date date = strToDate(strDate);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // int hour=c.get(Calendar.DAY_OF_WEEK);
        // hour中存的就是星期几了，其范围 1~7
        // 1=星期日 7=星期六，其他类推
        return new SimpleDateFormat("EEEE").format(c.getTime());
    }

    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    public static Date strToDate2(String strDate, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }


    /**
     * 计算两个日期相隔天数。
     *
     * @param strBeginDate 开始日期。
     * @param strEndDate   结束日期。
     * @return
     */
    public static String calcDiffDays(String strBeginDate, String strEndDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);

        long diff = 0;
        try {
            Date beginDate = sdf.parse(strBeginDate);
            Date endDate = sdf.parse(strEndDate);

            diff = endDate.getTime() - beginDate.getTime();
        } catch (ParseException e1) {
            e1.printStackTrace();
        }

        return String.valueOf(diff / (1000 * 60 * 60 * 24));
    }

    public static String calcDiffDays2(String strBeginDate, String strEndDate, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        long diff = 0;

        try {
            Date beginDate = sdf.parse(strBeginDate);
            Date endDate = sdf.parse(strEndDate);
            diff = endDate.getTime() - beginDate.getTime();
        } catch (ParseException e1) {
            e1.printStackTrace();
        }

        return String.valueOf(diff / (1000 * 60 * 60 * 24));
    }


    /**
     * 时间段不能超过1天
     *
     * @param beging
     * @param end
     * @return
     */
    public static int compareDateOneDay(String beging, String end) {
        int flag = 0;
        if (TextUtils.isEmpty(beging) || TextUtils.isEmpty(end)) {
            flag = 0;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            GregorianCalendar cal1 = new GregorianCalendar();
            GregorianCalendar cal2 = new GregorianCalendar();
            cal1.setTime(simpleDateFormat.parse(beging));
            cal2.setTime(simpleDateFormat.parse(end));
            int result = (int) ((cal2.getTimeInMillis() - cal1.getTimeInMillis()) / (1000 * 3600 * 24));
            if (result <= 1 && result >= 0) {
                flag = 1;
            } else if (result > 1) {
                flag = 2;
            } else if (result < 0) {
                flag = 3;
            }
        } catch (ParseException e) {
        }
        return flag;
    }


    /**
     * 时间段不能超过3天
     *
     * @param beging
     * @param end
     * @return
     */
    public static int compareDateThreeDay(String beging, String end) {
        int flag = 0;
        if (TextUtils.isEmpty(beging) || TextUtils.isEmpty(end)) {
            flag = 0;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            GregorianCalendar cal1 = new GregorianCalendar();
            GregorianCalendar cal2 = new GregorianCalendar();
            cal1.setTime(simpleDateFormat.parse(beging));
            cal2.setTime(simpleDateFormat.parse(end));
            int result = (int) ((cal2.getTimeInMillis() - cal1.getTimeInMillis()) / (1000 * 3600 * 24));
            if (result <= 3 && result >= 0) {
                flag = 1;
            } else if (result > 3) {
                flag = 2;
            } else if (result < 0) {
                flag = 3;
            }
        } catch (ParseException e) {
        }
        return flag;
    }


    /**
     * flag ==2
     *
     * @param beging
     * @param end
     * @param format
     * @param day    相差几天
     * @return
     */
    public static int compareDateNDays(String beging, String end, String format, int day) {
        int flag = 0;
        if (TextUtils.isEmpty(beging) || TextUtils.isEmpty(end)) {
            flag = 0;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            GregorianCalendar cal1 = new GregorianCalendar();
            GregorianCalendar cal2 = new GregorianCalendar();
            cal1.setTime(simpleDateFormat.parse(beging));
            cal2.setTime(simpleDateFormat.parse(end));
            int result = (int) ((cal2.getTimeInMillis() - cal1.getTimeInMillis()) / (1000 * 3600 * 24));
            if (result <= day && result >= 0) {
                flag = 1;
            } else if (result > day) {
                flag = 2;
            } else if (result < day) {
                flag = 3;
            }
        } catch (ParseException e) {
        }
        return flag;
    }

    /**
     * 比较两个日期大小。
     *
     * @param strBeginDate 开始日期。
     * @param strEndDate   结束日期。
     * @return
     */
    public static boolean compareDate(String strBeginDate, String strEndDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        boolean flag = false;

        try {
            Date beginDate = sdf.parse(strBeginDate);
            Date endDate = sdf.parse(strEndDate);

            flag = endDate.before(beginDate);
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        return flag;
    }

    public static boolean compareDate2(String strBeginDate, String strEndDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        boolean flag = false;

        try {
            Date beginDate = sdf.parse(strBeginDate);
            Date endDate = sdf.parse(strEndDate);

            flag = endDate.before(beginDate);
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        return flag;
    }


    public static boolean compareData(String strBeginDate, String strEndDate, String dataFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dataFormat);
        boolean flag = false;

        try {
            Date beginDate = sdf.parse(strBeginDate);
            Date endDate = sdf.parse(strEndDate);

            flag = endDate.before(beginDate);
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        return flag;

    }

    /**
     * 获取最近几天日期
     *
     * @param num
     * @return
     */
    public static String getLastNDays(int num) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, num);

        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    }

    /**
     * 获取最近几天日期
     *往前的日期 num为负数
     * @param num
     * @return
     */
    public static String getLastNDaysForm(int num, String form) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, num);

        return new SimpleDateFormat(form).format(cal.getTime());
    }

    /**
     * 得到当N天时间
     *
     * @return
     */
    public static String getBeforeDayNsTime(int day) {
        Format f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -day);
        return f.format(c.getTime());
    }

    /**
     * 获取当前n天的是时间
     *
     * @param day
     * @param format
     * @return
     */
    public static String getBeforeDayNsTime(int day, String format) {
        Format f = new SimpleDateFormat(format);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -day);
        return f.format(c.getTime());
    }

    /**
     * 得到当前前3天时间
     *
     * @return
     */
    public static String getBeforeThreeDayTime() {
        Format f = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -3);
        return f.format(c.getTime());
    }

    /**
     * 得到当前前1天时间
     *
     * @return
     */
    public static String getBeforeOneDayTime() {
        Format f = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -1);
        return f.format(c.getTime());
    }

    /**
     * 计算从今天的零点时刻开始  （2016-7-22 00:00:00）
     *
     * @return
     */
    public static long getTodayZero() {
        Date date = new Date();
        long l = 24 * 60 * 60 * 1000; //每天的毫秒数
        //date.getTime()是现在的毫秒数，它 减去 当天零点到现在的毫秒数（ 现在的毫秒数%一天总的毫秒数，取余。），理论上等于零点的毫秒数，不过这个毫秒数是UTC+0时区的。
        //减8个小时的毫秒值是为了解决时区的问题。
        return (date.getTime() - (date.getTime() % l) - 8 * 60 * 60 * 1000);
    }

    public static String getTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = new Date(time);
        return format.format(d1);
    }


    /**
     * 字符串日期(2016-5-25)转换为 2016特定：只适用于本系统
     *
     * @param str
     * @return
     */
    public synchronized static String ToYear(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }

        Date date = str2Date(str, FORMAT_YMDHM);
        if (date == null) {
            return null;
        }
        return date2Str(date, FORMAT_Y);
    }

    /**
     * 字符串日期(2016-5-25 22:12)转换为 2016特定：只适用于本系统
     *
     * @param str
     * @return
     */
    public synchronized static String ToMonth(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }

        Date date = str2Date(str, FORMAT_YMDHM);
        if (date == null) {
            return null;
        }
        return date2Str(date, FORMAT_M);
    }

    /**
     * 字符串日期(2016-5-25 22:12)转换为 25特定：只适用于本系统
     *
     * @param str
     * @return
     */
    public synchronized static String ToDay(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }

        Date date = str2Date(str, FORMAT_YMDHM);
        if (date == null) {
            return null;
        }
        return date2Str(date, FORMAT_D);
    }

    /**
     * 字符串日期(2016-5-25 22:12)转换为 22特定：只适用于本系统
     *
     * @param str
     * @return
     */
    public synchronized static String ToHour(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }

        Date date = str2Date(str, FORMAT_YMDHM);
        if (date == null) {
            return null;
        }
        return date2Str(date, FORMAT_HH);
    }

    /**
     * 字符串日期(2016-5-25 22:12)转换为 12 特定：只适用于本系统
     *
     * @param str
     * @return
     */
    public synchronized static String ToMinute(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }

        Date date = str2Date(str, FORMAT_YMDHM);
        if (date == null) {
            return null;
        }
        return date2Str(date, FORMAT_mm);
    }


    /**
     * 得到当前时间
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getNowTime() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);
        return time;
    }

    public static String getNowTime(String Format) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat(Format);
        String time = format.format(date);
        return time;
    }

    ////获取某前月的第一天
    public static String getStartYMTime(String Format,int month) {
        //获取前月的第一天
        SimpleDateFormat format = new SimpleDateFormat(Format);
        Calendar   cal_1=Calendar.getInstance();//获取当前日期
        cal_1.add(Calendar.MONTH, month);
        cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
        String time = format.format(cal_1.getTime());
        return time;
    }
    //获取某前月的最后一天
    public static String getEndYMTime(String Format,int month) {
        SimpleDateFormat format = new SimpleDateFormat(Format);
        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, month+1);
        cale.set(Calendar.DAY_OF_MONTH,0);//设置为1号,当前日期既为本月第一天
        String time = format.format(cale.getTime());
        return time;
    }



}
