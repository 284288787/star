/**create by liuhua at 2019年1月25日 下午2:21:02**/
package com.star.truffle.module.weixin.dto.req;

import com.star.truffle.core.jdbc.Page;
import com.star.truffle.module.weixin.domain.Coupon;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponRequestDto extends Coupon {

  private Page pager;
}
