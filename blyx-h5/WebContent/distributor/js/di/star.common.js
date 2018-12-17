var SERVICE_ADDRESS = 'http://local.cnhnkj.cn:8082';
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

function getLoginInfo(){
  try{
    var tem = localStorage.getItem("login_distributor_user");
    if(!tem) return null;
    var user = JSON.parse(tem);
    return user;
  }catch(e){
    localStorage.removeItem('login_distributor_user');
    return null;
  }
}

function islogin(){
  try{
    var tem = localStorage.getItem("login_distributor_user");
    if(!tem) return false;
    var user = JSON.parse(tem);
    return user.distributorId > 0 && user.py && user.shopName;
  }catch(e){
    localStorage.removeItem('login_distributor_user');
    return false;
  }
}

if(! islogin()){
  var href = encodeURIComponent(document.location.href);
  var url = 'http://fxs.hnkbmd.com/login.html?redirect_url=' + href;
  url = encodeURI(url);
  var state = 'yx';
  var wx = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx8a05f2d3eb34111f&redirect_uri="+url+"&response_type=code&scope=snsapi_userinfo&state="+state+"#wechat_redirect";
  document.location.href=wx;
}else{
	var user = getLoginInfo();
	ajax({
		url: '/api/distributor/getDistributorByMobile',
		data: {'mobile': user.mobile},
		success: function(data){
		  setLoginInfo(data);
		},
		othercode: function(res){
			if(res.code == 2){
				mui.toast(res.msg);
				setTimeout(function(){
					logout();
					document.location.href='http://fxs.hnkbmd.com/login.html';
				}, 1000);
			}
		}
	});
}

function setLoginInfo(user){
  localStorage.setItem('login_distributor_user', JSON.stringify(user));
}

function logout(){
  localStorage.removeItem('login_distributor_user');
}

function putLocalData(key, value){
  starStorage.setItem(key, value);
}

function getLocalData(key){
  return starStorage.getItem(key);
}

function delLocalData(key){
  var val = starStorage.getItem(key);
  starStorage.removeItem(key);
  return val;
}

mui('body').on('tap', 'a', function() {
  var href = jQuery(this).attr("href");
  if(href){
    if(href.indexOf("#")==0) return true;
    document.location.href = this.href;
  }
});

String.prototype.toMoney = function () {
  if(!this) return '0.00';
  return (this / 100.0).toFixed(2);
}

Number.prototype.toMoney = function () {
  if(!this) return 0.00;
  return (this / 100.0).toFixed(2);
}

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
window.cookieStorage = (new (function(){
  var maxage = 60*60*24*1000;
  var path = '/';

  var cookie = getCookie();

  function getCookie(){
      var cookie = {};
      var all = document.cookie;
      if(all === "")
          return cookie;
      var list = all.split("; ");
      for(var i=0; i < list.length; i++){
          var cookies = list[i];
          var p = cookies.indexOf("=");
          var name = cookies.substring(0,p);
          var value = cookies.substring(p+1);
          value = decodeURIComponent(value);
          cookie[name] = value;
      }
      return cookie;
  }

  var keys = [];
  for(var key in cookie)
      keys.push(key);

  this.length = keys.length;

  this.key = function(n){
      if(n<0 || n >= keys.length)
          return null;
      return keys[n];
  };

  this.setItem = function(key, value){
      if(! (key in cookie)){
          keys.push(key);
          this.length++;
      }

      cookie[key] = value;
      var cookies = key + "=" +encodeURIComponent(value);
      if(maxage)
          cookies += "; max-age=" + maxage;
      if(path)
          cookies += "; path=" + path;
      cookies += "; domain=hnkbmd.com";
      document.cookie = cookies;
  };

  this.getItem = function(name){
      return cookie[name] || null;
  };

  this.removeItem = function(key){
      if(!(key in cookie))
          return;

      delete cookie[key];

      for(var i=0; i<keys.length; i++){
          if(keys[i] === key){
              keys.splice(i, 1);
              break;
          }
      }
      this.length--;

      document.cookie = key + "=; max-age=0; domain=hnkbmd.com";
  };

  this.clear = function(){
      for(var i=0; i<keys.length; i++)
          document.cookie = keys[i] + "; max-age=0; domain=hnkbmd.com";
      cookie = {};
      keys = [];
      this.length = 0;
  };
})());

window.starStorage = (new (function(){
  
  var storage;    //声明一个变量，用于确定使用哪个本地存储函数

  if(window.localStorage){
      storage = localStorage;     //当localStorage存在，使用H5方式
  }
  else{
      storage = cookieStorage;    //当localStorage不存在，使用兼容方式
  }

  this.setItem = function(key, value){
      storage.setItem(key, value);
  };

  this.getItem = function(name){
      return storage.getItem(name);
  };

  this.removeItem = function(key){
      storage.removeItem(key);
  };

  this.clear = function(){
      storage.clear();
  };
})());