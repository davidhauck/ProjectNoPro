var gui = require('nw.gui');

function beginGoogleAuth() {

  $.ajax({
    url: "https://microsoft-apiapp7ba89e03b5fa4c9b8f7e54324dddecb0.azurewebsites.net/api/Account/ExternalLogins?returnUrl=/&generateState=true",
    type: "GET",
    success: onOauthUrlsFound,
    error: function(res) {
      alert(JSON.stringify(res));
    }
  });
}

function onOauthUrlsFound(res) {
  var googleUrl = findGoogleUrl(res);
  var finalUrl = 'https://microsoft-apiapp7ba89e03b5fa4c9b8f7e54324dddecb0.azurewebsites.net'.concat(googleUrl.Url);
  $("#testLabel").text(finalUrl);
  var authWindow = window.open(finalUrl, "authWindow");
  setTimeout(function() {
      authWindow.alert(authWindow.location.href);
      authWindow.location.href = finalUrl;
  }, 2000);
//  $(authWindow.document).bind('load', function(e) {
//    alert("hello");
//  });
}

function findGoogleUrl(arr) {
  for (var i=0; i < arr.length; i++) {
    if (arr[i]["Name"] == "Google") {
      return arr[i];
    }
  };
}