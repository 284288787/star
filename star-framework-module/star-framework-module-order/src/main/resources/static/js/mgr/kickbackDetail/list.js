var basePath = "/";
var kickbackDetailHandle = new ListHandle({
  basePath: basePath,
  tableId: '#grid-table',
  pagerId: '#grid-pager',
  formId: '#queryForm',
  entityName: '提成明细',
  winWidth: '500px',
  winHeight: '400px',
  primaryKey: 'id',
  urls:{
    list: basePath+'kickbackDetail/list',
  }
},{
  pass: function(id){
    artDialog.confirm("确认审核通过？", function() {
      kickbackDetailHandle.ajax({
        url : '/kickbackDetail/pass/'+id,
        success : function(res) {
          if (res.code == 0) {
            kickbackDetailHandle.query();
          } else {
            artDialog.alert(res.msg)
          }
        }
      });
    });
  },
  finish: function(id){
    artDialog.confirm("确认已完成汇款？", function() {
      kickbackDetailHandle.ajax({
        url : '/kickbackDetail/finish/'+id,
        success : function(res) {
          if (res.code == 0) {
            kickbackDetailHandle.query();
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
      kickbackDetailHandle.ajax({
        url : '/kickbackDetail/nopass/'+id,
        data: {reject: reject},
        success : function(res) {
          if (res.code == 0) {
            kickbackDetailHandle.query();
          } else {
            artDialog.alert(res.msg)
          }
        }
      });
    });
  },
  detail: function(id){
    artDialog.data("params", {'id':id});
    artDialog.open("/common/mgr/kickbackDetail/detail", {
      title : "提成明细",
      width : "90%",
      height : "600px",
      drag : true,
      resize : true,
      lock : true
    });
  }
});
$(function(){
  var colNames = ['主键', '分销商', '分销商电话', '分销区域', '区域名称', '起始日期', '终止日期', '总金额', '创建日期', '订单状态', '未通过原因', '操作'];
  var colModel = [
    {name: 'id', hidden: true, index: 'id', width: 50, align: "center", formatter: function(cellvalue, options, rowObject){
      return cellvalue.toFixed(0);
    }}, 
    {name: 'distributorName', index: 'distributor_name', width: 50, align: "center"}, 
    {name: 'distributorMobile', index: 'distributorMobile', sortable: false, width: 50, align: "center"}, 
    {sortable: false, width: 100, align: "center", formatter: function(cellvalue, options, rowObject){
      var temp = rowObject.provinceName;
      if(rowObject.cityName) temp += rowObject.cityName;
      if(rowObject.areaName) temp += rowObject.areaName;
      if(rowObject.townName) temp += rowObject.townName;
      return temp;
    }}, 
    {name: 'regionName', index: 'regionName', sortable: false, width: 70, align: "center"}, 
    {name: 'pointBeginTime', index: 'point_begin_time', width: 80, align: "center", formatter:'date', formatoptions: {srcformat: 'Y-m-d H:i:s', newformat:'Y-m-d H:i:s'}}, 
    {name: 'pointEndTime', index: 'point_end_time', width: 80, align: "center", formatter:'date', formatoptions: {srcformat: 'Y-m-d H:i:s', newformat:'Y-m-d H:i:s'}}, 
    {name: 'totalMoney', index: 'total_money', width: 50, align: "center", formatter: function(cellvalue, options, rowObject){
      return (cellvalue / 100.0).toFixed(2);
    }}, 
    {name: 'createTime', index: 'create_time', width: 80, align: "center", formatter:'date', formatoptions: {srcformat: 'Y-m-d H:i:s', newformat:'Y-m-d H:i:s'}}, 
    {name: 'state', index: 'state', width: 50, align: "center", formatter: 'select', editoptions: {value:'1:审核中;2:汇款中;3:未通过;4:已完成'}}, 
    {name: 'reject', index: 'reject', align: "center", formatter: function(cellvalue, options, rowObject){
      if(rowObject.state==3) return cellvalue;
      return "";
    }}, 
    {align: "center", editable: false, width: 130, sortable: false, formatter: function(cellvalue, options, rowObject){
      var temp = '';
      temp += '<a class="linetaga" href="javascript: kickbackDetailHandle.detail(\'' + rowObject.id.toFixed(0) + '\');" >查看明细</a>';
      if(hasAuthorize('kickbackDetail-pass')){
        temp += '<a class="linetaga" href="javascript: kickbackDetailHandle.pass(\'' + rowObject.id.toFixed(0) + '\');" >审核通过</a>';
      }
      if(hasAuthorize('kickbackDetail-nopass')){
        temp += '<a class="linetaga" href="javascript: kickbackDetailHandle.nopass(\'' + rowObject.id.toFixed(0) + '\');" >审核不通过</a>';
      }
      if(hasAuthorize('kickbackDetail-finish')){
        temp += '<a class="linetaga" href="javascript: kickbackDetailHandle.finish(\'' + rowObject.id.toFixed(0) + '\');" >已汇款</a>';
      }
      return temp;
    }}
  ];
  var rowList = [10, 20, 30, 50];
  var rownumbers = true;
  var multiselect = true;
  var config={caption: "提成列表", colNames: colNames, colModel: colModel, rowList: rowList, rownumbers: rownumbers, multiselect: multiselect};
  kickbackDetailHandle.init(config);
});