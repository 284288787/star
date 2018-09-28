/**create by framework at 2018年09月18日 11:52:26**/
package com.star.truffle.module.member.dao.read;

import java.util.List;
import java.util.Map;
import com.star.truffle.module.member.dto.res.DistributorResponseDto;

public interface DistributorReadDao {

  public DistributorResponseDto getDistributor(Long distributorId);

  public List<DistributorResponseDto> queryDistributor(Map<String, Object> conditions);

  public Long queryDistributorCount(Map<String, Object> conditions);

  public DistributorResponseDto getDistributorByOpenId(String openId);

}