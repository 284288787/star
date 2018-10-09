/**create by liuhua at 2018年10月9日 下午4:10:25**/
package com.star.truffle.module.product.cache;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {

  public static void main(String[] args) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = sdf.parse("2018-10-12 00:00:00");
    long l = date.getTime() - System.currentTimeMillis();
    System.out.println(l / 1000 );
  }

}
