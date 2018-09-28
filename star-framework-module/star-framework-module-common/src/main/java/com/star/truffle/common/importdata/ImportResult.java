/**create by liuhua at 2018年1月20日 上午9:30:50**/
package com.star.truffle.common.importdata;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class ImportResult implements Serializable {
  private static final long serialVersionUID = 1L;

  private String[] heads;
  private List<List<FieldValue>> errorRecords;
  private List<List<FieldValue>> successRecords;
  private Integer status;      //0:没有记录 1:列数不对 2:导入完成
  private String filename;
  private String sheetName;

  public ImportResult(Integer status, String[] heads, List<List<FieldValue>> errorRecords, List<List<FieldValue>> successRecords) {
    super();
    this.heads = heads;
    this.status = status;
    this.errorRecords = errorRecords;
    this.successRecords = successRecords;
  }
}
