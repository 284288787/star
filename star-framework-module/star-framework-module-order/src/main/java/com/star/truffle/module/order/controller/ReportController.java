/**create by liuhua at 2019年1月7日 下午1:41:12**/
package com.star.truffle.module.order.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/report")
public class ReportController {
  
  /**
   * 订单数量(商品数量，购买人数)，
   * 未税金额，已税金额，商品金额，
   * 运营提成，分销提成，
   * 快递金额，优惠金额，到账金额(实付金额 = 商品金额+快递金额-优惠金额)
   * @return
   */
  @RequestMapping(value = "/today", method = RequestMethod.GET)
  public String today() {
    return "mgr/report/today";
  }

}
