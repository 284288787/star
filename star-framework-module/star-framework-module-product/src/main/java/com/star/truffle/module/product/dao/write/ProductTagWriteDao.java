/**create by framework at 2018年11月07日 14:22:22**/
package com.star.truffle.module.product.dao.write;

import java.util.List;
import com.star.truffle.module.product.domain.ProductTag;
import com.star.truffle.module.product.dto.req.ProductTagRequestDto;
import com.star.truffle.module.product.dto.res.ProductTagResponseDto;

public interface ProductTagWriteDao {

  public int saveProductTag(ProductTag productTag);

  public int batchSaveProductTag(List<ProductTag> productTags);

  public int updateProductTag(ProductTagRequestDto productTagDto);

  public int deleteProductTag(Long tagId);

  public ProductTagResponseDto getProductTag(Long tagId);

}