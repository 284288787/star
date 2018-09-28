/**create by liuhua at 2018年7月22日 下午3:33:26**/
package com.star.truffle.module.user.dao.write;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.star.truffle.module.user.domain.Uri;

public interface UriWriteDao {

  public int batchSaveUri(List<Uri> uris);
  
  public int deleteUri(String uri);
  
  /**
   * 删除本次没有更新过的记录，确保存储的都是最新有效的连接
   * @param now 
   * @return
   */
  public int deleteUriBeforeNow(@Param("now") Date now);
  
  /**
   * 更新sourceId 和 intro
   * @param uri
   * @param uris
   * @return
   */
  public int updateUri(@Param("uri") Uri uri, @Param("uris") String uris);

  public int updateUriDate(@Param("uri") String uri);
}
