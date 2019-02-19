/**create by liuhua at 2019年2月19日 下午2:49:33**/
package com.star.truffle.module.order.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.star.truffle.common.importdata.AbstractDataImport;
import com.star.truffle.common.importdata.RecordType;
import com.star.truffle.module.order.cache.ExpressInformationCache;
import com.star.truffle.module.order.domain.ExpressInformation;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ImportExpressInformation extends AbstractDataImport<ExpressInformation> {

  @Autowired
  private ExpressInformationCache expressInformationCache;
  
  public ImportExpressInformation(byte[] bs, String filename) {
    super(bs, filename);
  }

  @Override
  public List<RecordType> getRecordTypes() {
    List<RecordType> types = new ArrayList<>();
    types.add(new RecordType("code", String.class, "文本"));
    types.add(new RecordType("sender", String.class, "文本"));
    types.add(new RecordType("senderMobile", String.class, "文本"));
    types.add(new RecordType("senderTel", String.class, "文本"));
    types.add(new RecordType("senderAddress", String.class, "文本"));
    types.add(new RecordType("receiver", String.class, "文本"));
    types.add(new RecordType("receiverMobile", String.class, "文本"));
    types.add(new RecordType("receiverTel", String.class, "文本"));
    types.add(new RecordType("receiverAddress", String.class, "文本"));
    types.add(new RecordType("goodsInfo", String.class, "文本"));
    types.add(new RecordType("expressCompany", String.class, "文本"));
    types.add(new RecordType("pointName", String.class, "文本"));
    types.add(new RecordType("trackingNumber", String.class, "文本"));
    return types;
  }

  @Override
  public Class<ExpressInformation> getBeanType() {
    return ExpressInformation.class;
  }

  @Override
  public Map<Integer, Object> saveBean(List<ExpressInformation> list) {
    Map<Integer, Object> errorIdx = new LinkedHashMap<>();
    if(null != list && ! list.isEmpty()) {
      for (int i = 0; i < list.size(); i++) {
        ExpressInformation expressInformation = list.get(i);
        try {
          expressInformation.setCreateTime(new Date());
          expressInformationCache.saveExpressInformation(expressInformation);
        } catch (Exception e) {
          log.error(e.getMessage(), e);
          errorIdx.put(i, e.getMessage());
        }
      }
    }
    return errorIdx;
  }

  @Override
  public void getApplication(ApplicationContext applicationContext) {
    expressInformationCache = applicationContext.getBean(ExpressInformationCache.class);
  }

}
