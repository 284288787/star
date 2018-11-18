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
var pageNum = 1, pageSize = 10;
var py = getParam("py");
var user = getLoginInfo();
var userCartNum;
if(py) putLocalData('py', py);
$(function(){
  initShopInfo();
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
  $(".xfq").on("tap", function(){
    $(".xfq").hide();
    $(".xf").animate({width:'100%', opacity: 1});
    $(".xf .close").on("tap", function(){
      $(".xfq").show();  
      $(".xf").animate({width:'0%', opacity: 0}, function(){
      });
      $(".xf .close").off();
    });
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
      $('.userimg').attr("src", distributor.head);
      $('.shopcode').text(distributor.shopCode);
      $('.shopname').text(distributor.shopName);
      $('.shopaddress').text(distributor.address);
      $('.soldNum').text(distributor.soldNum);
      $('.fansNum').text(distributor.fansNum);
    }
  });
}
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
    data: {'title': title, 'pager.pageNum': pageNum, 'pager.pageSize': pageSize},
    success: function(items){
      if(null != items && items.length > 0){
        mui('#pullrefresh').pullRefresh().endPullupToRefresh(false);
        var productIds = "";
        for(var o in items){
          var item = items[o];
          productIds += "," + item.productId;
          var li = '<li class="item" data-pid="'+item.productId+'">\
            <p class="shoptitle">本商品由'+item.supplier+'专供</p>\
            <p class="goodimg'+(item.state >= 3 ? ' sellover': '')+'"><a href="detail.html?py='+py+'&pid='+item.productId+'"><b  class="pos1">'+item.subtitle+'</b>';
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
            li += '<p class="gooddate">下架时间：'+item.offShelfTime.formatDate('M月d日 h点')+'</p>';
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