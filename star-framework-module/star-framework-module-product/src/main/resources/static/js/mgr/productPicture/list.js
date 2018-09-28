var basePath = "/";
var productPictureHandle = new ListHandle({
  basePath: basePath,
  tableId: '#grid-table',
  pagerId: '#grid-pager',
  formId: '#queryForm',
  entityName: '商品图片',
  winWidth: '500px',
  winHeight: '400px',
  primaryKey: 'id',
  urls:{
    list: basePath+'productPicture/list',
    addBefore: basePath+'common/mgr/productPicture/addProductPicture',
    editBefore: basePath+'productPicture/editBefore',
    enabled: basePath+'productPicture/enabled',
    disabled: basePath+'productPicture/disabled',
    deleted: basePath+'productPicture/deleted',
  }
},{});
$(function(){
  var colNames = ['商品ID', '图片类型', '商品路径', '操作'];
  var colModel = [
    {name: 'productId', index: 'product_id', width: 50, align: "center"}, 
    {name: 'type', index: 'type', width: 50, align: "center", formatter: 'select', editoptions: {value:'1:主图;2:多角图'}}, 
    {name: 'url', index: 'url', width: 50, align: "center"}, 
    {align: "center", editable: false, sortable: false, formatter: function(cellvalue, options, rowObject){
      var temp = '';
      if(hasAuthorize('productPicture-editBefore')){
        temp += '<a class="linetaga" href="javascript: productPictureHandle.edit(\'' + rowObject.id.toFixed(0) + '\');" >编辑</a>';
      }
      if(rowObject.enabled==1){
        if(hasAuthorize('productPicture-disabled')){
          temp += '<a class="linetaga" href="javascript: productPictureHandle.disabled(\'' + rowObject.id.toFixed(0) + '\');" >禁用</a>';
        }
      }else{
        if(hasAuthorize('productPicture-enabled')){
          temp += '<a class="linetaga" href="javascript: productPictureHandle.enabled(\'' + rowObject.id.toFixed(0) + '\');" >启用</a>';
        }
      }
      return temp;
    }}
  ];
  var rowList = [10, 20, 30, 50];
  var rownumbers = true;
  var multiselect = true;
  var config={caption: "商品图片列表", colNames: colNames, colModel: colModel, rowList: rowList, rownumbers: rownumbers, multiselect: multiselect};
  productPictureHandle.init(config);
});