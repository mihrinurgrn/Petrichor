var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/question', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    if(stompClient !== null) {
        stompClient.send('/app/question', {}, JSON.stringify({'txt-input': $("#txt-input").val()}));
    }
}

function showGreeting(questiont) {
    $("#greetings").append("<tr><td>" + questiont + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        $.post('/questionList/saveQuestion')
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });    $( "#disconnect" ).click(function() { disconnect(); });

    $( "#send" ).click(function() { sendName(); });
});
