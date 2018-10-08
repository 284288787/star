var user = getLoginInfo();
if(! user){
  document.location.href='login.html?redirect_url=addaddress.html';
}
var id = getParam("id");
mui.init();
mui('.mui-scroll-wrapper').scroll({
  scrollY : true, //是否竖向滚动
  bounce : true
//是否启用回弹
});
$("#address").on('tap', function() {
  mui('#addSelect').popover('show');
  addS();
})
function giveValue(provinceName, cityName, areaName) {
  $("#address").text(provinceName+cityName+areaName);
  $("#provinceName").val(provinceName);
  $("#cityName").val(cityName);
  $("#areaName").val(areaName);
  mui('#addSelect').popover('hide');
}
$(function() {
  ajax({
    url: '/api/deliveryAddress/getDeliveryAddress',
    data: {id: id},
    success: function(data){
      $("input.name").val(data.name);
      $("input.mobile").val(data.mobile);
      $("#provinceName").val(data.provinceName);
      $("#cityName").val(data.cityName);
      $("#areaName").val(data.areaName);
      $("input.address").val(data.address);
      $("#address").text(data.provinceName+data.cityName+data.areaName);
      if(data.def!=1)
      $(".mui-switch-mini").removeClass("mui-active");
    }
  });
  $(".mui-btn-success").on("tap", function(){
    var name = $("input.name").val();
    var mobile = $("input.mobile").val();
    var provinceName = $("#provinceName").val();
    var cityName = $("#cityName").val();
    var areaName = $("#areaName").val();
    var address = $("input.address").val();
    var def = $(".mui-switch-mini").hasClass("mui-active") ? 1 : 0;
    if(!name) {
      mui.toast("请填写姓名");
      return false;
    }
    if(!mobile) {
      mui.toast("请填写电话号码");
      return false;
    }
    if (!/^1[34578]\d{9}$/.test(mobile)) {
      mui.toast("手机号码错误");
      return false;
    }
    if(!areaName) {
      mui.toast("地区必须选择到区县");
      return false;
    }
    if(!address) {
      mui.toast("请填写详细地址");
      return false;
    }
    var param = {id: id, memberId: user.memberId, name: name, mobile: mobile, provinceName: provinceName, cityName: cityName, areaName: areaName, address: address, def: def};
    ajax({
      url: '/api/deliveryAddress/updateDeliveryAddress',
      data: param,
      success: function(){
        document.location.href='addresslist.html';
      }
    });
  });
});
