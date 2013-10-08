var factory = require('./Factory');


var contentType = "";
exports.getDelay = function (req, res){
	var getVersion = function getVersion(contentType){
	var version = contentType.substring(12, 14);
	return version;
	};
	contentType = req.get('Content-Type');
	var version = contentType.substring(12, 14);
	var delay = factory.getDelay(version);
	res.send(delay); 
}
