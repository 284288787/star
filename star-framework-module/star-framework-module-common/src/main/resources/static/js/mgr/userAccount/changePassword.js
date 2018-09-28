var basePath="/";
$(function(){
	var args=artDialog.data("params");
	$("#userId").val(args.userId);
	$("#editUserInfoForm").validate({
		rules: {
			userId: {
				required: true,
			},
			password: {
				required: true,
				rangelength: [5, 32]
			},
			password2: {
				required: true,
				rangelength: [5, 32],
				equalTo: "#password"
			}
		},
		messages: {
			userId: {
				required: '必填'
			},
			password: {
				required: "必填",
				rangelength: "5至32位"
			},
			password2: {
				required: "必填",
				rangelength: "5至32位",
				equalTo: "密码必须一致"
			}
		}
	});
	
	$("#saveBtn").click(function(){
		var flag = $("#editUserInfoForm").valid();
		if(! flag) return;
		var data=$("#editUserInfoForm").serializeArray();
		var params = {};
		$.each(data, function(i, field){
			var name = field.name;
			params[name] = field.value;
			console.log(name + "  " + field.value)
		});
		$.ajax({
			url: basePath+"userAccount/changePassword",
			data: params,
			type: 'post',
			dataType: 'json',
			success: function(res){
				if(res.code == 0){
					art.dialog.close();
					artDialog.alert("修改成功");
				}else{
					artDialog.alert(res.msg);
				}
			}
		});
	});
});