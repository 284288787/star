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
      contentnomore:'没有更多商品了',
      callback : pullupRefresh
    }
  }
});
var browsepid = getLocalData("browsepid");
var pageNum = 1, pageSize = 10;
var cateId = null;
var py = getParam("py");
var user = getLoginInfo();
var userCartNum;
if(py) putLocalData('py', py);
$(function(){
  ajax({
    url: '/weixin/ticket/jssdk',
    data: {
      url: document.location.href
    },
    success: function(data) {
      wx.config({
        debug: false,
        appId: 'wx8a05f2d3eb34111f',
        timestamp: data.timestamp,
        nonceStr: data.nonceStr,
        signature: data.signature,
        jsApiList: [
          'onMenuShareAppMessage',
          'onMenuShareTimeline',
          'showOptionMenu',
          'updateAppMessageShareData',
          'updateTimelineShareData',
          'onMenuShareWeibo'
        ]
      });
    }
  });
  initCategory();
  initCartNum();
  $("#search").on("input", function(){
    pulldownRefresh();
  });
  $("#search").keyup(function(e){
    if(e.keyCode == 13){
      pulldownRefresh();
    }
  });
  $(".mui-icon-clear").on("tap click mousedown", function(){
    pulldownRefresh();
  });
  $("#a2").on("tap", function(){
    if(islogin()){
      return true;
    }else{
      var temp = encodeURIComponent('mycar.html');
      document.location.href='login.html?redirect_url='+temp;
      return false;
    }
  });
  $("#a3").on("tap", function(){
    if(islogin()){
      return true;
    }else{
      var temp = encodeURIComponent('user.html');
      document.location.href='login.html?redirect_url='+temp;
      return false;
    }
  });
  $(".xfqdiv .zx").on("tap", function(){
    $(".xfqdiv .zx").css("visibility", "hidden");
    $(".xf").animate({width:'100%', opacity: 1});
    $(".xf .close").on("tap", function(){
      $(".xfqdiv .zx").css("visibility", "visible");
      $(".xf").animate({width:'0%', opacity: 0}, function(){
      });
      $(".xf .close").off();
    });
  });
  $(".xfqdiv .zd").on("tap", function(){
    mui('#pullrefresh').scroll().scrollTo(0,0,1000);
    $(".xfqdiv .zd").css("visibility", "hidden");
  });
  $(".categorydiv .cate").on("tap", function(){
    $(".categorydiv .cate").hide();
    $(".category").fadeIn();
    $(".category .close").on("tap", function(){
      $(".categorydiv .cate").show();  
      $(".category").fadeOut();
      $(".category .close").off();
    });
  });
  $("#pullrefresh").on("scrollend", function(e){
    var scroll = mui('#pullrefresh').scroll();
    if(scroll.y < -100){
      //$(".category").fadeIn();
      $(".categorydiv .cate").fadeIn();
      $(".xfqdiv .zd").css("visibility", "visible");
    }else{
      //$(".category").fadeOut();
      $(".categorydiv .cate").fadeOut();
      $(".xfqdiv .zd").css("visibility", "hidden");
    }
  });
  $("#pullrefresh").on("scrollstart", function(e){
    $(".categorydiv .cate").show();  
    $(".category").fadeOut();
    $(".category .close").off();
  });
});

function initCartNum(){
  if(islogin()){
    ajax({
      url: '/api/shoppingCart/queryShoppingCartCount',
      data: {'memberId': user.memberId},
      success: function(data){
        if(data>0){
          $('#a2 .mui-icon-extra-cart').html('<span class="mui-badge">'+data+'</span>');
        }
      }
    });
  }
}

function initCategory(){
  ajax({
    url: '/api/category/queryCategory',
    success: function(data){
      var idx = 2;
      for(var i in data){
        $("#sliderSegmentedControl .mui-scroll").append('<a class="mui-control-item" href="#item'+(idx++)+'mobile">'+data[i].cateName+'</a>');
        $("#slider .mui-slider-group").append('<div id="item'+(idx++)+'mobile" class="mui-slider-item mui-control-content">\
          <div id="scroll1" class="mui-scroll-wrapper">\
            <div class="mui-scroll">\
              <div class="shop clearfix">\
                <img src="images/qq.png" alt="" class="userimg">\
                <p>\
                  <label>ID：</label><span class="shopcode"></span>\
                </p>\
                <p>\
                  <label> <span class="mui-icon mui-icon-home"></span>：\
                  </label><span class="shopname"></span>\
                </p>\
                <p class="t">\
                  <label> <span class="mui-icon mui-icon-location"></span>：\
                  </label><span class="shopaddress"></span>\
                </p>\
                <span class="fr">购买指数<br><b class="soldNum">0</b><br>粉丝数<br><b class="fansNum">0</b></span>\
              </div>\
              <ul class="mui-table-view">\
              <li class="mui-table-view-cell">\
                    第1个选项卡子项-1\
                  </li>\
              </ul>\
            </div>\
          </div>\
        </div>');
      }
      initShopInfo();
      $(".category .cates span").on("tap", function(){
        cateId = $(this).attr("data-cateid");
        cateName = $(this).attr("data-catename");
        if(! cateId) cateId = null;
        if(cateName){
          $(".categorydiv .cate").text(cateName);
        }else{
          $(".categorydiv .cate").text("全部分类");
        }
        $(".categorydiv .cate").show();  
        $(".category").fadeOut();
        $(".category .close").off();
        mui('#pullrefresh').scroll().scrollTo(0,0,1);
        mui('#pullrefresh').pullRefresh().enablePullupToRefresh();
        mui('#pullrefresh').pullRefresh().endPullupToRefresh(false);
        pulldownRefresh();
      });
    }
  });
}

function initShopInfo(){
  if(!py) py = getLocalData('py')
  if(!py) py = 'wygc';
  ajax({
    url: '/api/distributionRegion/getDistributionRegion',
    data: {'py': py},
    success: function(data){
      var distributor = data.distributor;
      setShopInfo(distributor);
      document.title = distributor.shopName + " - 五杂优选";
      $('.userimg').attr("src", distributor.head ? ((distributor.head.indexOf("http")==0 ? '' : IMAGE_PREFIX) + distributor.head) : 'images/head.png');
      $('.shopcode').text(distributor.shopCode);
      $('.shopname').text(distributor.shopName);
      var a = ""; 
      if(distributor.provinceName) a += distributor.provinceName;
      if(distributor.cityName) a += distributor.cityName;
      if(distributor.areaName) a += distributor.areaName;
      if(distributor.townName) a += distributor.townName;
      $('.shopaddress').text(a + distributor.address);
      $('.soldNum').text(distributor.soldNum);
      $('.fansNum').text(distributor.fansNum);
      wx.ready(function(){
        wx.updateAppMessageShareData({
          title: "五杂优选（今日爆品），" + distributor.shopName,
          desc: '亲，所有单品高性价比，正品保证，售后无忧！',
          link: document.location.href,
          imgUrl: 'http://yx.hnkbmd.com/photo/shop.jpg',
          success: function () {
          }
        });
        wx.updateTimelineShareData({
          title: "五杂优选（今日爆品），" + distributor.shopName,
          link: document.location.href,
          imgUrl: 'http://yx.hnkbmd.com/photo/shop.jpg',
          success: function () {
          }
        });
        wx.onMenuShareWeibo({
          title: "五杂优选（今日爆品），" + distributor.shopName,
          desc: '亲，所有单品高性价比，正品保证，售后无忧！',
          link: document.location.href,
          imgUrl: 'http://yx.hnkbmd.com/photo/shop.jpg',
          success: function () {
          },
          cancel: function () {
          }
        });
      });
    }
  });
}
function pulldownRefresh() {
  setTimeout(function() {
    pageNum = 1;
    var title = $("#search").val();
    $(".itemlist").html("");
    loadData(pageNum, pageSize, title, function(){
      pageNum++;
      mui('#pullrefresh').pullRefresh().refresh(true);
      mui('#pullrefresh').pullRefresh().endPulldownToRefresh(true);
    });
  }, 10);
}
var times=0;
var cb = function(cont){
  console.log(browsepid + " " + (++times))
  pageNum++;
  if(cont){
    var title = $("#search").val();
    loadData(pageNum, pageSize, title, cb);
  }else if(browsepid){
    mui('.mui-scroll-wrapper').scroll().reLayout();
    var current_top = mui('.mui-scroll-wrapper').scroll().y;
    var y = $("#"+browsepid).offset().top-160;
    y = parseInt(current_top - y);
    if (y > 0) y = -y;
    mui('.mui-scroll-wrapper').scroll().scrollTo(0, y , 100);
    delLocalData("browsepid");
  }
};
function pullupRefresh() {
  setTimeout(function() {
    var title = $("#search").val();
    loadData(pageNum, pageSize, title, cb);
  }, 10);
}
function loadData(pageNum, pageSize, title, callback){
  ajax({
    url: '/api/product/queryProduct',
    data: {'title': title, 'pager.pageNum': pageNum, 'pager.pageSize': pageSize, 'cateId': cateId},
    success: function(items){
      if(null != items && items.length > 0){
        var cont = browsepid ? true : false;
        mui('#pullrefresh').pullRefresh().endPullupToRefresh(false);
        var productIds = "";
        for(var o in items){
          var item = items[o];
          if(cont){
            if(item.productId == browsepid) cont = false;
          }
          productIds += "," + item.productId;
          var li = '<li id="'+item.productId+'" class="item" data-pid="'+item.productId+'">\
            <p class="shoptitle">本商品由'+item.supplier+'专供</p>\
            <p class="goodimg'+(item.state >= 3 ? ' sellover': '')+'"><a class="pdetail" href="detail.html?py='+py+'&pid='+item.productId+'" data-pid="'+item.productId+'"><b  class="pos1">'+item.subtitle+'</b>';
          if(item.tag){
            li += '<b  class="pos2">'+item.tag+'</b>';
          }
          li += '<img src="'+IMAGE_PREFIX+item.mainPictureUrl+'" alt="">';
          if(item.state >= 3){
            li += '<span>已售罄</span>';
          }
          li +='</p>\
            <p class="goodtitle">'+item.title+'</p>\
            <p class="gooddes">'+item.specification+'</p></a>\
            <p class="goodtime clearfix">'
          if(item.presellTime && item.presellTime.before(new Date())){
            li +='预售时间：' + item.presellTime.formatDate('M月d日 h点');
          }
          li +='<span>已售 <b >'+item.soldNumber+'</b> 份';
          if(item.numberType == 2){
            li +='/ 限量'+item.number+'份';
          }
          li += '</span>\
            </p>';
          if(item.offShelfTime){
            li += '<p class="gooddate">结束时间：'+item.offShelfTime.formatDate('M月d日 h点')+'</p>';
          }
          li += '<p class="redp clearfix">￥<big>'+(item.price/100.0).toFixed(2)+'</big> <del>￥'+(item.originalPrice/100.0).toFixed(2)+'</del></p>';
          li += '<b class="like has" data-pid="'+item.productId+'"> <i >关注</i></b>';
          li += '<a class="addCar'+(item.state>=3 ? ' disable': '')+'" data-pid="'+item.productId+'">加入购物车';
          if(userCartNum && userCartNum[item.productId] && userCartNum[item.productId] > 0){
            li += '<span class="mui-icon"><span class="mui-badge" style="background:#2fa781">'+userCartNum[item.productId]+'</span></span>';
          }
          li += '</a></li>';
          $(".itemlist").append(li);
        }
        $(".pdetail").off().on("tap", function(){
          var pid = $(this).attr("data-pid");
          if(pid) putLocalData("browsepid", pid);
        });
        if(! userCartNum && user){
          userCartNum = {};
          ajax({
            url: '/api/shoppingCart/queryShoppingCart',
            data: {'memberId': user.memberId},
            success: function(items){
              if(null != items && items.length > 0){
                for(var o in items){
                  var item = items[o];
                  userCartNum[item.productId] = item.num;
                }
                $(".itemlist a.addCar:not(.disable)").each(function(){
                  var thisObj = $(this);
                  var pid=thisObj.attr("data-pid");
                  if(userCartNum[pid] && userCartNum[pid] > 0){
                    if($(".mui-badge", thisObj).length == 1){
                      $(".mui-badge", thisObj).text(userCartNum[pid]);
                    }else{
                      thisObj.html('加入购物车<span class="mui-icon"><span class="mui-badge" style="background:#2fa781">'+userCartNum[pid]+'</span></span>');
                    }
                  }
                });
              }
            }
          });
        }
        syncOtherInfo(productIds);
        
//        $(".itemlist a.addCar:not(.disable)").off().on("tap", function(){
        mui('body').on('tap','.itemlist a.addCar:not(.disable)',function(){
          var thisObj=$(this);
          if(islogin()){
            var pid=thisObj.attr("data-pid");
            ajax({
              async: false,
              url: '/api/shoppingCart/addShoppingCart',
              data: {'productId': pid, 'memberId': user.memberId, 'num': 1},
              success: function(data){
                mui.toast("添加购物车成功");
                if($(".mui-badge", thisObj).length == 1){
                  $(".mui-badge", thisObj).text($.trim($(".mui-badge", thisObj).text()) * 1 + 1);
                }else{
                  thisObj.html('加入购物车<span class="mui-icon"><span class="mui-badge" style="background:#2fa781">1</span></span>');
                }
                initCartNum();
              }
            });
          }else{
            mui.confirm('', '未登陆！是否去登陆?', ['否', '是'], function(e) {
              if (e.index == 1) {
                var temp = encodeURIComponent('index.html');
                document.location.href='login.html?redirect_url='+temp;
              }
            })
          }
          return false;
        });
        $(".itemlist b.like").off().on("tap", function(){
          var thisObj=$(this);
          if(islogin()){
            var pid=thisObj.attr("data-pid");
            var url = '/api/productSubscription/unsubscribe';
            if($("i", thisObj).text().indexOf("已关注")==-1){
              url = '/api/productSubscription/subscribe';
            }
            ajax({
              async: false,
              url: url,
              data: {'productId': pid, 'openId': user.openId},
              success: function(data){
                if($("i", thisObj).text().indexOf("已关注")==-1){
                  $("i", thisObj).text("已关注");
                  mui.toast("关注成功");
                }else{
                  $("i", thisObj).text("关注");
                  mui.toast("取消关注成功");
                }
              }
            });
          }else{
            mui.confirm('', '未登陆！是否去登陆?', ['否', '是'], function(e) {
              if (e.index == 1) {
                var temp = encodeURIComponent('index.html');
                document.location.href='login.html?redirect_url='+temp;
              }
            })
          }
          return false;
        });
        callback(cont);
      }else{
        mui('#pullrefresh').pullRefresh().endPullupToRefresh(true);
      }
    }
  });
}

//加入购物车<span class="mui-icon"><span class="mui-badge" style="background:#2fa781">2</span></span>
function syncOtherInfo(productIds){
  if(islogin()){
    ajax({
      async: false,
      url: '/api/productSubscription/isSubscription',
      data: {'openId': user.openId, 'productIds': productIds},
      success: function(data){
        for(var o in data){
          $(".itemlist li[data-pid="+o+"] b.like i").text("已关注");
        }
      }
    });
  }
}