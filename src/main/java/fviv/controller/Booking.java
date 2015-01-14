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
import fviv.util.collections.Tuple2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

/**
 * @author justusadam
 */
@Controller
@PreAuthorize("hasRole('ROLE_BOSS')")
public class Booking {

    private RestBooking restBooking;
    private EventsRepository eventsRepository;
    private ArtistsRepository artistsRepository;
    private AreaItemsRepository areaItemsRepository;
    private ScheduleController scheduleController;
    private FestivalRepository festivalRepository;

    @Autowired
    public Booking(RestBooking restBooking, EventsRepository eventsRepository, ArtistsRepository artistsRepository, AreaItemsRepository areaItemsRepository, ScheduleController scheduleController, FestivalRepository festivalRepository){
        this.restBooking = restBooking;
        this.eventsRepository = eventsRepository;
        this.artistsRepository = artistsRepository;
        this.areaItemsRepository = areaItemsRepository;
        this.scheduleController = scheduleController;
        this.festivalRepository = festivalRepository;
    }

    @RequestMapping(value = "booking/artist", method = RequestMethod.POST)
    public String handle(@Validated LineupForm lineupForm){
        LocalDateTime start = LocalDateTime.of(lineupForm.getStartYear(), lineupForm.getStartMonth(), lineupForm.getStartDay(), lineupForm.getStartHour(), lineupForm.getStartMinute(), 0);
        LocalDateTime end = start.plusHours(lineupForm.getDuration());
        Artist artist = artistsRepository.findOne(lineupForm.getArtist());
        AreaItem stage =  areaItemsRepository.findOne(lineupForm.getStage());
        if (artist != null && stage != null) {
            eventsRepository.save(new Event(start, end, artist, stage.getFestival(), stage));
        }
        return "redirect:/booking/artist/schedule";

    }

    @RequestMapping(value = "booking/artist", method = RequestMethod.GET)
    public String handle(Model model) {
        List<Tuple2<Festival,List<Tuple2<AreaItem,Iterable<Event>>>>> list = new LinkedList<>();
        for (Festival festival : festivalRepository.findAll()) {
            list.add(new Tuple2<>(festival, scheduleController.getEvents(festival)));
        }
        model.addAttribute("bookedArtists", restBooking.booked());
        model.addAttribute("festivalSchedules", list);
        return "lineup";
    }
}
