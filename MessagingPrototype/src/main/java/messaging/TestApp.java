package messaging;

import org.salespointframework.Salespoint;
import org.salespointframework.SalespointSecurityConfiguration;
import org.salespointframework.SalespointWebConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * Created by justusadam on 20/11/14.
 */
@Configuration
@EnableAutoConfiguration
@Import({ SalespointWebConfiguration.class })
@EntityScan(basePackageClasses = {Salespoint.class, TestApp.class})
@EnableJpaRepositories(basePackageClasses = {Salespoint.class, TestApp.class})
@ComponentScan
public class TestApp {
    public static void main(String[] args) {
        SpringApplication.run(TestApp.class, args);
    }

    @Configuration
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    static class WebSecurityConfiguration extends SalespointSecurityConfiguration {

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.csrf().disable();

            http.authorizeRequests().antMatchers("/**").permitAll().and().formLogin().loginProcessingUrl("/login").and()
                    .logout().logoutUrl("/logout").logoutSuccessUrl("/");
        }
    }
}
