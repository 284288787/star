/**create by framework at 2018年10月11日 11:07:21**/
package com.star.truffle.module.order.service;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.module.order.cache.KickbackDetailCache;
import com.star.truffle.module.order.domain.KickbackDetail;
import com.star.truffle.module.order.dto.req.KickbackDetailRequestDto;
import com.star.truffle.module.order.dto.res.KickbackDetailResponseDto;

@Service
public class KickbackDetailService {

  @Autowired
  private KickbackDetailCache kickbackDetailCache;

  public Long saveKickbackDetail(KickbackDetail kickbackDetail) {
    this.kickbackDetailCache.saveKickbackDetail(kickbackDetail);
    return kickbackDetail.getId();
  }

  public void updateKickbackDetail(KickbackDetailRequestDto kickbackDetailRequestDto) {
    this.kickbackDetailCache.updateKickbackDetail(kickbackDetailRequestDto);
  }

  public void deleteKickbackDetail(Long id) {
    this.kickbackDetailCache.deleteKickbackDetail(id);
  }

  public void deleteKickbackDetail(String idstr) {
    if (StringUtils.isBlank(idstr)) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    String[] ids = idstr.split(",");
    for (String str : ids) {
      Long id = Long.parseLong(str);
      this.kickbackDetailCache.deleteKickbackDetail(id);
    }
  }

  public KickbackDetailResponseDto getKickbackDetail(Long id) {
    KickbackDetailResponseDto kickbackDetailResponseDto = this.kickbackDetailCache.getKickbackDetail(id);
    return kickbackDetailResponseDto;
  }

  public List<KickbackDetailResponseDto> queryKickbackDetail(KickbackDetailRequestDto kickbackDetailRequestDto) {
    return this.kickbackDetailCache.queryKickbackDetail(kickbackDetailRequestDto);
  }

  public Long queryKickbackDetailCount(KickbackDetailRequestDto kickbackDetailRequestDto) {
    return this.kickbackDetailCache.queryKickbackDetailCount(kickbackDetailRequestDto);
  }

}