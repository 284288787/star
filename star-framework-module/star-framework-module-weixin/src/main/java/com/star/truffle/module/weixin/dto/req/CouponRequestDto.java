/**create by framework at 2019年03月25日 14:18:36**/
package com.star.truffle.module.weixin.dto.req;

import com.star.truffle.core.jdbc.Page;
import lombok.Getter;
import lombok.Setter;
import com.star.truffle.module.weixin.domain.Coupon;

@Getter
@Setter
public class CouponRequestDto extends Coupon {

  private Page pager;
}