var basePath = "/";
var productTagHandle = new ListHandle({
  basePath: basePath,
  tableId: '#grid-table',
  pagerId: '#grid-pager',
  formId: '#queryForm',
  entityName: '商品标签',
  winWidth: '500px',
  winHeight: '400px',
  primaryKey: 'tagId',
  urls:{
    list: basePath+'productTag/list',
    addBefore: basePath+'common/mgr/productTag/addProductTag',
    editBefore: basePath+'productTag/editBefore',
    enabled: basePath+'productTag/enabled',
    disabled: basePath+'productTag/disabled',
    deleted: basePath+'productTag/deleted',
  }
},{});
$(function(){
  var colNames = ['标签ID', '标签名称', '创建日期', '操作'];
  var colModel = [
    {name: 'tagId', index: 'tag_id', width: 50, align: "center", formatter: function(cellvalue, options, rowObject){
      return cellvalue.toFixed(0);
    }}, 
    {name: 'tagName', index: 'tag_name', width: 50, align: "center"}, 
    {name: 'createTime', index: 'create_time', width: 50, align: "center", formatter:'date', formatoptions: {srcformat: 'Y-m-d H:i:s', newformat:'Y-m-d H:i:s'}}, 
    {align: "center", editable: false, sortable: false, formatter: function(cellvalue, options, rowObject){
      var temp = '';
      if(hasAuthorize('productTag-editBefore')){
        temp += '<a class="linetaga" href="javascript: productTagHandle.edit(\'' + rowObject.tagId.toFixed(0) + '\');" >编辑</a>';
      }
      if(rowObject.enabled==1){
        if(hasAuthorize('productTag-disabled')){
          temp += '<a class="linetaga" href="javascript: productTagHandle.disabled(\'' + rowObject.tagId.toFixed(0) + '\');" >禁用</a>';
        }
      }else{
        if(hasAuthorize('productTag-enabled')){
          temp += '<a class="linetaga" href="javascript: productTagHandle.enabled(\'' + rowObject.tagId.toFixed(0) + '\');" >启用</a>';
        }
      }
      return temp;
    }}
  ];
  var rowList = [10, 20, 30, 50];
  var rownumbers = true;
  var multiselect = true;
  var config={caption: "商品标签列表", colNames: colNames, colModel: colModel, rowList: rowList, rownumbers: rownumbers, multiselect: multiselect};
  productTagHandle.init(config);
});