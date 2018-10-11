/**create by framework at 2018年10月11日 11:07:21**/
package com.star.truffle.module.order.dao.write;

import java.util.List;
import com.star.truffle.module.order.domain.KickbackDetail;
import com.star.truffle.module.order.dto.req.KickbackDetailRequestDto;
import com.star.truffle.module.order.dto.res.KickbackDetailResponseDto;

public interface KickbackDetailWriteDao {

  public int saveKickbackDetail(KickbackDetail kickbackDetail);

  public int batchSaveKickbackDetail(List<KickbackDetail> kickbackDetails);

  public int updateKickbackDetail(KickbackDetailRequestDto kickbackDetailDto);

  public int deleteKickbackDetail(Long id);

  public KickbackDetailResponseDto getKickbackDetail(Long id);

}