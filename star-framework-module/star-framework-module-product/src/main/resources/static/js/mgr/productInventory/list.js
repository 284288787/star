var basePath = "/";
var productInventoryHandle = new ListHandle({
  basePath: basePath,
  tableId: '#grid-table',
  pagerId: '#grid-pager',
  formId: '#queryForm',
  entityName: '商品库存',
  winWidth: '500px',
  winHeight: '400px',
  primaryKey: 'id',
  urls:{
    list: basePath+'productInventory/list',
    addBefore: basePath+'common/mgr/productInventory/addProductInventory',
    editBefore: basePath+'productInventory/editBefore',
    enabled: basePath+'productInventory/enabled',
    disabled: basePath+'productInventory/disabled',
    deleted: basePath+'productInventory/deleted',
  }
},{});
$(function(){
  var colNames = ['商品ID', '库存总数', '已售数量', '操作'];
  var colModel = [
    {name: 'productId', index: 'product_id', width: 50, align: "center"}, 
    {name: 'number', index: 'number', width: 50, align: "center"}, 
    {name: 'soldNumber', index: 'sold_number', width: 50, align: "center"}, 
    {align: "center", editable: false, sortable: false, formatter: function(cellvalue, options, rowObject){
      var temp = '';
      if(hasAuthorize('productInventory-editBefore')){
        temp += '<a class="linetaga" href="javascript: productInventoryHandle.edit(\'' + rowObject.id.toFixed(0) + '\');" >编辑</a>';
      }
      if(rowObject.enabled==1){
        if(hasAuthorize('productInventory-disabled')){
          temp += '<a class="linetaga" href="javascript: productInventoryHandle.disabled(\'' + rowObject.id.toFixed(0) + '\');" >禁用</a>';
        }
      }else{
        if(hasAuthorize('productInventory-enabled')){
          temp += '<a class="linetaga" href="javascript: productInventoryHandle.enabled(\'' + rowObject.id.toFixed(0) + '\');" >启用</a>';
        }
      }
      return temp;
    }}
  ];
  var rowList = [10, 20, 30, 50];
  var rownumbers = true;
  var multiselect = true;
  var config={caption: "商品库存列表", colNames: colNames, colModel: colModel, rowList: rowList, rownumbers: rownumbers, multiselect: multiselect};
  productInventoryHandle.init(config);
});