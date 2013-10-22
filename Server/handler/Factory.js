var Delay = require('../Model/V1Models').Delay;
var Picture = require('../Model/V1Models').Picture;

function VersionException(message){
	this.message = message;
	this.name = "VersionException";
}


function DBException(message){
	this.message = message;
	this.name = "DbException";
}

exports.getDelay = function(res, version){
	switch(version){
		case "v1":
			Delay.findOne({version: version}, function(err, delay){
				if(err) throw new DBException(err);
				else{
					res.send(200, delay);
				}
			});
			break;
		default:
			throw new VersionException("you must supply a Content-Type as shown in the documentation.");
	}
}

exports.setDelay = function(res, version, value){
	switch(version){
		case "v1":
			Delay.update({version: version}, {time: value}, {upsert: true}, function(err, num, raw){
				if(err) throw new DBException(err);
				else{
					res.send(200, {time: value});
				}
			});
			break;
		default:
			throw new DBException("an undefined error occured! " + version);
	}
}


exports.insertPictures = function(res, version, body){
	var success = 0;
	var insert = function(picture, last){
		Picture.update({url: picture.url}, picture, {upsert: true}, function(err){
			if (!err) success++;
			if (last) {
				res.send(200, {successcount: success})
			};
		});
	};
	switch(version){
		case "v1":

			for (var i = body.length - 1; i >= 0; i--) {
				if (i == 0) {
					insert(body[i], true);
				}else{
					insert(body[i]);
				}
			}
			break;
		default:
			throw new VersionException("you must supply a Content-Type as shown in the documentation.");
	}
}

exports.getPictures = function (res, version){
	switch(version){
		case "v1":
				Picture.find({url: {$ne: null}}, function(err, pictures){
					if(err) throw new DBException(err);
					else{
						res.send(200, pictures);
					}
				});
			break;
		default:
			throw new VersionException("you must supply a Content-Type as shown in the documentation.");
	}
}
