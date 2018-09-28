/**create by liuhua at 2018年7月13日 下午2:14:46**/
package com.star.truffle.core.ehcache;

import lombok.Data;

@Data
public class StarEhcacheCache {

  //缓存名称(必须唯一)
  private String name;
  //内存最多可以存放的元素的数量
  private Integer maxEntriesLocalHeap;
  //元素最大闲置时间
  private Long timeToIdleSeconds;
  //元素最大生存时间
  private Long timeToLiveSeconds;
  //元素是否永久缓存
  private Boolean eternal;
  //缓存清理时间
  private Long diskExpiryThreadIntervalSeconds;
}
