/**create by liuhua at 2018年8月6日 上午9:35:23**/
package com.star.truffle.module.build.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.module.build.dto.Entity;
import com.star.truffle.module.build.dto.Field;
import com.star.truffle.module.build.dto.Project;
import com.star.truffle.module.build.utils.FreemarkerTemplateFactory;
import com.star.truffle.module.build.utils.Utils;

import freemarker.template.TemplateException;

@Service
public class ThymeleafService {

  private FreemarkerTemplateFactory factory = FreemarkerTemplateFactory.getInstance();
  
  private Field getPrimaryKey(List<Field> fields){
    return fields.stream().filter(field -> field.isPrimaryKey()).findFirst().orElseThrow(() -> new StarServiceException(ApiCode.PARAM_ERROR.getCode(), "必须设置一个主键，并且只能有一个"));
  }
  
  public void createMgrController(Project project, Entity entity) {
    String beanName = entity.getBeanName();
    String beanNameVar = Utils.firstToLower(beanName);
    String reqBeanName = beanName + "RequestDto";
    String resBeanName = beanName + "ResponseDto";
    String reqBeanNameVar = beanNameVar + "RequestDto";
    String resBeanNameVar = beanNameVar + "ResponseDto";
    String serviceVar = beanNameVar + "Service";
    
    List<Field> fields = entity.getFields();
    Field primary = getPrimaryKey(fields);
    
    StringBuffer service = new StringBuffer();
    service.append("/**create by framework at ").append(Utils.formatNow()).append("**/").append("\n");
    service.append("package ").append(project.getPackageController()).append(";\n\n");
    service.append("import java.util.HashMap;\n");
    service.append("import java.util.List;\n");
    service.append("import java.util.Map;\n");
    service.append("import org.springframework.beans.factory.annotation.Autowired;\n");
    service.append("import org.springframework.stereotype.Controller;\n");
    service.append("import org.springframework.ui.Model;\n");
    service.append("import org.springframework.web.bind.annotation.PathVariable;\n");
    service.append("import org.springframework.web.bind.annotation.RequestBody;\n");
    service.append("import org.springframework.web.bind.annotation.RequestMapping;\n");
    service.append("import org.springframework.web.bind.annotation.RequestMethod;\n");
    service.append("import org.springframework.web.bind.annotation.ResponseBody;\n");
    service.append("import com.star.truffle.core.StarServiceException;\n");
    service.append("import com.star.truffle.core.jdbc.Page;\n");
    service.append("import com.star.truffle.core.jdbc.Page.OrderType;\n");
    service.append("import com.star.truffle.core.web.ApiCode;\n");
    service.append("import com.star.truffle.core.web.ApiResult;\n");
    service.append("import lombok.extern.slf4j.Slf4j;\n");
    service.append("import " + project.getPackageDomain() + "." + beanName + ";\n");
    service.append("import " + project.getPackageService() + "." + beanName + "Service;\n");
    service.append("import " + project.getPackageRequestDto() + "." + reqBeanName + ";\n");
    service.append("import " + project.getPackageResponseDto() + "." + resBeanName + ";\n");
    
    StringBuffer body = new StringBuffer();
    body.append("\n@Slf4j\n");
    body.append("@Controller\n");
    body.append("@RequestMapping(\"/" + beanNameVar + "\")\n");
    body.append("public class " + beanName + "Controller {\n\n");
    body.append("  @Autowired\n");
    body.append("  private " + beanName + "Service " + serviceVar + ";\n\n");
    
    body.append("  @RequestMapping(value = \"/lists\", method = RequestMethod.GET)\n");
    body.append("  public String list() {\n");
    body.append("    return \"mgr/" + beanNameVar + "/list\";\n");
    body.append("  }\n\n");
    
    body.append("  @RequestMapping(value = \"/editBefore/{" + primary.getJavaName() + "}\", method = RequestMethod.GET)\n");
    body.append("  public String editBefore(@PathVariable " + primary.getJavaType() + " " + primary.getJavaName() + ", Model model) {\n");
    body.append("    " + resBeanName + " " + resBeanNameVar + " = this." + serviceVar + ".get" + beanName + "(" + primary.getJavaName() + ");\n");
    body.append("    model.addAttribute(\"" + resBeanNameVar + "\", " + resBeanNameVar + ");\n");
    body.append("    return \"mgr/" + beanNameVar + "/edit" + beanName + "\";\n");
    body.append("  }\n\n");
    
    body.append("  @ResponseBody\n");
    body.append("  @RequestMapping(value = \"/list\", method = {RequestMethod.POST, RequestMethod.GET})\n");
    body.append("  public Map<String, Object> list(" + reqBeanName + " " + reqBeanNameVar + ", Integer page, Integer rows, String sord, String sidx) {\n");
    body.append("    Page pager = new Page(page, rows, sidx, OrderType.desc.name().equals(sord) ? OrderType.desc : OrderType.asc);\n");
    body.append("    " + reqBeanNameVar + ".setPager(pager);\n");
    body.append("    List<" + resBeanName + "> list = " + serviceVar + ".query" + beanName + "(" + reqBeanNameVar + ");\n");
    body.append("    Long count = " + serviceVar + ".query" + beanName + "Count(" + reqBeanNameVar + ");\n\n");
    body.append("    Map<String, Object> map = new HashMap<>();\n");
    body.append("    map.put(\"page\", pager.getPageNum());\n");
    body.append("    map.put(\"total\", count % pager.getPageSize() == 0 ? count / pager.getPageSize() : count / pager.getPageSize() + 1);\n");
    body.append("    map.put(\"records\", count);\n");
    body.append("    map.put(\"rows\", list);\n");
    body.append("    return map;\n");
    body.append("  }\n\n");
    
    body.append("  @ResponseBody\n");
    body.append("  @RequestMapping(value = \"/add\", method = RequestMethod.POST)\n");
    body.append("  public ApiResult<Long> add(@RequestBody " + beanName + " " + beanNameVar + ") {\n");
    body.append("    try {\n");
    body.append("      Long id = " + serviceVar + ".save" + beanName + "(" + beanNameVar + ");\n");
    body.append("      return ApiResult.success(id);\n");
    body.append("    } catch (StarServiceException e) {\n");
    body.append("      return ApiResult.fail(e.getCode(), e.getMsg());\n");
    body.append("    } catch (Exception e) {\n");
    body.append("      log.error(e.getMessage(), e);\n");
    body.append("      return ApiResult.fail(ApiCode.SYSTEM_ERROR);\n");
    body.append("    }\n");
    body.append("  }\n\n");
    
    body.append("  @ResponseBody\n");
    body.append("  @RequestMapping(value = \"/edit\", method = RequestMethod.POST)\n");
    body.append("  public ApiResult<Void> edit(@RequestBody " + reqBeanName + " " + reqBeanNameVar + ") {\n");
    body.append("    try {\n");
    body.append("      " + serviceVar + ".update" + beanName + "(" + reqBeanNameVar + ");\n");
    body.append("      return ApiResult.success();\n");
    body.append("    } catch (StarServiceException e) {\n");
    body.append("      return ApiResult.fail(e.getCode(), e.getMsg());\n");
    body.append("    } catch (Exception e) {\n");
    body.append("      log.error(e.getMessage(), e);\n");
    body.append("      return ApiResult.fail(ApiCode.SYSTEM_ERROR);\n");
    body.append("    }\n");
    body.append("  }\n\n");
    
    body.append("  @ResponseBody\n");
    body.append("  @RequestMapping(value = \"/deleted\", method = RequestMethod.POST)\n");
    body.append("  public ApiResult<Void> delete(String ids) {\n");
    body.append("    try {\n");
    body.append("      " + serviceVar + ".delete" + beanName + "(ids);\n");
    body.append("      return ApiResult.success();\n");
    body.append("    } catch (StarServiceException e) {\n");
    body.append("      return ApiResult.fail(e.getCode(), e.getMsg());\n");
    body.append("    } catch (Exception e) {\n");
    body.append("      log.error(e.getMessage(), e);\n");
    body.append("      return ApiResult.fail(ApiCode.SYSTEM_ERROR);\n");
    body.append("    }\n");
    body.append("  }\n\n");
    
    body.append("}");
    
    String content = service.append(body).toString();
    Utils.writeFileContent(project.getBasicJavaFilePath() + "/controller/" + beanName + "Controller.java", content);
    System.out.println("controller.mgr: " + beanName + "Controller.java 创建完成");
  }

  public void createHtmlList(Project project, Entity entity) {
    String beanName = entity.getBeanName();
    String beanNameVar = Utils.firstToLower(beanName); 
    Map<String, Object> data = new HashMap<>();
    List<Field> fields = entity.getFields();
    List<Field> queryFields = new ArrayList<>();
    for (Field field : fields) {
      if (null != field.getFieldQuery()) {
        queryFields.add(field);
      }
    }
    data.put("title", entity.getCaption());
    data.put("fields", queryFields);
    data.put("entityNameVar", beanNameVar);
    data.put("entity", entity);
    
    String content = Utils.readFileContent("/static/html/list.txt");
    factory.addTemplate("htmllist", content);
    String html = "";
    try {
      html = factory.getTemplateInstance(data, "htmllist");
    } catch (IOException | TemplateException e) {
      e.printStackTrace();
    }
    
    Utils.writeFileContent(project.getBasicResourcesFilePath() + "/templates/mgr/" + beanNameVar + "/list.html", html);
    System.out.println("mgr/list.html: list.html 创建完成");
  }
  
  public void createJsList(Project project, Entity entity) {
    String beanName = entity.getBeanName();
    String beanNameVar = Utils.firstToLower(beanName); 
    Map<String, Object> data = new HashMap<>();
    List<Field> fields = entity.getFields();
    Field primary = getPrimaryKey(fields);
    List<Field> listFields = new ArrayList<>();
    for (Field field : fields) {
      if (null != field.getFieldList()) {
        listFields.add(field);
      }
    }
    data.put("entity", entity);
    data.put("title", entity.getCaption());
    data.put("fields", listFields);
    data.put("entityName", beanName);
    data.put("entityNameVar", beanNameVar);
    data.put("winWidth", entity.getWinWidth());
    data.put("winHeight", entity.getWinHeight());
    data.put("primaryKey", primary.getJavaName());
    
    String content = Utils.readFileContent("/static/js/list.txt");
    factory.addTemplate("jslist", content);
    String html = "";
    try {
      html = factory.getTemplateInstance(data, "jslist");
    } catch (IOException | TemplateException e) {
      e.printStackTrace();
    }
    
    Utils.writeFileContent(project.getBasicStaticPath() + "/js/mgr/" + beanNameVar + "/list.js", html);
    System.out.println("js.list: list.js 创建完成");
  }

  public void createHtmlAdd(Project project, Entity entity) {
    String beanName = entity.getBeanName();
    String beanNameVar = Utils.firstToLower(beanName); 
    Map<String, Object> data = new HashMap<>();
    List<Field> fields = entity.getFields();
    List<Field> addFields = new ArrayList<>();
    for (Field field : fields) {
      if (null != field.getFieldAdd()) {
        addFields.add(field);
      }
    }
    data.put("title", entity.getCaption());
    data.put("fields", addFields);
    data.put("entityNameVar", beanNameVar);
    data.put("entity", entity);
    
    String content = Utils.readFileContent("/static/html/add.txt");
    factory.addTemplate("htmladd", content);
    String html = "";
    try {
      html = factory.getTemplateInstance(data, "htmladd");
    } catch (IOException | TemplateException e) {
      e.printStackTrace();
    }
    
    Utils.writeFileContent(project.getBasicResourcesFilePath() + "/templates/mgr/" + beanNameVar + "/add" + beanName + ".html", html);
    System.out.println("mgr/add" + beanName + ".html: add" + beanName + ".html 创建完成");
  }

  public void createJsAdd(Project project, Entity entity) {
    String beanName = entity.getBeanName();
    String beanNameVar = Utils.firstToLower(beanName); 
    Map<String, Object> data = new HashMap<>();
    List<Field> fields = entity.getFields();
    Field primary = getPrimaryKey(fields);
    List<Field> addFields = new ArrayList<>();
    for (Field field : fields) {
      if (null != field.getFieldAdd()) {
        addFields.add(field);
      }
    }
    data.put("entity", entity);
    data.put("title", entity.getCaption());
    data.put("fields", addFields);
    data.put("entityName", beanName);
    data.put("entityNameVar", beanNameVar);
    data.put("winWidth", entity.getWinWidth());
    data.put("winHeight", entity.getWinHeight());
    data.put("primaryKey", primary.getJavaName());
    
    String content = Utils.readFileContent("/static/js/add.txt");
    factory.addTemplate("jsadd", content);
    String html = "";
    try {
      html = factory.getTemplateInstance(data, "jsadd");
    } catch (IOException | TemplateException e) {
      e.printStackTrace();
    }
    
    Utils.writeFileContent(project.getBasicStaticPath() + "/js/mgr/" + beanNameVar + "/add" + beanName + ".js", html);
    System.out.println("js.add: add" + beanName + ".js 创建完成");
  }

  public void createHtmlEdit(Project project, Entity entity) {
    String beanName = entity.getBeanName();
    String beanNameVar = Utils.firstToLower(beanName); 
    Map<String, Object> data = new HashMap<>();
    List<Field> fields = entity.getFields();
    Field primary = getPrimaryKey(fields);
    List<Field> editFields = new ArrayList<>();
    for (Field field : fields) {
      if (null != field.getFieldEdit()) {
        editFields.add(field);
      }
    }
    data.put("title", entity.getCaption());
    data.put("fields", editFields);
    data.put("entityNameVar", beanNameVar);
    data.put("primary", primary);
    data.put("entity", entity);
    
    String content = Utils.readFileContent("/static/html/edit.txt");
    factory.addTemplate("htmledit", content);
    String html = "";
    try {
      html = factory.getTemplateInstance(data, "htmledit");
    } catch (IOException | TemplateException e) {
      e.printStackTrace();
    }
    
    Utils.writeFileContent(project.getBasicResourcesFilePath() + "/templates/mgr/" + beanNameVar + "/edit" + beanName + ".html", html);
    System.out.println("mgr/edit" + beanName + ".html: edit" + beanName + ".html 创建完成");
  }

  public void createJsEdit(Project project, Entity entity) {
    String beanName = entity.getBeanName();
    String beanNameVar = Utils.firstToLower(beanName); 
    Map<String, Object> data = new HashMap<>();
    List<Field> fields = entity.getFields();
    Field primary = getPrimaryKey(fields);
    List<Field> addFields = new ArrayList<>();
    for (Field field : fields) {
      if (null != field.getFieldEdit()) {
        addFields.add(field);
      }
    }
    data.put("entity", entity);
    data.put("title", entity.getCaption());
    data.put("fields", addFields);
    data.put("entityName", beanName);
    data.put("entityNameVar", beanNameVar);
    data.put("winWidth", entity.getWinWidth());
    data.put("winHeight", entity.getWinHeight());
    data.put("primaryKey", primary.getJavaName());
    
    String content = Utils.readFileContent("/static/js/edit.txt");
    factory.addTemplate("jsedit", content);
    String html = "";
    try {
      html = factory.getTemplateInstance(data, "jsedit");
    } catch (IOException | TemplateException e) {
      e.printStackTrace();
    }
    
    Utils.writeFileContent(project.getBasicStaticPath() + "/js/mgr/" + beanNameVar + "/edit" + beanName + ".js", html);
    System.out.println("js.edit: edit" + beanName + ".js 创建完成");
  }
}
