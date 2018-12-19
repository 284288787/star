/** create by liuhua at 2018年5月8日 下午5:22:53 **/
package com.star.truffle.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
  
  public static Date plusNow(long amountToAdd, TemporalUnit unit) {
    ZoneId zone = ZoneId.systemDefault();
    Instant instant = LocalDateTime.now().plus(amountToAdd, unit).atZone(zone).toInstant();
    return Date.from(instant);
  }
  
  public static Date plusDate(Date date, long amountToAdd, TemporalUnit unit) {
    ZoneId zone = ZoneId.systemDefault();
    Instant instant = LocalDateTime.ofInstant(date.toInstant(), zone).plus(amountToAdd, unit).atZone(zone).toInstant();
    return Date.from(instant);
  }
  
  public static Date minusNow(long amountToSubtract, TemporalUnit unit) {
    ZoneId zone = ZoneId.systemDefault();
    Instant instant = LocalDateTime.now().minus(amountToSubtract, unit).atZone(zone).toInstant();
    return Date.from(instant);
  }

  public static Date minusDate(Date date, long amountToSubtract, TemporalUnit unit) {
    ZoneId zone = ZoneId.systemDefault();
    Instant instant = LocalDateTime.ofInstant(date.toInstant(), zone).minus(amountToSubtract, unit).atZone(zone).toInstant();
    return Date.from(instant);
  }
  
  /**
   * 得到本周周一
   */
  public static Date getMondayOfThisWeek() {
    Calendar c = Calendar.getInstance();
    int week = c.get(Calendar.DAY_OF_WEEK) - 1;
    if (week == 0) week = 7;
    c.add(Calendar.DATE, -week + 1);
    return c.getTime();
  }
  
  /**
   * 获取本月第一天
   * @return
   */
  public static Date getFirstOfThisMonth() {
    Calendar c = Calendar.getInstance();
    c.set(Calendar.DATE, 1);
    return c.getTime();
  }

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
