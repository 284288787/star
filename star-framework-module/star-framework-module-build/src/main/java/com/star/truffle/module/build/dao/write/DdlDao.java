/**create by liuhua at 2018年8月7日 下午5:27:43**/
package com.star.truffle.module.build.dao.write;

import org.apache.ibatis.annotations.Param;

public interface DdlDao {

  public int createTable(@Param("sql") String sql);
}
