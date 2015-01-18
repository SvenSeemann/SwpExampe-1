package fviv.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.junit.Assert.assertThat;
import static org.joda.money.CurrencyUnit.EUR;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.joda.money.Money;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;

import fviv.AbstractIntegrationTests;
import fviv.festival.Festival;
import fviv.festival.FestivalRepository;
import fviv.location.LocationRepository;
import fviv.model.Finance;
import fviv.model.Finance.FinanceType;
import fviv.model.FinanceRepository;

/**
 * @author Hendric Eckelt
 */

public class CreateControllerTest extends AbstractIntegrationTests {
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	CreateController controller;
	@Autowired
	FestivalRepository festivalRepository;
	@Autowired
	LocationRepository locationRepository;
	@Autowired
	FinanceRepository financeRepository;

	protected void login(String userName, String password) {
		Authentication authentication = new UsernamePasswordAuthenticationToken(
				userName, password);
		SecurityContextHolder.getContext().setAuthentication(
				authenticationManager.authenticate(authentication));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void CreateControllerIndexTest() {
		login("boss", "123");

		ModelMap modelMap = new ModelMap();
		String returnedView = controller.index(modelMap);

		// returnedView has to be festival
		assertThat(returnedView, is("festival"));

		// festivallist must contain all festivals from the repository
		assertThat((Iterable<Object>) modelMap.get("festivallist"),
				is(iterableWithSize((int) festivalRepository.count())));

		// locationlist must contain all locations from the repository
		assertThat((Iterable<Object>) modelMap.get("locationlist"),
				is(iterableWithSize((int) locationRepository.count())));
	}

	@Test
	public void CreateControllerSetNewSalaryTest() {
		login("boss", "123");

		long amountOfFinancesInRepository = financeRepository
				.findByFinanceType(FinanceType.SALARY).size();

		// Create a new festival
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-LL-dd");
		LocalDate date1 = LocalDate.parse("2014-12-30", formatter);
		LocalDate date2 = LocalDate.parse("2015-01-03", formatter);
		Festival testFestival = new Festival(date1, date2, "testFestival", 3,
				"testArtist", 5000, Money.of(EUR, 40));
		festivalRepository.save(testFestival);

		BigDecimal zero = new BigDecimal(0);
		BigDecimal seven = new BigDecimal(7);
		BigDecimal nine = new BigDecimal(9);

		controller.setSelected(testFestival);
		String returnedView = controller.setNewSalary(nine, zero, zero, seven,
				zero);

		// returnedView has to be "redirect:/festival"
		assertThat(returnedView, is("redirect:/festival"));

		// Validate that the salaries got changed correctly
		assertThat(festivalRepository.findById(testFestival.getId())
				.getManagementSalaryPerHour().abs(), is(Money.of(EUR, 9).abs()));
		assertThat(festivalRepository.findById(testFestival.getId())
				.getLeadershipSalaryPerHour().abs(), is(Money.of(EUR, 0).abs()));
		assertThat(festivalRepository.findById(testFestival.getId())
				.getCateringSalaryPerHour().abs(), is(Money.of(EUR, 0).abs()));
		assertThat(festivalRepository.findById(testFestival.getId())
				.getSecuritySalaryPerHour().abs(), is(Money.of(EUR, 7).abs()));
		assertThat(festivalRepository.findById(testFestival.getId())
				.getCleaningSalaryPerHour().abs(), is(Money.of(EUR, 0).abs()));

		// Validate that finances are created
		amountOfFinancesInRepository += 5;
		assertThat(financeRepository.findByFinanceType(FinanceType.SALARY),
				is(iterableWithSize((int) amountOfFinancesInRepository)));
	}
}
