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
		"mongodb": "pensolut.com/live",
		"port": 8081
	},
	"logger": {
		"api": "logs/api.log",
		"exception": "logs/exceptions.log",
		"test": "logs/tests.log"
	}
};
