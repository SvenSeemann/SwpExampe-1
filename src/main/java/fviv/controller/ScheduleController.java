package fviv.controller;

import fviv.areaPlanner.AreaItem;
import fviv.areaPlanner.AreaItemsRepository;
import fviv.festival.Festival;
import fviv.festival.FestivalRepository;
import fviv.model.Event;
import fviv.model.EventsRepository;
import fviv.util.collections.Tuple2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedList;
import java.util.List;

/**
 * @author justusadam
 */
@Controller
public class ScheduleController {

    private EventsRepository eventsRepository;

    private FestivalRepository festivalRepository;

    private AreaItemsRepository areaItemsRepository;

    @Autowired
    public ScheduleController(EventsRepository eventsRepository, FestivalRepository festivalRepository, AreaItemsRepository areaItemsRepository){
        this.eventsRepository = eventsRepository;
        this.festivalRepository = festivalRepository;
        this.areaItemsRepository = areaItemsRepository;
    }

    @RequestMapping(value = "/schedule", method = RequestMethod.GET, params = "festival")
    public String handle(Model model, @RequestParam("festival") Long festival){
        model.addAttribute("stages", getEvents(festivalRepository.findOne(festival)));
        return "schedule";
    }

    @RequestMapping(value = "/schedule", method = RequestMethod.GET)
    public String handle(Model model){
        model.addAttribute("festivals", festivalRepository.findAll());
        return "scheduleOverview";
    }

    public List<Tuple2<AreaItem,Iterable<Event>>> getEvents(Festival festival) {
        List<Tuple2<AreaItem,Iterable<Event>>> list = new LinkedList<>();
        for (AreaItem stage : areaItemsRepository.findByFestivalAndType(festival, AreaItem.Type.STAGE)) {
            list.add(new Tuple2<>(stage, eventsRepository.findByStageOrderByStartAsc(stage)));
        }
        return list;
    }
}
