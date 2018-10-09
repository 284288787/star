var pid = getParam("pid");
var user = getLoginInfo();
mui('.mui-scroll-wrapper').scroll({
  deceleration: 0.0005,
  indicators: true, //是否显示滚动条
});
$(function() {
  initDetailInfo();
  initCartNum();
  var py = getParam("py");
  $(".homePage").on("tap", function(){
    document.location.href='index.html?py='+py;
  });
  $(".userPage").on("tap", function(){
    if(islogin()){
      document.location.href='user.html';
    }else{
      document.location.href='login.html?redirect_url=user.html';
    }
  });
  $("#nav2").on("tap", function(){
    if(islogin()){
      var url = '/api/productSubscription/unsubscribe';
      if($("#nav2 .mui-tab-label").text().indexOf("已关注")==-1){
        url = '/api/productSubscription/subscribe';
      }
      ajax({
        async: false,
        url: url,
        data: {'productId': pid, 'openId': user.openId},
        success: function(data){
          if($("#nav2 .mui-tab-label").text().indexOf("已关注")==-1){
            $("#nav2 .mui-tab-label").text("已关注");
            mui.toast("关注成功");
          }else{
            $("#nav2 .mui-tab-label").text("关注");
            mui.toast("取消关注成功");
          }
        }
      });
    }else{
      mui.confirm('', '未登陆！是否去登陆?', ['否', '是'], function(e) {
        if (e.index == 1) {
          var temp = encodeURIComponent('detail.html'+document.location.search);
          document.location.href='login.html?redirect_url='+temp;
        }
      })
    }
    return false;
  });
  $("#nav3").on("tap", function(){
    var num = $(".mui-input-numbox").val();
    if(islogin()){
      ajax({
        async: false,
        url: '/api/shoppingCart/addShoppingCart',
        data: {'productId': pid, 'memberId': user.memberId, 'num': num},
        success: function(data){
          mui.toast("添加购物车成功");
          initCartNum();
        }
      });
    }else{
      mui.confirm('', '未登陆！是否去登陆?', ['否', '是'], function(e) {
        if (e.index == 1) {
          var temp = encodeURIComponent('detail.html'+document.location.search);
          document.location.href='login.html?redirect_url='+temp;
        }
      })
    }
    return false;
  });
  $("#nav4").on("tap", function(){
    var num = $(".mui-input-numbox").val();
    if(islogin()){
      ajax({
        async: false,
        url: '/api/shoppingCart/buyNowCheck',
        data: {'productId': pid, 'num': num, 'memberId': user.memberId},
        success: function(data){
          document.location.href='buynow.html?pid='+pid+'&num='+num;
        }
      });
    }else{
      mui.confirm('', '未登陆！是否去登陆?', ['否', '是'], function(e) {
        if (e.index == 1) {
          var temp = encodeURIComponent('detail.html'+document.location.search);
          document.location.href='login.html?redirect_url='+temp;
        }
      })
    }
    return false;
  });
});

function initCartNum(){
  if(islogin()){
    ajax({
      url: '/api/shoppingCart/queryShoppingCartCount',
      data: {'memberId': user.memberId},
      success: function(data){
        if(data>0){
          $('#nav1 .mui-icon-extra-cart').html('<span class="mui-badge">'+data+'</span>');
        }
      }
    });
    ajax({
      async: false,
      url: '/api/productSubscription/isSubscription',
      data: {'openId': user.openId, 'productIds': pid},
      success: function(data){
        for(var o in data){
          $("#nav2 .mui-tab-label").text("已关注");
        }
          
      }
    });
  }
}

function initDetailInfo(){
  ajax({
    url: '/api/product/getProductInfo',
    data: {'productId': pid},
    success: function(product){
      document.title = product.title + " - 贝拉优选";
      var pictures = product.pictures;
      var len = pictures.length;
      $(".firstimg img").attr("src", IMAGE_PREFIX + pictures[len - 1].url);
      $(".lastimg img").attr("src", IMAGE_PREFIX + pictures[0].url);
      for(var i in pictures){
        $(".lastimg").before('<div class="mui-slider-item">\
            <a href="#"> <img class="productImg" src="'+IMAGE_PREFIX+pictures[i].url+'">\
            </a>\
          </div>');
        $(".point").append('<div class="mui-indicator'+(i==0 ? ' mui-active' : '')+'"></div>');
      }
      var slider = mui("#slider");
      slider.slider({
        interval:2000
      });
      $(".realPrice span").text((product.price / 100.0).toFixed(2));
      $(".marketPrice").text((product.originalPrice / 100.0).toFixed(2));
      $(".title").text(product.title);
      $(".subTitle .l").text(product.specification);
      $(".rinkpeople span").text(product.subscribers);
      if(product.offShelfTime){
        var t = product.offShelfTime.gapSeconds(0);
        console.log(t)
        countdown(t);// 倒计时还有多少秒 单位秒 时间差500000秒
      }else{
        $(".offShelf1, .offShelf2").remove();
      }
      var flag = true;
      if(product.presellTime && product.presellTime.before(new Date())){
        $(".pickUpTime").append('<p class="red">预售时间：'+product.presellTime.formatDate('M月d日 h点')+'</p>');
        flag = false;
      }
      if(product.pickupTime){
        $(".pickUpTime").append('<p class="red">提货时间：'+product.pickupTime.formatDate('M月d日 h点')+'</p>');
        flag = false;
      }
      if(flag)$(".pickUpTime").append('<p class="red">&nbsp;</p>');
      var temp = '<div class="number">已售 <span class="red">'+product.soldNumber+'</span> 份 ';
      if(product.numberType==2){
        temp +='<i>/ 限量 <span>'+product.number+'</span> 份</i>';
        $(".mui-numbox").attr("data-numbox-max", product.number - product.soldNumber);
      }
      $(".pickUpTime").append(temp+'</div>');
      $(".information .supplier").text(product.supplier);
      $(".information .brand").text(product.brand);
      $(".information .specification").text(product.specification);
      $(".information .originPlace").text(product.originPlace);
      $(".productContentBox5 .desc").html(product.description);
    }
  });
}

var inter;
function countdown(sec) {
  countDown2(sec);
  inter = setInterval(function(){
    countDown2(sec --);
  }, 1000);
}
function countDown2(sec) {
  var hour = 0;
  var minute = 0;
  var second = 0;
  var day = 0;
  if (sec >= 60) {
    minute = Math.floor(sec / 60);
    second = Math.floor(sec % 60);
    if (minute >= 60) {
      hour = Math.floor(minute / 60);
      minute = minute % 60;
      if(hour>=24){
        day = Math.floor(hour / 24)
        hour = hour % 24
      }
    } else {
      hour = 0;
    }
  } else {
    second = sec;
    hour = 0;
    minute = 0;
    day = 0;
  }
  var h = hour * 1 < 10 ? "0" + hour : hour;
  var m = minute * 1 < 10 ? "0" + minute : minute;
  var s = second * 1 < 10 ? "0" + second : second;
  var countdownStr = day + "天" + h + "小时" + m + "分" + s + "秒";
  $("#product-sec-countdown").text(countdownStr);
  if (sec <= 0) {
    clearInterval(inter);
    $(".offShelf1").hide();
    $(".offShelf2").show();
  }
}