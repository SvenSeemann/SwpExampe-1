/**
 * Created by justusadam on 12/12/14.
 */

var debug = true;



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

function init_check() {
    check_messages();
    //window.setInterval(check_messages, 30000);
}

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


var Message = function(message_content, dateReceived, sender, id) {
    this.message_content = message_content;
    this.dateReceived = dateFromReceived(dateReceived);
    this.sender = sender;
    this.id = id;
};


function add_messages(data) {
    for (var i = 0 ; i < data.length ; i++) {
        var message = data[i];
        message = new Message(message.message, message.date, message.sender, message.id);
        if (!messages.contains(message)) {
            messages.add_new(message)
        }
    }
}


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
        }
    })
}

function new_form(receiver) {
    var form = $('<form id="message-forms" action="/messaging/test/send" method="post">' +
    '<input type="hidden" value="' + receiver + '" name="recipient">' +
    '<label for="message-content">Your Message</label>' +
    '<textarea name="message" id="message-content"></textarea>' +
    '<button type="Submit" value="submit">Submit</button>' +
    '</form>');
    form.submit(function(e) {
        send_message(e, this);
    });
    $('#message-forms').html(
        form
    )
}

var Receiver = function(firstname, lastname, id) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.id = id;
};

var receivers = {
    html_represent : $("#recipients"),
    array : [],
    add : function (receiver) {
        if (!this.contains(receiver)) {
            this.array.push(receiver);
            this.html_represent.append(
                '<a onclick="new_form(\'' + receiver.id + '\')">' + receiver.firstname + ' ' + receiver.lastname + '</a>'
            );
        }
    },
    contains : function (receiver) {
        for (var i = 0 ; i < this.array.length ; i++) {
            if (this.array[i].id == receiver.id) return true;
        }
        return false;
    },
    add_all : function (receivers) {
        if (typeof receivers == typeof []) {
            for (var i = 0; i < receivers.length; i++) {
                var receiver = receivers[i];
                receiver = new Receiver(receiver.firstname, receiver.lastname, receiver.id.identifier);
                this.add(receiver);
            }
        }
    }
};

function refresh_receivers() {
    get_receivers(function(data) {
        receivers.add_all(data);
    });
}

function init_receivers() {
    refresh_receivers();
}

function get_receivers(callback) {
    $.ajax({
        type : "POST",
        cache : false,
        url : debug ? '/messaging/test/get/receivers' : '/messaging/get/receivers',
        success : callback
    })
}

$(document).ready(function() {
    init_check();
    init_receivers();
    $('form#message-form').submit(function(e) {
        send_message(e, this);
    });
    $('#refresh-messages').on('click', function() {
        check_messages();
    });
    $('#refresh-receivers').on('click', function() {
        refresh_receivers();
    });
});
