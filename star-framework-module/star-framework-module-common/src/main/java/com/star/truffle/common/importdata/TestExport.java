/**create by liuhua at 2018年8月15日 下午3:05:08**/
package com.star.truffle.common.importdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.star.truffle.common.dto.Student;
import com.star.truffle.common.properties.Excel;
import com.star.truffle.core.util.DateUtils;

public class TestExport extends AbstractDataExport<Student>{

  public TestExport(Excel excel) {
    super(excel);
  }

  @Override
  public List<String[]> getRecordsData(Map<String, Object> params, int pageNumber, int pageSize) {
    System.out.println(pageNumber + "\t" + pageSize + "\t" + params);
    List<String[]> list = new ArrayList<>();
    list.add(new String[] {"1", "张1", "zhangsan1", "13142222431"});
    list.add(new String[] {"2", "张2", "zhangsan2", "13142222432"});
    list.add(new String[] {"3", "张3", "zhangsan3", "13142222433"});
    
    list.add(new String[] {"4", "张4", "zhangsan4", "13142222434"});
    list.add(new String[] {"5", "张5", "zhangsan5", "13142222435"});
    list.add(new String[] {"6", "张6", "zhangsan6", "13142222436"});
    
    list.add(new String[] {"7", "张7", "zhangsan7", "13142222437"});
    list.add(new String[] {"8", "张8", "zhangsan8", "13142222438"});
    list.add(new String[] {"9", "张9", "zhangsan9", "13142222439"});
    
    list.add(new String[] {"10", "张10", "zhangsan10", "13142222440"});
    int fromIndex = (pageNumber - 1) * pageSize;
    if (fromIndex >= list.size()) {
      return null;
    }
    int toIndex = fromIndex + pageSize;
    if (toIndex >= list.size()) {
      toIndex = list.size();
    }
    return list.subList(fromIndex, toIndex);
  }

  public int getPageSize() {
    return 3;
  }
  
  @Override
  public void getApplication(ApplicationContext applicationContext) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public Map<String, Object> getTemplateDatas() {
    Map<String, Object> map = new HashMap<>();
    map.put("now", DateUtils.formatTodayDate());
    return map;
  }
}
