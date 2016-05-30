
var express = require('express')
  , app = express()
  , bodyParser = require('body-parser')
  , lights = require('./lib/lights')
  , gpio = require('./lib/pigpio')
  , config = require('./config.json')
;

lights.config({
  hostname: config.controller.ip,
  port: config.controller.port
});

//app.use(bodyParser());
app.use(bodyParser.text());
app.use(bodyParser.urlencoded({ extended: false }));

app.post('/', function(req, res) {
  console.log(req.body);

  lights.lights(req.body.lights);
  lights.sound(req.body.times, req.body.duration, req.body.pause);

  res.sendStatus(200);
});

lights.ports(lights.RED | lights.YELLOW | lights.GREEN | lights.AB | lights.CD )
  .forEach(function(port) { gpio.set(port); });


app.listen(8081);
