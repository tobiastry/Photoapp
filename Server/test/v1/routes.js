/**
 * the v1 tests
 */
var should = require('should');
var assert = require('assert');
var request = require('supertest');
var mongoose = require('mongoose');
var winston = require('winston');
var config = require('./Config/config');

var url = config.dev.url;
function tests() {
	
	it('should return the slideshow delay', function() {
		request(url)
			.get('/api/delay')
			.expect('Content-Type', 'application/v1+json')
			.expect(400)
			//end handles the response
			.end(function(err, res) {
				if(err){
					throw err;
				}
				//this is should.js syntax
				res.should.have.status(200);
				res.body.time.should.equal(4);
				done();
			});
	});
}

//exports
exports.tests = tests;
