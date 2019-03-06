/**create by liuhua at 2018年11月28日 下午9:32:34**/
package com.star.truffle.module.order.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.star.truffle.common.importdata.AbstractDataExport;
import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.core.jdbc.Page;
import com.star.truffle.core.util.DateUtils;
import com.star.truffle.module.order.cache.OrderDetailCache;
import com.star.truffle.module.product.cache.CategoryCache;
import com.star.truffle.module.product.cache.ProductCache;
import com.star.truffle.module.product.constant.ProductConstant;
import com.star.truffle.module.product.domain.Product;
import com.star.truffle.module.product.dto.req.CategoryRequestDto;
import com.star.truffle.module.product.dto.req.ProductRequestDto;
import com.star.truffle.module.product.dto.res.CategoryResponseDto;
import com.star.truffle.module.product.dto.res.ProductResponseDto;

public class ExportProduct extends AbstractDataExport<Product> {

  private ProductCache productCache;
  private OrderDetailCache orderDetailCache;
  private StarJson starJson;
  private static Map<Long, CategoryResponseDto> categories = new HashMap<>();
  
  @Override
  public void getApplication(ApplicationContext applicationContext) {
    productCache = applicationContext.getBean(ProductCache.class);
    orderDetailCache = applicationContext.getBean(OrderDetailCache.class);
    this.starJson = applicationContext.getBean(StarJson.class);
    CategoryCache categoryCache = applicationContext.getBean(CategoryCache.class);
    CategoryRequestDto categoryRequestDto = new CategoryRequestDto();
    List<CategoryResponseDto> cates = categoryCache.queryCategory(categoryRequestDto);
    if (null != cates && ! cates.isEmpty()) {
      for (CategoryResponseDto categoryResponseDto : cates) {
        categories.put(categoryResponseDto.getCateId(), categoryResponseDto);
      }
    }
  }

  @Override
  public Map<String, Object> getTemplateDatas() {
    return new HashMap<>();
  }

  @Override
  public List<String[]> getRecordsData(Map<String, Object> params, int pageNumber, int pageSize) {
    List<String[]> list = new ArrayList<>();
    ProductRequestDto productRequestDto = starJson.map2Bean(params, ProductRequestDto.class);
    Page pager = new Page(pageNumber, pageSize, null, null);
    productRequestDto.setPager(pager);
    List<ProductResponseDto> products = this.productCache.queryProduct(productRequestDto);
    if (null != products && ! products.isEmpty()) {
      //商品ID,状态,分类,标题,规格,预售时间,下架时间,供货商,供货商联系人,供货商电话,原价,未税价,含税价,售价,分销商提成,上级分销商提成,库存总量,已售数量,实时库存,累计金额,购买人数
      for (ProductResponseDto product : products) {
        Map<String, Integer> buyTotal = this.orderDetailCache.buyRecordTotal(product.getProductId());
        try {
          String[] arr = {product.getProductId().toString(), ProductConstant.getCaption(product.getState()), categories.get(product.getCateId()).getCateName(), product.getTitle(),  
              product.getSpecification(), 
              null == product.getPresellTime() ? "现在有货" : DateUtils.formatDate(product.getPresellTime(), "yyyy-MM-dd HH点"), 
              null == product.getOffShelfTime() ? "永不下架" : DateUtils.formatDate(product.getOffShelfTime(), "yyyy-MM-dd HH点"),
              product.getSupplier(), product.getSupplierName(), product.getSupplierMobile(),
              ProductConstant.formatMoney(product.getOriginalPrice()), ProductConstant.formatMoney(product.getPriceWei()), ProductConstant.formatMoney(product.getPriceHan()), 
              ProductConstant.formatMoney(product.getPrice()), ProductConstant.formatMoney(product.getBrokerageValue()), ProductConstant.formatMoney(product.getBrokerageFirst()),
              product.getNumberType() == 1 ? "不限库存" : String.valueOf(product.getNumber()), String.valueOf(product.getSoldNumber()), null == product.getNumberType() || product.getNumberType() == 1 ? "不限库存" : String.valueOf(product.getNumber()-product.getSoldNumber()), 
              ProductConstant.formatMoney(String.valueOf(buyTotal.get("money"))), 
              String.valueOf(buyTotal.get("members"))};
          list.add(arr);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    return list;
  }

  
}
