var basePath = "/";
var orderHandle = new ListHandle({
  basePath: basePath,
  tableId: '#grid-table',
  pagerId: '#grid-pager',
  formId: '#queryForm',
  entityName: '订单',
  winWidth: '900px',
  winHeight: '550px',
  primaryKey: 'orderId',
  urls:{
    list: basePath+'order/list',
    addBefore: basePath+'common/mgr/order/addOrder',
    viewBefore: basePath+'order/editBefore'
  }
},{
  exportTransportOrder: function(){
    artDialog.open(basePath+'common/mgr/order/exportTransportOrder', {
      title : "导出订单",
      width : "600px",
      height : "300px",
      drag : true,
      resize : true,
      lock : true
    });
  },
  deliverGoods: function(orderId){
    artDialog.prompt("填写快递单号：", function(expressNumber) {
      expressNumber = $.trim(expressNumber);
      if(! expressNumber){
        artDialog.alert("请填写快递单号");
        return false;
      }
      orderHandle.ajax({
        url : '/order/deliverGoods',
        data: {orderId: orderId, expressNumber: expressNumber},
        success : function(res) {
          if (res.code == 0) {
            artDialog.tips("已发货修改成功")
            orderHandle.query();
          } else {
            artDialog.alert(res.msg)
          }
        }
      });
    });
  },
  deliverGoodsFinish: function(orderId){
    artDialog.confirm("确定已完成？：", function() {
      orderHandle.ajax({
        url : '/order/deliverGoodsFinish',
        data: {orderId: orderId},
        success : function(res) {
          if (res.code == 0) {
            artDialog.tips("修改成功")
            orderHandle.query();
          } else {
            artDialog.alert(res.msg)
          }
        }
      });
    });
  },
});
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
$(function(){
  var states={1:'待付款',2:'待提货',3:'已提货',4:'已退货',5:'已删除'};
  var transportStates={1:'待发货',2:'已发货',3:'已完成'};
  var colors={1:'#e87108',2:'#ad30de',3:'green',4:'red',5:'gray'}
  var colNames = ['操作', '订单ID', '订单状态', '运输状态', '快递单号', '订单编号', '总金额', '总提成', '提货码', '订单类型', '用户姓名', '用户手机号', '收货类型', '收货地址', '收件人', '收件人手机', '分销区域', '分销商', '店铺名称', '店铺电话', '店铺地址', '创建日期'];
  var colModel = [
    {align: "center", width: "130px", editable: false, sortable: false, formatter: function(cellvalue, options, rowObject){
      var temp = '';
      if(hasAuthorize('order-editBefore')){
        if(rowObject.state >= 2){
          if(rowObject.transportState == 1 || rowObject.transportState == null){
            if(hasAuthorize('order-deliverGoods'))
              temp += '<a class="linetaga" href="javascript: orderHandle.deliverGoods(\'' + rowObject.orderId.toFixed(0) + '\');" >已发货</a>';
          }else if(rowObject.transportState == 2){
            if(hasAuthorize('order-deliverGoodsFinish'))
              temp += '<a class="linetaga" href="javascript: orderHandle.deliverGoodsFinish(\'' + rowObject.orderId.toFixed(0) + '\');" >已完成</a>';
          } 
        }
      }
      temp += '<a class="linetaga" href="javascript: orderHandle.view(\'' + rowObject.orderId.toFixed(0) + '\');" >查看详情</a>';
      return temp;
    }},
    {name: 'orderId', index: 'order_id', width: "60px", align: "center", formatter: function(cellvalue, options, rowObject){
      return cellvalue.toFixed(0);
    }}, 
    {name: 'state', index: 'state', width: "70px", align: "center", formatter: function(cellvalue, options, rowObject){
      return '<span style="color:'+colors[cellvalue]+'">'+states[cellvalue]+'</span>';
    }}, 
    {name: 'transportState', index: 'transport_state', width: "70px", align: "center", formatter: function(cellvalue, options, rowObject){
      if(!cellvalue) return "";
      return '<span style="color:'+colors[cellvalue]+'">'+transportStates[cellvalue]+'</span>';
    }}, 
    {name: 'expressNumber', index: 'express_number', width: "100px", align: "center"}, 
    {name: 'orderCode', index: 'order_code', width: "100px", align: "center"}, 
    {name: 'totalMoney', index: 'total_money', width: "70px", align: "center", formatter: function(cellvalue, options, rowObject){
        return (cellvalue / 100.0).toFixed(2);
      }}, 
    {name: 'totalBrokerage', index: 'total_brokerage', width: "70px", align: "center", formatter: function(cellvalue, options, rowObject){
        return (cellvalue / 100.0).toFixed(2);
    }}, 
    {name: 'pickupCode', index: 'pickup_code', width: "70px", align: "center"}, 
    {name: 'type', index: 'type', width: "60px", align: "center", formatter: 'select', editoptions: {value:'1:自主下单;2:代客下单'}},
    {name: 'name', index: 'name', width: "120px", align: "center", formatter: function(cellvalue, options, rowObject){
      return "<div style='word-wrap: break-word;word-break:break-all;white-space:normal'>" + cellvalue + "</div>";
    }}, 
    {name: 'mobile', index: 'mobile', width: "100px", align: "center"}, 
    {name: 'deliveryType', index: 'delivery_type', width: "60px", align: "center", formatter: 'select', editoptions: {value:'1:自提;2:快递'}}, 
    {name: 'deliveryAddress', index: 'delivery_address', width: "170px", align: "center", formatter: function(cellvalue, options, rowObject){
      if(!cellvalue) return "";
      var temp = "";
      if(rowObject.provinceName) temp += rowObject.provinceName;
      if(rowObject.cityName) temp += rowObject.cityName;
      if(rowObject.areaName) temp += rowObject.areaName;
      temp += cellvalue;
      return "<div style='word-wrap: break-word;word-break:break-all;white-space:normal'>" + temp + "</div>";
    }}, 
    {name: 'deliveryName', index: 'delivery_name', width: "120px", align: "center"}, 
    {name: 'deliveryMobile', index: 'delivery_mobile', width: "100px", align: "center"}, 
    {name: 'regionName', index: 'region_id', width: "70px", align: "center"}, 
    {name: 'distributorName', index: 'distributor_id', width: "90px", align: "center"}, 
    {name: 'shopName', index: 'shop_name', width: "120px", align: "center", formatter: function(cellvalue, options, rowObject){
      return "<div style='word-wrap: break-word;word-break:break-all;white-space:normal'>" + cellvalue + "</div>";
    }}, 
    {name: 'shopMobile', index: 'shop_mobile', width: "100px", align: "center"},
    {name: 'shopAddress', index: 'shop_address', width: "120px", align: "center", formatter: function(cellvalue, options, rowObject){
      return "<div style='word-wrap: break-word;word-break:break-all;white-space:normal'>" + cellvalue + "</div>";
    }}, 
    {name: 'createTime', index: 'create_time', width: "120px", align: "center", formatter:'date', formatoptions: {newformat:'Y-m-d H:i:s'}}, 
  ];
  var rowList = [10, 20, 30, 50];
  var rownumbers = true;
  var multiselect = true;
  var config={caption: "订单列表", autowidth: false, colNames: colNames, colModel: colModel, rowList: rowList, rownumbers: rownumbers, multiselect: multiselect};
  orderHandle.init(config);
});