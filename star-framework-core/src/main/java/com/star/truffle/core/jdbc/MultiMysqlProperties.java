/**create by liuhua at 2018年7月2日 上午11:51:03**/
package com.star.truffle.core.jdbc;

import java.util.Map;
import lombok.Data;

@Data
public class MultiMysqlProperties {
  private Map<String, MysqlProperties> starMysql;
}
