var setting = {
	async: {
		enable: true,
		url:"/distributor/tree",
		autoParam:["id=parentDistributorId"],
		dataFilter: filter
	},
	view: {
	  expandSpeed:"",
		addHoverDom: addHoverDom,
		removeHoverDom: removeHoverDom,
		selectedMulti: false
	},
	edit: {
	  drag: {
      autoExpandTrigger: true,
      isCopy: false,
      prev: dropPrev,
      inner: dropInner,
      next: dropNext
    },
    enable: true,
    showRemoveBtn: false,
    showRenameBtn: false
  },
	data: {
	  simpleData: {
      enable: true
    }
	},
	callback: {
		onNodeCreated: function(event, treeId, treeNode){
			//if(treeNode.id==1){
				var zTree = $.fn.zTree.getZTreeObj(treeId);
				zTree.reAsyncChildNodes(treeNode, "refresh", false);
			//}
			$(".zTreeDemoBackground").css({"height": document.documentElement.clientHeight - 10})
		},
		onClick: onClick,
		beforeDrag: beforeDrag,
    beforeDrop: beforeDrop,
    beforeDragOpen: beforeDragOpen,
    onDrag: onDrag,
    onDrop: onDrop,
    onExpand: onExpand
	}
};
var zNodes =[
  {name:"五杂优选", id:"0", isParent:true, drag:false}
];
function dropPrev(treeId, nodes, targetNode) {
  var pNode = targetNode.getParentNode();
  if (pNode && pNode.dropInner === false) {
    return false;
  } else {
    for (var i=0,l=curDragNodes.length; i<l; i++) {
      var curPNode = curDragNodes[i].getParentNode();
      if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === false) {
        return false;
      }
    }
  }
  return true;
}
function dropInner(treeId, nodes, targetNode) {
  if (targetNode && targetNode.dropInner === false) {
    return false;
  } else {
    for (var i=0,l=curDragNodes.length; i<l; i++) {
      if (!targetNode && curDragNodes[i].dropRoot === false) {
        return false;
      } else if (curDragNodes[i].parentTId && curDragNodes[i].getParentNode() !== targetNode && curDragNodes[i].getParentNode().childOuter === false) {
        return false;
      }
    }
  }
  return true;
}
function dropNext(treeId, nodes, targetNode) {
  var pNode = targetNode.getParentNode();
  if (pNode && pNode.dropInner === false) {
    return false;
  } else {
    for (var i=0,l=curDragNodes.length; i<l; i++) {
      var curPNode = curDragNodes[i].getParentNode();
      if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === false) {
        return false;
      }
    }
  }
  return true;
}

var log, className = "dark", curDragNodes, autoExpandNode;
function beforeDrag(treeId, treeNodes) {
  className = (className === "dark" ? "":"dark");
  for (var i=0,l=treeNodes.length; i<l; i++) {
    if (treeNodes[i].drag === false) {
      curDragNodes = null;
      return false;
    } else if (treeNodes[i].parentTId && treeNodes[i].getParentNode().childDrag === false) {
      curDragNodes = null;
      return false;
    }
  }
  curDragNodes = treeNodes;
  return true;
}
function beforeDragOpen(treeId, treeNode) {
  autoExpandNode = treeNode;
  return true;
}
function beforeDrop(treeId, treeNodes, targetNode, moveType, isCopy) {
  className = (className === "dark" ? "":"dark");
  if(isCopy )
    return false;
  var pid = moveType == 'inner' ? targetNode.id : targetNode.pId;
  if(pid == treeNodes[0].pId) return false; 
  return true;
}
function onDrag(event, treeId, treeNodes) {
  className = (className === "dark" ? "":"dark");
}
function onDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {
  className = (className === "dark" ? "":"dark");
  if(moveType && moveType != null){//"inner"：成为子节点，"prev"：成为同级前一个节点，"next"：成为同级后一个节点
    var pid = moveType == 'inner' ? targetNode.id : targetNode.pId;
    $.ajax({
      async: false,
      url: "/distributor/changeLevel",
      data: {"distributorId": treeNodes[0].id, "parentDistributorId": pid},
      type: 'post',
      dataType: 'json',
      success: function(res){
        if(res.code==0){
          artDialog.tips("级别调整成功")
        }else{
          artDialog.alert(res.msg)
        }
      }
    });
  }
}
function onExpand(event, treeId, treeNode) {
  if (treeNode === autoExpandNode) {
    className = (className === "dark" ? "":"dark");
  }
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
		nodes[i] = {'id':childNodes[i].distributorId.toFixed(0), 'pId':childNodes[i].parentDistributorId.toFixed(0), 'name': childNodes[i].name, 'isParent': isParent, 'open': isParent, dropRoot:false};
	}
	return nodes;
}
function onClick(event, treeId, treeNode, clickFlag){
	distributorHandle.options.distributorId = treeNode.id;
	distributorHandle.options.name = treeNode.name;
	distributorHandle.queryByParams({parentDistributorId: treeNode.id});
	distributorHandle.setCaption("[" + treeNode.name + "]的下级分销商");
}

var newCount = 1;
function addHoverDom(treeId, treeNode) {
	var sObj = $("#" + treeNode.tId + "_span");
	if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
	var addStr = "<span class='button add' id='addBtn_" + treeNode.tId + "' title='新增下级分销' onfocus='this.blur();'></span>";
	if(treeNode.id > 0)
	addStr += '<span class="button edit" id="editBtn_'+treeNode.tId+'" title="编辑当前分销" onfocus="this.blur();"></span>';
//	if(!treeNode.isParent)
//	addStr += '<span class="button remove" id="removeBtn_'+treeNode.tId+'" title="删除分销商" onfocus="this.blur();"></span>';
	sObj.after(addStr);
	var btn = $("#addBtn_"+treeNode.tId);
	if (btn) btn.bind("click", function(){
		var zTree = $.fn.zTree.getZTreeObj(treeId);
		artDialog.data("nodes", {'parentNode': treeNode, 'zTree': zTree, 'callback': function(){
      zTree.reAsyncChildNodes(treeNode, "refresh", false);
    }});
		artDialog.open('/common/mgr/distributor/addDistributor', {
      title : "新增下级分销商",
      width : "700px",
      height : "600px",
      drag : true,
      resize : true,
      lock : true
    /*
     * , close:function(){ document.location.reload(); }
     */
    });
		return false;
	});
	var editBtn = $("#editBtn_"+treeNode.tId);
	if (editBtn) editBtn.bind("click", function(){
		var zTree = $.fn.zTree.getZTreeObj(treeId);
		artDialog.data("nodes", {'parentNode': treeNode.getParentNode(), 'zTree': zTree, 'callback': function(pid){
		  if(pid == 0){
		    zTree.refresh();
		    return ;
		  }
		  var nodes = zTree.getNodesByParam("id", pid);
		  nodes[0].isParent = true;
		  zTree.updateNode(nodes[0]);
		  zTree.reAsyncChildNodes(nodes[0], "refresh", false);
		  zTree.reAsyncChildNodes(treeNode.getParentNode(), "refresh", false, function(){
		    var folderNodes = zTree.getNodesByFilter(function (node) { return true}, false, treeNode.getParentNode());
		    if(folderNodes.length == 0){
		      treeNode.getParentNode().isParent = false;
		    }else{
		      treeNode.getParentNode().isParent = true;
		    }
		    zTree.updateNode(treeNode.getParentNode());
		  });
		}});
    artDialog.open("/distributor/editBefore/" + treeNode.id, {
      title : "编辑" + treeNode.name,
      width : "700px",
      height : "600px",
      drag : true,
      resize : true,
      lock : true
    /*
     * , close:function(){ document.location.reload(); }
     */
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
	$.fn.zTree.init($("#operatorsTree"), setting, zNodes);
	
//	$(".menuUriEnter").click(function(){
//		var sourceId = distributorHandle.options.sourceId;
//		var uri = $("input[name=menuUri]").val();
//		
//	});
});