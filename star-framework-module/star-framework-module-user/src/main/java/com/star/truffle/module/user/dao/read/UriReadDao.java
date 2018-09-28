/**create by liuhua at 2018年7月22日 下午3:36:41**/
package com.star.truffle.module.user.dao.read;

import java.util.List;
import java.util.Map;
import com.star.truffle.module.user.domain.Uri;
import com.star.truffle.module.user.dto.UriDto;

public interface UriReadDao {

  public Uri getUri(String uri);
  
  public List<UriDto> queryUri(Map<String, Object> conditions);
  
  public Integer queryUriCount(Map<String, Object> conditions);
}
