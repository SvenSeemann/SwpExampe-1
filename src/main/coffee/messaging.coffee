
debug = true

class messaging
  date_from_received = (date) ->
    new Date(
      date.year,
      date.monthValue - 1,
      date.dayOfMonth,
      date.hour,
      date.minute,
      date.second
    )

  messages =
    thing : $("#messages").children('tbody')
    array : []
    add_new : (message) ->
      @array.push(message)
      @thing.append @templated message
    templated : (message) ->
      '<tr class="highlight">
      <td class="message-sender">' + message.sender.firstname + message.sender.lastname + '</td>
      <td class="message-date">[' + message.dateReceived.toString() + ']</td>
      <td class="message-separator" style="text-align: center;">:</td>
      <td class="message-message">' + message.message_content + '</td>
      </tr>'
    contains : (message) ->
      for item in @array
        if item.id is message.id
          return true
      false

  class Message
    constructor : (@message_content, dateReceived, @sender, @id) ->
      @dateReceived = date_from_received dateReceived

  add_messages = (data) ->
    for message in data
      message = new Message(message.message, message.date, message.sender, message.id)
      unless messages.contain message
        messages.add_new message

  check_messages = ->
    $.ajax(
      type: "POST",
      cache: false,
      url: if debug then '/messaging/test/get' else '/messaging/get',
      data:
        last: if messages.array.length == 0 then new Date(1000, 0).toUTCString() else messages.array[messages.array.length - 1].dateReceived.toUTCString()

      success: (data) ->
        if data.length > 0
          messages.message_thing.children('.highlight').removeClass 'highlight'
          add_messages data
    )

  send_message = (e, form) ->
    e.preventDefault()
    form = $(form)
    console.log(form)
    $.ajax(
      type: "POST"
      cache: false
      url: form.attr('action')
      data: form.serialize()
      success: (data) ->
        e.target.reset()
    )

  new_form = (receiver) ->
    form = $(
      '<form id="message-forms" action="/messaging/test/send" method="post">
      <input type="hidden" value="' + receiver + '" name="recipient">
      <label for="message-content">Your Message</label>
      <textarea name="message" id="message-content"></textarea>
      <button type="Submit" value="submit">Submit</button>
      </form>'
    )
    form.submit(
      (e) => send_message(e)
    )
    $('#message-forms').html(
      form
    )

  class Receiver
    constructor : (@firstname, @lastname, @id) ->

  receivers =
    representation : $("#recipients")
    array : []
    add_all : (recipients) ->
      for r in recipients
        @add r
    add : (recipient) ->
      rec = new Receiver(recipient.firstname, recipient.lastname, recipient.id.identifier)
      unless @contains(rec)
        @array.push rec
        @representation.append(
          '<a class="receiver" onclick="messaging.new_form(\'' + rec.id + '\')">' +
          rec.firstname +
          rec.lastname +
          '</a>'
        )
    contains : (recipient) ->
      for r in @array
        if r.id is recipient.id
          return true
      false

  refresh_receivers = ->
    get_receivers (data) -> receivers.add_all(data)

  get_receivers = (callback) ->
    $.ajax(
      type: "POST",
      cache: false,
      url: if debug then '/messaging/test/get/receivers' else '/messaging/get/receivers'
      success: callback
    )

  constructor: ->
    $('form#message-form').submit((e) ->
      send_message(e, this)
    )
    $('#refresh-messages').on('click', ->
      check_messages()
    )
    $('#refresh-receivers').on('click', ->
      refresh_receivers()
    )

    refresh_receivers()
    check_messages()


$(document).ready -> messaging = new messaging()