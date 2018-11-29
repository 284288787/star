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
},{
  top: function(id){
    productHandle.ajax({
      url : basePath+'product/top/' + id,
      success : function(res) {
        if (res.code == 0) {
          productHandle.query();
          artDialog.tips("置顶成功")
        } else {
          artDialog.tips(res.msg)
        }
      }
    });
  },
  up: function(id){
    productHandle.ajax({
      url : basePath+'product/up/' + id,
      success : function(res) {
        if (res.code == 0) {
          productHandle.query();
          artDialog.tips("上移成功")
        } else {
          artDialog.tips(res.msg)
        }
      }
    });
  },
  down: function(id){
    productHandle.ajax({
      url : basePath+'product/down/' + id,
      success : function(res) {
        if (res.code == 0) {
          productHandle.query();
          artDialog.tips("下移成功")
        } else {
          artDialog.tips(res.msg)
        }
      }
    });
  },
  sortBySoldNumber: function(){
    productHandle.ajax({
      url : basePath+'product/sortBySoldNumber',
      success : function(res) {
        if (res.code == 0) {
          productHandle.query();
          artDialog.tips("按销售数量降序排列完成")
        } else {
          artDialog.tips(res.msg)
        }
      }
    });
  },
  exportProduct: function(){
    var params = productHandle.getQueryParams();
    params["key"] = "product";
    params["handle"] = "com.star.truffle.module.order.service.ExportProduct";
    var url = '/download/excel/data?params=';
    url+=encodeURI(JSON.stringify(params));
    window.open(url);
  }
});
$(function(){
  new UtilsHandle({
    basePath: "/",
    choose: [
      {
        object: $("input[name=cateName]"),
        service: "categoryService",
        title: "选择商品分类",
        width: "800px",
        height: "500px",
        callback: function(rowObject){
          $("input[name=cateName]").val(rowObject.cateName);
          $("input[name=cateId]").val(rowObject.cateId);
        }
      }, {
        object: $("input[name=tag]"),
        service: "productTagService",
        title: "选择商品标签",
        width: "800px",
        height: "500px",
        callback: function(rowObject){
          $("input[name=tag]").val(rowObject.tagName);
        }
      }
    ],
  },{});
  
  var colNames = ['操作', '主图', '商品分类', '商品ID', '商品状态', '单买最大量', '商品标题', '副标题', '商品标签', '预售时间', '下架时间', '提货时间', '原价', '含税价', '未税价', '售价', '一级分销提成', '二级分销提成', '库存数量', '已售数量', '供应商', '供应商联系人', '供应商电话', '商品品牌', '商品规格', '商品产地', '关注人数', '更新日期', '更新人'];
  var colModel = [
    {align: "center", width: '160px', editable: false, sortable: false, frozen: true, formatter: function(cellvalue, options, rowObject){
      var temp = '';
      if(hasAuthorize('product-top')){
        temp += '<a class="linetaga" href="javascript: productHandle.top(\'' + rowObject.productId.toFixed(0) + '\');" >置顶</a>';
        temp += '<a class="linetaga" href="javascript: productHandle.up(\'' + rowObject.productId.toFixed(0) + '\');" >上移</a>';
        temp += '<a class="linetaga" href="javascript: productHandle.down(\'' + rowObject.productId.toFixed(0) + '\');" >下移</a>';
      }
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
    {name: 'mainPictureUrl', index: 'mainPictureUrl', width: '70px', align: "center", editable: false, frozen: true, sortable: false, formatter: function(cellvalue, options, rowObject){
      return "<img src='http://mgr.hnkbmd.com"+cellvalue+"' height='60px'>";
    }}, 
    {name: 'cateName', index: 'cate_id', width: '70px', align: "center", frozen: true}, 
    {name: 'productId', index: 'product_id', width: '50px', align: "center", frozen: true, formatter: function(cellvalue, options, rowObject){
      return cellvalue.toFixed(0);
    }}, 
    {name: 'state', index: 'state', width: '50px', align: "center", frozen: true, formatter: 'select', editoptions: {value:'1:上架;2:预售;3:售罄;4:下架;5:禁用;6:删除'}}, 
    {name: 'times', index: 'times', width: '80px', align: "center", formatter: function(cellvalue, options, rowObject){
      if(cellvalue==0) return "不限";
      return "<div style='word-wrap: break-word;word-break:break-all;white-space:normal'>" + cellvalue + "件</div>";
    }}, 
    {name: 'title', index: 'title', width: '80px', align: "center", formatter: function(cellvalue, options, rowObject){
      return "<div style='word-wrap: break-word;word-break:break-all;white-space:normal'>" + cellvalue + "</div>";
    }}, 
    {name: 'subtitle', index: 'subtitle', width: '80px', align: "center", formatter: function(cellvalue, options, rowObject){
      return "<div style='word-wrap: break-word;word-break:break-all;white-space:normal'>" + cellvalue + "</div>";
    }}, 
    {name: 'tag', index: 'tag', width: '70px', align: "center"}, 
    {name: 'presellTime', index: 'presell_time', width: '120px', align: "center", formatter: function(cellvalue, options, rowObject){
      if(cellvalue) return cellvalue.substring(0,13)+"点";
      else return "<span style='color:lightgray'>现在有货</span>"
    }}, 
    {name: 'offShelfTime', index: 'off_shelf_time', width: '120px', align: "center", formatter: function(cellvalue, options, rowObject){
      if(cellvalue) return cellvalue.substring(0,13)+"点";
      else return "<span style='color:lightgray'>永不下架</span>"
    }}, 
    {name: 'pickupTime', index: 'pickup_time', width: '120px', align: "center", formatter: function(cellvalue, options, rowObject){
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
    {name: 'updateTime', index: 'update_time', width: '140px', align: "center", formatter:'date', formatoptions: {srcformat: 'Y-m-d H:i:s', newformat:'Y-m-d H:i:s'}}, 
    {name: 'updateUser', index: 'update_user', width: '90px', align: "center"}
  ];
  var rowList = [10, 20, 30, 50];
  var rownumbers = true;
  var multiselect = true;
  var screenWidth = document.documentElement.clientWidth || document.body.clientWidth - 140;
  var config={rowNum: 50, autowidth: false, dataType:'local', caption: "商品信息列表", colNames: colNames, colModel: colModel, rowList: rowList, rownumbers: rownumbers, multiselect: multiselect};
  productHandle.init(config, {
    jsonReader: {
      repeatitems : false
    },
    shrinkToFit: false,
  });
  productHandle.query();
});