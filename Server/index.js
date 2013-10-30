var config = require('./Config/config');
var winston = require('winston');
var mongoose = require('mongoose');
var server = require('./server');
var configuration = "";
// log normal api operations into api.log
console.log("Starting logger...");
winston.add(winston.transports.File, {
	filename: config.logger.api
});

//log uncaught exceptions to exceptions.log
winston.handleExceptions(new winston.transports.File({
	filename: config.logger.exception
}));

if (process.env.NODE_ENV == 'development') {
	configuration = config.dev.test.mongodb;
}else{
	configuration = config.prod.mongodb;
}
console.log("logger started. Connection to mongoDB..");

mongoose.connect(configuration);

var db = mongoose.connection;
db.on('error', function(){
	console.log("An error occured trying to connect to mongodb: " + config.prod.mongodb);
	process.exit(1);
}/*console.error.bind(console, 'connection error:')*/);
db.once('open', function(){
	console.log("Successfully connected to mongoDB at: " + configuration + ".\nStarting web server...");
	server.app.set('port', process.env.Port || config.dev.test.port);
	server.start();
	console.log("Successfully started web server. Waiting for incoming connections...");
});
