package fviv;

import java.io.IOException;

import net.sourceforge.barbecue.BarcodeException;

import org.salespointframework.Salespoint;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.boot.SpringApplication;

import fviv.model.Manager;


@Configuration
@EnableAutoConfiguration
@EntityScan(basePackageClasses = { Salespoint.class, Application.class })
@ComponentScan
public class Application {
	public static void main(String[] args) throws IOException, BarcodeException{
		SpringApplication.run(Application.class, args);
Manager.pdfvorlagebearbeiten();
Manager.barcodegen();
Manager.addbarcode();
	}
	
	@Configuration
	@EnableWebSecurity
	static class SecurityConfiguration extends WebSecurityConfigurerAdapter{
		@Override
		protected void configure(HttpSecurity http) throws Exception{
			http.csrf().disable();
			http.authorizeRequests().antMatchers("/**").permitAll();
		}
	}
}
