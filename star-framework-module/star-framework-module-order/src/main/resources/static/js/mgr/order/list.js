var basePath = "/";
var orderHandle = new ListHandle({
  basePath: basePath,
  tableId: '#grid-table',
  pagerId: '#grid-pager',
  formId: '#queryForm',
  entityName: '订单',
  winWidth: '900px',
  winHeight: '500px',
  primaryKey: 'orderId',
  urls:{
    list: basePath+'order/list',
    addBefore: basePath+'common/mgr/order/addOrder',
    viewBefore: basePath+'order/editBefore'
  }
},{});
new UtilsHandle({
  choose: [
    {
      object: $("input[name=regionName]"),
      service: "distributionRegionService",
      title: "选择分销地区",
      width: "800px",
      height: "500px",
      callback: function(rowObject){
        $("input[name=regionName]").val(rowObject.name);
        $("input[name=regionId]").val(rowObject.regionId);
      }
    },
    {
      object: $("input[name=distributorName]"),
      service: "distributorService",
      title: "选择分销商",
      width: "800px",
      height: "500px",
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
$(function(){
  var states={1:'待付款',2:'待提货',3:'已提货',4:'已退货',5:'已删除'};
  var colors={1:'#e87108',2:'#ad30de',3:'green',4:'red',5:'gray'}
  var colNames = ['订单ID', '订单状态', '订单编号', '总金额', '总提成', '提货码', '分销区域', '分销商', '店铺名称', '店铺电话', '店铺地址', '订单类型', '用户姓名', '用户手机号', '收货类型', '收货地址', '收件人', '收件人手机', '创建日期', '操作'];
  var colModel = [
    {name: 'orderId', index: 'order_id', width: 50, align: "center", formatter: function(cellvalue, options, rowObject){
      return cellvalue.toFixed(0);
    }}, 
    {name: 'state', index: 'state', width: 50, align: "center", formatter: function(cellvalue, options, rowObject){
      return '<span style="color:'+colors[cellvalue]+'">'+states[cellvalue]+'</span>';
    }}, 
    {name: 'orderCode', index: 'order_code', width: 50, align: "center"}, 
    {name: 'totalMoney', index: 'total_money', width: 50, align: "center", formatter: function(cellvalue, options, rowObject){
        return (cellvalue / 100.0).toFixed(2);
      }}, 
    {name: 'totalBrokerage', index: 'total_brokerage', width: 50, align: "center", formatter: function(cellvalue, options, rowObject){
        return (cellvalue / 100.0).toFixed(2);
    }}, 
    {name: 'pickupCode', index: 'pickup_code', width: 50, align: "center"}, 
    {name: 'regionName', index: 'region_id', width: 50, align: "center"}, 
    {name: 'distributorName', index: 'distributor_id', width: 50, align: "center"}, 
    {name: 'shopName', index: 'shop_name', width: 50, align: "center"}, 
    {name: 'shopMobile', index: 'shop_mobile', width: 50, align: "center"},
    {name: 'shopAddress', index: 'shop_address', width: 50, align: "center"}, 
    {name: 'type', index: 'type', width: 50, align: "center", formatter: 'select', editoptions: {value:'1:自主下单;2:代客下单'}},
    {name: 'name', index: 'name', width: 50, align: "center"}, 
    {name: 'mobile', index: 'mobile', width: 50, align: "center"}, 
    {name: 'deliveryType', index: 'delivery_type', width: 50, align: "center", formatter: 'select', editoptions: {value:'1:自提;2:快递'}}, 
    {name: 'deliveryAddress', index: 'delivery_address', width: 50, align: "center"}, 
    {name: 'deliveryName', index: 'delivery_name', width: 50, align: "center"}, 
    {name: 'deliveryMobile', index: 'delivery_mobile', width: 50, align: "center"}, 
    {name: 'createTime', index: 'create_time', width: 50, align: "center", formatter:'date', formatoptions: {newformat:'Y-m-d H:i:s'}}, 
    {align: "center", editable: false, sortable: false, formatter: function(cellvalue, options, rowObject){
      var temp = '';
      temp += '<a class="linetaga" href="javascript: orderHandle.view(\'' + rowObject.orderId.toFixed(0) + '\');" >查看详情</a>';
      if(hasAuthorize('order-editBefore')){
        temp += '<a class="linetaga" href="javascript: orderHandle.edit(\'' + rowObject.orderId.toFixed(0) + '\');" >编辑</a>';
      }
      if(rowObject.enabled==1){
        if(hasAuthorize('order-disabled')){
          temp += '<a class="linetaga" href="javascript: orderHandle.disabled(\'' + rowObject.orderId.toFixed(0) + '\');" >禁用</a>';
        }
      }else{
        if(hasAuthorize('order-enabled')){
          temp += '<a class="linetaga" href="javascript: orderHandle.enabled(\'' + rowObject.orderId.toFixed(0) + '\');" >启用</a>';
        }
      }
      return temp;
    }}
  ];
  var rowList = [10, 20, 30, 50];
  var rownumbers = true;
  var multiselect = true;
  var config={caption: "订单列表", colNames: colNames, colModel: colModel, rowList: rowList, rownumbers: rownumbers, multiselect: multiselect};
  orderHandle.init(config);
});