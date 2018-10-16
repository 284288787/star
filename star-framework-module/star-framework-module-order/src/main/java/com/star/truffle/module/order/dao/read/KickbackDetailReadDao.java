/**create by framework at 2018年10月11日 11:07:21**/
package com.star.truffle.module.order.dao.read;

import java.util.List;
import java.util.Map;
import com.star.truffle.module.order.dto.res.KickbackDetailResponseDto;

public interface KickbackDetailReadDao {

  public KickbackDetailResponseDto getKickbackDetail(Long id);

  public List<KickbackDetailResponseDto> queryKickbackDetail(Map<String, Object> conditions);

  public Long queryKickbackDetailCount(Map<String, Object> conditions);

  public KickbackDetailResponseDto getLatestKickbackDetail(Long distributorId);

}