/**create by liuhua at 2018年7月13日 下午2:12:24**/
package com.star.truffle.core.ehcache;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Data;

@Data
@ConfigurationProperties(prefix = "star-ehcache")
public class StarEhcacheProperties {

  //指定除自身之外的网络群体中其他提供同步的主机列表，用“|”分开不同的主机
  //peerDiscovery=manual,rmiUrls=//localhost:40004/metaCache|//localhost:40005/metaCache
  private String peerProvider;
  //配宿主主机配置监听程序
  //port=40004,socketTimeoutMillis=2000
  private String peerListener;
  
  private List<StarEhcacheCache> caches;
}
