package com.star.truffle.core.okhttp;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import com.google.common.base.Strings;
import com.star.truffle.core.web.ApiCode;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;

@Slf4j
public class StarOkHttpClient {

  public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
  @Getter
  private OkHttpClient okHttpClient;

  public StarOkHttpClient(OkHttpClient okHttpClient) {
    this.okHttpClient = okHttpClient;
  }

  public String get(StarOkHttpBuilder starGetBuilder) {
    if (StringUtils.isBlank(starGetBuilder.getUrl())) {
      return null;
    }
    Builder builder = createRequestBuilder(createGetUrl(starGetBuilder.getUrl(), starGetBuilder.getParams()), starGetBuilder.getHeaders());
    return execute(builder.get().build());
  }
  
  public String get(String url, Map<String, String> params, Map<String, String> headers) {
    if (StringUtils.isBlank(url)) {
      return null;
    }
    Builder builder = createRequestBuilder(createGetUrl(url, params), headers);
    return execute(builder.get().build());
  }

  public String get(String url) {
    return get(url, null, null);
  }

  public String post(StarOkHttpBuilder starPostBuilder) {
    if (StringUtils.isBlank(starPostBuilder.getUrl())) {
      return null;
    }
    Builder builder = createRequestBuilder(starPostBuilder.getUrl(), starPostBuilder.getHeaders());
    FormBody.Builder formBuilder = new FormBody.Builder();
    Optional.ofNullable(starPostBuilder.getParams()).ifPresent(c -> c.forEach((key, value) -> {
      if (value != null) {
        formBuilder.add(key, value);
      } else {
        log.warn("key:{} is null", key);
      }
    }));
    return execute(builder.post(formBuilder.build()).build());
  }

  public String post(String url, Map<String, String> params, Map<String, String> headers) {
    if (StringUtils.isBlank(url)) {
      return null;
    }
    Builder builder = createRequestBuilder(url, headers);
    FormBody.Builder formBuilder = new FormBody.Builder();
    Optional.ofNullable(params).ifPresent(c -> c.forEach((key, value) -> {
      if (value != null) {
        formBuilder.add(key, value);
      } else {
        log.warn("key:{} is null", key);
      }
    }));
    return execute(builder.post(formBuilder.build()).build());
  }
  
  public String postJson(StarOkHttpBuilder starPostBuilder) {
    if (StringUtils.isBlank(starPostBuilder.getUrl())) {
      return null;
    }
    Builder builder = createRequestBuilder(starPostBuilder.getUrl(), starPostBuilder.getHeaders());
    RequestBody body = RequestBody.create(JSON, starPostBuilder.getBody());
    return execute(builder.post(body).build());
  }
  
  public String postJson(String url, String json, Map<String, String> headers) {
    if (StringUtils.isBlank(url)) {
      return null;
    }
    Builder builder = createRequestBuilder(url, headers);
    RequestBody body = RequestBody.create(JSON, json);
    return execute(builder.post(body).build());
  }
  
  public String uploadFile(String url, String filePath, String fileName) {
    if (StringUtils.isBlank(url)) {
      return null;
    }
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", "Client-ID " + UUID.randomUUID());
    
    Builder builder = createRequestBuilder(url, headers);
    RequestBody body = new MultipartBody.Builder()
        .setType(MultipartBody.FORM)
        .addFormDataPart("file", fileName, RequestBody.create(MediaType.parse("multipart/form-data"), new File(filePath)))
        .build();
    
    return execute(builder.post(body).build());
  }

  public String postJson(String url, String json) {
    return postJson(url, json, null);
  }

  private String execute(Request request) {
    try {
      Response response = this.okHttpClient.newCall(request).execute();
      if (response.isSuccessful()) {
        String result = response.body().string();
        log.info("okhttp: " + result);
        return result;
      } else {
        log.error("request url: {} http status code:{}", request.url(), response.code());
        String result = Optional.ofNullable(response.body()).map(body -> {
          try {
            return Strings.emptyToNull(body.string());
          } catch (IOException e) {
            log.error("获取body失败:", e);
            return null;
          }
        }).orElse(ApiCode.HTTP_ERROR.getMsg());
        log.info("okhttp: " + result);
        return result;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  @SuppressWarnings("unused")
  private boolean isPrimitiveType(Type type) {
    if (type instanceof Class) {
      Class<?> clazz = (Class<?>) type;
      if (ClassUtils.isPrimitiveOrWrapper(clazz) || ClassUtils.isAssignable(clazz, String.class)) {
        return true;
      }
    }
    return false;
  }

  private Builder createRequestBuilder(String url, Map<String, String> headers) {
    Builder builder = new Request.Builder();
    builder.url(url);
    Optional.ofNullable(headers).ifPresent(c -> c.forEach(builder::addHeader));
    return builder;
  }

  private String createGetUrl(String url, Map<String, String> params) {
    if (CollectionUtils.isEmpty(params)) {
      return url;
    }
    StringBuilder sb = new StringBuilder();
    params.forEach((k, v) -> {
      if (sb.length() == 0) {
        sb.append("?");
      } else if (sb.length() > 0) {
        sb.append("&");
      }
      sb.append(k);
      sb.append("=").append(v);
    });
    return url + sb.toString();
  }
}
