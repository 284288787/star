var basePath = "/";
var parentParams = artDialog.data('params');
$(function() {
  $("#editRoleForm").validate({
    rules : {
      roleRemark : {
        required : true,
      },
      roleName : {
        required : true,
      },
      roleIntro : {
        required : true,
      }
    },
    messages : {
      roleRemark : {
        required : "必填",
      },
      roleName : {
        required : "必填",
      },
      roleIntro : {
        required : "必填",
      }
    }
  });

  $("#saveBtn").click(function() {
    var flag = $("#editRoleForm").valid();
    if (!flag)
      return;
    var data = $("#editRoleForm").serializeArray();
    var params = {};
    $.each(data, function(i, field) {
      var name = field.name;
      params[name] = field.value;
      console.log(name + "  " + field.value)
    });
    $("#saveBtn").attr("disabled", true);
    $.ajax({
      url : basePath + "role/add",
      data : params,
      type : 'post',
      dataType : 'json',
      success : function(res) {
        if (res.code == 0) {
          parentParams.query();
          art.dialog.close();
        } else {
          artDialog.alert(res.msg)
        }
        $("#saveBtn").removeAttr("disabled");
      }
    });
  });
});