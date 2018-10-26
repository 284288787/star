/**create by framework at 2018年10月26日 09:40:50**/
package com.star.truffle.module.member.dao.read;

import java.util.List;
import java.util.Map;
import com.star.truffle.module.member.dto.res.DistributorApplyResponseDto;

public interface DistributorApplyReadDao {

  public DistributorApplyResponseDto getDistributorApply(Long id);

  public List<DistributorApplyResponseDto> queryDistributorApply(Map<String, Object> conditions);

  public Long queryDistributorApplyCount(Map<String, Object> conditions);

}