/**
 * Created by justusadam on 10/12/14.
 */

var debug = true;

function send_message(e, form) {
    e.preventDefault();
    form = $(form);
    console.log(form);
    $.ajax({
        type: "POST",
        cache : false,
        url : form.attr('action'),
        data : form.serialize(),
        success : function(data){
            e.target.reset();
            check_messages();
        }
    })
}

function add_messages(data) {
    if (!messages.contains(data)) {
        messages.add(data)
    }
    //data = JSON.parse(data);
    //for (var message in data.messages) {
    //    if (!messages.contains(message)) {
    //        messages.add(message)
    //    }
    //}
}

var Message = function(message_content, dateReceived, sender) {
    this.message_content = message_content;
    this.dateReceived = dateReceived;
    this.sender = sender
};

var messages = {
    message_thing : $("#messages").children('tbody'),
    array : [],
    add : function (message) {
        //this.array.push(message);
        this.message_thing.append(message)
    },
    contains : function(message) {
        return this.array.indexOf(message) != -1
    }
};

function check_messages() {
    $.ajax({
        type: "POST",
        cache : false,
        url : debug ? '/messaging/test/get' : '/messaging/get',
        data : {
            last : messages.message_thing.children().last('tr').children('.message-date').text()
        },
        success : function (data) {
            add_messages(data);
        }
    })
}

function init_check() {
    check_messages();
    window.setInterval(check_messages, 30000);
}


$(document).ready(function() {
    init_check();
    $('form#message-form').submit(function(e) {
        send_message(e, this);
    });
    $('#refresh-messages').on('click', function() {
        check_messages();
    });
});
