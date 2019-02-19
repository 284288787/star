var basePath = "/";
var expressInformationHandle = new ListHandle({
  basePath: basePath,
  tableId: '#grid-table',
  pagerId: '#grid-pager',
  formId: '#queryForm',
  entityName: '物流信息',
  winWidth: '500px',
  winHeight: '400px',
  primaryKey: 'id',
  urls:{
    list: basePath+'expressInformation/list',
    addBefore: basePath+'common/mgr/expressInformation/addExpressInformation',
    editBefore: basePath+'expressInformation/editBefore',
    enabled: basePath+'expressInformation/enabled',
    disabled: basePath+'expressInformation/disabled',
    deleted: basePath+'expressInformation/deleted',
  }
},{
  importInfo: function(){
    artDialog.data("importHandle", "com.star.truffle.module.order.service.ImportExpressInformation");
    artDialog.open("common/upload/excels", {
      title : "导入快递信息",
      width : "90%",
      height : "90%",
      drag : true,
      resize : true,
      lock : true, 
      close:function(){ document.location.reload(); }
    });
  }
});
$(function(){
  var colNames = ['编号', '快递单号', '创建日期', '收件人', '收件人手机号', '收件人座机号', '收件人地址', '寄件人', '寄件人手机号', '寄件人座机号', '寄件人地址', '物品信息', '快递公司', '快递网点'];
  var colModel = [
    {name: 'code', index: 'code', width: 30, align: "center"}, 
    {name: 'trackingNumber', index: 'tracking_number', width: 100, align: "center"}, 
    {name: 'createTime', index: 'create_time', width: 140, align: "center", formatter:'date', formatoptions: {srcformat:'Y-m-d H:i:s',newformat:'Y-m-d H:i:s'}}, 
    {name: 'receiver', index: 'receiver', width: 50, align: "center"}, 
    {name: 'receiverMobile', index: 'receiver_mobile', width: 100, align: "center"}, 
    {name: 'receiverTel', index: 'receiver_tel', width: 100, align: "center"}, 
    {name: 'receiverAddress', index: 'receiver_address', width: 300, align: "center"}, 
    {name: 'sender', index: 'sender', width: 50, align: "center"}, 
    {name: 'senderMobile', index: 'sender_mobile', width: 100, align: "center"}, 
    {name: 'senderTel', index: 'sender_tel', width: 100, align: "center"}, 
    {name: 'senderAddress', index: 'sender_address', width: 250, align: "center"}, 
    {name: 'goodsInfo', index: 'goods_info', width: 80, align: "center"}, 
    {name: 'expressCompany', index: 'express_company', width: 120, align: "center"}, 
    {name: 'pointName', index: 'point_name', width: 50, align: "center"}, 
  ];
  var rowList = [10, 20, 30, 50];
  var rownumbers = true;
  var multiselect = true;
  var config={sortorder: "desc", dataType:'local', autowidth: false, caption: "物流信息列表", rowNum: 50, colNames: colNames, colModel: colModel, rowList: rowList, rownumbers: rownumbers, multiselect: multiselect};
  expressInformationHandle.init(config, {
    jsonReader: {
      repeatitems : false
    },
    shrinkToFit: false,
  });
  expressInformationHandle.query();
});