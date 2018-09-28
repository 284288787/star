/**create by liuhua at 2018年8月1日 下午4:36:31**/
package com.star.truffle.module.build.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Entity {

  private String caption;
  private String beanName;
  private String tableName;
  
  private String winWidth;
  private String winHeight;
  
  private boolean btnAdd;
  private boolean btnEdit;
  private boolean btnEnabled;
  private boolean btnDelete;
  private boolean createTable;      //是否建表
  private boolean existsDrop;       //createTable = true, 若表存在 是否删掉先
  
  private long idWorkerId;
  private long idWorkerDataCenterId;
  private List<Field> fields;

  public static Entity of(String caption, String beanName, String tableName, long idWorkerId, long idWorkerDataCenterId, String winWidth, String winHeight, boolean createTable, boolean existsDrop){
    return new Entity(caption, beanName, tableName, idWorkerId, idWorkerDataCenterId, winWidth, winHeight, createTable, existsDrop);
  }
  
  private Entity(String caption, String beanName, String tableName, long idWorkerId, long idWorkerDataCenterId, String winWidth, String winHeight, boolean createTable, boolean existsDrop) {
    super();
    this.caption = caption;
    this.beanName = beanName;
    this.tableName = tableName;
    this.idWorkerId = idWorkerId;
    this.idWorkerDataCenterId = idWorkerDataCenterId;
    this.winHeight = winHeight;
    this.winWidth = winWidth;
    this.createTable = createTable;
    this.existsDrop = existsDrop;
  }
}
