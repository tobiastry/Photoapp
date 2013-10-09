var request = require('supertest');
var expect  = require('expect.js');

describe('delay', function(){
	it (function(done){
		request.get('localhost:8084/api/delay').end(function(res){
			expect(res).to.exist;
			expect(res.status).to.equal(200);
			expect(res.body.time).to.equal(4);
			done();
		});
	});
});
