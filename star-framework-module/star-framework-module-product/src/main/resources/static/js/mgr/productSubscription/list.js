var basePath = "/";
var productSubscriptionHandle = new ListHandle({
  basePath: basePath,
  tableId: '#grid-table',
  pagerId: '#grid-pager',
  formId: '#queryForm',
  entityName: '商品订阅',
  winWidth: '500px',
  winHeight: '400px',
  primaryKey: 'id',
  urls:{
    list: basePath+'productSubscription/list',
    addBefore: basePath+'common/mgr/productSubscription/addProductSubscription',
    editBefore: basePath+'productSubscription/editBefore',
    enabled: basePath+'productSubscription/enabled',
    disabled: basePath+'productSubscription/disabled',
    deleted: basePath+'productSubscription/deleted',
  }
},{});
$(function(){
  var colNames = ['供应ID', "供应标题", '用户头像', '用户ID', '用户姓名', '用户手机号', '创建日期'];
  var colModel = [
    {name: 'productId', index: 'product_id', width: 30, align: "center"}, 
    {name: 'title', index: 'title', width: 100, align: "center"}, 
    {name: 'head', index: 'head', width: 60, align: "center", formatter: function(cellvalue, a, b){
      return "<img src='"+cellvalue+"' height='60px'>"
    }}, 
    {name: 'memberId', index: 'member_id', width: 30, align: "center"}, 
    {name: 'name', index: 'name', width: 50, align: "center"}, 
    {name: 'mobile', index: 'mobile', width: 50, align: "center"}, 
    {name: 'createTime', index: 'create_time', width: 50, align: "center", formatter:'date', formatoptions: {newformat:'Y-m-d H:i:s'}}
  ];
  var rowList = [10, 20, 30, 50];
  var rownumbers = true;
  var multiselect = true;
  var config={caption: "商品订阅列表", colNames: colNames, colModel: colModel, rowList: rowList, rownumbers: rownumbers, multiselect: multiselect};
  productSubscriptionHandle.init(config);
});