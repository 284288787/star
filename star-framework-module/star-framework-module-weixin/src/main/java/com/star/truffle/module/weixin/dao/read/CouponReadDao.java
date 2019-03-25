/**create by framework at 2019年03月25日 14:18:36**/
package com.star.truffle.module.weixin.dao.read;

import java.util.List;
import java.util.Map;
import com.star.truffle.module.weixin.dto.res.CouponResponseDto;

public interface CouponReadDao {

  public CouponResponseDto getCoupon(Long couponId);

  public List<CouponResponseDto> queryCoupon(Map<String, Object> conditions);

  public Long queryCouponCount(Map<String, Object> conditions);

}