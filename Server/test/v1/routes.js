/**
 * Sets up the version 1 api test
 */
var should = require('should');
var assert = require('assert');
var request = require('supertest');
var mongoose = require('mongoose');
var winston = require('winston');
var config = require('../../Config/config');
var tests = require('./tests');

describe('Routing', function() {
	
	before(function() {
		mongoose.connect(config.dev.db.mongodb);
		done();
	});
	 // use describe to give a title to your test suite, in this case the tile is "Account"
	// and then specify a function in which we are going to declare all the tests
	// we want to run. Each test starts with the function it() and as a first argument
	// we have to provide a meaningful title for it, whereas as the second argument we
	// specify a function that takes a single parameter, "done", that we will use
	// to specify when our test is completed, and that's what makes easy
	// to perform async test!
	
	describe('API', tests);
});
