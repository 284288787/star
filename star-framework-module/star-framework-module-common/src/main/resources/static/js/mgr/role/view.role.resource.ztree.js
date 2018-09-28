var parentParams=artDialog.data('params');
//var parentParams={roleId:9}
var setting = {
	async: {
		enable: true,
		url:"/role/viewResource/" + parentParams.roleId,
		autoParam:["id=parentId", "type", "isLeaf"]
	},
	view: {
		selectedMulti: false,
		nameIsHTML: true
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
		}
	}
};
var log, className = "dark";
function beforeDrag(treeId, treeNodes) {
	return false;
}

$(document).ready(function(){
	$.fn.zTree.init($("#allResourceTree"), setting);
});