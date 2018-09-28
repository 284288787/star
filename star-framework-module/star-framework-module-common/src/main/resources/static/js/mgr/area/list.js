var basePath = "/";
var areaHandle = new ListHandle({
	basePath: basePath,
	tableId: '#grid-table',
	pagerId: '#grid-pager',
	formId: '#queryForm',
	entityName: '地区',
	winWidth: '480px',
	winHeight: '330px',
	primaryKey: 'areaId',
	urls:{
		list: basePath+'area/list',        //列表
		addBefore: basePath+'common/mgr/area/addArea',   //添加之前
		enabled: basePath+'area/enabled',      //启用
		disabled: basePath+'area/disabled',    //禁用
		deleted: basePath+'area/deleted',      //删除
	}
},{
  addArea: function(parentId, shortName){
    var params={parentAreaId: parentId};
    $.extend(areaHandle, params);
    artDialog.data("params", areaHandle);
    artDialog.open(basePath+'common/mgr/area/addArea',{
      title: "给[" + shortName + "]下新增下级地区",
      width : '456px',
      height: '480px',
      drag:true,
      resize:true,
      lock:true
    });
  },
});
$(function(){
	var colNames = [ 'areaId', '简称', '全称', '类型', '父ID', 'pinyin', 'py', '状态', '操作' ];
	var colModel = [
		{name: 'areaId', index: 'areaId', width: 70, align: "center"}, 
		{name: 'shortName', index: 'shortName', width: 70, align: "center"}, 
		{name: 'areaName', index: 'areaName', width: 50, align: "center"},
		{name: 'type', index: 'type', width: 60, align: "center", formatter: 'select', editoptions:{value:"1:直辖市;2:港澳台;3:省;4:市;5:区县;6:乡镇/街道"}}, 
		{name: 'parentId', index: 'parentId', width: 50, align: "center"},
		{name: 'pinyin', index: 'pinyin', width: 50, align: "center"},
		{name: 'py', index: 'py', width: 50, align: "center"},
		{name: 'status', index: 'status', width: 60, align: "center", formatter: 'select', editoptions:{value:"1:启用中;0:禁用中"}}, 
		{align: "left", editable: false, sortable: false, formatter: function(cellvalue, options, rowObject){
			var temp = '';
			if(rowObject.status==1){
				if(hasAuthorize('area-disabled')){
					temp += '<a class="linetaga" href="javascript: areaHandle.disabled(\'' + rowObject.areaId.toFixed(0) + '\');" >禁用</a>';
				}
			}else{
				if(hasAuthorize('area-enabled')){
					temp += '<a class="linetaga" href="javascript: areaHandle.enabled(\'' + rowObject.areaId.toFixed(0) + '\');" >启用</a>';
				}
			}
			if(rowObject.type >= 4){
			  if(hasAuthorize('area-add')){
			    temp += '<a class="linetaga" href="javascript: areaHandle.addArea(' + rowObject.areaId.toFixed(0) + ',\''+rowObject.shortName+'\');" >新增下级地区</a>';
			  }
			}
			return temp;
		}}
	];
	var rowList = [10, 20, 30, 50];
	var rownumbers = true;
	var multiselect = true;
	var config={caption: "地区列表", colNames: colNames, colModel: colModel, rowList: rowList, rownumbers: rownumbers, multiselect: multiselect};
	areaHandle.init(config);
});