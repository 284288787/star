/** create by liuhua at 2018年7月2日 上午11:51:32 **/
package com.star.truffle.core.jdbc;

import java.util.function.Supplier;
import lombok.Data;

@Data
public class MysqlProperties {
  
  public static final Supplier<JdbcPoolConfig> DEFAULT_POOL = JdbcPoolConfig::new;
  private String username;
  private String password;
  private String url;
  private JdbcPoolConfig pool = DEFAULT_POOL.get();
}
