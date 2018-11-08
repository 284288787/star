/**create by framework at 2018年09月04日 10:28:04**/
package com.star.truffle.module.product.dao.write;

import java.util.List;
import com.star.truffle.module.product.domain.Product;
import com.star.truffle.module.product.dto.req.ProductRequestDto;
import com.star.truffle.module.product.dto.res.ProductResponseDto;

public interface ProductWriteDao {

  public int saveProduct(Product product);

  public int batchSaveProduct(List<Product> products);

  public int updateProduct(ProductRequestDto productDto);

  public int deleteProduct(Long productId);

  public ProductResponseDto getProduct(Long productId);

  public Integer getMinIdx();

}