Alarm plugin for Ionic
===============
This plugin allow to set an alarm. When the alarm trigger the app is launched and shown even on the lock screen of the phone.
The user will see the alarm clock icon is the status bar and on the lock screen.

#### Warning
**When the alarm is triggered a new webview is created and shown on top of the lock screen. Use the function "isFromAlarmTrigger" to know if the app was started by the alarm plugin.**


#### Platform Support
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

- Check if app was started by the alarm

The success callback return true when the webview was started by the alarm
```javascript
window.alarm.isFromAlarmTrigger(successCallback, errorCallback);
```
