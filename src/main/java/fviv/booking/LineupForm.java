package fviv.booking;

import javax.validation.constraints.NotNull;

/**
 * @author justusadam
 */
public class LineupForm {

    @NotNull
    private long artist;

    @NotNull private int startYear;
    @NotNull private int startMonth;
    @NotNull private int startDay;
    @NotNull private int startHour;
    @NotNull private int startMinute;

    @NotNull private long stage;

    public long getStage() {
        return stage;
    }

    public void setStage(long stage) {
        this.stage = stage;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    @NotNull private long duration;


    public long getArtist() {
        return artist;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void setArtist(long artist) {
        this.artist = artist;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public int getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(int startMonth) {
        this.startMonth = startMonth;
    }

    public int getStartDay() {
        return startDay;
    }

    public void setStartDay(int startDay) {
        this.startDay = startDay;
    }

}
