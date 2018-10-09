/**create by liuhua at 2018年9月25日 下午2:54:04**/
package com.star.truffle.module.weixin.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.module.alibaba.service.SmsUtil;
import com.star.truffle.module.member.dto.res.MemberResponseDto;
import com.star.truffle.module.member.service.MemberService;
import com.star.truffle.module.order.constant.DeliveryTypeEnum;
import com.star.truffle.module.order.constant.OrderStateEnum;
import com.star.truffle.module.order.domain.OrderDetail;
import com.star.truffle.module.order.dto.req.OrderRequestDto;
import com.star.truffle.module.order.dto.res.OrderResponseDto;
import com.star.truffle.module.order.service.OrderService;
import com.star.truffle.module.product.service.ProductService;
import com.star.truffle.module.weixin.dao.WeiXinApiDao;
import com.star.truffle.module.weixin.dao.write.PayDetailInfoWriteDao;
import com.star.truffle.module.weixin.domain.PayDetailInfo;
import com.star.truffle.module.weixin.dto.req.PayReqData;
import com.star.truffle.module.weixin.dto.res.PayResData;
import com.star.truffle.module.weixin.intf.WeixinConfigIntf;

@Service
public class PayService {

  @Autowired
  private StarJson starJson;
  @Autowired
  private OrderService orderService;
  @Autowired
  private MemberService memberService;
  @Autowired
  private ProductService productService;
  @Autowired
  private WeiXinApiDao weiXinApiDao;
  @Autowired
  private PayDetailInfoWriteDao payDetailInfoWriteDao;
  @Autowired
  private WeixinConfigIntf weixinConfig;

  public Map<String, Object> unifiedOrder(Long memberId, String openId, Long orderId, String ip) {
    if (null == memberId || StringUtils.isBlank(openId) || null == orderId) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    MemberResponseDto member = memberService.getMember(memberId);
    if (null == member) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "会员不存在");
    }
    if (!openId.equals(member.getOpenId())) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "会员Id和openId不匹配");
    }
    OrderResponseDto order = orderService.getOrder(orderId);
    if (null == order) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "订单不存在");
    }
    if (order.getMemberId() != memberId.longValue()) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "订单和会员不匹配");
    }
    if (order.getState() != OrderStateEnum.nopay.state()) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "该订单不能支付，可能已支付或已过期");
    }

    String outTradeNo = UUID.randomUUID().toString().replace("-", "");
    Integer money = order.getTotalMoney() + (null == order.getDespatchMoney() ? 0 : order.getDespatchMoney());
    PayReqData prd = new PayReqData(order.getShopName(), order.getOrderId().toString(), outTradeNo, money, order.getCreateTime(), weixinConfig.getAppId(), weixinConfig.getKey(), weixinConfig.getMchId(), weixinConfig.getNotifyUrl(), ip, weixinConfig.getTradeType(), order.getOpenId());
    PayResData res = weiXinApiDao.unifiedOrderGZH(prd);
    Map<String, Object> payInfo = starJson.bean2Map(res);
    payInfo.remove("appid");
    payInfo.remove("mch_id");
    return payInfo;
  }

  public boolean payCallback(PayDetailInfo payDetailInfo) {
    boolean bool = false;
    this.payDetailInfoWriteDao.savePayDetailInfo(payDetailInfo);
    if ("SUCCESS".equals(payDetailInfo.getResult_code())) {
      bool = hasFinish(payDetailInfo.getAttach());
      if (!bool) {
        bool = finishOrder(payDetailInfo.getAttach());
      }
    }
    return bool;
  }

  private boolean finishOrder(String orderId) {
    if (StringUtils.isNotBlank(orderId)) {
      OrderResponseDto order = orderService.getOrder(Long.parseLong(orderId));
      if (null != order) {
        OrderRequestDto orderRequestDto = new OrderRequestDto();
        orderRequestDto.setOrderId(order.getOrderId());
        orderRequestDto.setState(OrderStateEnum.nosend.state());
        if (order.getDeliveryType() == DeliveryTypeEnum.self.type()) {
          orderRequestDto.setPickupCode(SmsUtil.buildCode(4));
        }
        orderService.updateOrder(orderRequestDto);
        List<OrderDetail> details = order.getDetails();
        if (null != details && ! details.isEmpty()) {
          for (OrderDetail orderDetail : details) {
            productService.updateProductSoldNumber(orderDetail.getProductId(), orderDetail.getCount());
          }
        }
      }
    }
    return false;
  }

  private boolean hasFinish(String orderId) {
    if (StringUtils.isNotBlank(orderId)) {
      OrderResponseDto order = orderService.getOrder(Long.parseLong(orderId));
      if (null != order) {
        if (order.getState() == OrderStateEnum.nosend.state()) {
          return true;
        }
      }
    }
    return false;
  }

}