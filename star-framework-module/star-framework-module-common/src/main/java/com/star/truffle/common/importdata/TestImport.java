/**create by liuhua at 2018年8月13日 下午2:40:18**/
package com.star.truffle.common.importdata;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.star.truffle.common.dto.Student;

public class TestImport extends AbstractDataImport<Student> {
  
  public TestImport(byte[] bs, String filename) {
    super(bs, filename);
  }

  @Override
  public List<RecordType> getRecordTypes() {
    List<RecordType> types = new ArrayList<>();
    types.add(new RecordType("code", String.class, "文本"));
    types.add(new RecordType("name", String.class, "文本"));
    types.add(new RecordType("sex", String.class, Integer.class, "文本", "{\"男\":\"1\", \"女\":\"0\"}"));
    types.add(RecordType.ofDate("birth", "yyyy-MM-dd"));
    types.add(new RecordType("intro", String.class, "文本"));
    return types;
  }

  @Override
  public Class<Student> getBeanType() {
    return Student.class;
  }

  @Override
  public Map<Integer, Object> saveBean(List<Student> list) {
    Map<Integer, Object> errorIdx = new LinkedHashMap<>();
    for (int i = 0; i < list.size(); i++) {
      System.out.println(list.get(i));
    }
    return errorIdx;
  }

  @Override
  public void getApplication(ApplicationContext applicationContext) {
    
  }

}
