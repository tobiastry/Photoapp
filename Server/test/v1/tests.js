/**
 * the v1 tests
 */
var should = require('should');
var assert = require('assert');
var request = require('supertest');
var mongoose = require('mongoose');
var winston = require('winston');
var config = require('./Config/v1/config-debug');

var url = config.url;
function tests() {
	
	it('should return the slideshow delay', function() {
		request(url)
			.get('/api/delay')
			.set('Accept', 'application/v1+json')
			.expect('Content-Type', 'v1+json')
			.expect(400)
			//end handles the response
			.end(function(err, res) {
				if(err){
					throw err;
				}
				//this is should.js syntax
				res.should.have.status(400);
				res.body.time.should.equal(1);
				done();
			});
	});
}

//exports
exports.tests = tests;
