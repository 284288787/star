mui.init();
var browsepid = getLocalData("browsepid");
var browsesnum = getLocalData("browsesnum");
var cateId = null;
var pageSize=10;
var py = getParam("py");
var user = getLoginInfo();
var userCartNum;
var selfs = new Array();
var pullToRefreshs = new Array();
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
  initCartNum();
  $("#search").on("input", function(){
    pullToRefreshs[mui("#slider").slider().getSlideNumber()].pullDownLoading();
  });
  $("#search").keyup(function(e){
    if(e.keyCode == 13){
      pullToRefreshs[mui("#slider").slider().getSlideNumber()].pullDownLoading();
    }
  });
  $(".mui-icon-clear").on("tap click mousedown", function(){
    pullToRefreshs[mui("#slider").slider().getSlideNumber()].pullDownLoading();
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

(function($m) {
  $m.ready(function() {
    $m('.mui-scroll-wrapper').scroll({
      indicators: true, //是否显示滚动条
    });
    ajax({
      url: '/api/category/queryCategory',
      success: function(data){
        var idx = 1;
        $("#sliderSegmentedControl .mui-scroll .mui-control-item:first").text(data[0].cateName);
        $("#item1mobile ul").attr({'data-cateid':data[0].cateId, 'data-catemsg':data[0].cateName});
        for(var i = 1; i < data.length; i++) {
          idx++;
          $("#sliderSegmentedControl .mui-scroll").append('<a class="mui-control-item '+(idx==1?'mui-active':'')+'" style="padding-left: 10px !important;padding-right: 10px !important;" href="#item'+(idx)+'mobile">'+data[i].cateName+'</a>');
          $("#slider .mui-slider-group").append('<div id="item'+(idx)+'mobile" class="mui-slider-item mui-control-content">\
            <div id="scroll'+idx+'" class="mui-scroll-wrapper">\
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
                <ul class="mui-table-view" data-cateid="'+data[i].cateId+'" data-catemsg="'+data[i].cateName+'类商品">\
                </ul>\
              </div>\
            </div>\
          </div>');
        }
        var slider = mui("#slider").slider({interval:0});
        slider.stopped = false;
        initShopInfo();
        $m.each(document.querySelectorAll('.mui-slider-group .mui-scroll'), function(index, pullRefreshEl) {
          var pageNum = 1;
          pullToRefreshs[index] = $m(pullRefreshEl).pullToRefresh({
            down: {
              callback: function() {
                var self = this;
                setTimeout(function() {
                  var ul = self.element.querySelector('.mui-table-view');
                  ul.innerHTML = "";
                  var title = $("#search").val();
                  pageNum = 1;
                  loadData(index, self, pageNum, pageSize, title, function(){
                    //mui('#pullrefresh').pullRefresh().refresh(true);
                    //mui('#pullrefresh').pullRefresh().endPulldownToRefresh(true);
                  });
                  pageNum++;
                  self.refresh(true);
                  self.endPullDownToRefresh();
                }, 200);
              }
            },
            up: {
              auto: true, //默认执行一次上拉加载
              //contentinit: '上拉可以加载更多信息哦',
              //contentdown: '上拉加载结束啦',
              //contentrefresh: '正在加载信息请稍等',
              contentnomore: '已没有更多商品',
              callback: function() {
                var self = this;
                selfs.push(self);
                setTimeout(function() {
                  var title = $("#search").val();
                  loadData(index, self, pageNum, pageSize, title, function(index, self, productIds, fn){
                    pageNum++;
                    if(browsesnum) {
                      mui("#slider").slider().gotoItem(browsesnum);
                    }
                    if(browsesnum == index && (productIds+",").indexOf(","+browsepid+",") != -1){
                      self = selfs[browsesnum];
                      mui(self.element.parentNode).scroll().reLayout();
                      var current_top = mui(self.element.parentNode).scroll().y;
                      var y = $("#"+browsepid).offset().top-160;
                      y = parseInt(current_top - y);
                      if (y > 0) y = -y;
                      mui(self.element.parentNode).scroll().scrollTo(0, y , 100);
                      delLocalData("browsepid");
                      delLocalData("browsesnum");
                      setTimeout(function(){
                        mui("#slider").slider().gotoItem(browsesnum);
                      }, 1000)
                    }else if(browsesnum == index){
                      var title = $("#search").val();
                      loadData(index, self, pageNum, pageSize, title, fn);
                    }
                  });
                }, 1);
              }
            }
          });
        });
      }
    });
  });
})(mui);

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

function loadData(index, self, pageNum, pageSize, title, callback){
  $(self.element.parentNode).on("scrollend", function(e){
    var scroll = mui(self.element.parentNode).scroll();
    if(scroll.y < -100){
      $(".xfqdiv .zd").css("visibility", "visible");
      $(".xfqdiv .zd").off().on("tap", function(){
        mui(self.element.parentNode).scroll().scrollTo(0,0,1000);
        $(".xfqdiv .zd").css("visibility", "hidden");
      });
    }else{
      $(".xfqdiv .zd").css("visibility", "hidden");
    }
  });
  var ul = self.element.querySelector('.mui-table-view');
  var cateId = ul.dataset.cateid;
  var catemsg = ul.dataset.catemsg;
  ajax({
    url: '/api/product/queryProduct',
    data: {'title': title, 'pager.pageNum': pageNum, 'pager.pageSize': pageSize, 'cateId': cateId},
    success: function(items){
      if(null != items && items.length > 0){
        self.endPullUpToRefresh(false);
        var productIds = "";
        for(var o in items){
          var item = items[o];
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
          li +='<span>已售 <b >'+(item.soldNumber*10)+'</b> 份';
          if(item.numberType == 2){
            li +='/ 限量'+(item.number*10)+'份';
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
          ul.appendChild($(li)[0]);
        }
        $(".pdetail").off().on("tap", function(){
          var pid = $(this).attr("data-pid");
          if(pid) {
            putLocalData("browsesnum", mui("#slider").slider().getSlideNumber());
            putLocalData("browsepid", pid);
          }
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
                $(".mui-slider-group a.addCar:not(.disable)").each(function(){
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
        
        $(".mui-slider-group a.addCar:not(.disable)").off().on("tap", function(){
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
        $(".mui-slider-group b.like").off().on("tap", function(){
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
        callback(index, self, productIds, callback);
      }else{
        if(pageNum == 1){
          $(".mui-pull-bottom-tips", $(self.element).parent()).hide();
          ul.appendChild($('<li>\
              <div class="nocontent">\
              <span class="mui-icon mui-icon-extra mui-icon-extra-notice"></span>\
              <p>暂无'+catemsg+'，敬请期待！</p>\
              </div>\
          </li>')[0]);
        }
        self.endPullUpToRefresh(true);
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