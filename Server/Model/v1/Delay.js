// The Delay model

var mongoose = require('mongoose'),
	Schema = mongoose.Schema;

var delaySchema = new Schema({
	version: {type: String, default: 'v1'},
	time: {type: Number, default: 1}
});

module.exports = mongoose.model('Delay', delaySchema, 'delay');
