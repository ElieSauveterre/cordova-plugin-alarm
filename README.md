Alarm plugin for Ionic
===============

#### Warning
**When the alarm is triggered a new webview is created and shown on top of the lock screen. Use the "alarmFired" event to know if the app was started by the alarm plugin.**


####Platform Support
Android with Ionic only. The lock screen webview uses IonicWebViewEngine, it could be replaced by CordovaWebViewEngine to work for Cordova.


#### Installation
```bash
cordova plugin add https://github.com/ElieSauveterre/cordova-plugin-alarm
```

#### Usage
- Add
```javascript
window.alarm.add(successCallback, errorCallback, {
    date: '2019-03-08 08:06:00'
});
```

- Remove
```javascript
window.alarm.remove(successCallback, errorCallback);
```

- Snooze
```javascript
window.alarm.snooze(successCallback, errorCallback);
```

- Stop
```javascript
window.alarm.stop(successCallback, errorCallback);
```

- Listen to alarm fire event

The success callback is only called on the webview launched by the alarm.
```javascript
window.alarm.alarmFired(successCallback, errorCallback);
```