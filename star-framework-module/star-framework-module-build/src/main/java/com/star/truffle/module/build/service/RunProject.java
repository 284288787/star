/**create by liuhua at 2018年9月3日 上午9:52:33**/
package com.star.truffle.module.build.service;

import com.star.truffle.module.build.dto.Project;

public class RunProject {
  
  public static void main(String[] args) {
    Project project = new Project("F:/newframework/star-framework-module", "F:/newframework/star-framework-module", "alibaba", 12354, "0.0.4-MODULE");
    ProjectService projectService = new ProjectService();
    projectService.buildProject(project);
  }
}
