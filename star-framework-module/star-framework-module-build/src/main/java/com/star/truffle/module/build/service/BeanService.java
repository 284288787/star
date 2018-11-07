/**create by liuhua at 2018年8月2日 上午9:04:30**/
package com.star.truffle.module.build.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.module.build.constant.FieldType;
import com.star.truffle.module.build.dto.Entity;
import com.star.truffle.module.build.dto.Field;
import com.star.truffle.module.build.dto.FieldAdd;
import com.star.truffle.module.build.dto.FieldList;
import com.star.truffle.module.build.dto.FieldQuery;
import com.star.truffle.module.build.dto.Project;
import com.star.truffle.module.build.utils.Utils;

@Service
public class BeanService {
  
  private static StarJson starJson = new StarJson(new ObjectMapper());
  
  public void createApiController(Project project, Entity entity) {
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
    service.append("package ").append(project.getPackageController()).append(".api;\n\n");
    service.append("import java.util.List;\n");
    service.append("import org.springframework.beans.factory.annotation.Autowired;\n");
    service.append("import org.springframework.web.bind.annotation.RequestMapping;\n");
    service.append("import org.springframework.web.bind.annotation.RequestMethod;\n");
    service.append("import org.springframework.web.bind.annotation.RestController;\n");
    service.append("import com.star.truffle.core.StarServiceException;\n");
    service.append("import com.star.truffle.core.web.ApiResult;\n");
    service.append("import com.star.truffle.core.web.ApiCode;\n");
    service.append("import io.swagger.annotations.Api;\n");
    service.append("import io.swagger.annotations.ApiImplicitParam;\n");
    service.append("import io.swagger.annotations.ApiImplicitParams;\n");
    service.append("import io.swagger.annotations.ApiOperation;\n");
    service.append("import springfox.documentation.annotations.ApiIgnore;\n");
    service.append("import lombok.extern.slf4j.Slf4j;\n");
    service.append("import " + project.getPackageDomain() + "." + beanName + ";\n");
    service.append("import " + project.getPackageService() + "." + beanName + "Service;\n");
    service.append("import " + project.getPackageRequestDto() + "." + reqBeanName + ";\n");
    service.append("import " + project.getPackageResponseDto() + "." + resBeanName + ";\n");
    
    StringBuffer body = new StringBuffer();
    body.append("\n@Slf4j\n");
    body.append("@RestController\n");
    body.append("@RequestMapping(\"/api/" + beanNameVar + "\")\n");
    body.append("@Api(tags = \"" + beanName + "ApiController\")\n");
    body.append("public class " + beanName + "ApiController {\n\n");
    body.append("  @Autowired\n");
    body.append("  private " + beanName + "Service " + serviceVar + ";\n\n");
    
    body.append("  @RequestMapping(value = \"/get" + beanName + "\", method = RequestMethod.POST)\n");
    body.append("  @ApiOperation(value = \"根据主键获取" + entity.getCaption() + "\", notes = \"根据主键获取" + entity.getCaption() + "\", httpMethod = \"POST\", response = " + resBeanName + ".class)\n");
    body.append("  @ApiImplicitParams({\n");
    body.append("    @ApiImplicitParam(name = \"" + primary.getJavaName() + "\", value = \"" + primary.getCaption() + "\", dataType = \"" + primary.getJavaType().name() + "\", required = true, paramType = \"query\")\n");
    body.append("  })\n");
    body.append("  public ApiResult<" + resBeanName + "> get" + beanName + "(" + primary.getJavaType().name() + " " + primary.getJavaName() + ") {\n");
    body.append("    try {\n");
    body.append("      " + resBeanName + " " + resBeanNameVar + " = " + serviceVar + ".get" + beanName + "(" + primary.getJavaName() + ");\n");
    body.append("      return ApiResult.success(" + resBeanNameVar + ");\n");
    body.append("    } catch (StarServiceException e) {\n");
    body.append("      return ApiResult.fail(e.getCode(), e.getMsg());\n");
    body.append("    } catch (Exception e) {\n");
    body.append("      log.error(e.getMessage(), e);\n");
    body.append("      return ApiResult.fail(ApiCode.SYSTEM_ERROR);\n");
    body.append("    }\n");
    body.append("  }\n\n");
    
    body.append("  @RequestMapping(value = \"/query" + beanName + "\", method = RequestMethod.POST)\n");
    body.append("  @ApiOperation(value = \"根据条件获取" + entity.getCaption() + "列表\", notes = \"根据条件获取" + entity.getCaption() + "列表\", httpMethod = \"POST\", response = ApiResult.class)\n");
    body.append("  @ApiImplicitParams({\n");
    body.append("    @ApiImplicitParam(name = \"" + primary.getJavaName() + "\", value = \"" + primary.getCaption() + "\", dataType = \"" + primary.getJavaType().name() + "\", required = false, paramType = \"query\"),\n");
    for (Field field : fields) {
      if (null != field.getFieldQuery()) {
        body.append("    @ApiImplicitParam(name = \"" + field.getJavaName() + "\", value = \"" + field.getCaption() + "\", dataType = \"" + (FieldType.Integer.equals(field.getJavaType()) ? "int" : field.getJavaType().name()) + "\", required = false, paramType = \"query\"),\n");
      }
    }
    body.append("    @ApiImplicitParam(name = \"page.pageNum\", value = \"页码，页码如果没有则查询满足条件的所有记录\", dataType = \"int\", required = false, paramType = \"query\"),\n");
    body.append("    @ApiImplicitParam(name = \"page.pageSize\", value = \"一页最大记录数，当页码有值时，该值没有指定则默认为10\", dataType = \"int\", required = false, paramType = \"query\"),\n");
    body.append("    @ApiImplicitParam(name = \"page.orderBy\", value = \"排序字段，必须和数据库的字段一致\", dataType = \"String\", required = false, paramType = \"query\"),\n");
    body.append("    @ApiImplicitParam(name = \"page.orderType\", value = \"升序/降序\", dataType = \"String\", required = false, paramType = \"query\", allowableValues = \"asc,desc\")\n");
    body.append("  })\n");
    body.append("  public ApiResult<List<" + resBeanName + ">> query" + beanName + "(@ApiIgnore " + reqBeanName + " " + reqBeanNameVar + ") {\n");
    body.append("    try {\n");
    body.append("      List<" + resBeanName + "> list = " + serviceVar + ".query" + beanName + "(" + reqBeanNameVar + ");\n");
    body.append("      return ApiResult.success(list);\n");
    body.append("    } catch (StarServiceException e) {\n");
    body.append("      return ApiResult.fail(e.getCode(), e.getMsg());\n");
    body.append("    } catch (Exception e) {\n");
    body.append("      log.error(e.getMessage(), e);\n");
    body.append("      return ApiResult.fail(ApiCode.SYSTEM_ERROR);\n");
    body.append("    }\n");
    body.append("  }\n\n");
    
    body.append("  @RequestMapping(value = \"/query" + beanName + "Count\", method = RequestMethod.POST)\n");
    body.append("  @ApiOperation(value = \"根据条件获取" + entity.getCaption() + "数量\", notes = \"根据条件获取" + entity.getCaption() + "数量\", httpMethod = \"POST\", response = ApiResult.class)\n");
    body.append("  @ApiImplicitParams({\n");
    body.append("    @ApiImplicitParam(name = \"" + primary.getJavaName() + "\", value = \"" + primary.getCaption() + "\", dataType = \"" + primary.getJavaType().name() + "\", required = false, paramType = \"query\"),\n");
    for (Field field : fields) {
      if (null != field.getFieldQuery()) {
        body.append("    @ApiImplicitParam(name = \"" + field.getJavaName() + "\", value = \"" + field.getCaption() + "\", dataType = \"" + (FieldType.Integer.equals(field.getJavaType()) ? "int" : field.getJavaType().name()) + "\", required = false, paramType = \"query\"),\n");
      }
    }
    body.append("  })\n");
    body.append("  public ApiResult<Long> query" + beanName + "Count(@ApiIgnore " + reqBeanName + " " + reqBeanNameVar + ") {\n");
    body.append("    try {\n");
    body.append("      Long count = " + serviceVar + ".query" + beanName + "Count(" + reqBeanNameVar + ");\n");
    body.append("      return ApiResult.success(count);\n");
    body.append("    } catch (StarServiceException e) {\n");
    body.append("      return ApiResult.fail(e.getCode(), e.getMsg());\n");
    body.append("    } catch (Exception e) {\n");
    body.append("      log.error(e.getMessage(), e);\n");
    body.append("      return ApiResult.fail(ApiCode.SYSTEM_ERROR);\n");
    body.append("    }\n");
    body.append("  }\n\n");
    
    body.append("  @RequestMapping(value = \"/save" + beanName + "\", method = RequestMethod.POST)\n");
    body.append("  @ApiOperation(value = \"新增" + entity.getCaption() + "\", notes = \"新增" + entity.getCaption() + "\", httpMethod = \"POST\", response = ApiResult.class)\n");
    body.append("  @ApiImplicitParams({\n");
    for (Field field : fields) {
      if (null != field.getFieldAdd()) {
        body.append("    @ApiImplicitParam(name = \"" + field.getJavaName() + "\", value = \"" + field.getCaption() + "\", dataType = \"" + (FieldType.Integer.equals(field.getJavaType()) ? "int" : field.getJavaType().name()) + "\", required = true, paramType = \"query\"),\n");
      }
    }
    body.append("  })\n");
    body.append("  public ApiResult<Long> save" + beanName + "(@ApiIgnore " + beanName + " " + beanNameVar + ") {\n");
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
    
    body.append("  @RequestMapping(value = \"/update" + beanName + "\", method = RequestMethod.POST)\n");
    body.append("  @ApiOperation(value = \"编辑" + entity.getCaption() + "\", notes = \"编辑" + entity.getCaption() + "\", httpMethod = \"POST\", response = ApiResult.class)\n");
    body.append("  @ApiImplicitParams({\n");
    for (Field field : fields) {
      if (null != field.getFieldEdit()) {
        body.append("    @ApiImplicitParam(name = \"" + field.getJavaName() + "\", value = \"" + field.getCaption() + "\", dataType = \"" + (FieldType.Integer.equals(field.getJavaType()) ? "int" : field.getJavaType().name()) + "\", required = true, paramType = \"query\"),\n");
      }
    }
    body.append("  })\n");
    body.append("  public ApiResult<Void> update" + beanName + "(@ApiIgnore " + reqBeanName + " " + reqBeanNameVar + ") {\n");
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
    
    body.append("  @RequestMapping(value = \"/delete" + beanName + "\", method = RequestMethod.POST)\n");
    body.append("  @ApiOperation(value = \"根据主键删除" + entity.getCaption() + "\", notes = \"根据主键删除" + entity.getCaption() + "\", httpMethod = \"POST\", response = ApiResult.class)\n");
    body.append("  @ApiImplicitParams({\n");
    body.append("    @ApiImplicitParam(name = \"" + primary.getJavaName() + "\", value = \"" + primary.getCaption() + "\", dataType = \"" + primary.getJavaType().name() + "\", required = true, paramType = \"query\")\n");
    body.append("  })\n");
    body.append("  public ApiResult<Void> delete" + beanName + "(" + primary.getJavaType().name() + " " + primary.getJavaName() + ") {\n");
    body.append("    try {\n");
    body.append("      " + serviceVar + ".delete" + beanName + "(" + primary.getJavaName() + ");\n");
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
    Utils.writeFileContent(project.getBasicJavaFilePath() + "/controller/api/" + beanName + "ApiController.java", content);
    System.out.println("controller.api: " + beanName + "ApiController.java 创建完成");
  }

  public void createService(Project project, Entity entity) {
    String beanName = entity.getBeanName();
    String beanNameVar = Utils.firstToLower(beanName);
    String reqBeanName = beanName + "RequestDto";
    String resBeanName = beanName + "ResponseDto";
    String reqBeanNameVar = beanNameVar + "RequestDto";
    String resBeanNameVar = beanNameVar + "ResponseDto";
    String cacheVar = beanNameVar + "Cache";
    
    List<Field> fields = entity.getFields();
    Field primary = getPrimaryKey(fields);
    
    StringBuffer service = new StringBuffer();
    service.append("/**create by framework at ").append(Utils.formatNow()).append("**/").append("\n");
    service.append("package ").append(project.getPackageService()).append(";\n\n");
    service.append("import java.util.List;\n");
    service.append("import org.apache.commons.lang3.StringUtils;\n");
    service.append("import org.springframework.beans.factory.annotation.Autowired;\n");
    service.append("import org.springframework.stereotype.Service;\n");
    service.append("import com.star.truffle.core.StarServiceException;\n");
//    service.append("import com.star.truffle.core.util.IdWorker;\n");
//    service.append("import com.star.truffle.core.util.IdWorkerFactory;\n");
    service.append("import com.star.truffle.core.web.ApiCode;\n");
    service.append("import " + project.getPackageCache() + "." + beanName + "Cache;\n");
    service.append("import " + project.getPackageDomain() + "." + beanName + ";\n");
    service.append("import " + project.getPackageRequestDto() + "." + reqBeanName + ";\n");
    service.append("import " + project.getPackageResponseDto() + "." + resBeanName + ";\n");
    
    StringBuffer body = new StringBuffer();
    body.append("\n@Service\n");
    body.append("public class " + beanName + "Service {\n\n");
//    body.append("  private IdWorker idWorker = IdWorkerFactory.getIdWorker(" + entity.getIdWorkerId() + ", " + entity.getIdWorkerDataCenterId() + ");\n\n");
    body.append("  @Autowired\n");
    body.append("  private " + beanName + "Cache " + cacheVar + ";\n\n");
    
    body.append("  public Long save" + beanName + "(" + beanName + " " + beanNameVar + ") {\n");
//    body.append("    Long id = idWorker.nextId();\n");
//    body.append("    " + beanNameVar + ".set" + Utils.firstToUpper(primary.getJavaName()) + "(id);\n");
    for (Field field : fields) {
      if (StringUtils.isNotBlank(field.getDefaultAddfieldValue())) {
        body.append("    " + beanNameVar + ".set" + Utils.firstToUpper(field.getJavaName()) + "(" + field.getDefaultAddfieldValue() + ");\n");
        if (service.indexOf(field.getDefaultAddfieldValueType()) == -1) {
          service.append("import " + field.getDefaultAddfieldValueType() + ";\n");
        }
      }
    }
    body.append("    this." + cacheVar + ".save" + beanName + "(" + beanNameVar + ");\n");
    body.append("    return " + beanNameVar + ".get" + Utils.firstToUpper(primary.getJavaName()) + "();\n");
    body.append("  }\n\n");
    
    body.append("  public void update" + beanName + "(" + reqBeanName + " " + reqBeanNameVar + ") {\n");
    for (Field field : fields) {
      if (StringUtils.isNotBlank(field.getDefaultEditfieldValue())) {
        body.append("    " + reqBeanNameVar + ".set" + Utils.firstToUpper(field.getJavaName()) + "(" + field.getDefaultEditfieldValue() + ");\n");
        if (service.indexOf(field.getDefaultEditfieldValueType()) == -1) {
          service.append("import " + field.getDefaultEditfieldValueType() + ";\n");
        }
      }
    }
    body.append("    this." + cacheVar + ".update" + beanName + "(" + reqBeanNameVar + ");\n");
    body.append("  }\n\n");
    
    body.append("  public void delete" + beanName + "(" + primary.getJavaType().name() + " " + primary.getJavaName() + ") {\n");
    body.append("    this." + cacheVar + ".delete" + beanName + "(" + primary.getJavaName() + ");\n");
    body.append("  }\n\n");
    
    body.append("  public void delete" + beanName + "(String idstr) {\n");
    body.append("    if (StringUtils.isBlank(idstr)) {\n");
    body.append("      throw new StarServiceException(ApiCode.PARAM_ERROR);\n");
    body.append("    }\n");
    body.append("    String[] " + primary.getJavaName() + "s = idstr.split(\",\");\n");
    body.append("    for (String str : " + primary.getJavaName() + "s) {\n");
    body.append("      Long " + primary.getJavaName() + " = Long.parseLong(str);\n");
    body.append("      this." + cacheVar + ".delete" + beanName + "(" + primary.getJavaName() + ");\n");
    body.append("    }\n");
    body.append("  }\n\n");
    
    body.append("  public " + resBeanName + " get" + beanName + "(" + primary.getJavaType().name() + " " + primary.getJavaName() + ") {\n");
    body.append("    " + resBeanName + " " + resBeanNameVar + " = this." + cacheVar + ".get" + beanName + "(" + primary.getJavaName() + ");\n");
    body.append("    return " + resBeanNameVar + ";\n");
    body.append("  }\n\n");
    
    body.append("  public List<" + resBeanName + "> query" + beanName + "(" + reqBeanName + " " + reqBeanNameVar + ") {\n");
    body.append("    return this." + cacheVar + ".query" + beanName + "(" + reqBeanNameVar + ");\n");
    body.append("  }\n\n");
    
    body.append("  public Long query" + beanName + "Count(" + reqBeanName + " " + reqBeanNameVar + ") {\n");
    body.append("    return this." + cacheVar + ".query" + beanName + "Count(" + reqBeanNameVar + ");\n");
    body.append("  }\n\n");
    
    body.append("}");
    
    String content = service.append(body).toString();
    Utils.writeFileContent(project.getBasicJavaFilePath() + "/service/" + beanName + "Service.java", content);
    System.out.println("service: " + beanName + "Service.java 创建完成");
  }

  @SuppressWarnings("unchecked")
  public void createEnum(Project project, Entity entity) {
    List<Field> fields = entity.getFields();
    for (Field field : fields) {
      String enumName = field.getEnumName();
      String enumOptionTypes = field.getEnumOptionTypes();
      String enumOptionValues = field.getEnumOptionValues();
      if (StringUtils.isNotBlank(enumName) && StringUtils.isNotBlank(enumOptionValues)) {
        StringBuffer enums = new StringBuffer();
        enums.append("/**create by framework at ").append(Utils.formatNow()).append("**/").append("\n");
        enums.append("package ").append(project.getPackageConstant()).append(";\n\n");
        enums.append("public enum " + enumName + " { \n\n");
        if (StringUtils.isBlank(enumOptionTypes)) {
          String[] values = starJson.str2obj(enumOptionValues, String[].class);
          String temp = "";
          for (String v : values) {
            temp += ", " + v;
          }
          enums.append("  " + temp.substring(2) + ";\n");
        }else{
          Map<String, String> types = starJson.str2Map(enumOptionTypes);
          Map<String, Object> values = starJson.str2Map(enumOptionValues);
          String str = "";
          for (Map.Entry<String, Object> value : values.entrySet()) {
            String nam = value.getKey();
            Map<String, Object> eles = (Map<String, Object>) value.getValue();
            String temp = nam + "(";
            for (Map.Entry<String, String> type : types.entrySet()) {
              String k = type.getKey();
              String v = type.getValue();
              String t = eles.get(k).toString();
              if ("String".equals(v)) {
                t = "\"" + t + "\"";
              }
              temp += t + ", ";
            }
            temp = temp.substring(0, temp.length() - 2) + ")";
            str += ", " + temp;
          }
          enums.append("  " + str.substring(2) + ";\n\n");
          String t1 = "";
          String t2 = "";
          String t3 = "";
          for (Map.Entry<String, String> type : types.entrySet()) {
            String k = type.getKey();
            String v = type.getValue();
            enums.append("  private " + v + " " + k + ";\n");
            t1 += ", " + v + " " + k;
            t2 += "    this." + k + " = " + k + ";\n";
            t3 += "  public " + v + " " + k + "() {\n";
            t3 += "    return this." + k + ";\n";
            t3 += "  }\n\n";
          }
          enums.append("\n  private " + enumName + "(" + t1.substring(2) + ") {\n");
          enums.append(t2);
          enums.append("  }\n\n");
          enums.append(t3);
        }
        enums.append("}");
        
        String content = enums.toString();
        Utils.writeFileContent(project.getBasicJavaFilePath() + "/constant/" + enumName + ".java", content);
        System.out.println("enum: " + enumName + ".java 创建完成");
      }
    }
  }

  public void createCache(Project project, Entity entity) {
    String beanName = entity.getBeanName();
    String readDaoBeanName = beanName + "ReadDao";
    String writeDaoBeanName = beanName + "WriteDao";
    String reqBeanName = beanName + "RequestDto";
    String resBeanName = beanName + "ResponseDto";
    String beanNameVar = Utils.firstToLower(beanName);
    String readVar = beanNameVar + "ReadDao";
    String writeVar = beanNameVar + "WriteDao";
    String reqBeanNameVar = beanNameVar + "RequestDto";
    String resBeanNameVar = beanNameVar + "ResponseDto";
    List<Field> fields = entity.getFields();
    Field primary = getPrimaryKey(fields);
    
    String cacheValue = "module-" + project.getName() + "-" + beanNameVar;
    String cacheKey = "'" + beanNameVar + "_" + primary.getJavaName() + "_'+#result." + primary.getJavaName();
    String cacheDelKey = "'" + beanNameVar + "_" + primary.getJavaName() + "_'+#" + primary.getJavaName();
    String cacheCondition = "#result != null and #result." + primary.getJavaName() + " != null";
    String cacheDelCondition = "#" + primary.getJavaName() + " != null";
    
    StringBuffer cache = new StringBuffer();
    cache.append("/**create by framework at ").append(Utils.formatNow()).append("**/").append("\n");
    cache.append("package ").append(project.getPackageCache()).append(";\n\n");
    cache.append("import java.util.List;\n");
    cache.append("import java.util.Map;\n");
    cache.append("import org.springframework.beans.factory.annotation.Autowired;\n");
    cache.append("import org.springframework.cache.annotation.CacheEvict;\n");
    cache.append("import org.springframework.cache.annotation.CachePut;\n");
    cache.append("import org.springframework.cache.annotation.Cacheable;\n");
    cache.append("import org.springframework.stereotype.Service;\n");
    cache.append("import com.star.truffle.core.jackson.StarJson;\n");
    cache.append("import " + project.getPackageDaoRead() + "." + readDaoBeanName + ";\n");
    cache.append("import " + project.getPackageDaoWrite() + "." + writeDaoBeanName + ";\n");
    cache.append("import " + project.getPackageDomain() + "." + beanName + ";\n");
    cache.append("import " + project.getPackageRequestDto() + "." + reqBeanName + ";\n");
    cache.append("import " + project.getPackageResponseDto() + "." + resBeanName + ";\n");
    
    cache.append("\n@Service\n");
    cache.append("public class " + beanName + "Cache {\n\n");
    cache.append("  @Autowired\n");
    cache.append("  private StarJson starJson;\n");
    cache.append("  @Autowired\n");
    cache.append("  private " + writeDaoBeanName + " " + writeVar + ";\n");
    cache.append("  @Autowired\n");
    cache.append("  private " + readDaoBeanName + " " + readVar + ";\n\n");
    
    cache.append("  @CachePut(value = \"" + cacheValue + "\", key = \"" + cacheKey + "\", condition = \"" + cacheCondition + "\")\n");
    cache.append("  public " + resBeanName + " save" + beanName + "(" + beanName + " " + beanNameVar + "){\n");
    cache.append("    this." + writeVar + ".save" + beanName + "(" + beanNameVar + ");\n");
    cache.append("    "+ resBeanName +" " + resBeanNameVar + " = this." + writeVar + ".get" + beanName + "(" + beanNameVar + ".get" + Utils.firstToUpper(primary.getJavaName()) + "());\n");
    cache.append("    return " + resBeanNameVar + ";\n");
    cache.append("  }\n\n");
    
    cache.append("  @CachePut(value = \"" + cacheValue + "\", key = \"" + cacheKey + "\", condition = \"" + cacheCondition + "\")\n");
    cache.append("  public " + resBeanName + " update" + beanName + "(" + reqBeanName + " " + reqBeanNameVar + "){\n");
    cache.append("    this." + writeVar + ".update" + beanName + "(" + reqBeanNameVar + ");\n");
    cache.append("    "+ resBeanName +" " + resBeanNameVar + " = this." + writeVar + ".get" + beanName + "(" + reqBeanNameVar + ".get" + Utils.firstToUpper(primary.getJavaName()) + "());\n");
    cache.append("    return " + resBeanNameVar + ";\n");
    cache.append("  }\n\n");
    
    cache.append("  @CacheEvict(value = \"" + cacheValue + "\", key = \"" + cacheDelKey + "\", condition = \"" + cacheDelCondition + "\")\n");
    cache.append("  public int delete" + beanName + "(" + primary.getJavaType().name() + " " + primary.getJavaName() + "){\n");
    cache.append("    return this." + writeVar + ".delete" + beanName + "(" + primary.getJavaName() + ");\n");
    cache.append("  }\n\n");
    
    cache.append("  @Cacheable(value = \"" + cacheValue + "\", key = \"" + cacheDelKey + "\", condition = \"" + cacheDelCondition + "\")\n");
    cache.append("  public " + resBeanName + " get" + beanName + "(" + primary.getJavaType().name() + " " + primary.getJavaName() + "){\n");
    cache.append("    "+ resBeanName +" " + resBeanNameVar + " = this." + readVar + ".get" + beanName + "(" + primary.getJavaName() + ");\n");
    cache.append("    return " + resBeanNameVar + ";\n");
    cache.append("  }\n\n");
    
    cache.append("  public List<" + resBeanName + "> query" + beanName + "(" + reqBeanName + " " + reqBeanNameVar + "){\n");
    cache.append("    Map<String, Object> conditions = starJson.bean2Map(" + reqBeanNameVar + ");\n");
    cache.append("    return this." + readVar + ".query" + beanName + "(conditions);\n");
    cache.append("  }\n\n");
    
    cache.append("  public Long query" + beanName + "Count(" + reqBeanName + " " + reqBeanNameVar + "){\n");
    cache.append("    Map<String, Object> conditions = starJson.bean2Map(" + reqBeanNameVar + ");\n");
    cache.append("    return this." + readVar + ".query" + beanName + "Count(conditions);\n");
    cache.append("  }\n\n");
    
    cache.append("}");
    
    String content = cache.toString();
    Utils.writeFileContent(project.getBasicJavaFilePath() + "/cache/" + beanName + "Cache.java", content);
    System.out.println("cache: " + beanName + "Cache.java 创建完成");
  }

  private Field getPrimaryKey(List<Field> fields){
    return fields.stream().filter(field -> field.isPrimaryKey()).findFirst().orElseThrow(() -> new StarServiceException(ApiCode.PARAM_ERROR.getCode(), "必须设置一个主键，并且只能有一个"));
  }
  
  public void createWriteMapper(Project project, Entity entity){
    String beanName = entity.getBeanName();
    String tableName = entity.getTableName();
    List<Field> fields = entity.getFields();
    Field primary = getPrimaryKey(fields);
    
    String daoClasspath = project.getPackageDaoWrite() + "." + beanName + "WriteDao";
    String dtoClasspath = project.getPackageRequestDto() + "." + beanName + "RequestDto";
    String dtoVar = Utils.firstToLower(beanName) + "ResponseDto";
    String resDtoClasspath = project.getPackageResponseDto() + "." + beanName + "ResponseDto";
    String domainClasspath = project.getPackageDomain() + "." + beanName;
    
    StringBuffer mapper = new StringBuffer();
    mapper.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
    mapper.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >\n");
    mapper.append("<mapper namespace=\"" + daoClasspath + "\">\n");
    mapper.append("  <insert id=\"save" + beanName + "\" useGeneratedKeys=\"true\" keyProperty=\"" + primary.getJavaName() + "\" parameterType=\"" + domainClasspath + "\">\n");
    mapper.append("    insert into " + tableName).append("(");
    for (int i = 0; i < fields.size(); i++) {
      Field field = fields.get(i);
      mapper.append(field.getDsName());
      if (i < fields.size() - 1) {
        mapper.append(", ");
      }
    }
    mapper.append(")\n");
    mapper.append("    values(");
    for (int i = 0; i < fields.size(); i++) {
      Field field = fields.get(i);
      mapper.append("#{" + field.getJavaName() + "}");
      if (i < fields.size() - 1) {
        mapper.append(", ");
      }
    }
    mapper.append(")\n");
    mapper.append("  </insert>\n\n");
    
    mapper.append("  <insert id=\"batchSave" + beanName + "\" parameterType=\"java.util.List\">\n");
    mapper.append("    insert into " + tableName).append("(");
    for (int i = 0; i < fields.size(); i++) {
      Field field = fields.get(i);
      mapper.append(field.getDsName());
      if (i < fields.size() - 1) {
        mapper.append(", ");
      }
    }
    mapper.append(")\n");
    mapper.append("    values\n");
    mapper.append("    <foreach collection=\"list\" item=\"item\" index=\"index\" separator=\",\">\n");
    mapper.append("      (");
    for (int i = 0; i < fields.size(); i++) {
      Field field = fields.get(i);
      mapper.append("#{item." + field.getJavaName() + "}");
      if (i < fields.size() - 1) {
        mapper.append(", ");
      }
    }
    mapper.append(")\n");
    mapper.append("    </foreach>\n");
    mapper.append("  </insert>\n\n");
    
    mapper.append("  <update id=\"update" + beanName + "\" parameterType=\"" + dtoClasspath + "\">\n");
    mapper.append("    update " + tableName + "\n");
    mapper.append("    <trim prefix=\"set\" suffixOverrides=\",\">\n");
    for (Field field : fields) {
      if (! field.isPrimaryKey()) {
        mapper.append("      <if test=\"" + field.getJavaName() + " != null");
        if (field.getJavaType().equals(FieldType.String)) {
          mapper.append(" and " + field.getJavaName() + " != ''");
        }
        mapper.append("\">");
        mapper.append(field.getDsName() + " = #{" + field.getJavaName() + "},</if>\n");
      }
    }
    mapper.append("    </trim>\n");
    mapper.append("    where " + primary.getDsName() + " = #{" + primary.getJavaName() + "}\n");
    mapper.append("  </update>\n\n");
    
    mapper.append("  <delete id=\"delete" + beanName + "\" parameterType=\"java.lang.Long\">\n");
    mapper.append("    delete from " + tableName + " where " + primary.getDsName() + " = #{" + primary.getJavaName() + "}\n");
    mapper.append("  </delete>\n\n");
    
    mapper.append("  <resultMap id=\"" + dtoVar + "ResultMap\" type=\"" + resDtoClasspath + "\">\n");
    for (Field field : fields) {
      mapper.append("    <result column=\"" + field.getDsName() + "\" property=\"" + field.getJavaName() + "\" />\n");
    }
    mapper.append("  </resultMap>\n\n");
    
    mapper.append("  <sql id=\"sql_column\">\n    ");
    for (int i = 0; i < fields.size(); i++) {
      Field field = fields.get(i);
      mapper.append(field.getDsName());
      if (i < fields.size() - 1) {
        mapper.append(", ");
      }
    }
    mapper.append("\n  </sql>\n\n");
    
    mapper.append("  <select id=\"get" + beanName + "\" resultType=\"" + resDtoClasspath + "\">\n");
    mapper.append("    select\n");
    mapper.append("    <include refid=\"sql_column\" />\n");
    mapper.append("    from " + tableName + "\n");
    mapper.append("    where " + primary.getDsName() + " = #{" + primary.getJavaName() + "}\n");
    mapper.append("  </select>\n\n");
    
    mapper.append("</mapper>");
    
    String content = mapper.toString();
    Utils.writeFileContent(project.getBasicResourcesFilePath() + "/mapper/" + project.getName() + "/write/" + beanName + "WriteMapper.xml", content);
    System.out.println("mapper.write: " + beanName + "WriteMapper.xml 创建完成");
  }
  
  public void createReadMapper(Project project, Entity entity){
    String beanName = entity.getBeanName();
    String tableName = entity.getTableName();
    List<Field> fields = entity.getFields();
    Field primary = getPrimaryKey(fields);
    
    String dtoVar = Utils.firstToLower(beanName) + "ResponseDto";
    String daoClasspath = project.getPackageDaoRead() + "." + beanName + "ReadDao";
    String dtoClasspath = project.getPackageResponseDto() + "." + beanName + "ResponseDto";
    
    StringBuffer mapper = new StringBuffer();
    mapper.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
    mapper.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >\n");
    mapper.append("<mapper namespace=\"" + daoClasspath + "\">\n");
    mapper.append("  <resultMap id=\"" + dtoVar + "ResultMap\" type=\"" + dtoClasspath + "\">\n");
    for (Field field : fields) {
      mapper.append("    <result column=\"" + field.getDsName() + "\" property=\"" + field.getJavaName() + "\" />\n");
    }
    mapper.append("  </resultMap>\n\n");
    
    mapper.append("  <sql id=\"sql_column\">\n    ");
    for (int i = 0; i < fields.size(); i++) {
      Field field = fields.get(i);
      mapper.append(field.getDsName());
      if (i < fields.size() - 1) {
        mapper.append(", ");
      }
    }
    mapper.append("\n  </sql>\n\n");
    
    mapper.append("  <sql id=\"sql_where\">\n");
    mapper.append("    <trim prefix=\"WHERE\" prefixOverrides=\"AND | OR\">\n");
    mapper.append("      <if test=\"" + primary.getJavaName() + " != null\">\n");
    mapper.append("        and " + primary.getDsName() + " = #{" + primary.getJavaName() + "}\n");
    mapper.append("      </if>\n");
    for (Field field : fields) {
      if (null != field.getFieldQuery()) {
        mapper.append("      <if test=\"" + field.getJavaName() + " != null");
        if (field.getJavaType().equals(FieldType.String)) {
          mapper.append(" and " + field.getJavaName() + " != ''");
        }
        mapper.append("\">\n");
        mapper.append("        and " + field.getDsName() + " = #{" + field.getJavaName() + "}\n");
        mapper.append("      </if>\n");
      }
    }
    mapper.append("    </trim>\n");
    mapper.append("  </sql>\n\n");
    
    mapper.append("  <select id=\"get" + beanName + "\" resultType=\"" + dtoClasspath + "\">\n");
    mapper.append("    select\n");
    mapper.append("    <include refid=\"sql_column\" />\n");
    mapper.append("    from " + tableName + "\n");
    mapper.append("    where " + primary.getDsName() + " = #{" + primary.getJavaName() + "}\n");
    mapper.append("  </select>\n\n");
    
    mapper.append("  <select id=\"query" + beanName + "\" resultMap=\"" + dtoVar + "ResultMap\">\n");
    mapper.append("    select\n");
    mapper.append("    <include refid=\"sql_column\" />\n");
    mapper.append("    from " + tableName + "\n");
    mapper.append("    <include refid=\"sql_where\" />\n");
    mapper.append("    order by\n");
    mapper.append("    <choose>\n");
    mapper.append("      <when test=\"pager != null and pager.orderBy != null and pager.orderBy != ''\">${pager.orderBy}</when>\n");
    mapper.append("      <otherwise>" + primary.getDsName() + "</otherwise>\n");
    mapper.append("    </choose>\n");
    mapper.append("    <choose>\n");
    mapper.append("      <when test=\"pager != null and pager.orderType != null and pager.orderType != ''\">${pager.orderType}</when>\n");
    mapper.append("      <otherwise>desc</otherwise>\n");
    mapper.append("    </choose>\n");
    mapper.append("    <if test=\"pager != null and pager.pageSize!=null and pager.startIndex!=null\">\n");
    mapper.append("      limit #{pager.startIndex}, #{pager.pageSize}\n");
    mapper.append("    </if>\n");
    mapper.append("  </select>\n\n");
    
    mapper.append("  <select id=\"query" + beanName + "Count\" resultType=\"java.lang.Long\">\n");
    mapper.append("    select\n");
    mapper.append("    count(1)\n");
    mapper.append("    from " + tableName + "\n");
    mapper.append("    <include refid=\"sql_where\" />\n");
    mapper.append("  </select>\n");
    mapper.append("</mapper>");
    
    String content = mapper.toString();
    Utils.writeFileContent(project.getBasicResourcesFilePath() + "/mapper/" + project.getName() + "/read/" + beanName + "ReadMapper.xml", content);
    System.out.println("mapper.read: " + beanName + "ReadMapper.xml 创建完成");
  }
  
  public void createReadDao(Project project, Entity entity){
    String beanName = entity.getBeanName();
    String resDtoName = beanName + "ResponseDto";
    List<Field> fields = entity.getFields();
    Field primary = getPrimaryKey(fields);
    
    StringBuffer dao = new StringBuffer();
    dao.append("/**create by framework at ").append(Utils.formatNow()).append("**/").append("\n");
    dao.append("package ").append(project.getPackageDaoRead()).append(";\n\n");
    dao.append("import java.util.List;\n");
    dao.append("import java.util.Map;\n");
//    dao.append("import org.apache.ibatis.annotations.Param;\n");
    dao.append("import ").append(project.getPackageResponseDto()).append(".").append(resDtoName).append(";\n");
    if (StringUtils.isNotBlank(primary.getJavaType().getClasspath()) && dao.indexOf(primary.getJavaType().getClasspath()) == -1) {
      dao.append("import ").append(primary.getJavaType().getClasspath()).append(";\n");
    }
    
    StringBuffer body = new StringBuffer();
    body.append("\npublic interface ").append(beanName).append("ReadDao {\n\n");
    body.append("  public ").append(resDtoName).append(" ").append("get").append(beanName).append("(").append(primary.getJavaType().name()).append(" ").append(primary.getJavaName()).append(");\n\n");
    body.append("  public List<").append(resDtoName).append("> ").append("query").append(beanName).append("(Map<String, Object> conditions);\n\n");
    body.append("  public Long ").append("query").append(beanName).append("Count(Map<String, Object> conditions);\n\n");
    body.append("}");
    
    String content = dao.append(body).toString();
    Utils.writeFileContent(project.getBasicJavaFilePath() + "/dao/read/" + beanName + "ReadDao.java", content);
    System.out.println("dao.read: " + beanName + "ReadDao.java 创建完成");
  }
  
  public void createWriteDao(Project project, Entity entity){
    String beanName = entity.getBeanName();
    String reqDtoName = beanName + "RequestDto";
    String resDtoName = beanName + "ResponseDto";
    List<Field> fields = entity.getFields();
    Field primary = getPrimaryKey(fields);
    
    StringBuffer dao = new StringBuffer();
    dao.append("/**create by framework at ").append(Utils.formatNow()).append("**/").append("\n");
    dao.append("package ").append(project.getPackageDaoWrite()).append(";\n\n");
    dao.append("import java.util.List;\n");
    dao.append("import ").append(project.getPackageDomain()).append(".").append(beanName).append(";\n");
    dao.append("import ").append(project.getPackageRequestDto()).append(".").append(reqDtoName).append(";\n");
    dao.append("import ").append(project.getPackageResponseDto()).append(".").append(resDtoName).append(";\n");
    if (StringUtils.isNotBlank(primary.getJavaType().getClasspath()) && dao.indexOf(primary.getJavaType().getClasspath()) == -1) {
      dao.append("import ").append(primary.getJavaType().getClasspath()).append(";\n");
    }
    
    StringBuffer body = new StringBuffer();
    body.append("\npublic interface ").append(beanName).append("WriteDao {\n\n");
    body.append("  public int save").append(beanName).append("(").append(beanName).append(" ").append(Utils.firstToLower(beanName)).append(");\n\n");
    body.append("  public int batchSave").append(beanName).append("(List<").append(beanName).append("> ").append(Utils.firstToLower(beanName)).append("s);\n\n");
    body.append("  public int update").append(beanName).append("(").append(reqDtoName).append(" ").append(Utils.firstToLower(beanName)).append("Dto);\n\n");
    body.append("  public int delete").append(beanName).append("(").append(primary.getJavaType().name()).append(" ").append(primary.getJavaName()).append(");\n\n");
    body.append("  public ").append(resDtoName).append(" ").append("get").append(beanName).append("(").append(primary.getJavaType().name()).append(" ").append(primary.getJavaName()).append(");\n\n");
    body.append("}");
    
    String content = dao.append(body).toString();
    Utils.writeFileContent(project.getBasicJavaFilePath() + "/dao/write/" + beanName + "WriteDao.java", content);
    System.out.println("dao.write: " + beanName + "WriteDao.java 创建完成");
  }
  
  public void createDomain(Project project, Entity entity){
    String beanName = entity.getBeanName();
    List<Field> fields = entity.getFields();
    StringBuffer bean = new StringBuffer();
    bean.append("/**create by framework at ").append(Utils.formatNow()).append("**/").append("\n");
    bean.append("package ").append(project.getPackageDomain()).append(";\n\n");
    bean.append("import lombok.Getter;\n");
    bean.append("import lombok.Setter;\n");
    
    StringBuffer body = new StringBuffer();
    body.append("\n@Getter\n");
    body.append("@Setter\n");
    body.append("public class ").append(beanName).append(" {\n\n");
    for (Field field : fields) {
      FieldType javaType = field.getJavaType();
      if (StringUtils.isNotBlank(javaType.getClasspath()) && bean.indexOf(javaType.getClasspath()) == -1) {
        bean.append("import ").append(javaType.getClasspath()).append(";\n");
      }
      body.append("  // ").append(field.getCaption()).append("\n");
      body.append("  private ").append(javaType.name()).append(" ").append(field.getJavaName()).append(";\n");
    }
    body.append("}");
    
    String content = bean.append(body).toString();
    Utils.writeFileContent(project.getBasicJavaFilePath() + "/domain/" + beanName + ".java", content);
    System.out.println("domain: " + beanName + ".java 创建完成");
  }
  
  public void createRequestDto(Project project, Entity entity){
    String beanName = entity.getBeanName();
    StringBuffer bean = new StringBuffer();
    bean.append("/**create by framework at ").append(Utils.formatNow()).append("**/").append("\n");
    bean.append("package ").append(project.getPackageRequestDto()).append(";\n\n");
    bean.append("import com.star.truffle.core.jdbc.Page;\n");
    bean.append("import lombok.Getter;\n");
    bean.append("import lombok.Setter;\n");
    bean.append("import ").append(project.getPackageDomain()).append(".").append(beanName).append(";\n");
    
    StringBuffer body = new StringBuffer();
    body.append("\n@Getter\n");
    body.append("@Setter\n");
    body.append("public class ").append(beanName).append("RequestDto extends ").append(beanName).append(" {\n\n");
    body.append("  private Page pager;\n");
    body.append("}");
    
    String content = bean.append(body).toString();
    Utils.writeFileContent(project.getBasicJavaFilePath() + "/dto/req/" + beanName + "RequestDto.java", content);
    System.out.println("dto.request: " + beanName + "RequestDto.java 创建完成");
  }
  
  public void createResponseDto(Project project, Entity entity){
    List<String> substituteNames = substituteNames(entity.getFields());
    String beanName = entity.getBeanName();
    StringBuffer bean = new StringBuffer();
    bean.append("/**create by framework at ").append(Utils.formatNow()).append("**/").append("\n");
    bean.append("package ").append(project.getPackageResponseDto()).append(";\n\n");
    bean.append("import lombok.Getter;\n");
    bean.append("import lombok.Setter;\n");
    bean.append("import ").append(project.getPackageDomain()).append(".").append(beanName).append(";\n");
    
    StringBuffer body = new StringBuffer();
    body.append("\n@Getter\n");
    body.append("@Setter\n");
    body.append("public class ").append(beanName).append("ResponseDto extends ").append(beanName).append(" {\n\n");
    if (null != substituteNames && ! substituteNames.isEmpty()) {
      for (String substituteName : substituteNames) {
        body.append("  private String " + substituteName + ";\n");
      }
    }
    body.append("}");
    
    String content = bean.append(body).toString();
    Utils.writeFileContent(project.getBasicJavaFilePath() + "/dto/res/" + beanName + "ResponseDto.java", content);
    System.out.println("dto.response: " + beanName + "ResponseDto.java 创建完成");
  }
  
  private List<String> substituteNames(List<Field> fields){
    List<String> substituteNames = new ArrayList<>();
    for (Field field : fields) {
      FieldQuery fieldQuery = field.getFieldQuery();
      FieldList fieldList = field.getFieldList();
      FieldAdd fieldAdd = field.getFieldAdd();
      FieldAdd fieldEdit = field.getFieldEdit();
      if (null != fieldQuery && StringUtils.isNotBlank(fieldQuery.getSubstituteName()) && ! substituteNames.contains(fieldQuery.getSubstituteName())) {
        substituteNames.add(fieldQuery.getSubstituteName());
      }
      if (null != fieldList && StringUtils.isNotBlank(fieldList.getSubstituteName()) && ! substituteNames.contains(fieldList.getSubstituteName())) {
        substituteNames.add(fieldList.getSubstituteName());
      }
      if (null != fieldAdd && StringUtils.isNotBlank(fieldAdd.getSubstituteName()) && ! substituteNames.contains(fieldAdd.getSubstituteName())) {
        substituteNames.add(fieldAdd.getSubstituteName());
      }
      if (null != fieldEdit && StringUtils.isNotBlank(fieldEdit.getSubstituteName()) && ! substituteNames.contains(fieldEdit.getSubstituteName())) {
        substituteNames.add(fieldEdit.getSubstituteName());
      }
    }
    return substituteNames;
  }
}
