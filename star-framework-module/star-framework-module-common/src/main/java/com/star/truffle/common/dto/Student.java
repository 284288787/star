/**create by liuhua at 2018年8月13日 下午2:41:02**/
package com.star.truffle.common.dto;

import java.util.Date;

import lombok.Data;

@Data
public class Student {

  private String code;
  private String name;
  private Integer sex;
  private Date birth;
  private String intro;
}
