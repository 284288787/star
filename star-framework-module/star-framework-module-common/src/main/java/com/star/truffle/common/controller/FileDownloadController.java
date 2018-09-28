/**create by liuhua at 2018年8月13日 上午10:27:39**/
package com.star.truffle.common.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.star.truffle.common.dto.ExcelExportParam;
import com.star.truffle.common.importdata.ExcelUtil;
import com.star.truffle.common.properties.Excel;
import com.star.truffle.common.properties.ExcelTemplate;
import com.star.truffle.common.service.ExcelService;
import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.core.util.ParamHandler;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.core.web.ApiResult;

@RestController
@RequestMapping("/download")
public class FileDownloadController {

  @Autowired
  private StarJson starJson;
  @Autowired
  private ExcelTemplate excelTemplate;
  @Autowired
  private ExcelService excelService;
  
  @RequestMapping(value = "/excel/data", method = RequestMethod.GET)
  public void exportData(String params, HttpServletResponse response) throws IOException {
    try {
      ParamHandler paramHandler = ParamHandler.ofParam(params, starJson);
      List<ExcelExportParam> eeps = new ArrayList<>();
      String key = paramHandler.getString("key");
      if (key.indexOf(",") != -1) {
        String[] keys = key.split(",");
        String handle = paramHandler.getString("handle");
        String[] handles = handle.split(",");
        if (keys.length != handles.length) {
          ApiResult<Void> apiResult = ApiResult.fail(ApiCode.PARAM_ERROR.getCode(), "参数错误");
          response.setContentType("application/json;charset=UTF-8");
          response.getWriter().write(starJson.obj2string(apiResult));
          response.getWriter().flush();
          return;
        }
        for (int i = 0; i < keys.length; i ++) {
          Excel excel = excelTemplate.getExcel().get(keys[i]);
          if (null == excel) {
            ApiResult<Void> apiResult = ApiResult.fail(ApiCode.PARAM_ERROR.getCode(), "模板[" + keys[i] + "]不存在");
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(starJson.obj2string(apiResult));
            response.getWriter().flush();
            return;
          }
          Map<String, Object> param = paramHandler.getMapExcept("key", "handle");
          ExcelExportParam eep = ExcelExportParam.of(handles[i], excel, param);
          eeps.add(eep);
        }
      } else {
        Excel excel = excelTemplate.getExcel().get(key);
        if (null == excel) {
          ApiResult<Void> apiResult = ApiResult.fail(ApiCode.PARAM_ERROR.getCode(), "模板不存在");
          response.setContentType("application/json;charset=UTF-8");
          response.getWriter().write(starJson.obj2string(apiResult));
          response.getWriter().flush();
          return;
        }
        String handle = paramHandler.getString("handle");
        Map<String, Object> param = paramHandler.getMapExcept("key", "handle");
        ExcelExportParam eep = ExcelExportParam.of(handle, excel, param);
        eeps.add(eep);
      }
      
      excelService.exportExcel(eeps, response);
    } catch (StarServiceException e) {
      e.printStackTrace();
      ApiResult<Void> apiResult = ApiResult.fail(e.getCode(), e.getMsg());
      response.setContentType("application/json;charset=UTF-8");
      response.getWriter().write(starJson.obj2string(apiResult));
      response.getWriter().flush();
    } catch (Exception e) {
      e.printStackTrace();
      ApiResult<Void> apiResult = ApiResult.fail(ApiCode.SYSTEM_ERROR);
      response.setContentType("application/json;charset=UTF-8");
      response.getWriter().write(starJson.obj2string(apiResult));
      response.getWriter().flush();
    }
  }
  
  @RequestMapping(value = "/excel/template/{key}", method = RequestMethod.GET)
  public void excelTemplate(@PathVariable String key, HttpServletResponse response) throws IOException {
    try {
      Excel excel = excelTemplate.getExcel().get(key);
      if (null == excel) {
        ApiResult<Void> apiResult = ApiResult.fail(ApiCode.PARAM_ERROR.getCode(), "模板不存在");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(starJson.obj2string(apiResult));
        response.getWriter().flush();
        return;
      }
      OutputStream os = response.getOutputStream();
      response.setContentType("application/vnd.ms-excel");
      String filename = ExcelUtil.excelFileName(excel.getFileName()) + ".xlsx";
      filename = URLEncoder.encode(filename, "UTF-8");
      response.setHeader("Content-Disposition", "attachment; filename=" + filename);
      String[] titles = excel.getFields();
      List<String[]> datas = new ArrayList<>();
      ExcelUtil.createXlsxExcelFile2(os, excel.getSheetName(), titles, datas);
    } catch (IOException e) {
      e.printStackTrace();
      ApiResult<Void> apiResult = ApiResult.fail(ApiCode.SYSTEM_ERROR.getCode(), e.getMessage());
      response.setContentType("application/json;charset=UTF-8");
      response.getWriter().write(starJson.obj2string(apiResult));
      response.getWriter().flush();
    } catch (Exception e) {
      e.printStackTrace();
      ApiResult<Void> apiResult = ApiResult.fail(ApiCode.SYSTEM_ERROR);
      response.setContentType("application/json;charset=UTF-8");
      response.getWriter().write(starJson.obj2string(apiResult));
      response.getWriter().flush();
    }
  }
  
  @RequestMapping(value = "/excel/templates/{keys}", method = RequestMethod.GET)
  public void excelTemplates(@PathVariable String keys, HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
      String path = ExcelUtil.createTempDirOnServer(request);
      List<File> srcfile = new ArrayList<File>();
      String[] ks = keys.split(",");
      for (String key : ks) {
        Excel excel = excelTemplate.getExcel().get(key);
        if (null != excel) {
          String[] titles = excel.getFields();
          List<String[]> datas = new ArrayList<>();
          String filename = ExcelUtil.excelFileName(excel.getFileName()) + ".xlsx";
          String filePath = path + File.separator + filename;
          File xlsFile = new File(filePath);
          FileOutputStream fileOutputStream = new FileOutputStream(xlsFile);
          ExcelUtil.createXlsxExcelFile2(fileOutputStream, excel.getSheetName(), titles, datas);
          srcfile.add(xlsFile);
        }
      }
      ExcelUtil.downZipFile(response, "templates.zip", srcfile);
    } catch (IOException e) {
      e.printStackTrace();
      ApiResult<Void> apiResult = ApiResult.fail(ApiCode.SYSTEM_ERROR.getCode(), e.getMessage());
      response.setContentType("application/json;charset=UTF-8");
      response.getWriter().write(starJson.obj2string(apiResult));
      response.getWriter().flush();
    } catch (Exception e) {
      e.printStackTrace();
      ApiResult<Void> apiResult = ApiResult.fail(ApiCode.SYSTEM_ERROR);
      response.setContentType("application/json;charset=UTF-8");
      response.getWriter().write(starJson.obj2string(apiResult));
      response.getWriter().flush();
    }
  }

}
