/**
 * configuration
 */

module.exports = {
	"dev": {
		"test": {
			"mongodb": "mongodb://pensolut.net/phototest",
			"port": 33000
		},
		"mongodb": "mongodb://pensolut.net/photo",
		"url": "localhost:8084",		
		"port": 8084
	},
	"logger": {
			"api": "logs/api.log",
			"exception": "logs/exceptions.log",
			"test": "logs/tests.log"
	}
};
