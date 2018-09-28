(function(factory) {
	if (typeof define === "function" && define.amd) {
		define([ "jquery", "./jquery.validate" ], factory);
	} else if (typeof module === "object" && module.exports) {
		module.exports = factory(require("jquery"));
	} else {
		factory(jQuery);
	}
}(function($) {
	(function() {

		function stripHtml(value) {

			// Remove html tags and space chars
			return value.replace(/<.[^<>]*?>/g, " ").replace(/&nbsp;|&#160;/gi,
					" ")

			// Remove punctuation
			.replace(/[.(),;:!?%#$'\"_+=\/\-“”’]*/g, "");
		}
	}());
	jQuery.validator.addMethod("account", function(value, element) {
		var accountReg = new RegExp("^[a-zA-Z0-9_]{3,11}$");
		return this.optional(element) || (accountReg.test(value));
	}, "帐号格式错误");

	jQuery.validator.addMethod("mobile", function(value, element) {
		var moblieReg = new RegExp("^(13|14|15|17|18|16)[0-9]{9}$");
		return this.optional(element) || (moblieReg.test(value));
	}, "请填写正确的手机号");

	jQuery.validator.addMethod("money", function(value, element) {
		var moblieReg = new RegExp("^[1-9]{0,1}\\d*(\\.\\d{1,2}){0,1}$");
		return this.optional(element) || (moblieReg.test(value));
	}, "请填写正确的金额");

	jQuery.validator.addMethod("discount", function(value, element) {
		var moblieReg = new RegExp("^[1-9]{1}(\\.\\d{1}){0,1}$");
		return this.optional(element) || (moblieReg.test(value));
	}, "请填写正确的折扣");

	jQuery.validator.addMethod("weight", function(value, element) {
		var moblieReg = new RegExp("^[1-9]{1}\\d*(\\.\\d{1}){0,1}$");
		return this.optional(element) || (moblieReg.test(value));
	}, "请填写正确的体重");

	jQuery.validator.addMethod("number", function(value, element) {
		var moblieReg = new RegExp("^[1-9]{1}\\d*$");
		return this.optional(element) || (moblieReg.test(value));
	}, "请输入正整数");
	
	jQuery.validator.addMethod("time", function(value, element) {
		var moblieReg = new RegExp(
		"^(([0-2][0-3])|([0-1][0-9])|([1-9])):[0-5][0-9]$");
		return this.optional(element) || (moblieReg.test(value));
	}, "请输入时间");

	jQuery.validator.addMethod("zhengze", function(value, element, param) {
		var reg = new RegExp("^"+param+"$");
		return this.optional(element) || (reg.test(value));
	}, "错误");
}));