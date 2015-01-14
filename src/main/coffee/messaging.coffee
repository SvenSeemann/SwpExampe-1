
debug = false

class Messaging
  constructor : ->
    templates = $ '#messaging-templates'
    @templates = templates.clone()
    templates.remove()


    date_from_received = (date) ->
      new Date(
        date.year,
        date.monthValue - 1,
        date.dayOfMonth,
        date.hour,
        date.minute,
        date.second
      )

    @messages = messages =
      thing       : $("#messages")
      array       : []
      add_new     : (message) ->
        @array.push(message)
        m_rep = @templated message
        m_rep.addClass 'highlight'
        @thing.append m_rep

      template    : @templates.find ".single-message"
      templated   : (message) ->
        template = @template.clone()
        template.find(".message-sender").text message.sender.firstname + ' ' + message.sender.lastname
        template.find('.message-date').text message.dateReceived.toString()
        template.find('.message-message').text message.message_content
        template

      contains    : (message) ->
        for item in @array
          if item.id is message.id
            return true
        false


    class Message
      constructor : (@message_content, dateReceived, @sender, @id) ->
        @dateReceived = date_from_received dateReceived

    add_messages = (data) ->
      for message in data
        message = new Message message.message, message.date, message.sender, message.id
        unless messages.contains message
          messages.add_new message
      return

    check_messages = ->
      $.ajax(
        type    : "POST",
        cache   : false,
        url     : if debug then '/messaging/test/get' else '/messaging/get',
        data    :
          last  : if messages.array.length == 0 then new Date(1001, 0).toUTCString() else messages.array[messages.array.length - 1].dateReceived.toUTCString()

        success : (data) ->
          if data.length > 0
            messages.thing.children('.highlight').removeClass 'highlight'
            add_messages data
      )


    send_message = (e, form) ->
      e.preventDefault()
      form = $ form
      console.log form
      $.ajax(
        type    : "POST"
        cache   : false
        url     : form.attr 'action'
        data    : form.serialize()
        success : (data) ->
          e.target.reset()
      )


    new_form = (receiver) =>
      form = @templates.find('.message-form').clone()
      form.find('.recipient').val receiver
      form.submit (e) -> send_message e, this

      $('#message-forms').html form

    @new_form = new_form

    class Receiver
      constructor : (@firstname, @lastname, @id) ->

    receivers =
      representation  : $ "#recipients"
      array           : []
      template        : @templates.find '.receiver'
      add_all         : (recipients) -> @add r for r in recipients; return
      add             : (recipient) ->
        rec = new Receiver recipient.firstname, recipient.lastname, recipient.id.identifier
        unless @contains(rec)
          template = @template.clone()
          template.find('.firstname').text rec.firstname
          template.find('.lastname').text rec.lastname
          @array.push rec
          @representation.append template

          template.on 'click', -> new_form rec.id

      contains        : (recipient) ->
        for r in @array
          if r.id is recipient.id
            return true
        false

    refresh_receivers = ->
      get_receivers (data) -> receivers.add_all data


    get_receivers = (callback) ->
      $.ajax(
        type: "POST",
        cache: false,
        url: if debug then '/messaging/test/get/receivers' else '/messaging/get/receivers'
        success: callback
      )

    chat_visible = false

    toggle_chat = ->
      if chat_visible
        hide_chat()
        chat_visible = false
      else
        show_chat()
        chat_visible = true

    hide_chat = ->      
$('#messaging-area').animate
    width : 0px
    height: 0px
    $('#messaging-area').css 'display', 'none'


    show_chat = ->
      $('#messaging-area').css 'display', 'block'


    $('form#message-form').submit((e) -> send_message e, this)

    $('#refresh-messages').on 'click', -> check_messages()

    $('#refresh-receivers').on 'click', -> refresh_receivers()

    $('#toggle-chat').on 'click', -> toggle_chat()


    refresh_receivers()
    check_messages()

    hide_chat()


$(document).ready( ->
  window.messaging = new Messaging() unless window.messaging?
  return
)