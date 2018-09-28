/**create by liuhua at 2018年8月6日 上午10:17:00**/
package com.star.truffle.module.build.utils;

import java.util.Map;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

public class ThymeleafUtils {

  private static SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
  
  public static String process(String templateString, Map<String, Object> data){
    Context context = new Context();
    context.setVariables(data);
    String res = springTemplateEngine.process(templateString, context);
    return res;
  }
}
