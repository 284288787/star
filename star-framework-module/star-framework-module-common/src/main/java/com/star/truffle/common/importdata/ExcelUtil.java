package com.star.truffle.common.importdata;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.star.truffle.common.properties.Excel;
import com.star.truffle.common.properties.RowData;
import com.star.truffle.core.util.DateUtils;

public class ExcelUtil {

  public static void createExcelFile(OutputStream out, String title, List<String[]> contenList) throws Exception {
    HSSFWorkbook wb = new HSSFWorkbook();
    HSSFRow row = null;
    HSSFCellStyle style = wb.createCellStyle();
    HSSFFont f = wb.createFont();
    f.setBold(true);
    f.setColor(HSSFFont.COLOR_RED);// 红色
    style.setFont(f);
    style.setAlignment(HorizontalAlignment.CENTER);
    ;// 左右居中
    style.setVerticalAlignment(VerticalAlignment.CENTER);
    ;// 上下居中
    if (null != contenList && contenList.size() > 0) {
      final int excelMaxRow = 65535;// 一个excel表格sheet最多只能存放65535条记录
      int index = 0;// 记录数是否达到excelMaxRow
      int contentSize = contenList.size();
      int sheetNum = (contentSize / excelMaxRow) + ((contentSize % excelMaxRow) > 0 ? 1 : 0);
      int len = 0;
      for (int i = 0; i < sheetNum; i++) {
        HSSFSheet sheet = wb.createSheet();
        wb.setSheetName(i, "sheet" + (i + 1));
        while (!contenList.isEmpty()) {
          row = sheet.createRow(index);
          String[] content = contenList.remove(0);
          len = content.length;
          for (int k = 0; k < len; k++) {
            HSSFCell cell = row.createCell(k);
            cell.setCellValue(content[k]);
            if (k == 10) {
              cell.setCellStyle(style);
            }
            // else if (index == 0
            // && (k == 0 || k == 1 || k == 5 || k == 10)) {
            // cell.setCellStyle(style);
            // }
          }
          index++;
          if (index % excelMaxRow == 0 || index >= contentSize) {
            index = 0;
            break;
          }
        }
        for (int j = 0; j < len; j++) {
          sheet.autoSizeColumn(j);
          sheet.setColumnWidth(j, (sheet.getColumnWidth(j) * 2));
        }
      }

      wb.write(out);
      out.flush();
      out.close();
      wb.close();
    }

  }

  public static void createXlsxExcelFile(OutputStream out, List<String[]> contenList) throws Exception {
    XSSFWorkbook wb = new XSSFWorkbook();
    XSSFRow row = null;
    XSSFCellStyle style = wb.createCellStyle();
    XSSFFont f = wb.createFont();
    f.setBold(true);
    f.setColor(XSSFFont.COLOR_RED);// 红色
    style.setFont(f);
    style.setAlignment(HorizontalAlignment.CENTER);
    ;// 左右居中
    style.setVerticalAlignment(VerticalAlignment.CENTER);
    ;// 上下居中
    if (null != contenList && contenList.size() > 0) {
      final int excelMaxRow = 65535;// 一个excel表格sheet最多只能存放65535条记录
      int index = 0;// 记录数是否达到excelMaxRow
      int contentSize = contenList.size();
      int sheetNum = (contentSize / excelMaxRow) + ((contentSize % excelMaxRow) > 0 ? 1 : 0);
      int len = 0;
      for (int i = 0; i < sheetNum; i++) {
        XSSFSheet sheet = wb.createSheet();
        wb.setSheetName(i, "sheet" + (i + 1));
        while (!contenList.isEmpty()) {
          row = sheet.createRow(index);
          String[] content = contenList.remove(0);
          len = content.length;
          for (int k = 0; k < len; k++) {
            XSSFCell cell = row.createCell(k);
            cell.setCellValue(content[k]);
            if (k == 10) {
              cell.setCellStyle(style);
            }
            // else if (index == 0
            // && (k == 0 || k == 1 || k == 5 || k == 10)) {
            // cell.setCellStyle(style);
            // }
          }
          index++;
          if (index % excelMaxRow == 0 || index >= contentSize) {
            index = 0;
            break;
          }
        }
        for (int j = 0; j < len; j++) {
          sheet.autoSizeColumn(j);
          sheet.setColumnWidth(j, (sheet.getColumnWidth(j) * 2));
        }
      }

      wb.write(out);
      out.flush();
      out.close();
      wb.close();
    }
  }

  public static void createXlsxExcelFile(OutputStream out, String sheetName, String[] titles, List<String[]> contentList) throws Exception {
    XSSFWorkbook workbook = new XSSFWorkbook();
    XSSFSheet sheet = workbook.createSheet(sheetName);
    // sheet.setDefaultColumnWidth(12);

    if (titles != null && titles.length > 0) {
      XSSFRow titleRow = sheet.createRow(0);
      XSSFCell titleCell = null;
      for (int i = 0; i < titles.length; i++) {
        titleCell = titleRow.createCell(i);
        titleCell.setCellValue(titles[i]);
      }
    }
    if (contentList != null && contentList.size() > 0) {
      XSSFRow contentRow = null;
      XSSFCell contentCell = null;
      for (int iRow = 0; iRow < contentList.size(); iRow++) {
        String[] content = contentList.get(iRow);
        if (content != null && content.length > 0) {
          contentRow = sheet.createRow(iRow + 1);
          for (int iCell = 0; iCell < content.length; iCell++) {
            contentCell = null;
            contentCell = contentRow.createCell(iCell);
            contentCell.setCellValue(content[iCell]);
          }
        }

      }
    }
    workbook.write(out);
    out.flush();
    out.close();
    workbook.close();
  }

  public static void createXlsx(OutputStream out, Excel excel, Map<String, Object> params, List<String[]> contentList) throws IOException {
    excel = fullExcel(excel, params);
    String sheetName = excel.getSheetName();
    String[] titles = excel.getFields();
    XSSFWorkbook workbook = new XSSFWorkbook();
    XSSFSheet sheet = workbook.createSheet(sheetName);
    // sheet.setDefaultColumnWidth(12);
    Map<Integer, Integer> columnWidth = new HashMap<>();
    int row = null == excel.getFieldsRowNum() ? 0 : excel.getFieldsRowNum();
    if (titles != null && titles.length > 0) {
      XSSFRow titleRow = sheet.createRow(row);
      XSSFCell titleCell = null;
      for (int i = 0; i < titles.length; i++) {
        titleCell = titleRow.createCell(i);
        titleCell.setCellValue(titles[i]);
        int width = (int) (36 * len(titles[i]) * 15);
        columnWidth.put(i, width);
        sheet.setColumnWidth(i, width);
      }
    }
    if (contentList != null && contentList.size() > 0) {
      XSSFRow contentRow = null;
      XSSFCell contentCell = null;
      for (int iRow = 0; iRow < contentList.size(); iRow++) {
        String[] content = contentList.get(iRow);
        if (content != null && content.length > 0) {
          contentRow = sheet.createRow(++ row);
          for (int iCell = 0; iCell < content.length; iCell++) {
            int w = (int) (35.7 * len(content[iCell]) * 15);
            int ow = columnWidth.get(iCell);
            if (w > ow) {
              columnWidth.put(iCell, w);
              sheet.setColumnWidth(iCell, w);
            }
            contentCell = null;
            contentCell = contentRow.createCell(iCell);
            contentCell.setCellValue(content[iCell]);
          }
        }
      }
    }
    workbook.write(out);
    out.flush();
    out.close();
    workbook.close();
  }
  
  public static void createXlsx(OutputStream out, Excel excel, List<String[]> contentList) throws IOException {
    String sheetName = excel.getSheetName();
    String[] titles = excel.getFields();
    XSSFWorkbook workbook = new XSSFWorkbook();
    XSSFSheet sheet = workbook.createSheet(sheetName);
    // sheet.setDefaultColumnWidth(12);
    Map<Integer, Integer> columnWidth = new HashMap<>();
    int row = null == excel.getFieldsRowNum() ? 0 : (excel.getFieldsRowNum() - 1);
    if (titles != null && titles.length > 0) {
      XSSFRow titleRow = sheet.createRow(row);
      XSSFCell titleCell = null;
      for (int i = 0; i < titles.length; i++) {
        titleCell = titleRow.createCell(i);
        titleCell.setCellValue(titles[i]);
        int width = (int) (36 * len(titles[i]) * 15);
        columnWidth.put(i, width);
        sheet.setColumnWidth(i, width);
      }
    }
    if (contentList != null && contentList.size() > 0) {
      XSSFRow contentRow = null;
      XSSFCell contentCell = null;
      for (int iRow = 0; iRow < contentList.size(); iRow++) {
        String[] content = contentList.get(iRow);
        if (content != null && content.length > 0) {
          contentRow = sheet.createRow(++ row);
          for (int iCell = 0; iCell < content.length; iCell++) {
            int w = (int) (35.7 * len(content[iCell]) * 15);
            int ow = columnWidth.get(iCell);
            if (w > ow) {
              columnWidth.put(iCell, w);
              sheet.setColumnWidth(iCell, w);
            }
            contentCell = null;
            contentCell = contentRow.createCell(iCell);
            contentCell.setCellValue(content[iCell]);
          }
        }
      }
    }
    workbook.write(out);
    out.flush();
    out.close();
    workbook.close();
  }
  
  public static void createXlsxExcelFile2(OutputStream out, String sheetName, String[] titles, List<String[]> contentList) throws IOException {
    XSSFWorkbook workbook = new XSSFWorkbook();
    XSSFSheet sheet = workbook.createSheet(sheetName);
    // sheet.setDefaultColumnWidth(12);
    Map<Integer, Integer> columnWidth = new HashMap<>();
    if (titles != null && titles.length > 0) {
      XSSFRow titleRow = sheet.createRow(0);
      XSSFCell titleCell = null;
      for (int i = 0; i < titles.length; i++) {
        titleCell = titleRow.createCell(i);
        titleCell.setCellValue(titles[i]);
        int width = (int) (36 * len(titles[i]) * 15);
        columnWidth.put(i, width);
        sheet.setColumnWidth(i, width);
      }
    }
    if (contentList != null && contentList.size() > 0) {
      XSSFRow contentRow = null;
      XSSFCell contentCell = null;
      for (int iRow = 0; iRow < contentList.size(); iRow++) {
        String[] content = contentList.get(iRow);
        if (content != null && content.length > 0) {
          contentRow = sheet.createRow(iRow + 1);
          for (int iCell = 0; iCell < content.length; iCell++) {
            int w = (int) (35.7 * len(content[iCell]) * 15);
            int ow = columnWidth.get(iCell);
            if (w > ow) {
              columnWidth.put(iCell, w);
              sheet.setColumnWidth(iCell, w);
            }
            contentCell = null;
            contentCell = contentRow.createCell(iCell);
            contentCell.setCellValue(content[iCell]);
          }
        }

      }
    }
    workbook.write(out);
    out.flush();
    out.close();
    workbook.close();
  }
  
  public static Map<Integer, Integer> createXlsxExcelSheetHead(XSSFSheet sheet, String[] titles) throws IOException {
    Map<Integer, Integer> columnWidth = new HashMap<>();
    if (titles != null && titles.length > 0) {
      XSSFRow titleRow = sheet.createRow(0);
      XSSFCell titleCell = null;
      for (int i = 0; i < titles.length; i++) {
        titleCell = titleRow.createCell(i);
        titleCell.setCellValue(titles[i]);
        int width = (int) (36 * len(titles[i]) * 15);
        columnWidth.put(i, width);
        sheet.setColumnWidth(i, width);
      }
    }
    return columnWidth;
  }

  public static Integer createXlsxExcelSheetData(XSSFSheet sheet, Excel excel, Map<Integer, Integer> columnWidth, List<String[]> contentList, XSSFCellStyle style) throws IOException {
    style = (XSSFCellStyle) style.clone();
    style.setWrapText(true);
    Integer rowNum = excel.getFieldsRowNum();
    Integer[] fieldWidths = excel.getFieldsWidth();
    Map<Integer, Double> total = new HashMap<>();
    int contentLen = 0;
    XSSFCellStyle style1 = (XSSFCellStyle) style.clone();
    XSSFColor color1 = new XSSFColor(new java.awt.Color(183, 230, 213));
    style1.setFillForegroundColor(color1); // 前景色
    style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    
    XSSFCellStyle style2 = (XSSFCellStyle) style.clone();
    XSSFColor color2 = new XSSFColor(new java.awt.Color(213, 230, 183));
    style2.setFillForegroundColor(color2); // 前景色
    style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    if (contentList != null && contentList.size() > 0) {
      XSSFRow contentRow = null;
      XSSFCell contentCell = null;
      for (int iRow = 0; iRow < contentList.size(); iRow++) {
        String[] content = contentList.get(iRow);
        if (content != null && content.length > 0) {
          contentLen = content.length;
          contentRow = sheet.createRow(rowNum ++);
          for (int iCell = 0; iCell < content.length; iCell++) {
            int w = (int) (35.7 * len(content[iCell]) * 15);
            if (null != fieldWidths) {
              sheet.setColumnWidth(iCell, fieldWidths[iCell]);
            }else {
              int ow = columnWidth.get(iCell);
              if (w > ow) {
                columnWidth.put(iCell, w);
                sheet.setColumnWidth(iCell, w);
              }
            }
            contentCell = null;
            contentCell = contentRow.createCell(iCell);
            contentCell.setCellValue(content[iCell]);
            contentCell.setCellStyle(iRow % 2 == 0 ? style1 : style2);
            if (null != excel.getColTotal()) {
              Integer[] cols = excel.getColTotal().getCols();
              for (Integer col : cols) {
                if (col - 1 == iCell) {
                  Double d = total.get(col);
                  if (null == d) {
                    d = 0.0;
                  }
                  d += Double.parseDouble(content[iCell]);
                  total.put(col, d);
                }
              }
            }
          }
        }
      }
    }
    if (null != excel.getColTotal()) {
      XSSFRow totalRow = sheet.createRow(rowNum ++);
      for (int i = 0; i < contentLen; i++) {
        XSSFCell cell = totalRow.createCell(i);
        cell.setCellStyle(style);
      }
      totalRow.getCell(0).setCellValue(excel.getColTotal().getCaption());
      Integer[] cols = excel.getColTotal().getCols();
      for (Integer col : cols) {
        totalRow.getCell(col - 1).setCellValue(total.get(col));
      }
    }
    return rowNum;
  }
  
  private static int len(String str) {
    if (null == str || str.length() == 0) {
      return 0;
    }
    int len = str.length();
    Pattern p = Pattern.compile("([\u4E00-\u9FA5])");
    Matcher m = p.matcher(str);
    int num = 0;
    while (m.find()) {
      num++;
    }
    len = (int) ((len - num) / 1.6) + num;
    if (len > 50) { // 最多只能50个汉字
      len = 50;
    }
    return len;
  }

  public static void createZipFile(OutputStream out, List<File> srcfile) throws Exception {
    ZipOutputStream zipOutputStream = new ZipOutputStream(out);
    byte[] buf = new byte[1024];
    for (int i = 0; i < srcfile.size(); i++) {
      File temp = srcfile.get(i);
      FileInputStream in = new FileInputStream(temp);
      zipOutputStream.putNextEntry(new ZipEntry(temp.getName()));
      int len;
      while ((len = in.read(buf)) > 0) {
        zipOutputStream.write(buf, 0, len);
      }
      zipOutputStream.closeEntry();
      in.close();
    }
    // 关闭输出流
    zipOutputStream.flush();
    zipOutputStream.close();
  }

  public static String createTempDirOnServer(HttpServletRequest request) {
    Date today = new Date();
    SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
    String serverPath = request.getSession().getServletContext().getRealPath("/");
    String path = serverPath + sdf3.format(today);
    // 在服务器端创建文件夹
    File file = new File(path);
    if (!file.exists()) {
      file.mkdir();
    }
    return path;
  }

  public static void downZipFile(HttpServletResponse response, String zipFileName, List<File> srcfile) throws IOException {
    zipFileName = URLEncoder.encode(zipFileName, "UTF-8");
    response.setContentType("application/octet-stream");
    response.setHeader("Connection", "close");
    response.setHeader("Accept-Ranges", "bytes");
    response.setHeader("Content-Disposition", "attachment; filename=" + zipFileName);
    OutputStream out = response.getOutputStream();
    ZipOutputStream zipOutputStream = new ZipOutputStream(out);
    byte[] buf = new byte[1024];
    for (int i = 0; i < srcfile.size(); i++) {
      File temp = srcfile.get(i);
      FileInputStream in = new FileInputStream(temp);
      zipOutputStream.putNextEntry(new ZipEntry(temp.getName()));
      int len;
      while ((len = in.read(buf)) > 0) {
        zipOutputStream.write(buf, 0, len);
      }
      zipOutputStream.closeEntry();
      in.close();
    }
    // 关闭输出流
    zipOutputStream.flush();
    zipOutputStream.close();
    out.close();
  }

  public static Workbook getWorkbook(byte[] bs, String filename) {
    Workbook workbook = null;
    if (null != bs && StringUtils.isNotBlank(filename)) {
      try {
        if (filename.endsWith(".xlsx")) {
          workbook = new XSSFWorkbook(new ByteArrayInputStream(bs));
        } else {
          workbook = new HSSFWorkbook(new ByteArrayInputStream(bs));
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return workbook;
  }
  
  public static String fullDate(String content, Date date) {
    String regex = "\\{date:(.*)\\}";
    Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(content);
    if (matcher.find()) {
      String now = DateUtils.formatDate(date, matcher.group(1));
      content = content.replaceAll(regex, now);
    }
    return content;
  }
  
  public static Excel fullExcel(Excel excel, Map<String, Object> datas) {
    Excel res = new Excel();
    if (null != excel && null != datas) {
      StandardEvaluationContext context = toStandardEvaluationContext(datas);
      res.setFileName(fullParams(excel.getFileName(), context));
      res.setSheetName(fullParams(excel.getSheetName(), context));
      res.setTableCaption(fullParams(excel.getTableCaption(), context));
      res.setFieldsRowNum(excel.getFieldsRowNum());
      res.setColTotal(excel.getColTotal());
      res.setFieldsWidth(excel.getFieldsWidth());
      List<RowData> rowDatas = excel.getRowDatas();
      List<RowData> rowDatas2 = new ArrayList<>();
      if (null != rowDatas && ! rowDatas.isEmpty()) {
        for (RowData rowData : rowDatas) {
          List<Map<String, String>> ds = rowData.getRows();
          List<Map<String, String>> ds2 = new ArrayList<>();
          if (null != ds) {
            for (int i = 0; i < ds.size(); i++) {
              Map<String, String> temp = new HashMap<>();
              temp.put("value", fullParams(ds.get(i).get("value"), context));
              temp.put("col", ds.get(i).get("col"));
              ds2.add(temp);
            }
          }
          rowDatas2.add(new RowData(rowData.getRowNum(), ds2));
        }
        res.setRowDatas(rowDatas2);
      }
      String[] fields = excel.getFields();
      String[] fields2 = new String[fields.length];
      if (null != fields) {
        for (int i = 0; i < fields.length; i++) {
          fields2[i] = fullParams(fields[i], context);
        }
        res.setFields(fields2);
      }
    }
    return res;
  }
  
  private static ExpressionParser parser = new SpelExpressionParser();
  
  public static String fullParams(String content, StandardEvaluationContext context) {
    if (StringUtils.isBlank(content) || content.indexOf("#") == -1) {
      return content;
    }
    Expression keyExp = parser.parseExpression(content);
    content = (String) keyExp.getValue(context);
    return content;
  }
  
  public static StandardEvaluationContext toStandardEvaluationContext(Map<String, Object> params) {
    StandardEvaluationContext context = new StandardEvaluationContext();
    if (null != params && ! params.isEmpty()) {
      for (Map.Entry<String, Object> entry : params.entrySet()) {
        context.setVariable(entry.getKey(), entry.getValue());
      }
    }
    return context;
  }
  
  public static String fullDateOfNow(String content) {
    String regex = "\\{date:(.*)\\}";
    Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(content);
    if (matcher.find()) {
      String now = DateUtils.formatNow(matcher.group(1));
      content = content.replaceAll(regex, now);
    }
    return content;
  }

  public static Map<Integer, Integer> createXlsxExcelSheetHead(XSSFSheet sheet, Excel excel, XSSFCellStyle style) {
    Map<Integer, Integer> columnWidth = new HashMap<>();
    String[] titles = excel.getFields();
    Integer[] fieldWidths = excel.getFieldsWidth();
    Integer titlesLen = null != titles ? (titles.length - 1) : 0;
    XSSFCellStyle style1 = (XSSFCellStyle) style.clone();
    String tableCaption = excel.getTableCaption();
    if (StringUtils.isNotBlank(tableCaption)) {
      XSSFRow xrow = sheet.createRow(0);
      XSSFCell cell = xrow.createCell(0);
      cell.setCellValue(tableCaption);
      cell.setCellStyle(style);
      for (int i = 1; i <= titlesLen; i++) {
        XSSFCell xc = xrow.createCell(i);
        xc.setCellStyle(style);
      }
      sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, titlesLen));
    }
    List<RowData> rowDatas = excel.getRowDatas();
    if (null != rowDatas && ! rowDatas.isEmpty()) {
      for (RowData rowData : rowDatas) {
        int r = rowData.getRowNum() - 1;
        XSSFRow xrow = sheet.createRow(r);
        for (int i = 0; i <= titlesLen; i++) {
          XSSFCell xc = xrow.createCell(i);
          xc.setCellStyle(style);
        }
        List<Map<String, String>> rd = rowData.getRows();
        int cbegin = 0;
        int cend = 0;
        for (int i = 0; i < rd.size(); i++) {
          Map<String, String> row = rd.get(i);
          String value = row.get("value");
          String col = row.get("col");
          if (i == rd.size() - 1 && i < titlesLen && StringUtils.isBlank(col)) {
            cend = titlesLen;
            sheet.addMergedRegion(new CellRangeAddress(r, r, cbegin, cend));// 开始行，结束行，开始列，结束列
          }
          if (StringUtils.isNotBlank(col)) {
            cend = cbegin + Integer.parseInt(col) - 1;
            sheet.addMergedRegion(new CellRangeAddress(r, r, cbegin, cend));
            cbegin = cend + 1;
          }
          XSSFCell cell = xrow.getCell(i);
          cell.setCellValue(value);
//          if (i != rd.size() - 1) {
//            int width = (int) (40 * len(value) * 20);
//            columnWidth.put(i, width);
//            sheet.setColumnWidth(i, width);
//          }
        }
      }
    }
    
    if (titles != null && titles.length > 0) {
      XSSFColor color = new XSSFColor(new java.awt.Color(226, 220, 220));
//      style1.setFillBackgroundColor(color);
//      style1.setFillPattern(FillPatternType.ALT_BARS);
      style1.setFillForegroundColor(color); // 前景色
      style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
      int row = null == excel.getFieldsRowNum() ? 0 : (excel.getFieldsRowNum() - 1);
      XSSFRow titleRow = sheet.createRow(row);
      XSSFCell titleCell = null;
      for (int i = 0; i < titles.length; i++) {
        titleCell = titleRow.createCell(i);
        titleCell.setCellValue(titles[i]);
        titleCell.setCellStyle(style1);
        if (null != fieldWidths) {
          sheet.setColumnWidth(i, fieldWidths[i]);
        }else {
          if (null == columnWidth.get(i)) {
            int width = (int) (40 * len(titles[i]) * 20);
            columnWidth.put(i, width);
            sheet.setColumnWidth(i, width);
          }
        }
      }
    }
    return columnWidth;
  }

}
