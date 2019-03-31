var basePath = "/";
var couponHandle = new ListHandle({
  basePath: basePath,
  tableId: '#grid-table',
  pagerId: '#grid-pager',
  formId: '#queryForm',
  entityName: '卡券',
  winWidth: '600px',
  winHeight: '300px',
  primaryKey: 'couponId',
  urls:{
    list: basePath+'coupon/list',
    addBefore: basePath+'common/mgr/coupon/addCoupon',
    editBefore: basePath+'coupon/editBefore',
    enabled: basePath+'coupon/enabled',
    disabled: basePath+'coupon/disabled',
    deleted: basePath+'coupon/deleted',
  }
},{});
$(function(){
  var colNames = ['卡券ID', '卡券标题', '微信卡券Id', '创建日期', '操作'];
  var colModel = [
    {name: 'couponId', index: 'coupon_id', width: 50, align: "center", formatter: function(cellvalue, options, rowObject){
      return cellvalue.toFixed(0);
    }}, 
    {name: 'title', index: 'title', width: 50, align: "center"}, 
    {name: 'cardId', index: 'card_id', width: 50, align: "center"}, 
    {name: 'createTime', index: 'create_time', width: 50, align: "center", formatter:'date', formatoptions: {newformat:'Y-m-d'}}, 
    {align: "center", editable: false, sortable: false, formatter: function(cellvalue, options, rowObject){
      var temp = '';
      if(hasAuthorize('coupon-editBefore')){
        temp += '<a class="linetaga" href="javascript: couponHandle.edit(\'' + rowObject.couponId.toFixed(0) + '\');" >编辑</a>';
      }
      if(rowObject.enabled==1){
        if(hasAuthorize('coupon-disabled')){
          temp += '<a class="linetaga" href="javascript: couponHandle.disabled(\'' + rowObject.couponId.toFixed(0) + '\');" >禁用</a>';
        }
      }else{
        if(hasAuthorize('coupon-enabled')){
          temp += '<a class="linetaga" href="javascript: couponHandle.enabled(\'' + rowObject.couponId.toFixed(0) + '\');" >启用</a>';
        }
      }
      return temp;
    }}
  ];
  var rowList = [10, 20, 30, 50];
  var rownumbers = true;
  var multiselect = true;
  var config={caption: "卡券列表", colNames: colNames, colModel: colModel, rowList: rowList, rownumbers: rownumbers, multiselect: multiselect};
  couponHandle.init(config);
});