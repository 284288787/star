/**create by framework at 2019年03月30日 10:29:30**/
package com.star.truffle.module.weixin.service;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.module.weixin.cache.CouponRelationCache;
import com.star.truffle.module.weixin.domain.CouponRelation;
import com.star.truffle.module.weixin.dto.req.CouponRelationRequestDto;
import com.star.truffle.module.weixin.dto.res.CouponRelationResponseDto;

@Service
public class CouponRelationService {

  @Autowired
  private CouponRelationCache couponRelationCache;

  public Long saveCouponRelation(CouponRelation couponRelation) {
    this.couponRelationCache.saveCouponRelation(couponRelation);
    return couponRelation.getId();
  }

  public void updateCouponRelation(CouponRelationRequestDto couponRelationRequestDto) {
    this.couponRelationCache.updateCouponRelation(couponRelationRequestDto);
  }

  public void deleteCouponRelation(Long id) {
    this.couponRelationCache.deleteCouponRelation(id);
  }

  public void deleteCouponRelation(String idstr) {
    if (StringUtils.isBlank(idstr)) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    String[] ids = idstr.split(",");
    for (String str : ids) {
      Long id = Long.parseLong(str);
      this.couponRelationCache.deleteCouponRelation(id);
    }
  }

  public CouponRelationResponseDto getCouponRelation(Long id) {
    CouponRelationResponseDto couponRelationResponseDto = this.couponRelationCache.getCouponRelation(id);
    return couponRelationResponseDto;
  }

  public List<CouponRelationResponseDto> queryCouponRelation(CouponRelationRequestDto couponRelationRequestDto) {
    return this.couponRelationCache.queryCouponRelation(couponRelationRequestDto);
  }

  public Long queryCouponRelationCount(CouponRelationRequestDto couponRelationRequestDto) {
    return this.couponRelationCache.queryCouponRelationCount(couponRelationRequestDto);
  }

}