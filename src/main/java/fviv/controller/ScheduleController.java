package fviv.controller;

import fviv.festival.FestivalRepository;
import fviv.model.EventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author justusadam
 */
@Controller
public class ScheduleController {

    private EventsRepository eventsRepository;

    private FestivalRepository festivalRepository;

    @Autowired
    public ScheduleController(EventsRepository eventsRepository, FestivalRepository festivalRepository){
        this.eventsRepository = eventsRepository;
        this.festivalRepository = festivalRepository;
    }

    @RequestMapping(value = "/schedule", method = RequestMethod.GET, params = "festival")
    public String handle(Model model, @RequestParam("festival") Long festival){
        model.addAttribute("events",eventsRepository.findByFestivalOrderByStartAsc(festivalRepository.findOne(festival)));
        return "schedule";
    }

    @RequestMapping(value = "/schedule", method = RequestMethod.GET)
    public String handle(Model model){
        model.addAttribute("festivals", festivalRepository.findAll());
        return "scheduleOverview";
    }
}
