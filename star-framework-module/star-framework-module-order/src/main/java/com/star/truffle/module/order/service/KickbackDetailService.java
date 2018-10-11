/**create by framework at 2018年10月11日 11:07:21**/
package com.star.truffle.module.order.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.jdbc.Page;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.module.order.cache.KickbackDetailCache;
import com.star.truffle.module.order.constant.KickbackStateEnum;
import com.star.truffle.module.order.constant.OrderStateEnum;
import com.star.truffle.module.order.domain.KickbackDetail;
import com.star.truffle.module.order.dto.req.KickbackDetailRequestDto;
import com.star.truffle.module.order.dto.req.OrderRequestDto;
import com.star.truffle.module.order.dto.res.KickbackDetailResponseDto;
import com.star.truffle.module.order.dto.res.OrderResponseDto;

@Service
public class KickbackDetailService {

  @Autowired
  private KickbackDetailCache kickbackDetailCache;
  @Autowired
  private OrderService orderService;

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

  public void changeState(Long id, int state, String reject) {
    if (null == id || (state == KickbackStateEnum.nopass.state() && StringUtils.isBlank(reject))) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    KickbackDetailResponseDto responseDto = kickbackDetailCache.getKickbackDetail(id);
    if (null == responseDto) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "记录不存在");
    }
    KickbackDetailRequestDto kickbackDetailRequestDto = new KickbackDetailRequestDto();
    kickbackDetailRequestDto.setId(id);
    kickbackDetailRequestDto.setState(state);
    kickbackDetailRequestDto.setReject(reject);
    this.kickbackDetailCache.updateKickbackDetail(kickbackDetailRequestDto);
  }

  public List<OrderResponseDto> detail(Long id, Page pager) {
    if (null == id) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    KickbackDetailResponseDto responseDto = kickbackDetailCache.getKickbackDetail(id);
    if (null == responseDto) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "记录不存在");
    }
    OrderRequestDto orderRequestDto = new OrderRequestDto();
    orderRequestDto.setPager(pager);
    orderRequestDto.setStates(OrderStateEnum.nosend.state() + "," + OrderStateEnum.success.state());
    orderRequestDto.setDistributorId(responseDto.getDistributorId());
    orderRequestDto.setBeginCreateTime(responseDto.getPointBeginTime());
    orderRequestDto.setEndCreateTime(responseDto.getPointEndTime());
    orderRequestDto.setBrokerage0(0);
    List<OrderResponseDto> orders = orderService.queryOrder(orderRequestDto);
    return orders;
  }

  public Long detailCount(Long id, Page pager) {
    if (null == id) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    KickbackDetailResponseDto responseDto = kickbackDetailCache.getKickbackDetail(id);
    if (null == responseDto) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "记录不存在");
    }
    OrderRequestDto orderRequestDto = new OrderRequestDto();
    orderRequestDto.setPager(pager);
    orderRequestDto.setStates(OrderStateEnum.nosend.state() + "," + OrderStateEnum.success.state());
    orderRequestDto.setDistributorId(responseDto.getDistributorId());
    orderRequestDto.setBeginCreateTime(responseDto.getPointBeginTime());
    orderRequestDto.setEndCreateTime(responseDto.getPointEndTime());
    Long count = orderService.queryOrderCount(orderRequestDto);
    return count;
  }

}