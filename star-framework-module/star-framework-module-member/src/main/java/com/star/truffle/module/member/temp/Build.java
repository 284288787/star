/**create by liuhua at 2018年8月16日 下午5:50:41**/
package com.star.truffle.module.member.temp;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.star.truffle.module.build.dto.Project;
import com.star.truffle.module.build.service.BuildFiles;

public class Build {

  public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
    Project project = new Project("F:/newframework/star-framework-module", "F:/newframework/star-framework-module", "member", 12351, "0.0.4-MODULE");
    String classpath = "com.star.truffle.module.memeber.temp.Member";
    String classpath2 = "com.star.truffle.module.memeber.temp.Distributor";
    BuildFiles.build(false, project, classpath, classpath2);
  }
}
