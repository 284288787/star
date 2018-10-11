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
    addBefore: basePath+'common/mgr/kickbackDetail/addKickbackDetail',
    editBefore: basePath+'kickbackDetail/editBefore',
    enabled: basePath+'kickbackDetail/enabled',
    disabled: basePath+'kickbackDetail/disabled',
    deleted: basePath+'kickbackDetail/deleted',
  }
},{});
$(function(){
  var colNames = ['主键', '分销商', '日期节点', '总金额', '创建日期', '订单状态', '未通过原因', '操作'];
  var colModel = [
    {name: 'id', index: 'id', width: 50, align: "center", formatter: function(cellvalue, options, rowObject){
      return cellvalue.toFixed(0);
    }}, 
    {name: 'distributorId', index: 'distributor_id', width: 50, align: "center"}, 
    {name: 'pointTime', index: 'point_time', width: 50, align: "center", formatter:'date', formatoptions: {newformat:'Y-m-d'}}, 
    {name: 'totalMoney', index: 'total_money', width: 50, align: "center"}, 
    {name: 'createTime', index: 'create_time', width: 50, align: "center", formatter:'date', formatoptions: {newformat:'Y-m-d H:i:s'}}, 
    {name: 'state', index: 'state', width: 50, align: "center", formatter: 'select', editoptions: {value:'1:审核中;2:汇款中;3:未通过;4:已完成'}}, 
    {name: 'reject', index: 'reject', width: 50, align: "center"}, 
    {align: "center", editable: false, sortable: false, formatter: function(cellvalue, options, rowObject){
      var temp = '';
      if(hasAuthorize('kickbackDetail-editBefore')){
        temp += '<a class="linetaga" href="javascript: kickbackDetailHandle.edit(\'' + rowObject.id.toFixed(0) + '\');" >编辑</a>';
      }
      if(rowObject.enabled==1){
        if(hasAuthorize('kickbackDetail-disabled')){
          temp += '<a class="linetaga" href="javascript: kickbackDetailHandle.disabled(\'' + rowObject.id.toFixed(0) + '\');" >禁用</a>';
        }
      }else{
        if(hasAuthorize('kickbackDetail-enabled')){
          temp += '<a class="linetaga" href="javascript: kickbackDetailHandle.enabled(\'' + rowObject.id.toFixed(0) + '\');" >启用</a>';
        }
      }
      return temp;
    }}
  ];
  var rowList = [10, 20, 30, 50];
  var rownumbers = true;
  var multiselect = true;
  var config={caption: "提成明细列表", colNames: colNames, colModel: colModel, rowList: rowList, rownumbers: rownumbers, multiselect: multiselect};
  kickbackDetailHandle.init(config);
});