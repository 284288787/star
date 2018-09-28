/** create by liuhua at 2017年5月19日 下午2:07:11 **/
package com.star.truffle.core.security;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SecurtityUserServiceDetails implements UserDetailsService {

  PasswordEncoder encode = PasswordEncoderFactories.createDelegatingPasswordEncoder();

  List<SimpleGrantedAuthority> nullAuth = new ArrayList<>();
  private Map<String, User> users = new HashMap<String, User>() {
    private static final long serialVersionUID = 1L;

    {
      put("admin", new StarUserInfo("admin", encode.encode("111"), Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))));
      put("test1", new StarUserInfo("test1", encode.encode("111"), Collections.singletonList(new SimpleGrantedAuthority("ROLE_TEST"))));
      put("test2", new StarUserInfo("test2", encode.encode("111"), nullAuth));
//      put("admin", new User("admin", "111", Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))));
//      put("test1", new User("test1", "111", Collections.singletonList(new SimpleGrantedAuthority("ROLE_TEST"))));
//      put("test2", new User("test2", "111", nullAuth));
    }
  };

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
    // authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
    // authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
    return users.get(username);
  }

  public PasswordEncoder getPasswordEncoder(){
    return encode;
  }
}
