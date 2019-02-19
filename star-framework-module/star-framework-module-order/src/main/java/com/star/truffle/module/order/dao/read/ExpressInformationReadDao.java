/**create by framework at 2019年02月19日 14:19:41**/
package com.star.truffle.module.order.dao.read;

import java.util.List;
import java.util.Map;
import com.star.truffle.module.order.dto.res.ExpressInformationResponseDto;

public interface ExpressInformationReadDao {

  public ExpressInformationResponseDto getExpressInformation(Long id);

  public List<ExpressInformationResponseDto> queryExpressInformation(Map<String, Object> conditions);

  public Long queryExpressInformationCount(Map<String, Object> conditions);

}