/**create by liuhua at 2018年8月1日 上午9:44:31**/
package com.star.truffle.module.build.service;

import org.springframework.stereotype.Service;
import com.star.truffle.module.build.dto.Project;
import com.star.truffle.module.build.utils.Utils;

@Service
public class ProjectService {
      
  public void buildProject(Project project){
    buildDirs(project);
    buildJavaFiles(project);
    buildxmlFiles(project);
  }
  
  private void buildxmlFiles(Project project) {
    //pom
    String content = Utils.readFileContent("/static/xml/pom.txt");
    content = content.replace("{projectName}", project.getProjectName());
    content = content.replace("{frameworkVersion}", project.getFrameworkVersion());
    Utils.writeFileContent(project.getProjectPath() + "/pom.xml", content);
    
    //bootstrap
    content = Utils.readFileContent("/static/xml/bootstrap.txt");
    content = content.replace("{port}", project.getPort().toString());
    content = content.replace("{projectName}", project.getProjectName());
    Utils.writeFileContent(project.getBasicResourcesFilePath() + "/bootstrap.yml", content);
  }

  private void buildJavaFiles(Project project) {
    //application
    String content = Utils.readFileContent("/static/java/application.txt");
    content = content.replace("{now}", Utils.formatNow());
    content = content.replace("{package}", project.getBasicPackage());
    content = content.replace("{moduleName}", project.getInitial());
    Utils.writeFileContent(project.getBasicJavaFilePath() + "/" + project.getInitial() + "Application.java", content);
    
    //enableModule
    content = Utils.readFileContent("/static/java/enableModule.txt");
    content = content.replace("{now}", Utils.formatNow());
    content = content.replace("{package}", project.getPackageConfig());
    content = content.replace("{moduleName}", project.getInitial());
    Utils.writeFileContent(project.getBasicJavaFilePath() + "/config/EnableModule" + project.getInitial() + ".java", content);
    
    //moduleConfig
    content = Utils.readFileContent("/static/java/moduleConfig.txt");
    content = content.replace("{now}", Utils.formatNow());
    content = content.replace("{package}", project.getPackageConfig());
    content = content.replace("{moduleName}", project.getInitial());
    content = content.replace("{daoPackage}", project.getPackageDao());
    content = content.replace("{cachePackage}", project.getPackageCache());
    content = content.replace("{servicePackage}", project.getPackageService());
    content = content.replace("{controllerPackage}", project.getPackageController());
    content = content.replace("{propertiesPackage}", project.getPackageProperties());
    Utils.writeFileContent(project.getBasicJavaFilePath() + "/config/" + project.getInitial() + "ModuleConfig.java", content);
  }

  private void buildDirs(Project project){
    String[] javaLeafPaths = project.getJavaLeafPaths();
    for (String path : javaLeafPaths) {
      Utils.createDirs(project.getBasicJavaFilePath() + "/" + path);
    }
    String[] resourcesLeafPaths = project.getResourcesLeafPaths();
    for (String path : resourcesLeafPaths) {
      Utils.createDirs(project.getBasicResourcesFilePath() + "/" + path);
    }
  }
}
