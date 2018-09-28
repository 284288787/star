var authorize=false;
var basePath = "/";
var resourceHandle = new ListHandle({
	basePath: basePath,
	tableId: '#grid-table',
	//pagerId: '#grid-pager',
	formId: '#queryForm',
	entityName: '资源信息',
	winWidth: '480px',
	winHeight: '330px',
	primaryKey: 'sourceId',
	urls:{
		list: basePath+'resource/list',        //列表
		addBefore: basePath+'common/mgr/resource/addUser',   //添加之前
		editBefore: basePath+'resource/editBefore',          //编辑之前
	}
},{
	setRole: function(sourceId, name){
		var params={sourceId: sourceId, name: name};
		artDialog.data("params", params);
		artDialog.open(basePath+'common/mgr/userAccount/setRole',{
			title: "分配角色 -> " + name,
			width : '456px',
			height: '590px',
			drag:true,
			resize:true,
			lock:true
		});
	},
	viewRole: function(sourceId, name){
		var params={sourceId: sourceId, name: name};
		artDialog.data("params", params);
		artDialog.open(basePath+'common/mgr/userAccount/viewRole',{
			title: "查看角色 -> " + name,
			width : '456px',
			height: '590px',
			drag:true,
			resize:true,
			lock:true
		});
	}
});
var handles={};
$(function(){
	var colNames = [ 'sourceId', '资源名称', '菜单图标', 'parentId', '顺序', '是否禁用', '操作' ];
	var colModel = [
		{name: 'sourceId', index: 'sourceId', width: 70, align: "center", formatter: function(cellvalue, options, rowObject){
			return cellvalue.toFixed(0);
		}}, 
		{name: 'sourceName', index: 'sourceName', width: 50, align: "center"}, 
		{name: 'sourceIcoCls', index: 'sourceIcoCls', width: 50, align: "center"}, 
		{name: 'parentId', index: 'parentId', width: 50, align: "center"}, 
		{name: 'idx', index: 'idx', width: 50, align: "center"}, 
		{name: 'enabled', index: 'enabled', width: 60, align: "center", formatter: 'select', editoptions:{value:"1:启用中;0:禁用中"}}, 
		{align: "center", editable: false, sortable: false, formatter: function(cellvalue, options, rowObject){
			var temp = '';
			temp += '<a class="linetaga" href="javascript: resourceHandle.edit(\'' + rowObject.sourceId.toFixed(0) + '\');" >编辑用户信息</a>';
			if(rowObject.enabled==1){
				temp += '<a class="linetaga" href="javascript: resourceHandle.disabled(\'' + rowObject.sourceId.toFixed(0) + '\');" >禁用</a>';
			}else{
				temp += '<a class="linetaga" href="javascript: resourceHandle.enabled(\'' + rowObject.sourceId.toFixed(0) + '\');" >启用</a>';
			}
			//if(authorize){
				temp += '<a class="linetaga" href="javascript: resourceHandle.setRole(\'' + rowObject.sourceId.toFixed(0) + '\',\''+rowObject.name+'\');" >指定角色</a>';
				temp += '<a class="linetaga" href="javascript: resourceHandle.viewRole(\'' + rowObject.sourceId.toFixed(0) + '\',\''+rowObject.name+'\');" >查看角色</a>';
			//}
			return temp;
		}}
	];
	var rowList = [10, 20, 30, 50];
	var rownumbers = true;
	var multiselect = true;
	var config={dataType: 'json', caption: "资源列表", colNames: colNames, colModel: colModel, rowList: rowList, rownumbers: rownumbers, multiselect: multiselect};
	//subGrid(config, resourceHandle);
	//resourceHandle.setConfig(config);
	resourceHandle.setConfig(config);
	//jQuery("#grid-table").jqGrid('filterToolbar',{stringResult: false,searchOnEnter : false});
	
	function subGrid(config, parentGridHandle){
		config.subGrid = true;
		config.subGridRowExpanded = function(subgrid_id, row_id) {
			var childHandle = new ListHandle({
				basePath: basePath,
				urls:{
					list: basePath+'resource/list'
				}
			},{});
			handles['childHandle'+subgrid_id+'_'+row_id] = childHandle;
			childHandle.options.parentGrid=parentGridHandle;
			var rowData = $(childHandle.options.parentGrid.options.tableId).jqGrid("getRowData", row_id);
			var subgrid_table_id, pager_id;
			subgrid_table_id = subgrid_id+"_t";
			pager_id = "p_"+subgrid_table_id;
			$("#"+subgrid_id).html("<table id='"+subgrid_table_id+"' class='scroll'></table><div id='"+pager_id+"' class='scroll'></div>");
			
			childHandle.options.subGrid=true;
			childHandle.options.tableId="#"+subgrid_table_id;
			childHandle.options.pagerId="#1"+pager_id;
			var config={params: 'parentId='+rowData.sourceId, caption: rowData.sourceName, colNames: colNames, colModel: colModel, rowNum: 100, rownumbers: rownumbers, multiselect: multiselect, 
				height: '100%'
			};
			subGrid(config, childHandle);
			childHandle.init(config);
		};
		config.subGridRowColapsed = function(subgrid_id, row_id) {
			jQuery("#"+subgrid_id).remove();
		};
	}
});