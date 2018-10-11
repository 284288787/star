/**create by framework at 2018年10月11日 11:07:21**/
package com.star.truffle.module.order.cache;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.module.order.dao.read.KickbackDetailReadDao;
import com.star.truffle.module.order.dao.write.KickbackDetailWriteDao;
import com.star.truffle.module.order.domain.KickbackDetail;
import com.star.truffle.module.order.dto.req.KickbackDetailRequestDto;
import com.star.truffle.module.order.dto.res.KickbackDetailResponseDto;

@Service
public class KickbackDetailCache {

  @Autowired
  private StarJson starJson;
  @Autowired
  private KickbackDetailWriteDao kickbackDetailWriteDao;
  @Autowired
  private KickbackDetailReadDao kickbackDetailReadDao;

  @CachePut(value = "module-order-kickbackDetail", key = "'kickbackDetail_id_'+#result.id", condition = "#result != null and #result.id != null")
  public KickbackDetailResponseDto saveKickbackDetail(KickbackDetail kickbackDetail){
    this.kickbackDetailWriteDao.saveKickbackDetail(kickbackDetail);
    KickbackDetailResponseDto kickbackDetailResponseDto = this.kickbackDetailWriteDao.getKickbackDetail(kickbackDetail.getId());
    return kickbackDetailResponseDto;
  }

  @CachePut(value = "module-order-kickbackDetail", key = "'kickbackDetail_id_'+#result.id", condition = "#result != null and #result.id != null")
  public KickbackDetailResponseDto updateKickbackDetail(KickbackDetailRequestDto kickbackDetailRequestDto){
    kickbackDetailRequestDto.setUpdateTime(new Date());
    this.kickbackDetailWriteDao.updateKickbackDetail(kickbackDetailRequestDto);
    KickbackDetailResponseDto kickbackDetailResponseDto = this.kickbackDetailWriteDao.getKickbackDetail(kickbackDetailRequestDto.getId());
    return kickbackDetailResponseDto;
  }

  @CacheEvict(value = "module-order-kickbackDetail", key = "'kickbackDetail_id_'+#id", condition = "#id != null")
  public int deleteKickbackDetail(Long id){
    return this.kickbackDetailWriteDao.deleteKickbackDetail(id);
  }

  @Cacheable(value = "module-order-kickbackDetail", key = "'kickbackDetail_id_'+#id", condition = "#id != null")
  public KickbackDetailResponseDto getKickbackDetail(Long id){
    KickbackDetailResponseDto kickbackDetailResponseDto = this.kickbackDetailReadDao.getKickbackDetail(id);
    return kickbackDetailResponseDto;
  }

  public List<KickbackDetailResponseDto> queryKickbackDetail(KickbackDetailRequestDto kickbackDetailRequestDto){
    Map<String, Object> conditions = starJson.bean2Map(kickbackDetailRequestDto);
    return this.kickbackDetailReadDao.queryKickbackDetail(conditions);
  }

  public Long queryKickbackDetailCount(KickbackDetailRequestDto kickbackDetailRequestDto){
    Map<String, Object> conditions = starJson.bean2Map(kickbackDetailRequestDto);
    return this.kickbackDetailReadDao.queryKickbackDetailCount(conditions);
  }

}