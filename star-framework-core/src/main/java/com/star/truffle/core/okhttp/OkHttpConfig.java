package com.star.truffle.core.okhttp;

import static java.util.concurrent.TimeUnit.MINUTES;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.core.okhttp.OkHttpClientProperties.Connection;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

@ConditionalOnClass(OkHttpClient.class)
@Configuration
@EnableConfigurationProperties({ OkHttpClientProperties.class })
@AutoConfigureAfter(name = { "org.springframework.cloud.netflix.ribbon.okhttp.OkHttpRibbonConfiguration", "org.springframework.cloud.sleuth.autoconfig.TraceAutoConfiguration" })
public class OkHttpConfig {

  @Autowired
  private HttpLoggingInterceptor httpLoggingInterceptor;

  private OkHttpClient.Builder createBuilder(OkHttpClientProperties okHttpClientProperties, ConnectionPool connectionPool) {
    OkHttpClient.Builder builder = new okhttp3.OkHttpClient.Builder()
        .readTimeout(okHttpClientProperties.getReadTimeout(), TimeUnit.MILLISECONDS)
        .connectTimeout(okHttpClientProperties.getConnectTimeout(), TimeUnit.MILLISECONDS)
        .writeTimeout(okHttpClientProperties.getWriteTimeout(), TimeUnit.MILLISECONDS)
        .connectionPool(connectionPool).followRedirects(okHttpClientProperties.isFollowRedirects())
        .retryOnConnectionFailure(okHttpClientProperties.isRetryOnConnectionFailure())
        .addInterceptor(httpLoggingInterceptor);

    return builder;
  }

  @Bean
  @ConditionalOnMissingBean
  public ConnectionPool connectionPool(OkHttpClientProperties okHttpClientProperties) {
    Connection connection = okHttpClientProperties.getConnection();
    return new ConnectionPool(connection.getMaxIdleConnections(), connection.getKeepAliveDurationNs(), MINUTES);
  }

  @Bean("starOkHttpClient")
  @ConditionalOnBean(StarJson.class)
  public StarOkHttpClient starOkHttpClient(OkHttpClientProperties okHttpClientProperties, ConnectionPool connectionPool) {
    return new StarOkHttpClient(createBuilder(okHttpClientProperties, connectionPool).build());
  }

  @Bean("starSslOkHttpClient")
  @ConditionalOnBean(StarJson.class)
  public StarOkHttpClient starSslOkHttpClient(StarSslOkHttp starSslOkHttp) {
    return new StarOkHttpClient(starSslOkHttp.okHttpClient());
  }

  @Bean
  public StarSslOkHttp starSslOkHttp(OkHttpClientProperties okHttpClientProperties, ConnectionPool connectionPool) throws Exception {
    OkHttpClient.Builder builder = createBuilder(okHttpClientProperties, connectionPool);
    final X509TrustManager trustManager = new X509TrustManagerImpl();
    SSLSocketFactory sslSocketFactory;
    SSLContext sslContext = SSLContext.getInstance("SSL");
    sslContext.init(null, new TrustManager[] { trustManager }, new SecureRandom());
    sslSocketFactory = sslContext.getSocketFactory();
    builder.sslSocketFactory(sslSocketFactory, trustManager).hostnameVerifier((hostname, session) -> true);
    return new StarSslOkHttp(builder.build());
  }

  private class X509TrustManagerImpl implements X509TrustManager {

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
      return new X509Certificate[0];
    }
  }

}
