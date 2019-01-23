/**create by liuhua at 2018年9月7日 上午9:51:26**/
package com.star.truffle.module.weixin.controller.api;

import java.io.InputStream;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.web.ApiResult;
import com.star.truffle.module.weixin.domain.WeixinConfig;
import com.star.truffle.module.weixin.dto.res.WeixinUserInfo;
import com.star.truffle.module.weixin.service.TicketService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/weixin/ticket")
@Api(tags = "微信凭证")
public class TicketApiController {

  @Autowired
  private TicketService ticketService;

  @RequestMapping(value = "/getWeixinUserInfo", method = RequestMethod.POST)
  @ApiOperation(value = "获取微信用户信息", notes = "获取微信用户信息", httpMethod = "POST", response = ApiResult.class)
  @ApiImplicitParams({ 
    @ApiImplicitParam(name = "code", value = "code", paramType = "query", required = true, dataType = "String"), 
  })
  public ApiResult<WeixinUserInfo> getWeixinUserInfo(String code) throws Exception {
    try {
      WeixinUserInfo weixinUserInfo = ticketService.getWeixinUserInfo(code);
      return ApiResult.success(weixinUserInfo);
    } catch (StarServiceException e) {
      return ApiResult.fail(e);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail();
    }
  }

  @RequestMapping(value = "/jssdk", method = RequestMethod.POST)
  @ApiOperation(value = "获取js sdk 参数", notes = "获取js sdk 参数", httpMethod = "POST", response = WeixinConfig.class)
  @ApiImplicitParams({
      @ApiImplicitParam(name = "url", value = "当前打开页面的完整url", paramType = "query", required = true, dataType = "String"),
  })
  public ApiResult<WeixinConfig> ojssdk(String url) throws Exception {
    try {
      WeixinConfig config = ticketService.jssdk(url);
      return ApiResult.success(config);
    } catch (StarServiceException e) {
      return ApiResult.fail(e);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResult.fail();
    }
  }
  
  @RequestMapping(value = "/serverConfig", method = { RequestMethod.POST, RequestMethod.GET })
  @ApiOperation(value = "微信服务器配置认证", notes = "微信服务器配置认证", httpMethod = "POST", response = ApiResult.class)
  public String serverConfig(String signature, String timestamp, String nonce, String echostr, HttpServletRequest request) throws Exception {
    log.info(".................微信服务器配置认证:");
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
    if (ticketService.checkSignature(signature, timestamp, nonce)) {
      return echostr;
    }
    return null;
  }
}
