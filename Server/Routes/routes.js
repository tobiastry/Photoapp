/**
 * Contains routes to different services
 */
var DelayHandler = require('../handler/DelayHandler');

function setup(app){
	//Delay
	app.get('/api/delay', DelayHandler.getDelay);
	app.put('/api/delay/:value', DelayHandler.setDelay);
}

exports.setup = setup;
