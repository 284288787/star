function LoginInfo(options){
	var M = {};
	
	M.getLoginInfo = function(callback){
		$.ajax({
			url: options.basePath+'getLoginInfo',
			type: 'post',
			dataType: 'json',
			success: function(res){
				callback(res);
			}
		});
	}
	return M;
}