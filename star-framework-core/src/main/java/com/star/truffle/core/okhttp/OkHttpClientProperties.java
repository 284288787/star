package com.star.truffle.core.okhttp;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import okhttp3.logging.HttpLoggingInterceptor;

@Data
@ConfigurationProperties(prefix = "star-okhttpclient")
public class OkHttpClientProperties {

  private int connectTimeout = 5000;
  private int readTimeout = 30000;
  private int writeTimeout = 30000;
  private boolean retryOnConnectionFailure = true;
  private boolean followRedirects = true;
  private boolean followSslRedirects = true;
  private List<String> interceptors = new ArrayList<>();
  private List<String> networkInterceptors = new ArrayList<>();
  private Connection connection = new Connection();
  private HttpLoggingInterceptor.Level level;

  @Data
  public static class Connection {
    private int maxIdleConnections = 5;
    private long keepAliveDurationNs = 5;
  }
}
