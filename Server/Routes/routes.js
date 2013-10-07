/**
 * Contains routes to different services
 */
function setup(app, handlers){
	app.get('api/delay', handlers.delay.getDelay);
}

exports.setup = setup;
