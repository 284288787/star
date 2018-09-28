var basePath="/";
var parentParams=artDialog.data('params');
$(function(){
	$("#editUserInfoForm").validate({
		rules: {
			name: {
				required: true,
				rangelength: [1, 10]
			},
			mobile: {
				required: true,
				mobile: true
			}
		},
		messages: {
			name: {
				required: "必填",
				rangelength: "1至10个字"
			},
			mobile: {
				required: "必填",
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
		$("#saveBtn").attr("disabled", true);
		$.ajax({
			url: basePath+"userInfo/edit",
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