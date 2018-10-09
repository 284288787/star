var SERVICE_ADDRESS = 'http://mgr.hnkbmd.com';
var IMAGE_PREFIX = 'http://mgr.hnkbmd.com';
function getParam(name){
  var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");     
  var r = window.location.search.substr(1).match(reg);
  //search,查询？后面的参数，并匹配正则    
  if(r!=null)
    return  unescape(r[2]); 
  return null;
}

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
        options.success(res.data);
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

function getLoginInfo(){
  if(!localStorage.login_user) return null;
  var user = JSON.parse(localStorage.login_user);
  return user;
}

function getShopInfo(){
  if(!localStorage.distributor) return null;
  var distributor = JSON.parse(localStorage.distributor);
  return distributor;
}

function islogin(){
  if(!localStorage.login_user) return false;
  var user = JSON.parse(localStorage.login_user);
  return user.memberId > 0;
}

function setLoginInfo(user){
  localStorage.login_user = JSON.stringify(user);
}

function setShopInfo(distributor){
  localStorage.distributor = JSON.stringify(distributor);
}

function logout(){
  localStorage.removeItem('login_user');
}

function putLocalData(key, value){
  localStorage[key] = value;
}

function getLocalData(key){
  return localStorage[key];
}

function delLocalData(key){
  var val = localStorage[key];
  localStorage.removeItem(key);
  return val;
}

mui('body').on('tap', 'a', function() {
  var href = jQuery(this).attr("href");
  if(href.indexOf("#")==0) return true;
  document.location.href = this.href;
});

String.prototype.gapSeconds = function (g) {
  if(!this) return '';
  var tem = this.substring(0,19);
  tem = tem.replace(/-/g,'/'); 
  var t1 = new Date().getTime();
  var t2 = new Date(tem).getTime();
  var t = g - (t1 - t2) / 1000;
  return t.toFixed(0);
}

String.prototype.before = function (time) {
  if(!this) return '';
  var tem = this.substring(0,19);    
  tem = tem.replace(/-/g,'/'); 
  var date = new Date(tem);
  return date > time;
}

String.prototype.formatDate = function (fmt, addSeconds) {
  if(!this) return '';
  var tem = this.substring(0,19);    
  tem = tem.replace(/-/g,'/'); 
  var date = new Date(tem);
  if(addSeconds){
    date = new Date(date.getTime() + addSeconds * 1000);
  }
  return date.format(fmt);
}

//对Date的扩展，将 Date 转化为指定格式的String
//月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
//年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
//例子： 
//(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
//(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
Date.prototype.format = function (fmt) {
  var o = {
      "M+": this.getMonth() + 1, //月份 
      "d+": this.getDate(), //日 
      "h+": this.getHours(), //小时 
      "m+": this.getMinutes(), //分 
      "s+": this.getSeconds(), //秒 
      "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
      "S": this.getMilliseconds() //毫秒
  };
  if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
  for (var k in o)
  if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
  return fmt;
 }