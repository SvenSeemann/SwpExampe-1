package fviv.model;

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

    @ManyToOne
    private Artist artist;

    @ManyToOne
    private Festival festival;

    @Deprecated
    public Event(){}

    public Event(LocalDateTime start, LocalDateTime end, Artist artist, Festival festival) {
        this.start = start;
        this.end = end;
        this.artist = artist;
        this.festival = festival;
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
