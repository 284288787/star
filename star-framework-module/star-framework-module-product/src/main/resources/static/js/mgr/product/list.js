var basePath = "/";
var productHandle = new ListHandle({
  basePath: basePath,
  tableId: '#grid-table',
  pagerId: '#grid-pager',
  formId: '#queryForm',
  entityName: '商品信息',
  winWidth: '80%',
  winHeight: '90%',
  primaryKey: 'productId',
  urls:{
    list: basePath+'product/list',
    addBefore: basePath+'common/mgr/product/addProduct',
    editBefore: basePath+'product/editBefore',
    enabled: basePath+'product/setPresell',
    disabled: basePath+'product/disabled',
    deleted: basePath+'product/deleted',
  }
},{});
$(function(){
  var colNames = ['操作', '主图', '商品分类', '商品ID', '商品状态', '商品标题', '副标题', '商品标签', '预售时间', '下架时间', '提货时间', '原价', '含税价', '未税价', '售价', '一级分销提成', '二级分销提成', '库存数量', '已售数量', '供应商', '供应商联系人', '供应商电话', '商品品牌', '商品规格', '商品产地', '关注人数', '更新日期', '更新人'];
  var colModel = [
    {align: "center", width: '90px', editable: false, sortable: false, formatter: function(cellvalue, options, rowObject){
      var temp = '';
      if(hasAuthorize('product-editBefore')){
        temp += '<a class="linetaga" href="javascript: productHandle.edit(\'' + rowObject.productId.toFixed(0) + '\');" >编辑</a>';
      }
//      if(hasAuthorize('product-setInventory')){
//        temp += '<a class="linetaga" href="javascript: productHandle.setInventory(\'' + rowObject.productId.toFixed(0) + '\');" >设置库存</a>';
//      }
      if(rowObject.state!=5){
        if(hasAuthorize('product-disabled')){
          temp += '<a class="linetaga" href="javascript: productHandle.disabled(\'' + rowObject.productId.toFixed(0) + '\');" >禁用</a>';
        }
      }else{
        if(hasAuthorize('product-enabled')){
          temp += '<a class="linetaga" href="javascript: productHandle.enabled(\'' + rowObject.productId.toFixed(0) + '\');" >启用</a>';
        }
      }
      return temp;
    }},
    {name: 'mainPictureUrl', index: 'mainPictureUrl', width: '70px', align: "center", editable: false, sortable: false, formatter: function(cellvalue, options, rowObject){
      return "<img src='http://mgr.hnkbmd.com"+cellvalue+"' height='60px'>";
    }}, 
    {name: 'cateName', index: 'cate_id', width: '70px', align: "center"}, 
    {name: 'productId', index: 'product_id', width: '50px', align: "center", formatter: function(cellvalue, options, rowObject){
      return cellvalue.toFixed(0);
    }}, 
    {name: 'state', index: 'state', width: '50px', align: "center", formatter: 'select', editoptions: {value:'1:上架;2:预售;3:售罄;4:下架;5:禁用;6:删除'}}, 
    {name: 'title', index: 'title', width: '80px', align: "center", formatter: function(cellvalue, options, rowObject){
      return "<div style='word-wrap: break-word;word-break:break-all;white-space:normal'>" + cellvalue + "</div>";
    }}, 
    {name: 'subtitle', index: 'subtitle', width: '80px', align: "center", formatter: function(cellvalue, options, rowObject){
      return "<div style='word-wrap: break-word;word-break:break-all;white-space:normal'>" + cellvalue + "</div>";
    }}, 
    {name: 'tag', index: 'tag', width: '70px', align: "center"}, 
    {name: 'presellTime', index: 'presell_time', width: '100px', align: "center", formatter: function(cellvalue, options, rowObject){
      if(cellvalue) return cellvalue.substring(0,13)+"点";
      else return "<span style='color:lightgray'>现在有货</span>"
    }}, 
    {name: 'offShelfTime', index: 'off_shelf_time', width: '100px', align: "center", formatter: function(cellvalue, options, rowObject){
      if(cellvalue) return cellvalue.substring(0,13)+"点";
      else return "<span style='color:lightgray'>永不下架</span>"
    }}, 
    {name: 'pickupTime', index: 'pickup_time', width: '100px', align: "center", formatter: function(cellvalue, options, rowObject){
      if(cellvalue) return cellvalue.substring(0,13)+"点";
      else return "<span style='color:lightgray'>随时提货</span>"
    }}, 
    {name: 'originalPrice', index: 'original_price', width: '90px', align: "center", formatter: function(cellvalue, options, rowObject){
      if(!cellvalue) return "";
      return (cellvalue/100.0).toFixed(2);
    }}, 
    {name: 'priceHan', index: 'price_han', width: '90px', align: "center", formatter: function(cellvalue, options, rowObject){
      if(!cellvalue) return "";
      return (cellvalue/100.0).toFixed(2);
    }}, 
    {name: 'priceWei', index: 'price_wei', width: '90px', align: "center", formatter: function(cellvalue, options, rowObject){
      if(!cellvalue) return "";
      return (cellvalue/100.0).toFixed(2);
    }}, 
    {name: 'price', index: 'price', width: '90px', align: "center", formatter: function(cellvalue, options, rowObject){
      return (cellvalue/100.0).toFixed(2);
    }}, 
    {name: 'brokerageFirst', index: 'brokerage_first', width: '90px', align: "center", formatter: function(cellvalue, options, rowObject){
      if(!cellvalue) return "";
      var val = (cellvalue/100.0).toFixed(2);
      return val;
    }}, 
    {name: 'brokerageValue', index: 'brokerage_value', width: '90px', align: "center", formatter: function(cellvalue, options, rowObject){
      if(!cellvalue) return "";
      var val = (cellvalue/100.0).toFixed(2);
      if(rowObject.brokerageType==2){
        val = (cellvalue/100.0).toFixed(1)+"%";
      }
      return val;
    }}, 
    {name: 'number', index: 'number', width: '90px', align: "center", formatter: function(cellvalue, options, rowObject){
      if(rowObject.numberType==2)return cellvalue;
      return "<span style='color:lightgray'>不限库存</span>";
    }}, 
    {name: 'soldNumber', index: 'soldNumber', width: '90px', align: "center"}, 
    {name: 'supplier', index: 'supplier', width: '90px', align: "center", formatter: function(cellvalue, options, rowObject){
      return "<div style='word-wrap: break-word;word-break:break-all;white-space:normal'>" + cellvalue + "</div>";
    }}, 
    {name: 'supplierName', index: 'supplier_name', width: '90px', align: "center"}, 
    {name: 'supplierMobile', index: 'supplier_mobile', width: '90px', align: "center"}, 
    {name: 'brand', index: 'brand', width: '90px', align: "center", formatter: function(cellvalue, options, rowObject){
      return "<div style='word-wrap: break-word;word-break:break-all;white-space:normal'>" + cellvalue + "</div>";
    }}, 
    {name: 'specification', index: 'specification', width: '90px', align: "center", formatter: function(cellvalue, options, rowObject){
      return "<div style='word-wrap: break-word;word-break:break-all;white-space:normal'>" + cellvalue + "</div>";
    }}, 
    {name: 'originPlace', index: 'origin_place', width: '90px', align: "center"}, 
    {name: 'subscribers', index: 'subscribers', width: '90px', align: "center"}, 
    {name: 'updateTime', index: 'update_time', width: '90px', align: "center", formatter:'date', formatoptions: {newformat:'Y-m-d'}}, 
    {name: 'updateUser', index: 'update_user', width: '90px', align: "center"}
  ];
  var rowList = [10, 20, 30, 50];
  var rownumbers = true;
  var multiselect = true;
  var config={autowidth: false, caption: "商品信息列表", colNames: colNames, colModel: colModel, rowList: rowList, rownumbers: rownumbers, multiselect: multiselect};
  productHandle.init(config);
});