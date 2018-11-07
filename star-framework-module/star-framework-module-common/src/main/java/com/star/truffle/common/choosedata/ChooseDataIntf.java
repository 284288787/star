/**create by liuhua at 2018年9月18日 下午3:26:56**/
package com.star.truffle.common.choosedata;

import java.util.List;
import java.util.Map;

import com.star.truffle.core.jdbc.Page;

public interface ChooseDataIntf {

  public GridPagerResponse getDatas(Map<String, Object> condition, Page pager);

  public List<GridColumn> getGridColumns();
}
