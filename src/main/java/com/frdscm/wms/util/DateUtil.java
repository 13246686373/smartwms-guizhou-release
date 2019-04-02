package com.frdscm.wms.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author: dizhang
 * @Date: 2018/7/5
 * @Desc:
 **/
public class DateUtil {


    private static final String DATE_FORMAT_NORMAL = "yyyy-MM-dd";

    private static DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_NORMAL);

    public static String dayNow() {
        Date dayNow = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(dayNow);
    }

    public static ArrayList<String> getPastDateList(int intervals) {
        ArrayList<String> pastDaysList = new ArrayList<>();
        for (int i = 0; i <= intervals; i++) {
            pastDaysList.add(getPastDate(i));
        }
        return pastDaysList;
    }

    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;
    }

    /**
     * 算出两个日期间相差多少天
     *
     * @param start_date
     * @param end_date
     * @return
     * @throws Exception
     */
    public static int getDayLength(String start_date, String end_date) throws Exception {

        //开始日期
        Date fromDate = getStrToDate(start_date, DATE_FORMAT_NORMAL);
        //结束日期
        Date toDate = getStrToDate(end_date, DATE_FORMAT_NORMAL);
        long from = fromDate.getTime();
        long to = toDate.getTime();

        //一天等于多少毫秒：24*3600*1000

        int day = (int) ((to - from) / (24 * 60 * 60 * 1000));
        return day;
    }


    public static Date getStrToDate(String date, String fomtter) throws Exception {
        DateFormat df = new SimpleDateFormat(fomtter);
        return df.parse(date);
    }


    public static List<String> findDates(Date startTime, Date endTime) {
        List<String> dates = new ArrayList<>();
        dates.add(dateFormat.format(startTime));
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(startTime);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(endTime);
        // 测试此日期是否在指定日期之后
        while (endTime.after(calBegin.getTime()) && endTime.getTime() - startTime.getTime() > 86400000L) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            dates.add(dateFormat.format(calBegin.getTime()));
        }
        return dates;
    }

    /**
     * 时间戳转换yyyy-MM-dd
     *
     * @param time
     * @return
     */
    public static String timeStamp2Date(String time) {
        Long timeLong = Long.parseLong(time);
        //要转换的时间格式
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NORMAL);
        Date date;
        try {
            date = sdf.parse(sdf.format(timeLong));
            return sdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}


