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
var beginTime = getParam("beginTime");
function pulldownRefresh() {
  setTimeout(function() {
    pageNum = 1;
    $(".mui-table-view li:not(:first)").remove("");
    loadData(pageNum, pageSize, beginTime);
    mui('#pullrefresh').pullRefresh().endPulldownToRefresh(true);
    pageNum++;
  }, 10);
}
function pullupRefresh() {
  setTimeout(function() {
    loadData(pageNum, pageSize, beginTime);
    pageNum++;
  }, 10);
}
var states={1:"审核中", 2:"汇款中", 3:"未通过", 4:"已完成"};
function loadData(pageNum, pageSize, beginTime){
  ajax({
    url: '/api/kickbackDetail/queryKickbackDetail',
    data: {'distributorId': user.distributorId, 'pager.pageNum': pageNum, 'pager.pageSize': pageSize},
    success: function(items){
      if(null != items && items.length > 0){
        mui('#pullrefresh').pullRefresh().endPullupToRefresh(false);
        var productIds = "";
        for(var o in items){
          var item = items[o];
          var li = '<li class="mui-table-view-cell"><span class="p1">'+item.createTime.formatDate('d号h点m分')+'</span> <span class="p2">起：'+item.pointBeginTime.formatDate('d号h点m分')+'<br>止：'+item.pointEndTime.formatDate('d号h点m分')+'</span> <span class="p3">￥'+(item.totalMoney / 100.0).toFixed(2)+'+'+(item.totalMoneyYun / 100.0).toFixed(2)+'</span><span class="p4">'+(item.state!=3 ? states[item.state]:item.reject)+'</span></li>';
          $(".mui-table-view").append(li);
        }
      }else{
        mui('#pullrefresh').pullRefresh().endPullupToRefresh(true);
      }
    }
  });
}