var basePath = "/";
var categoryHandle = new ListHandle({
  basePath: basePath,
  tableId: '#grid-table',
  pagerId: '#grid-pager',
  formId: '#queryForm',
  entityName: '商品分类',
  winWidth: '350px',
  winHeight: '300px',
  primaryKey: 'cateId',
  urls:{
    list: basePath+'category/list',
    addBefore: basePath+'common/mgr/category/addCategory',
    editBefore: basePath+'category/editBefore',
    enabled: basePath+'category/enabled',
    disabled: basePath+'category/disabled',
    deleted: basePath+'category/deleted',
  }
},{});
$(function(){
  var colNames = ['分类ID', '分类图片', '分类名称', '创建日期', '操作'];
  var colModel = [
    {name: 'cateId', index: 'cate_id', width: 50, align: "center", formatter: function(cellvalue, options, rowObject){
      return cellvalue.toFixed(0);
    }}, 
    {name: 'catePic', width: 50, editable: false, sortable: false, align: "center",formatter: function(cellvalue, options, rowObject){
      if(! cellvalue) return "";
      var temp = '<img class="dataImg" src="'+cellvalue+'" height="60px">';
      return temp;
    }}, 
    {name: 'cateName', index: 'cate_name', width: 50, align: "center"}, 
    {name: 'createTime', index: 'create_time', width: 50, align: "center", formatter:'date', formatoptions: {srcformat: 'Y-m-d H:i:s', newformat:'Y-m-d H:i:s'}}, 
    {align: "center", editable: false, sortable: false, formatter: function(cellvalue, options, rowObject){
      var temp = '';
      if(hasAuthorize('category-editBefore')){
        temp += '<a class="linetaga" href="javascript: categoryHandle.edit(\'' + rowObject.cateId.toFixed(0) + '\');" >编辑</a>';
      }
      if(rowObject.enabled==1){
        if(hasAuthorize('category-disabled')){
          temp += '<a class="linetaga" href="javascript: categoryHandle.disabled(\'' + rowObject.cateId.toFixed(0) + '\');" >禁用</a>';
        }
      }else{
        if(hasAuthorize('category-enabled')){
          temp += '<a class="linetaga" href="javascript: categoryHandle.enabled(\'' + rowObject.cateId.toFixed(0) + '\');" >启用</a>';
        }
      }
      return temp;
    }}
  ];
  var rowList = [10, 20, 30, 50];
  var rownumbers = true;
  var multiselect = true;
  var config={caption: "商品分类列表", colNames: colNames, colModel: colModel, rowList: rowList, rownumbers: rownumbers, multiselect: multiselect, callback: function(){
    $('.dataImg').unbind().click(function(){
      var img = new Image();
      img.src = $(this).attr("src");
      img.onload = function(){
        var w=img.width;
        var h=img.height;
        artDialog.alert2('<div style="width:50%;height:50%;"><img src="'+img.src+'">') 
      }
    });  
  }};
  categoryHandle.init(config);
});