var redirectUrl = getParam("redirect_url");
if(!redirectUrl)redirectUrl="index.html";
$(function() {
  var code = getParam("code");
  ajax({
    url: "/weixin/ticket/getWeixinUserInfo",
    data: {code: code},
    success: function(data){
      var head = data.headimgurl;
      var name = data.nickname;
      var openid = data.openid;
      $(".mui-btn-success").on("tap", function(){
        var mobile=$(".phone").val();
        var code=$(".code").val();
        if(!mobile || !code){
          mui.toast("手机号码，验证码必填");
          return;
        }
        ajax({
          url: '/api/distributor/login',
          data: {'mobile': mobile, 'code': code, 'tag': 2, 'openId': openid, 'name': name, 'head': head},
          success: function(data){
            setLoginInfo(data);
            document.location.href=redirectUrl;
          }
        });
      });
    }
  });
  
  $("#send").on("tap", function(){
    var mobile=$(".phone").val();
    if (!/^1[34578]\d{9}$/.test(mobile)) {
      mui.toast("请填写正确的手机号");
      return false;
    }
    $("#send").addClass("disabled");
    ajax({
      url: '/api/sms/send',
      data: {'mobile': mobile, 'tag': 2},
      success: function(data){
        const TIME_COUNT = 60;
        if (!this.timer) {
          this.count = TIME_COUNT;
          this.timer = setInterval(() => {
            if (this.count > 0 && this.count <= TIME_COUNT) {
              $("#send").text("重新发送(" + this.count + ")") ;
              this.count--;
            } else {
              $("#send").removeClass("disabled");
              $("#send").text("发送验证码");
              clearInterval(this.timer);
              this.timer = null;
            }
          }, 1000);
        }
      }
    });
    return false;
  })
});

var SERVICE_ADDRESS = 'http://mgr.hnkbmd.com';
function ajax(options){
  $.ajax({
    contentType: options.contentType || 'application/x-www-form-urlencoded',
    url: SERVICE_ADDRESS + options.url,
    async: options.async || true,
    data: options.data,
    type: options.type || 'post',
    dataType: 'json',
    success: function(res){
      if(res.code==0){
        if(options.success) options.success(res.data);
      }else{
        if(options.othercode){
          options.othercode(res);
        }else{
          mui.toast(res.msg);
        }
      }
    },
    error: function(){
      mui.toast("服务繁忙，请稍后再试！");
    }
  });
}

function getParam(name){
  var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");     
  var r = window.location.search.substr(1).match(reg);
  //search,查询？后面的参数，并匹配正则    
  if(r!=null)
    return  unescape(r[2]); 
  return null;
}