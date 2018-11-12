var user = getLoginInfo();
if(! user){
  document.location.href='login.html?redirect_url=mycar.html';
}
$(function() {
  mui('.mui-scroll-wrapper').scroll({
    deceleration: 0.0005
  });
  loadCarData();
});

function loadCarData(){
  ajax({
    url: '/api/shoppingCart/queryShoppingCart',
    data: {"memberId": user.memberId},
    success: function(items){
      $(".main ul").html("");
      if(null != items && items.length > 0){
        for(var o in items){
          var item = items[o];
          if(item.state > 3){
            continue;
          }
          var canBuy = true;
          if(item.state > 1){
            canBuy = false;
          }
          $(".main ul").append('<li class="clearfix">\
              <div class="col1">\
              <div class="mui-input-row mui-checkbox mui-left">\
                <label>&nbsp;</label> <input name="subcheck" data-cartid="'+item.cartId+'" data-price="'+(item.price/100.0).toFixed(2)+'" type="checkbox" '+(item.checked==1 && canBuy?'checked':'')+(canBuy?'':' disabled')+'>\
              </div>\
            </div>\
            <div class="col2">\
              <img src="'+IMAGE_PREFIX+item.mainPictureUrl+'" alt="">\
            </div>\
            <div class="col3">\
              <p class="gt">'+item.title+' <em>￥'+(item.price/100.0).toFixed(2)+'</em>\
              </p>\
              <p class="gd">'+item.specification+'</p>' + 
              (canBuy ? '' : '<p class="gd red">'+(item.state==3? '已售罄': '预售')+'</p>') +
              '<div class="gn">\
                <div class="mui-numbox smallbox" data-numbox-min="1">\
                  <button class="mui-btn mui-btn-numbox-minus" type="button" data-cartid="'+item.cartId+'">-</button>\
                  <input class="mui-input-numbox" type="number" value="'+item.num+'" />\
                  <button class="mui-btn mui-btn-numbox-plus" type="button" data-cartid="'+item.cartId+'">+</button>\
                </div>\
              </div>\
            </div>\
          </li>');
        }
        mui('.mui-numbox').numbox();
        $("button.mui-btn-numbox-minus, button.mui-btn-numbox-plus").off().on('tap', function(){
          var cartId = $(this).attr("data-cartid");
          var num = $("input.mui-input-numbox", $(this).parent()).val();
          ajax({
            url: '/api/shoppingCart/updateShoppingCartNum',
            data: {'memberId': user.memberId, 'cartId': cartId, 'num': num}
          });
          total();
        });
        var chkall = $("#selectall");
        chkall.change(function(){
          var checked = this.checked;
          if(checked){
            $(":checkbox[name=subcheck]").each(function(){
              if(!this.disabled)
                this.checked = true;
            });
          }else{
            $(":checkbox[name=subcheck]").removeAttr("checked");
          }
          total();
        });
        var cbknum = $(":checkbox[name=subcheck]:not(:disabled)").length;
        var chknum = $(":checked[name=subcheck]").length;
        if (cbknum == chknum) {
          chkall[0].checked = true;
        } else {
          chkall[0].checked = false;
        }
        total();
        $(":checkbox[name=subcheck]").change(function() {
          if (this.checked) {
            chknum++;
          } else {
            chknum--;
          }
          var cbknum = $(":checkbox[name=subcheck]:not(:disabled)").length;
          var chknum = $(":checked[name=subcheck]").length;
          if (cbknum == chknum) {
            chkall[0].checked = true;
          } else {
            chkall[0].checked = false;
          }
          total();
        });
      }else{
        $(".main ul").html('<li class="clearfix"><div style="text-align:center">空的购物车，去<a href="index.html">添加商品</a></div></li>');
      }
    }
  });
}

function total(){
  var money = 0;
  var carIds = "";
  $(":checked[name=subcheck]").each(function(){
    var num=$(".mui-input-numbox", $(this).parents("li")).val();
    var price=$(this).attr("data-price") * 1 * num;
    money+=price;
    carIds+="," + $(this).attr("data-cartid");
  });
  $("#mean2 b.red").text("￥" + money.toFixed(2));
  if (! $("#header button").hasClass("edit")) {
    ajax({
      url: '/api/shoppingCart/updateShoppingCartChecked',
      data: {'memberId' : user.memberId, 'cartIds': carIds.substring(1)},
      success: function(data){
        
      }
    });
  }
}
var checks = new Array();
mui("#header").on("tap", "button", function() {
  if ($(this).hasClass("edit")) {
    $(this).text("编辑")
    $(this).removeClass("edit")
    $("#sub").text("去结算")
    if(checks && checks.length > 0){
      for(var i in checks){
        var item = checks[i];
        var obj = $(":checkbox[name=subcheck][data-cartid="+item.cartId+"]");
        if(obj.length == 1){
          obj[0].checked = item.checked;
          if(item.disabled) obj.attr("disabled", true);
        }
      }
      var chkall = $("#selectall");
      var cbknum = $(":checkbox[name=subcheck]:not(:disabled)").length;
      var chknum = $(":checked[name=subcheck]").length;
      if (cbknum == chknum && chknum > 0) {
        chkall[0].checked = true;
      } else {
        chkall[0].checked = false;
        $(".main ul").html('<li class="clearfix"><div style="text-align:center">空的购物车，去<a href="index.html">添加商品</a></div></li>');
      }
    }
  } else {
    $(this).addClass("edit")
    $(this).text("完成");
    $("#sub").text("删除");
    $(":checkbox[name=subcheck]").each(function(){
      checks.push({cartId: $(this).attr("data-cartid"), checked: this.checked, disabled: $(this).attr("disabled")});
    });
    $(":checkbox").removeAttr("checked");
    $(":checkbox").removeAttr("disabled");
  }
})
mui('#nav').on("tap", "#mean3", function() {
  if ($("#header button").hasClass("edit")) {
    var carIds = "";
    $(":checked[name=subcheck]").each(function(){
      carIds+="," + $(this).attr("data-cartid");
    });
    if(! carIds) {
      mui.toast('选中要删除的商品');
      return false;
    }
    ajax({
      url: '/api/shoppingCart/deleteShoppingCart',
      data: {'memberId' : user.memberId, 'cartIds': carIds.substring(1)},
      success: function(data){
        $(":checked[name=subcheck]").each(function(){
          $(this).parents("li").remove();
        });
      }
    });
  } else {
    ajax({
      url: '/api/shoppingCart/enterOrderCheck',
      data: {'memberId' : user.memberId},
      success: function(data){
        document.location.href='submit.html';
      }
    });
  }
  return false;
})