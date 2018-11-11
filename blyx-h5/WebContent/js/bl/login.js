var redirctUrl = getParam("redirect_url");
if(!redirctUrl)redirctUrl="user.html";
$(function() {
  $(".mui-btn-success").on("tap", function(){
    var mobile=$(".phone").val();
    var code=$(".code").val();
    if(!mobile || !code){
      mui.toast("手机号码，验证码必填");
      return;
    }
    var user = getLoginInfo();
    if(user && user.openId){
      ajax({
        url: '/api/member/loginAndReg',
        data: {'mobile': mobile, 'code': code, 'tag': 1, 'openId': user.openId},
        success: function(data){
          setLoginInfo(data);
          document.location.href=redirctUrl;
        },
        othercode: function(res){
          if(res.code==1){
            authLogin(mobile, code);
          }else{
            mui.toast(res.msg);
          }
        }
      });
    }else{
      authLogin(mobile, code);
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
      data: {'mobile': mobile, 'tag': 1},
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

function authLogin(mobile, code){
  redirctUrl = encodeURIComponent(redirctUrl);
  var url = location.protocol + '//'+ location.hostname + '/jump.html?to=' + redirctUrl;
  url = encodeURI(url);
  var state = 'yx';
  putLocalData("mobile", mobile);
  putLocalData("code", code);
  var wx = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx8a05f2d3eb34111f&redirect_uri="+url+"&response_type=code&scope=snsapi_userinfo&state="+state+"#wechat_redirect";
  document.location.href=wx;
}