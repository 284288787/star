var basePath = "/";
var couponRelationHandle = new ListHandle({
  basePath: basePath,
  tableId: '#grid-table',
  pagerId: '#grid-pager',
  formId: '#queryForm',
  entityName: '卡券关联关系',
  winWidth: '500px',
  winHeight: '400px',
  primaryKey: 'id',
  urls:{
    list: basePath+'couponRelation/list',
    addBefore: basePath+'common/mgr/couponRelation/addCouponRelation',
    editBefore: basePath+'couponRelation/editBefore',
    enabled: basePath+'couponRelation/enabled',
    disabled: basePath+'couponRelation/disabled',
    deleted: basePath+'couponRelation/deleted',
  }
},{});
$(function(){
  var colNames = ['ID', '业务类型', '业务ID', '卡券ID', '操作'];
  var colModel = [
    {name: 'id', index: 'id', width: 50, align: "center", formatter: function(cellvalue, options, rowObject){
      return cellvalue.toFixed(0);
    }}, 
    {name: 'businessType', index: 'business_type', width: 50, align: "center", formatter: 'select', editoptions: {value:'1:大分类;2:商品分类;3:商品;4:会员'}}, 
    {name: 'businessId', index: 'business_id', width: 50, align: "center"}, 
    {name: 'couponId', index: 'coupon_id', width: 50, align: "center"}, 
    {align: "center", editable: false, sortable: false, formatter: function(cellvalue, options, rowObject){
      var temp = '';
      if(hasAuthorize('couponRelation-editBefore')){
        temp += '<a class="linetaga" href="javascript: couponRelationHandle.edit(\'' + rowObject.id.toFixed(0) + '\');" >编辑</a>';
      }
      if(rowObject.enabled==1){
        if(hasAuthorize('couponRelation-disabled')){
          temp += '<a class="linetaga" href="javascript: couponRelationHandle.disabled(\'' + rowObject.id.toFixed(0) + '\');" >禁用</a>';
        }
      }else{
        if(hasAuthorize('couponRelation-enabled')){
          temp += '<a class="linetaga" href="javascript: couponRelationHandle.enabled(\'' + rowObject.id.toFixed(0) + '\');" >启用</a>';
        }
      }
      return temp;
    }}
  ];
  var rowList = [10, 20, 30, 50];
  var rownumbers = true;
  var multiselect = true;
  var config={caption: "卡券关联关系列表", colNames: colNames, colModel: colModel, rowList: rowList, rownumbers: rownumbers, multiselect: multiselect};
  couponRelationHandle.init(config);
});