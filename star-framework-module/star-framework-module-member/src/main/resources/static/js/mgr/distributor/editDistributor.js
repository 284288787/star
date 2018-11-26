var basePath="/";
var parentParams=artDialog.data('params');
var nodes=artDialog.data('nodes');
$(function(){
  new UtilsHandle({
    basePath: "/",
    uploadImages:{uploadFileId: 'uploadImage', multiple: true, items: [{
      uploadBtn: $('#uploadPicBtn'), 
      success: function (data, textStatus) {
        if(data.code==0){
          for(var o in data.data){
            var pic = data.data[o].original;
            $('#uploadPicBtn').attr({"src": pic, "data": pic}); 
          }
        }else{
          artDialog.alert(data.msg);
        }
      }
    }]},
    choose: [
      {
        object: $("input[name=regionName]"),
        service: "distributionRegionService",
        title: "选择分销地区",
        width: "800px",
        height: "500px",
        callback: function(rowObject){
          var r = "";
          if(rowObject.provinceName) r+=rowObject.provinceName;
          if(rowObject.cityName) r+=rowObject.cityName;
          if(rowObject.areaName) r+=rowObject.areaName;
          if(rowObject.townName) r+=rowObject.townName;
          r+=" -> "+rowObject.name;
          $("input[name=regionName]").val(r);
          $("input[name=regionId]").val(rowObject.regionId);
        }
      },
      {
        object: $("input[name=parentDistributor]"),
        service: "distributorService",
        title: "选择分销商",
        width: "800px",
        height: "550px",
        callback: function(rowObject){
          $("input[name=parentDistributor]").val(rowObject.name);
          $("input[name=parentDistributorId]").val(rowObject.distributorId);
        }
      }
    ]
  },{});
  $("#editDistributorForm").validate({
    rules: {
      name: {
        required: true,
        zhengze: ".{2,10}"
      },
      shopName: {
        required: true,
        zhengze: ".{2,40}"
      },
      mobile: {
        required: true,
        mobile: true
      },
      regionName: {
        required: true
      },
      address: {
        required: true,
        zhengze: ".{2,40}"
      }
    },
    messages: {
      name: {
        required: "必填",
        zhengze: "2至10个字"
      },
      shopName: {
        required: "必填",
        zhengze: "2至20个字"
      },
      mobile: {
        required: "必填",
      },
      regionName: {
        required: "必选"
      },
      address: {
        required: "必填",
        zhengze: "2至20个字"
      }
    }
  });
  
  $("#saveBtn").click(function(){
    var flag = $("#editDistributorForm").valid();
    if(! flag) return;
    var data=$("#editDistributorForm").serializeArray();
    var params = {};
    $.each(data, function(i, field){
      var name = field.name;
      params[name] = field.value;
      console.log(name + "  " + field.value)
    });
    params["head"] = $("#uploadPicBtn").attr("data");
    $("#saveBtn").attr("disabled", true);
    $.ajax({
      contentType: "application/json",
      url: basePath+"distributor/edit",
      data: JSON.stringify(params),
      type: 'post',
      dataType: 'json',
      success: function(res){
        if(res.code==0){
          if(parentParams && parentParams.query) parentParams.query();
          if(nodes && nodes.callback) nodes.callback(params.parentDistributorId);
          art.dialog.close();
        }else{
          artDialog.alert(res.msg)
        }
        $("#saveBtn").removeAttr("disabled");
      }
    });
  });
});