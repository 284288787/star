/**create by liuhua at 2018年8月13日 上午10:27:39**/
package com.star.truffle.common.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.star.truffle.common.importdata.ImportResult;
import com.star.truffle.common.service.ExcelService;
import com.star.truffle.common.utils.ImageShrink;
import com.star.truffle.common.utils.ImageWaterMark;
import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.core.web.ApiResult;
import com.star.truffle.core.web.config.StarSpringMvcProperties;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

@RestController
@RequestMapping("/upload")
public class FileUploadController {

  @Autowired
  private StarSpringMvcProperties starSpringMvcProperties;
  @Autowired
  private ExcelService excelService;
  
  @RequestMapping(value = "/images", method = RequestMethod.POST)
  public ApiResult<List<Map<String, String>>> images(MultipartHttpServletRequest multipartRequest) {
    ApiResult<List<Map<String, String>>> apiResult = null;
    String mark = multipartRequest.getParameter("mark");      //水印
    String shrink = multipartRequest.getParameter("shrink");  //压缩
    try {
      List<Map<String, String>> list = new ArrayList<>();
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
        MultipartFile mf = entity.getValue();
        byte[] bs = mf.getBytes();
        if (bs == null || bs.length == 0) {
          throw new StarServiceException(ApiCode.PARAM_ERROR.getCode(), "上传失败");
        } else {
//          String realPath = "e:/upload";
          String realPath = starSpringMvcProperties.getPhotoPath() + "image/";
//          String realPath = "/home/tomcat/upload/photo";
          File dir = new File(realPath);
          if (!dir.exists()) {
            dir.mkdirs();
          }
          Map<String, String> item = new HashMap<>();
          String name = UUID.randomUUID().toString().replace("-", "");
          String suffix = ImageWaterMark.getSuffix(mf.getOriginalFilename());
          String fileName = name + "." + suffix;
          FileUtils.writeByteArrayToFile(new File(dir, fileName), bs);
          FileUtils.write(new File(dir, name + ".name"), mf.getOriginalFilename(), "UTF-8"); //保留原文件名
//          item.put("original", "/photo/image/" + name + suffix);
          Thumbnails.of(realPath + fileName).scale(1f)
          .outputQuality(1f)
          .outputFormat(suffix)
          .toFile(realPath + name + "." + suffix);
          item.put("original", "/photo/image/" + name + "." + suffix);
          if (StringUtils.isNotBlank(mark) && "1".equals(mark)) {
//            ImageWaterMark.addWaterMarkImage(realPath, fileName, starSpringMvcProperties.getPhotoPath() + "shuiyin.png", ImageWaterMark.HORIZONTAL_ALIGN_RIGHT, ImageWaterMark.VERTICAL_ALIGN_CENTER);
//            item.put("mark", "/photo/image/" + name + ImageWaterMark.WATER_MARK_SUFFIX + suffix);
            Thumbnails.of(realPath + fileName).scale(1f)
            .watermark(Positions.CENTER_RIGHT, ImageIO.read(new File(starSpringMvcProperties.getPhotoPath() + "shuiyin.png")), 0.5f)
            .outputQuality(1f)
            .outputFormat(suffix)
            .toFile(realPath + name + ImageWaterMark.WATER_MARK_SUFFIX + "." + suffix);
            item.put("mark", "/photo/image/" + name + ImageWaterMark.WATER_MARK_SUFFIX + "." + suffix);
          }
          if (StringUtils.isNotBlank(shrink) && "1".equals(shrink)) {
//            ImageShrink.shrink(realPath, fileName, 300);
//            item.put("shrink", "/photo/image/" + name + ImageShrink.SHRINK_SUFFIX + suffix);
            Thumbnails.of(realPath + fileName).scale(0.25f)
            .outputQuality(1f)
            .outputFormat(suffix)
            .toFile(realPath + name + ImageShrink.SHRINK_SUFFIX + "." + suffix);
            item.put("shrink", "/photo/image/" + name + ImageShrink.SHRINK_SUFFIX + "." + suffix);
          }
          list.add(item);
        }
      }
      apiResult = ApiResult.success(list);
    } catch (StarServiceException e) {
      apiResult = ApiResult.fail(e.getCode(), e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      apiResult = ApiResult.fail(ApiCode.SYSTEM_ERROR.getCode(), e.getMessage());
    }
    return apiResult;
  }

  @RequestMapping(value = "/excels", method = RequestMethod.POST)
  public ApiResult<List<List<ImportResult>>> excels(MultipartHttpServletRequest multipartRequest) {
    ApiResult<List<List<ImportResult>>> apiResult = null;
    try {
      List<List<ImportResult>> list = new ArrayList<>();
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
        MultipartFile mf = entity.getValue();
        byte[] bs = mf.getBytes();
        if (bs == null || bs.length == 0) {
          throw new StarServiceException(ApiCode.PARAM_ERROR.getCode(), "上传失败");
        } else {
          String handle = multipartRequest.getParameter("handle");           //最终结果处理类
          String errorImport = multipartRequest.getParameter("errorImport"); //当遇到错误后，是否保存
          List<ImportResult> o = this.excelService.importExcel(bs, mf.getName(), handle, errorImport);
          list.add(o);
        }
      }
      apiResult = ApiResult.success(list);
    } catch (StarServiceException e) {
      apiResult = ApiResult.fail(e.getCode(), e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      apiResult = ApiResult.fail();
    }
    return apiResult;
  }
}
