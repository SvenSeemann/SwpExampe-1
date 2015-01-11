package fviv;

import org.neo4j.graphdb.GraphDatabaseService;
import org.salespointframework.Salespoint;
import org.salespointframework.SalespointSecurityConfiguration;
import org.salespointframework.SalespointWebConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.rest.SpringRestGraphDatabase;

@Configuration
@EnableAutoConfiguration
//@EnableNeo4jRepositories(basePackages = "fviv")
@EntityScan(basePackageClasses = { Salespoint.class, Application.class })
@EnableJpaRepositories(basePackageClasses = { Salespoint.class,
		Application.class })
@ComponentScan
public class Application /*extends Neo4jConfiguration*/{

	/*public static GraphDatabaseService repo;

	public Application() {
		repo = new SpringRestGraphDatabase("http://localhost:7474/db/data");
		setBasePackage("jrmds");
	}

	@Bean
	public GraphDatabaseService graphDatabaseService() {
		return repo;
	}*/
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Configuration
	static class ApplicationWebConfiguration extends
			SalespointWebConfiguration {

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

	@Configuration
	@EnableGlobalMethodSecurity(prePostEnabled = true)
	static class WebSecurityConfiguration extends
			SalespointSecurityConfiguration {

		@Override
		protected void configure(HttpSecurity http) throws Exception {

			http.csrf().disable();
			http.authorizeRequests().antMatchers("/**").permitAll().and()
					.//
					formLogin().loginPage("/login")
					.loginProcessingUrl("/login").and(). //
					logout().logoutUrl("/logout").logoutSuccessUrl("/index");
		}
		
		/*@Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http.csrf().disable();
			http
	            .authorizeRequests()
	                .antMatchers("/**").permitAll()
	                .anyRequest().authenticated()
	                .and()
	            .formLogin()
	                .loginPage("/login")
	                .permitAll()
	                .and()
	            .logout()
	                .permitAll();
	    }*/
		
	}
}