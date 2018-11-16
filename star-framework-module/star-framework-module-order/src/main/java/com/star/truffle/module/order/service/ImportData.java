/**create by liuhua at 2018年11月16日 下午8:50:52**/
package com.star.truffle.module.order.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.star.truffle.common.importdata.AbstractDataExport;
import com.star.truffle.common.properties.Excel;
import com.star.truffle.module.order.domain.Order;

public class ImportData extends AbstractDataExport<Order>{

  public ImportData(Excel excel) {
    super(excel);
  }

  @Override
  public Map<String, Object> getTemplateDatas() {
    Map<String, Object> map = new HashMap<>();
    map.put("shopCode", "sc1234");
    map.put("shopName", "店铺名称");
    map.put("orderDate", "2018-10-10");
    map.put("useObj", "门店");
    map.put("shopMobile", "13739067702");
    map.put("shopAddress", "黄兴路80号");
    return map;
  }

  @Override
  public List<String[]> getRecordsData(Map<String, Object> params, int pageNumber, int pageSize) {
    List<String[]> list = new ArrayList<>();
    if (pageNumber < 3) {
      String[] arr = {"提货号","代客下单","收货人/昵称","联系电话","收货地址","1","12.2","确认提货"};
      list.add(arr);
      String[] arr2 = {"提货号","代客下单","收货人/昵称","联系电话","收货地址","2","15.2","确认提货"};
      list.add(arr2);
    }
    return list;
  }

  @Override
  public void getApplication(ApplicationContext applicationContext) {
    
  }

}
