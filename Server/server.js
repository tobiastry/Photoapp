var http = require('http');
var server = http.createServer(function(req, res){
	res.writeHead(200, {'Content-Type': 'text/plain'});
	res.end('hello world\n');
});

server.listen(13000, '127.0.0.1');
console.log('server running!');