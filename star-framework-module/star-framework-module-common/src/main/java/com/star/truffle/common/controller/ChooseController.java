/**create by liuhua at 2018年9月18日 下午3:20:33**/
package com.star.truffle.common.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.star.truffle.common.choosedata.ChooseDataIntf;
import com.star.truffle.common.choosedata.GridColumn;
import com.star.truffle.common.choosedata.GridPagerRequest;
import com.star.truffle.common.choosedata.GridPagerResponse;
import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.core.jdbc.Page;
import com.star.truffle.core.jdbc.Page.OrderType;
import com.star.truffle.core.util.ParamHandler;
import com.star.truffle.core.web.config.SpringContextConfig;

@Controller
@RequestMapping("/choose")
public class ChooseController {

  @Autowired
  private StarJson starJson;
  
  @RequestMapping(value = "/dialog", method = RequestMethod.GET)
  public String dialog(String service, String condition, Model model) throws UnsupportedEncodingException {
    ChooseDataIntf data = SpringContextConfig.getBean(service, ChooseDataIntf.class);
    List<GridColumn> columns = data.getGridColumns();
    model.addAttribute("primaryKey", data.getPrimaryKey());
    model.addAttribute("columns", columns);
    model.addAttribute("service", service);
    if (StringUtils.isNotBlank(condition)) {
      model.addAttribute("conditions", starJson.str2Map(URLDecoder.decode(condition, "UTF-8")));
    }
    return "choose/dialog";
  }
  
  @ResponseBody
  @RequestMapping(value = "/dialogData", method = {RequestMethod.POST, RequestMethod.GET})
  public GridPagerResponse dialog(HttpServletRequest request, Model model) throws InstantiationException, IllegalAccessException {
    ParamHandler paramHandler = ParamHandler.ofRequest(request, starJson);
    GridPagerRequest gpr = paramHandler.getDTO(GridPagerRequest.class);
    GridPagerResponse nodes = new GridPagerResponse();
    ChooseDataIntf data = SpringContextConfig.getBean(gpr.getService(), ChooseDataIntf.class);
    if (null != data) {
      Page pager = new Page(gpr.getPage(), gpr.getRows(), gpr.getSidx(), OrderType.desc.name().equals(gpr.getSord()) ? OrderType.desc : OrderType.asc);
      nodes = data.getDatas(paramHandler.getMap(), pager);
    }
    return nodes;
  }

}
