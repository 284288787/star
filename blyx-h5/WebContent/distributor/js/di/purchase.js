var user = getLoginInfo();

function giveValue(provinceName, cityName, areaName) {
  $("#address").text(provinceName+cityName+areaName);
  $("#provinceName").val(provinceName);
  $("#cityName").val(cityName);
  $("#areaName").val(areaName);
  mui('#addSelect').popover('hide');
}

var despatchMoney = 0;
var despatchLimit = 0;
$(function(){
  ajax({
    url: '/api/order/getProperties',
    success: function(properties){
      despatchMoney = properties.despatchMoney;
      despatchLimit = properties.despatchLimit;
    }
  });
  mui('.mui-scroll-wrapper').scroll();
  $("#address").on('tap', function() {
    mui('#addSelect').popover('show');
    addS();
  })
  $("#item1 .blank").text("提货地址：" + user.provinceName + user.cityName + user.areaName + user.address);
  $("#item1 .red").text("自提点：" + user.shopName);
  $("#segmentedControl .mui-control-item").on("tap", function(){
    cal($(this).attr("href"));
  });
  $("#search").on("input", function(){
    pulldownRefresh();
  });
  $("#search").keyup(function(e){
    if(e.keyCode == 13){
      pulldownRefresh();
    }
  });
  $("#mean3").on("tap", function(){
    var details = new Array();
    $(".goodslist li").each(function(){
      var thisObj = $(this);
      var p = thisObj.attr("data-pid");
      var n = $(".mui-input-numbox", thisObj).val() * 1;
      details.push({'productId': p, 'count': n});
    });
    if(details.length == 0){
      mui.toast("请选择要下单的商品");
      return false;
    }
    var prices = cal();
    var deliveryType = $("#item1").hasClass("mui-active") ? 1 : 2;
    var param = {memberId: -1, distributorId: user.distributorId, deliveryType: deliveryType};
    if(deliveryType == 1){
      var name = $("#item1 input[name=name]").val();
      var mobile = $("#item1 input[name=mobile]").val();
      if(!name){
        mui.toast("请填写提货人姓名");
        return false;
      }
      if(!mobile){
        mui.toast("请填写提货人手机号码");
        return false;
      }
      if (!/^1[345789]\d{9}$/.test(mobile)) {
        mui.toast("提货人手机号码错误");
        return false;
      }
      param['name'] = name;
      param['mobile'] = mobile;
    }else{
      var name = $("#item2 input[name=name]").val();
      var mobile = $("#item2 input[name=mobile]").val();
      var deliveryAddress = $("#item2 input[name=deliveryAddress]").val();
      var provinceName = $("#provinceName").val();
      var cityName = $("#cityName").val();
      var areaName = $("#areaName").val();
      if(!name){
        mui.toast("请填写收货人姓名");
        return false;
      }
      if(!mobile){
        mui.toast("请填写收货人手机号码");
        return false;
      }
      if (!/^1[345789]\d{9}$/.test(mobile)) {
        mui.toast("收货人手机号码错误");
        return false;
      }
      if(! areaName){
        mui.toast("地区必须选择到区县");
        return false;
      }
      if(! deliveryAddress){
        mui.toast("收货详细地址必填");
        return false;
      }
      param['name'] = name;
      param['mobile'] = mobile;
      param['provinceName'] = provinceName;
      param['cityName'] = cityName;
      param['areaName'] = areaName;
      param['deliveryAddress'] = deliveryAddress;
    }
    param['details'] = details;
    param['remark'] = $("input[name=remark]").val();
    ajax({
      contentType: 'application/json',
      url: '/api/order/saveDistributorOrder',
      data: JSON.stringify(param),
      success: function(orderId){
        cookieStorage.setItem('login_distributor_user', JSON.stringify(user));
        document.location.href='http://yx.hnkbmd.com/pay.html?f=d&oid='+orderId;
      }
    });
    return false;
  });
  
  $(".enterBtn").on("tap", function(){
    var len = $(".itemlist :checked").length;
    if(len == 0){
      mui.toast("未选择商品")
    }else{
      mui('#goods').popover('hide');
      $(".itemlist :checked").each(function(){
        var thisObj = $(this);
        var productId = thisObj.attr("data-pid");
        var title = thisObj.attr("data-title");
        var price = thisObj.attr("data-price");
        var picture = thisObj.attr("data-picture");
        var len = $(".goodslist li[data-pid="+productId+"]").length;
        if(len > 0){
          var old = $(".goodslist li[data-pid="+productId+"] .mui-input-numbox").val();
          $(".goodslist li[data-pid="+productId+"] .mui-input-numbox").val(old * 1 + 1);
        }else{
          var li = '<li data-pid="'+productId+'" data-price="'+price+'"><span><img src="'+IMAGE_PREFIX+picture+'" alt=""></span>\
            <p>'+title+'</p>\
            <p>&nbsp;</p>\
            <p class="red">&nbsp;</p>\
            <p class="price">￥<b>'+price.toMoney()+'</b>\
              <del></del>\
            </p>\
            <div class="mui-numbox fl stepnum" data-numbox-min="1">\
              <button class="mui-btn mui-btn-numbox-minus" type="button">-</button>\
              <input id="test" class="mui-input-numbox" type="number" value="1" />\
              <button class="mui-btn mui-btn-numbox-plus" type="button">+</button>\
            </div> <span class="mui-icon mui-icon-trash del"></span></li>';
          $(".goodslist").append(li);
        }
        $(".mui-input-numbox").off().on("change", function(){
          cal();
        });
        $(".del").off().on("tap", function(){
          $(this).parents("li").remove();
          cal();
        });
        cal();
        mui('.mui-numbox').numbox();
        thisObj.removeAttr("checked");
      });
    }
  });
  mui.init({
    pullRefresh : {
      container : '#pullrefresh',
      down : {
        style : 'circle',
        callback: pulldownRefresh
      },
      up : {
        auto : true,
        contentrefresh : '正在加载...',
        callback : pullupRefresh
      }
    }
  });
  
});

function cal(href){
  var goodsNum = 0;
  var prices = 0;
  var youfei = despatchMoney;
  $(".goodslist li").each(function(){
    var thisObj = $(this);
    var p = $(this).attr("data-price") * 1;
    var n = $(".mui-input-numbox", thisObj).val() * 1;
    goodsNum += n;
    prices += p * n;
  });
  if(! href) href = $("#segmentedControl .mui-active").attr("href");
  if(href=="#item1"){
    $(".totaldiv .despatch").hide();
    $(".despatchLimit").hide();
    youfei = 0;
  }else{
    $(".totaldiv .despatch").show();
    $(".totaldiv .youfei").text("￥" + youfei.toMoney());
    youfei = despatchMoney;
    if(prices >= despatchLimit){
      youfei = 0;
      $(".despatchLimit span").text(despatchLimit.toMoney());
      $(".despatchLimit .youfei").text("-￥" + despatchMoney.toMoney());
      $(".despatchLimit").show();
    }else{
      $(".despatchLimit").hide();
    }
  }
  $(".totaldiv .number").text(goodsNum);
  $(".totaldiv .money").text("￥" + prices.toMoney());
  $(".totaldiv .big b").text("￥" + (prices + youfei).toMoney());
  $("#nav .red").text("￥" + (prices + youfei).toMoney());
}

var pageNum = 1, pageSize = 10;
function pulldownRefresh() {
  setTimeout(function() {
    pageNum = 1;
    var title = $("#search").val();
    $(".itemlist").html("");
    loadData(pageNum, pageSize, title);
    mui('#pullrefresh').pullRefresh().endPulldownToRefresh(true);
    pageNum++;
  }, 10);
}
function pullupRefresh() {
  setTimeout(function() {
    var title = $("#search").val();
    loadData(pageNum, pageSize, title);
    pageNum++;
  }, 10);
}
function loadData(pageNum, pageSize, title){
  ajax({
    url: '/api/product/queryProduct',
    data: {'title': title, 'pager.pageNum': pageNum, 'pager.pageSize': pageSize, 'state': 1},
    success: function(items){
      if(null != items && items.length > 0){
        mui('#pullrefresh').pullRefresh().endPullupToRefresh(false);
        var productIds = "";
        for(var o in items){
          var item = items[o];
          productIds += "," + item.productId;
          var li = '<li class="mui-table-view-cell mui-media">\
            <div class="mui-media-body">\
              <div class="mui-input-row mui-checkbox mui-left">\
                <label>\
                  <img class="mui-media-object mui-pull-left" class="chimg" src="'+IMAGE_PREFIX+item.mainPictureUrl+'">\
                  <span class="block">'+item.title+'</span>\
                  <span class="block red">￥'+item.price.toMoney()+'<del>￥'+item.originalPrice.toMoney()+'</del></span>\
                </label>\
                <input name="checkbox" type="checkbox" class="chox" data-pid="'+item.productId+'" data-title="'+item.title+'" data-price="'+item.price+'" data-picture="'+item.mainPictureUrl+'" >\
              </div>\
            </div>\
          </li>';
          $(".itemlist").append(li);
        }
      }else{
        mui('#pullrefresh').pullRefresh().endPullupToRefresh(true);
      }
    }
  });
}