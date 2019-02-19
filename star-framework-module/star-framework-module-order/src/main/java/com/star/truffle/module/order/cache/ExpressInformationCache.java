/**create by framework at 2019年02月19日 14:19:41**/
package com.star.truffle.module.order.cache;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.module.order.dao.read.ExpressInformationReadDao;
import com.star.truffle.module.order.dao.write.ExpressInformationWriteDao;
import com.star.truffle.module.order.domain.ExpressInformation;
import com.star.truffle.module.order.dto.req.ExpressInformationRequestDto;
import com.star.truffle.module.order.dto.res.ExpressInformationResponseDto;

@Service
public class ExpressInformationCache {

  @Autowired
  private StarJson starJson;
  @Autowired
  private ExpressInformationWriteDao expressInformationWriteDao;
  @Autowired
  private ExpressInformationReadDao expressInformationReadDao;

  public ExpressInformationResponseDto saveExpressInformation(ExpressInformation expressInformation){
    this.expressInformationWriteDao.saveExpressInformation(expressInformation);
    ExpressInformationResponseDto expressInformationResponseDto = this.expressInformationWriteDao.getExpressInformation(expressInformation.getId());
    return expressInformationResponseDto;
  }

  public ExpressInformationResponseDto updateExpressInformation(ExpressInformationRequestDto expressInformationRequestDto){
    this.expressInformationWriteDao.updateExpressInformation(expressInformationRequestDto);
    ExpressInformationResponseDto expressInformationResponseDto = this.expressInformationWriteDao.getExpressInformation(expressInformationRequestDto.getId());
    return expressInformationResponseDto;
  }

  public int deleteExpressInformation(Long id){
    return this.expressInformationWriteDao.deleteExpressInformation(id);
  }

  public ExpressInformationResponseDto getExpressInformation(Long id){
    ExpressInformationResponseDto expressInformationResponseDto = this.expressInformationReadDao.getExpressInformation(id);
    return expressInformationResponseDto;
  }

  public List<ExpressInformationResponseDto> queryExpressInformation(ExpressInformationRequestDto expressInformationRequestDto){
    Map<String, Object> conditions = starJson.bean2Map(expressInformationRequestDto);
    return this.expressInformationReadDao.queryExpressInformation(conditions);
  }

  public Long queryExpressInformationCount(ExpressInformationRequestDto expressInformationRequestDto){
    Map<String, Object> conditions = starJson.bean2Map(expressInformationRequestDto);
    return this.expressInformationReadDao.queryExpressInformationCount(conditions);
  }

}