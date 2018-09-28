/**create by liuhua at 2018年7月14日 上午9:10:40**/
package com.star.truffle.core.util;

import java.util.HashMap;
import java.util.Map;

public class IdWorkerFactory {

  private static Map<String, IdWorker> map = new HashMap<>();
  
  public static synchronized IdWorker getIdWorker(long workerId, long datacenterId){
    String key = workerId + "_" + datacenterId;
    IdWorker idWorker = map.get(key);
    if (null == idWorker) {
      idWorker = new IdWorker(workerId, datacenterId);
      map.put(key, idWorker);
    }
    return idWorker;
  }
}
