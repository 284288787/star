var basePath="/";
var parentParams=artDialog.data('params');
$(function(){
  $("#editCouponRelationForm").validate({
    rules: {
      businessType: {
        required: true
      }
    },
    messages: {
      businessType: {
        required: "必选"
      }
    }
  });
  
  $("#saveBtn").click(function(){
    var flag = $("#editCouponRelationForm").valid();
    if(! flag) return;
    var data=$("#editCouponRelationForm").serializeArray();
    var params = {};
    $.each(data, function(i, field){
      var name = field.name;
      params[name] = field.value;
      console.log(name + "  " + field.value)
    });
    $("#saveBtn").attr("disabled", true);
    $.ajax({
      contentType: "application/json",
      url: basePath+"couponRelation/add",
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