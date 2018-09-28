String.prototype.replaceAll = function(s1,s2){ 
	return this.replace(new RegExp(s1,"gm"),s2); 
}
$(function(){
	var html = 
		 '<tr class="businessTr">                                                                                                                                                                                                                                        '
		+'  <td colspan="2">                                                                                                                                                                                                                          '
		+'    <table class="gy_table2">                                                                                                                                                                                                               '
		+'      <tr>                                                                                                                                                                                                                                  '
		+'        <td style="text-align: left; padding-left: 20px; font-weight: bold; background-color: #9ec7ef; color: #FFF" class="borderbottom title">业务一</td>                                                                                        '
		+'        <td style="text-align: right; padding-right: 20px; background-color: #9ec7ef; color: #FFF" class="borderbottom"></td>                                                                                                               '
		+'      </tr>                                                                                                                                                                                                                                 '
		+'      <tr>                                                                                                                                                                                                                                  '
		+'        <td class="borderbottom" width="30px" style="white-space: nowrap">业务名称：</td>                                                                                                                                                   '
		+'        <td class="borderbottom"><input type="text" name="entityCaption" placeholder="业务名称，例如：用户"></td>                                                                                                                           '
		+'      </tr>                                                                                                                                                                                                                                 '
		+'      <tr>                                                                                                                                                                                                                                  '
		+'        <td style="white-space: nowrap">业务实体名称：</td>                                                                                                                                                                                 '
		+'        <td><input type="text" name="entityName" style="width: 400px" placeholder="业务实体名称"></td>                                                                                                                                      '
		+'      </tr>                                                                                                                                                                                                                                 '
		+'      <tr>                                                                                                                                                                                                                                  '
		+'        <td class="borderbottom"></td>                                                                                                                                                                                                      '
		+'        <td class="borderbottom"><span class="rule">由字母组成，以字母开头，首字母必须大写，长度不得小于2位，不得大于30位</span></td>                                                                                                       '
		+'      </tr>                                                                                                                                                                                                                                 '
		+'      <tr>                                                                                                                                                                                                                                  '
		+'        <td style="white-space: nowrap">业务表名称：</td>                                                                                                                                                                                   '
		+'        <td><input type="text" name="tableName" style="width: 400px" placeholder="业务表名称，可不填"></td>                                                                                                                                 '
		+'      </tr>                                                                                                                                                                                                                                 '
		+'      <tr>                                                                                                                                                                                                                                  '
		+'        <td class="borderbottom"></td>                                                                                                                                                                                                      '
		+'        <td class="borderbottom"><span class="rule">由字母、下划线组成，以字母开头，下划线只能出现一次，且不能以下划线结尾，长度不得小于2位，不得大于30位；<br>若业务表名称为空，则取业务实体名称的小写形式作为业务表的名称                 '
		+'        </span></td>                                                                                                                                                                                                                        '
		+'      </tr>                                                                                                                                                                                                                                 '
		+'      <tr>                                                                                                                                                                                                                                  '
		+'        <td style="white-space: nowrap"></td>                                                                                                                                                                                               '
		+'        <td><input type="checkbox" name="createTable" id="createTable_{businessCount}" checked><label for="createTable_{businessCount}">需要建表</label> <input type="checkbox" name="existsDrop" id="existsDrop_{businessCount}"><label for="existsDrop_{businessCount}">表若存在，则先删表，再建表</label>'
		+'        </td>                                                                                                                                                                                                                               '
		+'      </tr>                                                                                                                                                                                                                                 '
		+'      <tr>                                                                                                                                                                                                                                  '
		+'        <td style="white-space: nowrap">主键生成IdWorker：</td>                                                                                                                                                                             '
		+'        <td><input type="text" name="workerId" placeholder="workerId，数字"> <input type="text" name="dataCenterId" placeholder="dataCenterId，数字"></td>                                                                                  '
		+'      </tr>                                                                                                                                                                                                                                 '
		+'      <tr>                                                                                                                                                                                                                                  '
		+'        <td class="borderbottom"></td>                                                                                                                                                                                                      '
		+'        <td class="borderbottom"><span class="rule">填写时应该注意不要重复</span></td>                                                                                                                                                      '
		+'      </tr>                                                                                                                                                                                                                                 '
		+'      <tr>                                                                                                                                                                                                                                  '
		+'        <td style="white-space: nowrap">html：</td>                                                                                                                                                                                         '
		+'        <td><input type="checkbox" checked name="createListHtml" id="createListHtml_{businessCount}"><label for="createListHtml_{businessCount}">生成列表页面</label> <input type="checkbox" checked name="createAddHtml" id="createAddHtml_{businessCount}"><label                         '
		+'          for="createAddHtml_{businessCount}">生成新增页面</label> <input type="checkbox" checked name="createEditHtml" id="createEditHtml_{businessCount}"><label for="createEditHtml_{businessCount}">生成编辑页面</label></td>                                                           '
		+'      </tr>                                                                                                                                                                                                                                 '
		+'      <tr>                                                                                                                                                                                                                                  '
		+'        <td class="borderbottom"></td>                                                                                                                                                                                                      '
		+'        <td class="borderbottom"><span class="rule">若只需要后端代码，则请不要选中上面的3个复选框，反之请选中对应的复选框</span></td>                                                                                                       '
		+'      </tr>                                                                                                                                                                                                                                 '
		+'      <tr>                                                                                                                                                                                                                                  '
		+'        <td style="white-space: nowrap">业务字段：</td>                                                                                                                                                                                     '
		+'        <td><input type="button" class="button blue addField" value="添加字段">&nbsp;&nbsp;&nbsp;点击添加字段按钮之前，请选择好需要生成的html页面，会根据选择出现不同界面</td>                                                              '
		+'      </tr>                                                                                                                                                                                                                                 '
		+'    </table>                                                                                                                                                                                                                                '
		+'  </td>                                                                                                                                                                                                                                     '
		+'</tr>                                                                                                                                                                                                                                       ';
	var businessCount = 0;
	var businessText = {1:'一', 2:'二', 3:'三', 4:'四', 5:'五', 6:'六', 7:'七', 8:'八', 9:'久', 10:'十'}
	$("#addBusiness").click(function(){
		businessCount ++;
		var $tr = $(html.replaceAll("{businessCount}", businessCount));
		$tr.attr("data-index", businessCount);
		$(".title", $tr).text("业务" + businessText[businessCount]);
		$(".gy_table").append($tr);
		$(".addField", $tr).click(function(){
			var params={'createListHtml': $(":checkbox[name=createListHtml]", $tr)[0].checked, 'createAddHtml': $(":checkbox[name=createAddHtml]", $tr)[0].checked, 'createEditHtml': $(":checkbox[name=createEditHtml]", $tr)[0].checked};
			artDialog.data("params", params);
			artDialog.open('/project/buildField',{
				title: "新增业务字段",
				width : '80%',
				height: '90%',
				drag:true,
				resize:true,
				lock:true/* ,
				close:function(){
					document.location.reload();
				} */
			});
		});
	});
});