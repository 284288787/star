/**create by liuhua at 2018年8月13日 上午11:32:34**/
package com.star.truffle.common.service;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import com.star.truffle.common.dto.ExcelExportParam;
import com.star.truffle.common.importdata.AbstractDataExport;
import com.star.truffle.common.importdata.AbstractDataImport;
import com.star.truffle.common.importdata.ExcelUtil;
import com.star.truffle.common.importdata.ImportResult;
import com.star.truffle.common.properties.Excel;
import com.star.truffle.core.util.ClassUtils;

@Service
public class ExcelService {

  public List<ImportResult> importExcel(byte[] bs, String filename, String handle, String errorImport) {
    AbstractDataImport<?> dataImport = (AbstractDataImport<?>) ClassUtils.getInstance(handle, new Class<?>[] { byte[].class, String.class}, new Object[] { bs, filename});
    List<ImportResult> list = dataImport.importDataOfAllSheet();
    for (ImportResult importResult : list) {
      if (("0".equals(errorImport) || importResult.getErrorRecords().isEmpty()) && !importResult.getSuccessRecords().isEmpty()) {
        dataImport.save(importResult);
        importResult.setStatus(2);
      }
      importResult.setFilename(filename);
    }

    return list;
  }

  public void exportExcel(List<ExcelExportParam> eeps, HttpServletResponse response) throws IOException {
    Map<String, String> excelFiles = new LinkedHashMap<>();
    for (ExcelExportParam excelExportParam : eeps) {
      AbstractDataExport<?> dataExport = (AbstractDataExport<?>) ClassUtils.getInstance(excelExportParam.getHandle(), new Class<?>[] {Excel.class}, new Object[] {excelExportParam.getExcel()});
      dataExport.setParams(excelExportParam.getParams());
      String filePath = dataExport.exportData();
      String filename = filePath.substring(filePath.lastIndexOf(File.separator) + 1);
      filename = URLEncoder.encode(filename, "UTF-8");
      excelFiles.put(filename, filePath);
    }
    
    if (excelFiles.size() == 1) {
      for (Map.Entry<String, String> ef : excelFiles.entrySet()) {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=" + ef.getKey());
        OutputStream os = response.getOutputStream();
        File temp = new File(ef.getValue());
        FileUtils.copyFile(temp, os);
        temp.delete();
      }
    } else if (excelFiles.size() > 1) {
      List<File> srcfile = new ArrayList<File>();
      for (Map.Entry<String, String> ef : excelFiles.entrySet()) {
        File temp = new File(ef.getValue());
        srcfile.add(temp);
      }
      ExcelUtil.downZipFile(response, "star-all.zip", srcfile);
    }
  }
}
