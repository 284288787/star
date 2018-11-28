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
  new UtilsHandle({
    choose: [
      {
        object: $("input[name=distributorName]"),
        service: "distributorService",
        title: "选择分销商",
        width: "800px",
        height: "550px",
        valid: function(){
          return {valid: true};//{valid:$("#condition").val() != "", msg: "请先选择地区"};
        },
        condition: function(){
          var cond = {'regionId': $("input[name=regionId]").val(), 'regionName': $("input[name=regionName]").val()};
          return JSON.stringify(cond);
        },
        callback: function(rowObject){
          $("input[name=distributorName]").val(rowObject.name);
          $("input[name=distributorId]").val(rowObject.distributorId);
        }
      }
    ]
  },{});
  var colNames = ['分销商ID', '分销商', '订单日期', '订单数', '已卖商品数', '支付人数', '快递人数', '自主下单人数', '商品金额', '运费', '分销商提成', '上级分销商提成'];
  var colModel = [
    {name: 'distributorId', index: 'distributor_id', width: 50, align: "center"}, 
    {name: 'distributorName', index: 'distributor_name', width: 50, align: "center"}, 
    {name: 'createTime', index: 'create_time', width: 50, align: "center", formatter:'date', formatoptions: {newformat:'Y-m-d'}}, 
    {name: 'orderNum', index: 'order_num', width: 50, align: "center"}, 
    {name: 'productNum', index: 'product_num', width: 50, align: "center"}, 
    {name: 'payPeopleNum', index: 'pay_people_num', width: 50, align: "center"}, 
    {name: 'useDespatchNum', index: 'use_despatch_num', width: 50, align: "center"}, 
    {name: 'buySelfNum', index: 'buy_self_num', width: 50, align: "center"}, 
    {name: 'payMoneyOfProduct', index: 'pay_money_of_product', width: 50, align: "center", formatter: function(cellvalue, options, rowObject){
      return (cellvalue / 100.0).toFixed(2);
    }}, 
    {name: 'payMoneyOfDespatch', index: 'pay_money_of_despatch', width: 50, align: "center", formatter: function(cellvalue, options, rowObject){
      return (cellvalue / 100.0).toFixed(2);
    }}, 
    {name: 'kickbackSecond', index: 'kickback_second', width: 50, align: "center", formatter: function(cellvalue, options, rowObject){
      return (cellvalue / 100.0).toFixed(2);
    }}, 
    {name: 'kickbackFirst', index: 'kickback_first', width: 50, align: "center", formatter: function(cellvalue, options, rowObject){
      return (cellvalue / 100.0).toFixed(2);
    }}, 
  ];
  var rowList = [10, 20, 30, 50];
  var rownumbers = true;
  var multiselect = false;
  var config={sortorder: 'desc', rowNum: 50, caption: "每天统计明细列表", colNames: colNames, colModel: colModel, rowList: rowList, rownumbers: rownumbers, multiselect: multiselect};
  distributorTotalHandle.init(config);
});