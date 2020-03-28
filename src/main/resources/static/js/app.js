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
            let k=JSON.parse(greeting.body).text;
            let m=JSON.stringify(k);
            $("#greetings").append("<tr><td>" + m + "</td></tr>");

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
        let q=JSON.stringify({'text': $("#txt-input").val()});
        let c=JSON.parse(q).text;
        let s=JSON.stringify(c);
        stompClient.send('/app/question', {}, c);
        /* $.post('/questionList/saveQuestion');*/
        var questionRegister = {}
        questionRegister["text"] = $("#text").val();
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/questionList/saveQuestion",
            data: JSON.stringify(questionRegister),
            dataType: 'json',
            cache: false,
            timeout: 600000

        });

        /*
                if(flag==0)
                {
                    flag=0;
                    showGreeting(JSON.parse(s).body);
                }

                flag=0;*/

    }
}



function showGreeting(question1) {

    $("#greetings").append("<tr><td>" + question1 + "</td></tr>");
}


$(function () {
    $("form-login").on('submit', function (e) {
        e.preventDefault();
    });
     $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });

});



