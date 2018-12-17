/**create by framework at 2018年09月21日 15:21:35**/
package com.star.truffle.module.order.cache;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.module.order.dao.read.OrderAfterSaleReadDao;
import com.star.truffle.module.order.dao.write.OrderAfterSaleWriteDao;
import com.star.truffle.module.order.domain.OrderAfterSale;
import com.star.truffle.module.order.dto.req.OrderAfterSaleRequestDto;
import com.star.truffle.module.order.dto.res.OrderAfterSaleResponseDto;

@Service
public class OrderAfterSaleCache {

  @Autowired
  private StarJson starJson;
  @Autowired
  private OrderAfterSaleWriteDao orderAfterSaleWriteDao;
  @Autowired
  private OrderAfterSaleReadDao orderAfterSaleReadDao;

  @CachePut(value = "module-order-orderAfterSale", key = "'orderAfterSale_id_'+#result.id", condition = "#result != null and #result.id != null")
  public OrderAfterSaleResponseDto saveOrderAfterSale(OrderAfterSale orderAfterSale){
    this.orderAfterSaleWriteDao.saveOrderAfterSale(orderAfterSale);
    OrderAfterSaleResponseDto orderAfterSaleResponseDto = this.orderAfterSaleWriteDao.getOrderAfterSale(orderAfterSale.getId());
    return orderAfterSaleResponseDto;
  }

  @CachePut(value = "module-order-orderAfterSale", key = "'orderAfterSale_id_'+#result.id", condition = "#result != null and #result.id != null")
  public OrderAfterSaleResponseDto updateOrderAfterSale(OrderAfterSaleRequestDto orderAfterSaleRequestDto){
    orderAfterSaleRequestDto.setUpdateTime(new Date());
    this.orderAfterSaleWriteDao.updateOrderAfterSale(orderAfterSaleRequestDto);
    OrderAfterSaleResponseDto orderAfterSaleResponseDto = this.orderAfterSaleWriteDao.getOrderAfterSale(orderAfterSaleRequestDto.getId());
    return orderAfterSaleResponseDto;
  }

  @CacheEvict(value = "module-order-orderAfterSale", key = "'orderAfterSale_id_'+#id", condition = "#id != null")
  public int deleteOrderAfterSale(Long id){
    return this.orderAfterSaleWriteDao.deleteOrderAfterSale(id);
  }

  @Cacheable(value = "module-order-orderAfterSale", key = "'orderAfterSale_id_'+#id", condition = "#id != null")
  public OrderAfterSaleResponseDto getOrderAfterSale(Long id){
    OrderAfterSaleResponseDto orderAfterSaleResponseDto = this.orderAfterSaleReadDao.getOrderAfterSale(id);
    return orderAfterSaleResponseDto;
  }

  public List<OrderAfterSaleResponseDto> queryOrderAfterSale(OrderAfterSaleRequestDto orderAfterSaleRequestDto){
    Map<String, Object> conditions = starJson.bean2Map(orderAfterSaleRequestDto);
    return this.orderAfterSaleReadDao.queryOrderAfterSale(conditions);
  }

  public Long queryOrderAfterSaleCount(OrderAfterSaleRequestDto orderAfterSaleRequestDto){
    Map<String, Object> conditions = starJson.bean2Map(orderAfterSaleRequestDto);
    return this.orderAfterSaleReadDao.queryOrderAfterSaleCount(conditions);
  }

  public void batchSaveOrderAfterSale(List<OrderAfterSale> orderAfterSales) {
    this.orderAfterSaleWriteDao.batchSaveOrderAfterSale(orderAfterSales);
  }

}