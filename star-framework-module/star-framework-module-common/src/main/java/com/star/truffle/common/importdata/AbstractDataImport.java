/**create by liuhua at 2018年1月20日 上午9:28:19**/
package com.star.truffle.common.importdata;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.context.ApplicationContext;

import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.core.util.DateUtils;
import com.star.truffle.core.web.config.SpringContextConfig;

public abstract class AbstractDataImport<T> {

  private Workbook workbook;
  private Map<String, Object> params;
  private String[] heads;
  private Integer status;
  private StarJson starJson;
  private DecimalFormat decimalFormat = new DecimalFormat("#");
  
  public AbstractDataImport(byte[] bs, String filename) {
    super();
    workbook = ExcelUtil.getWorkbook(bs, filename);
    this.starJson = SpringContextConfig.getBean(StarJson.class);
    this.getApplication(SpringContextConfig.getApplicationContext());
  }

  public Map<String, Object> getParams() {
    return this.params;
  }

  public void setParams(Map<String, Object> params) {
    this.params = params;
  }

  protected Sheet getSheet(int index) {
    if (null == workbook) {
      return null;
    }
    return workbook.getSheetAt(index);
  }

  protected Sheet getSheet(String name) {
    if (null == workbook) {
      return null;
    }
    return workbook.getSheet(name);
  }

  protected int getNumberOfSheets() {
    if (null == workbook) {
      return 0;
    }
    return workbook.getNumberOfSheets();
  }

  protected void closeWorkBook() {
    try {
      this.workbook.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public List<ImportResult> importDataOfAllSheet() {
    List<ImportResult> res = new ArrayList<>();
    int num = getNumberOfSheets();
    for (int i = 0; i < num; i++) {
      ImportResult map = importData(getSheet(i));
      res.add(map);
    }
    return res;
  }

  public ImportResult importData(Sheet sheet) {
    List<RecordType> recordTypes = getRecordTypes();
    List<List<FieldValue>> errorRecords = new ArrayList<>();
    List<List<FieldValue>> successRecords = new ArrayList<>();
    int rows = sheet.getPhysicalNumberOfRows(); // getLastRowNum();
    if (rows == 0) {
      status = 0;
    }
    if (null == status) {
      setHeads(sheet.getRow(0));
      for (int i = 1; i < rows; i++) {
        List<FieldValue> fields = new ArrayList<>();
        boolean success = true;
        Row row = sheet.getRow(i);
        long cols = row.getLastCellNum();
        if (cols > recordTypes.size()) {
          status = 1;
          break;
        }
        if (cols > heads.length) {
          status = 1;
          break;
        }
        for (int j = 0; j < cols; j++) {
          RecordType recordType = recordTypes.get(j);
          String caption = heads[j];
          FieldValue fieldValue = new FieldValue();
          String desc = null;
          boolean valid = true;
          Object sourceValue = null, value = null;
          try {
            Cell cell = row.getCell(j);
            CellType type = cell.getCellTypeEnum();
            switch (type) {
            case STRING:
              sourceValue = cell.getStringCellValue();
              if (!recordType.getClazz().equals(String.class)) {
                valid = false;
                desc = "类型错误，Excel里为文本，实际上【" + caption + "】必须为" + recordType.getTypeName();
              }
              if (null != recordType.getFieldType() && StringUtils.isNotBlank(recordType.getValueOpts())) {
                String opts = recordType.getValueOpts();
                Map<String, Object> map = starJson.str2Map(opts);
                Object object = map.get(sourceValue);
                if (null == object) {
                  valid = false;
                  desc = "值错误，只能选填指定的值";
                } else {
                  if (recordType.getClazz().equals(Integer.class)) {
                    value = Integer.parseInt(object.toString());
                  } else if (recordType.getClazz().equals(Long.class)) {
                    value = Long.valueOf(object.toString());
                  } else if (recordType.getClazz().equals(Double.class)) {
                    value = Double.valueOf(object.toString());
                  } else {
                    value = object;
                  }
                }
              }
              break;
            case NUMERIC:
              if (HSSFDateUtil.isCellDateFormatted(cell)) {
                Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
                value = date;
                sourceValue = DateUtils.formatDateTime(date);
                if (!recordType.getClazz().equals(Date.class)) {
                  valid = false;
                  desc = "类型错误，Excel里为日期，实际上【" + caption + "】必须为" + recordType.getTypeName();
                } else {
                  sourceValue = DateUtils.formatDate(date, recordType.getPattern());
                }
              } else {
                if (null != recordType.getFieldType() && StringUtils.isNotBlank(recordType.getValueOpts())) {
                  String opts = recordType.getValueOpts();
                  if ("电话号码".equals(opts)) {
                    sourceValue = Double.valueOf(cell.getNumericCellValue()).longValue();
                    value = sourceValue.toString();
                    break;
                  } else {
                    Map<String, Object> map = starJson.str2Map(opts);
                    Object object = map.get(sourceValue);
                    if (null == object) {
                      valid = false;
                      desc = "值错误，只能选填指定的值";
                    } else {
                      if (recordType.getClazz().equals(Integer.class)) {
                        value = Integer.parseInt(object.toString());
                      } else if (recordType.getClazz().equals(Long.class)) {
                        value = Long.valueOf(object.toString());
                      } else if (recordType.getClazz().equals(Double.class)) {
                        value = Double.valueOf(object.toString());
                      } else {
                        value = object;
                      }
                    }
                  }
                }
                double temp = cell.getNumericCellValue() * recordType.getMultiplyBy();
                if (recordType.getClazz().equals(Integer.class)) {
                  sourceValue = Double.valueOf(temp).intValue();
                } else if (recordType.getClazz().equals(Long.class)) {
                  sourceValue = Double.valueOf(temp).longValue();
                } else if (recordType.getClazz().equals(Double.class)) {
                  sourceValue = temp;
                } else if (recordType.getClazz().equals(String.class)) {
                  sourceValue = decimalFormat.format(cell.getNumericCellValue());
                } else {
                  sourceValue = cell.getNumericCellValue();
                  valid = false;
                  desc = "类型错误，Excel里为数字，实际上【" + caption + "】必须为" + recordType.getTypeName();
                }
              }
              break;
            case FORMULA:
              sourceValue = cell.getCellFormula();
              valid = false;
              desc = "类型错误，Excel里为公式，实际上【" + caption + "】必须为" + recordType.getTypeName();
              break;
            case BLANK:
              sourceValue = null;
              if (!recordType.isNullValue()) {
                valid = false;
                desc = "类型错误，Excel里为空，实际上【" + caption + "】必须填写值并且为" + recordType.getTypeName();
              }
              break;
            case BOOLEAN:
              sourceValue = cell.getBooleanCellValue();
              if (!recordType.getClazz().equals(Boolean.class)) {
                sourceValue = sourceValue.toString();
                valid = false;
                desc = "类型错误，Excel里是布尔值，实际上【" + caption + "】必须为" + recordType.getTypeName();
              }
              break;
            default:
              sourceValue = "未知";
              if (!recordType.isNullValue()) {
                valid = false;
                desc = "类型错误，未知的类型，实际上【" + caption + "】必须填写值并且为" + recordType.getTypeName();
              }
              break;
            }
          } catch (Exception e) {
            e.printStackTrace();
            valid = false;
            desc = "异常，联系管理员：" + e.getMessage();
          }
          if (valid) {
            if (null == value) {
              value = sourceValue;
            }
            fieldValue.setValidValue(i + 1, j + 1, recordType.getName(), caption, sourceValue, value);
          } else {
            fieldValue.setErrorValue(i + 1, j + 1, recordType.getName(), caption, sourceValue, desc);
            success = false;
          }
          fields.add(fieldValue);
        }
        if (success) {
          successRecords.add(fields);
        } else {
          errorRecords.add(fields);
        }
      }
    }
    ImportResult importResult = new ImportResult(status, heads, errorRecords, successRecords);
    importResult.setSheetName(sheet.getSheetName());
    return importResult;
  }

  private void setHeads(Row row) {
    short cols = row.getLastCellNum();
    this.heads = new String[cols];
    for (int j = 0; j < cols; j++) {
      Cell cell = row.getCell(j);
      this.heads[j] = cell.getStringCellValue();
    }
  }

  public abstract List<RecordType> getRecordTypes();

  public abstract Class<T> getBeanType();

  public abstract Map<Integer, Object> saveBean(List<T> list);
  
  public abstract void getApplication(ApplicationContext applicationContext);

  public ImportResult importFirstSheetData() {
    ImportResult map = importData(getSheet(0));
    closeWorkBook();
    return map;
  }

  public void save(ImportResult importResult) {
    List<List<FieldValue>> successList = importResult.getSuccessRecords();
    if (null == successList || successList.isEmpty()) {
      return;
    }
    List<List<FieldValue>> errorList = importResult.getErrorRecords();
    Class<T> clzz = getBeanType();
    List<T> data = new ArrayList<>();
    for (List<FieldValue> record : successList) {
      Map<String, Object> line = new HashMap<>();
      for (FieldValue fieldValue : record) {
        line.put(fieldValue.getFieldName(), fieldValue.getValue());
      }
      String json = starJson.obj2string(line);
      data.add(starJson.str2obj(json, clzz));
    }
    Map<Integer, Object> errorIdx = saveBean(data);
    List<List<FieldValue>> removed = new ArrayList<>();
    if (null != errorIdx && !errorIdx.isEmpty()) {
      for (Map.Entry<Integer, Object> en : errorIdx.entrySet()) {
        int idx = en.getKey();
        Object value = en.getValue();
        List<FieldValue> list = successList.get(idx);
        removed.add(list);

        if (value instanceof Integer) {
          int index = (Integer) value;
          FieldValue fieldValue = list.get(index);
          fieldValue.toError(fieldValue.getCaption() + "信息未找到");
        } else if (value instanceof String) {
          FieldValue fieldValue = new FieldValue();
          fieldValue.setRowNum(idx);
          fieldValue.toError("异常", (String) value);
          list.add(fieldValue);
        }
        errorList.add(list);
      }
      for (List<FieldValue> list : removed) {
        successList.remove(list);
      }
      errorList.sort((a, b) -> {
        int ar = a.get(0).getRowNum();
        int br = b.get(0).getRowNum();
        return Integer.compare(ar, br);
      });
    }
  }
}
