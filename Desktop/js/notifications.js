$(function () {
    $.connection.hub.url = localStorage.host + 'signalr';

    var nHub = $.connection.notification;
    nHub.client.TestNotification = function (title, message) {
        console.log('got notification' + title + ' ' + message);
        showNotification(title, message)
    };

    $.connection.hub.start().done(function () {
        console.log('connected');
        joinGroup();
    });
});

function joinGroup() {
    var nHub = $.connection.notification;
    nHub.server.joinGroup('s').done(function () {
        console.log('joined group');
    });
    console.log('attempting group join');
}

function showNotification(title, text) {
    var options =
        {
            icon: "http://r.ddmcdn.com/s_f/o_1/cx_633/cy_0/cw_1725/ch_1725/w_720/APL/uploads/2014/11/too-cute-doggone-it-video-playlist.jpg",
            body: text
        };

    var notification = new Notification(title, options);
    notification.onclick = function () {
        alert('Notification clicked');
    }

}

function postData() {
    var theData = JSON.stringify({ 'Title': "title",
                                    'Text': "text" });
    $.ajax({
        url: localStorage.host.concat('api/Notification'),
        data: theData,
        contentType: 'application/json; charset=utf-8',
        type: 'POST',
        headers: {
            'Authorization': 'Bearer ' + localStorage.accessToken
        },
        success: function (data) {
            window.location.href = './index.html';
        },
        error: function (data) {
            alert('Registration failed'.concat(JSON.stringify(data)));
        }
    });
}