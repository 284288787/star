var basePath="/";
var parentParams=artDialog.data('params');
$(function(){
  $("#editProductPictureForm").validate({
    rules: {
      productId: {
        required: true
      },
      type: {
        required: true
      },
      url: {
        required: true
      }
    },
    messages: {
      productId: {
        required: "必填"
      },
      type: {
        required: "必选"
      },
      url: {
        required: "必填"
      }
    }
  });
  
  $("#saveBtn").click(function(){
    var flag = $("#editProductPictureForm").valid();
    if(! flag) return;
    var data=$("#editProductPictureForm").serializeArray();
    var params = {};
    $.each(data, function(i, field){
      var name = field.name;
      params[name] = field.value;
      console.log(name + "  " + field.value)
    });
    $("#saveBtn").attr("disabled", true);
    $.ajax({
      url: basePath+"productPicture/add",
      data: params,
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