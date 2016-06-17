$(function () {
            $.connection.hub.url = localStorage.host + 'signalr';

            var nHub = $.connection.notification;
            nHub.client.TestNotification = function (title, message) {
                alert('title:' + title + message);
            };

            $.connection.hub.start().done(function () {
                alert('connected');
            });
        });