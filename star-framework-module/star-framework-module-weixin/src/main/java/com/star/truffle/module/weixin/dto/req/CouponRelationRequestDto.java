/**create by framework at 2019年03月30日 10:29:30**/
package com.star.truffle.module.weixin.dto.req;

import com.star.truffle.core.jdbc.Page;
import lombok.Getter;
import lombok.Setter;
import com.star.truffle.module.weixin.domain.CouponRelation;

@Getter
@Setter
public class CouponRelationRequestDto extends CouponRelation {

  private Page pager;
}