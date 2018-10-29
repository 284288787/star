$(function(){
  mui('.mui-scroll-wrapper').scroll();
  var mask=mui.createMask(function(){//遮罩层callback事件
    $('#viewImg').remove();
    return true;//返回true关闭mask
  });
  
  $(".enterBtn").on("tap", function(){
    var top = 0;
    var param = {};
    var name = $("input[name=name]").val();
    if(!name){
      $("input[name=name]").addClass("error");
      $("input[name=name]").next().show();
      top = $("input[name=name]").offset().top;
      mui('.mui-scroll-wrapper').scroll().scrollTo(0, -572,0);
    }
    param['name']=name;
    var idCardPic1=$(".idCardPic1 .dataImg").attr("data");
    if(!idCardPic1){
      $(".idCardPic1").addClass("error");
      if(top == 0){
        top = -669;
        mui('.mui-scroll-wrapper').scroll().scrollTo(0, -669,0);
      }
    }
    param['idCardPic1']=idCardPic1;
    var idCardPic2=$(".idCardPic2 .dataImg").attr("data");
    if(!idCardPic2){
      $(".idCardPic2").addClass("error");
      if(top == 0){
        top = -773;
        mui('.mui-scroll-wrapper').scroll().scrollTo(0, -773,0);
      }
    }
    param['idCardPic2']=idCardPic2;
    var shopName = $("input[name=shopName]").val();
    if(!shopName){
      $("input[name=shopName]").addClass("error");
      $("input[name=shopName]").next().show();
      if(top == 0){
        top = -880;
        mui('.mui-scroll-wrapper').scroll().scrollTo(0, -880,0);
      }
    }
    param['shopName']=shopName;
    var shopPic=$(".shopPic .dataImg").attr("data");
    if(!shopPic){
      $(".shopPic").addClass("error");
      if(top == 0){
        top = -975;
        mui('.mui-scroll-wrapper').scroll().scrollTo(0, -975,0);
      }
    }
    param['shopPic']=shopPic;
    var mobile = $("input[name=mobile]").val();
    if(!mobile){
      $("input[name=mobile]").addClass("error");
      $("input[name=mobile]").next().show();
      if(top == 0){
        top = -1081;
        mui('.mui-scroll-wrapper').scroll().scrollTo(0, -1081,0);
      }
    }
    param['mobile']=mobile;
    var weixinPic=$(".weixinPic .dataImg").attr("data");
    if(!weixinPic){
      $(".weixinPic").addClass("error");
      if(top == 0){
        top = -1183;
        mui('.mui-scroll-wrapper').scroll().scrollTo(0, -1183,0);
      }
    }
    param['weixinPic']=weixinPic;
    var provinceId=$(".provinceId").val();
    if(!provinceId){
      $(".provinceId").addClass("error");
      $(".provinceId").next().show();
      if(top == 0){
        top = -1300;
        mui('.mui-scroll-wrapper').scroll().scrollTo(0, -1300,0);
      }
    }else{
      param['provinceId']=provinceId;
      param['provinceName']=$.trim($(".provinceId option:selected").text());
      var provinceType = $(".provinceId option:selected").attr("data-type");
      var cityId=null;
      if(provinceType == 3){
        cityId=$(".cityId").val();
        if(!cityId){
          $(".cityId").addClass("error");
          $(".cityId").next().show();
          if(top == 0){
            top = -1300;
            mui('.mui-scroll-wrapper').scroll().scrollTo(0, -1300,0);
          }
        }
        param['cityId']=cityId;
        param['cityName']=$.trim($(".cityId option:selected").text());
      }else{
        cityId = provinceId;
        param['cityId']=provinceId;
        param['cityName']=param.provinceName;
      }
      var areaId=$(".areaId").val();
      if(!areaId){
        $(".areaId").addClass("error");
        $(".areaId").next().show();
        if(top == 0){
          top = -1300;
          mui('.mui-scroll-wrapper').scroll().scrollTo(0, -1300,0);
        }
      }
      param['areaId']=areaId;
      param['areaName']=$.trim($(".areaId option:selected").text());
    }
    var address = $("input[name=address]").val();
    if(!address){
      $("input[name=address]").addClass("error");
      $("input[name=address]").next().show();
      if(top == 0){
        top = -1300;
        mui('.mui-scroll-wrapper').scroll().scrollTo(0, -1300,0);
      }
    }
    param['address']=address;
    var businessLicense = $("input[name=businessLicense]").val();
    if(!businessLicense){
      $("input[name=businessLicense]").addClass("error");
      $("input[name=businessLicense]").next().show();
      if(top == 0){
        top = -1588;
        mui('.mui-scroll-wrapper').scroll().scrollTo(0, -1588,0);
      }
    }
    param['businessLicense']=businessLicense;
    var businessLicensePic=$(".businessLicensePic .dataImg").attr("data");
    if(!businessLicensePic){
      $(".businessLicensePic").addClass("error");
      if(top == 0){
        top = -1730;
        mui('.mui-scroll-wrapper').scroll().scrollTo(0, -1730,0);
      }
    }
    param['businessLicensePic']=businessLicensePic;
    var foodAllowanceLicense = $("input[name=foodAllowanceLicense]").val();
    if(!foodAllowanceLicense){
      $("input[name=foodAllowanceLicense]").addClass("error");
      $("input[name=foodAllowanceLicense]").next().show();
      if(top == 0){
        top = -1834;
        mui('.mui-scroll-wrapper').scroll().scrollTo(0, -1834,0);
      }
    }
    param['foodAllowanceLicense']=foodAllowanceLicense;
    var foodAllowanceLicensePic=$(".foodAllowanceLicensePic .dataImg").attr("data");
    if(!foodAllowanceLicensePic){
      $(".foodAllowanceLicensePic").addClass("error");
      if(top == 0){
        top = -1960;
        mui('.mui-scroll-wrapper').scroll().scrollTo(0, -1960,0);
      }
    }
    param['foodAllowanceLicensePic']=foodAllowanceLicensePic;
    var acreage = $("input[name=acreage]").val();
    if(!acreage){
      $("input[name=acreage]").addClass("error");
      $("input[name=acreage]").next().show();
      if(top == 0){
        top = -2082;
        mui('.mui-scroll-wrapper').scroll().scrollTo(0, -2082,0);
      }
    }
    param['acreage']=acreage;
    var bankCardName = $("input[name=bankCardName]").val();
    if(!bankCardName){
      $("input[name=bankCardName]").addClass("error");
      $("input[name=bankCardName]").next().show();
      if(top == 0){
        top = -2221;
        mui('.mui-scroll-wrapper').scroll().scrollTo(0, -2221,0);
      }
    }
    param['bankCardName']=bankCardName;
    var bankCardNo = $("input[name=bankCardNo]").val();
    if(!bankCardNo){
      $("input[name=bankCardNo]").addClass("error");
      $("input[name=bankCardNo]").next().show();
      if(top == 0){
        top = -2221;
        mui('.mui-scroll-wrapper').scroll().scrollTo(0, -2221,0);
      }
    }
    param['bankCardNo']=bankCardNo;
    var bankAddress = $("input[name=bankAddress]").val();
    if(!bankAddress){
      $("input[name=bankAddress]").addClass("error");
      $("input[name=bankAddress]").next().show();
      if(top == 0){
        top = -2221;
        mui('.mui-scroll-wrapper').scroll().scrollTo(0, -2221,0);
      }
    }
    param['bankAddress']=bankAddress;
    for(var i in param){
      if(!param[i]){
        return false;
      }
    }
    $(this).addClass("mui-disabled");
    $(this).off();
    ajax({
      url: '/api/distributorApply/save',
      data: param,
      success: function(data){
        document.location.href="applysuccess.html";
      }
    });
  });
  
  $("input[type=text],input[type=number],select").on("input", function(){
    $(this).removeClass("error");
    $(this).next().hide();
  });
  ajax({
    url: '/api/getAreasByParentId',
    data: {'parentId': 0},
    success: function(items){
      for(var i in items){
        var item = items[i];
        if(item.status==1){
          $(".provinceId").append('<option value="'+item.areaId+'" data-type="'+item.type+'">'+item.shortName+'</option>');
        }
      }
    }
  });
  $(".provinceId").change(function(){
    var val=$(this).val();
    var type=$(".provinceId option:selected").attr("data-type");
    $(".cityId option:not(:first)").remove();
    $(".areaId option:not(:first)").remove();
    if(val){
      var oo = null
      if(type==3){ //省
        $(".areaId").removeClass("mui-hidden");
        $(".cityId").removeClass("mui-hidden");
        oo = $(".cityId");
      }else if(type < 3){ //直辖市 港澳台
        $(".cityId").addClass("mui-hidden");
        $(".areaId").removeClass("mui-hidden");
        oo = $(".areaId");
      }
      ajax({
        url: '/api/getAreasByParentId',
        data: {'parentId': val},
        success: function(items){
          for(var i in items){
            var item = items[i];
            if(item.status==1){
              var name = item.shortName ? item.shortName : item.areaName;
              oo.append('<option value="'+item.areaId+'">'+name+'</option>');
            }
          }
        }
      });
    }
  });
  $(".cityId").change(function(){
    var val=$(this).val();
    $(".areaId option:not(:first)").remove();
    ajax({
      url: '/api/getAreasByParentId',
      data: {'parentId': val},
      success: function(items){
        for(var i in items){
          var item = items[i];
          if(item.status==1){
            var name = item.shortName ? item.shortName : item.areaName;
            $(".areaId").append('<option value="'+item.areaId+'">'+name+'</option>');
          }
        }
      }
    });
  });
  
  new UploadHandle({
    basePath: SERVICE_ADDRESS,
    uploadImages:{uploadFileId: 'uploadImage', multiple: true, items: [{
      data: {"mark1": 1},
      uploadBtn: $('.idCardPic1'), 
      success: function (data, textStatus) {
        if(data.code==0){
          $(".idCardPic1").removeClass("error");
          $('.idCardPic1 .select-text').hide(); 
          for(var o in data.data){
            var pic = data.data[o].original;
            if($('.idCardPic1 span').length>0){
              $('.idCardPic1 span').remove();
            }
            $('.idCardPic1').append('<span><img class="dataImg" height="35px" src="'+IMAGE_PREFIX+pic+'" data="'+pic+'"><span style="float:right">点击可更换图片</span></span>'); 
          }
        }else{
          artDialog.alert(data.msg);
        }
      },
      complete: function (XMLHttpRequest, textStatus) {
        $('.dataImg').off().on('tap', function(){
          var imgSrc=$(this).attr("src");
          mask.show();//关闭：mask.close();
          if($('#viewImg').length>0){ 
            $('#viewImg').remove(); 
          } 
          $('body').append('<div id="viewImg" class="center">\
              <img src="'+imgSrc+'" width="100%">\
              </div>');
          $('#viewImg').off().on('tap', function(){
            mask.close();
            $(this).remove();
          });
          return false;
        }); 
      } 
    },{
      data: {"mark1": 1},
      uploadBtn: $('.idCardPic2'), 
      success: function (data, textStatus) {
        if(data.code==0){
          $(".idCardPic2").removeClass("error");
          $('.idCardPic2 .select-text').hide(); 
          for(var o in data.data){
            var pic = data.data[o].original;
            if($('.idCardPic2 span').length>0){
              $('.idCardPic2 span').remove();
            }
            $('.idCardPic2').append('<span><img class="dataImg" height="35px" src="'+IMAGE_PREFIX+pic+'" data="'+pic+'"><span style="float:right">点击可更换图片</span></span>'); 
          }
        }else{
          artDialog.alert(data.msg);
        }
      },
      complete: function (XMLHttpRequest, textStatus) {
        $('.dataImg').off().on('tap', function(){
          var imgSrc=$(this).attr("src");
          mask.show();//关闭：mask.close();
          if($('#viewImg').length>0){ 
            $('#viewImg').remove(); 
          } 
          $('body').append('<div id="viewImg" class="center">\
              <img src="'+imgSrc+'" width="100%">\
              </div>');
          $('#viewImg').off().on('tap', function(){
            mask.close();
            $(this).remove();
          });
          return false;
        }); 
      } 
    },{
      data: {"mark1": 1},
      uploadBtn: $('.shopPic'), 
      success: function (data, textStatus) {
        if(data.code==0){
          $(".shopPic").removeClass("error");
          $('.shopPic .select-text').hide(); 
          for(var o in data.data){
            var pic = data.data[o].original;
            if($('.shopPic span').length>0){
              $('.shopPic span').remove();
            }
            $('.shopPic').append('<span><img class="dataImg" height="35px" src="'+IMAGE_PREFIX+pic+'" data="'+pic+'"><span style="float:right">点击可更换图片</span></span>'); 
          }
        }else{
          artDialog.alert(data.msg);
        }
      },
      complete: function (XMLHttpRequest, textStatus) {
        $('.dataImg').off().on('tap', function(){
          var imgSrc=$(this).attr("src");
          mask.show();//关闭：mask.close();
          if($('#viewImg').length>0){ 
            $('#viewImg').remove(); 
          } 
          $('body').append('<div id="viewImg" class="center">\
              <img src="'+imgSrc+'" width="100%">\
              </div>');
          $('#viewImg').off().on('tap', function(){
            mask.close();
            $(this).remove();
          });
          return false;
        }); 
      } 
    },{
      data: {"mark1": 1},
      uploadBtn: $('.weixinPic'), 
      success: function (data, textStatus) {
        if(data.code==0){
          $(".weixinPic").removeClass("error");
          $('.weixinPic .select-text').hide(); 
          for(var o in data.data){
            var pic = data.data[o].original;
            if($('.weixinPic span').length>0){
              $('.weixinPic span').remove();
            }
            $('.weixinPic').append('<span><img class="dataImg" height="35px" src="'+IMAGE_PREFIX+pic+'" data="'+pic+'"><span style="float:right">点击可更换图片</span></span>'); 
          }
        }else{
          artDialog.alert(data.msg);
        }
      },
      complete: function (XMLHttpRequest, textStatus) {
        $('.dataImg').off().on('tap', function(){
          var imgSrc=$(this).attr("src");
          mask.show();//关闭：mask.close();
          if($('#viewImg').length>0){ 
            $('#viewImg').remove(); 
          } 
          $('body').append('<div id="viewImg" class="center">\
              <img src="'+imgSrc+'" width="100%">\
              </div>');
          $('#viewImg').off().on('tap', function(){
            mask.close();
            $(this).remove();
          });
          return false;
        }); 
      } 
    },{
      data: {"mark1": 1},
      uploadBtn: $('.businessLicensePic'), 
      success: function (data, textStatus) {
        if(data.code==0){
          $(".businessLicensePic").removeClass("error");
          $('.businessLicensePic .select-text').hide(); 
          for(var o in data.data){
            var pic = data.data[o].original;
            if($('.businessLicensePic span').length>0){
              $('.businessLicensePic span').remove();
            }
            $('.businessLicensePic').append('<span><img class="dataImg" height="35px" src="'+IMAGE_PREFIX+pic+'" data="'+pic+'"><span style="float:right">点击可更换图片</span></span>'); 
          }
        }else{
          artDialog.alert(data.msg);
        }
      },
      complete: function (XMLHttpRequest, textStatus) {
        $('.dataImg').off().on('tap', function(){
          var imgSrc=$(this).attr("src");
          mask.show();//关闭：mask.close();
          if($('#viewImg').length>0){ 
            $('#viewImg').remove(); 
          } 
          $('body').append('<div id="viewImg" class="center">\
              <img src="'+imgSrc+'" width="100%">\
              </div>');
          $('#viewImg').off().on('tap', function(){
            mask.close();
            $(this).remove();
          });
          return false;
        }); 
      } 
    },{
      data: {"mark1": 1},
      uploadBtn: $('.foodAllowanceLicensePic'), 
      success: function (data, textStatus) {
        if(data.code==0){
          $(".foodAllowanceLicensePic").removeClass("error");
          $('.foodAllowanceLicensePic .select-text').hide(); 
          for(var o in data.data){
            var pic = data.data[o].original;
            if($('.foodAllowanceLicensePic span').length>0){
              $('.foodAllowanceLicensePic span').remove();
            }
            $('.foodAllowanceLicensePic').append('<span><img class="dataImg" height="35px" src="'+IMAGE_PREFIX+pic+'" data="'+pic+'"><span style="float:right">点击可更换图片</span></span>'); 
          }
        }else{
          artDialog.alert(data.msg);
        }
      },
      complete: function (XMLHttpRequest, textStatus) {
        $('.dataImg').off().on('tap', function(){
          var imgSrc=$(this).attr("src");
          mask.show();//关闭：mask.close();
          if($('#viewImg').length>0){ 
            $('#viewImg').remove(); 
          } 
          $('body').append('<div id="viewImg" class="center">\
              <img src="'+imgSrc+'" width="100%">\
              </div>');
          $('#viewImg').off().on('tap', function(){
            mask.close();
            $(this).remove();
          });
          return false;
        }); 
      } 
    }]
    }
  },{});
});

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