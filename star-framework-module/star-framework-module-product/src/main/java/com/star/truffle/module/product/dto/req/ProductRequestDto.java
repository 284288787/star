/**create by framework at 2018年09月04日 10:28:04**/
package com.star.truffle.module.product.dto.req;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.star.truffle.core.jdbc.Page;
import lombok.Getter;
import lombok.Setter;
import com.star.truffle.module.product.domain.Product;
import com.star.truffle.module.product.domain.ProductInventory;
import com.star.truffle.module.product.domain.ProductPicture;

@Getter
@Setter
public class ProductRequestDto extends Product {

  private Page pager;
  
  private String states;
  
  private List<ProductPicture> pictures;
  
  private ProductInventory productInventory;
  
  public boolean checkSaveData() {
    if (null != getProductId() || StringUtils.isBlank(getTitle()) ||StringUtils.isBlank(getSubtitle())
        || StringUtils.isBlank(getTag()) || null == getOriginalPrice() || null == getPrice() || StringUtils.isBlank(getSupplier())
        || StringUtils.isBlank(getSpecification()) || StringUtils.isBlank(getOriginPlace())
        || StringUtils.isBlank(getDescription()) || null == getBrokerageType() || null == getBrokerageValue()
        || null == pictures || pictures.isEmpty() || null == productInventory || null == productInventory.getNumberType() 
        || (2 == productInventory.getNumberType() && (null == productInventory.getNumber() || 0 > productInventory.getNumber()))) {
      return false;
    }
    return true;
  }
  
  public boolean checkUpdateData() {
    if (null == getProductId() || StringUtils.isBlank(getTitle()) ||StringUtils.isBlank(getSubtitle())
        || StringUtils.isBlank(getTag()) || null == getOriginalPrice() || null == getPrice() || StringUtils.isBlank(getSupplier())
        || StringUtils.isBlank(getSpecification()) || StringUtils.isBlank(getOriginPlace())
        || StringUtils.isBlank(getDescription()) || null == getBrokerageType() || null == getBrokerageValue()
        || null == pictures || pictures.isEmpty() || null == productInventory || null == productInventory.getNumberType() 
        || (2 == productInventory.getNumberType() && (null == productInventory.getNumber() || 0 > productInventory.getNumber()))) {
      return false;
    }
    return true;
  }
}