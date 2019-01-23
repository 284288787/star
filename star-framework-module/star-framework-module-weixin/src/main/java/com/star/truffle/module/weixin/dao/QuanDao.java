/**create by liuhua at 2019年1月23日 上午11:23:10**/
package com.star.truffle.module.weixin.dao;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.star.truffle.core.jackson.StarJson;
import com.star.truffle.core.okhttp.StarOkHttpClient;
import com.star.truffle.module.weixin.domain.CardList;

import okhttp3.OkHttpClient;

public class QuanDao {
  private static StarJson starJson = new StarJson(new ObjectMapper());
  private static StarOkHttpClient starOkHttpClient = new StarOkHttpClient(new OkHttpClient());
  private static AccessToken accessToken = null;
  private static String appId = "wx8a05f2d3eb34111f";
  private static String appSecret = "e7de8670fa659ca9293fd8be95125919";
  private static final String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
  
  private static final String getCardListUrl = "https://api.weixin.qq.com/card/user/getcardlist?access_token=%s";
  private static final String getCardDetailUrl = "https://api.weixin.qq.com/card/get?access_token=%s";
  private static final String checkCardUrl = "https://api.weixin.qq.com/card/code/get?access_token=%s";
  private static final String consumeCardUrl = "https://api.weixin.qq.com/card/code/consume?access_token=%s";
  private static final String createCardUrl = "https://api.weixin.qq.com/card/create?access_token=%s";
  
  public static void main(String[] args) throws IOException {
//    CardList cardList = getCardList("ooQ_o1eQ33P9zguW7U4BvrvUa4NY");
//    System.out.println(starJson.obj2string(cardList));
//    System.out.println(canConsume("607054664258"));
//    System.out.println(starJson.obj2string(getCardDetail("poQ_o1Zl9PgJ1N9Ce8UKFKRmnPfU")));
    createGroupon("双人套餐\\n -进口红酒一支。\\n孜然牛肉一份。");
  }
  
  //团购券
  public static void createGroupon(String dealDetail) throws IOException {
    Map<String, Object> params = new HashMap<>();
    {
      Map<String, Object> card = new HashMap<>();
      card.put("card_type", "GROUPON");
      {
        Map<String, Object> groupon = new HashMap<>();
        {
          Map<String, Object> baseinfo = new HashMap<>();
          //必填信息开始
          baseinfo.put("logo_url", "http://mmbiz.qlogo.cn/mmbiz_jpg/fPINJCcdCoRKr8MZytt1GxVZt13xNyPcvNjALDeMONjKDBwXY8QDm5AVPMdakJwAj2r5QelLykNQjcQk7MmFiaA/0?wx_fmt=jpeg");
          baseinfo.put("code_type", "CODE_TYPE_NONE");
          baseinfo.put("brand_name", "五杂优选");
          baseinfo.put("title", "132元双人火锅套餐");
          baseinfo.put("color", "Color030");
          baseinfo.put("notice", "使用时向服务员出示此券");
          baseinfo.put("description", "不可与其他优惠同享\n如需团购券发票，请在消费时向商户提出\n店内均可使用，仅限堂食");
          {
            Map<String, Object> sku = new HashMap<>();
            sku.put("quantity", 120);
            baseinfo.put("sku", sku);
          }
          {
            Map<String, Object> dateinfo = new HashMap<>();
            dateinfo.put("type", "DATE_TYPE_FIX_TERM");
            dateinfo.put("fixed_begin_term", 0);
            dateinfo.put("fixed_term", 3);
            baseinfo.put("date_info", dateinfo);
          }
          //必填信息结束
          //非必填信息开始
          baseinfo.put("service_phone", "020-88888888");
          baseinfo.put("use_all_locations", true);
          baseinfo.put("center_title", "立即使用");
          baseinfo.put("center_sub_title", "在下单时可直接使用");
          baseinfo.put("center_url", "http://yx.hnkbmd.com");
          baseinfo.put("custom_url_name", "五杂优选");
          baseinfo.put("custom_url", "http://yx.hnkbmd.com");
          baseinfo.put("custom_url_sub_title", "正品，质保");
          baseinfo.put("promotion_url_name", "产品介绍");
          baseinfo.put("promotion_url_sub_title", "卖场大优惠");
          baseinfo.put("promotion_url", "http://yx.hnkbmd.com");
          baseinfo.put("get_limit", 5); //每人可领券的数量限制,不填写默认为50。
          baseinfo.put("use_limit", 5); //每人可核销的数量限制,不填写默认为50。
          baseinfo.put("can_share", true); //卡券领取页面是否可分享。
          baseinfo.put("can_give_friend", false); //卡券是否可转赠。
          //非必填信息结束
          groupon.put("base_info", baseinfo);
          Map<String, Object> advancedinfo = new HashMap<>();
          {
            Map<String, Object> condition = new HashMap<>();
            condition.put("can_use_with_other_discount", false); //不可以与其他类型共享门槛 ，填写false时系统将在使用须知里 拼写“不可与其他优惠共享”， 填写true时系统将在使用须知里 拼写“可与其他优惠共享”， 默认为true
            advancedinfo.put("use_condition", condition);
          }
          {
            Map<String, Object> abstrac = new HashMap<>();
            abstrac.put("abstract", "呵呵呵"); //封面描述
            abstrac.put("icon_url_list", new String[] {"http://mmbiz.qpic.cn/mmbiz_png/fPINJCcdCoRGDibeB4OSZL48nB1lWGecxtNXmTQQDzER8oUCSp2drhulKfhT6Rvy7lA28KDnvlaS2CyHQZLPSWw/0?wx_fmt=png"}); //封面图片
            advancedinfo.put("abstract", abstrac);
          }
          {
            Map<String, Object> til1 = new HashMap<>();
            til1.put("text", "此菜品精选食材，以独特的烹饪方法，最大程度地刺激食 客的味蕾"); //封面描述
            til1.put("image_url", "http://mmbiz.qpic.cn/mmbiz_png/fPINJCcdCoRGDibeB4OSZL48nB1lWGecxtNXmTQQDzER8oUCSp2drhulKfhT6Rvy7lA28KDnvlaS2CyHQZLPSWw/0?wx_fmt=png"); //封面图片
            Map<String, Object> til2 = new HashMap<>();
            til2.put("text", "多萨法撒旦方法"); //封面描述
            til2.put("image_url", "http://mmbiz.qpic.cn/mmbiz_png/fPINJCcdCoRGDibeB4OSZL48nB1lWGecxtNXmTQQDzER8oUCSp2drhulKfhT6Rvy7lA28KDnvlaS2CyHQZLPSWw/0?wx_fmt=png"); //封面图片
            advancedinfo.put("text_image_list", new Map[] {til1, til2});
          }
          //商家服务类型： BIZ_SERVICE_DELIVER 外卖服务； BIZ_SERVICE_FREE_PARK 停车位； BIZ_SERVICE_WITH_PET 可带宠物； BIZ_SERVICE_FREE_WIFI 免费wifi， 可多选
          advancedinfo.put("business_service", new String[] {"BIZ_SERVICE_FREE_WIFI", "BIZ_SERVICE_WITH_PET"});
          groupon.put("advanced_info", advancedinfo);
          groupon.put("deal_detail", dealDetail); //团购券专用，团购详情。例如：双人套餐\n -进口红酒一支。\n孜然牛肉一份。
        }
        card.put("groupon", groupon);
      }
      params.put("card", card);
    }
    String args = starJson.obj2string(params);
    System.out.println(args);
    String result = starOkHttpClient.postJson(String.format(createCardUrl, getAccessToken()), args);
    System.out.println(result);
  }
  
  public static synchronized String getAccessToken() throws IOException {
    if (null == accessToken || !accessToken.isExpires()) {
      String res = starOkHttpClient.get(String.format(accessTokenUrl, appId, appSecret));
      accessToken = starJson.str2obj(res, AccessToken.class);
    }
    return accessToken.getAccess_token();
  }
  
  /**
   * 是否可以核销
   * 
   * @param code
   * @throws IOException
   */
  public static boolean canConsume(String code) throws IOException {
    String temp = starOkHttpClient.postJson(String.format(checkCardUrl, getAccessToken()), "{\"code\": \"" + code + "\", \"check_consume\": false}");
    boolean bool = false;
    if (temp.indexOf("card") != -1) {
      Map<String, Object> map = starJson.str2obj(temp, new TypeReference<Map<String, Object>>() {});
      bool = Boolean.parseBoolean(map.get("can_consume").toString());
    }
    return bool;
  }
  
  /**
   * 是否可以核销 return 0 无效的code 1 有效的code，并且cardId没有填 2 有效的code，并且属于cardId 3
   * 有效的code，但不属于cardId
   */
  public static String canConsume(String code, List<String> cardIds) throws IOException {
    String temp = starOkHttpClient.postJson(String.format(checkCardUrl, getAccessToken()), "{\"code\": \"" + code + "\", \"check_consume\": false}");
    String cardIdRes = "";
    @SuppressWarnings("unused")
    int res = 0;
    if (temp.indexOf("card") != -1) {
      Map<String, Object> map = starJson.str2obj(temp, new TypeReference<Map<String, Object>>() {});
      boolean bool = Boolean.parseBoolean(map.get("can_consume").toString());
      if (bool) {
        res = 1;
        if (null != cardIds && !cardIds.isEmpty()) {
          @SuppressWarnings("unchecked")
          Map<String, Object> cardObject = (Map<String, Object>) map.get("card");
          for (String cardId : cardIds) {
            if (StringUtils.isNotBlank(cardId)) {
              if (cardId.equals(cardObject.get("card_id"))) {
                res = 2;
                cardIdRes = cardId;
                break;
              } else {
                res = 3;
              }
            }
          }
        }
      }
    }
    return cardIdRes;
  }
  
  /**
   * 核销卡券
   * 
   * @param code
   * @throws IOException
   */
  public static boolean consumeCard(String code) throws IOException {
    String temp = starOkHttpClient.postJson(String.format(consumeCardUrl, getAccessToken()), "{\"code\": \"" + code + "\"}");
    System.out.println(String.format("核销卡券，code：%s，result: %s", code, temp));
    if (temp.indexOf("card") != -1) {
      return true;
    }
    return false;
  }
  
  /**
   * 所有卡券
   * 
   * @param openId
   * @throws IOException
   */
  public static CardList getCardList(String openId) throws IOException {
    String temp = starOkHttpClient.postJson(String.format(getCardListUrl, getAccessToken()), "{\"openid\": \"" + openId + "\"}");
    CardList cardList = starJson.str2obj(temp, new TypeReference<CardList>() {});
    return cardList;
  }
  
  /**
   * 卡详情
   * 
   * @param cardId
   * @return
   * @throws IOException
   */
  public static Map<String, Object> getCardDetail(String cardId) throws IOException {
    Map<String, Object> res = new HashMap<>();
    String data = "{\"card_id\": \"" + cardId + "\"}";
    String temp = starOkHttpClient.postJson(String.format(getCardDetailUrl, getAccessToken()), data);
    System.out.println(temp);
    if (temp.indexOf("card") != -1) {
      Map<String, Object> map = starJson.str2obj(temp, new TypeReference<Map<String, Object>>() {
      });
      String cardStr = starJson.obj2string(map.get("card"));
      map = starJson.str2obj(cardStr, new TypeReference<Map<String, Object>>() {
      });
      // 团购券：GROUPON; 折扣券：DISCOUNT; 礼品券：GIFT; 代金券：CASH; 通用券：GENERAL_COUPON;
      // 会员卡：MEMBER_CARD;
      // 景点门票：SCENIC_TICKET ；电影票：MOVIE_TICKET； 飞机票：BOARDING_PASS；
      // 会议门票：MEETING_TICKET； 汽车票：BUS_TICKET;
      String cardType = map.get("card_type").toString();
      Object object = map.get(cardType.toLowerCase());
      if (null == object) {
        System.out.println("券详情错误：" + object);
      }
      cardStr = starJson.obj2string(object);
      map = starJson.str2obj(cardStr, new TypeReference<Map<String, Object>>() {
      });
      @SuppressWarnings("unchecked")
      Map<String, Object> obj = (Map<String, Object>) map.get("base_info");
      res.put("cardType", cardType);
      res.put("title", obj.get("title"));
      res.put("getLimit", obj.get("get_limit"));
      switch (cardType) {
      case "GROUPON": // 团购券
        String detail = map.get("deal_detail").toString(); // 团购券专用，团购详情
        res.put("keys", new String[] { "detail" });
        res.put("detail", detail);
        break;
      case "DISCOUNT": // 折扣券
        int discount = Integer.parseInt(map.get("discount").toString()); // 折扣券专用，表示打折额度（百分比）。填30就是七折
        res.put("keys", new String[] { "discount" });
        res.put("discount", discount);
        break;
      case "GIFT": // 礼品券 兑换券
        String gift = map.get("gift").toString(); // 兑换券专用，填写兑换内容的名称
        res.put("keys", new String[] { "gift" });
        res.put("gift", gift);
        break;
      case "CASH": // 代金券
        int leastCost = Integer.parseInt(map.get("least_cost").toString()); // 起用金额
                                                                            // 分
        int reduceCost = Integer.parseInt(map.get("reduce_cost").toString()); // 减免金额
                                                                              // 分
        res.put("keys", new String[] { "leastCost", "reduceCost" });
        res.put("leastCost", leastCost);
        res.put("reduceCost", reduceCost);
        break;
      case "GENERAL_COUPON": // 通用券
        String general = map.get("default_detail").toString(); // 优惠券专用，填写优惠详情
        res.put("keys", new String[] { "general" });
        res.put("general", general);
        break;
      default:
        System.out.println("不支持的卡券类型");
        break;
      }
    }
    return res;
  }
}
