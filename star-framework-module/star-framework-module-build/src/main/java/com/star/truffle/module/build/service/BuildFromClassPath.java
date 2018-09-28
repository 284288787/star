/**create by liuhua at 2018年8月16日 上午11:28:58**/
package com.star.truffle.module.build.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.star.truffle.core.util.ClassUtils;
import com.star.truffle.module.build.annotation.StarDomainName;
import com.star.truffle.module.build.annotation.StarField;
import com.star.truffle.module.build.annotation.StarFieldAdd;
import com.star.truffle.module.build.annotation.StarFieldEdit;
import com.star.truffle.module.build.annotation.StarFieldList;
import com.star.truffle.module.build.annotation.StarFieldQuery;
import com.star.truffle.module.build.constant.DsType;
import com.star.truffle.module.build.constant.InputType;
import com.star.truffle.module.build.constant.TextAlign;
import com.star.truffle.module.build.dto.Entity;
import com.star.truffle.module.build.dto.Field;
import com.star.truffle.module.build.dto.FieldAdd;
import com.star.truffle.module.build.dto.FieldList;
import com.star.truffle.module.build.dto.FieldQuery;
import com.star.truffle.module.build.dto.Project;
import com.star.truffle.module.build.dto.Select;
import com.star.truffle.module.build.utils.Utils;

public class BuildFromClassPath {

//  public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
//    Project project = new Project("D:/newframework/star-framework-module", "D:/newframework/star-framework-module", "school", 12398, "0.0.2-MODULE");
//    String classpath = "com.star.truffle.module.build.dto.Student";
//  }
  
  protected BuildFromClassPath(boolean createProject, Project project, String classpath) throws JsonParseException, JsonMappingException, IOException {
    Class<?> clazz = ClassUtils.getClass(classpath);
    Entity entity = createEntity(classpath);
    List<Field> fields = createFields(clazz, entity);
    entity.setFields(fields);
    
    ProjectService projectService = new ProjectService();
    BeanService beanService = new BeanService();
    ThymeleafService thymeleafService = new ThymeleafService();
    DatabaseService databaseService = new DatabaseService();
    
    if (createProject) {
      projectService.buildProject(project);
    }
    
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
  }
  
  private ObjectMapper objectMapper = new ObjectMapper();
  
  public List<Field> createFields(Class<?> clazz, Entity entity) throws JsonParseException, JsonMappingException, IOException {
    List<Field> fields = new ArrayList<>();
    java.lang.reflect.Field[] rfields = clazz.getDeclaredFields();
    for (java.lang.reflect.Field field : rfields) {
      StarField starField = field.getAnnotation(StarField.class);
      String caption = starField.caption();
      DsType dsType = starField.dsType();
      String javaName = field.getName();
      int length = starField.dsLength();
      boolean primary = starField.primary();
      String enumName = starField.enumName();
      String enumOptTypes = starField.enumOptTypes();
      String enumOptValues = starField.enumOptValues();
      Field sfield = null;
      String typeName = field.getType().getSimpleName();
      switch (typeName) {
      case "String":
        sfield = Field.ofString(caption, javaName, dsType, length);
        break;
      case "Date":
        sfield = Field.ofDate(caption, javaName);
        break;
      case "Integer":
        if (StringUtils.isNotBlank(enumOptValues)) {
          sfield = Field.ofInteger(caption, javaName, dsType, enumName, enumOptTypes, enumOptValues);
        }else {
          sfield = Field.ofInteger(caption, javaName, dsType, length);
        }
        break;
      case "Long":
        if (primary) {
          sfield = Field.ofLongPrimary(caption, javaName, dsType, length);
        }else {
          sfield = Field.ofLong(caption, javaName, dsType, length);
        }
        break;
      default:
        break;
      }
      if (null != sfield) {
        createFieldList(field, sfield);
        createFieldQuery(field, sfield);
        createFieldAdd(field, sfield);
        createFieldEdit(field, sfield);
        fields.add(sfield);
      }
    }
    
    return fields;
  }
  
  private void createFieldEdit(java.lang.reflect.Field field, Field sfield) throws JsonParseException, JsonMappingException, IOException {
    StarFieldEdit starFieldEdit = field.getAnnotation(StarFieldEdit.class);
    if (null != starFieldEdit) {
      InputType inputType = starFieldEdit.inputType();
      String inputValue = starFieldEdit.inputValue();
      String placeholder = starFieldEdit.placeholder();
      String requiredMsg = starFieldEdit.requiredMsg();
      String zhengze = starFieldEdit.zhengze();
      String zhengzeMsg = starFieldEdit.zhengzeMsg();
      boolean hidden = starFieldEdit.hidden();
      String substituteName = starFieldEdit.substituteName();
      FieldAdd fieldEdit = null;
      if (inputType.equals(InputType.select) && StringUtils.isNotBlank(inputValue)) {
        Map<String, String> map = objectMapper.readValue(inputValue, new TypeReference<Map<String, String>>() {});
        Select select = Select.build().addMap(map);
        fieldEdit = FieldAdd.of(inputType, select, placeholder, StringUtils.isNotBlank(requiredMsg), requiredMsg, zhengze, zhengzeMsg, hidden, StringUtils.isNotBlank(substituteName), substituteName);
      }else {
        fieldEdit = FieldAdd.of(inputType, inputValue, placeholder, StringUtils.isNotBlank(requiredMsg), requiredMsg, zhengze, zhengzeMsg, hidden, StringUtils.isNotBlank(substituteName), substituteName);
      }
      sfield.setFieldEdit(fieldEdit);
    }
  }
  
  private void createFieldAdd(java.lang.reflect.Field field, Field sfield) throws JsonParseException, JsonMappingException, IOException {
    StarFieldAdd starFieldAdd = field.getAnnotation(StarFieldAdd.class);
    if (null != starFieldAdd) {
      InputType inputType = starFieldAdd.inputType();
      String inputValue = starFieldAdd.inputValue();
      String placeholder = starFieldAdd.placeholder();
      String requiredMsg = starFieldAdd.requiredMsg();
      String zhengze = starFieldAdd.zhengze();
      String zhengzeMsg = starFieldAdd.zhengzeMsg();
      boolean hidden = starFieldAdd.hidden();
      String substituteName = starFieldAdd.substituteName();
      FieldAdd fieldAdd = null;
      if (inputType.equals(InputType.select) && StringUtils.isNotBlank(inputValue)) {
        Map<String, String> map = objectMapper.readValue(inputValue, new TypeReference<Map<String, String>>() {});
        Select select = Select.build().addMap(map);
        fieldAdd = FieldAdd.of(inputType, select, placeholder, StringUtils.isNotBlank(requiredMsg), requiredMsg, zhengze, zhengzeMsg, hidden, StringUtils.isNotBlank(substituteName), substituteName);
      }else {
        fieldAdd = FieldAdd.of(inputType, inputValue, placeholder, StringUtils.isNotBlank(requiredMsg), requiredMsg, zhengze, zhengzeMsg, hidden, StringUtils.isNotBlank(substituteName), substituteName);
      }
      sfield.setFieldAdd(fieldAdd);
    }
  }

  private void createFieldQuery(java.lang.reflect.Field field, Field sfield) throws JsonParseException, JsonMappingException, IOException {
    StarFieldQuery starFieldQuery = field.getAnnotation(StarFieldQuery.class);
    if (null != starFieldQuery) {
      InputType inputType = starFieldQuery.inputType();
      String inputValue = starFieldQuery.inputValue();
      boolean hidden = starFieldQuery.hidden();
      String substituteName = starFieldQuery.substituteName();
      FieldQuery fieldQuery = null;
      switch (inputType) {
      case date:
        fieldQuery = FieldQuery.ofDate(inputValue);
        break;
      case text:
        fieldQuery = FieldQuery.ofText();
        break;
      case area:
        if (hidden && StringUtils.isNotBlank(substituteName)) {
          fieldQuery = FieldQuery.ofArea(substituteName);
        }else {
          fieldQuery = FieldQuery.ofArea();
        }
        break;
      case select:
        if(StringUtils.isNotBlank(inputValue)) {
          Map<String, String> map = objectMapper.readValue(inputValue, new TypeReference<Map<String, String>>() {});
          fieldQuery = FieldQuery.ofSelect(Select.build().addMap(map));
        }else {
          fieldQuery = FieldQuery.ofText();
        }
        break;
      default:
        break;
      }
      sfield.setFieldQuery(fieldQuery);
    }
  }
  
  private void createFieldList(java.lang.reflect.Field field, Field sfield) {
    StarFieldList starFieldList = field.getAnnotation(StarFieldList.class);
    if (null != starFieldList) {
      InputType inputType = starFieldList.inputType();
      String inputValue = starFieldList.inputValue();
      String width = starFieldList.width();
      boolean edit = starFieldList.edit();
      boolean sort = starFieldList.sort();
      TextAlign align = starFieldList.align();
      boolean hidden = starFieldList.hidden();
      String substituteName = starFieldList.substituteName();
      FieldList fieldList = FieldList.of(inputType, inputValue, width, sort, edit, align.name(), hidden, substituteName);
      sfield.setFieldList(fieldList);
    }
  }

  public Entity createEntity(String classpath) {
    Class<?> clazz = ClassUtils.getClass(classpath);
    StarDomainName domainName = clazz.getAnnotation(StarDomainName.class);
    String caption = domainName.caption();
    String beanName = clazz.getSimpleName();
    String tableName = domainName.tableName();
    if (StringUtils.isBlank(tableName)) {
      tableName = Utils.camelToUnderline(beanName);
    }
    long idWorkerId = domainName.idWorkerId();
    long idWorkerDataCenterId = domainName.idWorkerDataCenterId();
    String winWidth = domainName.winWidth();
    String winHeight = domainName.winHeight();
    boolean createTable = domainName.createTable();
    boolean existsDrop = domainName.existsDrop();
    Entity entity = Entity.of(caption, beanName, tableName, idWorkerId, idWorkerDataCenterId, winWidth, winHeight, createTable, existsDrop);
    entity.setBtnAdd(domainName.addButton());
    entity.setBtnEdit(domainName.editButton());
    entity.setBtnEnabled(domainName.disabledButton());
    entity.setBtnDelete(domainName.deleteButton());
    
    return entity;
  }

}
