/**create by framework at 2018年10月11日 11:07:21**/
package com.star.truffle.module.order.service;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.jdbc.Page;
import com.star.truffle.core.util.DateUtils;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.module.order.cache.KickbackDetailCache;
import com.star.truffle.module.order.cache.OrderCache;
import com.star.truffle.module.order.constant.KickbackStateEnum;
import com.star.truffle.module.order.constant.OrderStateEnum;
import com.star.truffle.module.order.domain.KickbackDetail;
import com.star.truffle.module.order.dto.req.KickbackDetailRequestDto;
import com.star.truffle.module.order.dto.req.OrderRequestDto;
import com.star.truffle.module.order.dto.res.KickbackDetailResponseDto;
import com.star.truffle.module.order.dto.res.OrderResponseDto;
import com.star.truffle.module.order.properties.OrderProperties;

@Service
public class KickbackDetailService {

  @Autowired
  private KickbackDetailCache kickbackDetailCache;
  @Autowired
  private OrderCache orderCache;
  @Autowired
  private OrderProperties orderProperties;

  public void apply(Long distributorId) {
    Date beginTime = null;
    KickbackDetailResponseDto last = this.kickbackDetailCache.getLatestKickbackDetail(distributorId);
    if (null != last) {
      beginTime = last.getPointEndTime();
      if (last.getState() == KickbackStateEnum.auditing.state()) {
        throw new StarServiceException(ApiCode.PARAM_ERROR, "已有一笔提现申请正在处理");
      }else if (last.getState() == KickbackStateEnum.nopass.state()) {
        beginTime = last.getPointBeginTime();
      }
    }else {
      beginTime = DateUtils.toDateYmd("2018-10-10"); //这个时间项目还没上线
    }
    Map<String, Object> map = orderCache.totalMoney(distributorId, beginTime);
    Integer totalMoney = Integer.parseInt(map.get("totalMoney").toString());
    if (totalMoney < orderProperties.getKickbackMoney()) {
      DecimalFormat decimalFormat = new DecimalFormat("0.00");
      throw new StarServiceException(ApiCode.PARAM_ERROR, "暂不可提现，可提现金额须达到" + decimalFormat.format(orderProperties.getKickbackMoney() / 100.0)+ "元。");
    }
    Date pointEndTime = DateUtils.toDateYmdHms(map.get("time").toString());
    KickbackDetail kickbackDetail = new KickbackDetail();
    kickbackDetail.setDistributorId(distributorId);
    kickbackDetail.setPointBeginTime(beginTime);
    kickbackDetail.setPointEndTime(pointEndTime);
    kickbackDetail.setTotalMoney(totalMoney);
    kickbackDetail.setState(KickbackStateEnum.auditing.state());
    kickbackDetail.setCreateTime(new Date());
    kickbackDetail.setUpdateTime(kickbackDetail.getCreateTime());
    this.kickbackDetailCache.saveKickbackDetail(kickbackDetail);
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
    List<OrderResponseDto> orders = orderCache.queryOrder(orderRequestDto);
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
    Long count = orderCache.queryOrderCount(orderRequestDto);
    return count;
  }

  public Map<String, Object> getDistributorMoney(Long distributorId) {
    Map<String, Object> map = new HashMap<>();
    //1.获取他最后的一条记录，根据状态计算
    //2.从上面得到最近时间，然后从订单里统计到现在的回扣和
    //3.返回起止时间、审核中的金额、
    Integer auditingMoney = 0;
    Date beginTime = null;
    Integer state = 0;
    KickbackDetailResponseDto last = this.kickbackDetailCache.getLatestKickbackDetail(distributorId);
    if (null != last) {
      beginTime = last.getPointEndTime();
      if (last.getState() == KickbackStateEnum.auditing.state()) {
        auditingMoney = last.getTotalMoney();
      }else if (last.getState() == KickbackStateEnum.nopass.state()) {
        beginTime = last.getPointBeginTime();
      }
      state = last.getState();
    }else {
      beginTime = DateUtils.toDateYmd("2018-10-10"); //这个时间项目还没上线
    }
    Map<String, Object> totalMoney = orderCache.totalMoney(distributorId, beginTime);
    map.put("auditingMoney", auditingMoney);
    map.put("beginTime", beginTime);
    map.put("totalMoeny", totalMoney.get("totalMoney"));
    map.put("state", state);
    return map;
  }

}