var config = require('./Config/config');
var express = require('express');
var app = express()

var DelayHandler = require('./handler/DelayHandler');

var routes = require('./Routes/routes');
var fs = require('fs');

var expressLogFile = fs.createWriteStream('./logs/express.log', {flags: 'a'});
// Configuration
app.configure(function(){
	app.use(express.logger({stream: expressLogFile}));
	app.use(express.bodyParser());
	app.use(express.methodOverride());
	app.use(app.router);
	app.use(express.static(_dirname + '/public'));
});

app.configure('development', function(){
	app.use(express.errorHandler({dumpExceptions: true, showStack: true }));
});

app.configure('production', function(){
	app.use(express.errorHandler());
});

var handlers = {
	delay: new DelayHandler();
};

function start(){
	routes.setup(app, handlers);
	var port = process.env.Port || config.dev.port;
	app.listen(port);
	console.log("Express server listening on port %d in %s mode", port, app.settings.env);
}

// exports
exports.start = start;
exports.app = app;
