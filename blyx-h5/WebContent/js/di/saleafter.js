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
            var self = this;
            setTimeout(function() {
              if(index == 0){
                loadOrderData(self, pageNum, 10);
                pageNum++;
              } else {
                loadSaleAfterData(self, pageNum, 10);
                pageNum++;
              }
            }, 1);
          }
        }
      });
    });
  });
})(mui);

var states={1: '待付款', 2: '待提货', 3: '已提货'}
function loadSaleAfterData(self, pageNum, pageSize){
  
}
function loadOrderData(self, pageNum, pageSize){
  var param = {'distributorId': distributor.distributorId, 'states': '2,3', 'pager.pageNum': pageNum, 'pager.pageSize': pageSize};
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
          <p class="pbox">订单编号：'+item.orderCode+'<br> 下单时间：'+item.createTime+'</p>';
          var num = 0;
          for(var d in details){
            var detail = details[d];
            ele += '<div class="imgbox clearfix">\
              <img src="'+IMAGE_PREFIX+detail.mainPictureUrl+'" alt="" class="goodsicon">\
              <p>'+detail.title+'</p>\
              <p>价格：￥'+(detail.price / 100.0).toFixed(2)+'，数量：'+detail.count+'份，合计：￥'+((detail.price * detail.count) / 100.0).toFixed(2)+'</p>\
            </div>';
            num += detail.count;
          }
          ele += '<p class="price">'+(item.deliveryType == 2 ? '邮费：￥'+(item.despatchMoney/100.0).toFixed(2) : '&nbsp;')+' <span>'+num+'件商品，共：<b>￥'+(item.totalMoney / 100.0).toFixed(2)+'</b></span>\
             <p class="price">&nbsp; <span>总金额： <b>￥'+((item.totalMoney + (item.deliveryType == 2 ? item.despatchMoney : 0)) / 100.0).toFixed(2)+'</b></span>\
              </p>\
              <p class="pbox clearfix">\
                <b>客户名：'+item.name+'</b><br /> <em>联系电话：'+item.mobile+'</em>\
                <button data-orderId="'+item.orderId+'" class="pl mui-btn mui-btn-danger mui-btn-outlined saleafterBtn" size="small" type="danger" plain>申请售后</button>\
              </p>\
            </div>\
          </li>'
          ul.appendChild($(ele)[0]);
        }
        $(".saleafterBtn").off().on('tap', function(){
          var thisObj = $(".saleafterBtn");
          mui.prompt("确定要对该条订单申请售后？", "申请售后说明", ' ', ['取消', '申请'], function(e) {
            if (e.index == 1) {
              var remark = e.value;
              if(!remark) {
                mui.toast("请填写申请售后说明");
                return false;
              }
              var orderId = $(this).attr("data-orderId");
              ajax({
                url: '/api/orderAfterSale/saveOrderAfterSale',
                data: {'orderId': orderId, 'distributorId': distributor.distributorId, 'remark': remark},
                success: function(data){
                  mui.toast("申请已提交，请等耐结果反馈");
                  thisObj.attr("disabled", true).text("处理中");
                }
              });
            }
          }, 'div')
          return false;
        });
        $(".viewdetail").off().on('tap', function(){
          var orderId = $(this).attr("data-orderId");
          document.location.href='orderdetail.html?orderId='+orderId;
        });
        if(items.length < 2 && pageNum == 1){
          $(".mui-pull-bottom-tips", $(self.element).parent()).hide();
        }
      }else{
        if(pageNum == 1){
          $(".mui-pull-bottom-tips", $(self.element).parent()).hide();
          ul.appendChild($('<li>\
              <div class="nocontent">\
              <span class="mui-icon mui-icon-extra mui-icon-extra-order"></span>\
              <p>暂无已交易完成的订单</p>\
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