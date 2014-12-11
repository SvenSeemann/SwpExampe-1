/**
 * Created by justusadam on 10/12/14.
 */

var debug = true;

function send_message(e) {
    e.preventDefault();

    var form = $(this);
    $.ajax({
        type: "POST",
        cache : false,
        url : form.attr('action'),
        data : form.serialize(),
        success : function(data){
            e.target.reset();
        }
    })
}

function add_messages(data) {
    data = JSON.parse(data);
    for (var message in data.messages) {
        if (!messages.contains(message)) {
            messages.add(message)
        }
    }
}

var Message = function(message_content, dateReceived, sender) {
    this.message_content = message_content;
    this.dateReceived = dateReceived;
    this.sender = sender
};

var messages = {
    message_thing : $("#messages"),
    array : [],
    add : function (message) {
        this.array.push(message);
        this.message_thing.append(
            '<tr><td>' + message.sender + '</td><td>' + message.dateReceived + '</td><td>' + message.message_content + '</td>'
        );
    },
    contains : function(message) {
        return this.array.indexOf(message) != -1
    }
};

function check_messages() {
    $.ajax({
        type: "POST",
        cache : true,
        url : debug ? '/messaging/test/get' : '/messaging/get',
        data : {
            date : messages[messages.length - 1].dateReceived
        },
        success : function (data) {
            add_messages(data);
        }
    })
}


$(document).ready(function() {
    var forms = $('form.message-form');
    for (var form in forms) {
        $(form).submit(send_message);
    }
    window.setInterval(check_messages, 60000);
});
