var user = getLoginInfo();

$(function(){
  ajax({
    url: '/api/kickbackDetail/getDistributorMoney',
    data: {'distributorId': user.distributorId},
    success: function(data){
      $("p.rbm b").text((data.totalMoeny / 100.0).toFixed(2));
      $("p.total").text(((data.totalMoeny + data.auditingMoney) / 100.0).toFixed(2));
      $("p.auditing").text((data.auditingMoney / 100.0).toFixed(2));
      $(".mui-btn-link").on("tap", function(){
        document.location.href="mingxi.html?beginTime="+data.beginTime;
      });
      if(data.state == 1 || data.totalMoeny <= 0){
        $("button.mui-btn-success").addClass("mui-disabled");
      }else{
        $("button.mui-btn-success").on("tap", function(){
          var thisObj = $(this);
          mui.confirm("确定申请提现？", ' ', ['取消', '确定'], function(e) {
            if (e.index == 1) {
              ajax({
                url: '/api/kickbackDetail/apply',
                data: {'distributorId': user.distributorId},
                success: function(data){
                  mui.toast("申请成功，请等待审核结果。");
                  thisObj.addClass('mui-disabled').off();
                }
              });
            }
          }, 'div')
        })
      }
    }
  });
});