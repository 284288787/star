/**create by liuhua at 2018年1月20日 上午9:28:19**/
package com.star.truffle.common.importdata;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.ApplicationContext;

import com.star.truffle.common.properties.Excel;
import com.star.truffle.core.web.config.SpringContextConfig;

public abstract class AbstractDataExport<T> {

  public static final String tempDir = "d:/temp";
  private int pageSize = 1;
  private Map<String, Object> params;
  private Excel excel;
  
  public AbstractDataExport(Excel excel) {
    super();
    this.excel = excel;
    this.getApplication(SpringContextConfig.getApplicationContext());
    File dir = new File(tempDir);
    if (! dir.exists()) {
      dir.mkdirs();
    }
  }

  public Map<String, Object> getParams() {
    return this.params;
  }

  public void setParams(Map<String, Object> params) {
    this.params = params;
  }
  
  public abstract List<String[]> getRecordsData(Map<String, Object> params, int pageNumber, int pageSize);
  
  public abstract void getApplication(ApplicationContext applicationContext);

  public String exportData() throws IOException {
    int pageNumber = 1;
    XSSFWorkbook workbook = new XSSFWorkbook();
    while(true) {
      List<String[]> datas = getRecordsData(params, pageNumber, getPageSize());
      if (null == datas || datas.isEmpty()) {
        break;
      }
      XSSFSheet sheet = workbook.createSheet(excel.getSheetName() + (pageNumber > 1 ? pageNumber : ""));
      Map<Integer, Integer> columnWidth = ExcelUtil.createXlsxExcelSheetHead(sheet, excel.getFields());
      ExcelUtil.createXlsxExcelSheetData(sheet, columnWidth, datas);
      pageNumber ++;
    }
    String filename = ExcelUtil.excelFileName(excel.getFileName()) + ".xlsx";
    String filePath = tempDir + File.separator + filename;
    File xlsFile = new File(filePath);
    FileOutputStream fileOutputStream = new FileOutputStream(xlsFile);
    workbook.write(fileOutputStream);
    fileOutputStream.flush();
    fileOutputStream.close();
    workbook.close();
    return filePath;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public Excel getExcel() {
    return excel;
  }
}
