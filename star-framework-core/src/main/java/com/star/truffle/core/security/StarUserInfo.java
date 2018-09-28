/** create by liuhua at 2017年5月26日 上午11:09:40 **/
package com.star.truffle.core.security;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StarUserInfo extends User {
  private static final long serialVersionUID = 1L;

  private Long userId;

  private List<String> roles;

  private String userType;
  
  private String typeName;

  private List<UserInfo> userInfos;

  private String token;

  private long loginTime;

  private List<Map<String, Object>> navMenus;

  private List<Map<String, Object>> menu;

  public StarUserInfo(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
    super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    this.loginTime = System.currentTimeMillis();
  }

  public StarUserInfo(Integer enabled, Integer locked, String username, String password, Long userId, String userType, String typeName, List<SimpleGrantedAuthority> authorities, List<String> roles, List<UserInfo> userInfos) {
    super(username, password, enabled == 1, true, true, locked == 1, authorities);
    this.roles = roles;
    this.userId = userId;
    this.userInfos = userInfos;
    this.userType = userType;
    this.loginTime = System.currentTimeMillis();
  }

  public StarUserInfo(String username, String password, List<SimpleGrantedAuthority> singletonList) {
    super(username, password, true, true, true, true, singletonList);
    this.loginTime = System.currentTimeMillis();
  }
}
