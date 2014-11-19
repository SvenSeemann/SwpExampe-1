package catering;

import org.salespointframework.Salespoint;
import org.salespointframework.SalespointSecurityConfiguration;
import org.salespointframework.SalespointWebConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;



@Configuration
@EnableAutoConfiguration
@EntityScan(basePackageClasses = { Salespoint.class, CateringPrototype.class })
@EnableJpaRepositories(basePackageClasses = { Salespoint.class, CateringPrototype.class })
@ComponentScan
public class CateringPrototype {

	
	
    public static void main(String[] args) {
        SpringApplication.run(CateringPrototype.class, args);
    }
    
    @Configuration
	static class CateringPrototypeWebConfiguration extends SalespointWebConfiguration {

		/**
		 * We configure {@code /login} to be directly routed to the {@code login} template without any controller
		 * interaction.
		 * 
		 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addViewControllers(org.springframework.web.servlet.config.annotation.ViewControllerRegistry)
		 */
		@Override
		public void addViewControllers(ViewControllerRegistry registry) {
			registry.addViewController("/login").setViewName("login");
		}
	} 
    
    @Bean
	public CharacterEncodingFilter characterEncodingFilter() {

		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);

		return characterEncodingFilter;
	} 
    
    
   Order order;
   
    
    
    @Configuration
	@EnableGlobalMethodSecurity(prePostEnabled = true)
	static class WebSecurityConfiguration extends SalespointSecurityConfiguration {

		/**
		 * Disabling Spring Security's CSRF support as we do not implement pre-flight request handling for the sake of
		 * simplicity. Setting up basic security and defining login and logout options.
		 * 
		 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
		 */
		@Override
		protected void configure(HttpSecurity http) throws Exception {

			http.csrf().disable();

			http.authorizeRequests().antMatchers("/**").permitAll().and().//
					formLogin().loginPage("/login").loginProcessingUrl("/login").and(). //
					logout().logoutUrl("/logout").logoutSuccessUrl("/");
		}
	}
}