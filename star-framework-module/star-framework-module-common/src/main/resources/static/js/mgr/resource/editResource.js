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
			var sourceId=$("input[name=sourceId]").val();
			var sel=false;
			var idx = 1;
			if(res.length>0){
				idx = res[res.length-1].idx + 1;
			}
			html = '<option value="'+idx+'">放在最后</option>';
			
			for(var o in res){
				if(res[o].sourceId.toFixed(0) != sourceId){
					html+='<option value="'+res[o].idx+'_1" '+(sel ? 'selected' : '')+' >'+res[o].sourceName+'之前</option>';
					sel = false;
				}else{
					sel = true;
				}
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
		$("#saveBtn").attr("disabled", true);
		$.ajax({
			url: basePath+"resource/edit",
			data: params,
			type: 'post',
			dataType: 'json',
			success: function(res){
				if(res.code==0){
					parentParams.zTree.reAsyncChildNodes(parentParams.parentNode, "refresh", false, function(){
						var nodes = parentParams.zTree.getNodesByParam("name", params.sourceName, parentParams.parentNode);
						parentParams.zTree.selectNode(nodes[0], false, true);
					});
					art.dialog.close();
				}else{
					artDialog.alert(res.msg)
				}
				$("#saveBtn").removeAttr("disabled");
			}
		});
	});
});