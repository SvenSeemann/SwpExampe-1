package fviv.model;

import fviv.areaPlanner.AreaItem;
import fviv.festival.Festival;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

/**
 * @author justusadam
 */
@Entity
public class Event {
    @Id
    @GeneratedValue
    private long id;

    @Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentLocalDateTime")
    private LocalDateTime start;

    @Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentLocalDateTime")
    private LocalDateTime end;

    /**
     * An artist associated with the event.
     */
    @ManyToOne
    private Artist artist;

    /**
     * The festival for which this event is supposed to happen.
     */
    @ManyToOne
    private Festival festival;

    @ManyToOne
    private AreaItem stage;

    public AreaItem getStage() {
        return stage;
    }

    public void setStage(AreaItem stage) {
        this.stage = stage;
    }

    @Deprecated
    public Event(){}

    public Event(LocalDateTime start, LocalDateTime end, Artist artist, Festival festival, AreaItem stage) {
        this.start = start;
        this.end = end;
        this.artist = artist;
        this.festival = festival;
        this.stage = stage;
    }

    public Festival getFestival() {
        return festival;
    }

    public void setFestival(Festival festival) {
        this.festival = festival;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

}
