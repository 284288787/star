var parentParams=artDialog.data('params');
//var parentParams={roleId:9}
var setting = {
	async: {
		enable: true,
		url:"/role/mainUri/" + parentParams.roleId,
		autoParam:["id=parentId", "type", "isLeaf"]
	},
	view: {
		selectedMulti: false
	},
	check: {
		enable: true,
		chkStyle: "radio",
		radioType: "level"
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
	var params={'roleId': parentParams.roleId, 'status': treeNode.checked, 'sourceId': treeNode.pId, 'uri': treeNode.uri};
	$.ajax({
		url: '/role/setMainUri',
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