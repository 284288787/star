var basePath = "/";
var productCategoryHandle = new ListHandle({
  basePath: basePath,
  tableId: '#grid-table',
  pagerId: '#grid-pager',
  formId: '#queryForm',
  entityName: '商品分类',
  winWidth: '500px',
  winHeight: '400px',
  primaryKey: 'productCateId',
  urls:{
    list: basePath+'productCategory/list',
    addBefore: basePath+'common/mgr/productCategory/addProductCategory',
    editBefore: basePath+'productCategory/editBefore',
    enabled: basePath+'productCategory/enabled',
    disabled: basePath+'productCategory/disabled',
    deleted: basePath+'productCategory/deleted',
  }
},{});
$(function(){
  var colNames = ['分类ID', '分类名称', '创建日期', '操作'];
  var colModel = [
    {name: 'productCateId', index: 'product_cate_id', width: 50, align: "center", formatter: function(cellvalue, options, rowObject){
      return cellvalue.toFixed(0);
    }}, 
    {name: 'productCateName', index: 'product_cate_name', width: 50, align: "center"}, 
    {name: 'createTime', index: 'create_time', width: 50, align: "center", formatter:'date', formatoptions: {newformat:'Y-m-d'}}, 
    {align: "center", editable: false, sortable: false, formatter: function(cellvalue, options, rowObject){
      var temp = '';
      if(hasAuthorize('productCategory-editBefore')){
        temp += '<a class="linetaga" href="javascript: productCategoryHandle.edit(\'' + rowObject.productCateId.toFixed(0) + '\');" >编辑</a>';
      }
      if(rowObject.enabled==1){
        if(hasAuthorize('productCategory-disabled')){
          temp += '<a class="linetaga" href="javascript: productCategoryHandle.disabled(\'' + rowObject.productCateId.toFixed(0) + '\');" >禁用</a>';
        }
      }else{
        if(hasAuthorize('productCategory-enabled')){
          temp += '<a class="linetaga" href="javascript: productCategoryHandle.enabled(\'' + rowObject.productCateId.toFixed(0) + '\');" >启用</a>';
        }
      }
      return temp;
    }}
  ];
  var rowList = [10, 20, 30, 50];
  var rownumbers = true;
  var multiselect = true;
  var config={caption: "商品分类列表", colNames: colNames, colModel: colModel, rowList: rowList, rownumbers: rownumbers, multiselect: multiselect};
  productCategoryHandle.init(config);
});