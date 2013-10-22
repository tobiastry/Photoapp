/**
 * configuration
 */

module.exports = {
	"dev": {
		"test": {
			"mongodb": "pensolut.com/test",
			"port": 8084
		}
	},		
	"prod": {
		"mongodb": "pensolut.com/live",
		"port": 80
	},
	"logger": {
		"api": "logs/api.log",
		"exception": "logs/exceptions.log",
		"test": "logs/tests.log"
	}
};
