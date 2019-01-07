var basePath = "/";
var orderAfterSaleHandle = new ListHandle({
  basePath: basePath,
  tableId: '#grid-table',
  pagerId: '#grid-pager',
  formId: '#queryForm',
  entityName: '订单售后',
  winWidth: '500px',
  winHeight: '400px',
  primaryKey: 'id',
  urls:{
    list: basePath+'orderAfterSale/list',
    addBefore: basePath+'common/mgr/orderAfterSale/addOrderAfterSale',
    editBefore: basePath+'orderAfterSale/editBefore',
    enabled: basePath+'orderAfterSale/enabled',
    disabled: basePath+'orderAfterSale/disabled',
    deleted: basePath+'orderAfterSale/deleted',
  }
},{
  pass: function(id){
    artDialog.confirm("<div class='dialog'>\
    		审核通过，请填写收货地址：<br>\
        <input class='name' type='text' placeholder='收件人' value=''><br>\
        <input class='mobile' type='text' placeholder='收件人电话' value=''><br>\
        <input class='address' type='text' placeholder='收件地址' value=''>\
      </div>", function(win) {
        var name = $(".dialog .name", $(win.document)).val();
        var mobile = $(".dialog .mobile", $(win.document)).val();
        var address = $(".dialog .address", $(win.document)).val();
        if(!name || !mobile || ! address){
          artDialog.tips("收件人，收件人电话，收件地址必填")
          return false;
        }
        var params = {id: id, addressee: name, addresseeMobile: mobile, addresseeAddress: address};
      orderAfterSaleHandle.ajax({
        url : '/orderAfterSale/pass',
        data: params,
        success : function(res) {
          if (res.code == 0) {
            orderAfterSaleHandle.query();
          } else {
            artDialog.alert(res.msg)
          }
        }
      });
    });
  },
  nopass: function(id){
    artDialog.prompt("审核未通过原因：", function(reject) {
      if(! reject){
        artDialog.alert("原因必填");
        return false;
      }
      orderAfterSaleHandle.ajax({
        url : '/orderAfterSale/nopass/'+id,
        data: {reject: reject},
        success : function(res) {
          if (res.code == 0) {
            orderAfterSaleHandle.query();
          } else {
            artDialog.alert(res.msg)
          }
        }
      });
    });
  },
  doing: function(id){
    orderAfterSaleHandle.ajax({
      url : '/orderAfterSale/doing/'+id,
      success : function(res) {
        if (res.code == 0) {
          orderAfterSaleHandle.query();
        } else {
          artDialog.alert(res.msg)
        }
      }
    });
  },
  finish: function(id){
    artDialog.confirm("确认已经完成此次售后申请？", function() {
      orderAfterSaleHandle.ajax({
        url : '/orderAfterSale/finish/'+id,
        success : function(res) {
          if (res.code == 0) {
            orderAfterSaleHandle.query();
          } else {
            artDialog.alert(res.msg)
          }
        }
      });
    });
  }
});
$(function(){
  var colNames = ['操作', '申请日期', '售后状态', '主键', '售后单号', '售后类型', '商品标题', '购买单价', '购买数量', '申请数量', '数量金额', '申请备注', '购买时间', '订单ID', '订单编号', '订单详情ID', '购买会员', '会员电话', '收件人', '收件人电话', '收件地址', '寄件截止时间', '快递公司', '快递单号', '单号填写时间'];
  var colModel = [
    {align: "center", width:'180px', editable: false, sortable: false, formatter: function(cellvalue, options, rowObject){
      var temp = '';
      //1待处理 2通过 3不通过 4已寄件 5处理中 6完成 7已取消 8已删除
      if(rowObject.state <= 3){
        if(rowObject.state == 2 && ! rowObject.effectiveTime.before(new Date())){
          temp += '<span class="linetaga" >逾期未填写快递信息</span>';
          temp += '<a class="linetaga" href="javascript: orderAfterSaleHandle.finish(\'' + rowObject.id.toFixed(0) + '\');" >'+(rowObject.type==1? '退货' : '换货')+'完成</a>';
        }else{
          if(hasAuthorize('orderAfterSale-pass')){
            temp += '<a class="linetaga" href="javascript: orderAfterSaleHandle.pass(\'' + rowObject.id.toFixed(0) + '\');" >通过</a>';
          }
          if(hasAuthorize('orderAfterSale-nopass')){
            temp += '<a class="linetaga" href="javascript: orderAfterSaleHandle.nopass(\'' + rowObject.id.toFixed(0) + '\');" >不通过</a>';
          }
        }
      }else if (rowObject.state == 4){
        if(rowObject.type==1){
          temp += '<a class="linetaga" href="javascript: orderAfterSaleHandle.finish(\'' + rowObject.id.toFixed(0) + '\');" >退货完成</a>';
        }else{
          temp += '<a class="linetaga" href="javascript: orderAfterSaleHandle.doing(\'' + rowObject.id.toFixed(0) + '\');" >换货处理</a>';
        }
      }else if(rowObject.state == 5){
        temp += '<a class="linetaga" href="javascript: orderAfterSaleHandle.finish(\'' + rowObject.id.toFixed(0) + '\');" >换货完成</a>';
      }
      return temp;
    }},
    {name: 'createTime', index: 'create_time', width:'120px', align: "center", formatter:'date', formatoptions: {srcformat: 'Y-m-d H:i:s', newformat:'Y-m-d H:i:s'}}, 
    {name: 'state', index: 'state', width:'80px', align: "center", formatter: 'select', editoptions: {value:'1:待处理;2:通过;3:不通过;4:已寄件;5:处理中;6:完成;7:已取消;8:已删除'}},
    {name: 'id', index: 'id', width:'40px', align: "center", formatter: function(cellvalue, options, rowObject){
      return cellvalue.toFixed(0);
    }}, 
    {name: 'afterCode', index: 'after_code', width:'100px', align: "center"}, 
    {name: 'type', index: 'type', width:'80px', align: "center", formatter: 'select', editoptions: {value:'1:退货;2:换货'}}, 
    {name: 'title', index: 'title', width:'200px', align: "left", formatter: function(cellvalue, options, rowObject){
      return "<div style='word-wrap: break-word;word-break:break-all;white-space:normal'>" + cellvalue + "</div>";
    }}, 
    {name: 'price', index: 'price', width:'80px', align: "center", formatter: function(cellvalue, options, rowObject){
      return (cellvalue / 100.0).toFixed(2);
    }}, 
    {name: 'detailCount', index: 'detailCount', width:'80px', align: "center"}, 
    {name: 'count', index: 'count', width:'80px', align: "center"}, 
    {width:'80px', align: "center", formatter: function(cellvalue, options, rowObject){
      return (rowObject.count * rowObject.price / 100.0).toFixed(2);
    }}, 
    {name: 'remark', index: 'remark', width:'120px', align: "center"}, 
    {name: 'orderCreateTime', sortable: false, width:'120px', align: "center", formatter:'date', formatoptions: {srcformat: 'Y-m-d H:i:s', newformat:'Y-m-d H:i:s'}}, 
    {name: 'orderId', index: 'order_id', width:'80px', align: "center"}, 
    {name: 'orderCode', index: 'order_code', width:'80px', align: "center"}, 
    {name: 'detailId', index: 'detail_id', width:'80px', align: "center"}, 
    {name: 'memberName', sortable: false, width:'100px', align: "center", formatter: function(cellvalue, options, rowObject){
      return "<div style='word-wrap: break-word;word-break:break-all;white-space:normal'>" + cellvalue + "</div>";
    }}, 
    {name: 'memberMobile', sortable: false, width:'120px', align: "center"}, 
    {name: 'addressee', sortable: false, width:'100px', align: "center", formatter: function(cellvalue, options, rowObject){
      if(! cellvalue) return '';
      return "<div style='word-wrap: break-word;word-break:break-all;white-space:normal'>" + cellvalue + "</div>";
    }}, 
    {name: 'addresseeMobile', sortable: false, width:'120px', align: "center"}, 
    {name: 'addresseeAddress', sortable: false, width:'100px', align: "center", formatter: function(cellvalue, options, rowObject){
      if(! cellvalue) return '';
      return "<div style='word-wrap: break-word;word-break:break-all;white-space:normal'>" + cellvalue + "</div>";
    }}, 
    {name: 'effectiveTime', sortable: false, width:'120px', align: "center", formatter:'date', formatoptions: {srcformat: 'Y-m-d H:i:s', newformat:'Y-m-d H:i:s'}},
    {name: 'expressageCompany', index: 'title', width:'100px', align: "left", formatter: function(cellvalue, options, rowObject){
      if(! cellvalue) return '';
      return "<div style='word-wrap: break-word;word-break:break-all;white-space:normal'>" + cellvalue + "</div>";
    }}, 
    {name: 'expressageNumber', index: 'title', width:'200px', align: "left", formatter: function(cellvalue, options, rowObject){
      if(! cellvalue) return '';
      return "<div style='word-wrap: break-word;word-break:break-all;white-space:normal'>" + cellvalue + "</div>";
    }}, 
    {name: 'expressageTime', sortable: false, width:'120px', align: "center", formatter:'date', formatoptions: {srcformat: 'Y-m-d H:i:s', newformat:'Y-m-d H:i:s'}},
  ];
  var rowList = [10, 20, 30, 50];
  var rownumbers = true;
  var multiselect = true;
  var config={caption: "订单售后列表", autowidth: false, dataType:'local', colNames: colNames, colModel: colModel, rowList: rowList, rownumbers: rownumbers, multiselect: multiselect};
  orderAfterSaleHandle.init(config, {
    jsonReader: {
      repeatitems : false
    },
    shrinkToFit: false,
  });
  orderAfterSaleHandle.query();
});