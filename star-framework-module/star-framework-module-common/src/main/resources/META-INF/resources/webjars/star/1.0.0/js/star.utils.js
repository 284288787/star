function UtilsHandle(options, funcs){
	var handle = {};
	
	/**
	 * opts.urlParams 是数组，表示可以有多个参数，每个元素包含的属性介绍：
	 * 	   type: value的值的类型 input表示文本框，需要在请求前获取input框的值
	 *                         text 表示文本，可以直接使用，基本数据类型都是text 例如 数字
	 *     key: 参数键；
	 *     value: 参数值，根据type确定值的内容
	 * **/
	handle.choose = function(opts){
		artDialog.data("opts", opts);
		var condition = "";
		if(opts.valid){
		  var res = opts.valid();
		  if(! res.valid){
		    artDialog.alert(res.msg);
		    return false;
		  }
		}
		if(opts.condition){
		  condition = encodeURI(opts.condition());
		}
		var params = encodeURI("?service=" + opts.service + "&condition="+condition);
		console.log(params);
		artDialog.open("/choose/dialog" + params,{
			title: opts.title,
			width : opts.width,
			height: opts.height,
			drag:true,
			resize:false,
			lock:true/* ,
			close:function(){
				document.location.reload();
			} */
		});
	}
	
	if(options.choose){
    var optsss={};
    for(var temp in options.choose){
      var o = options.choose[temp];
      optsss[o.object.attr("name")] = o;
      o.object.click(function(){
        var oo = $(this).attr("name");
        handle.choose(optsss[oo]);
      });
    }
  }
	
	handle.addUploadImages = function(uf){
		if(options.uploadImages){
			var l = options.uploadImages.items.length;
			options.uploadImages.items.push(uf);
			uf.uploadBtn.attr("data-tag", l);
			uf.uploadBtn.unbind("click").click(function(){
				$("#"+options.uploadImages.uploadFileId).attr("data-tag", $(this).attr("data-tag"));
				$("#"+options.uploadImages.uploadFileId).click();
			});
		}
	}
	
	$.extend(handle, funcs);
	if(options.chooseCity){
	  var key = options.chooseCity.key;
	  var items = options.chooseCity.items;
	  for (var o in items){
	    var temp = items[o];
	    var idName = temp.areaIdObj.attr("name");
	    temp.areaNameObj.attr("data-idName", idName);
	    temp.areaNameObj.click(function(){
	      var areaNameObj = $(this);
	      var areaIdObj = $("input[name="+$(this).attr("data-idName")+"]");
	      var params = {};
	      params[key] = areaIdObj.val();
	      params['callback'] = function(areaId, areaName, provinceId, provinceName){
	        areaNameObj.val(provinceName + " - " + areaName);
	        areaIdObj.val(areaId);
        };
	      artDialog.data("opts", params);
        artDialog.open('/area/choose',{
          title: "选择城市",
          width : '900px',
          height: '610px',
          drag:true,
          resize:true,
          lock:true
        });
	    });
	  }
	}
	if(options.chooseArea){
    var items = options.chooseArea.items;
    var areaCallbacks = {};
    var areaOnclick = {};
    for (var o in items){
      var temp = items[o];
      var name = temp.areaNameObj.attr("name");
      areaCallbacks[name] = temp.callback;
      areaOnclick[name] = temp.onClick;
      temp.areaNameObj.click(function(){
        var areaNameObj = $(this);
        var attrName = areaNameObj.attr("name");
        if(areaOnclick[attrName]) areaOnclick[attrName]();
        var params = {};
        params['callback'] = function(areas){
          var name = "";
          if(areas.provinceName) name += areas.provinceName;
          if(areas.cityName) name += (name.length>0 ? " - " : "") + areas.cityName;
          if(areas.areaName) name += (name.length>0 ? " - " : "") + areas.areaName;
          if(areas.townName) name += (name.length>0 ? " - " : "") + areas.townName;
          areaNameObj.val(name);
          if(areaCallbacks[attrName])areaCallbacks[attrName](areas);
        };
        artDialog.data("opts", params);
        artDialog.open('/common/mgr/area/chooseArea',{
          title: "选择地区",
          width : '350px',
          height: '410px',
          drag:true,
          resize:true,
          lock:true
        });
      });
    }
  }
	if(options.kindEditor){
		for(var o in options.kindEditor){
			var temp=options.kindEditor[o];
			handle["kindEditorOptions"]={
				cssPath : '/webjars/kindeditor/plugins/code/prettify.css',
				uploadJson : '/kindeditor/upload',
				fileManagerJson : '/kindeditor/manager',
				allowFileManager : true,
				width: temp.width,
				height: temp.height,
				resizeType:1,
				autoHeightMode : false,
				items : [
				  'source', '|', 'undo', 'redo', '|', 'preview', 'template', 'cut', 'copy', 'paste',
			    'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
			    'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
			    'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
			    'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
			    'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image', 'multiimage',
			    'flash', 'media', 'insertfile', 'table', 'hr', 'emoticons', 'baidumap', 'pagebreak',
			    'anchor', 'link', 'unlink',
				],
				afterCreate : function() {
				},
				afterChange: function(){
				}
			};
			var kk=KindEditor.create(temp.object, handle.kindEditorOptions);
			temp.afterCreate(kk);
		}
	}
	if(options.uploadExcels){
	  buildUploadExcelsFile(options.uploadExcels, fileOnChangeFunc);
		handle["uploadExcelFunc"] = function(uploadFileId, data, beforeSend, success, complete){
		  var filenames=new Array();
			var formData = new FormData();
			var files=$('#'+uploadFileId)[0].files;
			if(files.length <= 0) return;
			var totalSize = 0;
			for(var i=0;i<files.length;i++){
			  if(files[i].size > 1024*1024*10){
          artDialog.alert("Excel文件["+files[i].name+"]不得大于10MB");
          return;
        }
			  totalSize+=files[i].size;
				formData.append(files[i].name, files[i]);
				filenames.push(files[i].name);
			}
			if(totalSize > 1024*1024*100){
			  artDialog.alert("选择的Excel文件总共不得大于100MB");
			  return;
			}
			if(data){
				for(var j in data){
					formData.append(j, data[j]);
				}
			}
			$.ajax({
			    url: options.basePath+'upload/excels',
			    type: 'POST',
			    cache: false,
			    data: formData,
			    processData: false,
			    contentType: false,
			    beforeSend: function(xhr){
			    	if(beforeSend) beforeSend(xhr);
			    }
			}).done(function(res) {
				if(success) success(res, filenames);
				if(complete) complete(res, filenames);
				buildUploadExcelsFile(options.uploadExcels, fileOnChangeFunc);
			}).fail(function(res,a,c) {
				var msg = "服务器出错，错误内容：" + XMLHttpRequest.responseText;
	     	artDialog.alert(msg)
			});
		};
		var fileOnChangeFunc = function(){
			var idx = $(this).attr("data-tag")
			var uf2 = options.uploadExcels.items[idx];
			handle.uploadExcelFunc(options.uploadExcels.uploadFileId, uf2.data, uf2.beforeSend, uf2.success, uf2.complete);
		};
		$("#"+options.uploadExcels.uploadFileId).on("change", fileOnChangeFunc);
		for(var o in options.uploadExcels.items){
			var uf = options.uploadExcels.items[o];
			uf.uploadBtn.attr("data-tag", o);
			uf.uploadBtn.click(function(){
				$("#"+options.uploadExcels.uploadFileId).attr("data-tag", $(this).attr("data-tag"));
				$("#"+options.uploadExcels.uploadFileId).click();
			});
		}
	}
	if(options.uploadImages){
	  buildUploadImagesFile(options.uploadImages, fileOnChangeFunc);
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
          url: options.basePath+'upload/images',
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
  }
	
	function buildUploadExcelsFile(uploadExcels, fileOnChangeFunc){
	  if($("#uploadExcelDiv").length > 0){
	    $("#uploadExcelDiv").remove();
	  }
	  $("body").append('<div id="uploadExcelDiv" style="display:none"></div>');
	  $("#uploadExcelDiv").html('<input type="file"'+(uploadExcels.multiple?' multiple="multiple" ':' ')+'name="'+uploadExcels.uploadFileId+'" id="'+uploadExcels.uploadFileId+'" accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel">');
	  $("#uploadExcelDiv #"+options.uploadExcels.uploadFileId).on("change", fileOnChangeFunc);
	}
	
	function buildUploadImagesFile(uploadImages, fileOnChangeFunc){
	  if($("#uploadImageDiv").length > 0){
	    $("#uploadImageDiv").remove();
	  }
	  $("body").append('<div id="uploadImageDiv" style="display:none"></div>');
	  $("#uploadImageDiv").html('<input type="file"'+(uploadImages.multiple?' multiple="multiple" ':' ')+'name="'+uploadImages.uploadFileId+'" id="'+uploadImages.uploadFileId+'" accept="image/*">');
    $("#uploadImageDiv #"+options.uploadImages.uploadFileId).on("change", fileOnChangeFunc);
	}
	return handle;
}
//对Date的扩展，将 Date 转化为指定格式的String
//月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
//年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
//例子： 
//(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
//(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
Date.prototype.format = function (fmt) {
 var o = {
     "M+": this.getMonth() + 1, //月份 
     "d+": this.getDate(), //日 
     "h+": this.getHours(), //小时 
     "m+": this.getMinutes(), //分 
     "s+": this.getSeconds(), //秒 
     "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
     "S": this.getMilliseconds() //毫秒
 };
 if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
 for (var k in o)
 if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
 return fmt;
}