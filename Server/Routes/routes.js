/**
 * Contains routes to different services
 */
var DelayHandler = require('../handler/DelayHandler');
var PictureHandler = require('../handler/PictureHandler');

function setup(app){
	//Delay
	app.get('/api/delay', DelayHandler.getDelay);
	app.put('/api/delay/:value', DelayHandler.setDelay);
	app.post('/api/picture/addPictures', PictureHandler.insertPictures);
}

exports.setup = setup;
