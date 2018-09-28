/**create by liuhua at 2018年1月20日 上午11:01:59**/
package com.star.truffle.common.importdata;

import java.io.Serializable;
import java.util.Date;

public class RecordType implements Serializable {
  private static final long serialVersionUID = 1L;

  private String name;
  private Class<?> clazz;
  private Class<?> fieldType;
  private String typeName;
  private boolean nullValue;
  private int multiplyBy; // clazz为数字型的才有效
  private String valueOpts;
  private String pattern;
  
  public static RecordType ofDate(String name, String pattern) {
    RecordType recordType = new RecordType(name, Date.class, "日期");
    recordType.setPattern(pattern);
    return recordType;
  }
  
  public RecordType(String name, Class<?> clazz, String typeName, int multiplyBy, boolean nullValue) {
    super();
    this.name = name;
    this.clazz = clazz;
    this.fieldType = clazz;
    this.multiplyBy = multiplyBy;
    this.nullValue = nullValue;
    this.typeName = typeName;
  }

  public RecordType(String name, Class<?> clazz, String typeName, int multiplyBy) {
    super();
    this.name = name;
    this.clazz = clazz;
    this.fieldType = clazz;
    this.multiplyBy = multiplyBy;
    this.typeName = typeName;
  }

  public RecordType(String name, Class<?> clazz, String typeName, boolean nullValue) {
    super();
    this.name = name;
    this.clazz = clazz;
    this.fieldType = clazz;
    this.multiplyBy = 1;
    this.nullValue = nullValue;
    this.typeName = typeName;
  }

  public RecordType(String name, Class<?> clazz, String typeName) {
    super();
    this.name = name;
    this.clazz = clazz;
    this.fieldType = clazz;
    this.multiplyBy = 1;
    this.typeName = typeName;
  }

  public RecordType(String name, Class<?> ExcelType, Class<?> fieldType, String typeName, String valueOpts) {
    this.name = name;
    this.clazz = ExcelType;
    this.fieldType = fieldType;
    this.multiplyBy = 1;
    this.typeName = typeName;
    this.valueOpts = valueOpts;
  }
  
  public String getName() {
    return name;
  }

  public Class<?> getClazz() {
    return clazz;
  }

  public Class<?> getFieldType() {
    return fieldType;
  }

  public int getMultiplyBy() {
    return multiplyBy;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setClazz(Class<?> clazz) {
    this.clazz = clazz;
  }

  public void setMultiplyBy(int multiplyBy) {
    this.multiplyBy = multiplyBy;
  }

  public boolean isNullValue() {
    return nullValue;
  }

  public void setNullValue(boolean nullValue) {
    this.nullValue = nullValue;
  }

  public String getTypeName() {
    return typeName;
  }

  public String getValueOpts() {
    return valueOpts;
  }

  public String getPattern() {
    return pattern;
  }

  public void setPattern(String pattern) {
    this.pattern = pattern;
  }
}
