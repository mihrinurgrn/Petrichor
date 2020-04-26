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
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);

            stompClient.subscribe('/topic/greetings', function (greeting) {
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
    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
    var questionRegister = {}
    questionRegister["text"] = $("#name").val();
    console.log(questionRegister);
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/questionList/saveQuestion",
        data: JSON.stringify(questionRegister),
        dataType: 'json',
        cache: false,
        timeout: 600000
    });
}
function voteQuestion(questionId) {
  let id=JSON.stringify( questionId )
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/vote",
        data: "{\"questionId\": "+ id +"}",
        dataType: 'json',
        cache: false,
        timeout: 600000
    });


}
function showGreeting(message)
  {
          $.ajax({
          type : "GET",
          url : "/example",
          success : function(result) {
              console.log(result);
              var table = $('#tableQuestions');
              if (result.msg == "ok") {
                  $('#tableQuestions').empty();
                  $.each(result.result, function(i, question) {
                       $('#tableQuestions').append("<tr><td>" +  question.text + "</td><td>" + question.voteValue + "</td><td>\n" +
                          "                                <button onclick=\"voteQuestion(" + question.questionId + ")\" class=\"btn btn-default\" id=\"vote\" type=\"submit\" value=\"question.questionId\"> Vote the question</button>\n" +
                          "\n" +
                          "                            </td></tr>");
                         });
                  console.log("Success: ", result);

              } else {
                  $("#getResultDiv").html("<strong>Error</strong>");
                  console.log("Fail: ", result);
              }
          },
          error : function(e) {
              $("#getResultDiv").html("<strong>Error</strong>");
              console.log("ERROR: ", e);
          }
      });
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    console.log("test");

    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
    $( "#vote" ).click(function() { voteQuestion(); });
});