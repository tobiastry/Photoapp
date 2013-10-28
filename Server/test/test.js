/**
 * Sets up the version 1 api test
 */
var assert = require('assert');
var config = require('../Config/config');
var request = require('request');
var mongoose = require('mongoose');
var server = require('../server');
var Delay = require('../Model/V1Models').Delay;
var Picture = require('../Model/V1Models').Picture;
var url = "";
var db = "";
var ct = "application/v1+json";

describe ('Test', function(){

	before(function(done){
		url = "http://localhost:" + config.dev.test.port;

		console.log("logger started. Connection to mongoDB..");
		mongoose.connect(config.dev.test.mongodb);
		db = mongoose.connection;
		db.on('error', console.error.bind(console, 'connection error:'));
		db.once('open', function(){
			console.log("Successfully connected to mongoDB. Starting web server...");
			server.app.set('port', config.dev.test.port);
			server.start();
			console.log("Successfully started web server. Waiting for incoming connections...");
			done();
		})				
	})//end before

	after(function(done){
		Delay.remove({version: "v1", time: 4}, function(err){});
		Picture.remove({url: {$ne: null}}, function(err){});
		db.close();
		server.stop();
		done();
	})//end after
	
	describe ('Delay Test', function(){
		it('should insert delay of 4', function(done){
			request({				
				uri: url + '/api/delay/4',
				method: 'PUT',
				headers: {'Content-Type': ct}
									
				}, function(err, res, body){
				assert.equal(200, res.statusCode);
				assert.equal(4, JSON.parse(body).time);
				done()
			})
		})//end insert delay 4

		it('should return delay 4', function(done){
			request({				
				uri: url + '/api/delay',
				method: 'GET',
				headers: {'Content-Type': ct}
									
				}, function(err, res, body){
					assert.equal(200, res.statusCode, body);
					assert.equal(4, JSON.parse(body).time, body);
					done();
				})
		})// end get delay
	})//end delay describe
	
	describe ('Pictures Test', function(){
		var pictures = [];
		pictures.push({thumburl: "aaa", url: "fff"});
		pictures.push({thumburl: "bbb", url: "zzz"});
		it('should insert pictures', function(done){
			request({
				uri: url+ '/api/picture/addPictures',
				method: 'POST',
				headers: {'Content-Type': ct},
				body: JSON.stringify(pictures)
				}, function(err, res, body){
					assert.equal(200, res.statusCode, body);
					assert.equal(2, JSON.parse(body).successcount, body);
					done();
				})
		})// end insert pictures

		it('should return pictures', function(done){
			request({
				uri: url+ '/api/picture/getpictures',
				method: 'GET',
				headers: {'Content-Type': ct}
				}, function(err, res, body){
					assert.equal(200, res.statusCode, body);
					assert.equal(2, JSON.parse(body).length, body);
					assert.equal("bbb", JSON.parse(body)[0].thumburl, body);
					done();
				})
		})// end return pictures

		it('should delete pictures', function(done){
			var urls = [];
			urls.push({url: "aaa"});
			urls.push({url: "bbb"});
			request({
				uri: url+ '/api/picture/delete',
				method: 'DELETE',
				headers: {'Content-Type': ct},
				body: JSON.stringify(urls)
				}, function(err, res, body){
					assert.equal(200, res.statusCode, body);
					assert.equal(3, JSON.parse(body).deletecount, body);
					done();
				})
		})// end delete pictures
	})// end pictures describe 
	
})//end describe