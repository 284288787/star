var user = getLoginInfo();
if(! user){
  document.location.href='login.html?redirect_url=pay.html';
}
var orderId = getLocalData("pay_orderId");
if(! orderId){
  mui.alert("此次支付已经失效，请重新选择订单进行支付");
  document.location.href='orderlist.html';
}
var distributor = getShopInfo();
var states={1: '待支付', 2: '待提货', 3: '已提货', 4: '已退货', 5: '已删除'};
$(function() {
  ajax({
    url: '/weixin/ticket/jssdk',
    data: {url: document.location.href},
    success: function(data) {
      //通过config接口注入权限验证配置
      wx.config({
        debug: false,
        appId: 'wx43fd4135600dcee3',
        timestamp: data.timestamp,
        nonceStr: data.nonceStr,
        signature: data.signature,
        jsApiList: ['chooseWXPay',
          'onMenuShareTimeline',
          'onMenuShareAppMessage',
          'onMenuShareQQ',
          'onMenuShareWeibo'
        ]
      });
    }
  });
  ajax({
    url: '/api/order/getOrder',
    data: {orderId: orderId},
    success: function(order){
      var money = order.totalMoney;
      if(order.despatchMoney) money += order.despatchMoney;
      $("p.num b").text((money / 100.0).toFixed(2));
      $("button.mui-btn-block").on('tap', function(){
        ajax({
          url: "/weixin/pay/unifiedOrder",
          data: {
            memberId: user.memberId,
            openId: user.openId,
            orderId: orderId
          },
          success: function(data){
            var config = {
              'timestamp': data.timeStamp, 
              'nonceStr': data.nonce_str,
              'package': 'prepay_id=' + data.prepay_id, 
              'signType': 'MD5', 
              'paySign': data.sign, 
              'success': function(res1) {
                document.location.href='paysuccess.html';
              },
              'cancel': function(res2) {
                //取消支付
                //alert(JSON.stringify(res2))
              },
              'fail': function(res3) {
                //支付失败
                //alert(JSON.stringify(res3))
              }
            };
            //alert(JSON.stringify(config))
            wx.chooseWXPay(config);
          }
        });
      });
      if(order.state != 1){
        $("button.mui-btn-block").css({"background": "#b3a5a5", "border": "1px solid #b3a5a5"});
        $("button.mui-btn-block").off().text(states[order.state] + "订单，不能支付");
      }
    }
  });
});