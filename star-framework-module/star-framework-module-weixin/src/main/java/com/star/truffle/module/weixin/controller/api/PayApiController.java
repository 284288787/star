/**create by liuhua at 2018年9月25日 下午2:52:27**/
package com.star.truffle.module.weixin.controller.api;

import java.io.InputStream;
import java.util.Enumeration;
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
  public String callback(HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info(".................微信支付返回:");
    String url = request.getRequestURL().toString();
    log.info(url);
    Enumeration<String> headers = request.getHeaderNames();
    if (null != headers) {
      while (headers.hasMoreElements()) {
        String string = (String) headers.nextElement();
        log.info("-->" + string + "\t" + request.getHeader(string));
      }
    }
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
  
  @RequestMapping(value = "/callback2", method = RequestMethod.GET)
  public String callback2(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String resXml = "<com.star.truffle.module.weixin.domain.PayDetailInfo><appid><![CDATA[wx8a05f2d3eb34111f]]></appid>\r\n" + 
        "<attach><![CDATA[1626]]></attach>\r\n" + 
        "<bank_type><![CDATA[CFT]]></bank_type>\r\n" + 
        "<cash_fee><![CDATA[3954]]></cash_fee>\r\n" + 
        "<coupon_count><![CDATA[1]]></coupon_count>\r\n" + 
        "<coupon_fee>6</coupon_fee>\r\n" + 
        "<coupon_fee_0><![CDATA[6]]></coupon_fee_0>\r\n" + 
        "<coupon_id_0><![CDATA[2000000064794819197]]></coupon_id_0>\r\n" + 
        "<fee_type><![CDATA[CNY]]></fee_type>\r\n" + 
        "<is_subscribe><![CDATA[Y]]></is_subscribe>\r\n" + 
        "<mch_id><![CDATA[1518623831]]></mch_id>\r\n" + 
        "<nonce_str><![CDATA[7030a7227e934db987f316f9acdd646d]]></nonce_str>\r\n" + 
        "<openid><![CDATA[ooQ_o1Sk1f_KjJRxVM_o2wo1Snlw]]></openid>\r\n" + 
        "<out_trade_no><![CDATA[1b941a40d7a24cbeb3a282310ce31403]]></out_trade_no>\r\n" + 
        "<result_code><![CDATA[SUCCESS]]></result_code>\r\n" + 
        "<return_code><![CDATA[SUCCESS]]></return_code>\r\n" + 
        "<sign><![CDATA[3F447EADA5FECCBC14837B92E88F4FB5]]></sign>\r\n" + 
        "<time_end><![CDATA[20181212125628]]></time_end>\r\n" + 
        "<total_fee>3960</total_fee>\r\n" + 
        "<trade_type><![CDATA[JSAPI]]></trade_type>\r\n" + 
        "<transaction_id><![CDATA[4200000208201812123486625781]]></transaction_id>\r\n" + 
        "</com.star.truffle.module.weixin.domain.PayDetailInfo>";
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
