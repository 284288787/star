/** create by liuhua at 2018年6月5日 下午2:24:14 **/
package com.star.truffle.core.jackson;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.star.truffle.core.web.ApiResult;

import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

public class StarJson {

  private ObjectMapper objectMapper;
  private ObjectMapper objectMapper2;

  public StarJson(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
    this.objectMapper2 = objectMapper.copy();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    objectMapper2.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    objectMapper2.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    objectMapper2.setSerializationInclusion(Include.NON_NULL);  
  }
  
  /**
   * 将对象转换为json字符串
   */
  public <T> String obj2string(T t) {
    try {
      if (null == t) {
        return "{}";
      }
      return objectMapper.writeValueAsString(t);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 将对象转换为json字符串 转成减号
   */
  public <T> String obj2stringsnake(T t) {
    try {
      if (null == t) {
        return "{}";
      }
      
      String result = objectMapper2.writeValueAsString(t);
      return result;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  public <T> T str2objsnake(String jsonStr, TypeReference<T> typeReference) {
    try {
      return objectMapper2.readValue(jsonStr, typeReference);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * description: 注意此修改配置，会影响此线程的objectMapper
   * 
   * @param t
   * @param serializationView
   * @param <T>
   * @return
   */
  public <T> String obj2string(T t, Class<?> serializationView) {
    try {
      return objectMapper.writerWithView(serializationView).writeValueAsString(t);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  public <T> List<T> str2list(String jsonStr) {
    try {
//      JavaType t = objectMapper.getTypeFactory().constructParametricType(List.class, cls);
      return objectMapper.readValue(jsonStr, new TypeReference<List<T>>() {});
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  
  public <K, V> Map<K, V> str2Map(String jsonStr) {
    try {
      if (StringUtils.isBlank(jsonStr)) {
        return new HashMap<>();
      }
      return objectMapper.readValue(jsonStr, new TypeReference<Map<K, V>>() {});
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public <K, V> Map<K, V> bean2Map(Object object) {
    try {
      if (null == object) {
        return new HashMap<>();
      }
      return str2Map(obj2string(object));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  
  public <T> T str2obj(String jsonStr, TypeReference<T> typeReference) {
    try {
      return objectMapper.readValue(jsonStr, typeReference);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  public <T> T str2obj(String jsonStr, JavaType javaType) {
    try {
      return objectMapper.readValue(jsonStr, javaType);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  public <T> T inputStream2obj(InputStream inputStream, TypeReference<T> typeReference) {
    try {
      return objectMapper.readValue(inputStream, typeReference);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public <T> T inputStream2obj(InputStream inputStream, Class<T> clazz) {
    try {
      return objectMapper.readValue(inputStream, clazz);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


  /**
   * 将字符串转为对象
   */
  public <T> T str2obj(String jsonStr, Class<T> cls) {
    try {
      if (StringUtils.isBlank(jsonStr)) {
        return null;
      }
      return objectMapper.readValue(jsonStr, cls);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }



  /**
   * 将字符串转为json节点
   */
  public JsonNode str2node(String jsonStr) {
    try {
      return objectMapper.readTree(jsonStr);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public <T> T readAs(byte[] bytes, TypeReference<T> typeReference) {
    try {
      return objectMapper.readValue(bytes, typeReference);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 获取BaseResult中的泛型，传入的是泛型类型
   * 
   * @param clz
   * @param <T>
   * @return
   */
  @SuppressWarnings("restriction")
  public static <T> TypeReference<T> getReference(Class<T> clz) {
    Type[] types = new Type[1];
    types[0] = clz;
    final ParameterizedTypeImpl type = ParameterizedTypeImpl.make(ApiResult.class, types, ApiResult.class.getDeclaringClass());
    return new TypeReference<T>() {
      @Override
      public Type getType() {
        return type;
      }
    };
  }

  public JavaType constructType(Type type) {
    return this.objectMapper.getTypeFactory().constructType(type);
  }

  public <T> T map2Bean(Map<String, Object> params, Class<T> cls) {
    return this.str2obj(this.obj2string(params), cls);
  }
}
