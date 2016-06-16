var gui = require('nw.gui');
//var host = 'http://localhost:51471/';
var host = 'https://microsoft-apiapp7ba89e03b5fa4c9b8f7e54324dddecb0.azurewebsites.net/';

if (localStorage.accessToken != null) {
    window.location.href = './index.html';
}

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
          localStorage.accessToken = token;
          accessTokenObtained();
          new_win.close();
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

function accessTokenObtained() {
  $.ajax({
    url: host.concat('api/Account/UserInfo'),
    type: "GET",
    success: function (res) {
      if (res.HasRegistered == false) {
        registerUser(res);
      }
      else {
        window.location.href = './index.html';
      }
    },
    headers: {
      'Authorization': 'Bearer ' + localStorage.accessToken
    },
    error: function (res) {
      alert(JSON.stringify(res));
    }
  });
}

function registerUser(account) {
  var theData = JSON.stringify({ 'Name': account.Name });
  $.ajax({
    url: host.concat('api/Account/RegisterExternal'),
    data: theData,
    contentType: 'application/json; charset=utf-8',
    type: 'POST',
    headers: {
      'Authorization': 'Bearer ' + localStorage.accessToken
    },
    success: function (data) {
      alert('Registration success'.concat(JSON.stringify(data)));
      window.location.href = './index.html';
    },
    error: function (data) {
      alert('Registration failed'.concat(JSON.stringify(data)));
    }
  });
}