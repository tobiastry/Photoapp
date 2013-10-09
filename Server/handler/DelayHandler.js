var factory = require('./Factory');
var util = require('./utils');

exports.getDelay = function (req, res){
	try{
		console.log(req.get('Content-Type'));
		var version = util.getVersion(req);
		factory.getDelay(res, version);
	}catch(e){
		res.send(500, e.message);	
	}
}

exports.setDelay = function (req, res){
	try{
		console.log(req.get('Content-Type'));
		var version = util.getVersion(req);
		factory.setDelay(res, version, req.params.value);
	}catch(e){
		res.send(500, e.message);
	}
}
