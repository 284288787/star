var basePath = "/";
var distributionRegionHandle = new ListHandle({
  basePath: basePath,
  tableId: '#grid-table',
  pagerId: '#grid-pager',
  formId: '#queryForm',
  entityName: '分销区域',
  winWidth: '500px',
  winHeight: '480px',
  primaryKey: 'regionId',
  urls:{
    list: basePath+'distributionRegion/list',
    addBefore: basePath+'common/mgr/distributionRegion/addDistributionRegion',
    editBefore: basePath+'distributionRegion/editBefore',
    enabled: basePath+'distributionRegion/enabled',
    disabled: basePath+'distributionRegion/disabled',
    deleted: basePath+'distributionRegion/deleted',
  }
},{});
new UtilsHandle({
  chooseArea: {items: [
    {
      areaNameObj: $("input[name=areaName]"),
      callback: function(areas){
        $("input[name=provinceId]").val(areas.provinceId);
        $("input[name=cityId]").val(areas.cityId);
        $("input[name=areaId]").val(areas.areaId);
        $("input[name=townId]").val(areas.townId);
      }
    }
  ]}
},{});
$(function(){
  var colNames = ['区域ID', '区域名称', '拼音简称', '省份', '城市', '区县', '乡镇/街道', '区域状态', '创建日期', '更新日期', '粉丝数', '已售件数', '操作'];
  var colModel = [
    {name: 'regionId', index: 'region_id', width: 50, align: "center", formatter: function(cellvalue, options, rowObject){
      return cellvalue.toFixed(0);
    }}, 
    {name: 'name', index: 'name', width: 50, align: "center"}, 
    {name: 'py', index: 'py', width: 50, align: "center"}, 
    {name: 'provinceName', index: 'province_id', width: 50, align: "center"}, 
    {name: 'cityName', index: 'city_id', width: 50, align: "center"}, 
    {name: 'areaName', index: 'area_id', width: 50, align: "center"}, 
    {name: 'townName', index: 'town_id', width: 50, align: "center"}, 
    {name: 'state', index: 'state', width: 50, align: "center", formatter: 'select', editoptions: {value:'1:有效;2:禁用;3:删除'}}, 
    {name: 'createTime', index: 'create_time', width: 80, align: "center", formatter:'date', formatoptions: {srcformat: 'Y-m-d H:i:s', newformat:'Y-m-d H:i:s'}}, 
    {name: 'updateTime', index: 'update_time', width: 80, align: "center", formatter:'date', formatoptions: {srcformat: 'Y-m-d H:i:s', newformat:'Y-m-d H:i:s'}}, 
    {name: 'fansNum', index: 'fans_num', width: 50, align: "center"}, 
    {name: 'soldNum', index: 'sold_num', width: 50, align: "center"}, 
    {align: "center", editable: false, sortable: false, formatter: function(cellvalue, options, rowObject){
      var temp = '';
      if(hasAuthorize('distributionRegion-editBefore')){
        temp += '<a class="linetaga" href="javascript: distributionRegionHandle.edit(\'' + rowObject.regionId.toFixed(0) + '\');" >编辑</a>';
      }
      if(rowObject.state==1){
        if(hasAuthorize('distributionRegion-disabled')){
          temp += '<a class="linetaga" href="javascript: distributionRegionHandle.disabled(\'' + rowObject.regionId.toFixed(0) + '\');" >禁用</a>';
        }
      }else{
        if(hasAuthorize('distributionRegion-enabled')){
          temp += '<a class="linetaga" href="javascript: distributionRegionHandle.enabled(\'' + rowObject.regionId.toFixed(0) + '\');" >启用</a>';
        }
      }
      return temp;
    }}
  ];
  var rowList = [10, 20, 30, 50];
  var rownumbers = true;
  var multiselect = true;
  var config={caption: "分销区域列表", colNames: colNames, colModel: colModel, rowList: rowList, rownumbers: rownumbers, multiselect: multiselect};
  distributionRegionHandle.init(config);
});