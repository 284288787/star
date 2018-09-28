/**create by liuhua at 2018年8月16日 下午5:50:41**/
package com.star.truffle.module.order.temp;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.star.truffle.module.build.dto.Project;
import com.star.truffle.module.build.service.BuildFiles;

public class Build {

  public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
    Project project = new Project("F:/newframework/star-framework-module", "F:/newframework/star-framework-module", "order", 12352, "0.0.4-MODULE");
    String classpath1 = "com.star.truffle.module.order.temp.ShoppingCart";
    String classpath2 = "com.star.truffle.module.order.temp.DeliveryAddress";
    String classpath3 = "com.star.truffle.module.order.temp.Order";
    String classpath5 = "com.star.truffle.module.order.temp.OrderDetail";
    String classpath4 = "com.star.truffle.module.order.temp.OrderAfterSale";
    BuildFiles.build(false, project, classpath1, classpath2, classpath3, classpath5, classpath4);
  }
}
