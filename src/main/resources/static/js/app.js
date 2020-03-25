var stompClient = null;
var flag=0;

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
            let s=JSON.stringify(greeting);

            if(flag==0)
            {
                flag=1;
                showGreeting(JSON.parse(s).body);
            }


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
        flag=0;
        let q=JSON.stringify({'text': $("#txt-input").val()});
        let c=JSON.parse(q).text;
        $("#greetings").append("<tr><td>" + c + "</td></tr>");
       /* $.post('/questionList/saveQuestion');*/
        stompClient.send('/app/question', {}, c);

    }
}


function showGreeting(question1) {

    $("#greetings").append("<tr><td>" + question1 + "</td></tr>");
}


$(function () {
 /*   $("form").on('submit', function (e) {
        $.post('/questionList/saveQuestion');
        e.preventDefault();
    });*/
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });

});
