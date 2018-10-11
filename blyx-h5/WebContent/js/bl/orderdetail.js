var user = getLoginInfo();
if(! user){
  document.location.href='login.html?redirect_url=orderlist.html#item2mobile';
}
var distributor = getShopInfo();
var orderId = getParam("orderId");
mui('.main').on('tap', '.cancel', function() {
  mui('.mui-popover').popover('hide');
});
mui('.mui-scroll-wrapper').scroll({
  deceleration: 0.0005
});
$(function() {
  initOrderDetail();
});
var states={'11': '待支付', '12': '待提货', '13': '已提货', '14': '已退货', '15': '已删除', '21': '待支付', '22': '待发货', '23': '已收货', '24': '已退货', '25': '已删除'};
var btnnames = {1: '去支付', 2: '确认提货', 3: '删除', 4: '删除', 5: ''};
function initOrderDetail(){
  var originalPrice = 0;
  var totalMoney = 0;
  var num = 0;
  ajax({
    url: '/api/order/getOrder',
    data: {'orderId' : orderId},
    success: function(data){
      $(".goodslist ul").html("");
      var items = data.details;
      if(null != items && items.length > 0){
        for(var o in items){
          var item = items[o];
          originalPrice += item.originalPrice / 100.0 * item.count;
          totalMoney += item.price / 100.0 * item.count;
          num += item.count;
          $(".goodslist ul").append('<li>\
              <span><img src="'+IMAGE_PREFIX+item.mainPictureUrl+'" alt=""></span>\
              <p>'+item.title+'</p>\
              <p>'+item.specification+'</p>' +
              '<p class="red">' + (data.deliveryType==2 ? '&nbsp;' : (item.pickupTime != null ? item.pickupTime.formatDate('M月d日 h点') : '随时')+'可提货') + '</p>' +
              '<p class="price">￥<b>'+((item.price / 100.0).toFixed(2))+'</b>\
                <del>$'+((item.originalPrice / 100.0).toFixed(2))+'</del>\
                <span class="fl">x'+item.count+'</span>\
              </p>\
            </li>');
        }
      }
      $(".orderstate").text(states[data.deliveryType + '' + data.state]);
      $("#mean3 span").text(btnnames[data.state]);
      $("#mean3").on("tap", function(){
        if(data.state==1){
          putLocalData("pay_orderId", orderId);
          document.location.href='pay.html';
        }else if(data.state==2){
          ajax({
            url: '/api/order/pickupOrder',
            data: {orderId: orderId},
            success: function(){
              $("#mean3").css("background", "#b3a5a5").removeClass("topay");
              $("#mean3").off();
            }
          });
        }else if(data.state>2){
          ajax({
            url: '/api/order/deleteOrder',
            data: {orderId: orderId},
            success: function(){
              document.location.href='orderlist.html';
            }
          });
        }
        return false;
      });
      $(".totaldiv .productNum").text(num);
      $(".totaldiv .productMoney").text(totalMoney.toFixed(2));
      $(".totaldiv .productOriginalPrice").text(originalPrice.toFixed(2));
      if(data.deliveryType==2){
        $(".totaldiv .despatchMoney").text((data.despatchMoney/100.0).toFixed(2));
        $("#item2").append('<p>收件人：'+ data.deliveryName + '</p>');
        $("#item2").append('<p>收件人电话：'+ data.deliveryMobile + '</p>');
        $("#item2").append('<p>收件人地址：'+ data.provinceName + data.cityName + data.areaName + data.deliveryAddress + '</p>');
      }else{
        $(".totaldiv .despatchMoney").parent().remove();
        $("#item2").append('<p>取件人：'+ data.name + '</p>');
        $("#item2").append('<p>取件电话：'+ data.mobile + '</p>');
        if(data.state > 1){
          $("#item2").append('<p>取货码：'+ data.pickupCode + '</p>');
        }
        $("#item2").append('<p>取件地址：'+ data.regionProvinceName + data.regionCityName + data.regionAreaName + data.shopAddress + '</p>');
        $("#item2").append('<p>店铺名称：'+ data.shopName + '</p>');
      }
      $("#item2").removeClass("mui-hidden");
      $(".totaldiv .productTotalMoney").text(totalMoney.toFixed(2));
      $(".ordercode span").text(data.orderCode);
      $(".ordertime span").text(data.createTime);
    }
  });
}