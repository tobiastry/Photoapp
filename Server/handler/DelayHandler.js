var factory = require('./Factory');
var util = require('./utils');

exports.getDelay = function (req, res){
	var version = util.getVersion(req);
	factory.getDelay(res, version, function(err){
		if (err){
			if (err.message === "400") {
				res.send(404);
			}else{
				res.send(500);
			}

		} 
	});
}

exports.setDelay = function (req, res){
	var version = util.getVersion(req);
	factory.setDelay(res, version, req.params.value, function(err){
		if (err) res.send(500, err.message);
	});
}
