var user = getLoginInfo();
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
          'onMenuShareTimeline',
          'onMenuShareAppMessage',
          'onMenuShareQQ',
          'updateAppMessageShareData'
        ]
      });
    }
  });
  wx.ready(function(){
    wx.updateAppMessageShareData({
      title: "五杂优选（今日爆品），" + user.shopName,
      desc: '亲，所有单品高性价比，正品保证，售后无忧！',
      link: 'http://yx.hnkbmd.com/index.html?py='+user.py,
      imgUrl: 'http://yx.hnkbmd.com/photo/shop.jpg',
    }, function(res){
      alert(JSON.stringify(res))
    });
    
  });
  mui('.mui-scroll-wrapper').scroll({
    deceleration: 0.0005
  });
  $(".fright").html("("+user.shopCode+")" + user.shopName);
  $(".fleft img").attr("src", user.head);
  ajax({
    url: '/api/order/shopIndex',
    data: {'distributorId': user.distributorId},
    success: function(data){
      $(".orderNumRate").text((data.orderNumRateOfToday * 100).toFixed(2) + "%");
      $(".ranking").text(data.rankingOfToday == 0 ? "未上榜" : "第" + data.rankingOfToday + "名");
      $(".todayMoneyOfToday").text((data.totalMoneyOfToday / 100.0).toFixed(2));
      $(".todayMoney").text((data.totalMoney.totalMoney / 100.0).toFixed(2));
      
      var getOption = function(chartType) {
        var chartOption = {
          calculable : false,
          series : [ {
            name : '订单数量',
            type : 'pie',
            radius : '60%',
            center : [ '50%', '50%' ],
            data : [ {
              value : data.orderNumOfToday,
              name : '我的订单数('+data.orderNumOfToday+')'
            }, {
              value : data.totalOrderNumOfToday - data.orderNumOfToday,
              name : '其他商家订单数总和('+(data.totalOrderNumOfToday - data.orderNumOfToday)+')'
            } ]
          } ]
        }
        return chartOption;
      };
      var byId = function(id) {
        return document.getElementById(id);
      };

      var pieChart = echarts.init(byId('pieChart'));
      pieChart.setOption(getOption('pie'));
    }
  });
  
  $("a.share").on("tap", function(){
    mui.alert("<div style='text-align:left'>1.点击右上角菜单(即，三个圆点)，<br>2.选择'发送给朋友'，完成分享</div>", " ");
    return false;
  });
  
  $("a.logout").on("tap", function(){
    logout();
    document.location.href="login.html";
    return false;
  });
});