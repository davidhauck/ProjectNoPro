var signalR = require('signalr-client');
var client;
window.onload = function beginSignalR() {
     client = new signalR.client(
        //signalR service URL
        "http://localhost:51471/signalr",
        // array of hubs to be supported in the connection
        ['notification']
        //, 10 /* Reconnection Timeout is optional and defaulted to 10 seconds */
        , true /* doNotStart is option and defaulted to false. If set to true client will not start until .start() is called */
    );
    client.headers['Authorization'] = 'Bearer ' + localStorage.accessToken;
    client.serviceHandlers.connected = function(connection) {
        console.log('connected');
        joinGroup();
    }
    client.serviceHandlers.connectFailed = function(conn) {
        alert('error ' + JSON.stringify(conn));
    };
    client.on(
        'notification',
        'TestNotification',
        function(title, message) {
            alert('title ' + title + ' message ' + message);
        });
    client.start();

};

function joinGroup() {
    alert('joining');
    client.invoke(
	'notification', // Hub Name (case insensitive)
	'JoinGroup',	// Method Name (case insensitive)
    's'
	);
}