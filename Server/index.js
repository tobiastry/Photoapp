var config = require('./Config/config');
var winston = require('winston');
var mongoose = require('mongoose');
var server = require('./server');

// log normal api operations into api.log
console.log("Starting logger...");
winston.add(winston.transports.File, {
	filename: config.logger.api
});

//log uncaught exceptions to exceptions.log
winston.handleExceptions(new winston.transports.File({
	filename: config.logger.exception
}));

console.log("logger started. Connection to mongoDB..");
mongoose.connect(config.dev.mongodb);
var db = mongoose.connection;
db.on('error', console.error.bind(console, 'connection error:'));
db.once('open', function(){
	console.log("Successfully connected to mongoDB. Starting web server...");
	server.app.set('port', process.env.Port || config.dev.port);
	server.start();
	console.log("Successfully started web server. Waiting for incoming connections...");
});
