var basePath = "/";
var orderAfterSaleHandle = new ListHandle({
  basePath: basePath,
  tableId: '#grid-table',
  pagerId: '#grid-pager',
  formId: '#queryForm',
  entityName: '订单售后',
  winWidth: '500px',
  winHeight: '400px',
  primaryKey: 'id',
  urls:{
    list: basePath+'orderAfterSale/list',
    addBefore: basePath+'common/mgr/orderAfterSale/addOrderAfterSale',
    editBefore: basePath+'orderAfterSale/editBefore',
    enabled: basePath+'orderAfterSale/enabled',
    disabled: basePath+'orderAfterSale/disabled',
    deleted: basePath+'orderAfterSale/deleted',
  }
},{
  pass: function(id){
    artDialog.confirm("确认审核通过？", function() {
      orderAfterSaleHandle.ajax({
        url : '/orderAfterSale/pass/'+id,
        success : function(res) {
          if (res.code == 0) {
            orderAfterSaleHandle.query();
          } else {
            artDialog.alert(res.msg)
          }
        }
      });
    });
  },
  nopass: function(id){
    artDialog.prompt("审核未通过原因：", function(reject) {
      if(! reject){
        artDialog.alert("原因必填");
        return false;
      }
      orderAfterSaleHandle.ajax({
        url : '/orderAfterSale/nopass/'+id,
        data: {reject: reject},
        success : function(res) {
          if (res.code == 0) {
            orderAfterSaleHandle.query();
          } else {
            artDialog.alert(res.msg)
          }
        }
      });
    });
  }
});
$(function(){
  var colNames = ['主键', '订单ID', '售后单号', 'openid', '分销商姓名', '分销商手机号', '总金额', '申请备注', '售后状态', '创建日期', '操作'];
  var colModel = [
    {name: 'id', index: 'id', width: 50, align: "center", formatter: function(cellvalue, options, rowObject){
      return cellvalue.toFixed(0);
    }}, 
    {name: 'orderId', index: 'order_id', width: 50, align: "center"}, 
    {name: 'afterCode', index: 'after_code', width: 50, align: "center"}, 
    {name: 'openId', index: 'open_id', width: 50, align: "center"}, 
    {name: 'name', index: 'name', width: 50, align: "center"}, 
    {name: 'mobile', index: 'mobile', width: 50, align: "center"}, 
    {name: 'totalMoney', index: 'total_money', width: 50, align: "center"}, 
    {name: 'remark', index: 'remark', width: 50, align: "center"}, 
    {name: 'state', index: 'state', width: 50, align: "center", formatter: 'select', editoptions: {value:'1:待处理;2:通过;3:不通过;4:已取消;5:已删除'}}, 
    {name: 'createTime', index: 'create_time', width: 50, align: "center", formatter:'date', formatoptions: {newformat:'Y-m-d H:i:s'}}, 
    {align: "center", editable: false, sortable: false, formatter: function(cellvalue, options, rowObject){
      var temp = '';
      if(hasAuthorize('orderAfterSale-pass')){
        temp += '<a class="linetaga" href="javascript: orderAfterSaleHandle.pass(\'' + rowObject.id.toFixed(0) + '\');" >通过</a>';
      }
      if(hasAuthorize('orderAfterSale-nopass')){
        temp += '<a class="linetaga" href="javascript: orderAfterSaleHandle.nopass(\'' + rowObject.id.toFixed(0) + '\');" >不通过</a>';
      }
      return temp;
    }}
  ];
  var rowList = [10, 20, 30, 50];
  var rownumbers = true;
  var multiselect = true;
  var config={caption: "订单售后列表", colNames: colNames, colModel: colModel, rowList: rowList, rownumbers: rownumbers, multiselect: multiselect};
  orderAfterSaleHandle.init(config);
});