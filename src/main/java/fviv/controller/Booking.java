package fviv.controller;

import fviv.booking.Artist;
import fviv.booking.ArtistsRepository;
import fviv.util.http.Headers;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author justusadam
*/
@RestController
public class Booking {

    private final ArtistsRepository artistsRepository;

    @Autowired
    public Booking(ArtistsRepository artistsRepository){
        this.artistsRepository = artistsRepository;
    }

    @RequestMapping(value = "/booking/book/artist", method = RequestMethod.POST, headers = Headers.AJAX)
    public boolean bookNew(
            @RequestParam("id") long artistId,
            @RequestParam("name") String name,
            @RequestParam("price") long price,
            @RequestParam("genre") long genre){
        Artist artist = artistsRepository.findOne(artistId);
        System.out.println(artist);
        System.out.println(price);
        System.out.println(artistId);
        if (artist == null) {
            artistsRepository.save(new Artist(artistId, Money.of(CurrencyUnit.EUR, price), name, genre));
            return true;
        } else {
            return false;
        }
    }

}
