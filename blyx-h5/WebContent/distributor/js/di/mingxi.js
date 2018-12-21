var user = getLoginInfo();
var pageNum = 1, pageSize = 15;
var beginTime = getParam("beginTime");
(function($m) {
  $m.ready(function() {
    $m('.mui-scroll-wrapper').scroll({
      indicators: true, //是否显示滚动条
    });
    $m.each(document.querySelectorAll('.mui-slider-group .mui-scroll'), function(index, pullRefreshEl) {
      var pageNum = 1;
      $m(pullRefreshEl).pullToRefresh({
        down: {
          callback: function() {
            var self = this;
            setTimeout(function() {
              var ul = self.element.querySelector('.mui-table-view');
              ul.innerHTML = "";
              pageNum = 1;
              loadData(index, self, pageNum, pageSize, beginTime);
              pageNum++;
              self.endPullDownToRefresh();
            }, 200);
          }
        },
        up: {
          auto: true, //默认执行一次上拉加载
          //contentinit: '上拉可以加载更多信息哦',
          //contentdown: '上拉加载结束啦',
          //contentrefresh: '正在加载信息请稍等',
          contentnomore: '没有更多相关明细记录',
          callback: function() {
            console.log(pageNum)
            var self = this;
            setTimeout(function() {
              loadData(index, self, pageNum, pageSize, beginTime);
              pageNum++;
            }, 1);
          }
        }
      });
    });
  });
})(mui);

function loadData(index, self, pageNum, pageSize, beginTime){
  var params = {'beginCreateTime': beginTime, 'apiquery': true, 'states': '2,3', 'pager.pageNum': pageNum, 'pager.pageSize': pageSize};
  if(index) params["distributorId"] = user.distributorId;
  else{
    params["parentDistributorId"] = user.distributorId;
  }
  ajax({
    url: '/api/order/queryOrder',
    data: params,
    success: function(items){
      var ul = self.element.querySelector('.mui-table-view');
      if(null != items && items.length > 0){
        self.endPullUpToRefresh(false);
        for(var o in items){
          var item = items[o];
//          var li = '<li class="mui-table-view-cell"><span class="p1">'+item.createTime.formatDate('d号h点m分')+'</span> <span class="p2">'+item.orderCode+'</span> <span class="p3">￥'+(item.totalMoney / 100.0).toFixed(2)+'</span><span class="p4">￥'+(item.totalBrokerage / 100.0).toFixed(2)+'</span></li>';
          var li = '\
            <li class="mui-table-view-cell">\
              <div class="oitem">\
                <p class="pbox">交易时间：'+item.createTime.formatDate('M月d日h点m分')+'<span>交易单号：'+item.orderCode+'</span></p>\
                <p class="pbox">商品金额：￥'+(item.totalMoney / 100.0).toFixed(2)+'<span>提成金额：￥'+((index ? item.totalBrokerage:item.totalBrokerageFirst) / 100.0).toFixed(2)+'</span></p>\
              </div>\
            </li>';
          ul.appendChild($(li)[0]);
        }
      }else{
        if(pageNum == 1){
          $(".mui-pull-bottom-tips", $(self.element).parent()).hide();
          ul.appendChild($('<li>\
              <div class="nocontent">\
              <span class="mui-icon mui-icon-extra mui-icon-extra-order"></span>\
              <p>您目前还没有相关明细记录</p>\
              </div>\
          </li>')[0]);
        }
        self.endPullUpToRefresh(true);
      }
    }
  });
}