/**create by liuhua at 2018年11月28日 下午9:49:38**/
package com.star.truffle.module.product.constant;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class ProductConstant {

  private static Map<Integer, String> states = new HashMap<>();
  
  public static String getCaption(Integer state) {
    if (states.isEmpty()) {
      ProductEnum[] pes = ProductEnum.values();
      for (ProductEnum productEnum : pes) {
        states.put(productEnum.state(), productEnum.caption());
      }
    }
    return states.get(state);
  }
  
  public static String formatMoney(Integer money) {
    DecimalFormat decimalFormat = new DecimalFormat("0.00");
    return decimalFormat.format(money / 100.0);
  }
  public static String formatMoney(String money) {
    Integer m = Integer.parseInt(money);
    return formatMoney(m);
  }
}
