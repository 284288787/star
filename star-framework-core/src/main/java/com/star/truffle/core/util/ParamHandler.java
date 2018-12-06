/** create by liuhua at 2016年5月23日 下午1:43:07 **/
package com.star.truffle.core.util;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.core.web.ApiResult;

/**
 * 简化取参数的过程
 * 
 * @author liuhua
 *
 */
public class ParamHandler {
  public static final String SIGN_KEY = "HN_huinong20160707";
  
  private Map<String, Object> map;
  private StarJson starJson;
  
  public static ParamHandler ofMap(Map<String, Object> map, StarJson starJson) {
    return new ParamHandler(map, starJson);
  }
  
  public static <T> ParamHandler ofBean(T object, StarJson starJson) {
    Map<String, Object> map = starJson.bean2Map(object);
    return new ParamHandler(map, starJson);
  }
  
  public static ParamHandler ofParam(String params, StarJson starJson) {
    Map<String, Object> map = null;
    if (StringUtils.isNotBlank(params)) {
      // if (DesUtil.encrypt_args) {
      // params = DesUtil.decrypt(params);
      // }
      map = starJson.str2Map(params);
    } else {
      map = new HashMap<>();
    }
    return new ParamHandler(map, starJson);
  }
  
  public static ParamHandler ofRequest(HttpServletRequest request, StarJson starJson) {
    Map<String, Object> map = new HashMap<String, Object>();
    Enumeration<String> names = request.getParameterNames();
    while (names.hasMoreElements()) {
      String name = names.nextElement();
      String value = request.getParameter(name);
      if (StringUtils.isNotBlank(value)) {
        map.put(name, value);
      }
    }
    if (map.isEmpty()) {
      try {
        ServletInputStream inputStream = request.getInputStream();
        byte[] b = new byte[1024];
        int len = 0;
        StringBuffer temp = new StringBuffer();
        while ((len = inputStream.read(b)) != -1) {
          temp.append(new String(b, 0, len));
        }
        if (temp.length() > 0) {
          map = starJson.str2Map(temp.toString());
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return new ParamHandler(map, starJson);
  }
  
  private ParamHandler(Map<String, Object> map, StarJson starJson) {
    this.map = map;
    this.starJson = starJson;
  }

  /**
   * 加密
   * 
   * @param resultMessage
   * @return
   */
  public <T> String encrypt(ApiResult<T> apiResult) {
    String json = starJson.obj2string(apiResult);
    String enc = DesUtil.encrypt(json);
    return enc;
  }

  public Double getDouble(String name) {
    Object object = getObject(name);
    if (null == object) {
      return null;
    }
    try {
      return Double.parseDouble(object.toString());
    } catch (Exception e) {

    }
    return null;
  }

  public Date getDate(String name, String pattern) {
    Object object = getObject(name);
    if (null == object) {
      return null;
    }
    try {
      SimpleDateFormat sdf = new SimpleDateFormat(pattern);
      return sdf.parse(object.toString());
    } catch (Exception e) {

    }
    return null;
  }

  public Long getLong(String name) {
    Object object = getObject(name);
    if (null == object) {
      return null;
    }
    try {
      return Long.parseLong(object.toString());
    } catch (Exception e) {

    }
    return null;
  }

  public Integer getInteger(String name) {
    Object object = getObject(name);
    if (null == object) {
      return null;
    }
    try {
      return Integer.parseInt(object.toString());
    } catch (Exception e) {

    }
    return null;
  }

  public Boolean getBoolean(String name) {
    Object object = getObject(name);
    try {
      return Boolean.parseBoolean(object.toString());
    } catch (Exception e) {

    }
    return null;
  }
  
  public String getString(String name) {
    Object object = getObject(name);
    if (null == object) {
      return null;
    }
    return object.toString().trim();
  }

  @SuppressWarnings("unchecked")
  public Map<String, Object> getMap(String name) {
    Object object = getObject(name);
    if (null == object) {
      return null;
    }
    return (Map<String, Object>) object;
  }

  public Object getObject(String name) {
    if (null == map) {
      return null;
    }
    return map.get(name);
  }

  /**
   * 可以把map里的参数 封装成希望的bean @author liuhua
   *
   * @param classOfT
   * @throws IllegalAccessException 
   * @throws InstantiationException 
   */
  public <T> T getDTO(Class<T> classOfT) throws InstantiationException, IllegalAccessException {
    T t = starJson.str2obj(starJson.obj2string(map), classOfT);
    if (null == t) {
      t = classOfT.newInstance();
    }
    return t;
  }

  public <T> T getDTO(Type type) {
    return starJson.str2obj(starJson.obj2string(map), starJson.constructType(type));
  }

  /**
   * 签名校验
   * 
   * @author liuhua
   *
   * @param signKey 如果key没有，则使用默认的key
   * @return
   */
  public boolean checkParam(String signKey) {
    if (null != map) {
      String sign = getString("sign");
      if (null == sign || sign.length() == 0) {
        return false;
      }
      Map<String, Object> newMap = new HashMap<String, Object>();
      newMap.putAll(map);
      newMap.remove("sign");
      if (null == signKey || signKey.length() == 0) {
        signKey = SIGN_KEY;
      }
      String tempSign = SignUtils.createSign(newMap, signKey);
      if (sign.equals(tempSign)) {
        return true;
      }
    }
    return false;
  }

  public void addParam(String key, Object value) {
    if (null == map) {
      map = new HashMap<>();
    }
    map.put(key, value);
  }
  
  public Map<String, Object> getMap() {
    return map;
  }

  public Map<String, Object> getMapExcept(String ... keys) {
//    Map<String, Object> temp = starJson.str2Map(starJson.obj2string(map));
    if (null != map && null != keys && keys.length > 0) {
      for (String key : keys) {
        map.remove(key);
      }
    }
    return map;
  }

  public static String changeName(String name) {
    if (StringUtils.isNotBlank(name)) {
      int idx = name.indexOf("_");
      if (idx != -1) {
        if (idx + 1 == name.length()) {
          name = name.substring(0, name.length() - 1);
        } else {
          String temp = name.substring(idx + 1, idx + 2);
          name = name.replaceFirst("_" + temp, temp.toUpperCase());
          return changeName(name);
        }
      }
    }
    return name;
  }
}
