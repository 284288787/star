var basePath="/";
var parentParams=artDialog.data('params');
$(function(){
	$("#editResourceForm").validate({
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
	$.ajax({
		url: basePath+'resource/resources',
		data: {'parentId': parentParams.parentNode.id},
		type: 'post',
		dataType: 'json',
		success: function(res){
			var idx = 1;
			if(res.length>0){
				idx = res[res.length-1].idx + 1;
			}
			html = '<option value="'+idx+'">放在最后</option>';
			for(var o in res){
				html+='<option value="'+res[o].idx+'_1">'+res[o].sourceName+'之前</option>';
				//html+='<option value="'+res[o].idx+'_2">'+res[o].sourceName+'之后</option>';
			}
			$("select[name=idxstr]").html(html);
		}
	});
	
	$("#saveBtn").click(function(){
		var flag = $("#editResourceForm").valid();
		if(! flag) return;
		var data=$("#editResourceForm").serializeArray();
		var params = {};
		$.each(data, function(i, field){
			var name = field.name;
			params[name] = field.value;
			console.log(name + "  " + field.value)
		});
		params['parentId']=parentParams.parentNode.id;
		$("#saveBtn").attr("disabled", true);
		$.ajax({
			url: basePath+"resource/add",
			data: params,
			type: 'post',
			dataType: 'json',
			success: function(res){
				if(res.code==0){
					var n=$.trim($("select[name=idxstr] option:selected").text());
					var nodes = parentParams.zTree.getNodesByParam("name", n.substr(0,n.length-2), parentParams.parentNode);
					var nodeIndex = parentParams.zTree.getNodeIndex(nodes[0]);
					var index = n.indexOf("之前")!=-1 ? nodeIndex : nodeIndex + 1;
					var newns = parentParams.zTree.addNodes(parentParams.parentNode, index, {id:res.data.toFixed(0), pId:parentParams.parentNode.id, name: params.sourceName});
					parentParams.zTree.selectNode(newns[0], false, true);
					art.dialog.close();
				}else{
					artDialog.alert(res.msg)
				}
				$("#saveBtn").removeAttr("disabled");
			}
		});
	});
});