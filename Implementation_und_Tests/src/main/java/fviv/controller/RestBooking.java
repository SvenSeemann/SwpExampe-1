package fviv.controller;

import fviv.model.Artist;
import fviv.model.ArtistsRepository;
import fviv.util.http.Headers;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

/**
 * @author justusadam
*/
@RestController
public class RestBooking {

    private final ArtistsRepository artistsRepository;

    @Autowired
    public RestBooking(ArtistsRepository artistsRepository){
        this.artistsRepository = artistsRepository;
    }

    @RequestMapping(value = "/booking/artists/book", method = RequestMethod.POST, headers = Headers.AJAX)
    public boolean bookNew(
            @RequestParam("id") long artistId,
            @RequestParam("name") String name,
            @RequestParam("price") long price,
            @RequestParam("genre") long genre){
        Artist artist = artistsRepository.findOne(artistId);
        if (artist == null) {
            artistsRepository.save(new Artist(artistId, Money.of(CurrencyUnit.EUR, price), name, genre));
            return true;
        } else {
            return false;
        }
    }

    @RequestMapping(value = "/booking/artists/booked", method = RequestMethod.POST, headers = Headers.AJAX)
    public List<Artist> booked(){
        Iterable<Artist> artists = artistsRepository.findAll();
        LinkedList<Artist> out = new LinkedList<>();
        for (Artist artist : artists) out.add(artist);
        return out;
    }

}
