var basePath = "/";
var userHandle = new ListHandle({
	basePath: basePath,
	tableId: '#grid-table',
	pagerId: '#grid-pager',
	formId: '#queryForm',
	entityName: '用户信息',
	winWidth: '480px',
	winHeight: '330px',
	primaryKey: 'userId',
	urls:{
		list: basePath+'userAccount/list',        //列表
		addBefore: basePath+'common/mgr/userAccount/addUser',   //添加之前
		editBefore: basePath+'userInfo/editBefore',          //编辑之前
		enabled: basePath+'userAccount/enabled',      //启用
		disabled: basePath+'userAccount/disabled',    //禁用
		deleted: basePath+'userAccount/deleted',      //删除
	}
},{
	locked: function(userId){
		artDialog.confirm("确认锁定？", function(){
		userHandle.ajax({
			url: basePath+'userAccount/locked',
			data: {ids: userId},
			success: function(res){
				if(res.code == 0){
					artDialog.alert("锁定成功")
					userHandle.query();
				}else{
					artDialog.alert(res.msg)
				}
			}
		});
		})
	},
	unlock: function(userId){
		userHandle.ajax({
			url: basePath+'userAccount/unlock',
			data: {ids: userId},
			success: function(res){
				if(res.code == 0){
					artDialog.alert("解锁成功")
					userHandle.query();
				}else{
					artDialog.alert(res.msg)
				}
			}
		});
	},
	changePassword: function(userId){
		var params={userId: userId};
		artDialog.data("params", params);
		artDialog.open(basePath+'common/mgr/userAccount/changePassword',{
			title: "修改密码",
			width : '456px',
			height: '230px',
			drag:true,
			resize:true,
			lock:true/* ,
			close:function(){
				document.location.reload();
			} */
		});
	},
	setRole: function(userId, name){
		var params={userId: userId, name: name};
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
	viewRole: function(userId, name){
		var params={userId: userId, name: name};
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
$(function(){
	var colNames = [ 'userId', '帐号', '帐号类型', '帐号类型', '姓名', '手机号', '是否禁用', '是否锁定', '创建时间', '操作' ];
	var colModel = [
		{name: 'userId', index: 'userId', width: 70, align: "center", formatter: function(cellvalue, options, rowObject){
			return cellvalue.toFixed(0);
		}}, 
		{name: 'account', index: 'account', width: 70, align: "center"}, 
		{name: 'typeName', width: 60, align: "center", sortable: false, editable: false}, 
		{name: 'userType', hidden: true, sortable: false, editable: false}, 
		{name: 'name', index: 'name', width: 50, align: "center"}, 
		{name: 'mobile', index: 'mobile', width: 50, align: "center"}, 
		{name: 'enabled', index: 'enabled', width: 60, align: "center", formatter: 'select', editoptions:{value:"1:启用中;0:禁用中"}}, 
		{name: 'nonLocked', index: 'nonLocked', width: 60, align: "center", formatter: 'select', editoptions:{value:"1:未锁定;0:已锁定"}}, 
		{name: 'createTime', index: 'createTime', width: 70, align: "center"}, 
		{align: "center", editable: false, sortable: false, formatter: function(cellvalue, options, rowObject){
			var temp = '';
			if(hasAuthorize('useraccount-changePassword')){
				temp += '<a class="linetaga" href="javascript: userHandle.changePassword(\'' + rowObject.userId.toFixed(0) + '\');" >修改密码</a>';
			}
			if(hasAuthorize('useraccount-editBefore')){
				if(rowObject.userType && rowObject.userType.indexOf('userInfoService') != -1){
					temp += '<a class="linetaga" href="javascript: userHandle.edit(\'' + rowObject.userId.toFixed(0) + '\');" >编辑用户信息</a>';
				}else{
					temp += '<a class="linetaga" href="javascript: artDialog.alert(\'请前往相应模块进行用户信息编辑\');" >编辑用户信息</a>';
				}
			}
			if(rowObject.enabled==1){
				if(hasAuthorize('useraccount-disabled')){
					temp += '<a class="linetaga" href="javascript: userHandle.disabled(\'' + rowObject.userId.toFixed(0) + '\');" >禁用</a>';
				}
			}else{
				if(hasAuthorize('useraccount-enabled')){
					temp += '<a class="linetaga" href="javascript: userHandle.enabled(\'' + rowObject.userId.toFixed(0) + '\');" >启用</a>';
				}
			}
			if(rowObject.nonLocked==1){
				if(hasAuthorize('useraccount-locked')){
					temp += '<a class="linetaga" href="javascript: userHandle.locked(\'' + rowObject.userId.toFixed(0) + '\');" >锁定</a>';
				}
			}else{
				if(hasAuthorize('useraccount-unlock')){
					temp += '<a class="linetaga" href="javascript: userHandle.unlock(\'' + rowObject.userId.toFixed(0) + '\');" >解锁</a>';
				}
			}
			if(hasAuthorize('useraccount-setRole')){
				temp += '<a class="linetaga" href="javascript: userHandle.setRole(\'' + rowObject.userId.toFixed(0) + '\',\''+(rowObject.name?rowObject.name:rowObject.account)+'\');" >分配角色</a>';
			}
			if(hasAuthorize('useraccount-viewRole')){
				temp += '<a class="linetaga" href="javascript: userHandle.viewRole(\'' + rowObject.userId.toFixed(0) + '\',\''+(rowObject.name?rowObject.name:rowObject.account)+'\');" >查看角色</a>';
			}
			return temp;
		}}
	];
	var rowList = [10, 20, 30, 50];
	var rownumbers = true;
	var multiselect = true;
	var config={caption: "用户列表", colNames: colNames, colModel: colModel, rowList: rowList, rownumbers: rownumbers, multiselect: multiselect};
	userHandle.init(config);
});