var basePath="/";
var parentParams=artDialog.data('params');
$(function(){
	$("#editUserAccountForm").validate({
		rules: {
			password: {
				required: true,
				rangelength: [5, 32]
			},
			password2: {
				required: true,
				rangelength: [5, 32],
				equalTo: "#password"
			},
			account: {
				required: true,
				account: true
			}
		},
		messages: {
			password: {
				required: "必填",
				rangelength: "5至32位"
			},
			password2: {
				required: "必填",
				rangelength: "5至32位",
				equalTo: "密码必须一致"
			},
			account: {
				required: "必填",
			}
		}
	});
	
	$("#saveBtn").click(function(){
		var flag = $("#editUserAccountForm").valid();
		if(! flag) return;
		var data=$("#editUserAccountForm").serializeArray();
		var params = {};
		$.each(data, function(i, field){
			var name = field.name;
			params[name] = field.value;
			console.log(name + "  " + field.value)
		});
		params["userType"] = "userInfoService";
		params["typeName"] = "后台用户";
		$("#saveBtn").attr("disabled", true);
		$.ajax({
			url: basePath+"userAccount/add",
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