/**create by liuhua at 2018年9月25日 下午2:52:27**/
package com.star.truffle.module.weixin.controller.api;

import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.util.HttpUtils;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.core.web.ApiResult;
import com.star.truffle.module.weixin.domain.PayDetailInfo;
import com.star.truffle.module.weixin.service.PayService;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/weixin/pay")
@Api(tags = "微信支付")
public class PayApiController {

  @Autowired
  private PayService payService;
  
  @RequestMapping(value = "/unifiedOrder", method = RequestMethod.POST)
  @ApiOperation(value = "统一下单", notes = "返回订单号，金额等信息，使客户端完成支付", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "memberId", value = "会员Id", dataType = "Long", required = true, paramType = "query"),
    @ApiImplicitParam(name = "openId", value = "openid", dataType = "String", required = true, paramType = "query"),
    @ApiImplicitParam(name = "orderId", value = "主键", dataType = "Long", required = true, paramType = "query")
  })
  public ApiResult<Map<String, Object>> unifiedOrder(Long memberId, String openId, Long orderId, HttpServletRequest request) {
    try {
      String ip = HttpUtils.getIpAddress(request);
      Map<String, Object> payInfo = payService.unifiedOrder(memberId, openId, orderId, ip);
      return ApiResult.success(payInfo);
    } catch (StarServiceException e) {
      return ApiResult.fail(e.getCode(), e.getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
    }
  }
  
//  @RequestMapping(value = "/finishOrder", method = RequestMethod.POST)
//  @ApiOperation(value = "统一下单", notes = "返回订单号，金额等信息，使客户端完成支付", httpMethod = "POST", response = ApiResult.class)
//  @ApiImplicitParams({
//    @ApiImplicitParam(name = "orderId", value = "主键", dataType = "Long", required = true, paramType = "query")
//  })
//  public ApiResult<Boolean> finishOrder(String orderId) {
//    try {
//      boolean payInfo = payService.finishOrder(orderId);
//      return ApiResult.success(payInfo);
//    } catch (StarServiceException e) {
//      return ApiResult.fail(e.getCode(), e.getMsg());
//    } catch (Exception e) {
//      log.error(e.getMessage(), e);
//      return ApiResult.fail(ApiCode.SYSTEM_ERROR);
//    }
//  }
  
  @RequestMapping(value = "/callback", method = RequestMethod.POST)
  @ApiOperation(value = "统一下单", notes = "返回订单号，金额等信息，使客户端完成支付", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "memberId", value = "会员Id", dataType = "Long", required = true, paramType = "query"),
    @ApiImplicitParam(name = "openId", value = "openid", dataType = "String", required = true, paramType = "query"),
    @ApiImplicitParam(name = "orderId", value = "主键", dataType = "Long", required = true, paramType = "query")
  })
  public String callback(HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info(".................微信支付返回");
    InputStream is = request.getInputStream();
    StringBuffer sBuffer = new StringBuffer();
    byte[] b = new byte[1024];
    int len = 0;
    while ((len = is.read(b)) != -1) {
        sBuffer.append(new String(b, 0, len));
    }
    String resXml = sBuffer.toString();
    resXml = resXml.replace("<xml>", "<com.star.truffle.module.weixin.domain.PayDetailInfo>").replace("</xml>", "</com.star.truffle.module.weixin.domain.PayDetailInfo>");
    log.info(resXml);
    XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
    PayDetailInfo payOrder = (PayDetailInfo) xStreamForRequestPostData.fromXML(resXml);
    boolean bool = payService.payCallback(payOrder);
    if (bool) {
        return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
    } else {
        return "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[FAIL]]></return_msg></xml>";
    }
}
}
