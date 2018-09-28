/**create by liuhua at 2018年8月16日 下午5:45:44**/
package com.star.truffle.module.build.service;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.star.truffle.module.build.dto.Project;

public class BuildFiles {

  public static void build(boolean createProject, Project project, String ... classpaths) throws JsonParseException, JsonMappingException, IOException {
    if (null != project && null != classpaths && classpaths.length > 0) {
      for (String classpath : classpaths) {
        new BuildFromClassPath(createProject, project, classpath);
      }
    }
  }
}
