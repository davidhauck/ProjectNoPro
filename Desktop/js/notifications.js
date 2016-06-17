$(function () {
    $.connection.hub.url = localStorage.host + 'signalr';

    var nHub = $.connection.notification;
    nHub.client.TestNotification = function (title, message) {
        alert('got notification');
        showNotification(title + message)
    };

    $.connection.hub.start().done(function () {
        alert('connected');
    });
});

function showNotification(text) 
{
    var options = 
        {
            icon: "http://r.ddmcdn.com/s_f/o_1/cx_633/cy_0/cw_1725/ch_1725/w_720/APL/uploads/2014/11/too-cute-doggone-it-video-playlist.jpg",
            body: text
        };

    var notification = new Notification("Notification Title",options);
    notification.onclick = function () 
    {
        alert('Notification clicked');
    }
    
}