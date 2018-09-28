var parentParams=artDialog.data('params');
//var parentParams={roleId:9}
var setting = {
	async: {
		enable: true,
		url:"/directory/dirlist",
		autoParam:["path=parent"]
	},
	view: {
		selectedMulti: false
	},
	data: {
		simpleData: {
			enable: true
		}
	},
	callback: {
		beforeDrag: beforeDrag,
	}
};
var log, className = "dark";
function beforeDrag(treeId, treeNodes) {
	return false;
}

$(document).ready(function(){
	$.fn.zTree.init($("#dir"), setting);
	var zTree = $.fn.zTree.getZTreeObj("dir");
	$("#thisDir").click(function(){
		var nodes = zTree.getSelectedNodes();
		if(nodes.length==0){
			artDialog.alert("请选择文件夹");
		}else{
			node = nodes[0];
			alert(node.path);
		}
	});
	$("#newDir").click(function(){
		var nodes = zTree.getSelectedNodes();
		if(nodes.length==0){
			artDialog.alert("请选择文件夹");
			return;
		}
		node = nodes[0];
		artDialog.prompt("请输入文件夹名称", function(value, here){
			if(value){
				var reg = new RegExp("^[a-zA-Z0-9_-]+$");
				if(! reg.test(value)){
					artDialog.alert("文件夹["+value+"]名称不满足命名规范.<br>规范：字母、数字、下划线、减号");
					return false;
				}
				var ns = zTree.getNodesByParam("name", value, node);
				if(ns.length > 0){
					artDialog.alert("文件夹["+value+"]已经存在");
					return false;
				}
				var path = node.path + value;
				alert(path)
			}
		});
	});
});