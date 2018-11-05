/**create by liuhua at 2018年8月13日 上午10:27:39**/
package com.star.truffle.common.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.star.truffle.common.utils.ImageWaterMark;
import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.core.web.config.StarSpringMvcProperties;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

@RestController
@RequestMapping("/kindeditor")
public class KindeditorController {

  @Autowired
  private StarSpringMvcProperties starSpringMvcProperties;
  @Autowired
  private StarJson starJson;

  @RequestMapping(value = "/upload", method = RequestMethod.POST)
  public String upload(MultipartHttpServletRequest multipartRequest) {
    // 文件保存目录路径
//    String savePath = "e:/upload/";
    String savePath = starSpringMvcProperties.getPhotoPath();
    // 文件保存目录URL
    String osName = System.getProperty("os.name");
    String saveUrl = "http://mgr.hnkbmd.com/photo/";
    if (osName.toLowerCase().indexOf("win") != -1) {
      saveUrl = multipartRequest.getScheme() + "://" + multipartRequest.getServerName() + ":"+multipartRequest.getServerPort() + "/photo/";
    }
    // 定义允许上传的文件扩展名
    Map<String, String> extMap = new HashMap<String, String>();
    extMap.put("image", "gif,jpg,jpeg,png,bmp");
    extMap.put("flash", "swf,flv");
    extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb,mp4");
    extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");
    String dirName = multipartRequest.getParameter("dir");
    savePath += dirName + "/";
    saveUrl += dirName + "/";
    // 最大文件大小 2m
    long maxSize = 2097152;
    // 创建文件夹
    File saveDirFile = new File(savePath);
    if (!saveDirFile.exists()) {
      saveDirFile.mkdirs();
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    String ymd = sdf.format(new Date());
    savePath += ymd + "/";
    saveUrl += ymd + "/";
    File dirFile = new File(savePath);
    if (!dirFile.exists()) {
      dirFile.mkdirs();
    }
    try {
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
        MultipartFile mf = entity.getValue();
        byte[] bs = mf.getBytes();
        if (bs == null || bs.length == 0) {
          throw new StarServiceException(ApiCode.PARAM_ERROR.getCode(), "上传失败");
        } else {
          String fileName = mf.getOriginalFilename();
          // 检查文件大小
          if (mf.getSize() > maxSize) {
            String message = "上传文件大小不得超过" + maxSize / 1024 + "K(2M)。";
            return message;
          }
          // 检查扩展名
          String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
          if (!Arrays.<String> asList(extMap.get(dirName).split(",")).contains(fileExt)) {
            String message = "上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。";
            return message;
          }
          String name = UUID.randomUUID().toString().replace("-", "");
          String newFileName = name + "." + fileExt;
          FileUtils.writeByteArrayToFile(new File(savePath, newFileName), bs);
          FileUtils.write(new File(savePath, name + ".name"), mf.getOriginalFilename(), "UTF-8"); // 保留原文件名
          Thumbnails.of(savePath + newFileName).scale(1f)
          .watermark(Positions.CENTER_RIGHT, ImageIO.read(new File(starSpringMvcProperties.getPhotoPath() + "shuiyin.png")), 0.5f)
          .outputQuality(0.8f)
          .outputFormat(fileExt)
          .toFile(savePath + name + ImageWaterMark.WATER_MARK_SUFFIX + "." + fileExt);
//          ImageWaterMark.addWaterMarkImage(savePath, newFileName, starSpringMvcProperties.getPhotoPath() + "shuiyin.png", ImageWaterMark.HORIZONTAL_ALIGN_RIGHT, ImageWaterMark.VERTICAL_ALIGN_CENTER);
          return "{\"error\": 0, \"url\": \"" + saveUrl + name + ImageWaterMark.WATER_MARK_SUFFIX + "."+fileExt+"\"}";
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "";
  }

  @RequestMapping(value = "/manager")
  public String manager(HttpServletRequest request) {
    // 根目录路径，可以指定绝对路径，比如 /var/www/attached/
//    String rootPath = "e:/upload/";
    String rootPath = starSpringMvcProperties.getPhotoPath();
    // 根目录URL，可以指定绝对路径，比如 http://www.yoursite.com/attached/
    String osName = System.getProperty("os.name");
    String rootUrl = "http://mgr.hnkbmd.com/photo/";
    if (osName.toLowerCase().indexOf("win") != -1) {
      rootUrl = request.getScheme() + "://" + request.getServerName() + ":"+request.getServerPort() + "/photo/";
    }
    // 图片扩展名
    String[] fileTypes = new String[] { "gif", "jpg", "jpeg", "png", "bmp" };
    String dirName = request.getParameter("dir");
    if (dirName != null) {
      if (!Arrays.<String> asList(new String[] { "image", "flash", "media", "file" }).contains(dirName)) {
        return "Invalid Directory name.";
      }
      rootPath += dirName + "/";
      rootUrl += dirName + "/";
      File saveDirFile = new File(rootPath);
      if (!saveDirFile.exists()) {
        saveDirFile.mkdirs();
      }
    }
    // 根据path参数，设置各路径和URL
    String path = request.getParameter("path") != null ? request.getParameter("path") : "";
    String currentPath = rootPath + path;
    String currentUrl = rootUrl + path;
    String currentDirPath = path;
    String moveupDirPath = "";
    if (!"".equals(path)) {
      String str = currentDirPath.substring(0, currentDirPath.length() - 1);
      moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0, str.lastIndexOf("/") + 1) : "";
    }

    // 排序形式，name or size or type
    String order = request.getParameter("order") != null ? request.getParameter("order").toLowerCase() : "name";

    // 不允许使用..移动到上一级目录
    if (path.indexOf("..") >= 0) {
      return "没有权限移动到上级目录";
    }
    // 最后一个字符不是/
    if (!"".equals(path) && !path.endsWith("/")) {
      return "Parameter is not valid.";
    }
    // 目录不存在或不是目录
    File currentPathFile = new File(currentPath);
    if (!currentPathFile.isDirectory()) {
      return "Directory does not exist.";
    }

    // 遍历目录取的文件信息
    List<Map<String, Object>> fileList = new ArrayList<>();
    if (currentPathFile.listFiles() != null) {
      for (File file : currentPathFile.listFiles()) {
        Map<String, Object> hash = new HashMap<>();
        String fileName = file.getName();
        if (file.isDirectory()) {
          hash.put("is_dir", true);
          hash.put("has_file", (file.listFiles() != null));
          hash.put("filesize", 0L);
          hash.put("is_photo", false);
          hash.put("filetype", "");
        } else if (file.isFile()) {
          String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
//          if (! fileName.endsWith(ImageShrink.SHRINK_SUFFIX + ".") && ! fileName.endsWith(ImageWaterMark.WATER_MARK_SUFFIX + ".")) {
//            continue;
//          }
          boolean ext = Arrays.<String> asList(fileTypes).contains(fileExt);
          hash.put("is_dir", false);
          hash.put("has_file", false);
          hash.put("filesize", file.length());
          hash.put("is_photo", ext);
          hash.put("filetype", fileExt);
        }
        hash.put("filename", fileName);
        hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
        fileList.add(hash);
      }
    }

    if ("size".equals(order)) {
      fileList.sort((a, b) -> {
        if (((Boolean) a.get("is_dir")) && !((Boolean) b.get("is_dir"))) {
          return -1;
        } else if (!((Boolean) a.get("is_dir")) && ((Boolean) b.get("is_dir"))) {
          return 1;
        } else {
          if (((Long) a.get("filesize")) > ((Long) b.get("filesize"))) {
            return 1;
          } else if (((Long) a.get("filesize")) < ((Long) b.get("filesize"))) {
            return -1;
          } else {
            return 0;
          }
        }
      });
    } else if ("type".equals(order)) {
      fileList.sort((a, b) -> {
        if (((Boolean) a.get("is_dir")) && !((Boolean) b.get("is_dir"))) {
          return -1;
        } else if (!((Boolean) a.get("is_dir")) && ((Boolean) b.get("is_dir"))) {
          return 1;
        } else {
          return ((String) a.get("filetype")).compareTo((String) b.get("filetype"));
        }
      });
    } else {
      fileList.sort((a, b) -> {
        if (((Boolean) a.get("is_dir")) && !((Boolean) b.get("is_dir"))) {
          return -1;
        } else if (!((Boolean) a.get("is_dir")) && ((Boolean) b.get("is_dir"))) {
          return 1;
        } else {
          return ((String) a.get("filename")).compareTo((String) b.get("filename"));
        }
      });
    }

    Map<String, Object> result = new HashMap<>();
    result.put("moveup_dir_path", moveupDirPath);
    result.put("current_dir_path", currentDirPath);
    result.put("current_url", currentUrl);
    result.put("total_count", fileList.size());
    result.put("file_list", fileList);
    return starJson.obj2string(result);
  }
}