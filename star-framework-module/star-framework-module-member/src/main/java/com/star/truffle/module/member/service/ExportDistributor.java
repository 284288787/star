/**create by liuhua at 2018年12月7日 下午1:55:59**/
package com.star.truffle.module.member.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;

import com.star.truffle.common.importdata.AbstractDataExport;
import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.core.jdbc.Page;
import com.star.truffle.module.member.domain.Distributor;
import com.star.truffle.module.member.dto.req.DistributorRequestDto;
import com.star.truffle.module.member.dto.res.DistributorResponseDto;

public class ExportDistributor extends AbstractDataExport<Distributor> {
  private DistributorService distributorService;
  private StarJson starJson;
  
  @Override
  public void getApplication(ApplicationContext applicationContext) {
    this.starJson = applicationContext.getBean(StarJson.class);
    this.distributorService = applicationContext.getBean(DistributorService.class);
  }

  @Override
  public Map<String, Object> getTemplateDatas() {
    return new HashMap<>();
  }

  @Override
  public List<String[]> getRecordsData(Map<String, Object> params, int pageNumber, int pageSize) {
    List<String[]> list = new ArrayList<>();
    DistributorRequestDto distributorRequestDto = starJson.map2Bean(params, DistributorRequestDto.class);
    Page pager = new Page(pageNumber, pageSize, null, null);
    distributorRequestDto.setPager(pager);
    List<DistributorResponseDto> details = this.distributorService.queryDistributor(distributorRequestDto);
    if (null != details && ! details.isEmpty()) {
      for (DistributorResponseDto distributorResponseDto : details) {
        //姓名,电话,地址
        StringBuilder addr = new StringBuilder();
        if (StringUtils.isNotBlank(distributorResponseDto.getProvinceName())) {
          addr.append(distributorResponseDto.getProvinceName());
        }
        if (StringUtils.isNotBlank(distributorResponseDto.getCityName())) {
          addr.append(distributorResponseDto.getCityName());
        }
        if (StringUtils.isNotBlank(distributorResponseDto.getAreaName())) {
          addr.append(distributorResponseDto.getAreaName());
        }
        if (StringUtils.isNotBlank(distributorResponseDto.getTownName())) {
          addr.append(distributorResponseDto.getTownName());
        }
        addr.append(distributorResponseDto.getAddress());
        String[] arr = {distributorResponseDto.getName(), distributorResponseDto.getMobile(), addr.toString()};
        list.add(arr);
      }
    }
    return list;
  }

}
