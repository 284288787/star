/**create by framework at 2019年03月25日 14:18:36**/
package com.star.truffle.module.weixin.service;

import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.module.weixin.cache.CouponCache;
import com.star.truffle.module.weixin.domain.Coupon;
import com.star.truffle.module.weixin.dto.req.CouponRequestDto;
import com.star.truffle.module.weixin.dto.res.CouponResponseDto;

@Service
public class CouponService {

  @Autowired
  private CouponCache couponCache;

  public Long saveCoupon(Coupon coupon) {
    if (null == coupon || StringUtils.isBlank(coupon.getTitle()) || StringUtils.isBlank(coupon.getCardId())) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    coupon.setCreateTime(new Date());
    this.couponCache.saveCoupon(coupon);
    return coupon.getCouponId();
  }

  public void updateCoupon(CouponRequestDto couponRequestDto) {
    if (null == couponRequestDto || null == couponRequestDto.getCouponId()) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    this.couponCache.updateCoupon(couponRequestDto);
  }

  public void deleteCoupon(Long couponId) {
    this.couponCache.deleteCoupon(couponId);
  }

  public void deleteCoupon(String idstr) {
    if (StringUtils.isBlank(idstr)) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    String[] couponIds = idstr.split(",");
    for (String str : couponIds) {
      Long couponId = Long.parseLong(str);
      this.couponCache.deleteCoupon(couponId);
    }
  }

  public CouponResponseDto getCoupon(Long couponId) {
    CouponResponseDto couponResponseDto = this.couponCache.getCoupon(couponId);
    return couponResponseDto;
  }

  public List<CouponResponseDto> queryCoupon(CouponRequestDto couponRequestDto) {
    return this.couponCache.queryCoupon(couponRequestDto);
  }

  public Long queryCouponCount(CouponRequestDto couponRequestDto) {
    return this.couponCache.queryCouponCount(couponRequestDto);
  }

}