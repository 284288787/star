/**create by framework at 2018年11月07日 14:22:22**/
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
import com.star.truffle.module.product.cache.ProductTagCache;
import com.star.truffle.module.product.domain.ProductTag;
import com.star.truffle.module.product.dto.req.ProductTagRequestDto;
import com.star.truffle.module.product.dto.res.ProductTagResponseDto;

@Service
public class ProductTagService implements ChooseDataIntf {

  @Autowired
  private ProductTagCache productTagCache;
  @Autowired
  private StarJson starJson;
  
  public Long saveProductTag(ProductTag productTag) {
    productTag.setCreateTime(new Date());
    productTag.setType(1);
    this.productTagCache.saveProductTag(productTag);
    return productTag.getTagId();
  }

  public void updateProductTag(ProductTagRequestDto productTagRequestDto) {
    this.productTagCache.updateProductTag(productTagRequestDto);
  }

  public void deleteProductTag(Long tagId) {
    this.productTagCache.deleteProductTag(tagId);
  }

  public void deleteProductTag(String idstr) {
    if (StringUtils.isBlank(idstr)) {
      throw new StarServiceException(ApiCode.PARAM_ERROR);
    }
    String[] tagIds = idstr.split(",");
    for (String str : tagIds) {
      Long tagId = Long.parseLong(str);
      this.productTagCache.deleteProductTag(tagId);
    }
  }

  public ProductTagResponseDto getProductTag(Long tagId) {
    ProductTagResponseDto productTagResponseDto = this.productTagCache.getProductTag(tagId);
    return productTagResponseDto;
  }

  public List<ProductTagResponseDto> queryProductTag(ProductTagRequestDto productTagRequestDto) {
    return this.productTagCache.queryProductTag(productTagRequestDto);
  }

  public Long queryProductTagCount(ProductTagRequestDto productTagRequestDto) {
    return this.productTagCache.queryProductTagCount(productTagRequestDto);
  }

  @Override
  public GridPagerResponse getDatas(Map<String, Object> condition, Page pager) {
    ProductTagRequestDto productTagRequestDto = starJson.str2obj(starJson.obj2string(condition), ProductTagRequestDto.class);
    List<ProductTagResponseDto> list = productTagCache.queryProductTag(productTagRequestDto);
    Long count = productTagCache.queryProductTagCount(productTagRequestDto);
    long total = count % pager.getPageSize() == 0 ? count / pager.getPageSize() : count / pager.getPageSize() + 1;
    return new GridPagerResponse(pager.getPageNum(), total, count, list);
  }

  @Override
  public List<GridColumn> getGridColumns() {
    List<GridColumn> columns = new ArrayList<>();
    columns.add(GridColumn.builder().caption("tagId").javaName("tagId").dsName("tag_id").hidden(true).build());
    columns.add(GridColumn.builder().caption("标签名称").javaName("tagName").dsName("tagName").query(true).type("text").placeholder("标签名称").build());
    return columns;
  }

}