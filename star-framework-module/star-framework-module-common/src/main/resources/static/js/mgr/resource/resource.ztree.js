var setting = {
	async: {
		enable: true,
		url:"/resource/resources",
		autoParam:["id=parentId"],
		dataFilter: filter
	},
	view: {
		addHoverDom: addHoverDom,
		removeHoverDom: removeHoverDom,
		selectedMulti: false
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
		onClick: onClick
	}
};
var log, className = "dark"; canHidden = false;
function beforeDrag(treeId, treeNodes) {
	return false;
}
function filter(treeId, parentNode, childNodes) {
	if (childNodes.length==0){
		parentNode.isParent=false;
		return null;
	}
	var nodes = new Array();
	for (var i=0, l=childNodes.length; i<l; i++) {
		var isParent = childNodes[i].childNum > 0;
		var iconSkin = null == parentNode ? 'pIcon01' : isParent ? 'pIcon02' : 'icon03';
		nodes[i] = {'id':childNodes[i].sourceId.toFixed(0), 'pId':childNodes[i].parentId.toFixed(0), 'name': childNodes[i].sourceName, 'isParent': isParent, 'open': isParent};
	}
	return nodes;
}
function onClick(event, treeId, treeNode, clickFlag){
	if(!treeNode.isParent){
		uriHandle.initLazy();
		uriHandle.options.sourceId = treeNode.id;
		uriHandle.options.sourceName = treeNode.name;
		uriHandle.queryByParams({});
		uriHandle.setCaption("指定[" + treeNode.name + "]的Uri");
		//$(".menuUri").show();
		$("table.ztree .data-container").show();
		canHidden = true;
	}else{
		if(canHidden){
			//$(".menuUri").hide();
			$("table.ztree .data-container").hide();
		}
	}
}

var newCount = 1;
function addHoverDom(treeId, treeNode) {
	var sObj = $("#" + treeNode.tId + "_span");
	if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
	var addStr = "<span class='button add' id='addBtn_" + treeNode.tId + "' title='新增菜单' onfocus='this.blur();'></span>";
	addStr += '<span class="button edit" id="editBtn_'+treeNode.tId+'" title="编辑菜单" onfocus="this.blur();"></span>';
	if(!treeNode.isParent)
	addStr += '<span class="button remove" id="removeBtn_'+treeNode.tId+'" title="删除菜单" onfocus="this.blur();"></span>';
	sObj.after(addStr);
	var btn = $("#addBtn_"+treeNode.tId);
	if (btn) btn.bind("click", function(){
		var zTree = $.fn.zTree.getZTreeObj(treeId);
		artDialog.data("params", {'parentNode': treeNode, 'zTree': zTree});
		artDialog.open("/common/mgr/resource/addResource",{
			title: "新增["+treeNode.name+"]的子菜单",
			width : "400px",
			height: "300px",
			drag:true,
			resize:false,
			lock:true
		});
		return false;
	});
	var editBtn = $("#editBtn_"+treeNode.tId);
	if (editBtn) editBtn.bind("click", function(){
		var zTree = $.fn.zTree.getZTreeObj(treeId);
		artDialog.data("params", {'parentNode': treeNode.getParentNode(), 'zTree': zTree});
		artDialog.open("/resource/editBefore/"+treeNode.id,{
			title: "编辑["+treeNode.name+"]的子菜单["+treeNode.name+"]",
			width : "400px",
			height: "300px",
			drag:true,
			resize:false,
			lock:true
		});
		return false;
	});
	var removeBtn = $("#removeBtn_"+treeNode.tId);
	if (removeBtn) removeBtn.bind("click", function(){
		var zTree = $.fn.zTree.getZTreeObj(treeId);
		artDialog.confirm("确认删除["+treeNode.name+"]？", function(){
			$.ajax({
				url: "/resource/delete/"+treeNode.id,
				type: 'post',
				dataType: 'json',
				success: function(res){
					if(res.code==0){
						zTree.removeNode(treeNode);
					}else{
						artDialog.alert(res.msg)
					}
				}
			});
		});
		return false;
	});
};
function removeHoverDom(treeId, treeNode) {
	$("#addBtn_"+treeNode.tId).unbind().remove();
	$("#editBtn_"+treeNode.tId).unbind().remove();
	$("#removeBtn_"+treeNode.tId).unbind().remove();
};

$(document).ready(function(){
	$.fn.zTree.init($("#resourceTree"), setting);
	$("ul.ztree").css("height", (uriHandle.getScreenHeight() - 20) + "px");
	uriHandle.config.height = uriHandle.getScreenHeight() - 96;
	
//	$(".menuUriEnter").click(function(){
//		var sourceId = uriHandle.options.sourceId;
//		var uri = $("input[name=menuUri]").val();
//		
//	});
});