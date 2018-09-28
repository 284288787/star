var parentParams=artDialog.data('params');
//var parentParams={roleId:9}
var setting = {
	async: {
		enable: true,
		url:"/role/resources/" + parentParams.roleId,
		autoParam:["id=parentId", "type", "isLeaf"]
	},
	view: {
		selectedMulti: false
	},
	check: {
		enable: true,
		chkboxType: { "Y":"", "N":""}
	},
	data: {
		simpleData: {
			enable: true
		}
	},
	callback: {
		beforeDrag: beforeDrag,
		onNodeCreated: function(event, treeId, treeNode){
			//if(treeNode.id==1){
				var zTree = $.fn.zTree.getZTreeObj(treeId);
				zTree.reAsyncChildNodes(treeNode, "refresh", false);
			//}
		},
		onCheck: onCheck
	}
};
var log, className = "dark";
function beforeDrag(treeId, treeNodes) {
	return false;
}

function onCheck(event, treeId, treeNode){
	var params={'roleId': parentParams.roleId, 'status': treeNode.checked};
	if(treeNode.type=='resource'){
		params['sourceId'] = treeNode.id;
		params['uri'] = '/,/bui/**,/common/mgr/**,/changeMinePass';
	}else{
		params['sourceId'] = treeNode.pId;
		params['uri'] = treeNode.uri;
	}
	$.ajax({
		url: '/role/saveRoleResourceRelation',
		data: params,
		type: 'post',
		dataType: 'json',
		success: function(res){
			if(res.code!=0){
				artDialog.alert(res.msg);
			}
		}
	});
}

$(document).ready(function(){
	$.fn.zTree.init($("#allResourceTree"), setting);
});