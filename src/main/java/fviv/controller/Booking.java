package fviv.controller;

import fviv.areaPlanner.AreaItem;
import fviv.areaPlanner.AreaItemsRepository;
import fviv.booking.LineupForm;
import fviv.festival.Festival;
import fviv.festival.FestivalRepository;
import fviv.model.Artist;
import fviv.model.ArtistsRepository;
import fviv.model.Event;
import fviv.model.EventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDateTime;

/**
 * @author justusadam
 */
@Controller
public class Booking {

    private RestBooking restBooking;
    private EventsRepository eventsRepository;
    private ArtistsRepository artistsRepository;
    private FestivalRepository festivalRepository;
    private AreaItemsRepository areaItemsRepository;

    @Autowired
    public Booking(RestBooking restBooking, EventsRepository eventsRepository, ArtistsRepository artistsRepository, FestivalRepository festivalRepository, AreaItemsRepository areaItemsRepository){
        this.restBooking = restBooking;
        this.eventsRepository = eventsRepository;
        this.artistsRepository = artistsRepository;
        this.festivalRepository = festivalRepository;
        this.areaItemsRepository = areaItemsRepository;
    }

    @RequestMapping(value = "booking/artist", method = RequestMethod.POST)
    public String handle(@Validated LineupForm lineupForm){
        LocalDateTime start = LocalDateTime.of(lineupForm.getStartYear(), lineupForm.getStartMonth(), lineupForm.getStartDay(), lineupForm.getStartHour(), lineupForm.getStartMinute(), 0);
        LocalDateTime end = start.plusHours(lineupForm.getDuration());
        Artist artist = artistsRepository.findOne(lineupForm.getArtist());
        Festival festival = festivalRepository.findById(lineupForm.getFestival());
        AreaItem stage =  areaItemsRepository.findOne(lineupForm.getStage());
        if (festival != null && artist != null) {
            eventsRepository.save(new Event(start, end, artist, festival, stage));
        }
        return "redirect:/booking/artist/schedule";

    }

    @RequestMapping(value = "booking/artist", method = RequestMethod.GET)
    public String handle(Model model) {
        model.addAttribute("bookedArtists", restBooking.booked());
        model.addAttribute("festivals", festivalRepository.findAll());
        model.addAttribute("stages", areaItemsRepository.findByType(AreaItem.Type.STAGE));
        return "lineup";
    }
}
