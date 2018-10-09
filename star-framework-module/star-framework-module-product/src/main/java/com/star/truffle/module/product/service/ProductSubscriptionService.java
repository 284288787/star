/**create by framework at 2018年09月18日 10:59:57**/
package com.star.truffle.module.product.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.module.member.constant.LoginStateEnum;
import com.star.truffle.module.member.dto.res.MemberResponseDto;
import com.star.truffle.module.member.service.MemberService;
import com.star.truffle.module.product.cache.ProductSubscriptionCache;
import com.star.truffle.module.product.constant.ProductEnum;
import com.star.truffle.module.product.domain.ProductSubscription;
import com.star.truffle.module.product.dto.req.ProductRequestDto;
import com.star.truffle.module.product.dto.req.ProductSubscriptionRequestDto;
import com.star.truffle.module.product.dto.res.ProductResponseDto;
import com.star.truffle.module.product.dto.res.ProductSubscriptionResponseDto;

@Service
public class ProductSubscriptionService {

  @Autowired
  private ProductSubscriptionCache productSubscriptionCache;
  @Autowired
  private ProductService productService;
  @Autowired
  private MemberService memberService;

  public Long saveProductSubscription(ProductSubscription productSubscription) {
    this.productSubscriptionCache.saveProductSubscription(productSubscription);
    return productSubscription.getId();
  }

  public void updateProductSubscription(ProductSubscriptionRequestDto productSubscriptionRequestDto) {
    this.productSubscriptionCache.updateProductSubscription(productSubscriptionRequestDto);
  }

  public void deleteProductSubscription(Long id) {
    this.productSubscriptionCache.deleteProductSubscription(id);
  }

  public void deleteProductSubscription(String idstr) {
    if (StringUtils.isBlank(idstr)) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    String[] ids = idstr.split(",");
    for (String str : ids) {
      Long id = Long.parseLong(str);
      this.productSubscriptionCache.deleteProductSubscription(id);
    }
  }

  public ProductSubscriptionResponseDto getProductSubscription(Long id) {
    ProductSubscriptionResponseDto productSubscriptionResponseDto = this.productSubscriptionCache.getProductSubscription(id);
    return productSubscriptionResponseDto;
  }

  public List<ProductSubscriptionResponseDto> queryProductSubscription(ProductSubscriptionRequestDto productSubscriptionRequestDto) {
    return this.productSubscriptionCache.queryProductSubscription(productSubscriptionRequestDto);
  }

  public Long queryProductSubscriptionCount(ProductSubscriptionRequestDto productSubscriptionRequestDto) {
    return this.productSubscriptionCache.queryProductSubscriptionCount(productSubscriptionRequestDto);
  }

  public void subscribe(Long productId, String openId) {
    MemberResponseDto member = memberService.getMemberByOpenId(openId);
    if (null == member) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "用户不存在");
    }
    if (member.getState() == LoginStateEnum.logout.getState()) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "用户未登录");
    }
    ProductResponseDto product = productService.getProduct(productId);
    if (null == product || product.getState() == ProductEnum.deleted.state()) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "供应记录不存在");
    }
    ProductSubscription productSubscription = new ProductSubscription(); 
    productSubscription.setProductId(productId);
    productSubscription.setMemberId(member.getMemberId()); 
    productSubscription.setCreateTime(new Date());
    this.productSubscriptionCache.saveProductSubscription(productSubscription);
    ProductRequestDto param = new ProductRequestDto();
    param.setProductId(productId);
    param.setSubscribers(product.getSubscribers() + 1);
    this.productService.updateProduct(param);
  }

  public void unsubscribe(Long productId, String openId) {
    MemberResponseDto member = memberService.getMemberByOpenId(openId);
    if (null == member) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "用户不存在");
    }
    if (member.getState() == LoginStateEnum.logout.getState()) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "用户未登录");
    }
    ProductResponseDto product = productService.getProduct(productId);
    if (null == product) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "供应记录不存在");
    }
    ProductSubscriptionRequestDto productSubscriptionRequestDto = new ProductSubscriptionRequestDto();
    productSubscriptionRequestDto.setMemberId(member.getMemberId());
    productSubscriptionRequestDto.setProductId(productId);
    List<ProductSubscriptionResponseDto> list = productSubscriptionCache.queryProductSubscription(productSubscriptionRequestDto);
    if (null == list || list.isEmpty()) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "未关注过");
    }
    for (ProductSubscriptionResponseDto productSubscriptionResponseDto : list) {
      productSubscriptionCache.deleteProductSubscription(productSubscriptionResponseDto.getId());
    }
    ProductRequestDto param = new ProductRequestDto();
    param.setProductId(productId);
    param.setSubscribers(product.getSubscribers() - 1);
    this.productService.updateProduct(param);
  }

  public Map<Long, Boolean> isSubscription(String productIds, String openId) {
    if (StringUtils.isBlank(productIds) || StringUtils.isBlank(openId)) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    MemberResponseDto member = memberService.getMemberByOpenId(openId);
    if (null == member) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "用户不存在");
    }
    if (member.getState() == LoginStateEnum.logout.getState()) {
      throw new StarServiceException(ApiCode.PARAM_ERROR, "用户未登录");
    }
    ProductSubscriptionRequestDto productSubscriptionRequestDto = new ProductSubscriptionRequestDto();
    productSubscriptionRequestDto.setMemberId(member.getMemberId());
    List<ProductSubscriptionResponseDto> list = productSubscriptionCache.queryProductSubscription(productSubscriptionRequestDto);
    Map<Long, Boolean> map = new HashMap<>();
    if (null != list && ! list.isEmpty()) {
      String temp = "," + productIds + ",";
      for (ProductSubscriptionResponseDto productSubscriptionResponseDto : list) {
        if (temp.indexOf("," + productSubscriptionResponseDto.getProductId() + ",") != -1) {
          map.put(productSubscriptionResponseDto.getProductId(), true);
        }
      }
    }
    return map;
  }

}