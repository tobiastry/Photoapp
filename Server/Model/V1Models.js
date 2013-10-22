var mongoose = require('mongoose'),
	Schema = mongoose.Schema;

var delaySchema = new Schema({
	version: {type: String, default: 'v1'},
	time: {type: Number, default: 1}
});

var pictureSchema = new Schema({
	thumburl: String,
	url: String
});

exports.Delay = mongoose.model('Delay', delaySchema, 'delay');
exports.Picture = mongoose.model('Picture', pictureSchema, 'picture');
