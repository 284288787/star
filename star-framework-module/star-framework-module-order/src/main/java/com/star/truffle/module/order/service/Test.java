/**create by liuhua at 2018年10月17日 下午2:04:01**/
package com.star.truffle.module.order.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Date;

import com.star.truffle.core.util.DateUtils;

public class Test {

  public static void main(String[] args) {
    Date date = new Date(LocalDateTime.of(LocalDate.now(), LocalTime.ofNanoOfDay(0)).toInstant(ZoneOffset.of("+8")).toEpochMilli());
    System.out.println(date.getTime());
    Date date2 = DateUtils.toDateYmdHms("2018-10-17 00:00:00");
    System.out.println(date2.getTime());
    
    System.out.println(DateUtils.getFirstOfThisMonth());
  }

}
