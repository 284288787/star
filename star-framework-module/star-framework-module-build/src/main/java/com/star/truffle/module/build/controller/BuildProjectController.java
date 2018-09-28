/**create by liuhua at 2018年8月1日 上午9:39:28**/
package com.star.truffle.module.build.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.star.truffle.core.web.ApiResult;
import com.star.truffle.module.build.constant.DsType;
import com.star.truffle.module.build.dto.Entity;
import com.star.truffle.module.build.dto.Field;
import com.star.truffle.module.build.dto.FieldAdd;
import com.star.truffle.module.build.dto.FieldList;
import com.star.truffle.module.build.dto.FieldQuery;
import com.star.truffle.module.build.dto.Project;
import com.star.truffle.module.build.dto.Select;
import com.star.truffle.module.build.service.BeanService;
import com.star.truffle.module.build.service.DatabaseService;
import com.star.truffle.module.build.service.ProjectService;
import com.star.truffle.module.build.service.ThymeleafService;

@Controller
@RequestMapping("/project")
public class BuildProjectController {
  
  @Autowired
  private ProjectService projectService;
  @Autowired
  private BeanService beanService;
  @Autowired
  private ThymeleafService thymeleafService;
  @Autowired
  private DatabaseService databaseService;
  
  @RequestMapping(value = "/build", method = RequestMethod.GET)
  public String build(Model model){
    
    return "project/build";
  }
  
  @RequestMapping(value = "/buildField", method = RequestMethod.GET)
  public String buildField(Model model){
    
    return "project/buildField";
  }

  @ResponseBody
  @RequestMapping(value = "/buildFiles", method = RequestMethod.GET)
  public ApiResult<Void> buildFiles(){
    Project project = new Project("E:/star-framework-module", "E:/star-framework-module", "test", 12399, "0.0.1-SNAPSHOT");
    Entity entity = Entity.of("教练信息", "Coach", "t_coach", 1, 2, "480px", "400px", true, true);
    entity.setBtnAdd(true);
    entity.setBtnEdit(true);
    entity.setBtnEnabled(false);
    entity.setBtnDelete(true);
    
    List<Field> fields = new ArrayList<>();
    Field coachId = Field.ofLongPrimary("主键，id", "coachId", DsType.BIGINT, 20);
    FieldList coachIdList = FieldList.ofText("60");
    coachId.setFieldList(coachIdList);
    FieldAdd coachIdAdd = FieldAdd.ofTextHidden();
    coachId.setFieldEdit(coachIdAdd);
    
    Field name = Field.ofString("姓名", "name", DsType.VARCHAR, 20);
    FieldList nameList = FieldList.ofText("50");
    name.setFieldList(nameList);
    FieldQuery nameQuery = FieldQuery.ofText();
    name.setFieldQuery(nameQuery);
    FieldAdd nameAdd = FieldAdd.ofTextRequired("您的真实姓名", "必填", ".{2,5}", "2至5个字");
    name.setFieldAdd(nameAdd);
    name.setFieldEdit(nameAdd);
    
    Field sex = Field.ofInteger("性别", "sex", DsType.TINYINT, "SexEnum", "{\"val\":\"int\",\"caption\":\"String\"}", "{\"man\": {\"val\": 1, \"caption\": \"男\"}, \"woman\": {\"val\": 0, \"caption\": \"女\"}}");
//    Field sex = Field.ofInteger("性别", "sex", DsType.TINYINT, "SexEnum", null, "[\"man\", \"woman\"]");
    FieldList sexList = FieldList.ofSelect("50", "{value:'1:男;0:女'}");
    sex.setFieldList(sexList);
    FieldQuery sexQuery = FieldQuery.ofSelect(Select.build().addOption("1", "男").addOption("0", "女"));
    sex.setFieldQuery(sexQuery);
    FieldAdd sexAdd = FieldAdd.ofSelectRequired(Select.build().addOption("1", "男").addOption("0", "女"), "必选");
    sex.setFieldAdd(sexAdd);
    sex.setFieldEdit(sexAdd);
    
    Field mobile = Field.ofString("电话号码", "mobile", DsType.VARCHAR, 11);
    FieldList mobileList = FieldList.ofText("50");
    mobile.setFieldList(mobileList);
    FieldQuery mobileQuery = FieldQuery.ofText();
    mobile.setFieldQuery(mobileQuery);
    FieldAdd mobileAdd = FieldAdd.ofTextRequired("必填", "(13|14|15|16|17|18)[0-9]{9}", "需要正确的手机号");
    mobile.setFieldAdd(mobileAdd);
    mobile.setFieldEdit(mobileAdd);

    Field birth = Field.ofDate("生日", "birth");
    FieldList birthList = FieldList.ofDate("60", "Y-m-d");
    birth.setFieldList(birthList);
    FieldQuery birthQuery = FieldQuery.ofDate("Y-m-d");
    birth.setFieldQuery(birthQuery);
    FieldAdd birthAdd = FieldAdd.ofDateRequired("Y-m-d", "必选");
    birth.setFieldAdd(birthAdd);
    birth.setFieldEdit(birthAdd);
    
    Field createTime = Field.ofDate("创建时间", "createTime");
    FieldList createTimeList = FieldList.ofDate("60", "Y-m-d");
    createTime.setDefaultAddfieldValueType("java.util.Date");
    createTime.setDefaultAddfieldValue("new Date()");
    createTime.setFieldList(createTimeList);
    
    Field updateTime = Field.ofDate("更新时间", "updateTime");
    FieldList updateTimeList = FieldList.ofDate("60", "Y-m-d");
    updateTime.setDefaultEditfieldValueType("java.util.Date");
    updateTime.setDefaultEditfieldValue("new Date()");
    updateTime.setFieldList(updateTimeList);
    
    fields.add(coachId);
    fields.add(name);
    fields.add(sex);
    fields.add(mobile);
    fields.add(createTime);
    fields.add(updateTime);
    
    entity.setFields(fields);
    
    
    projectService.buildProject(project);

    beanService.createDomain(project, entity);
    beanService.createRequestDto(project, entity);
    beanService.createResponseDto(project, entity);
    beanService.createEnum(project, entity);
    beanService.createReadDao(project, entity);
    beanService.createReadMapper(project, entity);
    beanService.createWriteDao(project, entity);
    beanService.createWriteMapper(project, entity);
    beanService.createCache(project, entity);
    beanService.createService(project, entity);
    beanService.createApiController(project, entity);
    
    thymeleafService.createMgrController(project, entity);
    thymeleafService.createHtmlList(project, entity);
    thymeleafService.createJsList(project, entity);
    thymeleafService.createHtmlAdd(project, entity);
    thymeleafService.createJsAdd(project, entity);
    thymeleafService.createHtmlEdit(project, entity);
    thymeleafService.createJsEdit(project, entity);

    databaseService.createTable(entity);
    
    return ApiResult.success();
  }
}
