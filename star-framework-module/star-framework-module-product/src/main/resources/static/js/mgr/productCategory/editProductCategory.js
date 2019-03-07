var basePath="/";
var parentParams=artDialog.data('params');
$(function(){
  $("#editProductCategoryForm").validate({
    rules: {
      productCateName: {
        required: true,
        zhengze: ".{2,25}"
      }
    },
    messages: {
      productCateName: {
        required: "必填",
        zhengze: "长度在2至25个字"
      }
    }
  });
  
  $("#saveBtn").click(function(){
    var flag = $("#editProductCategoryForm").valid();
    if(! flag) return;
    var data=$("#editProductCategoryForm").serializeArray();
    var params = {};
    $.each(data, function(i, field){
      var name = field.name;
      params[name] = field.value;
      console.log(name + "  " + field.value)
    });
    $("#saveBtn").attr("disabled", true);
    $.ajax({
      contentType: "application/json",
      url: basePath+"productCategory/edit",
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