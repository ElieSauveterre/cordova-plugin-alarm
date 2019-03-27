var exec = require('cordova/exec');

var PLUGIN_NAME = 'Alarm';

var Alarm = {
    add: function (successCallback, errorCallback, options) {
        exec(successCallback, errorCallback, PLUGIN_NAME, "add", [options]);
    },
    remove: function (successCallback, errorCallback) {
        exec(successCallback, errorCallback, PLUGIN_NAME, "remove", []);
    },
    stop: function (successCallback, errorCallback) {
        exec(successCallback, errorCallback, PLUGIN_NAME, "stop", []);
    },
    snooze: function (successCallback, errorCallback) {
        exec(successCallback, errorCallback, PLUGIN_NAME, "snooze", []);
    },
    alarmFired: function (successCallback, errorCallback) {
        exec(successCallback, errorCallback, PLUGIN_NAME, "alarmFired", []);
    }
};

module.exports = Alarm;

