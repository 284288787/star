/**create by liuhua at 2018年10月9日 上午9:27:32**/
package com.star.truffle.module.product.job;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.star.truffle.module.product.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@EnableScheduling
public class ProductJob {

  @Autowired
  private ProductService productService;
  
  @PostConstruct
  public void systemStart(){
    System.out.println("系统启动时");
  }
  
  /**
   * 每个整点
   */
//  @Scheduled(cron = "0 0 * * * ?")   //整点
  @Scheduled(cron = "0 * * * * ?")   //每分
  public void syncProductState() {
    log.info("开始执行任务：syncProductState");
    productService.syncProductState();
    log.info("停止执行任务：syncProductState");
  }
}
