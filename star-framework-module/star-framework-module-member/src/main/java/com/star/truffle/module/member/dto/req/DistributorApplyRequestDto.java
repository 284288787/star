/**create by framework at 2018年10月26日 09:40:50**/
package com.star.truffle.module.member.dto.req;

import com.star.truffle.core.jdbc.Page;
import lombok.Getter;
import lombok.Setter;
import com.star.truffle.module.member.domain.DistributorApply;

@Getter
@Setter
public class DistributorApplyRequestDto extends DistributorApply {

  private Page pager;
}