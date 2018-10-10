/**create by framework at 2018年09月18日 11:52:26**/
package com.star.truffle.module.member.dao.write;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.star.truffle.module.member.domain.Distributor;
import com.star.truffle.module.member.dto.req.DistributorRequestDto;
import com.star.truffle.module.member.dto.res.DistributorResponseDto;

public interface DistributorWriteDao {

  public int saveDistributor(Distributor distributor);

  public int batchSaveDistributor(List<Distributor> distributors);

  public int updateDistributor(DistributorRequestDto distributorDto);

  public int deleteDistributor(Long distributorId);

  public DistributorResponseDto getDistributor(Long distributorId);

  public int addDistributorNum(@Param("distributorId") Long distributorId, @Param("type") String type, @Param("num") int num);

}