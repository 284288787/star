/**create by framework at 2018年09月04日 10:28:04**/
package com.star.truffle.module.product.domain;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class Product {

  // 商品ID
  private Long productId;
  // 商品标题
  private String title;
  // 副标题 悬浮在图片左下角的一行字，例如：老长沙月饼，儿时的味道
  private String subtitle;
  // 商品标签 列表图片右上角，例如：十点爆款
  private String tag;
  // 预售时间
  private Date presellTime;
  // 下架时间
  private Date offShelfTime;
  // 提货时间
  private Date pickupTime;
  // 商品状态 1上架 2预售 3售罄 4下架 5禁用 6删除
  private Integer state;
  // 商品原价
  private Integer originalPrice;
  // 商品售价
  private Integer price;
  // 供应商 例如：臻有味食品
  private String supplier;
  // 商品品牌 例如：宏兴隆
  private String brand;
  // 商品规格 例如：1袋
  private String specification;
  // 商品产地 例如：中国
  private String originPlace;
  // 商品描述
  private String description;
  // 关注人数
  private Integer subscribers;
  // 创建日期
  private Date createTime;
  // 创建人
  private String createUser;
  // 创建日期
  private Date updateTime;
  // 更新人
  private String updateUser;
  // 提成类型 1固定金额 2售价百分比
  private Integer brokerageType;
  // 二级分销商提成 例如：设置值为5，brokerageType=1，表示5分钱；=2，表示5%
  private Integer brokerageValue;
  // 一级分销商提成 金额
  private Integer brokerageFirst;
  // 含税价
  private Integer priceHan;
  // 未税价
  private Integer priceWei;
  // 供应商联系人
  private String supplierName;
  // 供应商电话
  private String supplierMobile;
}