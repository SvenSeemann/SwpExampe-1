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
    for (var i = 0 ; i < data.length ; i++) {
        var message = data[i];
        message = new Message(message.message, message.date, message.sender, message.id);
        if (!messages.contains(message)) {
            messages.add_new(message)
        }
    }
}

function dateFromReceived(dateReceived) {
    return new Date(
        dateReceived.year,
        dateReceived.monthValue - 1, // because the month value is 0-based
        dateReceived.dayOfMonth,
        dateReceived.hour,
        dateReceived.minute,
        dateReceived.second
    );
}

var Message = function(message_content, dateReceived, sender, id) {
    this.message_content = message_content;
    this.dateReceived = dateFromReceived(dateReceived);
    this.sender = sender;
    this.id = id;
};

var messages = {
    message_thing : $("#messages").children('tbody'),
    array : [],
    add_new : function (message) {
        this.array.push(message);
        this.message_thing.append(
            '<tr class="highlight">' +
            '<td class="message-sender">' + message.sender.firstname + message.sender.lastname + '</td>' +
            '<td class="message-date">[' + message.dateReceived.toString() + ']</td>' +
            '<td class="message-separator" style="text-align: center;">:</td>' +
            '<td class="message-message">' + message.message_content + '</td>' +
            '</tr>'
        )
    },
    contains : function(message) {
        for (var i = 0 ; i < this.array.length ; i++) {
            if (message.id == this.array[i].id) return true;
        }
        return false;
    }
};

function check_messages() {
    $.ajax({
        type: "POST",
        cache : false,
        url : debug ? '/messaging/test/get' : '/messaging/get',
        data : {
            last : messages.array.length == 0 ? new Date(1000,0).toUTCString() : messages.array[messages.array.length - 1].dateReceived.toUTCString()
        },
        success : function (data) {
            if (data.length > 0) {
                messages.message_thing.children('.highlight').removeClass('highlight');
                add_messages(data);
            }
        }
    })
}

function init_check() {
    check_messages();
    //window.setInterval(check_messages, 30000);
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
