/**
 * configuration
 */

module.exports = {
	"dev": {
		"test": {
			"mongodb": "localhost/test",
			"port": 8084
		}
	},		
	"prod": {
		"mongodb": "mongodb://admin:glenn@ds047948.mongolab.com:47948/db/photoapp",
		"port": 8081
	},
	"logger": {
		"api": "logs/api.log",
		"exception": "logs/exceptions.log",
		"test": "logs/tests.log"
	}
};
