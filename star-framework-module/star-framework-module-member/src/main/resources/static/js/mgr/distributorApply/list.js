var basePath = "/";
var distributorApplyHandle = new ListHandle({
  basePath: basePath,
  tableId: '#grid-table',
  pagerId: '#grid-pager',
  formId: '#queryForm',
  entityName: '分销商申请',
  winWidth: '500px',
  winHeight: '400px',
  primaryKey: 'id',
  urls:{
    list: basePath+'distributorApply/list',
    addBefore: basePath+'common/mgr/distributorApply/addDistributorApply',
    editBefore: basePath+'distributorApply/editBefore',
    enabled: basePath+'distributorApply/enabled',
    disabled: basePath+'distributorApply/disabled',
    deleted: basePath+'distributorApply/deleted',
  }
},{});
$(function(){
  var colNames = ['id', '手机号', '姓名', '店铺名称', '身份证1', '身份证2', '门店照片', '微信', '省', '省', '市', '市', '区县', '区县', '详细地址', '营业执照', '营业执照', '食品流通许可证', '食品流通许可证', '营业面积', '银行名', '开户行', '开户名', '银行卡号', '创建日期', 'openId', '操作'];
  var colModel = [
    {name: 'id', index: 'id', width: 50, align: "center", formatter: function(cellvalue, options, rowObject){
      return cellvalue.toFixed(0);
    }}, 
    {name: 'mobile', index: 'mobile', width: 50, align: "center"}, 
    {name: 'name', index: 'name', width: 50, align: "center"}, 
    {name: 'shopName', index: 'shop_name', width: 50, align: "center"}, 
    {name: 'idCardPic1', index: 'id_card_pic1', width: 50, align: "center"}, 
    {name: 'idCardPic2', index: 'id_card_pic2', width: 50, align: "center"}, 
    {name: 'shopPic', index: 'shop_pic', width: 50, align: "center"}, 
    {name: 'weixinPic', index: 'weixin_pic', width: 50, align: "center"}, 
    {name: 'provinceId', index: 'province_id', width: 50, align: "center"}, 
    {name: 'provinceName', index: 'province_name', width: 50, align: "center"}, 
    {name: 'cityId', index: 'city_id', width: 50, align: "center"}, 
    {name: 'cityName', index: 'city_name', width: 50, align: "center"}, 
    {name: 'areaId', index: 'area_id', width: 50, align: "center"}, 
    {name: 'areaName', index: 'area_name', width: 50, align: "center"}, 
    {name: 'address', index: 'address', width: 50, align: "center"}, 
    {name: 'businessLicense', index: 'business_license', width: 50, align: "center"}, 
    {name: 'businessLicensePic', index: 'business_license_pic', width: 50, align: "center"}, 
    {name: 'foodAllowanceLicense', index: 'food_allowance_license', width: 50, align: "center"}, 
    {name: 'foodAllowanceLicensePic', index: 'food_allowance_license_pic', width: 50, align: "center"}, 
    {name: 'acreage', index: 'acreage', width: 50, align: "center"}, 
    {name: 'bankName', index: 'bank_name', width: 50, align: "center"}, 
    {name: 'bankAddress', index: 'bank_address', width: 50, align: "center"}, 
    {name: 'bankCardName', index: 'bank_card_name', width: 50, align: "center"}, 
    {name: 'bankCardNo', index: 'bank_card_no', width: 50, align: "center"}, 
    {name: 'createTime', index: 'create_time', width: 50, align: "center", formatter:'date', formatoptions: {newformat:'Y-m-d H:i:s'}}, 
    {name: 'openId', index: 'open_id', width: 50, align: "center"}, 
    {align: "center", editable: false, sortable: false, formatter: function(cellvalue, options, rowObject){
      var temp = '';
      if(hasAuthorize('distributorApply-editBefore')){
        temp += '<a class="linetaga" href="javascript: distributorApplyHandle.edit(\'' + rowObject.id.toFixed(0) + '\');" >编辑</a>';
      }
      if(rowObject.enabled==1){
        if(hasAuthorize('distributorApply-disabled')){
          temp += '<a class="linetaga" href="javascript: distributorApplyHandle.disabled(\'' + rowObject.id.toFixed(0) + '\');" >禁用</a>';
        }
      }else{
        if(hasAuthorize('distributorApply-enabled')){
          temp += '<a class="linetaga" href="javascript: distributorApplyHandle.enabled(\'' + rowObject.id.toFixed(0) + '\');" >启用</a>';
        }
      }
      return temp;
    }}
  ];
  var rowList = [10, 20, 30, 50];
  var rownumbers = true;
  var multiselect = true;
  var config={caption: "分销商申请列表", colNames: colNames, colModel: colModel, rowList: rowList, rownumbers: rownumbers, multiselect: multiselect};
  distributorApplyHandle.init(config);
});