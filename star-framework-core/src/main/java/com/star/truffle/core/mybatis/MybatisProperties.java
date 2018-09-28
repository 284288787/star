/** create by liuhua at 2018年7月2日 上午11:35:19 **/
package com.star.truffle.core.mybatis;

import java.util.Map;
import lombok.Data;

@Data
public class MybatisProperties {

  private Map<String, MysqlMybatisConfig> starMysql;
}
