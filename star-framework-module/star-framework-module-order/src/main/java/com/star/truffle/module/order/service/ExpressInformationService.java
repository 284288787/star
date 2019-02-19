/**create by framework at 2019年02月19日 14:19:41**/
package com.star.truffle.module.order.service;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.module.order.cache.ExpressInformationCache;
import com.star.truffle.module.order.domain.ExpressInformation;
import com.star.truffle.module.order.dto.req.ExpressInformationRequestDto;
import com.star.truffle.module.order.dto.res.ExpressInformationResponseDto;

@Service
public class ExpressInformationService {

  @Autowired
  private ExpressInformationCache expressInformationCache;

  public Long saveExpressInformation(ExpressInformation expressInformation) {
    this.expressInformationCache.saveExpressInformation(expressInformation);
    return expressInformation.getId();
  }

  public void updateExpressInformation(ExpressInformationRequestDto expressInformationRequestDto) {
    this.expressInformationCache.updateExpressInformation(expressInformationRequestDto);
  }

  public void deleteExpressInformation(Long id) {
    this.expressInformationCache.deleteExpressInformation(id);
  }

  public void deleteExpressInformation(String idstr) {
    if (StringUtils.isBlank(idstr)) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    String[] ids = idstr.split(",");
    for (String str : ids) {
      Long id = Long.parseLong(str);
      this.expressInformationCache.deleteExpressInformation(id);
    }
  }

  public ExpressInformationResponseDto getExpressInformation(Long id) {
    ExpressInformationResponseDto expressInformationResponseDto = this.expressInformationCache.getExpressInformation(id);
    return expressInformationResponseDto;
  }

  public List<ExpressInformationResponseDto> queryExpressInformation(ExpressInformationRequestDto expressInformationRequestDto) {
    return this.expressInformationCache.queryExpressInformation(expressInformationRequestDto);
  }

  public Long queryExpressInformationCount(ExpressInformationRequestDto expressInformationRequestDto) {
    return this.expressInformationCache.queryExpressInformationCount(expressInformationRequestDto);
  }

}