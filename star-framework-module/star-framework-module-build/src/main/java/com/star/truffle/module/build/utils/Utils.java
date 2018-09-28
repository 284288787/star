/** create by liuhua at 2018年8月2日 上午9:05:28 **/
package com.star.truffle.module.build.utils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

public class Utils {
  private static ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();

  // 转变的依赖字符
  public static final char UNDERLINE = '_';

  /**
   * 将驼峰转换成"_"(userId:user_id)
   * 
   * @param param
   * @return
   */
  public static String camelToUnderline(String param) {
    if (param == null || "".equals(param.trim())) {
      return "";
    }
    int len = param.length();
    StringBuilder sb = new StringBuilder(len);
    for (int i = 0; i < len; i++) {
      char c = param.charAt(i);
      if (Character.isUpperCase(c)) {
        sb.append(UNDERLINE);
        sb.append(Character.toLowerCase(c));
      } else {
        sb.append(c);
      }
    }
    return sb.toString();
  }

  /**
   * 将"_"转成驼峰(user_id:userId)
   * 
   * @param param
   * @return
   */
  public static String underlineToCamel(String param) {
    if (param == null || "".equals(param.trim())) {
      return "";
    }
    StringBuilder sb = new StringBuilder(param);
    Matcher mc = Pattern.compile(UNDERLINE + "").matcher(param);
    int i = 0;
    while (mc.find()) {
      int position = mc.end() - (i++);
      String.valueOf(Character.toUpperCase(sb.charAt(position)));
      sb.replace(position - 1, position + 1, sb.substring(position, position + 1).toUpperCase());
    }
    return sb.toString();
  }
  
  public static String firstToLower(String name){
    return name.substring(0, 1).toLowerCase() + name.substring(1);
  }

  public static String firstToUpper(String name){
    return name.substring(0, 1).toUpperCase() + name.substring(1);
  }
  
  public static String formatNow() {
    return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss"));
  }

  public static void createDirs(String path) {
    File file = new File(path);
    if (!file.exists()) {
      file.mkdirs();
    }
  }

  public static String readFileContent(String path) {
    String content = null;
    try {
      Resource resource = resourceResolver.getResource(path);
      content = FileUtils.readFileToString(new File(resource.getURI()), "UTF-8");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return content;
  }

  public static void writeFileContent(String path, String content) {
    try {
      System.out.println(path);
      FileUtils.write(new File(path), content, "UTF-8");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public static List<Map<String, Object>> disks(){
    List<Map<String, Object>> list = new ArrayList<>();
    File[] root = File.listRoots();
    for (File file : root) {
      Map<String, Object> map = new HashMap<>();
      map.put("id", file.getPath());
      map.put("pId", file.getParent());
      map.put("name", file.getPath());
      map.put("path", file.getPath().replace("\\", "/"));
      map.put("isParent", true);
      list.add(map);
    }
    return list;
  }
  
  public static List<Map<String, Object>> childDirs(String parentPath){
    parentPath = parentPath.replace("\\", "/");
    List<Map<String, Object>> list = new ArrayList<>();
    File parent = new File(parentPath);
    if (parent.exists() && parent.isDirectory()) {
      String[] str = parent.list((file, name) -> {
        if (name.indexOf(" ") != -1 || name.indexOf("$") != -1 || name.indexOf(".") != -1) {
          return false;
        }
        boolean bool = Pattern.matches("[a-zA-Z0-9_-]+", name);
        File temp = new File(file.getPath() + "/" + name);
        return !temp.isHidden() && temp.isDirectory() && bool;
      });
      if (null == str) {
        return list;
      }
      for (String dir : str) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", dir);
        map.put("pId", parentPath);
        map.put("name", dir);
        map.put("path", parentPath + dir + "/");
        map.put("isParent", true);
        list.add(map);
      }
    }
    return list;
  }
}
