/**create by framework at 2019年03月30日 10:29:30**/
package com.star.truffle.module.weixin.cache;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.module.weixin.dao.read.CouponRelationReadDao;
import com.star.truffle.module.weixin.dao.write.CouponRelationWriteDao;
import com.star.truffle.module.weixin.domain.CouponRelation;
import com.star.truffle.module.weixin.dto.req.CouponRelationRequestDto;
import com.star.truffle.module.weixin.dto.res.CouponRelationResponseDto;

@Service
public class CouponRelationCache {

  @Autowired
  private StarJson starJson;
  @Autowired
  private CouponRelationWriteDao couponRelationWriteDao;
  @Autowired
  private CouponRelationReadDao couponRelationReadDao;

  @CachePut(value = "module-weixin-couponRelation", key = "'couponRelation_id_'+#result.id", condition = "#result != null and #result.id != null")
  public CouponRelationResponseDto saveCouponRelation(CouponRelation couponRelation){
    this.couponRelationWriteDao.saveCouponRelation(couponRelation);
    CouponRelationResponseDto couponRelationResponseDto = this.couponRelationWriteDao.getCouponRelation(couponRelation.getId());
    return couponRelationResponseDto;
  }

  @CachePut(value = "module-weixin-couponRelation", key = "'couponRelation_id_'+#result.id", condition = "#result != null and #result.id != null")
  public CouponRelationResponseDto updateCouponRelation(CouponRelationRequestDto couponRelationRequestDto){
    this.couponRelationWriteDao.updateCouponRelation(couponRelationRequestDto);
    CouponRelationResponseDto couponRelationResponseDto = this.couponRelationWriteDao.getCouponRelation(couponRelationRequestDto.getId());
    return couponRelationResponseDto;
  }

  @CacheEvict(value = "module-weixin-couponRelation", key = "'couponRelation_id_'+#id", condition = "#id != null")
  public int deleteCouponRelation(Long id){
    return this.couponRelationWriteDao.deleteCouponRelation(id);
  }

  @Cacheable(value = "module-weixin-couponRelation", key = "'couponRelation_id_'+#id", condition = "#id != null")
  public CouponRelationResponseDto getCouponRelation(Long id){
    CouponRelationResponseDto couponRelationResponseDto = this.couponRelationReadDao.getCouponRelation(id);
    return couponRelationResponseDto;
  }

  public List<CouponRelationResponseDto> queryCouponRelation(CouponRelationRequestDto couponRelationRequestDto){
    Map<String, Object> conditions = starJson.bean2Map(couponRelationRequestDto);
    return this.couponRelationReadDao.queryCouponRelation(conditions);
  }

  public Long queryCouponRelationCount(CouponRelationRequestDto couponRelationRequestDto){
    Map<String, Object> conditions = starJson.bean2Map(couponRelationRequestDto);
    return this.couponRelationReadDao.queryCouponRelationCount(conditions);
  }

}