var basePath = "/";
var categoryHandle = new ListHandle({
  basePath: basePath,
  tableId: '#grid-table',
  pagerId: '#grid-pager',
  formId: '#queryForm',
  entityName: '商品分类',
  winWidth: '300px',
  winHeight: '200px',
  primaryKey: 'cateId',
  urls:{
    list: basePath+'category/list',
    addBefore: basePath+'common/mgr/category/addCategory',
    editBefore: basePath+'category/editBefore',
    enabled: basePath+'category/enabled',
    disabled: basePath+'category/disabled',
    deleted: basePath+'category/deleted',
  }
},{});
$(function(){
  var colNames = ['分类ID', '分类名称', '创建日期', '操作'];
  var colModel = [
    {name: 'cateId', index: 'cate_id', width: 50, align: "center", formatter: function(cellvalue, options, rowObject){
      return cellvalue.toFixed(0);
    }}, 
    {name: 'cateName', index: 'cate_name', width: 50, align: "center"}, 
    {name: 'createTime', index: 'create_time', width: 50, align: "center", formatter:'date', formatoptions: {newformat:'Y-m-d'}}, 
    {align: "center", editable: false, sortable: false, formatter: function(cellvalue, options, rowObject){
      var temp = '';
      if(hasAuthorize('category-editBefore')){
        temp += '<a class="linetaga" href="javascript: categoryHandle.edit(\'' + rowObject.cateId.toFixed(0) + '\');" >编辑</a>';
      }
      if(rowObject.enabled==1){
        if(hasAuthorize('category-disabled')){
          temp += '<a class="linetaga" href="javascript: categoryHandle.disabled(\'' + rowObject.cateId.toFixed(0) + '\');" >禁用</a>';
        }
      }else{
        if(hasAuthorize('category-enabled')){
          temp += '<a class="linetaga" href="javascript: categoryHandle.enabled(\'' + rowObject.cateId.toFixed(0) + '\');" >启用</a>';
        }
      }
      return temp;
    }}
  ];
  var rowList = [10, 20, 30, 50];
  var rownumbers = true;
  var multiselect = true;
  var config={caption: "商品分类列表", colNames: colNames, colModel: colModel, rowList: rowList, rownumbers: rownumbers, multiselect: multiselect};
  categoryHandle.init(config);
});