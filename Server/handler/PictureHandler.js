var factory = require('./Factory');
var util = require('./utils');

exports.insertPictures = function (req, res){
	try{
		var version = util.getVersion(req);
		factory.insertPictures(res, version, req.body);
	}catch(e){
		res.send(500, e.message);
	}
}

exports.getPictures = function (req, res){
	try{
		var version = util.getVersion(req);
		factory.getPictures(res, version);
	}catch(e){
		res.send(500, e.message);
	}
}
