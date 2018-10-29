function UploadHandle(options, funcs){
  var handle = {};
  
  handle["uploadImagesFunc"] = function(uploadFileId, data, beforeSend, success, complete){
    var filenames=new Array();
    var formData = new FormData();
    var files=$('#'+uploadFileId)[0].files;
    if(files.length <= 0) return;
    var totalSize = 0;
    for(var i=0;i<files.length;i++){
      if(files[i].size > 1024*1024*1){
        artDialog.alert("图片文件["+files[i].name+"]不得大于1MB");
        return;
      }
      totalSize+=files[i].size;
      formData.append(files[i].name, files[i]);
      filenames.push(files[i].name);
    }
    if(totalSize > 1024*1024*10){
      artDialog.alert("选择的图片文件总共不得大于10MB");
      return;
    }
    if(data){
      for(var j in data){
        formData.append(j, data[j]);
      }
    }
    $.ajax({
      url: options.basePath+'/upload/images',
      type: 'POST',
      cache: false,
      data: formData,
      dataType: 'json',
      processData: false,
      contentType: false,
      beforeSend: function(xhr){
        if(beforeSend) beforeSend(xhr);
      }
    }).done(function(res) {
      if(success) success(res, filenames);
      if(complete) complete(res, filenames);
    }).fail(function(a,b,c) {
      var msg = "不得上传2MB以上的图片";
      artDialog.alert(msg)
    }).always(function(a,b,c,d){
      buildUploadImagesFile(options.uploadImages, fileOnChangeFunc);
    });
  };
  
  if(options.uploadImages){
    buildUploadImagesFile(options.uploadImages, fileOnChangeFunc);
    var fileOnChangeFunc = function(){
      var idx = $(this).attr("data-tag")
      var uf2 = options.uploadImages.items[idx];
      handle.uploadImagesFunc(options.uploadImages.uploadFileId, uf2.data, uf2.beforeSend, uf2.success, uf2.complete);
    };
    $("#"+options.uploadImages.uploadFileId).on("change", fileOnChangeFunc);
    for(var o in options.uploadImages.items){
      var uf = options.uploadImages.items[o];
      uf.uploadBtn.attr("data-tag", o);
      uf.uploadBtn.click(function(){
        $("#"+options.uploadImages.uploadFileId).attr("data-tag", $(this).attr("data-tag"));
        $("#"+options.uploadImages.uploadFileId).click();
      });
    }
    
    function buildUploadImagesFile(uploadImages, fileOnChangeFunc){
      if($("#uploadImageDiv").length > 0){
        $("#uploadImageDiv").remove();
      }
      $("body").append('<div id="uploadImageDiv" style="display:none"></div>');
      $("#uploadImageDiv").html('<input type="file"'+(uploadImages.multiple?' multiple="multiple" ':' ')+'name="'+uploadImages.uploadFileId+'" id="'+uploadImages.uploadFileId+'" accept="image/*">');
      $("#uploadImageDiv #"+options.uploadImages.uploadFileId).on("change", fileOnChangeFunc);
    }
  }
  return handle;
}
