
function ParseException(message){
	this.message = message;
	this.name = "ParseException";
}

//Returns the version number from the Content-Type header
exports.getVersion = function(req){
	try{
		return req.get('Content-Type').substring(12, 14);
	}catch(e){
		throw new ParseException("error trying to get the version! caused  by: " + e);
	}
}
