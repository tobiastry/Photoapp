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
console.log("Successfully connected to mongoDB. Starting web server...");
server.start();
console.log("Successfully started web serer. Waiting for incoming connections...");
