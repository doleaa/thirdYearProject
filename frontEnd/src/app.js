var express = require('express'),
    http = require('http'),
    path = require('path'),
    bodyParser = require('body-parser'),
    request = require('request');


var app = express(),
    server = http.createServer(app);
    port = 3068;

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));

/**
 * Serves static index.html located in the "public" directory
 */
app.use('/', express.static(path.join(__dirname, 'public')));

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  var err = new Error('Not Found');
  err.status = 404;
  next(err);
});

// error handler
app.use(function(err, req, res, next) {
  res.status(err.status || 500);
  res.json({
    message: err.message,
    error: err
  });
});

// Starting server...
server.listen(port, function() {
  console.log('Server started and listening on: ' + port);
});
