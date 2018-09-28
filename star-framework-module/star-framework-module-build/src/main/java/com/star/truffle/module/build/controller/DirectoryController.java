/**create by liuhua at 2018年8月1日 上午9:39:28**/
package com.star.truffle.module.build.controller;

import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.star.truffle.module.build.utils.Utils;

@Controller
@RequestMapping("/directory")
public class DirectoryController {
  
  @RequestMapping(value = "/chooseDir", method = RequestMethod.GET)
  public String chooseDir(){
    return "project/chooseDir";
  }
  
  @ResponseBody
  @RequestMapping(value = "/dirlist", method = RequestMethod.POST)
  public List<Map<String, Object>> dirlist(String parent){
    List<Map<String, Object>> list = null;
    if (StringUtils.isBlank(parent)) {
      list = Utils.disks();
    } else {
      list = Utils.childDirs(parent);
    }
    return list;
  }
}
