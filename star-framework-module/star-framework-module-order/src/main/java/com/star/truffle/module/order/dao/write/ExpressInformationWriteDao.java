/**create by framework at 2019年02月19日 14:19:41**/
package com.star.truffle.module.order.dao.write;

import java.util.List;
import com.star.truffle.module.order.domain.ExpressInformation;
import com.star.truffle.module.order.dto.req.ExpressInformationRequestDto;
import com.star.truffle.module.order.dto.res.ExpressInformationResponseDto;

public interface ExpressInformationWriteDao {

  public int saveExpressInformation(ExpressInformation expressInformation);

  public int batchSaveExpressInformation(List<ExpressInformation> expressInformations);

  public int updateExpressInformation(ExpressInformationRequestDto expressInformationDto);

  public int deleteExpressInformation(Long id);

  public ExpressInformationResponseDto getExpressInformation(Long id);

}