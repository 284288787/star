var user = getLoginInfo();
if (!user) {
  document.location.href = 'login.html?redirect_url=orderlist.html';
}
var distributor = getShopInfo();
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

function loadData(self, pageNum, pageSize, state){
  var param = {memberId: user.memberId, 'pager.pageNum': pageNum, 'pager.pageSize': pageSize};
  if(state > 0) param['states'] = state;
  else param['states'] = "1,2,3";
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
          var ele = '<li class="mui-table-view-cell">\
                <div class="oitem">\
                <p class="pbox">订单编号：'+item.orderCode+'\
                    <span class="red">'+states[item.state]+'</span>\
                </p>\
                <div class="imgbox mui-navigate-right viewdetail" data-orderId="'+item.orderId+'">';
          var num = 0;
          for(var d in details){
            var detail = details[d];
            ele += '<img src="'+IMAGE_PREFIX+detail.mainPictureUrl+'" alt="" class="goodsicon">';
            num += detail.count;
          }
          ele += '</div>\
                <p class="price">'+item.createTime+'\
                    <span>共'+num+'件商品\
                        <b>￥'+((item.totalMoney + (item.deliveryType == 2 ? item.despatchMoney : 0)) / 100.0).toFixed(2)+'</b>\
                    </span>\
                </p>\
                <p class="pbox clearfix">';
          if(item.state > 1){
            ele +='<em>提货单号：'+item.pickupCode+'</em>';
            if(item.state==2){
              ele +='<button data-orderId="'+item.orderId+'" class="fl mui-btn mui-btn-danger mui-btn-outlined enterorder" size="small" plain>确认提货</button>';
            }
            if(item.state==3){
              ele +='<button data-orderId="'+item.orderId+'" class="fl mui-btn mui-btn-outlined deleteorder" size="small" plain>删除</button>';
            }
          }else{
            ele +=item.createTime.formatDate("h点m分", 30*60)+'，订单将失效！</span><button data-orderId="'+item.orderId+'" class="fl mui-btn mui-btn-danger mui-btn-outlined topay" size="small" type="danger" plain>去付款</button>';
          }
          ele+='</p>\
            </div>\
          </li>'
          ul.appendChild($(ele)[0]);
        }
        var deleteOrder = function(){
          var obj = $(this);
          var orderId = $(this).attr("data-orderId");
          ajax({
            url: '/api/order/deleteOrder',
            data: {orderId: orderId},
            success: function(){
              obj.parents("li").remove();
            }
          });
        };
        $(".deleteorder").off().on('tap', deleteOrder);
        $(".enterorder").off().on('tap', function(){
          var obj = $(this);
          var orderId = $(this).attr("data-orderId");
          ajax({
            url: '/api/order/pickupOrder',
            data: {orderId: orderId},
            success: function(){
              obj.removeClass("enterorder").addClass("deleteorder").off().on('tap', deleteOrder);
              obj.text("删除");
            }
          });
        });
        $(".topay").off().on('tap', function(){
          var orderId = $(this).attr("data-orderId");
          putLocalData("pay_orderId", orderId);
          document.location.href='pay.html';
        });
        $(".viewdetail").off().on('tap', function(){
          var orderId = $(this).attr("data-orderId");
          document.location.href='orderdetail.html?orderId='+orderId;
        });
        if(items.length < 3 && pageNum == 1){
          $(".mui-pull-bottom-tips", $(self.element).parent()).hide();
        }
      }else{
        if(pageNum == 1){
          $(".mui-pull-bottom-tips", $(self.element).parent()).hide();
          ul.appendChild($('<li>\
              <div class="nocontent">\
              <span class="mui-icon mui-icon-extra mui-icon-extra-order"></span>\
              <p>您目前还没有相关订单...</p>\
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