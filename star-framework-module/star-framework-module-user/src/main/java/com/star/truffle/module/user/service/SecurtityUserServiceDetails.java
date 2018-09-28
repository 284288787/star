/** create by liuhua at 2017年5月19日 下午2:07:11 **/
package com.star.truffle.module.user.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.star.truffle.core.security.StarUserInfo;
import com.star.truffle.core.security.UserInfo;
import com.star.truffle.core.web.config.SpringContextConfig;
import com.star.truffle.module.user.constant.UserConstants.UserType;
import com.star.truffle.module.user.domain.Role;
import com.star.truffle.module.user.domain.UserAccountType;
import com.star.truffle.module.user.dto.ResourceDto;
import com.star.truffle.module.user.dto.UserAccountDto;

@Service("securtityUserServiceDetails")
public class SecurtityUserServiceDetails implements UserDetailsService {

  @Autowired
  private UserAccountService userAccountService;
  @Autowired
  private ResourceService resourceService;
  @Autowired
  private RoleService roleService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserAccountDto userAccount = userAccountService.getUserAccount(username);
    List<UserInfo> userInfos = new ArrayList<>();
    if (null != userAccount) {
      List<UserAccountType> types = userAccountService.queryUserAccountType(userAccount.getUserId());
      if (null != types && ! types.isEmpty()) {
        for (UserAccountType userAccountType : types) {
          String userType = userAccountType.getUserType();
          UserTypeIntf<?> userTypeIntf = SpringContextConfig.getBean(userType, UserTypeIntf.class);
          if (null != userTypeIntf) {
            Object userInfo = userTypeIntf.getByUserId(userAccount.getUserId());
            userInfos.add(new UserInfo(userAccount.getUserId(), userType, userAccountType.getTypeName(), userInfo));
          }
        }
      }
      List<String> roleNames = new ArrayList<>();
      List<SimpleGrantedAuthority> authorities = new ArrayList<>();
      StringBuilder roleIds = new StringBuilder();
      List<Role> roles = roleService.queryRoleByUserIds(userAccount.getUserId().toString());
      if (null != roles) {
        for (Role role : roles) {
          roleNames.add(role.getRoleRemark());
          authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName().toUpperCase()));
          roleIds.append(",").append(role.getRoleId());
        }
      }
      StarUserInfo starUserInfo = new StarUserInfo(userAccount.getEnabled(), userAccount.getNonLocked(), username, userAccount.getPassword(), userAccount.getUserId(), userAccount.getUserType(), userAccount.getTypeName(), authorities, roleNames, userInfos);
      if (roleIds.length() > 0 && starUserInfo.getUserType().indexOf(UserType.userInfoService.name()) != -1) {
        String rids = roleIds.substring(1).toString();
        List<ResourceDto> resources = resourceService.queryResourceByRoleIds(rids);
        if (null != resources && !resources.isEmpty()) {
          long root = -1;
          Map<Long, List<ResourceDto>> temp = new LinkedHashMap<>();

          for (ResourceDto resource : resources) {
            if (resource.getParentId() == 0) {
              root = resource.getSourceId();
            }
            Long parentId = resource.getParentId();
            List<ResourceDto> res = temp.get(parentId);
            if (null == res) {
              res = new ArrayList<>();
              temp.put(parentId, res);
            }
            res.add(resource);
          }
          if (root > 0) {
            List<Map<String, Object>> navMenus = new ArrayList<>();
            List<Map<String, Object>> menus = new ArrayList<>();
            List<ResourceDto> rootResource = temp.get(root);
            for (ResourceDto resource : rootResource) {
              Map<String, Object> nav = new LinkedHashMap<>();
              nav.put("name", resource.getSourceName());
              nav.put("icoCls", resource.getSourceIcoCls());
              nav.put("selected", true);
              navMenus.add(nav);
              Map<String, Object> menu = new LinkedHashMap<>();
              menu.put("id", "menu_" + resource.getSourceId());
              List<ResourceDto> res2 = temp.get(resource.getSourceId());
              if (null != res2) {
                List<Map<String, Object>> menuDetails = new ArrayList<>();
                for (ResourceDto resource2 : res2) {
                  Map<String, Object> item = new LinkedHashMap<>();
                  item.put("text", resource2.getSourceName());
                  List<ResourceDto> res3 = temp.get(resource2.getSourceId());
                  if (null != res3) {
                    List<Map<String, Object>> details = new ArrayList<>();
                    for (ResourceDto resource3 : res3) {
                      String mainUri = this.resourceService.getResourceMainUri(rids, resource3.getSourceId());
                      Map<String, Object> ele = new LinkedHashMap<>();
                      ele.put("id", "item_" + resource3.getSourceId());
                      ele.put("text", resource3.getSourceName());
                      ele.put("href", mainUri);
                      details.add(ele);
                    }
                    item.put("items", details);
                  }
                  menuDetails.add(item);
                }
                menu.put("menu", menuDetails);
              }
              menus.add(menu);
            }
            starUserInfo.setMenu(menus);
            starUserInfo.setNavMenus(navMenus);
          }
        }
      }
      return starUserInfo;
    }
    return null;
  }
}
