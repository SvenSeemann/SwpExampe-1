package messaging;

import org.salespointframework.SalespointSecurityConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * Created by justusadam on 20/11/14.
 */
@Configuration
@EnableAutoConfiguration
//@EntityScan(basePackageClasses = {Salespoint.class, messaging.TestJavaApp.class})
//@EnableJpaRepositories(basePackageClasses = {Salespoint.class, messaging.TestJavaApp.class})
@ComponentScan
public class TestJavaApp {
    public static void main(String[] args) {
        SpringApplication.run(TestJavaApp.class, args);
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
