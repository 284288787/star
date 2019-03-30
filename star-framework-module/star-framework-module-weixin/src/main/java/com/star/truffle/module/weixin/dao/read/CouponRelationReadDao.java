/**create by framework at 2019年03月30日 10:29:30**/
package com.star.truffle.module.weixin.dao.read;

import java.util.List;
import java.util.Map;
import com.star.truffle.module.weixin.dto.res.CouponRelationResponseDto;

public interface CouponRelationReadDao {

  public CouponRelationResponseDto getCouponRelation(Long id);

  public List<CouponRelationResponseDto> queryCouponRelation(Map<String, Object> conditions);

  public Long queryCouponRelationCount(Map<String, Object> conditions);

}