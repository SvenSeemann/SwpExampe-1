package fviv.booking;

import org.joda.money.Money;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author justusadam
 */
@Entity
public class Artist {

    @Id
    private long id;

    private Money price;

    private String name;

    public long getId() {
        return id;
    }

    private long genre;

    public Money getPrice() {
        return price;
    }

    public void setPrice(Money price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getGenre() {
        return genre;
    }

    public void setGenre(long genre) {
        this.genre = genre;
    }

    @Deprecated
    public Artist(){}

    public Artist(long id, Money price, String name, long genre) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.genre = genre;
    }

    @Deprecated
    public Artist(long id, String name){
        this.id = id;
        this.name = name;
    }
}
