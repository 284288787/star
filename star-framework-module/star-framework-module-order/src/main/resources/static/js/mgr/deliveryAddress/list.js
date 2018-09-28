var basePath = "/";
var deliveryAddressHandle = new ListHandle({
  basePath: basePath,
  tableId: '#grid-table',
  pagerId: '#grid-pager',
  formId: '#queryForm',
  entityName: '收货地址',
  winWidth: '500px',
  winHeight: '400px',
  primaryKey: 'id',
  urls:{
    list: basePath+'deliveryAddress/list',
    addBefore: basePath+'common/mgr/deliveryAddress/addDeliveryAddress',
    editBefore: basePath+'deliveryAddress/editBefore',
    enabled: basePath+'deliveryAddress/enabled',
    disabled: basePath+'deliveryAddress/disabled',
    deleted: basePath+'deliveryAddress/deleted',
  }
},{});
$(function(){
  var colNames = ['操作'];
  var colModel = [
    {align: "center", editable: false, sortable: false, formatter: function(cellvalue, options, rowObject){
      var temp = '';
      if(hasAuthorize('deliveryAddress-editBefore')){
        temp += '<a class="linetaga" href="javascript: deliveryAddressHandle.edit(\'' + rowObject.id.toFixed(0) + '\');" >编辑</a>';
      }
      if(rowObject.enabled==1){
        if(hasAuthorize('deliveryAddress-disabled')){
          temp += '<a class="linetaga" href="javascript: deliveryAddressHandle.disabled(\'' + rowObject.id.toFixed(0) + '\');" >禁用</a>';
        }
      }else{
        if(hasAuthorize('deliveryAddress-enabled')){
          temp += '<a class="linetaga" href="javascript: deliveryAddressHandle.enabled(\'' + rowObject.id.toFixed(0) + '\');" >启用</a>';
        }
      }
      return temp;
    }}
  ];
  var rowList = [10, 20, 30, 50];
  var rownumbers = true;
  var multiselect = true;
  var config={caption: "收货地址列表", colNames: colNames, colModel: colModel, rowList: rowList, rownumbers: rownumbers, multiselect: multiselect};
  deliveryAddressHandle.init(config);
});