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
              loadSaleAfterData(self, index, pageNum, 10);
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
              loadSaleAfterData(self, index, pageNum, 10);
              pageNum++;
            }, 1);
          }
        }
      });
    });
  });
})(mui);

var states={1: '待付款', 2: '待提货', 3: '已提货'};
var saleAfterStates={1: "处理中", 2: "已通过", 3: "不通过", 4: "已取消", 5: "已删除"};
function loadSaleAfterData(self, index, pageNum, pageSize){
  var param = {'distributorId': distributor.distributorId, 'pager.pageNum': pageNum, 'pager.pageSize': pageSize, 'pager.orderType': 'desc'};
  if(index==1){
    param['states'] = '1,2,3';
  }
  if(index==2){
    param['states'] = '1,2,3,4,5';
  }
  ajax({
    url: '/api/orderAfterSale/queryOrderAfterSale',
    data: param,
    success: function(items){
      var ul = self.element.querySelector('.mui-table-view');
      if(null != items && items.length > 0){
        self.endPullUpToRefresh(false);
        for(var o in items){
          var item = items[o];
          var ele = '\
            <li class="mui-table-view-cell mui-media">\
                <div class="mui-media-body">\
              <div class="mui-input-row mui-left item">\
                <img class="mui-media-object mui-pull-left" src="'+IMAGE_PREFIX+item.mainPictureUrl+'">\
                <span class="block productTitle">'+item.title+'</span><br>\
                <span class="block">数量：'+item.detailCount+'</span>\
              </div>\
              <div class="mui-left line">';
          if(item.state){
            if(item.state == 1){
              ele+='<span class="txt">申请时间：'+item.createTime.formatDate("yy年MM月dd日 hh点mm分")+'</span><span class="mui-right"><input type="button" class="cancelBtn pl mui-btn mui-btn-danger mui-btn-outlined" data-id="'+item.id+'" value="取消售后"></span>'
            }else if(item.state == 2 || item.state == 4){
              ele+='<span class="mui-right"><input type="button" class="exprBtn pl mui-btn mui-btn-danger mui-btn-outlined" data-id="'+item.id+'" value="'+(item.state==2 ? '填写' : '修改')+'快递信息"></span>'
            }else if(item.state == 3){
              ele+='<span class="txt">'+item.reason+'</span>';
            }else if(item.state == 5){
              ele+='<span class="txt">处理中</span>';
            }else if(item.state == 6){
              ele+='<span class="txt">已完成</span>';
            }else if(item.state == 7){
              ele+='<span class="txt">已取消</span>';
            }else if(item.state == 8){
              ele+='<span class="txt">已删除</span>';
            }
          }else{
            ele += (item.days>17? '<span class="txt">售后期已过</span><span class="mui-right">\
                <input type="button" class="applyBtn pl mui-btn mui-btn-danger mui-btn-outlined mui-disabled" value="申请售后"></span>\
                ':'<span class="mui-right"><input type="button" class="applyBtn pl mui-btn mui-btn-danger mui-btn-outlined" data-count="'+item.detailCount+'" data-detailid="'+item.detailId+'" data-orderid="'+item.orderId+'" value="申请售后"></span>');
          }
        ele+= '</div>\
            </div>\
          </li>'
          ul.appendChild($(ele)[0]);
        }
        $(".applyBtn").off().on('tap', function(){
          var detailId = $(this).attr("data-detailid");
          var orderId = $(this).attr("data-orderid");
          var count = $(this).attr("data-count");
          var title = $.trim($(".productTitle", $(this).parents("li")).text());
          var box = mui('.mui-numbox').numbox();
          box.options["max"]=count;
          $("#ok .productTitle b").text(title);
          $("#ok input.mui-input-numbox").val(count);
          $("#ok input[name=reason]").val("");
          $("#ok .sbmit").attr({"data-detailid": detailId, "data-orderid": orderId});
          mui('#ok').popover('show');
          return false;
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
              <p>暂无记录</p>\
              </div>\
          </li>')[0]);
        }
        self.endPullUpToRefresh(true);
      }
    }
  });
}
$(function(){
  $("#ok .cancel").on("tap", function(){
    mui('#ok').popover('hide');
  }); 
  $("#ok .sbmit").on("tap", function(){
    var detailId = $(this).attr("data-detailid");
    var orderId = $(this).attr("data-orderid");
    var count = $("#ok input.mui-input-numbox").val();
    var t1 = $("#ok #type_1")[0].checked;
    var t2 = $("#ok #type_2")[0].checked;
    var reason = $.trim($("#ok input[name=reason]").val());
    if(!count){
      mui.toast("售后数量必填");
      return false;
    }
    if(!t1 && !t2){
      mui.toast("售后方式必选");
      return false;
    }
    type = t1 ? 1 : 2;
    if(!reason){
      mui.toast("售后原因必填");
      return false;
    }
    var params={"orderId": orderId, "type": type, "count": count, "detailIds": [detailId], "distributorId": distributor.distributorId, "remark": reason};
    ajax({
      url: '/api/orderAfterSale/saveOrderAfterSale',
      data: JSON.stringify(params),
      contentType:"application/json",
      success: function(res){
        mui.toast("提交成功，请等待审核");
        mui('#ok').popover('hide');
      }
    });
  }); 
});