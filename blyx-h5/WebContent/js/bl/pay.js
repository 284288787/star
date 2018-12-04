var f = getParam("f");
var user = null;
if(f == 'd'){
  user = getDistributorLoginInfo();
}
if(! user){
  user = getLoginInfo();
}
var to = "";
var orderId = getParam("oid");
if(!orderId) orderId = getLocalData("pay_orderId");
else to = "distributor";
if(! user){
  document.location.href='login.html?redirect_url=pay.html?oid='+orderId;
}

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
        appId: 'wx8a05f2d3eb34111f',
        timestamp: data.timestamp,
        nonceStr: data.nonceStr,
        signature: data.signature,
        jsApiList: [
          'chooseWXPay',
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
      var m = (order.createTime.gapSeconds(30 * 60) / 60).toFixed(0);
      if(m <= 0){
        $("p.info").text("订单已失效");
        $("button.mui-btn-block").css({"background": "#b3a5a5", "border": "1px solid #b3a5a5"});
        $("button.mui-btn-block").off().text(states[order.state] + "订单，不能支付");
      } else $("p.info span").text(m);
      $("button.mui-btn-block").on('tap', function(){
        ajax({
          url: "/weixin/pay/unifiedOrder",
          data: {
            memberId: user.memberId ? user.memberId : user.distributorId,
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
                delLocalData("pay_orderId");
                if(to) putLocalData("pay_orderId_from", "distributor");
                document.location.href='paysuccess.html?oid='+orderId+'&'+(f? ('f='+f) : '');
              },
              'cancel': function(res2) {
                //取消支付
                mui.alert("您取消了本次支付，可前往我的订单继续支付，订单有效期为30分钟。");
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
      if(order.state != 1 || order.deleted == 1){
        $("button.mui-btn-block").css({"background": "#b3a5a5", "border": "1px solid #b3a5a5"});
        $("button.mui-btn-block").off().text(states[order.state] + "订单，不能支付");
      }
    }
  });
});