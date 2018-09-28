var basePath="/";
var parentParams=artDialog.data('params');
$(function(){
  $("#editProductInventoryForm").validate({
    rules: {
      productId: {
        required: true
      },
      number: {
        required: true,
        zhengze: "number"
      }
    },
    messages: {
      productId: {
        required: "必填"
      },
      number: {
        required: "必填",
        zhengze: "请输入正整数"
      }
    }
  });
  
  $("#saveBtn").click(function(){
    var flag = $("#editProductInventoryForm").valid();
    if(! flag) return;
    var data=$("#editProductInventoryForm").serializeArray();
    var params = {};
    $.each(data, function(i, field){
      var name = field.name;
      params[name] = field.value;
      console.log(name + "  " + field.value)
    });
    $("#saveBtn").attr("disabled", true);
    $.ajax({
      url: basePath+"productInventory/edit",
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