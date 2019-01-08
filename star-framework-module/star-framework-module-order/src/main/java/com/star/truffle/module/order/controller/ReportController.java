/**create by liuhua at 2019年1月7日 下午1:41:12**/
package com.star.truffle.module.order.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/report")
public class ReportController {

  @RequestMapping(value = "/lists", method = RequestMethod.GET)
  public String list() {
    return "mgr/order/list";
  }
}
