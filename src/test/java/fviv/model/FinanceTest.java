package fviv.model;

import org.joda.money.Money;

import static org.joda.money.CurrencyUnit.EUR;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.iterableWithSize;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fviv.AbstractIntegrationTests;
import fviv.model.Finance.FinanceType;
import fviv.model.Finance.Reference;

/**
 * @author Hendric Eckelt
 */

public class FinanceTest extends AbstractIntegrationTests {
	@Autowired
	FinanceRepository financeRepository;

	@Test
	public void newFinanceTest() {
		// Create new finances
		Finance testFinance1 = new Finance(2, Reference.EXPENSE, Money.of(EUR,
				80), FinanceType.RENT);
		Finance testFinance2 = new Finance(1, Reference.DEPOSIT, Money.of(EUR,
				356), FinanceType.CATERING);
		financeRepository.save(testFinance1);
		financeRepository.save(testFinance2);

		// Validate references
		assertThat(testFinance1.getReference(), is(Reference.EXPENSE));
		assertThat(testFinance2.getReference(), is(Reference.DEPOSIT));

		// Validate money
		assertThat(testFinance1.getAmount().getAmountMajorLong(), is(80L));
		assertThat(testFinance2.getAmount().getAmountMajorLong(), is(356L));

		// Validate financeType
		assertThat(testFinance1.getFinanceType(), is(FinanceType.RENT));
		assertThat(testFinance2.getFinanceType(), is(FinanceType.CATERING));

		// Check if the finances got saved to the repository
		assertThat(financeRepository.findAll(), is(iterableWithSize(2)));
	}
}
