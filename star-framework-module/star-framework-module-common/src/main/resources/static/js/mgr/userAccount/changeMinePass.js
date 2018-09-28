var basePath="/";
$(function(){
	$("#editPasswordForm").validate({
		rules: {
			oldpass: {
				required: true,
			},
			newpass: {
				required: true,
			},
			newpass2: {
				required: true,
				rangelength: [5, 32],
				equalTo: "#newpass"
			}
		},
		messages: {
			oldpass: {
				required: '必填'
			},
			newpass: {
				required: "必填",
			},
			newpass2: {
				required: "必填",
				equalTo: "密码必须一致"
			}
		}
	});
	
	$("#saveBtn").click(function(){
		var flag = $("#editPasswordForm").valid();
		if(! flag) return;
		var data=$("#editPasswordForm").serializeArray();
		var params = {};
		$.each(data, function(i, field){
			var name = field.name;
			params[name] = field.value;
			console.log(name + "  " + field.value)
		});
		$.ajax({
			url: basePath+"changeMinePass",
			data: params,
			type: 'post',
			dataType: 'json',
			success: function(res){
				if(res.code==0){
					art.dialog.close();
					artDialog.alert("修改成功");
				}else{
					artDialog.alert(res.msg);
				}
			}
		});
	});
});