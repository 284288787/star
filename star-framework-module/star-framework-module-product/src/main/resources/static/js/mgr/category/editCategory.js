var basePath="/";
var parentParams=artDialog.data('params');
var screenHeight = parentParams.getScreenHeight();
$(function(){
  new UtilsHandle({
    basePath: "/",
    uploadImages:{uploadFileId: 'uploadImage', multiple: false, items: [{
      data: {"mark": 0},
      uploadBtn: $('#uploadPicBtn'), 
      success: function (data, textStatus) {
        if(data.code==0){
          $('#productPicturesDiv').show();
          for(var o in data.data){
            var pic = data.data[o].original;
            $('#productPicturesDiv').html('<span style="position: relative;"><img class="dataImg" width="70px" height="70px" src="'+pic+'" data="'+pic+'"><div class="close">X</div></span>'); 
          }
        }else{
          artDialog.alert(data.msg);
        }
      },
      complete: function (XMLHttpRequest, textStatus) {
        $('.close').unbind().click(function(){
          $(this).parent().remove();
        }); 
        $('.dataImg').unbind().click(function(){
          var img = new Image();
          img.src = $(this).attr("src");
          img.onload = function(){
            var w=img.width;
            var h=img.height;
            var l = w / h;
            if(h > screenHeight * 0.85){
              h = screenHeight * 0.85;
              w = h * l;
            }
            artDialog.alert2('<div style="width:'+w+'px;height:'+h+'px;"><img style="height:'+h+'px" src="'+img.src+'">') 
          }
        }); 
      } 
    }]
    }
  },{});
  $("#editCategoryForm").validate({
    rules: {
      cateName: {
        required: true,
        zhengze: ".{2,25}"
      }
    },
    messages: {
      cateName: {
        required: "必填",
        zhengze: "长度在2至25个字"
      }
    }
  });
  
  $("#saveBtn").click(function(){
    var flag = $("#editCategoryForm").valid();
    if(! flag) return;
    var data=$("#editCategoryForm").serializeArray();
    var params = {};
    $.each(data, function(i, field){
      var name = field.name;
      params[name] = field.value;
      console.log(name + "  " + field.value)
    });
    var catePic=$(".dataImg").attr("src");
    params['catePic'] = catePic;
    $("#saveBtn").attr("disabled", true);
    $.ajax({
      contentType: "application/json",
      url: basePath+"category/edit",
      data: JSON.stringify(params),
      type: 'post',
      dataType: 'json',
      success: function(res){
        if(res.code==0){
          parentParams.query();
          art.dialog.close();
        }else{
          artDialog.alert(res.msg)
        }
        $("#saveBtn").removeAttr("disabled");
      }
    });
  });
});