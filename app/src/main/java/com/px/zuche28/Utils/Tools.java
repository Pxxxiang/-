package com.px.zuche28.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Tools {


    //时间戳转字符串
    public static String getStringDate(long time){
        Date date = new Date(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return simpleDateFormat.format(date);
    }

    //字符串转时间戳
    public static long getTime(String timeString){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        Date d;
        long time = 0;
        try{
            d = sdf.parse(timeString);
            time = d.getTime();
        } catch(ParseException e){
            e.printStackTrace();
        }
        System.out.println("date:" +time);
        return time;
    }
//    public static long getTime(String user_time) {
//        String re_time = null;
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
//        Date d;
//        try {
//            d = sdf.parse(user_time);
//            long l = d.getTime();
//            String str = String.valueOf(l);
//            re_time = str.substring(0, 10);
//        }catch (ParseException e) {
//            // TODO Auto-generated catch block e.printStackTrace();
//        }
//        return  Long.parseLong(re_time);
//    }
}
