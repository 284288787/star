/**create by framework at 2018年10月26日 09:40:50**/
package com.star.truffle.module.member.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.core.web.ApiResult;
import com.star.truffle.module.member.dto.req.DistributorApplyRequestDto;
import com.star.truffle.module.member.service.DistributorApplyService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@RestController
@RequestMapping("/api/distributorApply")
@Api(tags = "分销商申请")
public class DistributorApplyApiController {

  @Autowired
  private DistributorApplyService distributorApplyService;

  @RequestMapping(value = "/save", method = RequestMethod.POST)
  @ApiOperation(value = "新增分销商申请", notes = "新增分销商申请", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
  })
  public ApiResult<Long> saveDistributorApply(@ApiIgnore DistributorApplyRequestDto distributorApply) {
    try {
      Long id = distributorApplyService.saveDistributorApply(distributorApply);
      return ApiResult.success(id);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }
}