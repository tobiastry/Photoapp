var factory = require('./Factory');
var util = require('./utils');

exports.insertPictures = function (req, res){

		var version = util.getVersion(req);
		factory.insertPictures(res, version, req.body, function(err){
			if (err) {
				res.send(500, err.message);
			}
		});
}

exports.getPictures = function (req, res){
	var version = util.getVersion(req);
	factory.getPictures(res, version, function(err){
		if (err) {
			res.send(500, err.message);
		}
	});
	
}

exports.deletePictures = function (req, res){
	var version = util.getVersion(req);
	factory.deletePictures(res, version, req.body, function(err){
		if (err) {
			res.send(500, err.message);
		}
	});
}