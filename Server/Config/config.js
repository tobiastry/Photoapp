/**
 * configuration
 */

module.exports = {
	"dev": {
		"test": {
			"mongodb": "mongodb://admin:glenn@ds047948.mongolab.com:47948/db",
			"port": 33000
		},
		"mongodb": "mongodb://admin:glenn@ds047948.mongolab.com:47948/db/photoapp",
		"url": "localhost:8084",		
		"port": 8084
	},
	"logger": {
			"api": "logs/api.log",
			"exception": "logs/exceptions.log",
			"test": "logs/tests.log"
	}
};
