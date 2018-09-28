package com.star.truffle.core.web.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "star-web")
public class StarSpringMvcProperties {

  private String photoPath;
  
  private Log log = new Log();
  
  @Data
  public static class Log {
    public LogLevel level = LogLevel.RSP_HEADERS;
  }

  public enum LogLevel {
    BASIC(1),
    REQ_HEADERS(2),
    REQ_BODY(3),
    RSP_HEADERS(4),
    RSP_BODY(5);
    private int level;

    private LogLevel(int level) {
      this.level = level;
    }

    public int getLevel() {
      return this.level;
    }
  }

}
