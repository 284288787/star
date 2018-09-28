/**create by liuhua at 2018年8月16日 下午5:50:41**/
package com.star.truffle.module.alibaba.temp;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.star.truffle.module.build.dto.Project;
import com.star.truffle.module.build.service.BuildFiles;

public class Build {

  public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
    Project project = new Project("F:/newframework/star-framework-module", "F:/newframework/star-framework-module", "alibaba", 12354, "0.0.4-MODULE");
    String classpath = "com.star.truffle.module.memeber.temp.Member";
    BuildFiles.build(false, project, classpath);
  }
}
