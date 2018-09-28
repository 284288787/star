/**create by liuhua at 2018年7月3日 上午11:04:42**/
package com.star.truffle.core.ehcache;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Configuration;
import com.star.truffle.core.ConditionalOnMapProperty;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.DiskStoreConfiguration;
import net.sf.ehcache.config.FactoryConfiguration;
import net.sf.ehcache.config.PersistenceConfiguration;
import net.sf.ehcache.distribution.RMICacheManagerPeerListenerFactory;
import net.sf.ehcache.distribution.RMICacheManagerPeerProviderFactory;
import net.sf.ehcache.distribution.RMICacheReplicatorFactory;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;

@Configuration
@EnableCaching
@ConditionalOnMapProperty(prefix = "star-ehcache")
@ConditionalOnProperty(value = "star-ehcache.enable", matchIfMissing = true)
@EnableConfigurationProperties(StarEhcacheProperties.class)
public class EHcacheCacheAutoConfig extends CachingConfigurerSupport {

//  @Bean
//  public EhCacheManagerFactoryBean cacheManagerFactoryBean(){
//    EhCacheManagerFactoryBean bean = new EhCacheManagerFactoryBean();
//    
//    bean.setConfigLocation(new ClassPathResource("encache.xml"));
//    return bean;
//  }
  @Autowired
  private StarEhcacheProperties ehcacheProperties;
  
  @Override
  public CacheManager cacheManager() {
    net.sf.ehcache.config.Configuration configuration = new net.sf.ehcache.config.Configuration()
      .diskStore(new DiskStoreConfiguration().path("java.io.tmpdir"))//临时文件目录
      ;
    if (StringUtils.isNotBlank(ehcacheProperties.getPeerProvider())) {
      //指定除自身之外的网络群体中其他提供同步的主机列表，用“|”分开不同的主机
      configuration.cacheManagerPeerProviderFactory(new FactoryConfiguration<FactoryConfiguration<?>>()
          .className(RMICacheManagerPeerProviderFactory.class.getName())
          .properties(ehcacheProperties.getPeerProvider()));
    }
    if (StringUtils.isNotBlank(ehcacheProperties.getPeerListener())) {
      //配宿主主机配置监听程序
      configuration.cacheManagerPeerListenerFactory(new FactoryConfiguration<FactoryConfiguration<?>>()
          .className(RMICacheManagerPeerListenerFactory.class.getName())
          .properties("port=40004,socketTimeoutMillis=2000"));
    }
    List<StarEhcacheCache> caches = ehcacheProperties.getCaches();
    if (null != caches && ! caches.isEmpty()) {
      for (StarEhcacheCache starEhcacheCache : caches) {
        if (null == starEhcacheCache.getMaxEntriesLocalHeap()) {
          starEhcacheCache.setMaxEntriesLocalHeap(10000);
        }
        CacheConfiguration cacheConfiguration = new CacheConfiguration(starEhcacheCache.getName(), starEhcacheCache.getMaxEntriesLocalHeap())//缓存名称(必须唯一),maxElements内存最多可以存放的元素的数量
            .memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LFU)//清理机制：LRU最近最少使用 FIFO先进先出 LFU较少使用
            //LOCALTEMPSWAP当缓存容量达到上限时，将缓存对象（包含堆和非堆中的）交换到磁盘中
            //NONE当缓存容量达到上限时，将缓存对象（包含堆和非堆中的）交换到磁盘中
            //DISTRIBUTED按照_terracotta标签配置的持久化方式执行。非分布式部署时，此选项不可用
            .persistence(new PersistenceConfiguration().strategy(PersistenceConfiguration.Strategy.NONE)).maxEntriesLocalDisk(0)//磁盘中最大缓存对象数0表示无穷大)
            .cacheEventListenerFactory(new CacheConfiguration.CacheEventListenerFactoryConfiguration().className(RMICacheReplicatorFactory.class.getName()));
        if (null != starEhcacheCache.getTimeToIdleSeconds()) {
          cacheConfiguration.setTimeToIdleSeconds(starEhcacheCache.getTimeToIdleSeconds());
        }
        if (null != starEhcacheCache.getTimeToLiveSeconds()) {
          cacheConfiguration.setTimeToLiveSeconds(starEhcacheCache.getTimeToLiveSeconds());
        }
        if (null != starEhcacheCache.getEternal()) {
          //元素是否永久缓存
          cacheConfiguration.setEternal(starEhcacheCache.getEternal());
        }
        if (null != starEhcacheCache.getDiskExpiryThreadIntervalSeconds()) {
           //缓存清理时间(默认120秒)
          cacheConfiguration.setDiskExpiryThreadIntervalSeconds(starEhcacheCache.getDiskExpiryThreadIntervalSeconds());
        }
        configuration.addCache(cacheConfiguration);
      }
    }
    net.sf.ehcache.CacheManager cm = net.sf.ehcache.CacheManager.create(configuration);
    EhCacheCacheManager cacheManager = new EhCacheCacheManager(cm);
    return cacheManager;
  }
  
}
