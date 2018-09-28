/** create by liuhua at 2018年7月20日 下午3:58:39 **/
package com.star.truffle.module.user.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.star.truffle.module.user.dao.read.UriReadDao;
import com.star.truffle.module.user.dao.write.UriWriteDao;
import com.star.truffle.module.user.domain.Uri;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class initSourceUri implements ApplicationListener<ContextRefreshedEvent> {

  @Autowired
  private UriWriteDao uriWriteDao;
  @Autowired
  private UriReadDao uriReadDao;

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    Date now = new Date();
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    if (null != event.getApplicationContext().getParent()) {
      List<String> paths = new ArrayList<>();
      Map<String, Object> beans = event.getApplicationContext().getBeansWithAnnotation(Controller.class);
      for (Map.Entry<String, Object> entry : beans.entrySet()) {
        Class<?> clazz = entry.getValue().getClass();
        String[] qzs = null;
        RequestMapping crm = clazz.getAnnotation(RequestMapping.class);
        if (null != crm) {
          qzs = crm.value();
        }
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
          RequestMapping rm = method.getAnnotation(RequestMapping.class);
          if (null != rm) {
            String[] vals = rm.value();
            for (String val : vals) {
              String path = val.startsWith("/") ? val : "/" + val;
              if (null != qzs && qzs.length > 0) {
                for (String qz : qzs) {
                  qz = qz.startsWith("/") ? qz : "/" + qz;
                  path = qz + path;
                }
              }
              if (path.startsWith("/swagger") || path.equals("/api/logout") || path.equals("/getLoginInfo") || path.equals("/api/getLoginInfo") 
                  || path.equals("/login") || path.equals("/accessDenied") || path.equals("/api/login") || path.equals("/") || path.equals("/common/**")
                  || path.equals("/changeMinePass")) {
                continue;
              }
              paths.add(path.replaceAll("\\{[^\\}]+\\}", "*"));
            }
          }
        }
      }
      List<Uri> uris = new ArrayList<>();
      for (String path : paths) {
        Uri uri = this.uriReadDao.getUri(path);
        if (null == uri) {
          uris.add(new Uri(path));
        } else {
          uris.add(uri);
        }
      }
      this.uriWriteDao.deleteUriBeforeNow(now);
      this.uriWriteDao.batchSaveUri(uris);
      log.info("uri length: " + paths.size());
    }
  }
}
