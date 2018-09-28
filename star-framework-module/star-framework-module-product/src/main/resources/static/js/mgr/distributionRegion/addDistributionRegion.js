var basePath="/";
var parentParams=artDialog.data('params');
$(function(){
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
  $("#editDistributionRegionForm").validate({
    rules: {
      name: {
        required: true,
        zhengze: ".{2,10}"
      },
      py: {
        required: true,
        zhengze: ".{2,10}"
      },
      areaName: {
        required: true
      }
    },
    messages: {
      name: {
        required: "必填",
        zhengze: "长度在2至10位"
      },
      py: {
        required: "必填",
        zhengze: "长度在2至10位"
      },
      areaName: {
        required: "必选"
      }
    }
  });
  
  $("#saveBtn").click(function(){
    var flag = $("#editDistributionRegionForm").valid();
    if(! flag) return;
    var data=$("#editDistributionRegionForm").serializeArray();
    var params = {};
    $.each(data, function(i, field){
      var name = field.name;
      params[name] = field.value;
      console.log(name + "  " + field.value)
    });
    if(!params.areaId){
      artDialog.alert("地区必须选择到区县");
      return false;
    }
    $("#saveBtn").attr("disabled", true);
    $.ajax({
      url: basePath+"distributionRegion/add",
      data: params,
      type: 'post',
      dataType: 'json',
      success: function(res){
        if(res.code==0){
          parentParams.query();
          art.dialog.close();
        }else{
          artDialog.alert(res.msg)
        }
        $("#saveBtn").removeAttr("disabled");
      }
    });
  });
});