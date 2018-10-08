var user = getLoginInfo();
if(! user){
  document.location.href='login.html?redirect_url=addresslist.html';
}

$(function() {
  initAddressList();
});
var addrs;
function initAddressList(){
  ajax({
    url: '/api/deliveryAddress/queryDeliveryAddress',
    data: {'memberId' : user.memberId},
    success: function(items){
      addrs = items;
      $(".main ul").html("");
      if(null != items && items.length > 0){
        var oldId = getLocalData("deliveryAddressId");
        var to = getLocalData("to");
        for(var o in items){
          var item = items[o];
          $(".main ul").append('<li class="mui-table-view-cell mui-media" data-idx="'+o+'">\
              <div class="editor">\
              '+(to ? '<div class="mui-input-row mui-radio mui-left mui-pull-left">\
                <label></label> <input name="address" type="radio" value="'+item.id+'" class="radio" '+(item.id==oldId ? 'checked' : '')+'>\
              </div>' : '')+'\
              <div class="mui-media-body">'+item.name+'，'+item.mobile+'\
                <p>'+item.provinceName+item.cityName+item.areaName+item.address+'</p>\
              </div>\
              <span class="edit"> <span class="mui-icon mui-icon-compose" data-id="'+item.id+'"></span>\
              </span>\
            </div>\
            <div class="editor mui-pull-right"><a class="abtn delete" data-id="'+item.id+'">删除地址</a>' + 
            (item.def==1 ? '<a class="abtn setdef" data-id="'+item.id+'">默认地址</a>' : '<a class="abtn setdef" data-id="'+item.id+'">设为默认</a>') +
            '</div>\
          </li>');
        }
        $(".edit .mui-icon-compose.mui-icon").off().on("tap", function(){
          var id = $(this).attr("data-id");
          document.location.href='editaddress.html?id='+id;
        })
        $(".setdef").off().on("tap", function(){
          var obj = $(this);
          if(obj.text().indexOf("默认地址")!=-1) return false;
          var id = obj.attr("data-id");
          ajax({
            url: '/api/deliveryAddress/setDef',
            data: {id: id, memberId: user.memberId},
            success: function(){
              $(".setdef").text("设为默认");
              obj.text("默认地址");
            }
          });
          return false;
        });
        $(".delete").off().on("tap", function(){
          var obj = $(this);
          mui.confirm('', '确定删除该收货地址?', ['否', '是'], function(e) {
            if (e.index == 1) {
              var id = obj.attr("data-id");
              ajax({
                url: '/api/deliveryAddress/deleteDeliveryAddress',
                data: {id: id, memberId: user.memberId},
                success: function(){
                  obj.parents("li").remove();
                }
              });
            }
          })
          return false;
        });
        if(to){
          $(".main ul li").off().on("tap", function(){
            var idx = $(this).attr("data-idx");
            var item = addrs[idx];
            putLocalData("chooseAddress", JSON.stringify(item));
            delLocalData("deliveryAddressId");
            delLocalData("to");
            document.location.href=to;
          })
        }
      }else{
        $(".main ul").html('<li class="clearfix"><div style="text-align:center;background-color:transparent;height:500px;line-height:500px;">请添加收货地址</div></li>');
      }
    }
  });
}