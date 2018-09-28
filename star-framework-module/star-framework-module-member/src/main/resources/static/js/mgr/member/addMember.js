var basePath="/";
var parentParams=artDialog.data('params');
$(function(){
  $("#editMemberForm").validate({
    rules: {
      head: {
        required: 
      }
    },
    messages: {
      head: {
        required: ""
      }
    }
  });
  
  $("#saveBtn").click(function(){
    var flag = $("#editMemberForm").valid();
    if(! flag) return;
    var data=$("#editMemberForm").serializeArray();
    var params = {};
    $.each(data, function(i, field){
      var name = field.name;
      params[name] = field.value;
      console.log(name + "  " + field.value)
    });
    $("#saveBtn").attr("disabled", true);
    $.ajax({
      url: basePath+"member/add",
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