var gui = require('nw.gui');
//var host = 'http://localhost:51471/';
var host = 'https://microsoft-apiapp7ba89e03b5fa4c9b8f7e54324dddecb0.azurewebsites.net/';

function beginGoogleAuth() {
  $.ajax({
    url: host.concat('api/Account/ExternalLogins?returnUrl=/&generateState=true'),
    type: "GET",
    success: onOauthUrlsFound,
    error: function (res) {
      alert(JSON.stringify(res));
    }
  });
}

function onOauthUrlsFound(res) {
  var googleUrl = findGoogleUrl(res);
  var finalUrl = host.concat(googleUrl.Url);
  $("#testLabel").text(finalUrl);
  var win = gui.Window.get();
  var authWindow = gui.Window.open(host.concat(googleUrl.Url), {}, function (new_win) {
    new_win.on('loaded', function () {
      console.log('loaded');
      (function loop() {
        var l = new_win.window.location.href;
        var i = l.indexOf('#access_token');
        if (i > -1) {
          var start = l.indexOf('=', i) + 1;
          var end = l.indexOf('&', i);
          var token = l.substring(start, end);
          console.log(token);
          accessTokenObtained(token);
        }
        else {
          setTimeout(loop, 1000)
        }
      })();
    });
  });
}

function findGoogleUrl(arr) {
  for (var i = 0; i < arr.length; i++) {
    if (arr[i]["Name"] == "Google") {
      return arr[i];
    }
  };
}

function accessTokenObtained(token) {
  $.ajax({
    url: host.concat('api/Account/UserInfo'),
    type: "GET",
    success: function (res) {
      alert(JSON.stringify(res));
    },
    headers: {
      'Authorization': 'Bearer ' + token
    },
    error: function (res) {
      alert(JSON.stringify(res));
    }
  });
}