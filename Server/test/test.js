/**
 * Sets up the version 1 api test
 */
//var should = require('should');
var assert = require('assert');
//var request = require('supertest');
//var mongoose = require('mongoose');
var winston = require('winston');
var config = require('../Config/config');
//var routes = require('./v1/routes');
//var server = require('../server');
var request = require('request');


	describe('PUT /api/delay/2', function(){
		var url = "";
		before(function(done){
			url = "http://localhost:" + config.dev.port;	
			done();			
		})
		
		it('should return 4', function(done){
			request({				
				uri: url + '/api/delay/4',
				method: 'PUT',
				headers: {'Content-Type': 'application/v1+json'}
									
			}, function(err, res, body){
				assert.equal(200, res.statusCode);
				assert.equal(4, JSON.parse(body).time);
				done()
			})
		})
	})

	describe('GET /api/delay', function(){
		var url = "http://localhost:" + config.dev.port;
		it('should return 4', function(done){
			request({				
				uri: url + '/api/delay',
				method: 'GET',
				headers: {'Content-Type': 'application/v1+json'}
									
			}, function(err, res, body){
				
				assert.equal(200, res.statusCode, body);
				assert.equal(4, JSON.parse(body).time, body);
				assert.equal("v1", JSON.parse(body).version, body);
				done();
			})
		})
	})
