var basePath = "/";
var memberHandle = new ListHandle({
  basePath: basePath,
  tableId: '#grid-table',
  pagerId: '#grid-pager',
  formId: '#queryForm',
  entityName: '消费者',
  winWidth: '500px',
  winHeight: '400px',
  primaryKey: 'memberId',
  urls:{
    list: basePath+'member/list',
    addBefore: basePath+'common/mgr/member/addMember',
    editBefore: basePath+'member/editBefore',
    enabled: basePath+'member/enabled',
    disabled: basePath+'member/disabled',
    deleted: basePath+'member/deleted',
  }
},{});
$(function(){
  var colNames = ['memberId', '姓名', '手机号', 'openid', '创建日期'];
  var colModel = [
    {name: 'memberId', index: 'member_id', width: 50, align: "center", formatter: function(cellvalue, options, rowObject){
      return cellvalue.toFixed(0);
    }}, 
    {name: 'name', index: 'name', width: 50, align: "center"}, 
    {name: 'mobile', index: 'mobile', width: 50, align: "center"}, 
    {name: 'openId', index: 'open_id', width: 50, align: "center"}, 
    {name: 'createTime', index: 'create_time', width: 50, align: "center", formatter:'date', formatoptions: {newformat:'Y-m-d'}}
  ];
  var rowList = [10, 20, 30, 50];
  var rownumbers = true;
  var multiselect = true;
  var config={caption: "消费者列表", colNames: colNames, colModel: colModel, rowList: rowList, rownumbers: rownumbers, multiselect: multiselect};
  memberHandle.init(config);
});