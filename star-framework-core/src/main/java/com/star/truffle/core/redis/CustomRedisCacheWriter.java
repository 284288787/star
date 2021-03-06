/**create by liuhua at 2018年7月3日 上午10:40:12**/
package com.star.truffle.core.redis;

import java.time.Duration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.lang.Nullable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomRedisCacheWriter implements RedisCacheWriter {

 private RedisCacheWriter delegate;

 public CustomRedisCacheWriter(RedisCacheWriter delegate) {
   this.delegate = delegate;
 }

 @Override
 public void put(String s, byte[] bytes, byte[] bytes1, @Nullable Duration duration) {
   try {
     delegate.put(s, bytes, bytes1, duration);
   } catch (Exception e) {
     handleException(e);
   }
 }

 @Nullable
 @Override
 public byte[] get(String s, byte[] bytes) {
   try {
     return delegate.get(s, bytes);
   } catch (Exception e) {
     return handleException(e);
   }
 }

 @Nullable
 @Override
 public byte[] putIfAbsent(String s, byte[] bytes, byte[] bytes1, @Nullable Duration duration) {
   try {
     return delegate.putIfAbsent(s, bytes, bytes1, duration);
   } catch (Exception e) {
     return handleException(e);
   }
 }

 @Override
 public void remove(String s, byte[] bytes) {
   try {
     delegate.remove(s, bytes);
   } catch (Exception e) {
     handleException(e);
   }
 }

 @Override
 public void clean(String s, byte[] bytes) {
   try {
     delegate.clean(s, bytes);
   } catch (Exception e) {
     handleException(e);
   }
 }

 private <T> T handleException(Exception e) {
   log.error("handleException", e);
   return null;
 }
}