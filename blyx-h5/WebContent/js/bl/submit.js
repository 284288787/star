var user = getLoginInfo();
if(! user){
  document.location.href='login.html?redirect_url=submit.html';
}
var distributor = getShopInfo();
mui('.main').on('tap', '.cancel', function() {
  mui('.mui-popover').popover('hide');
});
var despatchMoney = 0;
var despatchLimit = 0;
var deliveryAddress = null;
var originalPrice = 0;
var totalMoney = 0;
var num = 0;
var details = new Array();

$(function() {
  if(location.href.indexOf("#item2") != -1){
    var item = getLocalData("chooseAddress");
    if(item){
      var deliveryAddress = JSON.parse(item);
      $("#deliveryAddressId").val(deliveryAddress.id);
      $(".deliveryname").text(deliveryAddress.name);
      $(".deliverymobile").text(deliveryAddress.mobile);
      $(".deliveryaddr").text(deliveryAddress.provinceName + deliveryAddress.cityName + deliveryAddress.areaName + deliveryAddress.address);
      delLocalData("chooseAddress");
    }
    $("a[href='#item1']").removeClass("mui-active");
    $("a[href='#item2']").addClass("mui-active");
    $("#item1").removeClass("mui-active");
    $("#item2").addClass("mui-active");
  }
  $(".totaldiv .despatch").hide();
  $(".totaldiv .despatchLimit").hide();
  $(".myself input[name=name]").val(user.name);
  $(".myself input[name=mobile]").val(user.mobile);
  $("#item1 .blank span").text(distributor.provinceName + distributor.cityName + distributor.areaName + ' ' + distributor.address);
  $("#item1 .red span").text(distributor.shopName + " " + distributor.mobile);
  initOrder();
  $(".mui-control-item").on("tap", function(){
    var href=$(this).attr("href");
    if(href=='#item1'){
      $(".totaldiv .despatch").hide();
      $(".totaldiv .despatchLimit").hide();
      $(".totaldiv .productTotalMoney").text("￥"+totalMoney.toFixed(2));
      $("#mean2 .red").text("￥"+totalMoney.toFixed(2));
    }else {
      $(".totaldiv .despatch").show();
      var yf = despatchMoney;
      if(totalMoney >= despatchLimit){
        $(".totaldiv .despatchLimit").show();
        yf = 0;
      }else{
        mui.alert('', '满'+(despatchLimit/100.0).toFixed(2)+'元，即可包邮', function() {});
      }
      $(".totaldiv .productTotalMoney").text("￥"+(totalMoney + yf/100.0).toFixed(2));
      $("#mean2 .red").text("￥"+(totalMoney + yf/100.0).toFixed(2));
    }
  });
  
  $("#mean3").on("tap", function(){
    var deliveryType = $("#item1").hasClass("mui-active") ? 1 : 2;
    var param = {memberId: user.memberId, distributorId: distributor.distributorId, deliveryType: deliveryType};
    if(deliveryType == 1){
      var name = $("input[name=name]").val();
      var mobile = $("input[name=mobile]").val();
      if(!name){
        mui.toast("请填写提货人姓名");
        return false;
      }
      if(!mobile){
        mui.toast("请填写提货人手机号码");
        return false;
      }
      if (!/^1[34578]\d{9}$/.test(mobile)) {
        mui.toast("提货人手机号码错误");
        return false;
      }
      param['name'] = name;
      param['mobile'] = mobile;
    }else{
      param['deliveryId'] = $("#deliveryAddressId").val();
      if(! param.deliveryId){
        mui.toast("请选择收货地址");
        return false;
      }
    }
    if(details.length == 0){
      mui.toast("没有可以支付的商品，请重新添加商品并购买");
      return false;
    }
    param['details'] = details;
    ajax({
      contentType: 'application/json',
      url: '/api/order/saveMemberOrder',
      data: JSON.stringify(param),
      success: function(orderId){
        //putLocalData("pay_orderId", orderId);
        document.location.href='pay.html?oid='+orderId;
      }
    });
    return false;
  });
});

function initOrder(){
  ajax({
    url: '/api/shoppingCart/enterOrder',
    data: {'memberId' : user.memberId},
    success: function(data){
      $(".goodslist ul").html("");
      despatchMoney = data.despatchMoney;
      despatchLimit = data.despatchLimit;
      deliveryAddress = data.deliveryAddress;
      if(deliveryAddress != null){
        $("#deliveryAddressId").val(deliveryAddress.id);
        $(".deliveryname").text(deliveryAddress.name);
        $(".deliverymobile").text(deliveryAddress.mobile);
        $(".deliveryaddr").text(deliveryAddress.provinceName + deliveryAddress.cityName + deliveryAddress.areaName + deliveryAddress.address);
      }else{
        $("#item2 .mui-navigate-right").html('<p class="addDelivery">选择收获地址</p>');
      }
      $("#item2 .mui-navigate-right").on("tap", function(){
        putLocalData("to", "submit.html#item2");
        var deliveryAddressId = $("#deliveryAddressId").val();
        if(deliveryAddressId) putLocalData("deliveryAddressId", deliveryAddressId);
        document.location.href="addresslist.html";
      });
      var items = data.products;
      if(null != items && items.length > 0){
        for(var o in items){
          var item = items[o];
          originalPrice += item.originalPrice / 100.0 * item.num;
          totalMoney += item.price / 100.0 * item.num;
          num += item.num;
          $(".goodslist ul").append('<li>\
              <span><img src="'+IMAGE_PREFIX+item.mainPictureUrl+'" alt=""></span>\
              <p>'+item.title+'</p>\
              <p>'+item.specification+'</p>' +
              '<p class="red">'+(item.pickupTime != null ? item.pickupTime.formatDate('M月d日 h点') : '随时')+'可提货</p>' +
              '<p class="price">￥<b>'+((item.price / 100.0).toFixed(2))+'</b>\
                <del>$'+((item.originalPrice / 100.0).toFixed(2))+'</del>\
                <span class="fl">x'+item.num+'</span>\
              </p>\
            </li>');
          details.push({'productId': item.productId, 'count': item.num});
        }
        
      }
      $(".totaldiv .productNum").text(num);
      $(".totaldiv .productMoney").text("￥"+totalMoney.toFixed(2));
      $(".totaldiv .productOriginalPrice").text("￥"+originalPrice.toFixed(2));
      $(".totaldiv .despatchMoney").text("￥"+(despatchMoney/100.0).toFixed(2));
      $(".totaldiv .despatchLimit span").text("￥"+(despatchLimit/100.0).toFixed(0));
      $(".totaldiv .despatchLimit b.despatchMoney").text("-￥"+(despatchMoney/100.0).toFixed(2));
      $(".totaldiv .productTotalMoney").text("￥"+totalMoney.toFixed(2));
      $("#mean2 .red").text("￥"+totalMoney.toFixed(2));
    }
  });
}