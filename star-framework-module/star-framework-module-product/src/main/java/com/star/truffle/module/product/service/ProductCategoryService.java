/**create by framework at 2019年03月07日 10:03:37**/
package com.star.truffle.module.product.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.star.truffle.common.choosedata.ChooseDataIntf;
import com.star.truffle.common.choosedata.GridColumn;
import com.star.truffle.common.choosedata.GridPagerResponse;
import com.star.truffle.core.StarServiceException;
import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.core.jdbc.Page;
import com.star.truffle.core.web.ApiCode;
import com.star.truffle.module.product.cache.ProductCategoryCache;
import com.star.truffle.module.product.domain.ProductCategory;
import com.star.truffle.module.product.dto.req.ProductCategoryRequestDto;
import com.star.truffle.module.product.dto.res.ProductCategoryResponseDto;

@Service
public class ProductCategoryService implements ChooseDataIntf {

  @Autowired
  private ProductCategoryCache productCategoryCache;
  @Autowired
  private StarJson starJson;

  public Long saveProductCategory(ProductCategory productCategory) {
    productCategory.setCreateTime(new Date());
    this.productCategoryCache.saveProductCategory(productCategory);
    return productCategory.getProductCateId();
  }

  public void updateProductCategory(ProductCategoryRequestDto productCategoryRequestDto) {
    this.productCategoryCache.updateProductCategory(productCategoryRequestDto);
  }

  public void deleteProductCategory(Long productCateId) {
    this.productCategoryCache.deleteProductCategory(productCateId);
  }

  public void deleteProductCategory(String idstr) {
    if (StringUtils.isBlank(idstr)) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    String[] productCateIds = idstr.split(",");
    for (String str : productCateIds) {
      Long productCateId = Long.parseLong(str);
      this.productCategoryCache.deleteProductCategory(productCateId);
    }
  }

  public ProductCategoryResponseDto getProductCategory(Long productCateId) {
    ProductCategoryResponseDto productCategoryResponseDto = this.productCategoryCache.getProductCategory(productCateId);
    return productCategoryResponseDto;
  }

  public List<ProductCategoryResponseDto> queryProductCategory(ProductCategoryRequestDto productCategoryRequestDto) {
    return this.productCategoryCache.queryProductCategory(productCategoryRequestDto);
  }

  public Long queryProductCategoryCount(ProductCategoryRequestDto productCategoryRequestDto) {
    return this.productCategoryCache.queryProductCategoryCount(productCategoryRequestDto);
  }

  @Override
  public GridPagerResponse getDatas(Map<String, Object> condition, Page pager) {
    ProductCategoryRequestDto productCategoryRequestDto = starJson.str2obj(starJson.obj2string(condition), ProductCategoryRequestDto.class);
    productCategoryRequestDto.setPager(pager);
    Long count = productCategoryCache.queryProductCategoryCount(productCategoryRequestDto);
    List<ProductCategoryResponseDto> list = new ArrayList<>();
    if (count > 0) {
      list = productCategoryCache.queryProductCategory(productCategoryRequestDto);
    }
    long total = count % pager.getPageSize() == 0 ? count / pager.getPageSize() : count / pager.getPageSize() + 1;
    return new GridPagerResponse(pager.getPageNum(), total, count, list);
  }

  @Override
  public List<GridColumn> getGridColumns() {
    List<GridColumn> columns = new ArrayList<>();
    columns.add(GridColumn.builder().caption("productCateId").javaName("productCateId").dsName("product_cate_id").hidden(true).build());
    columns.add(GridColumn.builder().caption("分类名称").javaName("productCateName").dsName("productCateName").query(true).type("text").placeholder("分类名称").build());
    return columns;
  }

  @Override
  public String getPrimaryKey() {
    return "productCateId";
  }

}