var basePath="/";
var parentParams=artDialog.data('params');
$(function(){
  $("#editProductSubscriptionForm").validate({
    rules: {
    },
    messages: {
    }
  });
  
  $("#saveBtn").click(function(){
    var flag = $("#editProductSubscriptionForm").valid();
    if(! flag) return;
    var data=$("#editProductSubscriptionForm").serializeArray();
    var params = {};
    $.each(data, function(i, field){
      var name = field.name;
      params[name] = field.value;
      console.log(name + "  " + field.value)
    });
    $("#saveBtn").attr("disabled", true);
    $.ajax({
      url: basePath+"productSubscription/edit",
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