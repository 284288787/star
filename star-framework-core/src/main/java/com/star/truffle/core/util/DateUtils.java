/** create by liuhua at 2018年5月8日 下午5:22:53 **/
package com.star.truffle.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

  public static String formatTodayDate() {
    return formatDate(new Date(), "yyyy-MM-dd");
  }
  
  public static String formatTodayDateTime() {
    return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
  }

  public static String formatDate(Date date) {
    return formatDate(date, "yyyy-MM-dd");
  }
  
  public static String formatDateTime(Date date) {
    return formatDate(date, "yyyy-MM-dd HH:mm:ss");
  }
  
  public static String formatDate(Date date, String pattern) {
    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    return sdf.format(date);
  }
  
  public static String formatNow(String pattern) {
    return formatDate(new Date(), pattern);
  }

  public static Date toDateYmd(String str) {
    return toDate(str, "yyyy-MM-dd");
  }

  public static Date toDateYmdHms(String str) {
    return toDate(str, "yyyy-MM-dd HH:mm:ss");
  }

  public static Date toDate(String str, String pattern) {
    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    try {
      return sdf.parse(str);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return null;
  }
}
