var user = getLoginInfo();
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
var pageNum = 1, pageSize = 15;
function pulldownRefresh() {
  setTimeout(function() {
    pageNum = 1;
    $(".mui-table-view").html("");
    var keyword = $("input.keyword").val();
    loadData(pageNum, pageSize, keyword);
    mui('#pullrefresh').pullRefresh().endPulldownToRefresh(true);
    pageNum++;
  }, 10);
}
function pullupRefresh() {
  setTimeout(function() {
    var keyword = $("input.keyword").val();
    loadData(pageNum, pageSize, keyword);
    pageNum++;
  }, 10);
}
function loadData(pageNum, pageSize, keyword){
  ajax({
    url: '/api/order/seeUser',
    data: {'distributorId': user.distributorId, 'keyword': keyword, 'pageNum': pageNum, 'pageSize': pageSize},
    success: function(items){
      if(null != items && items.length > 0){
        mui('#pullrefresh').pullRefresh().endPullupToRefresh(false);
        for(var o in items){
          var item = items[o];
          var li = '<li class="mui-table-view-cell">\
                <div class="itemO clearfix">\
              <div class="w10">\
                <p class="p1">'+item.mobile+'</p>\
              </div>\
              <div class="w5 center">\
                <p class="p1">'+item.name+'</p>\
              </div>\
              <div class="w5 center">\
                <p class="p3">订单数量</p>\
                <p class="p2">'+item.orderNum+'</p>\
              </div>\
              <div class="w4 center">\
                <p class="p3">订单金额</p>\
                <p class="p2">'+item.totalMoney.toMoney()+'</p>\
              </div>\
            </div>\
          </li>';
          $(".mui-table-view").append(li);
        }
      }else{
        mui('#pullrefresh').pullRefresh().endPullupToRefresh(true);
      }
    }
  });
}