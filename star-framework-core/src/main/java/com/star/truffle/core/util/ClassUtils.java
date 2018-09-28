/**
 * com.star.framework.common.util.ClassUtils.java
 * date:2013-5-20
 * @author: liuhua
 */
package com.star.truffle.core.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ClassUtils {
  private static Log logger = LogFactory.getLog(ClassUtils.class);
  
  public static Class<?> getClass(String classPath) {
    try {
      Class<?> clazz = Class.forName(classPath);
      return clazz;
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SecurityException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    }
    
    return null;
  }

  public static Object getInstance(String classPath, Class<?>[] args, Object[] objs) {
    Object res = null;
    try {
      Class<?> clazz = Class.forName(classPath);
      Constructor<?> c = clazz.getConstructor(args);
      res = c.newInstance(objs);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SecurityException e) {
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }

    return res;
  }

  public static Object executeMethod(String methodPath) {
    Object result = null;
    if (null == methodPath || methodPath.indexOf("@") == -1) {
      logger.error("方法路径不对，格式: 类路径@方法名");
      return null;
    }
    String[] arg = methodPath.split("@");
    try {
      Class<?> clazz = Class.forName(arg[0]);
      Method method = clazz.getMethod(arg[1]);
      result = method.invoke(clazz.newInstance());
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SecurityException e) {
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
    return result;
  }

  public static Object getObjectByStaticMethod(String classPath) {
    try {
      Class<?> clazz = Class.forName(classPath);
      Method method = null;
      Method[] methods = clazz.getDeclaredMethods();
      for (int i = 0; i < methods.length; i++) {
        if (classPath.endsWith(methods[i].getReturnType().getName())) {
          method = methods[i];
        }
      }
      return method.invoke(null);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SecurityException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
    return null;
  }
}
