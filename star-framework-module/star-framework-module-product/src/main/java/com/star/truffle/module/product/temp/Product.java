/**create by liuhua at 2018年9月3日 上午10:01:24**/
package com.star.truffle.module.product.temp;

import java.util.Date;

import com.star.truffle.module.build.annotation.StarDomainName;
import com.star.truffle.module.build.annotation.StarField;
import com.star.truffle.module.build.annotation.StarFieldAdd;
import com.star.truffle.module.build.annotation.StarFieldEdit;
import com.star.truffle.module.build.annotation.StarFieldList;
import com.star.truffle.module.build.annotation.StarFieldQuery;
import com.star.truffle.module.build.constant.DsType;
import com.star.truffle.module.build.constant.InputType;

@StarDomainName(caption = "商品信息", createTable = true, tableName = "product")
public class Product {

  @StarField(caption = "商品ID", primary = true, dsType = DsType.BIGINT, dsLength = 20)
  @StarFieldList(inputType = InputType.text)
  private Long productId;
  
  @StarField(caption = "商品标题", dsType = DsType.VARCHAR, dsLength = 50)
  @StarFieldAdd(inputType = InputType.text, requiredMsg = "必填", zhengze = ".*{2,25}", zhengzeMsg = "长度在2至25个字", placeholder = "商品标题")
  @StarFieldEdit(inputType = InputType.text, requiredMsg = "必填", zhengze = ".*{2,25}", zhengzeMsg = "长度在2至25个字", placeholder = "商品标题")
  @StarFieldList(inputType = InputType.text)
  @StarFieldQuery(inputType = InputType.text)
  private String title;
  
  @StarField(caption = "副标题", dsType = DsType.VARCHAR, dsLength = 80)
  @StarFieldAdd(inputType = InputType.text, requiredMsg = "必填", zhengze = ".*{2,40}", zhengzeMsg = "长度在2至40个字", placeholder = "副标题")
  @StarFieldEdit(inputType = InputType.text, requiredMsg = "必填", zhengze = ".*{2,40}", zhengzeMsg = "长度在2至40个字", placeholder = "副标题")
  @StarFieldList(inputType = InputType.text)
  private String subtitle;       //小标题，悬浮在图片左下角的一行字，例如：老长沙月饼，儿时的味道
  
  @StarField(caption = "商品标签", dsType = DsType.VARCHAR, dsLength = 8)
  @StarFieldAdd(inputType = InputType.text, requiredMsg = "必填", zhengze = ".*{4,4}", zhengzeMsg = "4个字", placeholder = "商品标签，例如：十点爆款")
  @StarFieldEdit(inputType = InputType.text, requiredMsg = "必填", zhengze = ".*{4,4}", zhengzeMsg = "4个字", placeholder = "商品标签，例如：十点爆款")
  @StarFieldList(inputType = InputType.text)
  private String tag;            //列表图片右上角，例如：十点爆款
  
  @StarField(caption = "预售时间", dsType = DsType.DATETIME, dsLength = 0)
  @StarFieldAdd(inputType = InputType.date, inputValue = "Y-m-d", requiredMsg = "必选")
  @StarFieldEdit(inputType = InputType.date, inputValue = "Y-m-d", requiredMsg = "必选")
  @StarFieldList(inputType = InputType.text, inputValue = "Y-m-d")
  private Date presellTime;      //预售时间
  
  @StarField(caption = "下架时间", dsType = DsType.DATETIME, dsLength = 0)
  @StarFieldAdd(inputType = InputType.date, inputValue = "Y-m-d", requiredMsg = "必选")
  @StarFieldEdit(inputType = InputType.date, inputValue = "Y-m-d", requiredMsg = "必选")
  @StarFieldList(inputType = InputType.text, inputValue = "Y-m-d")
  private Date offShelfTime;     //下架时间
  
  @StarField(caption = "提货时间", dsType = DsType.DATETIME, dsLength = 0)
  @StarFieldAdd(inputType = InputType.date, inputValue = "Y-m-d", requiredMsg = "必选")
  @StarFieldEdit(inputType = InputType.date, inputValue = "Y-m-d", requiredMsg = "必选")
  @StarFieldList(inputType = InputType.text, inputValue = "Y-m-d")
  private Date pickupTime;       //提货时间
  
  @StarField(caption = "商品状态", dsType = DsType.TINYINT, dsLength = 1, enumName = "ProductEnum", enumOptTypes = "{\"state\":\"int\",\"caption\":\"String\"}", enumOptValues = "{\"onshelf\": {\"state\": 1, \"caption\": \"上架\"}, \"presell\": {\"state\": 2, \"caption\": \"预售\"}, \"sellout\": {\"state\": 3, \"caption\": \"售罄\"}, \"offshelf\": {\"state\": 4, \"caption\": \"下架\"}, \"disabled\": {\"state\": 5, \"caption\": \"禁用\"}, \"deleted\": {\"state\": 6, \"caption\": \"删除\"}}")
  @StarFieldAdd(inputType = InputType.select, inputValue = "{\"1\":\"上架\",\"2\":\"预售\",\"3\":\"售罄\",\"4\":\"下架\",\"5\":\"禁用\",\"6\":\"删除\"}", requiredMsg = "必选")
  @StarFieldEdit(inputType = InputType.select, inputValue = "{\"1\":\"上架\",\"2\":\"预售\",\"3\":\"售罄\",\"4\":\"下架\",\"5\":\"禁用\",\"6\":\"删除\"}", requiredMsg = "必选")
  @StarFieldList(inputType = InputType.select, inputValue = "{value:'1:上架;2:预售;3:售罄;4:下架;5:禁用;6:删除'}")
  @StarFieldQuery(inputType = InputType.select, inputValue = "{\"1\":\"上架\",\"2\":\"预售\",\"3\":\"售罄\",\"4\":\"下架\",\"5\":\"禁用\",\"6\":\"删除\"}")
  private Integer state;         //状态 1上架 2预售 3售罄 4下架 5禁用 6删除
  
  @StarField(caption = "商品原价", dsType = DsType.INT, dsLength = 10)
  @StarFieldAdd(inputType = InputType.text, requiredMsg = "必填", zhengze="money", zhengzeMsg="金额不正确")
  @StarFieldEdit(inputType = InputType.text, requiredMsg = "必填", zhengze="money", zhengzeMsg="金额不正确")
  @StarFieldList(inputType = InputType.text)
  private Integer originalPrice; //原价
  
  @StarField(caption = "商品售价", dsType = DsType.INT, dsLength = 10)
  @StarFieldAdd(inputType = InputType.text, requiredMsg = "必填", zhengze="money", zhengzeMsg="金额不正确")
  @StarFieldEdit(inputType = InputType.text, requiredMsg = "必填", zhengze="money", zhengzeMsg="金额不正确")
  @StarFieldList(inputType = InputType.text)
  private Integer price;         //售价
  
  @StarField(caption = "供应商", dsType = DsType.VARCHAR, dsLength = 20)
  @StarFieldAdd(inputType = InputType.text, requiredMsg = "必填", zhengze = ".*{2,10}", zhengzeMsg = "长度在2至10个字", placeholder = "供应商 例如：臻有味食品")
  @StarFieldEdit(inputType = InputType.text, requiredMsg = "必填", zhengze = ".*{2,10}", zhengzeMsg = "长度在2至10个字", placeholder = "供应商 例如：臻有味食品")
  @StarFieldList(inputType = InputType.text)
  private String supplier;       //供应商 例如：臻有味食品
  
  @StarField(caption = "商品品牌", dsType = DsType.VARCHAR, dsLength = 20)
  @StarFieldAdd(inputType = InputType.text, placeholder = "品牌 例如：宏兴隆")
  @StarFieldEdit(inputType = InputType.text, placeholder = "品牌 例如：宏兴隆")
  @StarFieldList(inputType = InputType.text)
  private String brand;          //品牌 例如：宏兴隆
  
  @StarField(caption = "商品规格", dsType = DsType.VARCHAR, dsLength = 80)
  @StarFieldAdd(inputType = InputType.text, requiredMsg = "必填", zhengze = ".*{2,40}", zhengzeMsg = "长度在2至40个字", placeholder = "规格 例如：1袋")
  @StarFieldEdit(inputType = InputType.text, requiredMsg = "必填", zhengze = ".*{2,40}", zhengzeMsg = "长度在2至40个字", placeholder = "规格 例如：1袋")
  @StarFieldList(inputType = InputType.text)
  private String specification;  //规格 例如：1袋
  
  @StarField(caption = "商品产地", dsType = DsType.VARCHAR, dsLength = 40)
  @StarFieldAdd(inputType = InputType.text, requiredMsg = "必填", zhengze = ".*{2,20}", zhengzeMsg = "长度在2至20个字", placeholder = "产地 例如：中国")
  @StarFieldEdit(inputType = InputType.text, requiredMsg = "必填", zhengze = ".*{2,20}", zhengzeMsg = "长度在2至20个字", placeholder = "产地 例如：中国")
  @StarFieldList(inputType = InputType.text)
  private String originPlace;    //产地 例如：中国
  
  @StarField(caption = "商品描述", dsType = DsType.LONGTEXT, dsLength = 0)
  @StarFieldAdd(inputType = InputType.text)
  @StarFieldEdit(inputType = InputType.text)
  private String description;    //商品描述
  
  @StarField(caption = "关注人数", dsType = DsType.INT, dsLength = 10)
  @StarFieldList(inputType = InputType.text)
  private Integer subscribers;      //关注人数
  
  @StarField(caption = "创建日期", dsType = DsType.DATETIME, dsLength = 0, defaultAddFieldValue = "new Date()", defaultAddFieldValueType = "java.util.Date")
  @StarFieldList(inputType = InputType.date, inputValue = "Y-m-d")
  private Date createTime;       //创建时间
  
  @StarField(caption = "创建人", dsType = DsType.VARCHAR, dsLength = 30)
  @StarFieldList(inputType = InputType.text)
  private String createUser;
  
  @StarField(caption = "创建日期", dsType = DsType.DATETIME, dsLength = 0, defaultEditFieldValue = "new Date()", defaultEditFieldValueType = "java.util.Date")
  @StarFieldList(inputType = InputType.date, inputValue = "Y-m-d")
  private Date updateTime;       //更新时间
  
  @StarField(caption = "更新人", dsType = DsType.VARCHAR, dsLength = 30)
  @StarFieldList(inputType = InputType.text)
  private String updateUser;
  
  @StarField(caption = "提成类型", dsType = DsType.TINYINT, dsLength = 1, enumName = "BrokerageTypeEnum", enumOptTypes = "{\"type\":\"int\",\"caption\":\"String\"}", enumOptValues = "{\"money\": {\"type\": 1, \"caption\": \"固定金额\"}, \"percent\": {\"type\": 2, \"caption\": \"售价百分比\"}}")
  @StarFieldAdd(inputType = InputType.select, inputValue = "{\"1\":\"固定金额\",\"2\":\"售价百分比\"}", requiredMsg = "必选")
  @StarFieldEdit(inputType = InputType.select, inputValue = "{\"1\":\"固定金额\",\"2\":\"售价百分比\"}", requiredMsg = "必选")
  @StarFieldList(inputType = InputType.select, inputValue = "{value:'1:固定金额;2:售价百分比'}")
  @StarFieldQuery(inputType = InputType.select, inputValue = "{\"1\":\"固定金额\",\"2\":\"售价百分比\"}")
  private Integer brokerageType; //提成类型：1固定金额 2售价百分比
  
  @StarField(caption = "商品提成", dsType = DsType.INT, dsLength = 8)
  @StarFieldAdd(inputType = InputType.text, requiredMsg = "必填")
  @StarFieldEdit(inputType = InputType.text, requiredMsg = "必填")
  @StarFieldList(inputType = InputType.text)
  private Integer brokerageValue;//提成值 例如：设置值为5，brokerageType=1，表示5分钱；=2，表示0.05%
}
