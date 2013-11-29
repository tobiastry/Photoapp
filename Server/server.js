var config = require('./Config/config');
var express = require('express');
var Delay = require('./Model/V1Models').Delay;
var app = express();

var routes = require('./Routes/routes');
var fs = require('fs');

var expressLogFile = fs.createWriteStream('./logs/express.log', {flags: 'a'});
// Configuration
app.configure(function(){
	app.use(express.logger({stream: expressLogFile}));
	app.use(express.json());
	app.use(express.urlencoded());
	app.use(express.methodOverride());
	app.use(app.router);
	//app.use(express.static(_dirname + '/public'));
});

app.configure('development', function(){
	//app.use(express.errorHandler({dumpExceptions: true, showStack: true }));
	app.use(function(err, req, res, next){
		console.error(err.stack);
		try {
        	JSON.parse(req.body);
    	} catch (e) {
        	res.send(400, err);
        	return;
    	}
		res.send(500, err);
	});
});

app.configure('production', function(){
	app.use(express.errorHandler());
});
var server = "";
function start(){
	Delay.findOne({version: "v1"}, function(err, delay){
		if(!delay) {
			Delay.create({}, function(err, delay){
				if (delay) {
					init();
				}else{
					process.exit(1);
				}
			});
		}else{
			init();
		}
	});
	
}

function init(){
	routes.setup(app);
		server = app.listen(app.get('port'), function(){
			if (process.env.NODE_ENV == 'production') {
				console.log("listening on port: " + app.get('port'));
			}
		});
}

function stop(){
	server.close();
	process.exit(0);
}

// exports
exports.start = start;
exports.app = app;
exports.stop = stop;
//test post hooks
