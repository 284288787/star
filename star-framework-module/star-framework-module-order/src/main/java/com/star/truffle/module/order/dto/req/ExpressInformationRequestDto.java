/**create by framework at 2019年02月19日 14:19:41**/
package com.star.truffle.module.order.dto.req;

import com.star.truffle.core.jdbc.Page;
import lombok.Getter;
import lombok.Setter;
import com.star.truffle.module.order.domain.ExpressInformation;

@Getter
@Setter
public class ExpressInformationRequestDto extends ExpressInformation {

  private Page pager;
}