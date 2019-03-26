/**create by framework at 2019年03月25日 14:18:36**/
package com.star.truffle.module.weixin.cache;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.module.weixin.dao.read.CouponReadDao;
import com.star.truffle.module.weixin.dao.write.CouponWriteDao;
import com.star.truffle.module.weixin.domain.Coupon;
import com.star.truffle.module.weixin.dto.req.CouponRequestDto;
import com.star.truffle.module.weixin.dto.res.CouponResponseDto;

@Service
public class CouponCache {

  @Autowired
  private StarJson starJson;
  @Autowired
  private CouponWriteDao couponWriteDao;
  @Autowired
  private CouponReadDao couponReadDao;

//  @CachePut(value = "module-weixin-coupon", key = "'coupon_couponId_'+#result.couponId", condition = "#result != null and #result.couponId != null")
  public CouponResponseDto saveCoupon(Coupon coupon){
    this.couponWriteDao.saveCoupon(coupon);
    CouponResponseDto couponResponseDto = this.couponWriteDao.getCoupon(coupon.getCouponId());
    return couponResponseDto;
  }

//  @CachePut(value = "module-weixin-coupon", key = "'coupon_couponId_'+#result.couponId", condition = "#result != null and #result.couponId != null")
  public CouponResponseDto updateCoupon(CouponRequestDto couponRequestDto){
    this.couponWriteDao.updateCoupon(couponRequestDto);
    CouponResponseDto couponResponseDto = this.couponWriteDao.getCoupon(couponRequestDto.getCouponId());
    return couponResponseDto;
  }

//  @CacheEvict(value = "module-weixin-coupon", key = "'coupon_couponId_'+#couponId", condition = "#couponId != null")
  public int deleteCoupon(Long couponId){
    return this.couponWriteDao.deleteCoupon(couponId);
  }

//  @Cacheable(value = "module-weixin-coupon", key = "'coupon_couponId_'+#couponId", condition = "#couponId != null")
  public CouponResponseDto getCoupon(Long couponId){
    CouponResponseDto couponResponseDto = this.couponReadDao.getCoupon(couponId);
    return couponResponseDto;
  }

  public List<CouponResponseDto> queryCoupon(CouponRequestDto couponRequestDto){
    Map<String, Object> conditions = starJson.bean2Map(couponRequestDto);
    return this.couponReadDao.queryCoupon(conditions);
  }

  public Long queryCouponCount(CouponRequestDto couponRequestDto){
    Map<String, Object> conditions = starJson.bean2Map(couponRequestDto);
    return this.couponReadDao.queryCouponCount(conditions);
  }

}