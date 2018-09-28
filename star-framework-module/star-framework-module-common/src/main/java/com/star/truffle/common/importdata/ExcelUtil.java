package com.star.truffle.common.importdata;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
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
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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

  public static void createXlsxExcelSheetData(XSSFSheet sheet, Map<Integer, Integer> columnWidth, List<String[]> contentList) throws IOException {
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
  
  public static String excelFileName(String name) {
    String regex = "\\{date:(.*)\\}";
    Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(name);
    if (matcher.find()) {
      String now = DateUtils.formatNow(matcher.group(1));
      name = name.replaceAll(regex, now);
    }
    return name;
  }
}
