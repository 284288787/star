var lastRowId;
var basePath = "/";
var uriHandle = new ListHandle({
	basePath: basePath,
	tableId: '#grid-table',
	pagerId: '#grid-pager',
	formId: '#queryForm',
	entityName: '资源信息',
	winWidth: '480px',
	winHeight: '330px',
	primaryKey: 'uri',
	urls:{
		list: basePath+'resource/uris',        //列表
	}
},{
});
var handles={};
$(function(){
	var constColors = ['#e59b1e', '#75b7db', '#a875db', '#ea86db', '#104968', '#3e38c5', '#684710', 'red', 'blue', 'green', 'gray'];
	var colorIdx = 0;
	var colors = {};
	var colNames = [ '', 'sourceId', 'uri', '资源', '简介' ];
	var colModel = [
        {width: '30px', fixed: true, align: "center", editable: false, sortable: false, formatter: function(cellvalue, options, rowObject){
        	var html='<input type="checkbox" uri="'+rowObject.uri+'" class="cbox star_jqgrid_checkbox">'
        	return html;
        }},
        {name: 'sourceId', hidden: true, formatter: function(cellvalue, options, rowObject){
          if(cellvalue) return cellvalue.toFixed(0);
          else return "";
        }},
        {name: 'uri', align: "left", editable: false, sortable: false},
        {name: 'sourceName', align: "left", editable: false, sortable: false, formatter: function(cellvalue, options, rowObject){
        	if(!cellvalue) return '';
        	var color = colors[rowObject.sourceId];
        	if(!color) {
        		color = constColors[colorIdx];
        		colorIdx ++;
        		colors[rowObject.sourceId] = color;
        	}
        	return "<span style='color:"+color+"'>"+cellvalue+"</span>";
        }},
		{name: 'intro', align: "left", editable: true, sortable: false}
	];
	var rownumbers = true;
	var multiselect = false;
	var config={dataType: 'json', caption: "uri列表", colNames: colNames, colModel: colModel, rowNum: 30, rownumbers: rownumbers, multiselect: multiselect,
		editurl :"/resource/editUri",
		serializeRowData: function(defaultParams){
			var rowData = jQuery(uriHandle.options.tableId).jqGrid("getRowData", defaultParams.id);
			return {intro: defaultParams.intro, uri: rowData.uri, sourceId: rowData.sourceId};
		},
		onCellSelect : function(obj, rowId, iCol, cellcontent, e) {
	      if (rowId && rowId !== lastRowId && iCol == colNames.length) {
	    	  var rowData = obj.jqGrid("getRowData", rowId);
	    	  obj.jqGrid('saveRow', lastRowId);
	    	  obj.jqGrid('editRow', rowId, true);
	    	  lastRowId = rowId;
	      }
	    },
		callback: function(table){
			var ids=table.jqGrid('getDataIDs');
			$.each(ids, function(i, rowIdx){
				var curChk = $("#"+rowIdx+"").find(":checkbox");
				var rowData = table.jqGrid("getRowData", rowIdx);
				if(rowData.sourceId == uriHandle.options.sourceId){
					curChk.attr("checked", true);
				}else if(rowData.sourceId){
					curChk.attr({"checked": true, "disabled": true});
				}
			});
			$(".star_jqgrid_checkbox").change(function(){
				var rowId = $(this).parents("tr").attr("id");
				var status = this.checked;
				var uri = $(this).attr("uri")
				var params={'uri': uri, 'sourceId': uriHandle.options.sourceId, 'status': status};
				uriHandle.ajax({
					async: false,
					url: basePath+'resource/saveResourceUri',
					data: params,
					success: function(res){
						if(res.code==0){
							var color = colors[uriHandle.options.sourceId];
				        	if(!color) {
				        		color = constColors[colorIdx];
				        		colorIdx ++;
				        		colors[uriHandle.options.sourceId] = color;
				        	}
							$(uriHandle.options.tableId).jqGrid('setCell',rowId,"sourceName", status ? "<span style='color:"+color+"'>"+uriHandle.options.sourceName+"</span>" : "", "", "", true);
						}else{
							artDialog.alert(res.msg);
						}
					}
				});
			})
		}
	};
	uriHandle.setConfig(config);
});