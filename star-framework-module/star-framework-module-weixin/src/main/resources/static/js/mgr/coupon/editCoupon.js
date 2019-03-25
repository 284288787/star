var basePath="/";
var parentParams=artDialog.data('params');
$(function(){
  $("#editCouponForm").validate({
    rules: {
      title: {
        required: true,
        zhengze: ".*{1,10}"
      },
      cardId: {
        required: true,
        zhengze: ".*{20,40}"
      }
    },
    messages: {
      title: {
        required: "必填",
        zhengze: "长度在1至10个字"
      },
      cardId: {
        required: "必填",
        zhengze: "长度在20至40个字符"
      }
    }
  });
  
  $("#saveBtn").click(function(){
    var flag = $("#editCouponForm").valid();
    if(! flag) return;
    var data=$("#editCouponForm").serializeArray();
    var params = {};
    $.each(data, function(i, field){
      var name = field.name;
      params[name] = field.value;
      console.log(name + "  " + field.value)
    });
    $("#saveBtn").attr("disabled", true);
    $.ajax({
      contentType: "application/json",
      url: basePath+"coupon/edit",
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