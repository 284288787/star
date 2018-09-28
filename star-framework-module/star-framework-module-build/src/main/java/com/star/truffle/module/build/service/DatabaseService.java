/**create by liuhua at 2018年8月7日 下午5:09:11**/
package com.star.truffle.module.build.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.module.build.constant.DsType;
import com.star.truffle.module.build.dao.write.DdlDao;
import com.star.truffle.module.build.dto.Entity;
import com.star.truffle.module.build.dto.Field;

@Service
public class DatabaseService {

  @Autowired
  private DdlDao ddlDao;
  
  private Field getPrimaryKey(List<Field> fields){
    return fields.stream().filter(field -> field.isPrimaryKey()).findFirst().orElseThrow(() -> new StarServiceException(ApiCode.PARAM_ERROR.getCode(), "必须设置一个主键，并且只能有一个"));
  }
  
  public void createTable(Entity entity) {
    if (! entity.isCreateTable()) {
      return ;
    }
    List<Field> fields = entity.getFields();
    Field primary = getPrimaryKey(fields);
    
    StringBuffer sql = new StringBuffer();
    if (entity.isExistsDrop()) {
      sql.append("DROP TABLE IF EXISTS `" + entity.getTableName() + "`;\n");
    }
    sql.append("CREATE TABLE `" + entity.getTableName() + "` (\n");
    sql.append("  `" + primary.getDsName() + "` " + primary.getDsType().name() + "(" + primary.getLength() + ") NOT NULL,\n");
    for (Field field : fields) {
      if (! field.isPrimaryKey()) {
        sql.append("  `" + field.getDsName() + "` " + field.getDsType().name());
        if (field.getDsType().equals(DsType.VARCHAR)) {
          sql.append("(" + field.getLength() + ") CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,\n");
        } else if (field.getDsType().equals(DsType.DATETIME)) {
          sql.append(" NULL DEFAULT NULL,\n");
        } else if (field.getDsType().equals(DsType.TEXT) || field.getDsType().equals(DsType.LONGTEXT)) {
          sql.append(" NULL,\n");
        } else if (field.getDsType().equals(DsType.INT) || field.getDsType().equals(DsType.TINYINT) || field.getDsType().equals(DsType.BIGINT)) {
          sql.append("(" + field.getLength() + ") NULL DEFAULT NULL,\n");
        }
      }
    }
    sql.append("  PRIMARY KEY (`" + primary.getDsName() + "`)\n");
    sql.append(")");
    
    System.out.println(sql);
    if (null != ddlDao) {
      ddlDao.createTable(sql.toString());
    }
  }

}
