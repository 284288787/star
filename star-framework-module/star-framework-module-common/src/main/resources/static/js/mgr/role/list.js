var authorize=false;
var basePath = "/";
var roleHandle = new ListHandle({
	basePath: basePath,
	tableId: '#grid-table',
	pagerId: '#grid-pager',
	formId: '#queryForm',
	entityName: '角色',
	winWidth: '480px',
	winHeight: '400px',
	primaryKey: 'roleId',
	urls:{
		list: basePath+'role/list',        //列表
		addBefore: basePath+'common/mgr/role/addRole',   //添加之前
		editBefore: basePath+'role/editBefore',          //编辑之前
		deleted: basePath+'role/deleted',      //删除
	}
},{
	setResource: function(roleId, roleRemark){
		var params={'roleId': roleId, 'roleRemark': roleRemark};
		artDialog.data("params", params);
		artDialog.open(basePath+'common/mgr/role/setResource',{
			title: "指定["+roleRemark+"]的资源",
			width : '456px',
			height: '630px',
			drag:true,
			resize:true,
			lock:true/* ,
			close:function(){
				document.location.reload();
			} */
		});
	},
	viewResource: function(roleId, roleRemark){
		var params={'roleId': roleId, 'roleRemark': roleRemark};
		artDialog.data("params", params);
		artDialog.open(basePath+'common/mgr/role/viewResource',{
			title: "查看["+roleRemark+"]的资源",
			width : '456px',
			height: '630px',
			drag:true,
			resize:true,
			lock:true/* ,
			close:function(){
				document.location.reload();
			} */
		});
	},
	setMainUri: function(roleId, roleRemark){
		var params={'roleId': roleId, 'roleRemark': roleRemark};
		artDialog.data("params", params);
		artDialog.open(basePath+'common/mgr/role/setMainUri',{
			title: "指定["+roleRemark+"]的主URI",
			width : '456px',
			height: '630px',
			drag:true,
			resize:true,
			lock:true/* ,
			close:function(){
				document.location.reload();
			} */
		});
	},
});
$(function(){
	var colNames = [ 'roleId', '角色名称', '角色描述', '角色编码', '创建时间', '操作' ];
	var colModel = [
		{name: 'roleId', index: 'roleId', width: 70, align: "center", formatter: function(cellvalue, options, rowObject){
			return cellvalue.toFixed(0);
		}}, 
		{name: 'roleRemark', index: 'roleRemark', width: 70, align: "center"}, 
		{name: 'roleIntro', index: 'roleIntro', width: 140, align: "center"}, 
		{name: 'roleName', index: 'roleName', width: 70, align: "center"}, 
		{name: 'createTime', index: 'createTime', width: 70, align: "center"}, 
		{align: "center", editable: false, sortable: false, formatter: function(cellvalue, options, rowObject){
			var temp = '';
			if(hasAuthorize("role-edit")){
				temp += '<a class="linetaga" href="javascript: roleHandle.edit(\'' + rowObject.roleId.toFixed(0) + '\');" >编辑</a>';
			}
			if(hasAuthorize("role-saveRoleResourceRelation")){
				temp += '<a class="linetaga" href="javascript: roleHandle.setResource(\'' + rowObject.roleId.toFixed(0) + '\',\''+rowObject.roleRemark+'\');" >指定资源</a>';
			}
			if(hasAuthorize("role-setMainUri")){
				temp += '<a class="linetaga" href="javascript: roleHandle.setMainUri(\'' + rowObject.roleId.toFixed(0) + '\',\''+rowObject.roleRemark+'\');" >指定主URI</a>';
			}
			if(hasAuthorize("role-viewResource")){
				temp += '<a class="linetaga" href="javascript: roleHandle.viewResource(\'' + rowObject.roleId.toFixed(0) + '\',\''+rowObject.roleRemark+'\');" >查看资源</a>';
			}
			return temp;
		}}
	];
	var rowList = [10, 20, 30, 50];
	var rownumbers = true;
	var multiselect = true;
	var config={caption: "角色列表", colNames: colNames, colModel: colModel, rowList: rowList, rownumbers: rownumbers, multiselect: multiselect};
	roleHandle.init(config);
});