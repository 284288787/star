/**create by liuhua at 2018年8月1日 下午2:42:02**/
package com.star.truffle.module.build.constant;

public enum FieldType {

  String("", DsType.VARCHAR, DsType.TEXT, DsType.LONGTEXT), 
  Long("", DsType.BIGINT), 
  Integer("", DsType.INT, DsType.TINYINT), 
  Date("java.util.Date", DsType.DATETIME);
  
  private String classpath;
  private DsType[] dsTypes;
  
  private FieldType(String classpath, DsType ... dsTypes){
    this.classpath = classpath;
    this.dsTypes = dsTypes;
  }
  
  public DsType[] getDsTypes(){
    return dsTypes;
  }
  public String getClasspath(){
    return classpath;
  }
}
