/**
 * Contains routes to different services
 */
var handlers = require('../handler/DelayHandler');
function setup(app){
	app.get('/api/delay', handlers.getDelay);
}

exports.setup = setup;
