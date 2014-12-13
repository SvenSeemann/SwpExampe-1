package fviv;

import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Base class for integration tests with necessary imports and annotations,
 * bootstrapping the core fviv {@link Application} configuration class as proposed in the videoshop example.
 * 
 * @author Niklas Fallik
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class AbstractIntegrationTests {

}
