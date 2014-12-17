// Generated by CoffeeScript 1.8.0
(function() {
  var Messaging, debug;

  debug = false;

  Messaging = (function() {
    function Messaging() {
      var Message, Receiver, add_messages, chat_visible, check_messages, date_from_received, get_receivers, hide_chat, messages, new_form, receivers, refresh_receivers, send_message, show_chat, templates, toggle_chat;
      templates = $('#messaging-templates');
      this.templates = templates.clone();
      templates.remove();
      date_from_received = function(date) {
        return new Date(date.year, date.monthValue - 1, date.dayOfMonth, date.hour, date.minute, date.second);
      };
      this.messages = messages = {
        thing: $("#messages"),
        array: [],
        add_new: function(message) {
          var m_rep;
          this.array.push(message);
          m_rep = this.templated(message);
          m_rep.addClass('highlight');
          return this.thing.append(m_rep);
        },
        template: this.templates.find(".single-message"),
        templated: function(message) {
          var template;
          template = this.template.clone();
          template.find(".message-sender").text(message.sender.firstname + ' ' + message.sender.lastname);
          template.find('.message-date').text(message.dateReceived.toString());
          template.find('.message-message').text(message.message_content);
          return template;
        },
        contains: function(message) {
          var item, _i, _len, _ref;
          _ref = this.array;
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            item = _ref[_i];
            if (item.id === message.id) {
              return true;
            }
          }
          return false;
        }
      };
      Message = (function() {
        function Message(message_content, dateReceived, sender, id) {
          this.message_content = message_content;
          this.sender = sender;
          this.id = id;
          this.dateReceived = date_from_received(dateReceived);
        }

        return Message;

      })();
      add_messages = function(data) {
        var message, _i, _len;
        for (_i = 0, _len = data.length; _i < _len; _i++) {
          message = data[_i];
          message = new Message(message.message, message.date, message.sender, message.id);
          if (!messages.contains(message)) {
            messages.add_new(message);
          }
        }
      };
      check_messages = function() {
        return $.ajax({
          type: "POST",
          cache: false,
          url: debug ? '/messaging/test/get' : '/messaging/get',
          data: {
            last: messages.array.length === 0 ? new Date(1000, 0).toUTCString() : messages.array[messages.array.length - 1].dateReceived.toUTCString()
          },
          success: function(data) {
            if (data.length > 0) {
              messages.thing.children('.highlight').removeClass('highlight');
              return add_messages(data);
            }
          }
        });
      };
      send_message = function(e, form) {
        e.preventDefault();
        form = $(form);
        console.log(form);
        return $.ajax({
          type: "POST",
          cache: false,
          url: form.attr('action'),
          data: form.serialize(),
          success: function(data) {
            return e.target.reset();
          }
        });
      };
      new_form = (function(_this) {
        return function(receiver) {
          var form;
          form = _this.templates.find('.message-form').clone();
          form.find('.recipient').val(receiver);
          form.submit(function(e) {
            return send_message(e, this);
          });
          return $('#message-forms').html(form);
        };
      })(this);
      this.new_form = new_form;
      Receiver = (function() {
        function Receiver(firstname, lastname, id) {
          this.firstname = firstname;
          this.lastname = lastname;
          this.id = id;
        }

        return Receiver;

      })();
      receivers = {
        representation: $("#recipients"),
        array: [],
        template: this.templates.find('.receiver'),
        add_all: function(recipients) {
          var r, _i, _len;
          for (_i = 0, _len = recipients.length; _i < _len; _i++) {
            r = recipients[_i];
            this.add(r);
          }
        },
        add: function(recipient) {
          var rec, template;
          rec = new Receiver(recipient.firstname, recipient.lastname, recipient.id.identifier);
          if (!this.contains(rec)) {
            template = this.template.clone();
            template.find('.firstname').text(rec.firstname);
            template.find('.lastname').text(rec.lastname);
            this.array.push(rec);
            this.representation.append(template);
            return template.on('click', function() {
              return new_form(rec.id);
            });
          }
        },
        contains: function(recipient) {
          var r, _i, _len, _ref;
          _ref = this.array;
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            r = _ref[_i];
            if (r.id === recipient.id) {
              return true;
            }
          }
          return false;
        }
      };
      refresh_receivers = function() {
        return get_receivers(function(data) {
          return receivers.add_all(data);
        });
      };
      get_receivers = function(callback) {
        return $.ajax({
          type: "POST",
          cache: false,
          url: debug ? '/messaging/test/get/receivers' : '/messaging/get/receivers',
          success: callback
        });
      };
      chat_visible = false;
      toggle_chat = function() {
        if (chat_visible) {
          hide_chat();
          return chat_visible = false;
        } else {
          show_chat();
          return chat_visible = true;
        }
      };
      hide_chat = function() {
        return $('#messaging-area').css('display', 'none');
      };
      show_chat = function() {
        return $('#messaging-area').css('display', 'block');
      };
      $('form#message-form').submit(function(e) {
        return send_message(e, this);
      });
      $('#refresh-messages').on('click', function() {
        return check_messages();
      });
      $('#refresh-receivers').on('click', function() {
        return refresh_receivers();
      });
      $('#toggle-chat').on('click', function() {
        return toggle_chat();
      });
      refresh_receivers();
      check_messages();
      hide_chat();
    }

    return Messaging;

  })();

  $(document).ready(function() {
    if (window.messaging == null) {
      window.messaging = new Messaging();
    }
  });

}).call(this);
