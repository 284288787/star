var starAuthorize = {};

addAuthorize = function(key, value){
	starAuthorize[key] = value;
}

hasAuthorize = function(key){
	var v = starAuthorize[key];
	if(v) return v;
	return false;
}