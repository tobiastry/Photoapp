var Delay = require('../Model/v1/Delay');

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
				res.send(200, delay);
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
				if(err) throw new DBException();//throw new DBException(err);
				res.send(200, {time: value});
			});
			break;
		default:
			throw new DBException("an undefined error occured! " + version);
	}
}
