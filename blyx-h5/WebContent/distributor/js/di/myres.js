var distributor = getLoginInfo();
$(function(){
  var iidx = location.href.indexOf("#item");
  if(iidx != -1){
    var id = location.href.substring(iidx);
    $("a.mui-active").removeClass("mui-active");
    $("a[href='"+id+"']").addClass("mui-active");
    $("div.mui-active").removeClass("mui-active");
    $("div"+id).addClass("mui-active");
  }
});
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
              loadData(self, pageNum, 10, index);
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
          contentnomore: '没有更多相关订单',
          callback: function() {
            console.log(pageNum)
            var self = this;
            setTimeout(function() {
              loadData(self, pageNum, 10, index);
              pageNum++;
            }, 1);
          }
        }
      });
    });
  });
})(mui);

var states={1: '待付款', 2: '待提货', 3: '已提货'}

function loadData(self, pageNum, pageSize, index){
  var param = {'time': index, 'brokerage0': 0, 'states': '2,3', distributorId: distributor.distributorId, 'pager.pageNum': pageNum, 'pager.pageSize': pageSize};
  ajax({
    url: '/api/order/sumBrokerage',
    data: param,
    success: function(val){
      var ele = self.element.querySelector('.totel');
      $("b", $(ele)).text((val/100.0).toFixed(2));
    }
  });
  ajax({
    url: '/api/order/queryOrder',
    data: param,
    success: function(items){
      var ul = self.element.querySelector('.mui-table-view');
      if(null != items && items.length > 0){
        self.endPullUpToRefresh(false);
        for(var o in items){
          var item = items[o];
          var details = item.details;
          for(var i in details){
            var detail = details[i];
            var ele = '<li class="mui-table-view-cell">\
              <div class="itemO clearfix">\
              <div class="w10">\
                <p class="p1">日期：'+item.createTime.formatDate('d号h点m分')+'</p>\
                <p class="p3">'+detail.title+'</p>\
              </div>\
              <div class="w5 center">\
                <p class="p1">提成单位</p>\
                <p class="p2">'+(detail.brokerage / 100.0).toFixed(2)+'</p>\
                <p class="p3">元/份</p>\
              </div>\
              <div class="w5 center">\
                <p class="p1">销售数量</p>\
                <p class="p2">'+detail.count+'</p>\
                <p class="p3">&nbsp;</p>\
              </div>\
              <div class="w4 center">\
                <p class="p1">提成</p>\
                <p class="p2">'+((detail.brokerage * detail.count) / 100.0).toFixed(2)+'</p>\
                <p class="p3">&nbsp;</p>\
              </div>\
            </div>\
          </li>';
          ul.appendChild($(ele)[0]);
          }
        }
        if(items.length < 3 && pageNum == 1){
          $(".mui-pull-bottom-tips", $(self.element).parent()).hide();
        }
      }else{
        if(pageNum == 1){
          $(".mui-pull-bottom-tips", $(self.element).parent()).hide();
          ul.appendChild($('<li>\
              <div class="nocontent">\
              <span class="mui-icon mui-icon-extra mui-icon-extra-order"></span>\
              <p>您目前还没有可查提成...</p>\
              </div>\
          </li>')[0]);
        }
        self.endPullUpToRefresh(true);
      }
    }
  });
}
$(function() {

});