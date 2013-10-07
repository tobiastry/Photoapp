var factory = require('./Factory');

function getVersion(contentType){
	var version = contentType.substring(12, 14);
	return version;
}

var contentType = "";
function getDelay(req, res){
	contentType = req.get('Content-Type');
	var version = getVersion(contentType);
	var delay = factory.getDelay(version);
	res.send(delay); 
}

//exports
exports.DelayHandler = getDelay;
