class ATR
  templates :
    make_artist_field : (name, id, genre, price, technician) ->
      field = @artist_field.clone()
      field.find('.artist-name').text(name)
      field.find('.artist-genre').text(genre.name)
      field.find('.artist-genre').data('id', genre.id)
      field.find('.artist-genre').on 'click', -> window.atr.fill_genre_table(genre.id)

      field.find('.artist-technician').text(technician)
      field.find('.artist-price').text(price)
      _button = field.find('button.book')
      _button.on 'click', -> $.ajax(
        url : '/booking/book/artist'
        type : 'POST'
        data :
          id : id
          name : name
          genre : genre.id
          price : if price is null then 0 else price
        success : (data) ->
          if data
            $(_button).text 'Booked'
          else
            $(_button).text 'Is already Booked'
      )
      field
    make_table_row : (name, id, genre, price, technician) ->
      row = @table_row.clone()
      row.data('id', id)
      row.children('.artist-name').text name
      row.children('.artist-name').on 'click', -> window.atr.display_single_artist(id)

      row.children('.artist-genre').text(genre.name)
      row.children('.artist-genre').data('id', genre.id)
      row.children('.artist-genre').on 'click', -> window.atr.fill_genre_table(genre.id)

      row.children('.artist-technician').text(technician)
      row.children('.artist-price').text(price)
      row
    make_genre_table_row : (name, id, price, technician) ->
      row = @table_row.clone()
      row.data('id', id)
      row.children('.artist-name').text(name)
      row.children('.artist-genre').remove()
      row.children('.artist-name').on 'click', -> window.atr.display_single_artist(id)

      row.children('.artist-technician').text(technician)
      row.children('.artist-price').text(price)
      row
  constructor : ->
    table_row = $('#artists-table').find('.single-artist')
    @templates.table_row = table_row.clone()
    table_row.remove()
    artist_field = $('#artist-showcase').find('.single-artist')
    @templates.artist_field = artist_field.clone()
    artist_field.remove()

  baseurl = 'http://178.238.237.25:8081/ArtistServiceRails/'

  urls :
    base_url : baseurl
    genre : baseurl + 'genre'
    by_genre_id : (id) -> @genre + '/' + id
    all : baseurl + 'artist'
    artist_by_id : (id) -> @all + '/' + id

  request : (url, callback) ->
    $.ajax(
      type : 'GET'
      url : url
      success : (data) -> callback(data)
      crossDomain : true
      error : (a,b,c) ->
        console.log(a,b,c)
    )

  init_all_artists : ->
    @request(@urls.all, (data) =>
      table = $('#artists-table').children('tbody')
      table.empty()
      for artist in data
        table.append @templates.make_table_row(
          artist.name,
          artist.id,
          artist.genre,
          artist.price,
          artist.technician
          )
      return
    )

  fill_genre_table : (id) ->
    @request @urls.by_genre_id(id), (data) =>
      table = $('#artists-genre-table').children('tbody')
      table.empty()
      for artist in data.artists
        table.append @templates.make_genre_table_row(
          artist.name,
          artist.id,
          artist.price,
          artist.technician
        )
      return


  display_single_artist: (id) ->
    @request @urls.artist_by_id(id), (data) =>
      field = $('#artist-showcase')
      field.empty()
      field.append(
        @templates.make_artist_field(data.name, data.id, data.genre, data.price, data.technician)
      )
      return



$(document).ready( ->
  window.atr = new ATR() unless window.atr?
  window.atr.init_all_artists()
  return
)