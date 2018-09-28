/**create by liuhua at 2018年8月1日 上午11:37:43**/
package com.star.truffle.module.build.dto;

import java.util.Arrays;
import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.module.build.constant.DsType;
import com.star.truffle.module.build.constant.FieldType;
import com.star.truffle.module.build.utils.Utils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Field {

  private String caption;                //展示名
  private String javaName;               //java字段名称
  private String dsName;                 //数据库字段名称
  private FieldType javaType;            //String, Long, integer, Date 等
  private DsType dsType;                 //varchar, bigint, int, tinyint, datetime, decimal 等
  private int length;                    //长度
  private boolean primaryKey;            //是否为主键 
  private String enumName;               //枚举类型名称
  private String enumOptionTypes;        //枚举内元素括号内的类型，若为空，表示括号内没有元素，例如： {"val":"int","caption":"String"}  或者 为空
  private String enumOptionValues;       //{"man": {"val": 1, "caption": "男"}, "woman": {"val": 0, "caption": "女"}}     或者 ["man", "woman"]
  private String defaultAddfieldValueType;//值类型，java.lang包下的不用写
  private String defaultAddfieldValue;   //如果fieldAdd没有值,说明该字段不在界面展示，例如添加时间，默认状态等
  private String defaultEditfieldValueType;//值类型，java.lang包下的不用写
  private String defaultEditfieldValue;  //如果fieldEdit没有值,说明该字段不在界面展示，例如编辑时间等
  
  private FieldList fieldList;           //此字段若作为列表展示，则应该有
  private FieldQuery fieldQuery;         //此字段若作为列表查询条件，则应该有
  private FieldAdd fieldAdd;             //此字段若作为新增记录的字段，则应该有
  private FieldAdd fieldEdit;            //此字段若作为编辑记录的字段，则应该有
  
  public static Field ofString(String caption, String javaName, DsType dsType, int length){
    if (! Arrays.asList(FieldType.String.getDsTypes()).contains(dsType)) {
      throw new StarServiceException(ApiCode.PARAM_ERROR.getCode(), "dsType 错误， 没有包含在FieldType.String里");
    }
    String dsName = Utils.camelToUnderline(javaName);
    Field field = new Field(caption, javaName, dsName, FieldType.String, dsType, length, false);
    return field;
  }
  public static Field ofLong(String caption, String javaName, DsType dsType, int length){
    if (! Arrays.asList(FieldType.Long.getDsTypes()).contains(dsType)) {
      throw new StarServiceException(ApiCode.PARAM_ERROR.getCode(), "dsType 错误， 没有包含在FieldType.Long里");
    }
    String dsName = Utils.camelToUnderline(javaName);
    Field field = new Field(caption, javaName, dsName, FieldType.Long, dsType, length, false);
    return field;
  }
  public static Field ofLongPrimary(String caption, String javaName, DsType dsType, int length){
    if (! Arrays.asList(FieldType.Long.getDsTypes()).contains(dsType)) {
      throw new StarServiceException(ApiCode.PARAM_ERROR.getCode(), "dsType 错误， 没有包含在FieldType.Long里");
    }
    String dsName = Utils.camelToUnderline(javaName);
    Field field = new Field(caption, javaName, dsName, FieldType.Long, dsType, length, true);
    return field;
  }
  public static Field ofInteger(String caption, String javaName, DsType dsType, int length){
    if (! Arrays.asList(FieldType.Integer.getDsTypes()).contains(dsType)) {
      throw new StarServiceException(ApiCode.PARAM_ERROR.getCode(), "dsType 错误， 没有包含在FieldType.Integer里");
    }
    String dsName = Utils.camelToUnderline(javaName);
    Field field = new Field(caption, javaName, dsName, FieldType.Integer, dsType, length, false);
    return field;
  }
  public static Field ofInteger(String caption, String javaName, DsType dsType, String enumName, String enumOptionTypes, String enumOptionValues){
    if (! Arrays.asList(FieldType.Integer.getDsTypes()).contains(dsType)) {
      throw new StarServiceException(ApiCode.PARAM_ERROR.getCode(), "dsType 错误， 没有包含在FieldType.Integer里");
    }
    String dsName = Utils.camelToUnderline(javaName);
    Field field = new Field(caption, javaName, dsName, FieldType.Integer, dsType, 3, false);
    field.setEnumName(enumName);
    field.setEnumOptionTypes(enumOptionTypes);
    field.setEnumOptionValues(enumOptionValues);
    return field;
  }
  public static Field ofDate(String caption, String javaName){
    String dsName = Utils.camelToUnderline(javaName);
    Field field = new Field(caption, javaName, dsName, FieldType.Date, DsType.DATETIME, 0, false);
    return field;
  }
  
  private Field(String caption, String javaName, String dsName, FieldType javaType, DsType dsType, int length, boolean primaryKey) {
    super();
    this.caption = caption;
    this.javaName = javaName;
    this.dsName = dsName;
    this.javaType = javaType;
    this.dsType = dsType;
    this.length = length;
    this.primaryKey = primaryKey;
  }
  
}
