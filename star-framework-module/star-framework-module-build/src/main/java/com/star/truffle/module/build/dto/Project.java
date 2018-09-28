/**create by liuhua at 2018年8月1日 上午9:47:43**/
package com.star.truffle.module.build.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {

  private String staticPath;               //静态资源存放路径
  private String path;                     //项目路径
  private String name;                     //项目名称，必须全部小写，固定前缀为 star-framework-module-
  private Integer port;                    //端口号
  private String frameworkVersion;         //框架版本
  
  public String getInitial(){
    return name.substring(0, 1).toUpperCase() + name.substring(1);
  }
  
  public String getProjectPath(){
    return this.path + "/" + getProjectName();
  }
  
  public String getProjectName(){
    return "star-framework-module-" + this.name;
  }
  
  public String getBasicPackage(){
    return "com.star.truffle.module." + this.name;
  }
  
  public String getPackageCache(){
    return getBasicPackage() + ".cache";
  }
  
  public String getPackageConfig(){
    return getBasicPackage() + ".config";
  }
  
  public String getPackageConstant(){
    return getBasicPackage() + ".constant";
  }
  
  public String getPackageController(){
    return getBasicPackage() + ".controller";
  }
  
  public String getPackageDao(){
    return getBasicPackage() + ".dao";
  }
  
  public String getPackageDaoRead(){
    return getBasicPackage() + ".dao.read";
  }
  
  public String getPackageDaoWrite(){
    return getBasicPackage() + ".dao.write";
  }
  
  public String getPackageDomain(){
    return getBasicPackage() + ".domain";
  }
  
  public String getPackageDto(){
    return getBasicPackage() + ".dto";
  }
  
  public String getPackageRequestDto(){
    return getBasicPackage() + ".dto.req";
  }
  
  public String getPackageResponseDto(){
    return getBasicPackage() + ".dto.res";
  }
  
  public String getPackageProperties(){
    return getBasicPackage() + ".properties";
  }
  
  public String getPackageService(){
    return getBasicPackage() + ".service";
  }
  
  public String getBasicJavaFilePath(){
    return getProjectPath() + "/src/main/java/com/star/truffle/module/" + this.name;
  }
  
  public String getBasicResourcesFilePath(){
    return getProjectPath() + "/src/main/resources";
  }
  
  public String[] getJavaLeafPaths(){
    return new String[]{"cache", "config", "constant", "controller", "dao/read", "dao/write", "domain", "dto/req", "dto/res", "properties", "service"};
  }
  
  public String[] getResourcesLeafPaths(){
    return new String[]{"/mapper/" + this.name + "/read", "mapper/" + this.name + "/write", "templates/mgr"};
  }
  
  public String getBasicStaticPath(){
    return this.getBasicResourcesFilePath() + "/static";
  }
}
