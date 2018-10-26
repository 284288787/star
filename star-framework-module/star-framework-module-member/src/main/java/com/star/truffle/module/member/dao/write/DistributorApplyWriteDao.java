/**create by framework at 2018年10月26日 09:40:50**/
package com.star.truffle.module.member.dao.write;

import java.util.List;
import com.star.truffle.module.member.domain.DistributorApply;
import com.star.truffle.module.member.dto.req.DistributorApplyRequestDto;
import com.star.truffle.module.member.dto.res.DistributorApplyResponseDto;

public interface DistributorApplyWriteDao {

  public int saveDistributorApply(DistributorApply distributorApply);

  public int batchSaveDistributorApply(List<DistributorApply> distributorApplys);

  public int updateDistributorApply(DistributorApplyRequestDto distributorApplyDto);

  public int deleteDistributorApply(Long id);

  public DistributorApplyResponseDto getDistributorApply(Long id);

}