
var fs = require('fs')
  , async = require('async')
;

var is_init = false;
var PORT_ON = 0, PORT_OFF = 1;
var DIRECTION_IN = 'in', DIRECTION_OUT = 'out';

var GPIO_BASE_PATH = '/sys/class/gpio'
  , GPIO_PREFIX = 'gpio'
;

function _get_gpio_path(port, file) {
  return GPIO_BASE_PATH + '/' + GPIO_PREFIX + port + '/' + file;
}

function _get_gpio_direction_path(port) {
  return _get_gpio_path(port, 'direction');
}

function _get_gpio_value_path(port) {
  var path = _get_gpio_path(port, 'value');
  console.log(port);
  return path;
}

function _set_port_state(port, state) {
  fs.writeFile( _get_gpio_value_path(port), state );
}

exports.set = function(port) {
  _set_port_state(port, PORT_ON);
}

exports.unset = function(port) {
  _set_port_state(port, PORT_OFF);
}

/**
Schema:
[
  {
    gpio: 27,
    direction: 'out',
    value: 0
  } ,
  { ... }
]
*/
exports.init = function(initdata) {
  if(is_init) return;

  async.each(initdata, function(settings) {
    console.log(settings);
    async.series([
      function(callback) {
        fs.writeFile( GPIO_BASE_PATH + '/export', settings.gpio, function() { callback(); } );
      },
      function(callback) {
        fs.writeFile( _get_gpio_direction_path(settings.gpio), settings.direction, function() { callback(); } );
      },
      function(callback) {
        fs.writeFile( _get_gpio_value_path(settings.gpio), settings.value, function() { callback(); }  );
      }
    ], function() {
      is_init = true;
    });

  });

}
