/**create by liuhua at 2018年7月14日 下午2:43:22**/
package com.star.truffle.module.user.service;

public interface UserTypeIntf<T> {

  public T getByUserId(Long userId);

  public void deleteByUserId(Long userId);
}
