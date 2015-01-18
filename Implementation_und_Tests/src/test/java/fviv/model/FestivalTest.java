package fviv.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.joda.money.Money;

import static org.joda.money.CurrencyUnit.EUR;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.iterableWithSize;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fviv.AbstractIntegrationTests;
import fviv.festival.Festival;
import fviv.festival.FestivalRepository;

/**
 * @author Hendric Eckelt
 */

public class FestivalTest extends AbstractIntegrationTests {
	@Autowired
	FestivalRepository festivalRepository;
	
	@Test
	public void newFestivalTest() {
		// Create new festivals
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-LL-dd");
		LocalDate date1 = LocalDate.parse("2015-01-10", formatter);
		LocalDate date2 = LocalDate.parse("2015-01-16", formatter);

		Festival testFestival1 = new Festival(date1, date2, "testFestival1", 3, "testActors1", 5000, Money.of(EUR, 40));
		Festival testFestival2 = new Festival(date2, date1, "testFestival2", 3, "testActors2", 5, Money.of(EUR, 260));
		festivalRepository.save(testFestival1);
		festivalRepository.save(testFestival2);
		
		// Check if festivals got saved to the repository
		assertThat(festivalRepository.findByFestivalName("testFestival1"), is(iterableWithSize(1)));
		assertThat(festivalRepository.findByFestivalName("testFestival2"), is(iterableWithSize(1)));
	
		// Validate the ticket price
		assertThat(testFestival1.getPreisTag().abs(), is(Money.of(EUR, 40).abs()));
		assertThat(testFestival2.getPreisTag().abs(), is(Money.of(EUR, 260).abs()));
	}
}
