var basePath = "/";
var distributorHandle = new ListHandle({
  basePath: basePath,
  tableId: '#grid-table',
  pagerId: '#grid-pager',
  formId: '#queryForm',
  entityName: '分销商',
  winWidth: '700px',
  winHeight: '400px',
  primaryKey: 'distributorId',
  urls:{
    list: basePath+'distributor/list',
    addBefore: basePath+'common/mgr/distributor/addDistributor',
    editBefore: basePath+'distributor/editBefore',
    enabled: basePath+'distributor/enabled',
    disabled: basePath+'distributor/disabled',
    deleted: basePath+'distributor/deleted',
  }
},{});
new UtilsHandle({
  basePath: basePath,
  chooseArea: {items: [
    {
      areaNameObj: $("input[name=areaName]"),
      onClick: function(){
      },
      callback: function(areas){
        $("input[name=provinceId],input[name=cityId],input[name=areaId],input[name=townd]").val("");
        var condition = {};
        if(areas.provinceId){
          $("input[name=provinceId]").val(areas.provinceId);
          condition["provinceId"] = areas.provinceId;
        }
        if(areas.cityId){
          $("input[name=cityId]").val(areas.cityId);
          condition["cityId"] = areas.cityId;
        }
        if(areas.areaId){
          $("input[name=areaId]").val(areas.areaId);
          condition["areaId"] = areas.areaId;
        }
        if(areas.townId){
          $("input[name=townd]").val(areas.townId);
          condition["townId"] = areas.townId;
        }
        $("#condition").val(JSON.stringify(condition));
      }
    }
  ]},
  choose: [
    {
      object: $("input[name=regionName]"),
      service: "distributionRegionService",
      title: "选择分销地区",
      width: "800px",
      height: "500px",
      valid: function(){
        return {valid: true};//{valid:$("#condition").val() != "", msg: "请先选择地区"};
      },
      condition: function(){
        var cond = $("#condition").val();
        if(cond){
          var con = JSON.parse(cond);
          con['areaName'] = $("input[name=areaName]").val();
          return JSON.stringify(con);
        }else{
          return "";
        }
      },
      callback: function(rowObject){
        $("input[name=regionName]").val(rowObject.name);
        $("input[name=regionId]").val(rowObject.regionId);
      }
    }
  ]
},{});
$(function(){
  var colNames = ['操作', '是否可用', 'distributorId', '姓名', '店铺名称', '店铺编码', '手机号', '粉丝数', '已售件数', '分销区域拼音', '分销区域', '街道地址', '更新日期', 'openid', '营业执照', '食品流通许可证', '营业面积', '开户行', '开户名', '银行卡号'];
  var colModel = [
    {align: "center", width: '80px', editable: false, sortable: false, frozen: true, formatter: function(cellvalue, options, rowObject){
      var temp = '';
      if(hasAuthorize('distributor-editBefore')){
        temp += '<a class="linetaga" href="javascript: distributorHandle.edit(\'' + rowObject.distributorId.toFixed(0) + '\');" >编辑</a>';
      }
      if(rowObject.enabled==1){
        if(hasAuthorize('distributor-disabled')){
          temp += '<a class="linetaga" href="javascript: distributorHandle.disabled(\'' + rowObject.distributorId.toFixed(0) + '\');" >禁用</a>';
        }
      }else{
        if(hasAuthorize('distributor-enabled')){
          temp += '<a class="linetaga" href="javascript: distributorHandle.enabled(\'' + rowObject.distributorId.toFixed(0) + '\');" >启用</a>';
        }
      }
      return temp;
    }},
    {name: 'enabled', index: 'enabled', width: 50, align: "center", frozen: true, formatter: 'select', editoptions: {value:'1:可用;0:禁用'}},
    {name: 'distributorId', index: 'distributor_id', width: '80px', frozen: true, align: "center", formatter: function(cellvalue, options, rowObject){
      return cellvalue.toFixed(0);
    }}, 
    {name: 'name', index: 'name', width: '100px', align: "center", frozen: true}, 
    {name: 'shopName', index: 'shop_name', width: '260px', align: "center", frozen: true}, 
    {name: 'shopCode', index: 'shop_code', width: '70px', align: "center", frozen: true}, 
    {name: 'mobile', index: 'mobile', width: '100px', align: "center"}, 
    {name: 'fansNum', index: 'fans_num', width: '80px', align: "center"}, 
    {name: 'soldNum', index: 'sold_num', width: '80px', align: "center"}, 
    {name: 'py', index: 'py', width: '80px', align: "center"}, 
    {name: 'regionName', index: 'regionName', width: '370px', align: "center", formatter: function(cellvalue, options, rowObject){
      var temp = cellvalue;
      if(rowObject.townName) temp = rowObject.townName + "-" + temp;
      if(rowObject.areaName) temp = rowObject.areaName + "-" + temp;
      if(rowObject.cityName) temp = rowObject.cityName + "-" + temp;
      if(rowObject.provinceName) temp = rowObject.provinceName + "-" + temp;
      return temp;
    }}, 
    {name: 'address', index: 'address', width: '300px', align: "center"}, 
    {name: 'updateTime', index: 'update_time', width: '140px', align: "center", formatter:'date', formatoptions: {srcformat: 'Y-m-d H:i:s', newformat:'Y-m-d H:i:s'}}, 
    {name: 'openId', index: 'open_id', width: '200px', align: "center", formatter: function(cellvalue, options, rowObject){
      var temp = cellvalue;
      if(! temp) temp = "<span style='color:lightgray'>老板从未登录过</span>";
      return temp;
    }}, 
    {editable: false, sortable: false, width: '100px', align: "center", formatter: function(cellvalue, options, rowObject){
      if(!cellvalue) return "";
      var temp = '<span style="word-wrap: break-word;word-break: break-all;white-space: pre-wrap !important;">'+rowObject.businessLicense+'</span><img class="dataImg" src="'+rowObject.businessLicensePic+'" height="60px;">';
      return temp;
    }}, 
    {editable: false, sortable: false, width: '100px', align: "center", formatter: function(cellvalue, options, rowObject){
      if(!cellvalue) return "";
      var temp = '<span style="word-wrap: break-word;word-break: break-all;white-space: pre-wrap !important;">'+rowObject.foodAllowanceLicense+'</span><img class="dataImg" src="'+rowObject.foodAllowanceLicensePic+'" height="60px;">';
      return temp;
    }}, 
    {name: 'acreage', index: 'acreage', width: '100px', align: "center"}, 
    {name: 'bankAddress', index: 'bank_address', width: '300px', align: "center", formatter: function(cellvalue, options, rowObject){
      if(!cellvalue) return "";
      var temp = '<span style="word-wrap: break-word;word-break: break-all;white-space: pre-wrap !important;">'+cellvalue+'</span>';
      return temp;
    }}, 
    {name: 'bankCardName', index: 'bank_card_name', width: '100px', align: "center"}, 
    {name: 'bankCardNo', index: 'bank_card_no', width: '200px', align: "center", formatter: function(cellvalue, options, rowObject){
      if(!cellvalue) return "";
      var temp = '<span style="word-wrap: break-word;word-break: break-all;white-space: pre-wrap !important;">'+cellvalue+'</span>';
      return temp;
    }}
  ];
  var rowList = [10, 20, 30, 50];
  var rownumbers = true;
  var multiselect = true;
  var config={rowNum: 50, caption: "分销商列表", dataType:"local", autowidth: false, colNames: colNames, colModel: colModel, rowList: rowList, rownumbers: rownumbers, multiselect: multiselect};
  distributorHandle.init(config, {
    jsonReader: {
      repeatitems : false
    },
    shrinkToFit: false,
  });
});