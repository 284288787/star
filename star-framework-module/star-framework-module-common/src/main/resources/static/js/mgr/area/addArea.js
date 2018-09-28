var basePath="/";
var parentParams=artDialog.data('params');
$(function(){
  $("input[name=parentId]").val(parentParams.parentAreaId);
	$("#editAreaForm").validate({
		rules: {
		  areaName: {
		    required: true,
		    rangelength: [2, 25]
		  },
		  pinyin: {
		    required: true,
		    rangelength: [2, 100]
		  },
		  py: {
		    required: true,
		    rangelength: [2, 20]
		  },
		  shortName: {
				required: true,
				rangelength: [2, 25]
			},
		},
		messages: {
		  areaName: {
				required: "必填",
				rangelength: "2至25个字内"
			},
			pinyin: {
				required: "必填",
				rangelength: "2至100个字母",
			},
			py: {
				required: "必填",
				rangelength: "2至20个字母",
			},
			shortName: {
        required: "必填",
        rangelength: "2至25个字内"
      }
		}
	});
	
	$("#saveBtn").click(function(){
		var flag = $("#editAreaForm").valid();
		if(! flag) return;
		var data=$("#editAreaForm").serializeArray();
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
			url: basePath+"area/add",
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