/**
 * Created by justusadam on 10/12/14.
 */

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

var messages = [];

function check_messages() {
    $.ajax({
        type: "POST",
        cache : true,
        url : '/messages/get',
        data : {
            date : messages[messages.length - 1]
        }
    })
}


$(document).ready(function() {
    var forms = $('form.message-form');
    for (var form in forms) {
        $(form).submit(send_message);
    }
});
