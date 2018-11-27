var basePath = "/";
var distributorTotalHandle = new ListHandle({
  basePath: basePath,
  tableId: '#grid-table',
  pagerId: '#grid-pager',
  formId: '#queryForm',
  entityName: '每天统计明细',
  winWidth: '500px',
  winHeight: '400px',
  primaryKey: 'id',
  urls:{
    list: basePath+'distributorTotal/list',
    addBefore: basePath+'common/mgr/distributorTotal/addDistributorTotal',
    editBefore: basePath+'distributorTotal/editBefore',
    enabled: basePath+'distributorTotal/enabled',
    disabled: basePath+'distributorTotal/disabled',
    deleted: basePath+'distributorTotal/deleted',
  }
},{});
$(function(){
  var colNames = ['主键', '分销商', '创建日期', '当天订单数', '当天已卖商品数', '当天支付人数', '当天快递人数', '当天自主下单人数', '当天总支付的商品金额', '当天总支付的运费', '当天直属分销商提成', '当天上级分销商提成', '操作'];
  var colModel = [
    {name: 'id', index: 'id', width: 50, align: "center", formatter: function(cellvalue, options, rowObject){
      return cellvalue.toFixed(0);
    }}, 
    {name: 'distributorId', index: 'distributor_id', width: 50, align: "center"}, 
    {name: 'createTime', index: 'create_time', width: 50, align: "center", formatter:'date', formatoptions: {newformat:'Y-m-d H:i:s'}}, 
    {name: 'orderNum', index: 'order_num', width: 50, align: "center"}, 
    {name: 'productNum', index: 'product_num', width: 50, align: "center"}, 
    {name: 'payPeopleNum', index: 'pay_people_num', width: 50, align: "center"}, 
    {name: 'useDespatchNum', index: 'use_despatch_num', width: 50, align: "center"}, 
    {name: 'BuySelfNum', index: '_buy_self_num', width: 50, align: "center"}, 
    {name: 'payMoneyOfProduct', index: 'pay_money_of_product', width: 50, align: "center"}, 
    {name: 'payMoneyOfDespatch', index: 'pay_money_of_despatch', width: 50, align: "center"}, 
    {name: 'kickbackSecond', index: 'kickback_second', width: 50, align: "center"}, 
    {name: 'kickbackFirst', index: 'kickback_first', width: 50, align: "center"}, 
    {align: "center", editable: false, sortable: false, formatter: function(cellvalue, options, rowObject){
      var temp = '';
      if(hasAuthorize('distributorTotal-editBefore')){
        temp += '<a class="linetaga" href="javascript: distributorTotalHandle.edit(\'' + rowObject.id.toFixed(0) + '\');" >编辑</a>';
      }
      if(rowObject.enabled==1){
        if(hasAuthorize('distributorTotal-disabled')){
          temp += '<a class="linetaga" href="javascript: distributorTotalHandle.disabled(\'' + rowObject.id.toFixed(0) + '\');" >禁用</a>';
        }
      }else{
        if(hasAuthorize('distributorTotal-enabled')){
          temp += '<a class="linetaga" href="javascript: distributorTotalHandle.enabled(\'' + rowObject.id.toFixed(0) + '\');" >启用</a>';
        }
      }
      return temp;
    }}
  ];
  var rowList = [10, 20, 30, 50];
  var rownumbers = true;
  var multiselect = true;
  var config={caption: "每天统计明细列表", colNames: colNames, colModel: colModel, rowList: rowList, rownumbers: rownumbers, multiselect: multiselect};
  distributorTotalHandle.init(config);
});