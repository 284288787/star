function ListHandle(options, funcs) {
	var handle = {};
	var selectedRows = {};
	var historyIds = ",";
	var screenHeight = document.documentElement.clientHeight || document.body.clientHeight;
	var inited = false;

	handle.options = options;
	handle.config = {};
	handle.authorize = {};

	handle.addAuthorize = function(key, value) {
		handle.authorize[key] = value;
	}

	handle.hasAuthorize = function(key) {
		var v = handle.authorize[key];
		if (v)
			return v;
		return false;
	}

	handle.getScreenHeight = function() {
		return screenHeight;
	}
	handle.setConfig = function(config) {
		handle.config = config;
	}
	handle.initLazy = function() {
		if (!inited) {
			inited = true;
			handle.init(handle.config);
		}
	}

	handle.init = function(config, cfg2) {
	  var cfg = {
      url : options.urls.list + (config.params ? '?' + config.params : ''),
      datatype : config.dataType || 'json',
      colNames : config.colNames,
      colModel : config.colModel,
      rowNum : config.rowNum || 10,
      rowList : config.rowList,
      pager : config.pagerId || options.pagerId,
      mtype : "post",
      rownumbers : config.rownumbers || false,
      multiselect : config.multiselect || false,
      viewrecords : false,
      autowidth : null != config.autowidth ? config.autowidth : true,
      hidegrid : false,
      loadtext : '加载中...',
      height : config.height || '300px',
      caption : config.caption,
      editurl: config.editurl || '',
      width: config.width || '',
      serializeRowData: config.serializeRowData,
      gridComplete : function() {
        // var
        // ids=jQuery(options.tableId).jqGrid('getDataIDs');
        // $.each(ids, function(i, rowIdx){
        // var curChk =
        // $("#"+rowIdx+"").find(":checkbox");
        // var rowData =
        // $(options.tableId).jqGrid("getRowData",
        // rowIdx);
        // if(rowData.enabled==0){
        // curChk.attr("disabled", true);
        // }
        // });
        $("#cb_grid-table").on("click", function() {
          var checked = this.checked;
          var ids = jQuery(options.tableId).jqGrid('getDataIDs');
          $.each(ids,function(i, rowIdx) {
            selectedRows[rowIdx] = checked;
            if (!checked) {
              var rowData = $(options.tableId).jqGrid("getRowData", rowIdx);
              var temp = "," + rowData[options.primaryKey] + ",";
              historyIds = historyIds.replace(temp, ",");
            }
          });
        });
        if (config.callback) {
          config.callback(obj);
        }
      },
      onSelectRow : function(rowId, status, obje) {
        selectedRows[rowId] = status;
        var rowData = $(options.tableId).jqGrid("getRowData", rowId);
        if (!status) {
          var temp = "," + rowData[options.primaryKey] + ",";
          historyIds = historyIds.replace(temp, ",");
        }
        if (config.onSelectRow) {
          config.onSelectRow(rowId, status, obj, rowData);
        }
      },
      onPaging : function(pgButton) {
        var history = handle.getIds2();
        if (history) {
          var tempIds = history.split(",");
          for ( var i in tempIds) {
            var temp = "," + tempIds[i] + ",";
            if (historyIds.indexOf(temp) == -1) {
              historyIds = "," + tempIds[i] + historyIds;
            }
          }
        }
        selectedRows = {};
      },
      loadComplete : function(xhr) {
        var ids = jQuery(options.tableId).jqGrid('getDataIDs');
        $.each(ids, function(i, rowIdx) {
          var rowData = $(options.tableId).jqGrid("getRowData", rowIdx);
          var keyValue = rowData[options.primaryKey];
          if (historyIds.indexOf("," + keyValue + ",") != -1) {
            selectedRows[rowIdx] = true;
            var curChk = $("#" + rowIdx).find(":checkbox");
            curChk.attr("checked",true);
          }
        });
        if (config.callback) {
          config.callback(obj);
        }
      },
      ondblClickRow : function(rowId, iRow, iCol, e){
        if (config.ondblClickRow) {
          config.ondblClickRow(obj, rowId, iRow, iCol, e);
        }
      },
      onCellSelect : function(rowId, iCol, cellcontent, e){
        if (config.onCellSelect) {
          config.onCellSelect(obj, rowId, iCol, cellcontent, e);
        }
      },
      subGrid : config.subGrid || false,
      subGridRowExpanded : config.subGridRowExpanded,
      subGridRowColapsed : config.subGridRowColapsed,
      sortname : config.sortname,
      grouping : config.grouping || false,
      groupingView : {
        groupField : [ 'parentId' ],
        groupColumnShow : [ true ],
        groupText : [ '<b>{0} - {1} Item(s)</b>' ],
        groupCollapse : false,
      }
    };
	  if(cfg2) $.extend(cfg, cfg2);
	  handle.config = cfg;
		var obj = jQuery(config.tableId || options.tableId).jqGrid(cfg);
		if(cfg2 && cfg2.jsonReader){
		  jQuery(options.tableId).jqGrid('setFrozenColumns');
		}
		if (!options.subGrid) {
			jQuery(options.tableId).jqGrid('navGrid', options.pagerId, {edit: false, add: false, del: false, search: false});
			var h = handle.getScreenHeight() - 160;
			if($(".search-container").length>0) h -= $(".search-container").height();
			if($(".btnGroup").length>0) h -= $(".btnGroup").height();
			jQuery(options.tableId).setGridHeight(config.height || h);
		}
	}

	handle.setCaption = function(newcap) {
		jQuery(options.tableId).jqGrid('setCaption', newcap);
	}

	handle.ajax = function(opts) {
		var o = {
			async : null == opts.async ? true : opts.async,
			url : opts.url,
			data : opts.data,
			type : opts.type || 'post',
			dataType : 'json',
			success : opts.success,
			error : function(a, b, c) {
				artDialog.alert(b)
			}
		};
		$.ajax(o);
	}
	handle.resetSelectedIds = function() {
	  selectedRows = {};
    historyIds = ",";
  }
	handle.setId = function(id) {
		selectedRows[id] = true;
	}
	handle.getIds2 = function() {
		var ids = "";
		$.each(selectedRows, function(i, obj) {
			if (obj) {
				var rowData = $(options.tableId).jqGrid("getRowData", i);
				ids += "," + rowData[options.primaryKey];
			}
		});
		if (ids.length > 0) {
			return ids.substring(1);
		} else {
			return null;
		}
	}
	handle.getIds = function() {
		var history = handle.getIds2();
		if (history) {
			var tempIds = history.split(",");
			for ( var i in tempIds) {
				var temp = "," + tempIds[i] + ",";
				if (historyIds.indexOf(temp) == -1) {
					historyIds = "," + tempIds[i] + historyIds;
				}
			}
		}
		if (historyIds.length == 1) {
			return null;
		}
		var temp = historyIds.substring(1, historyIds.length - 1);
		return temp;
	}
	handle.getSelectedRows = function() {
    return selectedRows;
  }
	handle.getQueryParams = function(){
	  var data = $(options.formId).serializeArray();
    var params = {};
    $.each(data, function(i, field) {
      if (field.value) {
        var name = field.name;
        if (params[name]) {
          params[name] += "," + field.value;
        } else {
          params[name] = field.value;
        }
        console.log(name + "  " + field.value)
      }
    });
    return params;
	}
	handle.query = function() {
		var params = this.getQueryParams();
		$(options.tableId).jqGrid('setGridParam', {
			datatype : 'json',
			postData : params,
			page : 1
		}, true).trigger("reloadGrid");
	}
	handle.queryByParams = function(params) {
		$(options.tableId).jqGrid('setGridParam', {
			datatype : 'json',
			postData : params,
			page : 1
		}, true).trigger("reloadGrid");
	}
	handle.reset = function() {
		$(options.formId + " input[type='text']").each(function(i) {
			$(this).val("");
		});
		$(options.formId + " input[type='checkbox']").each(function(i) {
			this.checked=true;
		});
		$(options.formId + " select").each(function(i) {
			// var id = $($(this).find("option:first")).val();
			$(this).val("");
		});
		$(options.formId + " input[type='hidden']").each(function(i) {
			$(this).val("");
		});
		handle.query();
	}
	handle.addNew = function(params) {
		if (params) {
			$.extend(handle, params);
		}
		artDialog.data("params", handle);
		artDialog.open(options.urls.addBefore, {
			title : "新增 " + options.entityName,
			width : options.winWidth,
			height : options.winHeight,
			drag : true,
			resize : true,
			lock : true
		/*
		 * , close:function(){ document.location.reload(); }
		 */
		});
	}
	handle.edit = function(recId) {
		artDialog.data("params", handle);
		artDialog.open(options.urls.editBefore + "/" + recId, {
			title : "编辑 " + options.entityName,
			width : options.winWidth,
			height : options.winHeight,
			drag : true,
			resize : true,
			lock : true
		/*
		 * , close:function(){ document.location.reload(); }
		 */
		});
	}
	handle.view = function(recId) {
		artDialog.data("params", handle);
		artDialog.open(options.urls.viewBefore + "/" + recId, {
			title : "查看 " + options.entityName,
			width : options.winWidth,
			height : options.winHeight,
			drag : true,
			resize : true,
			lock : true
		/*
		 * , close:function(){ document.location.reload(); }
		 */
		});
	}
	handle.enabled = function(recId) {
		var ids = handle.getIds();
		if (recId) {
			ids = recId;
		}
		if (ids) {
			handle.ajax({
				url : options.urls.enabled,
				data : {
					'ids' : ids
				},
				success : function(res) {
					if (res.code == 0) {
						artDialog.alert("启用成功")
						selectedRows = {};
						historyIds = ",";
						handle.query();
					} else {
						artDialog.alert(res.msg)
					}
				}
			});
		} else {
			artDialog.alert("请选择要启用的记录");
		}
	}
	handle.disabled = function(recId) {
		var ids = handle.getIds();
		if (recId) {
			ids = recId;
		}
		if (ids) {
			artDialog.confirm("确认禁用？", function() {
				handle.ajax({
					url : options.urls.disabled,
					data : {
						'ids' : ids
					},
					success : function(res) {
						if (res.code == 0) {
							artDialog.alert("禁用成功")
							selectedRows = {};
							historyIds = ",";
							handle.query();
						} else {
							artDialog.alert(res.msg)
						}
					}
				});
			})
		} else {
			artDialog.alert("请选择要禁用的记录");
		}
	}
	handle.remove = function() {
		var ids = handle.getIds();
		if (ids) {
			artDialog.confirm("确认删除？", function() {
				handle.ajax({
					url : options.urls.deleted,
					data : {
						'ids' : ids
					},
					success : function(res) {
						if (res.code == 0) {
							artDialog.alert("删除成功")
							selectedRows = {};
							historyIds = ",";
							handle.query();
						} else {
							artDialog.alert(res.msg)
						}
					}
				});
			})
		} else {
			artDialog.alert("请选择要删除的记录");
		}
	}
	$.extend(handle, funcs);
	return handle;
}