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
    deleted: basePath+'distributorApply/deleted',
  }
},{
  nopass: function(id){
    artDialog.prompt("驳回原因：", function(reject) {
      if(! reject){
        artDialog.alert("原因必填");
        return false;
      }
      distributorApplyHandle.ajax({
        url : '/distributorApply/nopass/'+id,
        data: {reject: reject},
        success : function(res) {
          if (res.code == 0) {
            distributorApplyHandle.query();
          } else {
            artDialog.alert(res.msg)
          }
        }
      });
    });
  },
  pass: function(id){
    artDialog.data("id", id);
    artDialog.data("distributorApplyHandle", distributorApplyHandle);
    artDialog.open(basePath+'common/mgr/distributorApply/pass', {
      title : "确认开通分销商？",
      width : '400px',
      height : '150px',
      drag : true,
      resize : true,
      lock : true
    });
  }
});
var screenHeight = distributorApplyHandle.getScreenHeight();
$(function(){
  var colNames = ['id', '状态', '手机号', '姓名', '店铺名称', '身份证', '门店照片', '微信', '省', '市', '区县', '详细地址', '营业执照', '食品流通许可证', '营业面积', '开户行', '开户名', '银行卡号', '创建日期', '操作'];
  var colModel = [
    {name: 'id', index: 'id', width: 20, align: "center", formatter: function(cellvalue, options, rowObject){
      return cellvalue.toFixed(0);
    }}, 
    {name: 'mobile', index: 'mobile', width: 50, align: "center"}, 
    {name: 'state', index: 'state', width: 30, align: "center", formatter: 'select', editoptions: {value:'1:待审核;2:已开通;3:已驳回;4:已删除'}}, 
    {name: 'name', index: 'name', width: 50, align: "center"}, 
    {name: 'shopName', index: 'shop_name', width: 50, align: "center"}, 
    {editable: false, sortable: false, width: '140px', align: "center", formatter: function(cellvalue, options, rowObject){
      var temp = '<img class="dataImg" src="'+rowObject.idCardPic1+'" height="60px;"> &nbsp;';
      temp += '<img class="dataImg" src="'+rowObject.idCardPic2+'" height="60px;">';
      return temp;
    }}, 
    {editable: false, sortable: false, width: 50, align: "center", formatter: function(cellvalue, options, rowObject){
      var temp = '<img class="dataImg" src="'+rowObject.shopPic+'" height="60px;">';
      return temp;
    }}, 
    {editable: false, sortable: false, width: 50, align: "center", formatter: function(cellvalue, options, rowObject){
      var temp = '<img class="dataImg" src="'+rowObject.weixinPic+'" height="60px;">';
      return temp;
    }}, 
    {name: 'provinceName', index: 'province_name', width: 30, align: "center"}, 
    {name: 'cityName', index: 'city_name', width: 40, align: "center"}, 
    {name: 'areaName', index: 'area_name', width: 50, align: "center"}, 
    {name: 'address', index: 'address', width: 50, align: "center", formatter: function(cellvalue, options, rowObject){
      var temp = '<span style="word-wrap: break-word;word-break: break-all;white-space: pre-wrap !important;">'+cellvalue+'</span>';
      return temp;
    }}, 
    {editable: false, sortable: false, width: 50, align: "center", formatter: function(cellvalue, options, rowObject){
      var temp = '<span style="word-wrap: break-word;word-break: break-all;white-space: pre-wrap !important;">'+rowObject.businessLicense+'</span><img class="dataImg" src="'+rowObject.businessLicensePic+'" height="60px;">';
      return temp;
    }}, 
    {editable: false, sortable: false, width: 50, align: "center", formatter: function(cellvalue, options, rowObject){
      var temp = '<span style="word-wrap: break-word;word-break: break-all;white-space: pre-wrap !important;">'+rowObject.foodAllowanceLicense+'</span><img class="dataImg" src="'+rowObject.foodAllowanceLicensePic+'" height="60px;">';
      return temp;
    }}, 
    {name: 'acreage', index: 'acreage', width: 40, align: "center"}, 
    {name: 'bankAddress', index: 'bank_address', width: 100, align: "center", formatter: function(cellvalue, options, rowObject){
      var temp = '<span style="word-wrap: break-word;word-break: break-all;white-space: pre-wrap !important;">'+cellvalue+'</span>';
      return temp;
    }}, 
    {name: 'bankCardName', index: 'bank_card_name', width: 50, align: "center"}, 
    {name: 'bankCardNo', index: 'bank_card_no', width: 50, align: "center", formatter: function(cellvalue, options, rowObject){
      var temp = '<span style="word-wrap: break-word;word-break: break-all;white-space: pre-wrap !important;">'+cellvalue+'</span>';
      return temp;
    }}, 
    {name: 'createTime', index: 'create_time', width: 80, align: "center", formatter:'date', formatoptions: {newformat:'Y-m-d H:i:s'}}, 
    {align: "center", editable: false, sortable: false, width: 80, formatter: function(cellvalue, options, rowObject){
      var temp = '';
      if(rowObject.state != 2){
        if(hasAuthorize('distributorApply-pass')){
          temp += '<a class="linetaga" href="javascript: distributorApplyHandle.pass(\'' + rowObject.id.toFixed(0) + '\');" >开通分销商</a>';
        }
        if(hasAuthorize('distributorApply-nopass')){
          temp += '<a class="linetaga" href="javascript: distributorApplyHandle.nopass(\'' + rowObject.id.toFixed(0) + '\');" >驳回</a>';
        }
      }
      return temp;
    }}
  ];
  var rowList = [10, 20, 30, 50];
  var rownumbers = true;
  var multiselect = true;
  var config={caption: "分销商申请列表", colNames: colNames, colModel: colModel, rowList: rowList, rownumbers: rownumbers, multiselect: multiselect, callback: function(){
    $('.dataImg').unbind().click(function(){
      if($('#viewImg').length>0){ 
        $('#viewImg').remove(); 
      } 
      var img = new Image();
      img.src = $(this).attr("src");
      img.onload = function(){
        var w=this.width;
        var h=this.height;
        var l = w / h;
        if(h > screenHeight * 0.85){
          h = screenHeight * 0.85;
          w = h * l;
        }
        artDialog.alert2('<div style="width:'+w+'px;height:'+h+'px;"><img style="height:'+h+'px" src="'+img.src+'">') 
      }
    }); 
  }};
  distributorApplyHandle.init(config);
});