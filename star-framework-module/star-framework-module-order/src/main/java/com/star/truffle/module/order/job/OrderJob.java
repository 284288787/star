/**create by liuhua at 2018年10月9日 上午9:27:32**/
package com.star.truffle.module.order.job;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.star.truffle.module.order.service.OrderService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@EnableScheduling
public class OrderJob {

  @Autowired
  private OrderService orderService;
  
  @PostConstruct
  public void systemStart(){
    System.out.println("系统启动时");
  }
  
  /**
   * 每分
   */
  @Scheduled(cron = "0 * * * * ?")   //每分
  public void deleteOrderJob() {
    log.info("开始执行任务：deleteOrderJob");
    orderService.deleteOrderJob();
    log.info("停止执行任务：deleteOrderJob");
  }
  
  @Scheduled(cron = "0 0 2 * * ? ")   //每天 凌晨 2点
  public void orderTotalJob() {
    log.info("开始执行任务：orderTotalJob");
    orderService.orderTotalJob();
    log.info("停止执行任务：orderTotalJob");
  }
}
