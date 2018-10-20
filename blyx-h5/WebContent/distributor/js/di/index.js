var user = getLoginInfo();

$(function(){
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
              name : '您的订单数('+data.orderNumOfToday+')'
            }, {
              value : data.totalOrderNumOfToday - data.orderNumOfToday,
              name : '其他总订单数('+(data.totalOrderNumOfToday - data.orderNumOfToday)+')'
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
    mui.alert("<div style='text-align:left'>1.点击右上角菜单(即，三个圆点)，<br>2.选择发送给朋友完成分享</div>", " ");
    return false;
  });
  
  $("a.logout").on("tap", function(){
    logout();
    document.location.href="login.html";
    return false;
  });
});