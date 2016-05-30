
var lights = require('./lights.js')
  , async = require('async')
  , gpio = require('../pigpio')
  , http = require('http')
;

for(var key in lights) {
  exports[key] = lights[key];
}

exports.config = function(_config) {
  config = _config;
}

var config = {
  hostname: "127.0.0.1",
  port: "8081"
}

var GPIO_LIGHT = {}
GPIO_LIGHT[lights.RED] = 17;
GPIO_LIGHT[lights.YELLOW] = 27;
GPIO_LIGHT[lights.GREEN] = 22;
GPIO_LIGHT[lights.AB] = 23;
GPIO_LIGHT[lights.CD] = 24;
GPIO_LIGHT[lights.HORN] = 25;

exports.ports = function(value) {
  var ports = [];
  for(var key in lights) {
    if(value & lights[key]) {
      ports.push(GPIO_LIGHT[lights[key]]);
    }
  }
  return ports;
}

function _check_and_set_light(value, light) {
  if(light & value) { gpio.set(GPIO_LIGHT[light]); }
  else { gpio.unset(GPIO_LIGHT[light]); }
}

exports.lights = function(value) {
  //if(lights.OFF === value) {
    //_check_and_set_light(value, lights.OFF);
    //return;
  //}
  _check_and_set_light(value, lights.RED);
  _check_and_set_light(value, lights.YELLOW);
  _check_and_set_light(value, lights.GREEN);
  _check_and_set_light(value, lights.AB);
  _check_and_set_light(value, lights.CD);
}

exports.sound = function(times, duration, pause) {
  var _times = times;

  function _set_sound() {
    _times--;
    gpio.set(GPIO_LIGHT[lights.HORN]); console.log("SOUND");

    setTimeout(function() {
      gpio.unset(GPIO_LIGHT[lights.HORN]); console.log("PAUSE");
      if(_times > 0) setTimeout(_set_sound, pause );
    }, duration);
  }
  _set_sound();
}

function _register() {
  http.get({
    hostname: config.hostname,
    port: config.port,
    path: "/register"
  })
  .on('error', function(err) { /* DO NOTHING */ });

  setTimeout(_register, 5000);
}
_register();
