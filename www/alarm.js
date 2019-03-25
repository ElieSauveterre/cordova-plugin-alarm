var exec = require('cordova/exec');

var PLUGIN_NAME = 'Alarm';

var Alarm = {
    add: function (successCallback, errorCallback) {
        exec(successCallback, errorCallback, PLUGIN_NAME, "add", []);
    }
};

module.exports = Alarm;
